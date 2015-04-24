package com.micromata.webengineering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @RequestMapping("/")
  public ModelAndView index() {
    LOG.info("Request to /index");

    ModelAndView mav = new ModelAndView("index");

    mav.addObject("list", entryRepository.findAllByOrderByVotesDesc());
    mav.addObject("date", new Date());

    return mav;
  }

  @RequestMapping("/entry")
  public String entry(@RequestParam("title") String title) {
    LOG.info("Request to /entry");
    LOG.debug("title={}", title);

    Entry entry = new Entry();
    entry.setTitle(title);
    entry.setVotes((long) (Math.random() * 10000));
    LOG.debug("Entry generated. entry={}", entry);
    entryRepository.save(entry);

    return "redirect:/";
  }
}
