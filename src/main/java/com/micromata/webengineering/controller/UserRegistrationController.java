package com.micromata.webengineering.controller;

import com.micromata.webengineering.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

  @RequestMapping("/register")
  public ModelAndView search(String username, String password1, String password2) {
    LOG.info("Request to /register for username={}", username);
    ModelAndView mav = new ModelAndView("register");

    return mav;
  }
}
