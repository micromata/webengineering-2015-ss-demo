package com.micromata.webengineering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides methods to work with Entry objects.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Service
public class EntryService {
  private static final Logger LOG = LoggerFactory.getLogger(EntryService.class);

  @Autowired
  private EntryRepository entryRepository;

  /**
   * Creates a new entry and stores it.
   *
   * @param title the entry' title
   */
  public long createEntry(String title) {
    Entry entry = new Entry();
    entry.setTitle(title);
    // One upvote from the user who created the entry.
    entry.setVotes(1L);
    LOG.info("Entry created. entry={}", entry);
    entryRepository.save(entry);

    return entry.getId();
  }

  /**
   * Upvotes an entry given by the id.
   *
   * @param id the id of the entry.
   */
  public void upvote(Long id) {
    Entry entry = entryRepository.findOne(id);
    if (entry == null) {
      LOG.warn("Entry not found. id={}", id);
      return;
    }

    Long votes = entry.getVotes();
    if (votes == null) {
      LOG.error("votes for entry is null. id={}", id);
      return;
    }

    votes += 1;
    // Check for overflow.
    if (votes < 0) {
      LOG.warn("Maximal votes reached. Ignoring upvote. id={}", id);
      votes = Long.MAX_VALUE;
    }

    entry.setVotes(votes);
    entryRepository.save(entry);
    LOG.info("Entry upvoted. vote={}, id={}", votes, id);
  }

  /**
   * Downvotes an entry given by the id.
   *
   * @param id the id of the entry.
   */
  public void downvote(Long id) {
    Entry entry = entryRepository.findOne(id);
    if (entry == null) {
      LOG.warn("Entry not found. id={}", id);
      return;
    }

    Long votes = entry.getVotes();
    if (votes == null) {
      LOG.error("votes for entry is null. id={}", id);
      return;
    }

    votes -= 1;
    // Check for negative votes.
    if (votes < 0) {
      LOG.debug("Minimum votes reached, ignoring downvote. id={}", id);
      return;
    }

    entry.setVotes(votes);
    entryRepository.save(entry);
    LOG.info("Entry downvoted. vote={}, id={}", votes, id);
  }
}
