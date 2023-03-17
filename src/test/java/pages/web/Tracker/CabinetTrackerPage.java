package pages.web.Tracker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class CabinetTrackerPage extends BasePage {

    public WebDriver driver;

    public CabinetTrackerPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public By editButton = By.xpath("//input[@id='btnEdit0']");
    public By okButton = By.xpath("//input[@id='btnOK']");
    public By cancelButton = By.xpath("//*[@id='btnCancel']");
    public By cabinetEquipmentTrackerTab = By.xpath("//div[@title='CABE:Cabinet Equipment Tracker']");
    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By searchButton = By.xpath("//input[@id='btnSearch0']");
    public By searchTypeDropdown = By.xpath(
            "//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*"
    );
    public By selectedOption = By.xpath(
            "//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']"
    );
    public String topDivSearchTypeDropdown =
            "//div[contains(@class,'component item_select')][normalize-space()='textName']";
    public By okButton1 = By.xpath("//input[@id='btnOK0']");
    public By applyButton = By.xpath("//input[@id='btnApply']");
    public By searchInputBox = By.xpath("//input[@id='qsValue0']");
    String parentWindow = "";

    public void searchForValue(String data, String type) throws Exception {
        sleep(5);
        waitUntilVisibleElement(find(editButton));
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitUntilVisibleElement(find(editButton));
        waitForPageToLoad();
        sleep(20);
    }

    public void selectSearchType(String type) throws Exception {
        WebElement searchDropdown = find(searchTypeDropdown);
        click(searchDropdown);
        System.out.println("sss" + searchDropdown);
        if (!find(selectedOption).getText().equals(type)) {
            dropDownButtons(topDivSearchTypeDropdown, type);
        }
    }

    public AddCabinetPage selectEditOption() throws Exception {
        waitUntilVisibleElement(find(editButton));
        sleep(10);
        click(find(editButton));
        sleep(5);
        return new AddCabinetPage(driver);
    }

    public AddCabinetPage clickAddButton() throws Exception {
        waitForPageToLoad();
        buttonClick("Add", 1);
        sleep(8);
        return new AddCabinetPage(driver);
    }

    public boolean cabinetEquipmentTrackerTabVerification(String parentWindow) throws Exception {
        waitForPageToLoad();
        waitUntilVisibleElement(find(okButton));
        Boolean tab = isDisplayed(cabinetEquipmentTrackerTab);
        click(find(cancelButton));
        switchToSpecificWindow(parentWindow);
        sleep(3);
        return tab;
    }

    public void switchToTrackerPage(String parentWindow) throws Exception {
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
    }

    public String addMandatoryFields() throws Exception {
        String parent = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        WebElement cabinetVendor = selectionBoxBySname("CAB:Cabinet Vendor").get(0);
        selectDropdownOption(cabinetVendor, "Delta");
        click(find(applyButton));
        waitForPageToLoad();
        sleep(5);
        fullScreen();
        sleep(2);
        dropDownDotsClick("CAB:Cabinet Model");
        String parent1 = switchToChildWindows();
        waitUntilVisibleElement(find(okButton1));
        fullScreenChildWindow();
        waitUntilVisibleElement(find(searchInputBox));
        setText(find(searchInputBox), "ESAA600-HCU03_Indoor Rack 600Amp");
        selectSearchType("PR:CAB MOD Cabinet Model");
        click(find(searchButton));
        tableRadioButtonClickWithExactValue("PR:CAB MOD Cabinet Model", "ESAA600-HCU03_Indoor Rack 600Amp");
        click(find(okButton1));
        sleep(5);
        switchToSpecificWindow(parent1);
        WebElement installationLocation = selectionBoxBySname("CAB:Cabinet Installation Location").get(0);
        selectDropdownOption(installationLocation, "Roof");
        click(find(applyButton));
        waitForPageToLoad();
        sleep(5);
        waitUntilVisibleElement(find(okButton));
        String CabinetID = inputBoxDataBySname("CAB:Cabinet ID").getAttribute("origval");
        System.out.println("Cabinet ID is " + CabinetID);
        sleep(2);
        click(find(okButton));
        switchToSpecificWindow(parent);
        return CabinetID;
    }
}
