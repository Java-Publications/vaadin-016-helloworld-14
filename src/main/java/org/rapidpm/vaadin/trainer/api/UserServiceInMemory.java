package org.rapidpm.vaadin.trainer.api;

import java.util.Optional;

/**
 *
 */
public class UserServiceInMemory implements UserService {

  //How to hold in sync with Shiro.ini ??? next part
  @Override
  public Optional<User> loadUser(String login) {
    if(login.equals("admin")) return Optional.of(new User().login("admin").foreName("Admin").familyName("Secure"));
    if(login.equals("max")) return Optional.of(new User().login("max").foreName("Max").familyName("Rimkus"));
    if(login.equals("sven")) return Optional.of(new User().login("sven").foreName("Sven").familyName("Ruppert"));
    return Optional.empty();
  }
}
