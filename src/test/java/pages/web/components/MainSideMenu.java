package pages.web.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import pages.web.OrangeHRMPages.AdminOrgHrmPage;
import java.util.List;

public class MainSideMenu extends BasePage {

    public WebDriver driver;

    public MainSideMenu(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
    public By adminOrgHrm = By.xpath("//span[text()='Admin']");




    public AdminOrgHrmPage goToAdminOrgHrm() throws Exception {
        waitUntilVisibleElement(find(adminOrgHrm));
        click(find(adminOrgHrm));
        return new AdminOrgHrmPage(driver);
    }
}