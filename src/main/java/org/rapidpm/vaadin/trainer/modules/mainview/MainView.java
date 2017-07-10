package org.rapidpm.vaadin.trainer.modules.mainview;

import static org.rapidpm.vaadin.trainer.ComponentIDGenerator.cssLayoutID;
import static org.rapidpm.vaadin.trainer.ComponentIDGenerator.horizontalLayoutID;
import static org.rapidpm.vaadin.trainer.ComponentIDGenerator.verticalLayoutID;

import org.rapidpm.vaadin.server.MainUI;
import org.rapidpm.vaadin.trainer.modules.AbstractBaseCustomComponent;
import org.rapidpm.vaadin.trainer.modules.mainview.menu.MenuComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 */
public class MainView extends AbstractBaseCustomComponent {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainView.class);

  @Override
  protected Component createComponent() {
    final CssLayout contentLayout = new CssLayout(new Label("Content"));
    contentLayout.setSizeFull();
    contentLayout.setId(cssLayoutID().apply(MainUI.class, "Content"));

    final VerticalLayout menuLayout = new VerticalLayout();
    menuLayout.setId(verticalLayoutID().apply(MainUI.class, "MenuLayout"));
    menuLayout.setStyleName(ValoTheme.MENU_ROOT);
    menuLayout.setWidth(100, Unit.PERCENTAGE);
    menuLayout.setHeight(100, Unit.PERCENTAGE);
    menuLayout.setSizeFull();

    // to hard bound
    menuLayout.addComponent(new MenuComponent(contentLayout));


    final HorizontalLayout mainLayout = new HorizontalLayout();
    mainLayout.setId(horizontalLayoutID().apply(MainUI.class, "MainLayout"));
    mainLayout.setSizeFull();
    mainLayout.addComponent(menuLayout);
    mainLayout.addComponent(contentLayout);

    mainLayout.setExpandRatio(menuLayout, 0.20f);
    mainLayout.setExpandRatio(contentLayout, 0.80f);

    return mainLayout;
  }

}
