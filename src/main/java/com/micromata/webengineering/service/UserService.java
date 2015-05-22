package com.micromata.webengineering.service;

import com.micromata.webengineering.persistence.User;
import com.micromata.webengineering.persistence.UserRepository;
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
}
