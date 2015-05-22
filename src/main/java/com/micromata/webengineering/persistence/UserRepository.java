package com.micromata.webengineering.persistence;

import org.springframework.data.repository.CrudRepository;

/**
 * Methods for accessing users (User-objects) in the database.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
public interface UserRepository extends CrudRepository<User, Long> {
  // Empty.
}
