package org.rapidpm.vaadin.trainer.modules.mainview.write;

import org.rapidpm.vaadin.trainer.modules.AbstractBaseCustomComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

/**
 *
 */
public class WriteComponent extends AbstractBaseCustomComponent {

  @Override
  protected Component createComponent() {
    return new Label("WriteComponent");
  }
}
