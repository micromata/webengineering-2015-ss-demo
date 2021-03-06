package com.micromata.webengineering.controller;

import com.micromata.webengineering.persistence.Entry;
import com.micromata.webengineering.persistence.EntryRepository;
import com.micromata.webengineering.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  @RequestMapping("/")
  public ModelAndView index(Pageable pageable) {
    LOG.info("Request to /index");

    ModelAndView mav = new ModelAndView("index");

    // TODO ML Clean up.

    Page<Entry> page = entryRepository.findAllByOrderByVotesDesc(pageable);
    mav.addObject("list", page);
    mav.addObject("page", page);
    mav.addObject("date", new Date());

    boolean authenticated = userService.isAuthenticated();
    mav.addObject("auth", authenticated);
    if (authenticated) {
      mav.addObject("user", userService.getUser());
    }

    return mav;
  }
}
