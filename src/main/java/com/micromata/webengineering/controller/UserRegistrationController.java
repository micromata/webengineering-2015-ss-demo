package com.micromata.webengineering.controller;

import com.micromata.webengineering.persistence.User;
import com.micromata.webengineering.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * User registration controller.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Controller
public class UserRegistrationController {
  private static final Logger LOG = LoggerFactory.getLogger(UserRegistrationController.class);

  @Autowired
  private UserService userService;

  @Autowired
  private MainController mainController;

  // Note that we could also use a bean here. Left as an exercise for the reader.
  @RequestMapping("/register")
  public ModelAndView search(String username, String password1, String password2) {
    LOG.info("Request to /register for username={}", username);
    ModelAndView mav = new ModelAndView("register");

    // Error on initial page.
    boolean error = username == null;

    if (StringUtils.equals(password1, password2) == false) {
      addProperty(mav, "passwordsNotEqual");
      error = true;
    }

    if (userService.userExists(username)) {
      addProperty(mav, "userExists");
      error = true;
    } else {
      mav.addObject("username", username);
    }

    if (error == false) {
      User user = userService.registerUser(username, password1);
      userService.login(user);
      return mainController.index(new PageRequest(0, 20));
    }

    return mav;
  }

  private void addProperty(ModelAndView mav, String key) {
    mav.addObject(key, true);
  }
}
