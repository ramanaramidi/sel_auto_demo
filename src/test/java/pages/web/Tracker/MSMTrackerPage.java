package pages.web.Tracker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import utility.helper.MiscHelpers;

import java.util.List;

public class MSMTrackerPage extends BasePage {

    public WebDriver driver;
    public By siteID = By.xpath("//*[@id='idx12_disp']");
    public By addButton = By.xpath("//*[@id='btnAdd0']");
    public By cancelButton = By.xpath("//*[@id='btnCancel']");
    public By LAC_TACTab = By.xpath("//*[@id='tabName2']");
    public By add_Button = By.xpath("//*[@id='btnAdd2']");
    public By RAC_Tab = By.xpath("//*[@id='tabLabel3']");
    public By buttonIcon = By.xpath("//*[@id='qsField2']/div[1]/div/input");
    public By searchOption3 = By.xpath("//input[@id='qsValue3']");
    public By searchButton3 = By.xpath("//input[@id='btnSearch3']");
    public By add_Button3 = By.xpath("//*[@id='btnAdd3']");
    public By searchOption2 = By.xpath("//input[@id='qsValue2']");
    public By searchButton2 = By.xpath("//input[@id='btnSearch2']");
    public By searchTypeDropdown_LACTAC = By.xpath("//div[@id='qsField2']//div[@class='component button u_button_invert btn_raised btn_white']//child::*");
    public By selectedOption_LACTAC = By.xpath("//div[@id='qsField2']//div[@class='l_items']//div[@class='component item_select selected']");
    public String topDivSearchTypeDropdown_LACTAC = "//div[contains(@class,'component item_select')][normalize-space()='textName']";
    public By LACID = By.xpath("//*[@id='gridbox2']/div[2]/table/tbody/tr[2]/td[2]");
    public By searchTypeDropdown_RAC = By.xpath("//div[@id='qsField3']//div[@class='component button u_button_invert btn_raised btn_white']//child::*");
    public By selectedOption_RAC = By.xpath("//div[@id='qsField3']//div[@class='l_items']//div[@class='component item_select selected']");
    public String topDivSearchTypeDropdown_RAC = "//div[contains(@class,'component item_select')][normalize-space()='textName']";
    public By RACID = By.xpath("//*[@id='gridbox3']/div[2]/table/tbody/tr[2]/td[2]");
    public By ApplyButton = By.xpath("//input[@id='btnApply']");
    public By okButton = By.xpath("//input[@id='btnOK']");
    public By cancel  = By.xpath("//input[@id='btnCancel']");
    String parentWindow;
    String LAC_ID;
    String RAC_ID;


    public MSMTrackerPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
    public MSMTrackerPage addNewMSMPage() throws Exception {
        waitUntilVisibleElement(find(addButton));
        click(find(addButton));
        return new MSMTrackerPage(driver);
    }
    public void createMSMRecord() throws Exception {
        sleep(5);
        dropDownValueSelection("MSM:Record Type", "1_Switch");
        dropDownValueSelection("MSM:Region", "WEST");
        dropDownValueSelection("MSM:Market", "TEST - LAB MARKET");
        inputBoxDataBySname("MSM:Switch").sendKeys(MiscHelpers.getRandomString(8,true));
        dropDownValueSelection("MSM:Selectable", "Yes");
        sleep(3);
        click(find(ApplyButton));
        sleep(10);
        //  click(find(okButton));
        //  sleep(5);
    }

    public void createMSM_MSC_MGW_MME_AMFRecord() throws Exception {
        sleep(5);
        dropDownValueSelection("MSM:Record Type", "2_MME 4G");
        dropDownValueSelection("MSM:Region", "CORPORATE");
        dropDownValueSelection("MSM:Market", "TEST - LAB MARKET");
        inputBoxDataBySname("MSM:Switch").sendKeys(MiscHelpers.getRandomString(10,true));
        inputBoxDataBySname("MSM:MSC / MGW / MME / AMF").sendKeys(MiscHelpers.getRandomString(10,true));
        dropDownValueSelection("MSM:Selectable", "Yes");
        sleep(3);
        click(find(ApplyButton));
        sleep(10);
        // click(find(okButton));
        //sleep(5);
    }

    public Boolean getMSMIdConcatMSMMSCMMEMGWAMF() throws Exception {
        sleep(5);
        String Market_Switch = inputBoxDataBySname("MSM:Switch").getAttribute("value");
        System.out.println("MSM:Switch is: " + Market_Switch);
        String MSM_MSC_MGW_MME_AMF = inputBoxDataBySname("MSM:MSC / MGW / MME / AMF").getAttribute("value");
        System.out.println("MSM_MSC_MGW_MME_AMF is: " + MSM_MSC_MGW_MME_AMF);
        List<WebElement> Market = selectionBoxBySname("MSM:Market");
        String market = getFirstSelectedOptionInDropdown(Market.get(0));
        System.out.println("Market is "+market);
        String MSM_ID = inputBoxDataBySname("MSM:MSM ID").getAttribute("value");
        System.out.println("MSM_ID is: " + MSM_ID);
        String MSMid = market + "_" + Market_Switch + "_" + MSM_MSC_MGW_MME_AMF;
        System.out.println("MSMid is "+MSMid);
      //  click(find(okButton));
        sleep(5);
        return MSM_ID.equals(MSMid);
    }

    public Boolean getMSMIdConcatMarket_Switch() throws Exception {
        sleep(10);
        String Market_Switch = inputBoxDataBySname("MSM:Switch").getAttribute("value");
        System.out.println("MSM:Switch is: " + Market_Switch);
        List<WebElement> Market = selectionBoxBySname("MSM:Market");
        String market = getFirstSelectedOptionInDropdown(Market.get(0));
        System.out.println("Market is "+ market);
        String MSMid =  market + "_" + Market_Switch;
        System.out.println("MSMid is "+MSMid);
        String MSM_ID = inputBoxDataBySname("MSM:MSM ID").getAttribute("value");
        System.out.println("MSM_ID is: " + MSM_ID);
       // click(find(okButton));
        sleep(5);
        System.out.println("value is " +MSM_ID.equals(MSMid));
        return MSM_ID.equals(MSMid);
    }

    public void createBSC_RNC_Record() throws Exception {
        sleep(5);
        dropDownValueSelection("MSM:Record Type", "3_BSC/RNC");
        dropDownValueSelection("MSM:Region", "CORPORATE");
        dropDownValueSelection("MSM:Market", "TEST - LAB MARKET");
        inputBoxDataBySname("MSM:Switch").sendKeys(MiscHelpers.getRandomString(10,true));
        inputBoxDataBySname("MSM:MSC / MGW / MME / AMF").sendKeys(MiscHelpers.getRandomString(10,true));
        inputBoxDataBySname("MSM:BSC/RNC").sendKeys(MiscHelpers.getRandomString(10,true));
        dropDownValueSelection("MSM:Selectable", "Yes");
        sleep(3);
        click(find(ApplyButton));
        sleep(10);
    }
    public Boolean getMSMIdConcatBSC_RNC() throws Exception {
        sleep(5);
        String Market_Switch = inputBoxDataBySname("MSM:Switch").getAttribute("value");
        System.out.println("MSM:Switch is: " + Market_Switch);
        String MSM_MSC_MGW_MME_AMF = inputBoxDataBySname("MSM:MSC / MGW / MME / AMF").getAttribute("value");
        System.out.println("MSM_MSC_MGW_MME_AMF is: " + MSM_MSC_MGW_MME_AMF);
        List<WebElement> Market = selectionBoxBySname("MSM:Market");
        String market = getFirstSelectedOptionInDropdown(Market.get(0));
        System.out.println("Market is "+market);
        String BSC_RNC = inputBoxDataBySname("MSM:BSC/RNC").getAttribute("value");
        System.out.println("BSC_RNC is: " + BSC_RNC);
        String MSM_ID = inputBoxDataBySname("MSM:MSM ID").getAttribute("value");
        System.out.println("MSM_ID is: " + MSM_ID);
        String MSMid = market + "_" + Market_Switch + "_" + MSM_MSC_MGW_MME_AMF + "_" + BSC_RNC;
        System.out.println("MSMid is "+MSMid);
      //  click(find(okButton));
        sleep(5);
        return MSM_ID.equals(MSMid);
    }

    public String createLAC_TAC_Record() throws Exception {
        sleep(5);
        dropDownValueSelection("MSM:Record Type", "2_MME 4G");
        dropDownValueSelection("MSM:Region", "CORPORATE");
        dropDownValueSelection("MSM:Market", "TEST - LAB MARKET");
        inputBoxDataBySname("MSM:Switch").sendKeys(MiscHelpers.getRandomString(10,true));
        inputBoxDataBySname("MSM:MSC / MGW / MME / AMF").sendKeys(MiscHelpers.getRandomString(10,true));
        dropDownValueSelection("MSM:Selectable", "Yes");
        sleep(3);
        click(find(ApplyButton));
        sleep(3);
        waitUntilVisibleElement(find(ApplyButton));
        click(find(LAC_TACTab));
        sleep(4);
        click(find(add_Button));
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        inputBoxDataBySname("LAC:LAC/TAC").sendKeys(MiscHelpers.getRandomNumber(8));
        dropDownValueSelection("LAC:SELECTABLE","Yes");
        click(find(ApplyButton));
        sleep(6);
        LAC_ID = inputBoxDataBySname("LAC:LAC ID").getAttribute("value");
        System.out.println("LAC_ID is "+LAC_ID);
        sleep(3);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        return LAC_ID;
    }
    public String createRACRecord() throws Exception {
        sleep(5);
        dropDownValueSelection("MSM:Record Type", "2_MME 4G");
        dropDownValueSelection("MSM:Region", "CORPORATE");
        dropDownValueSelection("MSM:Market", "TEST - LAB MARKET");
        inputBoxDataBySname("MSM:Switch").sendKeys(MiscHelpers.getRandomString(10,true));
        inputBoxDataBySname("MSM:MSC / MGW / MME / AMF").sendKeys(MiscHelpers.getRandomString(10,true));
        dropDownValueSelection("MSM:Selectable", "Yes");
        sleep(3);
        click(find(ApplyButton));
        sleep(3);
        waitUntilVisibleElement(find(ApplyButton));
        click(find(LAC_TACTab));
        sleep(4);
        click(find(add_Button));
        String parent = switchToChildWindows();
        fullScreenChildWindow();
        inputBoxDataBySname("LAC:LAC/TAC").sendKeys(MiscHelpers.getRandomNumber(8));
        dropDownValueSelection("LAC:SELECTABLE","Yes");
        click(find(ApplyButton));
        sleep(5);
        click(find(okButton));
        switchToSpecificWindow(parent);
        sleep(4);
        waitUntilVisibleElement(find(ApplyButton));
        click(find(RAC_Tab));
        sleep(3);
        click(find(add_Button3));
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        inputBoxDataBySname("RAC:RAC").sendKeys(MiscHelpers.getRandomNumber(8));
        dropDownValueSelection("RAC:SELECTABLE","Yes");
        click(find(ApplyButton));
        sleep(4);
        RAC_ID =  inputBoxDataBySname("RAC:RAC ID").getAttribute("value");
        System.out.println("RAC ID is: "+RAC_ID);
        sleep(6);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        return RAC_ID;
    }
    public boolean getLAC_TACRecord(String LAC_ID,String type) throws Exception {
        sleep(5);
        fullScreen();
        System.out.println(LAC_ID);
        sleep(4);
        waitUntilVisibleElement(find(searchOption2));
        clearInputBoxByElementAndSendKeys(find(searchOption2));
        setText(find(searchOption2), LAC_ID);
        selectSearchType_LACTAC(type);
        click(find(searchButton2));
        // waitUntilVisibleElement(find(editRFSector));
        System.out.println("search button clicked");
        sleep(5);
        String lac_id = find(LACID).getText();
        return !(lac_id).isEmpty();
    }

    public void selectSearchType_LACTAC(String type) throws Exception {
        sleep(6);
        WebElement searchDropdown = find(searchTypeDropdown_LACTAC);
        click(searchDropdown);
        System.out.println("sss" + searchDropdown);
        if (!find(selectedOption_LACTAC).getText().equals(type)) {
            System.out.println("not equal");
            dropDownButtons(topDivSearchTypeDropdown_LACTAC, type);
        } else {
            System.out.println("equal");
            dropDownButtons(topDivSearchTypeDropdown_LACTAC, type);
        }
    }

    public boolean getRACRecord(String RAC_ID,String type) throws Exception {
        sleep(5);
        System.out.println(RAC_ID);
        sleep(4);
        waitUntilVisibleElement(find(searchOption3));
        clearInputBoxByElementAndSendKeys(find(searchOption3));
        setText(find(searchOption3), RAC_ID);
        selectSearchType_RAC(type);
        click(find(searchButton3));
        System.out.println("search button clicked");
        sleep(5);
        String rac_id = find(RACID).getText();
        return !(rac_id).isEmpty();
    }
    public void selectSearchType_RAC(String type) throws Exception {
        sleep(6);
        WebElement searchDropdown = find(searchTypeDropdown_RAC);
        click(searchDropdown);
        System.out.println("sss" + searchDropdown);
        if (!find(selectedOption_RAC).getText().equals(type)) {
            System.out.println("not equal");
            dropDownButtons(topDivSearchTypeDropdown_RAC, type);
        } else {
            System.out.println("equal");
            dropDownButtons(topDivSearchTypeDropdown_RAC, type);
        }
    }
    public String switchToProjectPage(){
        parentWindow = switchToChildWindows();
        fullScreen();
        sleep(3);
        return parentWindow;
    }
    public void switchToTracker(String parentWindow) throws Exception {
        // waitUntilVisibleElement(find(okButton));
        sleep(8);
        click(find(okButton));
        sleep(5);
        switchToSpecificWindow(parentWindow);
    }
    public boolean switchToTrackerOnException(String parentWindow) throws Exception {
        click(find(okButton));
        sleep(5);
        if(isAlertPresent()){
            acceptAlert();
            // click(find(okButton));
            sleep(2);
            switchToSpecificWindow(parentWindow);
            return true;
        }
        else {
            // acceptAlert();
            click(find(cancel));
            if (isAlertPresent()){
                acceptAlert();
            }
            switchToSpecificWindow(parentWindow);
            return false;
        }
    }
}
