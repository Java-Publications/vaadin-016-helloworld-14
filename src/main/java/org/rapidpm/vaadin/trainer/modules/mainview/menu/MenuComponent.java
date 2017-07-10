package org.rapidpm.vaadin.trainer.modules.mainview.menu;

import static org.apache.shiro.SecurityUtils.getSubject;
import static org.rapidpm.vaadin.trainer.ComponentIDGenerator.buttonID;
import static org.rapidpm.vaadin.trainer.modules.login.LoginComponent.SESSION_ATTRIBUTE_USER;

import java.util.function.Supplier;
import java.util.stream.Stream;

import org.rapidpm.frp.model.Pair;
import org.rapidpm.vaadin.trainer.modules.AbstractBaseCustomComponent;
import org.rapidpm.vaadin.trainer.modules.mainview.MainView;
import org.rapidpm.vaadin.trainer.modules.mainview.calc.CalcComponent;
import org.rapidpm.vaadin.trainer.modules.mainview.dashboard.DashboardComponent;
import org.rapidpm.vaadin.trainer.modules.mainview.report.ReportComponent;
import org.rapidpm.vaadin.trainer.modules.mainview.write.WriteComponent;
import org.vaadin.dialogs.ConfirmDialog;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 */
public class MenuComponent extends AbstractBaseCustomComponent {

  private final CssLayout contentLayout;

  public MenuComponent(CssLayout contentLayout) {
    this.contentLayout = contentLayout;
  }


  @Override
  protected Component createComponent() {

    final CssLayout menuButtons = new CssLayout();
    menuButtons.setSizeFull();
    menuButtons.addStyleName(ValoTheme.MENU_PART);
    menuButtons.addStyleName(ValoTheme.MENU_PART_LARGE_ICONS);
    menuButtons.addComponents(getComponents());

    return menuButtons;
  }

  private Component[] getComponents() {
    return Stream
        .of(
            createMenuButton(VaadinIcons.VIEWPORT, "Dashboard", DashboardComponent::new),
//            createMenuButton(VaadinIcons.SITEMAP, "Sitemap", DashboardComponent::new),
            createMenuButton(VaadinIcons.ABACUS, "Calculate", CalcComponent::new),
            createMenuButton(VaadinIcons.EDIT, "Write", WriteComponent::new),
            createMenuButton(VaadinIcons.BAR_CHART, "Report", ReportComponent::new),
            createMenuButtonForNotification(VaadinIcons.EXIT, "Logout", "You want to go?")
        )
        .filter(p -> getSubject().isPermitted(p.getT1()))
        .map(Pair::getT2)
        .map(Component.class::cast)
        .toArray(Component[]::new);
  }


  //TODO more generic - refactoring
  private Pair<String, Button> createMenuButtonForNotification(VaadinIcons icon, String caption, String message) {
    final Button button
        = new Button(caption,
                     (e) -> {
                       UI ui = UI.getCurrent();
                       ConfirmDialog.show(
                           ui,
                           message, // ToDo extract in Executor
                           (ConfirmDialog.Listener) dialog -> {
                             if (dialog.isConfirmed()) {

                               getSubject().logout(); //removes all identifying information and invalidates their session too.

                               VaadinSession vaadinSession = ui.getSession();
                               vaadinSession.setAttribute(SESSION_ATTRIBUTE_USER, null);
                               vaadinSession.close();
                               ui.getPage().setLocation("/");
                             }
                             else {
                               // User did not confirm
                               // CANCEL STUFF
                             }
                           });
                     });

    button.setIcon(icon);
    button.addStyleName(ValoTheme.BUTTON_HUGE);
    button.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_TOP);
    button.addStyleName(ValoTheme.BUTTON_BORDERLESS);
    button.addStyleName(ValoTheme.MENU_ITEM);
    button.setWidth("100%");

    button.setId(buttonID().apply(MainView.class, caption));

    return new Pair<>(mapToShiroRole(caption), button);

  }


  private Pair<String, Button> createMenuButton(VaadinIcons icon, String caption, Supplier<CustomComponent> content) {
    final Button button = new Button(caption, (e) -> {
      contentLayout.removeAllComponents();
      contentLayout.addComponent(content.get());
    });
    button.setIcon(icon);
    button.addStyleName(ValoTheme.BUTTON_HUGE);
    button.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_TOP);
    button.addStyleName(ValoTheme.BUTTON_BORDERLESS);
    button.addStyleName(ValoTheme.MENU_ITEM);
    button.setWidth("100%");

    button.setId(buttonID().apply(this.getClass(), caption));
    return new Pair<>(mapToShiroRole(caption), button);
  }

  //not nice
  private String mapToShiroRole(String caption) {
    return (caption.equals("Calculate")) ? "math:CalcComponent" :
           (caption.equals("Write")) ? "write:WriteComponent" :
           (caption.equals("Report")) ? "report:*" :
           "all:default";
  }

}
