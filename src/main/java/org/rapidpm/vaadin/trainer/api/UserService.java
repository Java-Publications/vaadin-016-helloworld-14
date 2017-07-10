package org.rapidpm.vaadin.trainer.api;

import java.util.Optional;

/**
 *
 */
public interface UserService {
  Optional<User> loadUser(String login);
}
