package org.rapidpm.vaadin.trainer.api;

import java.io.Serializable;

/**
 *
 */
public class User  implements Serializable {

  private String login;
  private String foreName;
  private String familyName;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (! (o instanceof User)) return false;

    User user = (User) o;

    if (! login.equals(user.login)) return false;
    if (! foreName.equals(user.foreName)) return false;
    return familyName.equals(user.familyName);
  }

  @Override
  public int hashCode() {
    int result = login.hashCode();
    result = 31 * result + foreName.hashCode();
    result = 31 * result + familyName.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "User{" +
           "login='" + login + '\'' +
           ", foreName='" + foreName + '\'' +
           ", familyName='" + familyName + '\'' +
           '}';
  }

  public User login(final String login) {
    this.login = login;
    return this;
  }

  public User foreName(final String foreName) {
    this.foreName = foreName;
    return this;
  }

  public User familyName(final String familyName) {
    this.familyName = familyName;
    return this;
  }


  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getForeName() {
    return foreName;
  }

  public void setForeName(String foreName) {
    this.foreName = foreName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }
}
