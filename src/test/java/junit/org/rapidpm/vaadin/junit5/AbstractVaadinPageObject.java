package junit.org.rapidpm.vaadin.junit5;

import org.openqa.selenium.WebDriver;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.PasswordFieldElement;
import com.vaadin.testbench.elements.TextFieldElement;

/**
 *
 */
public abstract class AbstractVaadinPageObject
    extends TestBenchTestCase
    implements VaadinPageObject {


  public AbstractVaadinPageObject(WebDriver webDriver) {
    setDriver(webDriver);
  }

  public void switchToDebugMode() {
    getDriver().get(url().get() + "?debug&restartApplication");
  }

  public void restartApplication() {
    getDriver().get(urlRestartApp().get());
  }

  public void loadPage() {
    WebDriver driver = getDriver();
    driver.get(url().get());
  }

  public WithID<TextFieldElement> textField() {
    return (id) -> $(TextFieldElement.class).id(id);
  }

  public WithID<PasswordFieldElement> passwordField() {
    return (id) -> $(PasswordFieldElement.class).id(id);
  }

  public WithID<ButtonElement> btn() {
    return (id) -> $(ButtonElement.class).id(id);
  }


}
