package com.micromata.webengineering;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

  @Override
  public String toString() {
    return "Entry{" +
        "votes=" + votes +
        ", title='" + title + '\'' +
        '}';
  }
}
