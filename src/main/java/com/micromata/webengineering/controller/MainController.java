package com.micromata.webengineering.controller;

import com.micromata.webengineering.persistence.EntryRepository;
import com.micromata.webengineering.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * Main controller.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Controller
public class MainController {
  private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

  @Autowired
  private EntryRepository entryRepository;

  @Autowired
  private UserService userService;

  @RequestMapping("/fakeLogin")
  public ModelAndView fakeLogin() {
    ModelAndView mav = new ModelAndView("index");

    User user = new User("user", "foo", AuthorityUtils.createAuthorityList("ROLE_USER"));
    Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(auth);

    return mav;
  }

  @RequestMapping("/")
  public ModelAndView index() {
    LOG.info("Request to /index");

    ModelAndView mav = new ModelAndView("index");

    // TODO ML Clean up.
    mav.addObject("list", entryRepository.findAllByOrderByVotesDesc());
    mav.addObject("date", new Date());

    boolean authenticated = userService.isAuthenticated();
    mav.addObject("auth", authenticated);
    if (authenticated) {
      mav.addObject("user", userService.getUser());
    }

    return mav;
  }
}
