package pages.web.Tracker;

import commons.objects.Users;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import pages.web.Tracker.site.SiteTrackerPage;
import utility.helper.MiscHelpers;

import java.util.ArrayList;
import java.util.List;

public class AddCabinetPage extends BasePage {

    public WebDriver driver;

    public AddCabinetPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public By cabinetVendor = By.xpath("//select[@sname='CAB:Cabinet Vendor']//option");
    public By cabinetModel = By.xpath("(//label[text()='CAB:Cabinet Model']/../following-sibling::td/div/div/input)[2]");
    public By cabinet_Model = By.xpath("(//label[text()='CAB:Cabinet Model']/../following-sibling::td/div/div/input)[1]");
    public By cabinetInstallation = By.xpath("//select[@sname='CAB:Cabinet Installation Location']//option");
    public By applyButton = By.xpath("//input[@id='btnApply']");
    public By okButton = By.xpath("//input[@id='btnOK']");
    public By cancelButton = By.xpath("//input[@id='btnCancel']");
    public By okButton1 = By.xpath("//input[@id='btnOK0']");
    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By searchButton = By.xpath("//input[@id='btnSearch0']");
    public By getCount = By.xpath("//span[@id='tabCounter2']");
    public By addOption = By.xpath("//input[@value='Add']");
    public By searchTypeDropdown = By.xpath(
            "//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*"
    );
    public By selectedOption = By.xpath(
            "//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']"
    );
    public String topDivSearchTypeDropdown =
            "//div[@class='component item_select'][normalize-space()='textName']";
    public By cabinetTypePurpose = By.xpath("(//label[text()='CAB:Cabinet Type/Purpose']/../following-sibling::td/div/div/input)[2]");
    public By cabinetType_Purpose = By.xpath("(//label[text()='CAB:Cabinet Type/Purpose']/../following-sibling::td/div/div/input)[1]");
    public By RadioCabinetType = By.xpath("//select[@sname='CAB:Radio Cabinet Type']//option");
    public By CabinetVoltage = By.xpath("//select[@sname='CAB:Voltage']//option");
    public By cabinetStatus = By.xpath("//select[@sname='CAB:Cabinet Status']//option");
    public By cabinetEquipmentTrackerTab = By.xpath("//div[@title='CABE:Cabinet Equipment Tracker']");
    public String parentWindow = "";

    public String getValueInField(String sname) throws Exception {
        return getDocumentTextByXpathJs(inputBoxXpathBySname(sname));
    }



    public String switchToAddCabinetPage() {
        parentWindow = switchToChildWindows();
        fullScreen();
        sleep(3);
        return parentWindow;
    }

    public CabinetTrackerPage closeAddCabinet(String parentWindow) throws Exception {
        click(find(cancelButton));
        sleep(10);
        switchToSpecificWindow(parentWindow);
        return new CabinetTrackerPage(driver);
    }

    public boolean validateFieldIsDisplayed(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldNameText = fieldByLabelText(name);
        scrollToElement(fieldNameText);
        boolean fieldName = fieldNameText.isDisplayed();
        return fieldName;
    }

    public boolean validateField_IsDisplayed(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldNameText = fieldByLabelContainsText(name);
        scrollToElement(fieldNameText);
        boolean fieldName = fieldNameText.isDisplayed();
        return fieldName;
    }

    public Boolean verifyDropDownField_CabinetStatus(String status) throws Exception {
        waitForPageToLoad();
        scrollToElement(selectionBoxBySname("CAB:Cabinet Status").get(0));
        List<WebElement> dropDownField = findAll(cabinetStatus);
        System.out.println("DropDown Options are - " + dropDownField);
        for (WebElement e : dropDownField
        ) {
            String option = e.getText();
            System.out.println("Option is -" + option);
            if (e.getText().equals(status)) {
                e.click();
            }
        }
        click(find(applyButton));
        sleep(6);
        boolean Flag = false;
        if (dropDownField.size() > 0) {
            System.out.println(dropDownField.size());
            Flag = true;
        }
        return Flag;
    }

    public String verifyDotsDropDownField_CabinetTypePurpose(String data) throws Exception {
        waitForPageToLoad();
        scrollToElement(inputBoxDataBySname("CAB:Cabinet Type/Purpose"));
        sleep(6);
        find(cabinetTypePurpose).click();
        sleep(8);
        String parent = switchToChildWindows();
        fullScreenChildWindow();
        sleep(8);
        searchForValue(data, "PR:Power Reference ID");
        sleep(4);
        click(find(okButton1));
        switchToSpecificWindow(parent);
        String selectedOption = find(cabinetType_Purpose).getAttribute("title");
        System.out.println("Selected Option is " + selectedOption);
        click(find(applyButton));
        sleep(5);
        return selectedOption;
    }

    public boolean verifyDropDownField_RadioCabinetType(String value) throws Exception {
        waitForPageToLoad();
        scrollToElement(selectionBoxBySname("CAB:Radio Cabinet Type").get(0));
        List<WebElement> dropDownField = findAll(RadioCabinetType);
        System.out.println("DropDown Options are - " + dropDownField);
        for (WebElement e : dropDownField
        ) {
            String option = e.getText();
            System.out.println("Option is -" + option);
            if (e.getText().equals(value)) {
                e.click();
            }
        }
        click(find(applyButton));
        sleep(6);
        boolean Flag = false;
        if (dropDownField.size() > 0) {
            System.out.println(dropDownField.size());
            Flag = true;
        }
        return Flag;
    }

    public boolean verifyDropDownField_CabinetVoltage(String value) throws Exception {
        waitForPageToLoad();
        scrollToElement(selectionBoxBySname("CAB:Voltage").get(0));
        List<WebElement> dropDownField = findAll(CabinetVoltage);
        System.out.println("DropDown Options are - " + dropDownField);
        for (WebElement e : dropDownField
        ) {
            String option = e.getText();
            System.out.println("Option is -" + option);
            if (e.getText().equals(value)) {
                e.click();
            }
        }
        click(find(applyButton));
        sleep(6);
        boolean Flag = false;
        if (dropDownField.size() > 0) {
            System.out.println(dropDownField.size());
            Flag = true;
        }
        return Flag;
    }

    public boolean isPopALert(String parent) throws Exception {
        click(find(applyButton));
        sleep(10);
        if (!isAlertPresent()) {
            click(find(okButton));
            sleep(5);
            switchToSpecificWindow(parent);
            return false;
        } else {
            acceptAlert();
            click(find(cancelButton));
            switchToSpecificWindow(parent);
         /*   if (!isAlertPresent()) {
                switchToSpecificWindow(parent);
            } else {
                acceptAlert();
                switchToSpecificWindow(parent);
            }*/
            return true;
        }
    }

    public String validateNumberOfRectifiersSupported_Updated(String name) throws Exception {
        waitForPageToLoad();
        WebElement numberOfRectifiers = inputBoxDataBySname("CAB:Number of Rectifiers Supported (max) ");
        setText(numberOfRectifiers, "20");
        sleep(4);
        click(find(applyButton));
        sleep(3);
        String fieldName = inputBoxDataBySname("CAB:Number of Rectifiers Supported (max) ").getAttribute("origval");
        System.out.println("Number of Rectifiers supported (max) is " + fieldName);
        return fieldName;
    }

    public String validateMaxRectifiersCount_Updated(String name) throws Exception {
        waitForPageToLoad();
        WebElement numberOfRectifiers = inputBoxDataBySname("CAB:Number of Rectifiers Supported (max) ");
        String fieldName = numberOfRectifiers.getAttribute("origval");
        System.out.println("Max Rectifiers is " + fieldName);
        sleep(4);
        return fieldName;
    }

    public Boolean verifyDropDownField_CabinetVendor(String value) throws Exception {
        waitForPageToLoad();
        WebElement numberOfRectifiers = inputBoxDataBySname("CAB:Number of Rectifiers Supported (max) ");
        setText(numberOfRectifiers, "20");
        sleep(4);
        selectionBoxBySname("CAB:Cabinet Vendor").get(0);
        List<WebElement> dropDownField = findAll(cabinetVendor);
        System.out.println("DropDown Options are - " + dropDownField);
        for (WebElement e : dropDownField
        ) {
            String option = e.getText();
            System.out.println("Option is -" + option);
            if (e.getText().equals(value)) {
                e.click();
            }
        }
        click(find(applyButton));
        sleep(6);
        boolean Flag = false;
        if (dropDownField.size() > 0) {
            System.out.println(dropDownField.size());
            Flag = true;
        }
        return Flag;
    }

    public String verifyDotsDropDownField_CabinetModel(String data) throws Exception {
        waitForPageToLoad();
        sleep(6);
        find(cabinetModel).click();
        sleep(8);
        String parent = switchToChildWindows();
        fullScreenChildWindow();
        sleep(8);
        searchForValue(data, "PR:Power Reference ID");
        sleep(4);
        click(find(okButton1));
        switchToSpecificWindow(parent);
        String selectedOption = find(cabinet_Model).getAttribute("title");
        System.out.println("Selected Option is " + selectedOption);
        click(find(applyButton));
        sleep(5);
        return selectedOption;
    }

    public boolean verifyDropDownField_CabinetInstallationLocation() throws Exception {
        waitForPageToLoad();
        scrollToElement(selectionBoxBySname("CAB:Cabinet Installation Location").get(0));
        List<WebElement> dropDownField = findAll(cabinetInstallation);
        System.out.println("DropDown Options are - " + dropDownField);
        for (WebElement e : dropDownField
        ) {
            String option = e.getText();
            System.out.println("Option is -" + option);
            if (e.getText().equals("Roof")) {
                e.click();
            }
        }
        click(find(applyButton));
        sleep(6);
        boolean Flag = false;
        if (dropDownField.size() > 0) {
            System.out.println(dropDownField.size());
            Flag = true;
        }
        return Flag;
    }

    public void searchForValue(String data, String type) throws Exception {
        fullScreen();
        waitForPageToLoad();
        setText(find(searchOption), data);
        sleep(4);
        selectSearchType(type);
        click(find(searchButton));
        waitForPageToLoad();
    }

    public void selectSearchType(String type) throws Exception {
        WebElement searchDropdown = find(searchTypeDropdown);
        sleep(5);
        click(searchDropdown);
        System.out.println("sss" + searchDropdown);
        if (!find(selectedOption).getText().equals(type)) {
            dropDownButtons(topDivSearchTypeDropdown, type);
        }
    }

    public boolean validateFieldAllowsNumeric(String fieldName) throws Exception {
        waitForPageToLoad();
        setText(inputBoxDataBySname(fieldName), "1");
        click(find(applyButton));
        waitForPageToLoad();
        sleep(5);
        String getValue = inputBoxDataBySname(fieldName).getAttribute("origval");
        int size = getValue.length();
        if (size != 0) {
            System.out.println("Field only allows Numeric Values");
            return true;
        } else return false;
    }

    public boolean verifyAddBatteryNumberOfBatterySupported() throws Exception {
        waitForPageToLoad();
        String batterySupported = inputBoxDataBySname("CAB:Number of Battery Strings Supported").getAttribute("origval");
        int batterySupportedValue = Integer.parseInt(batterySupported);
        System.out.println(batterySupportedValue);
        String getBatteryCount = find(getCount).getText();
        int getBatteryCountValue = Integer.parseInt(getBatteryCount);
        System.out.println(getBatteryCountValue);
        if (batterySupportedValue == getBatteryCountValue && getBatteryCountValue == 1) {
            goToCabinetEquipmentTrackerTab();
            click(find(addOption));
            String parent1 = switchToChildWindows();
            fullScreenChildWindow();
            WebElement cabinetEquipmentType = selectionBoxBySname("CABE:Cabinet Equipment Type").get(0);
            selectDropdownOption(cabinetEquipmentType, "Battery");
            click(find(applyButton));
            sleep(2);
            if (isAlertPresent()) {
                System.out.println("Cannot be Added Extra Battery");
                acceptAlert();
                sleep(2);
                click(find(cancelButton));
                acceptAlert();
                switchToSpecificWindow(parent1);
                return true;
            }return true;
        }else {
            System.out.println("Can be Added Extra Battery");
            return false;
        }
    }

    public void goToCabinetEquipmentTrackerTab() throws Exception {
        waitForPageToLoad();
        waitUntilVisibleElement(find(applyButton));
        scrollToElement(find(cabinetEquipmentTrackerTab));
        click(find(cabinetEquipmentTrackerTab));
        waitForPageToLoad();
    }



    public void switchToTrackerPage(String parentWindow) throws Exception {
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
    }

    public CabinetTrackerPage switchToCabinetTrackerPage(String parentWindow) throws Exception {
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
        return new CabinetTrackerPage(driver);
    }

    public SiteTrackerPage switchToAddTrackerPage(String parentWindow) throws Exception {
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
        return new SiteTrackerPage(driver);
    }

    public boolean verifyVoltageBoosters() throws Exception{
        waitForPageToLoad();
        WebElement voltageBoosters = inputBoxDataBySname("CAB:Number Of Voltage Boosters Installed");
        String getValue = voltageBoosters.getAttribute("origval");
        if (getValue.contains("0")){
            return true;
        }else return false;
    }

    public boolean validateFieldIsReadOnly(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldName = inputBoxDataBySname(name);
        scrollToElement(fieldName);
        String readOnly = fieldName.getAttribute("readonly");
        return readOnly != null;
    }
    public boolean verifyDateTimeFormat(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldName = inputBoxDataBySname(name);
        String getDate = getDocumentTextByIdJs(fieldName.getAttribute("id"));
        //boolean isValidFormat = getDate.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}");
        String dateTimeFormat = "\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}";
        if (getDate.matches(dateTimeFormat)) {
            System.out.println("Date and Time format is correct");
            return true;
        } else {
            System.out.println("Date and Time format is incorrect");
            return false;
        }
    }

    public boolean getCreatedByAndCreatedDate(Users userDetails) throws Exception {
        waitForPageToLoad();
        String owner = userDetails.getNtCode();
        WebElement CABCreatedBy = inputBoxDataBySname(
                "CAB:Created By"
        );
        scrollToElement(CABCreatedBy);
        String getCreatedBy = getDocumentTextByIdJs(CABCreatedBy.getAttribute("id"));
        System.out.println("Name::" + getCreatedBy);
        boolean createdBy = getCreatedBy.contains(owner);
        System.out.println("boolean:: " + createdBy);
        String currentDate = MiscHelpers.currentDateTime("MM/dd/yyyy");
        WebElement CABCreatedDate = inputBoxDataBySname("CAB:Created Date");
        scrollToElement(CABCreatedDate);
        String getDate = getDocumentTextByIdJs(CABCreatedDate.getAttribute("id"));
        System.out.println("Date::" + getDate);
        boolean createdDate = getDate.contains(currentDate);
        System.out.println("boolean:: " + createdDate);
        return createdBy & createdDate;
    }

    public boolean getModifiedByAndModifiedDate(Users userDetails) throws Exception {
        waitForPageToLoad();
        WebElement coolingSystem = selectionBoxBySname("CAB:Cooling System").get(0);
        selectDropdownOption(coolingSystem, "DC Fans");
        click(find(applyButton));
        waitForPageToLoad();
        sleep(5);
        String owner = userDetails.getNtCode();
        WebElement CABModifiedBy = inputBoxDataBySname(
                "CAB:Modified By"
        );
        scrollToElement(CABModifiedBy);
        String getModifiedBy = getDocumentTextByIdJs(CABModifiedBy.getAttribute("id"));
        System.out.println("Name::" + getModifiedBy);
        boolean modifiedBy = getModifiedBy.contains(owner);
        System.out.println("boolean:: " + modifiedBy);
        String currentDate = MiscHelpers.currentDateTime("MM/dd/yyyy");
        WebElement CABModifiedDate = inputBoxDataBySname("CAB:Modified Date");
        scrollToElement(CABModifiedDate);
        String getDate = getDocumentTextByIdJs(CABModifiedDate.getAttribute("id"));
        System.out.println("Date::" + getDate);
        boolean modifiedDate = getDate.contains(currentDate);
        System.out.println("boolean:: " + modifiedDate);
        return modifiedBy & modifiedDate;
    }

    public List<String> fieldDropDownValues(String sName) throws Exception {
        waitForPageToLoad();
        List<String> options = new ArrayList<String>();
        WebElement selectBox = selectionBoxBySname(sName).get(0);
        scrollToElement(selectBox);
        List<WebElement> dropDownField = selectGetDropdownOptions(selectBox);
        if (dropDownField.size() > 0) {
            for (int i = 0; i < dropDownField.size(); i++) {
                options.add(getText(dropDownField.get(i)));
            }
            return options;
        }
        return null;
    }

    public String updateDropdownFieldValue(String sName, String option) throws Exception {
        waitForPageToLoad();
        WebElement fieldName = selectionBoxBySname(sName).get(0);
        scrollToElement(fieldName);
        selectDropdownOption(fieldName, option);
        click(find(applyButton));
        waitForPageToLoad();
        sleep(5);
        String getValue = getFirstSelectedOptionInDropdown(selectionBoxBySname(sName).get(0));
        System.out.println(getValue);
        return getValue;
    }

    public boolean validateCabinetFieldAllowsText(String fieldName) throws Exception {
        waitForPageToLoad();
        setText(inputBoxDataBySname(fieldName), "Test");
        click(find(applyButton));
        waitForPageToLoad();
        sleep(5);
        String getFieldValue = inputBoxDataBySname(fieldName).getAttribute("origval");
        int size = getFieldValue.length();
        if (size == 0) {
            System.out.println("Field doesn't allow Text Characters");
            return false;
        } else return true;
    }

    public boolean updateFieldValue(String fieldName, String fieldName1) throws Exception {
        waitForPageToLoad();
        String getValue = inputBoxDataBySname(fieldName).getAttribute("origval");
        System.out.println(getValue);
        int updateValue = Integer.parseInt(getValue);
        String input = String.valueOf(updateValue);
        System.out.println(input);
        setText(inputBoxDataBySname(fieldName1), input);
        click(find(applyButton));
        waitForPageToLoad();
        sleep(5);
        String updatedValue = inputBoxDataBySname(fieldName1).getAttribute("origval");
        return updatedValue.contains(input);
    }

    public boolean updateACLoadBreakerSlotsFieldValue(String fieldName) throws Exception {
        waitForPageToLoad();
        String input = MiscHelpers.getRandomNumber(1);
        setText(inputBoxDataBySname(fieldName), input);
        click(find(applyButton));
        waitForPageToLoad();
        sleep(5);
        String updatedValue = inputBoxDataBySname(fieldName).getAttribute("origval");
        return updatedValue.contains(input);
    }
}
