package org.rapidpm.vaadin.trainer.modules.mainview.calc;

import org.rapidpm.vaadin.trainer.modules.AbstractBaseCustomComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

/**
 *
 */
public class CalcComponent extends AbstractBaseCustomComponent {
  @Override
  protected Component createComponent() {
    return new Label("CalcComponent");
  }
}
