package com.micromata.webengineering.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * User object.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Entity
public class User {
  @Id
  @GeneratedValue
  private Long id;

  @Version
  private Long version;

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
