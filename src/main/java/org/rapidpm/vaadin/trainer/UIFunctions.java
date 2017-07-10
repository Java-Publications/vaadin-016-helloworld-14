package org.rapidpm.vaadin.trainer;

import java.util.Optional;
import java.util.function.BiFunction;

import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;

/**
 *
 */
public interface UIFunctions {

  public static BiFunction<HasComponents,String, Optional<Component>> componentWithID() {
    return (root, id) -> {
      for (Component child : root) {
        if (id.equals(child.getId())) {
          return Optional.of(child);
        } else if (child instanceof HasComponents) {
          Optional<Component> ret = componentWithID().apply((HasComponents) child, id);
          if (ret.isPresent()) return ret;
        }
      }
      // none was found
      return Optional.empty();
    };
  }


}
