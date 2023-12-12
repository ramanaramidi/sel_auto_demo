package pages.web.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import pages.web.OrangeHRMPages.AdminOrgHrmPage;

import commons.enums.LoginOptionEnum;
import commons.objects.Site;
import commons.objects.Users;
import org.checkerframework.checker.units.qual.A;


//import pages.web.Tracker.*;
//import pages.web.Tracker.site.SiteFopsPage;
//import pages.web.Tracker.site.SiteTrackerPage;
import pages.web.onboarding.LoginPage;
//import pages.web.reports.ImportPage;
//import pages.web.reports.RunReportsPage;

import java.util.List;

public class MainSideMenu extends BasePage {

    public WebDriver driver;

    public MainSideMenu(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
    public By adminOrgHrm = By.xpath("//span[text()='Admin']");
    public By pimTab = By.linkText("PIM");
    public By timeTab = By.linkText("Time");





    public AdminOrgHrmPage goToAdminOrgHrm() throws Exception {
        waitUntilVisibleElement(find(adminOrgHrm));
        click(find(adminOrgHrm));
        return new AdminOrgHrmPage(driver);
    }
    public AdminOrgHrmPage gotoPimOrgHrmPage() throws Exception {
        waitUntilVisibleElement(find(pimTab));
        click(find(pimTab));
        return new AdminOrgHrmPage(driver);
    }

    public AdminOrgHrmPage gotoTimeOrgHrmPage() throws Exception {
        waitUntilVisibleElement(find(timeTab));
        click(find(timeTab));
        return new AdminOrgHrmPage(driver);
    }







}