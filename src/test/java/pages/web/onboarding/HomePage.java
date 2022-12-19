package pages.web.onboarding;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;

import static java.lang.Thread.sleep;

public class HomePage extends BasePage {

    public WebDriver driver;
    //private WebDriverWait wait = new WebDriverWait(driver, 60);

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public By welcomeLabel = By.cssSelector("label[id='ttlPage'] nobr");

    public String getWelcomeLabelText() throws InterruptedException {
        sleep(50000);
        return driver.findElement(welcomeLabel).getText();
    }
}
