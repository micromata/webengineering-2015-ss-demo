package com.micromata.webengineering.service;

import com.micromata.webengineering.persistence.Entry;
import com.micromata.webengineering.persistence.User;
import com.micromata.webengineering.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Domain-specific methods for users.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Service
public class UserService {
  private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private UserRepository userRepository;

  public boolean isAuthenticated() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return (principal instanceof String) == false;
  }

  public User getUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof String) {
      return null;
    }

    org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) principal;
    User user = userRepository.findByUsername(userDetails.getUsername());
    if (user == null) {
      LOG.warn("User from session not found. username={}", userDetails.getUsername());
      return null;
    }

    return user;
  }

  public boolean addUpvote(Entry entry) {
    User user = getUser();
    if (user == null) {
      return false;
    }

    if (user.getVotedEntries().contains(entry)) {
      LOG.info("User already voted for entry. userId={}, entryId={}", user.getId(), entry.getId());
      return false;
    }

    user.getVotedEntries().add(entry);
    userRepository.save(user);
    LOG.info("User voted for entry. userId={}, entryId={}", user.getId(), entry.getId());
    return true;
  }

  public boolean addDownvote(Entry entry) {
    User user = getUser();
    if (user == null) {
      return false;
    }

    if (user.getVotedEntries().contains(entry) == false) {
      LOG.info("User has not yet voted for entry. userId={}, entryId={}", user.getId(), entry.getId());
      return false;
    }

    user.getVotedEntries().remove(entry);
    userRepository.save(user);
    LOG.info("User downvoted for entry. userId={}, entryId={}", user.getId(), entry.getId());
    return true;
  }

  public boolean userExists(String username) {
    return username != null && userRepository.findByUsername(username) != null;
  }

  public User registerUser(String username, String password) {
    if (userExists(username)) {
      LOG.warn("User already exists. username={}", username);
      return null;
    }

    User user = new User();
    user.setUsername(username);
    user.setPassword(new ShaPasswordEncoder(256).encodePassword(password, null));
    user.setVotedEntries(Collections.emptyList());
    userRepository.save(user);
    LOG.info("User created. username={}", username);
    return user;
  }

  public void login(User user) {
    org.springframework.security.core.userdetails.User authUser = new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        AuthorityUtils.createAuthorityList("ROLE_USER"));
    Authentication auth = new UsernamePasswordAuthenticationToken(authUser, authUser.getPassword(), authUser.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(auth);
    LOG.info("Programatically logged in user. username={}", user.getUsername());
  }
}
