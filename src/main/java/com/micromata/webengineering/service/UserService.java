package com.micromata.webengineering.service;

import com.micromata.webengineering.persistence.Entry;
import com.micromata.webengineering.persistence.User;
import com.micromata.webengineering.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

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

  public User getUser() {
    Iterator<User> it = userRepository.findAll().iterator();
    if (it.hasNext() == false) {
      User user = new User();
      user.setUsername("user");
      user.setPassword("foo");
      userRepository.save(user);
      return user;
    }

    return it.next();
  }

  public boolean addUpvote(Entry entry) {
    User user = getUser();

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

    if (user.getVotedEntries().contains(entry) == false) {
      LOG.info("User has not yet voted for entry. userId={}, entryId={}", user.getId(), entry.getId());
      return false;
    }

    user.getVotedEntries().remove(entry);
    userRepository.save(user);
    LOG.info("User downvoted for entry. userId={}, entryId={}", user.getId(), entry.getId());
    return true;
  }
}
