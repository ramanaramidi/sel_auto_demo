package pages.web.reports;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class ReportSettingsPage extends BasePage {

    public WebDriver driver;
    public By tbGridLink = By.xpath("//a[text()='TB Grid with Submit']");

    public By okButton = By.xpath("//input[@id='btnOK']");
    public By tbGridWorksheets = By.xpath("//div[@id='tab2']/div[2]");
    public By generalInfo = By.xpath("//div[@id='tab1']/div[2]");

    public By reportDeliveryOk = By.xpath("//input[@id='btnOK0']");



    public By trackorType = By.xpath("//select[@id='trackorType_1002226']");

    public By viewName = By.xpath("//select[@id='viewName_1002226']");

    public By filterName = By.xpath("//select[@id='filterName_1002226']");

    public By emailUncheck = By.xpath("//label[@id='lblcb3']");

    public By fileCheck = By.xpath("//label[@id='lblcb1']");
    public By reportLink = By.xpath("//*[@id='group_event_3998285']/div[1]/div[1]/a");
    public By generalTab = By.xpath("//*[@id='tab1']");
    public By reportId= By.xpath("//*[@id='divPage1']/table/tbody/tr[3]/td[2]/a");
    public ReportSettingsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    public RunReportsPage addNewReport( ) throws Exception {
        sleep(10);
        String parent1 = switchToChildWindows();
        fullScreen();
        //waitUntilVisibleElement(find(generalInfo));
        fullScreenChildWindow();
        sleep(10);
        quickClick(find(tbGridWorksheets));
        selectDropdownOption(find(trackorType),"Project Tracker");
        selectDropdownOption(find(viewName),"G: Project Milestone");
        selectDropdownOption(find(filterName),"G: Small Cell");
        find(generalInfo).click();
        dropDownDotsClick("Delivery");
        String parent2 = switchToChildWindows();
        waitUntilVisibleElement(find(reportDeliveryOk));
        fullScreenChildWindow();
        if(find(emailUncheck).isSelected())
            find(emailUncheck).click();
        find(fileCheck).click();
        waitUntilVisibleElement(find(reportDeliveryOk));
        click(find(reportDeliveryOk));
        switchToSpecificWindow(parent2);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        return new RunReportsPage(driver);
    }

    public RunReportsPage addReport() throws Exception{
        sleep(10);
        String parent1 = switchToChildWindows();
        waitUntilVisibleElement(find(okButton));
        fullScreen();
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        return new RunReportsPage(driver);
    }


    /*public String clickReport() throws Exception {
        waitUntilVisibleElement(find(reportLink));
        click(find(reportLink));
        sleep(10);
        String parent = switchToChildWindows();
        waitUntilVisibleElement(find(generalTab));
        String ReportId = find(reportId).getText();
        click(find(okButton));
        switchToSpecificWindow(parent);
        return ReportId;
    }*/
}
