package com.micromata.webengineering.service;

import com.micromata.webengineering.persistence.Entry;
import com.micromata.webengineering.persistence.EntryRepository;
import com.micromata.webengineering.persistence.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

  @Autowired
  private UserService userService;

  /**
   * Creates a new entry and stores it.
   *
   * @param title the entry' title
   */
  @Transactional
  public long createEntry(String title) {
    if (userService.isAuthenticated() == false) {
      LOG.info("Non-authenticated user tried to post an entry");
      return -1;
    }

    Entry entry = new Entry();
    entry.setTitle(title);
    // One upvote from the user who created the entry.
    entry.setVotes(1L);
    LOG.info("Entry created. entry={}", entry);

    User user = userService.getUser();
    entry.setCreator(user);

    try {
      entryRepository.save(entry);
    } catch (DataIntegrityViolationException e) {
      LOG.warn("Error while storing entry (DataIntegrityViolationException)", e);
      return -1;
    }

    return entry.getId();
  }

  /**
   * Upvotes an entry given by the id.
   *
   * @param id the id of the entry.
   */
  @Transactional
  public void upvote(Long id) {
    if (userService.isAuthenticated() == false) {
      return;
    }

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

    boolean isVoted = userService.addUpvote(entry);
    if (isVoted == false) {
      return;
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
  @Transactional
  public void downvote(Long id) {
    if (userService.isAuthenticated() == false) {
      return;
    }

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

    boolean isDownVoted = userService.addDownvote(entry);
    if (isDownVoted == false) {
      return;
    }

    entry.setVotes(votes);
    entryRepository.save(entry);
    LOG.info("Entry downvoted. vote={}, id={}", votes, id);
  }

  public List<Entry> search(String query) {
    return entryRepository.search(query.toLowerCase());
  }
}
