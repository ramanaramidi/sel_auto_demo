package pages.web.Tracker.site;

import commons.objects.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class AcsPage extends BasePage {

    public WebDriver driver;
    public AcsPage (WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public By applyButton = By.xpath("//input[@id='btnApply']");
    public By okButton = By.xpath("//input[@id='btnOK']");
    public By okButtonforchildwindow = By.xpath("//input[@id='btnOK0']");
    public By dotsclick = By.xpath("//input[@id='idx110_but']");
    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By searchButton = By.xpath("//input[@id='btnSearch0']");
    public By searchTypeDropdown = By.xpath("(//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*)[1]");
    public By selectedOption = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public By editSite = By.xpath("//input[@id='btnEdit0']");
    public String topDivSearchTypeDropdown = "//div[@class='component item_select'][normalize-space()='textName']";
    public By hubroutersiteid = By.xpath("//label[text()='S:Hub/Router Site ID']");
    public By activering = By.xpath("//td[text()='Active']");
    String parentWindow;


 public void selectSiteCategoryAggregateRouter() throws Exception{
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        WebElement sitecategory =selectionBoxBySname("S:Site Category").get(0);
        scrollToElement(sitecategory);
        selectDropdownOption(sitecategory,"Aggregate Router");
        click(find(applyButton));
        sleep(3);
 }

 public SiteTrackerPage goToSiteTracker() throws Exception {
     click(find(okButton));
     sleep(5);
     switchToSpecificWindow(parentWindow);
     return new SiteTrackerPage(driver);
 }

    public void selectSiteCategoryHub() throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        WebElement sitecategory = selectionBoxBySname("S:Site Category").get(0);
        scrollToElement(sitecategory);
        selectDropdownOption(sitecategory, "Hub");
        click(find(applyButton));
        sleep(5);
       // click(find(okButton));
       // sleep(10);
        dropDownDotsClick("S:Hub/Router Site ID");
        //click(find(dotsclick));
       // switchToSpecificWindow(parent1);


    }
    public void selectSiteCategoryHubForHubClusterId() throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        WebElement sitecategory = selectionBoxBySname("S:Site Category").get(0);
        scrollToElement(sitecategory);
        selectDropdownOption(sitecategory, "Hub");
        click(find(applyButton));
    }
    public void selectHubRouterSiteId() throws Exception {
        String parent1 = switchToChildWindows();
        WebElement hubrouter = find(hubroutersiteid);
        scrollToElement(hubrouter);
        dropDownDotsClick("S:Hub/Router Site ID");
       // click(find(dotsclick));
       // click( lockByLabelText("S:Hub/Router Site ID").get(0));

    }
    public void selectSiteCategoryBbu() throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        WebElement sitecategory = selectionBoxBySname("S:Site Category").get(0);
        scrollToElement(sitecategory);
        selectDropdownOption(sitecategory, "BBU/BTS");
        click(find(applyButton));
        sleep(5);
        dropDownDotsClick("S:Hub/Router Site ID");
       // click(find(dotsclick));
    }

    public void getSiteCode(String data) throws Exception {
        String parent2 = switchToChildWindows();
        fullScreen();
        sleep(5);
        waitUntilVisibleElement(find(searchOption));
        setText(find(searchOption), data);
        click(find(searchButton));
        waitUntilVisibleElement(find(searchOption));
        fullScreen();
        sleep(5);
        click(find(okButtonforchildwindow));
        sleep(3);
        switchToSpecificWindow(parent2);
        acsSiteFields();
    }

    public void selectInstallationProject() throws Exception{
     //sleep(5);
        dropDownDotsClick("S:BBU/BTS Installation Project");
        String parent2 = switchToChildWindows();
        fullScreen();
       // sleep(5);
        waitUntilVisibleElement(find(searchOption));
        click(find(searchOption));
        setText(find(searchOption),"00TESTOA-0002037960");
        click(find(searchButton));
        radioButtonClick("PJ:Project ID", "00TESTOA-0002037960");
        click(find(okButtonforchildwindow));
        sleep(5);
        switchToSpecificWindow(parent2);
        waitUntilVisibleElement(find(applyButton));
        click(find(applyButton));
        sleep(5);
    }
     public void addVenueCategoryAndNameToTheRing() throws Exception{
     parentWindow = switchToChildWindows();
     waitUntilVisibleElement(find(applyButton));
     fullScreenChildWindow();
     inputBoxDataBySname("R:Ring or Venue Name","DAS Venue");
     WebElement venuecategory =selectionBoxBySname("R:Venue Category").get(0);
     selectDropdownOption(venuecategory,"Arena - NBA");
     click(find(applyButton));
     sleep(5);
   //  click(find(okButton));
     //sleep(5);
     //switchToSpecificWindow(parentWindow);
      }

      public void acsSiteFields() throws Exception{

      WebElement acsumts =  selectionBoxBySname("S:ACS UMTS").get(0);
      scrollToElement(acsumts);
      selectDropdownOption(acsumts,"Site passes UMTS coverage criteria for Small Cells");
      WebElement acsmaintenance =  selectionBoxBySname("S:ACS Maintenance").get(0);
      selectDropdownOption(acsmaintenance,"Self Perform");
      inputBoxDataBySname("S:BBU/BTS Head End Address","1234 West Street");
      WebElement acsowner =  selectionBoxBySname("S:ACS Owner").get(0);
      scrollToElement(acsowner);
      selectDropdownOption(acsowner,"ATC");
      inputBoxDataBySname("S:ACS Management Company","company A");
      WebElement systembuildyear =  selectionBoxBySname("S:System Build Year").get(0);
      selectDropdownOption(systembuildyear,"2023");
      waitUntilVisibleElement(find(applyButton));
      click(find(applyButton));
      sleep(5);
      }

    public boolean hubClusterIdValidation() throws Exception{
     sleep(5);
     String value=inputBoxDataBySname("S:Hub Cluster ID").getAttribute("origval");
     if(!value.isEmpty()){
         return true;
     }
     return false;

    }
}
