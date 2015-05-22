package com.micromata.webengineering.persistence;

import javax.persistence.*;

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

  @OneToOne
  private User creator;

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Entry entry = (Entry) o;
    return !(id != null ? !id.equals(entry.id) : entry.id != null);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
