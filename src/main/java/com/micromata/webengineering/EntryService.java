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
    entry.setVotes((long) (Math.random() * 10000));
    LOG.info("Entry created. entry={}", entry);
    entryRepository.save(entry);

    return entry.getId();
  }
}
