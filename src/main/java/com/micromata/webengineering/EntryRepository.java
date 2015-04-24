package com.micromata.webengineering;

import org.springframework.data.repository.CrudRepository;

/**
 * Methods for accessing entries (Entry-objects) in the database.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
public interface EntryRepository extends CrudRepository<Entry, Long> {
  // Empty on purpose.
}
