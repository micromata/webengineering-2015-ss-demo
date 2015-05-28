package com.micromata.webengineering.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Methods for accessing entries (Entry-objects) in the database.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
public interface EntryRepository extends CrudRepository<Entry, Long> {
  List<Entry> findAllByOrderByVotesDesc();

  @Query("SELECT e FROM Entry e WHERE LOWER(e.title) LIKE %?1% ORDER BY e.votes DESC")
  List<Entry> search(String title);
}
