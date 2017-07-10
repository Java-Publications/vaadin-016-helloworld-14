package org.rapidpm.vaadin.trainer.modules.mainview.report;

import org.rapidpm.vaadin.trainer.modules.AbstractBaseCustomComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

/**
 *
 */
public class ReportComponent extends AbstractBaseCustomComponent {

  @Override
  protected Component createComponent() {
    return new Label("Report");
  }
}

