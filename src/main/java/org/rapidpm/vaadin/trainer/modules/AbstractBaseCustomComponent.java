package org.rapidpm.vaadin.trainer.modules;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

/**
 *
 */
public abstract class AbstractBaseCustomComponent extends CustomComponent  {

  public AbstractBaseCustomComponent() {
    setCompositionRoot(createComponent());
    setSizeFull();
  }

  protected abstract Component createComponent();
}
