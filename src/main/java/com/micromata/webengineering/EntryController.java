package com.micromata.webengineering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.Callable;

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
    retryOperation(() -> {
      entryService.upvote(id);
      return null;
    });

    return "redirect:/";
  }

  @RequestMapping("/entry/{id}/downvote")
  public String downvoteEntry(@PathVariable("id") Long id) {
    LOG.info("Request to /entry/downvote with id={}", id);

    // Upvote until it succeeds.
    retryOperation(() -> {
      entryService.downvote(id);
      return null;
    });
    return "redirect:/";
  }

  @ResponseBody
  @RequestMapping("/entry/all")
  public List<Entry> allEntries() {
    return entryRepository.findAllByOrderByVotesDesc();
  }

  /**
   * Retries an SQL operation until it succeeds, i.e. no OptimisticLockingFailureException is thrown.
   * <p>
   * Rethrows any other exception as a RuntimeException.
   *
   * @param method the method.
   */
  private void retryOperation(Callable<Void> method) {
    boolean retry;
    do {
      retry = false;
      try {
        method.call();
      } catch (OptimisticLockingFailureException e) {
        LOG.warn("Concurrent upvote, retrying.");
        retry = true;
      } catch (Exception e) {
        LOG.warn("Error while calling retry-able method.", e);
        throw new RuntimeException(e);
      }
    } while (retry);
  }
}
