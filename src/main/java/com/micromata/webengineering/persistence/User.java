package com.micromata.webengineering.persistence;

import javax.persistence.*;
import java.util.List;

/**
 * User object.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Entity
@Table(name = "USERS")
public class User {
  @Id
  @GeneratedValue
  private Long id;

  @Version
  private Long version;

  @ManyToMany
  List<Entry> votedEntries;

  public List<Entry> getVotedEntries() {
    return votedEntries;
  }

  public void setVotedEntries(List<Entry> votedEntries) {
    this.votedEntries = votedEntries;
  }

  private String username;
  private String password;

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "User{" +
        "username='" + username + '\'' +
        ", id=" + id +
        ", version=" + version +
        ", password='" + password + '\'' +
        '}';
  }
}
