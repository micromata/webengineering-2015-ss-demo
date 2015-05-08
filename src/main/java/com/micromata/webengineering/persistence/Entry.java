package com.micromata.webengineering.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * POJO (plain old java object) for entries.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Entity
public class Entry {
  @Id
  @GeneratedValue
  private Long id;

  @Version
  private Long version;

  private Long votes;
  private String title;

  public Long getVotes() {
    return votes;
  }

  public void setVotes(Long votes) {
    this.votes = votes;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Entry{" +
        "id=" + id +
        ", votes=" + votes +
        ", title='" + title + '\'' +
        '}';
  }
}
