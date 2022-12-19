package pages.web.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import pages.web.Tracker.*;
import pages.web.Tracker.site.SiteTrackerPage;
import pages.web.reports.ImportPage;
import pages.web.reports.RunReportsPage;

import java.util.List;

public class MainSideMenu extends BasePage {

    public WebDriver driver;

    public MainSideMenu(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public By AdminMenu = By.xpath("//input[@title='Admin Menu']");
    public By overflowAdminMenu = By.xpath("//div[text()='Admin Menu']");
    public By AppCenter = By.xpath("//div[text()='App Center']");
    public By RingTracker = By.xpath("//div[text()='Ring Tracker']");
    public By ProjectTracker = By.xpath("//div[text()='Project Tracker']");
    public By ringTrackerLocator = By.xpath("//div[text()='Ring Tracker']");
    public By mainLogo = By.xpath("//img[@id='clientLogo']");
    public By SiteTracker = By.xpath("//div[text()='Site Tracker']");
    public By porTracker = By.xpath("//div[text()='POR Tracker']");
    public By mainSideOverflow = By.xpath("//input[@id='btnOverflow']");

    public By RFSector = By.xpath("//div[text()='RF Sector/Cell Tracker']");
    public By searchBar = By.xpath("//div[@class='component panelForm panel_menu']//input[@id='search_']");
    public By importData = By.xpath("//div[@title='Data Ingestion']//following-sibling::div[@class='im_items']//div[text()='Import Data']");
    public By edbProducer = By.xpath("//div[@title='EDB Producer Message Tracker']//a");
    public By edbConsumer = By.xpath("//div[@title='EDB Consumer Message Tracker']//a");
    public By runReportsLocator = By.xpath("//div[text()='Run Reports']");

    public By infoCenter = By.xpath("//div[text()='Info Center']");

    public By RunReports = By.xpath("//div[text()='Run Reports']");

    public By dataViews = By.xpath("//div[text()='Data Views']");
    public By VendorMainMenu = By.xpath("//input[@title='Vendor']");
    public By mainLogo1 = By.xpath("//*[@id='mainLogo']");
    public By userLogoff = By.xpath("//*[@id='itemLogoff']/div");
    public By userNameText = By.xpath("//*[@id='topPanelUserName']");
    public By loginButton = By.xpath("//*[@id='btn_11033']");
    public By siteDevButton = By.xpath("//*[@id='10017374']");
    public By appCenter = By.xpath("//*[@id='itemMenu_10017374_10018560']/div[1]/div[1]/div/input");
    public By rfEngineeringMainOption = By.xpath("//input[@title='RF Engineering']");
    public By MSCTracker = By.xpath("//div[@title='Market Switch MSC Tracker']");
    public By ImportLink = By.linkText("Import Data");
    public By RingLink = By.linkText("Ring Tracker");
    public By SiteLink = By.linkText("Site Tracker");
    public By PORLink = By.linkText("POR Tracker");
    public By ProjectLink = By.linkText("Project Tracker");
    public By siteDevelopmentMainOption = By.xpath("//input[@title='Site Development']");

    public void navigateToAppCenter() throws Exception {
        List<WebElement> adminOption = findAll(AdminMenu,2);
        if(adminOption.size()>0){
            waitUntilVisibleElement(find(AdminMenu));
            find(AdminMenu).click();
            sleep(2);
        }
        else {
            List<WebElement> overflowMenu = findAll(mainSideOverflow,2);
            if(overflowMenu.size()>0){
                click(overflowMenu.get(0));
                click(find(overflowAdminMenu));
                fullScreen();
            }
        }
        if(!find(ringTrackerLocator).isDisplayed())
            find(AppCenter).click();
    }

    public void navigateToAppCenterRfEng() throws Exception {
        List<WebElement> adminOption = findAll(rfEngineeringMainOption,2);
        if(adminOption.size()>0){
            waitUntilVisibleElement(find(rfEngineeringMainOption));
            find(rfEngineeringMainOption).click();
            sleep(2);
        }
        else {
            List<WebElement> overflowMenu = findAll(mainSideOverflow,2);
            if(overflowMenu.size()>0){
                click(overflowMenu.get(0));
                click(find(overflowAdminMenu));
                fullScreen();
            }
        }
        if(!find(ringTrackerLocator).isDisplayed())
            find(AppCenter).click();
    }

    public void reset() throws Exception {
        navigateToAppCenter();
        sleep(2);
        clickMainLogo();
        sleep(5);
    }

    public void clickingRingTracker() throws Exception {
        waitUntilVisibleElement(find(RingTracker));
        click(find(RingTracker));
    }

    public void clickingProjectTracker() throws Exception {
        waitUntilVisibleElement(find(ProjectTracker));
        click(find(ProjectTracker));
    }

    public void clickingRFSectorTracker() throws Exception {
        waitUntilVisibleElement(find(RFSector));
        click(find(RFSector));
    }

    public RFSectorPage goToRFSectorTracker() throws Exception {
        navigateToAppCenter();
        clickingRFSectorTracker();
        sleep(10);
        return new RFSectorPage(driver);
    }

    public RFSectorPage goToRFSectorTrackerAsEng() throws Exception {
        navigateToAppCenterRfEng();
        clickingRFSectorTracker();
        sleep(10);
        return new RFSectorPage(driver);
    }

    public RunReportsPage goToRunReports() throws Exception {
        navigateToInfoCenter();
        waitUntilVisibleElement(find(dataViews));
        find(dataViews).click();
        sleep(10);
        clickingRunReports();
        return new RunReportsPage(driver);
    }

    public void navigateToInfoCenter() throws Exception {
        List<WebElement> adminOption = findAll(AdminMenu,2);
        if(adminOption.size()>0){
            waitUntilVisibleElement(find(AdminMenu));
            find(AdminMenu).click();
            sleep(2);
        }
        else {
            List<WebElement> overflowMenu = findAll(mainSideOverflow,2);
            if(overflowMenu.size()>0){
                click(overflowMenu.get(0));
                click(find(overflowAdminMenu));
                fullScreen();
            }
        }
        if(!find(runReportsLocator).isDisplayed())
            find(infoCenter).click();
    }

    private void clickingRunReports() throws Exception {
//        waitUntilVisibleElement(find(RunReports));
//        sleep(10);
        scrollToElement(find(RunReports));
        find(RunReports).click();
    }

    public RingTrackerPage goToRingTracker() throws Exception {
        navigateToAppCenter();
        clickingRingTracker();
        return new RingTrackerPage(driver);
    }

    public ProjectTrackerPage goToProjectTracker() throws Exception {
        navigateToAppCenter();
        sleep(4);
        clickingProjectTracker();
        sleep(5);
        return new ProjectTrackerPage(driver);
    }

    public ProjectTrackerPage goToVendorProjectTracker() throws Exception {
        fullScreen();
        waitUntilVisibleElement(find(VendorMainMenu));
        click(find(VendorMainMenu));
        clickingProjectTracker();
        sleep(5);
        return new ProjectTrackerPage(driver);
    }

    public void refreshPage() throws InterruptedException {
        refresh();
        sleep(5);
    }

    public void clickMainLogo() throws Exception {
        find(mainLogo1).click();
    }

    public SiteTrackerPage goToSiteTracker() throws Exception {
        navigateToAppCenter();
        clickingSiteTracker();
        return new SiteTrackerPage(driver);
    }

    public void clickingSiteTracker() throws Exception {
        waitUntilVisibleElement(find(SiteTracker));
        find(SiteTracker).click();
    }

    public void clickingPORTracker() throws Exception {
        waitUntilVisibleElement(find(porTracker));
        find(porTracker).click();
    }

    public PORTrackerPage goToPorTrackerPage() throws Exception {
        navigateToAppCenter();
        clickingPORTracker();
        return new PORTrackerPage(driver);
    }

    public void search(String option) throws Exception {
        setText(find(searchBar),option);
    }
    public ImportPage goToImportDataPage() throws Exception {
        navigateToAppCenter();
        search("import");
        click(find(importData));
        sleep(5);
        return new ImportPage(driver);
    }

    public EdbProducerTrackerPage goToEdbProducerPage() throws Exception {
        navigateToAppCenter();
        search("EDB P");
        click(find(edbProducer));
        sleep(5);
        return new EdbProducerTrackerPage(driver);
    }

    public EdbConsumerTrackerPage goToEdbConsumerPage() throws Exception {
        navigateToAppCenter();
        search("EDB C");
        click(find(edbConsumer));
        sleep(5);
        return new EdbConsumerTrackerPage(driver);
    }

    public ProjectTrackerPage navigateToSDMVendor() throws Exception{
        clickMainLogo();
        waitUntilVisibleElement(find(mainLogo1));
        clickingProjectTracker();
        sleep(10);
        return new ProjectTrackerPage(driver);
    }
    public Boolean isRingTrackerPresent() throws Exception {
        List<WebElement> allTrackers = findAll(By.tagName("a"));
        System.out.println("allTrackers Size is - "+allTrackers.size());

        for (WebElement tracker : allTrackers) {
                if(tracker.getText().equalsIgnoreCase("Ring Tracker")){
                    System.out.println("Found Ring Tracker - " + tracker.getText());
                    return true;
                }
            }
       System.out.println("Ring Tracker Not Found" );
       return false;
    }

    public Boolean isSiteTrackerPresent() throws Exception {
        sleep(5);
        List<WebElement> allTrackers = findAll(By.tagName("a"));
        System.out.println("allTrackers Size is - "+allTrackers.size());

        for (WebElement tracker : allTrackers) {
            if(tracker.getText().equalsIgnoreCase("Site Tracker")){
                System.out.println("Found Site Tracker - " + tracker.getText());
                return true;
            }
        }
        System.out.println("Site Tracker Not Found" );
        return false;
    }

    public Boolean isPORTrackerPresent() throws Exception {
        sleep(5);
        List<WebElement> allTrackers = findAll(By.tagName("a"));
        System.out.println("allTrackers Size is - "+allTrackers.size());

        for (WebElement tracker : allTrackers) {
            if(tracker.getText().equalsIgnoreCase("POR Tracker")){
                System.out.println("Found POR Tracker - " + tracker.getText());
                return true;
            }
        }
        System.out.println("POR Tracker Not Found" );
        return false;
    }

    public Boolean isProjectTrackerPresent() throws Exception {
        sleep(5);
        List<WebElement> allTrackers = findAll(By.tagName("a"));
        System.out.println("allTrackers Size is - "+allTrackers.size());

        for (WebElement tracker : allTrackers) {
            if(tracker.getText().equalsIgnoreCase("Project Tracker")){
                System.out.println("Found Project Tracker - " + tracker.getText());
                return true;
            }
        }
        System.out.println("Project Tracker Not Found" );
        return false;
    }

    public Boolean importDataPresent() throws Exception {
        sleep(5);
        List<WebElement> allTrackers = findAll(By.tagName("a"));
        System.out.println("allTrackers Size is - "+allTrackers.size());

        for (WebElement tracker : allTrackers) {
            if(tracker.getText().equalsIgnoreCase("Import Data")){
                System.out.println("Found Import Data - " + tracker.getText());
                return true;
            }
        }
        System.out.println("Import Data Not Found" );
        return false;
    }

    public void userLogoff() throws Exception {
        sleep(6);
        // WebElement userName = textAreaByTitle("topPanelUserName");
        waitUntilVisibleElement(find(userNameText));
        click(find(userNameText));
        sleep(2);
        waitUntilVisibleElement(find(userLogoff));
        click(find(userLogoff));
        acceptAlert();
        sleep(5);
    }

    public void userLogin() throws Exception{
        waitUntilVisibleElement(find(loginButton));
        click(find(loginButton));
        sleep(5);
    }

    public ProjectTrackerPage goToSiteDevelopment() throws Exception {
        waitUntilVisibleElement(find(siteDevButton));
        sleep(6);
        click(find(siteDevButton));
        sleep(2);
        click(find(appCenter));
        clickingProjectTracker();
        return new ProjectTrackerPage(driver);
    }

    public RFSectorPage goToRFSectorTrackerAsSiteDev() throws Exception {
        navigateToAppCenterSiteDev();
        clickingRFSectorTracker();
        sleep(10);
        return new RFSectorPage(driver);
    }
    public void navigateToAppCenterSiteDev() throws Exception {
        List<WebElement> adminOption = findAll(siteDevelopmentMainOption,2);
        if(adminOption.size()>0){
            waitUntilVisibleElement(find(siteDevelopmentMainOption));
            find(siteDevelopmentMainOption).click();
            sleep(2);
        }
        else {
            List<WebElement> overflowMenu = findAll(mainSideOverflow,2);
            if(overflowMenu.size()>0){
                click(overflowMenu.get(0));
                click(find(overflowAdminMenu));
                fullScreen();
            }
        }
        if(!find(ringTrackerLocator).isDisplayed())
            find(AppCenter).click();
    }

    public MSMTrackerPage goToMSMTracker() throws Exception {
        sleep(10);
        clickMainLogo1();
        search("MS");
        sleep(10);
        click(find(MSCTracker));
        sleep(5);
        return new MSMTrackerPage(driver);
    }
    public void clickMainLogo1() throws Exception {
        find(mainLogo1).click();
    }
}