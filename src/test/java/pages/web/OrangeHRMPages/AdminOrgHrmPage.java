package pages.web.OrangeHRMPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

public class AdminOrgHrmPage extends BasePage {

    public WebDriver driver;

    public AdminOrgHrmPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public By userName = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");

    public By userRole = By.xpath("(//div[@class='oxd-select-text-input'])[1]");


    public boolean isUserNameEnable() throws Exception {
        sleep(5);


        waitUntilVisibleElement(find(userName));

        System.out.println(getTitle());

        WebElement userNameField = find(userName);

        if (userNameField.isEnabled()) {

            return true;

        } else {
            return false;
        }
    }

    public void UserRoleFiled() throws Exception {
        sleep(5);
        waitUntilVisibleElement(find(userRole));
        WebElement userRoleField = find(userRole);
        userRoleField.sendKeys("ESS");

        /*
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].value='Admin';",userRoleField);

         */
        sleep(5);

    }






}
