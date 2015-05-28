package com.micromata.webengineering.controller;

import com.micromata.webengineering.service.EntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Search controller.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Controller
public class SearchController {
  private static final Logger LOG = LoggerFactory.getLogger(SearchController.class);

  @Autowired
  private EntryService entryService;

  @RequestMapping("/search")
  public ModelAndView search(String query) {
    LOG.info("Request to /search");
    ModelAndView mav = new ModelAndView("search");

    return mav;
  }
}
