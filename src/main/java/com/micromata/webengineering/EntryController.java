package com.micromata.webengineering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for handling all request of entries.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Controller
public class EntryController {
  private static final Logger LOG = LoggerFactory.getLogger(EntryController.class);

  @Autowired
  private EntryRepository entryRepository;

  @Autowired
  private EntryService entryService;

  @RequestMapping("/entry")
  public String entry(@RequestParam("title") String title) {
    LOG.info("Request to /entry");
    LOG.debug("title={}", title);

    entryService.createEntry(title);

    return "redirect:/";
  }

  @RequestMapping("/entry/{id}/upvote")
  public String upvoteEntry(@PathVariable("id") Long id) {
    LOG.info("Request to /entry/upvote with id={}", id);

    // Upvote until it succeeds.
    boolean retry;
    do {
      retry = false;
      try {
        entryService.upvote(id);
      } catch (OptimisticLockingFailureException e) {
        LOG.warn("Concurrent upvote, retrying.");
        retry = true;
      }
    } while (retry);
    return "redirect:/";
  }
}
