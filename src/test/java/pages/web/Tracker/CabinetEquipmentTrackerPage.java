package pages.web.Tracker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pages.BasePage;
import utility.helper.MiscHelpers;

import java.util.ArrayList;
import java.util.List;

public class CabinetEquipmentTrackerPage extends BasePage {

    public WebDriver driver;

    public CabinetEquipmentTrackerPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public By applyButton = By.xpath("//input[@id='btnApply']");
    public By okButton = By.xpath("//input[@id='btnOK']");
    public By cancelButton = By.xpath("//*[@id='btnCancel']");
    public By searchTypeDropdown = By.xpath("//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*");
    public By selectedOption = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public String topDivSearchTypeDropdown = "//div[contains(@class,'component item_select')][normalize-space()='textName']";
    public By searchButton = By.xpath("//input[@id='btnSearch0']");
    public By okButton1 = By.xpath("//input[@id='btnOK0']");
    public By vltManufacture = By.xpath("(//label[text()='CABE:VLT Manufacturer / Model']//following::input)[1]");
    public By batteryTab = By.xpath("//div[@title='CABE:Battery']");
    public By editSite = By.xpath("//input[@id='btnEdit0']");
    public By searchInputBox = By.xpath("//input[@id='qsValue0']");
    public By addOption = By.xpath("//input[@value='Add']");
    public By editOption = By.id("btnEdit2");
    public By cabinetID = By.xpath("//label[contains(text(),'CAB:Cabinet ID')]");
    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By equipmentIDClick = By.xpath("//div[@class='objbox customscroll']//table[@class='obj']//tbody//tr[2]//td[2]//a");
    public By generalInfoTab = By.xpath("//div[@title='CABE:General Info']");
    public By calendar = By.xpath("(//table[@id='calendar'])[2]");
    public By selectToday = By.xpath("//td[@_class='today']");
    public By selectMonth = By.xpath("(//select[@id='month'])[2]");
    public By selectYear = By.xpath("(//select[@id='year'])[2]");
    public By selectDay = By.xpath("(//td[contains(text(),'7')])[2]");
    public By voltageBoosterTab = By.xpath("//div[@title='CABE:Voltage Booster']");
    public String tableList = "//div[contains(@class,'customscroll')]//child::td[3]//a";
    public By notConnectedCheckBox = By.xpath("(//table//*[text()='CAB:Cabinet ID']/..//following::a[contains(text(),'Not Connected')]//parent::td)//ancestor::tr//label//input");
    public By warningMessage = By.xpath("//b[contains(text(),'Note:')]//br");
    public By closeButton = By.xpath("//input[@value='Close']");
    public By fieldHistory = By.xpath("//div[text()='Field History']");
    String parentWindow = "";

    public void selectAddNewCabinetOption() throws Exception {
        waitForPageToLoad();
        waitUntilVisibleElement(find(addOption));
        click(find(addOption));
    }

    public void selectEditCabinetOption() throws Exception {
        waitForPageToLoad();
        waitUntilVisibleElement(find(editOption));
        click(find(editOption));
    }

    public String switchToCabinetPage() {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        sleep(3);
        return parentWindow;
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

    public boolean validateCabinetIDFieldIsDisplayed() throws Exception {
        waitForPageToLoad();
        boolean fieldName = find(cabinetID).isDisplayed();
        return fieldName;
    }

    public void switchToTrackerPageByCancel(String parentWindow) throws Exception {
        click(find(cancelButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
    }

    public CabinetTrackerPage switchToAddTrackerPageByCancel(String parentWindow) throws Exception {
        click(find(cancelButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
        return  new CabinetTrackerPage(driver);
    }

    public CabinetTrackerPage switchToAddTrackerPageByOkay(String parentWindow) throws Exception {
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
        return  new CabinetTrackerPage(driver);
    }

    public boolean validateFieldIsDisplayed(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldNameText = fieldByLabelTextIndex(name).get(0);
        scrollToElement(fieldNameText);
        boolean fieldName = fieldNameText.isDisplayed();
        return fieldName;
    }

    public void verifyAddBattery(String cabinet,String type) throws Exception{
        waitForPageToLoad();
        dropDownDotsClick("CAB:Cabinet ID");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        waitUntilVisibleElement(find(searchInputBox));
        setText(find(searchInputBox), cabinet);
        click(find(searchButton));
        radioButtonValueSelectionByTd("CAB:Cabinet ID");
        click(find(okButton1));
        switchToSpecificWindow(parent2);
        WebElement cabinetEquipmentType = selectionBoxBySname("CABE:Cabinet Equipment Type").get(0);
        selectDropdownOption(cabinetEquipmentType, type);
        sleep(3);
    }

    public void verifyAddBattery(String type) throws Exception{
        fullScreenChildWindow();
        waitForPageToLoad();
        WebElement cabinetEquipmentType = selectionBoxBySname("CABE:Cabinet Equipment Type").get(0);
        selectDropdownOption(cabinetEquipmentType, type);
        sleep(3);
    }

    public boolean isPopAlertPresent(String parent) throws Exception {
        click(find(applyButton));
        sleep(5);
        if (!isAlertPresent()) {
            click(find(okButton));
            sleep(5);
            switchToSpecificWindow(parent);
            return false;
        } else {
            acceptAlert();
            click(find(cancelButton));
            if (!isAlertPresent()) {
                switchToSpecificWindow(parent);
            } else {
                acceptAlert();
                switchToSpecificWindow(parent);
            }
            return true;
        }
    }

    public String addBattery(String cabinet, String type) throws Exception {
        waitForPageToLoad();
        dropDownDotsClick("CAB:Cabinet ID");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        waitUntilVisibleElement(find(searchInputBox));
        setText(find(searchInputBox), cabinet);
        click(find(searchButton));
        radioButtonValueSelectionByTd("CAB:Cabinet ID");
        click(find(okButton1));
        switchToSpecificWindow(parent2);
        WebElement cabinetEquipmentType = selectionBoxBySname("CABE:Cabinet Equipment Type").get(0);
        selectDropdownOption(cabinetEquipmentType, type);
        click(find(applyButton));
        waitForPageToLoad();
        sleep(3);
        String EquipmentID = inputBoxDataBySname("CABE:Cabinet Equipment ID").getAttribute("origval");
        System.out.println("Equipment ID is " + EquipmentID);
        waitForPageToLoad();
        sleep(5);
        return EquipmentID;
    }

    public String addBattery(String type) throws Exception {
        waitForPageToLoad();
        fullScreenChildWindow();
        WebElement cabinetEquipmentType = selectionBoxBySname("CABE:Cabinet Equipment Type").get(0);
        selectDropdownOption(cabinetEquipmentType, type);
        click(find(applyButton));
        waitForPageToLoad();
        sleep(3);
        String EquipmentID = inputBoxDataBySname("CABE:Cabinet Equipment ID").getAttribute("origval");
        System.out.println("Equipment ID is " + EquipmentID);
        waitForPageToLoad();
        sleep(5);
        return EquipmentID;
    }

    public void switchToTrackerPage(String parentWindow) throws Exception {
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
    }

    public void searchForValue(String data, String type) throws Exception    {
        fullScreen();
        waitForPageToLoad();
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitForPageToLoad();
        sleep(3);
    }

    public void selectSearchType(String type) throws Exception {
        WebElement searchDropdown = find(searchTypeDropdown);
        click(searchDropdown);
        System.out.println("sss" + searchTypeDropdown);
        if (!find(selectedOption).getText().equals(type)) {
            System.out.println("not equal");
            dropDownButtons(topDivSearchTypeDropdown, type);
        } else {
            System.out.println("equal");
            dropDownButtons(topDivSearchTypeDropdown, type);
        }
    }

    public void equipmentIDClick() throws  Exception{
        waitForPageToLoad();
        sleep(5);
        waitUntilVisibleElement(find(equipmentIDClick));
        click(find(equipmentIDClick));
        sleep(2);
    }

    public boolean generalInfoVerification() throws Exception {
        waitForPageToLoad();
        waitUntilVisibleElement(find(okButton));
        Boolean tab = isDisplayed(generalInfoTab);
        return tab;
    }

    public CabinetTrackerPage selectEditOption() throws Exception {
        waitForPageToLoad();
        waitUntilVisibleElement(find(editSite));
        sleep(9);
        click(find(editSite));
        return new CabinetTrackerPage(driver);
    }

    public void goToBatteryTab() throws Exception {
        waitForPageToLoad();
        waitUntilVisibleElement(find(applyButton));
        scrollToElement(find(batteryTab));
        click(find(batteryTab));
        waitForPageToLoad();
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

    public boolean validateIsFieldDisplayed(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldNameText = fieldByLabelTextIndex(name).get(1);
        scrollToElement(fieldNameText);
        boolean fieldName = fieldNameText.isDisplayed();
        return fieldName;
    }

    public void updateBATStringValue() throws Exception {
        waitForPageToLoad();
        dropDownDotsClick("CABE:BAT String Type");
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton1));
        waitUntilVisibleElement(find(searchInputBox));
        setText(find(searchInputBox), "299");
        selectSearchType("PR:Power Reference ID");
        click(find(searchButton));
        tableRadioButtonSelectionWithExactValue("PR:Power Reference ID", "299");
        click(find(okButton1));
        sleep(5);
        switchToSpecificWindow(parent1);
        click(find(applyButton));
        waitForPageToLoad();
    }

    public boolean isValueUpdated(String sName) throws Exception {
        waitForPageToLoad();
        WebElement fieldName = inputBoxDataLabel(sName);
        String getValue = getDocumentTextByIdJs(fieldName.getAttribute("id"));
        System.out.println(getValue);
        if (getValue.equals("")) {
            return false;
        } else return true;
    }

    public void updateBATModelValue() throws Exception {
        waitForPageToLoad();
        dropDownDotsClick("CABE:BAT Model");
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        waitUntilVisibleElement(find(okButton1));
        radioButtonValueSelectionByTd("PR:Power Reference ID");
        click(find(okButton1));
        sleep(5);
        switchToSpecificWindow(parent1);
        click(find(applyButton));
        waitForPageToLoad();
    }
    public void selectNotConnectedAndNA() throws Exception{
        waitForPageToLoad();
        dropDownDotsClick("CABE:BAT Connected To");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        waitUntilVisibleElement(find(searchInputBox));
        checkBoxSelection("CAB:Cabinet ID","N/A");
        boolean check = isCheckboxSelected( find(notConnectedCheckBox).getAttribute("id"));
        if(!check){
            checkBoxSelection("CAB:Cabinet ID","Not Connected");
            sleep(2);
        }
        click(find(okButton1));
        switchToSpecificWindow(parent2);
    }
    public String updateBATConnectedTo(String siteId) throws Exception{
        waitForPageToLoad();
        dropDownDotsClick("CABE:BAT Connected To");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        waitUntilVisibleElement(find(searchInputBox));
        boolean check = isCheckboxSelected( find(notConnectedCheckBox).getAttribute("id"));
        if(check){
            checkBoxSelection("CAB:Cabinet ID","Not Connected");
            waitForPageToLoad();
            sleep(2);
        }
        checkBoxSelection("CAB:Cabinet ID",siteId+"_C1");
        waitForPageToLoad();
        sleep(2);
        click(find(okButton1));
        switchToSpecificWindow(parent2);
        click(find(applyButton));
        waitForPageToLoad();
        sleep(3);
        WebElement fieldName = inputBoxDataLabel("CABE:BAT Connected To");
        String getValue = getDocumentTextByIdJs(fieldName.getAttribute("id"));
        System.out.println(getValue);
        return getValue;
    }

    public boolean clickingDateIconOpensCalendarWidget(String sName) throws Exception{
        waitForPageToLoad();
        dropDownDotsClick(sName);
        String getValue = find(calendar).getAttribute("id");
        System.out.println(getValue);
        if (getValue.contains("calendar")){
            return true;
        }else return false;
    }
    public void updateNoOfModulesInstalled(String fieldName) throws Exception {
        waitForPageToLoad();
        setText(inputBoxDataBySname("CABE:VLT Number Of Modules Installed"), "0");
        sleep(2);
    }

    public boolean verifyDateFormat(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldName = inputBoxDataBySname(name);
        String getDate = getDocumentTextByIdJs(fieldName.getAttribute("id"));
        //boolean isValidFormat = getDate.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}");
        String dateTimeFormat = "\\d{2}/\\d{2}/\\d{4}";
        if (getDate.matches(dateTimeFormat)) {
            System.out.println("Date format is correct");
            return true;
        } else {
            System.out.println("Date format is incorrect");
            return false;
        }
    }

    public boolean selectCurrentDate(String sName) throws Exception {
        waitForPageToLoad();
        dropDownDotsClick(sName);
        click(find(selectToday));
        sleep(2);
        click(find(applyButton));
        waitForPageToLoad();
        sleep(3);
        String currentDate = MiscHelpers.currentDateTime("MM/dd/yyyy");
        String getDate = inputBoxDataBySname(sName).getAttribute("title");
        if (getDate.contains(currentDate)) {
            return true;
        } else return false;
    }

    public boolean selectDate(String sName) throws Exception {
        waitForPageToLoad();
        dropDownDotsClick(sName);
        WebElement month = find(selectMonth);
        WebElement year = find(selectYear);
        Select monthSelect = new  Select(month);
        monthSelect.selectByVisibleText("March");
        Select yearSelect = new  Select(year);
        yearSelect.selectByVisibleText("2023");
        click(find(selectDay));
        click(find(applyButton));
        waitForPageToLoad();
        sleep(3);
        String getDate = inputBoxDataBySname(sName).getAttribute("title");
        if (getDate.equals("")) {
            return false;
        } else return true;
    }

    public void goToVoltageBoosterTab() throws Exception {
        waitForPageToLoad();
        waitUntilVisibleElement(find(applyButton));
        scrollToElement(find(voltageBoosterTab));
        click(find(voltageBoosterTab));
        waitForPageToLoad();
    }
    public String  getTableValues(String sName) throws Exception{
        waitForPageToLoad();
        dropDownDotsClick("CABE:VLT Manufacturer / Model");
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        waitUntilVisibleElement(find(okButton1));
        sleep(15);
        List<String> tableData = getDocumentTextListByXpathJs(tableList);
        String tableValues = tableData.toString();
        System.out.println(tableValues);
        click(find(okButton1));
        switchToSpecificWindow(parent1);
        return tableValues;
    }
    public boolean verifyBatteryConnectedToCabinets() throws Exception{
        waitForPageToLoad();
        WebElement fieldName = inputBoxDataLabel("CABE:BAT Connected To");
        boolean getBatConnectedToValue = getDocumentTextByIdJs(fieldName.getAttribute("id")).contains("C1");
        boolean getCabinetDetailIDValue = inputBoxDataBySname("CAB:Cabinet Detail ID").getAttribute("origval").contains("C1");
        boolean getBatConnectedToCabinetsValue = inputBoxDataBySname("CABE:BAT Connected To Cabinets").getAttribute("origval").contains("C1");
        return getBatConnectedToValue && getCabinetDetailIDValue && getBatConnectedToCabinetsValue;
    }

    public void updateVLTManufacturerValue(String text) throws Exception {
        waitForPageToLoad();
        dropDownDotsClick("CABE:VLT Manufacturer / Model");
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        waitUntilVisibleElement(find(okButton1));
        waitUntilVisibleElement(find(searchInputBox));
        setText(find(searchInputBox), text);
        selectSearchType("PR:VLT Voltage Booster Model");
        click(find(searchButton));
        radioButtonValueSelectionByTd("PR:Power Reference ID");
        click(find(okButton1));
        sleep(5);
        switchToSpecificWindow(parent1);
        click(find(applyButton));
        waitForPageToLoad();
    }

    public boolean isFieldUpdatedWithModel(String text) throws Exception {
        waitForPageToLoad();
        WebElement fieldName = inputBoxDataLabel("CABE:VLT Manufacturer / Model");
        String getValue = getDocumentTextByIdJs(fieldName.getAttribute("id"));
        System.out.println(getValue);
        if (getValue.contains(text)) {
            return true;
        } else return false;
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

    public boolean validateVLTManufactureFieldIsReadOnly() throws Exception {
        waitForPageToLoad();
        WebElement fieldName = find(vltManufacture);
        scrollToElement(fieldName);
        String readOnly = fieldName.getAttribute("readonly");
        return readOnly != null;
    }

    public boolean validateFieldIsDisabled(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldName = selectionBoxBySname(name).get(0);
        scrollToElement(fieldName);
        String readOnly = fieldName.getAttribute("disabled");
        return readOnly != null;
    }

    public boolean validateFieldIsReadOnly(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldName = inputBoxDataBySname(name);
        scrollToElement(fieldName);
        String readOnly = fieldName.getAttribute("readonly");
        return readOnly != null;
    }
    public boolean validateFieldIsLocked(String sName) throws Exception{
        waitForPageToLoad();
        WebElement lock = lockByLabel(sName);
        System.out.println(lock.getAttribute("class"));
        if(lock.getAttribute("class").contains("lock-icon locked")){
            return true;
        }else {
            return false;
        }
    }
    public String getInputBoxFieldValue(String sName) throws Exception{
        waitForPageToLoad();
        WebElement fieldName = inputBoxDataBySname(sName);
        String getValue = fieldName.getAttribute("origval");
        return getValue;
    }
    public boolean verifyDetailID(String sName) throws Exception{
        waitForPageToLoad();
        String getValue = inputBoxDataBySname(sName).getAttribute("origval");
        if(getValue.startsWith("A")){
            return true;
        }else return false;
    }
    public boolean verifyWarningMessage() throws Exception{
        waitForPageToLoad();
        if (findAll(warningMessage).size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean verifyOtherCabinetDetailIDValues(String sName) throws Exception{
        waitForPageToLoad();
        WebElement fieldName = fieldByLabelTextIndex(sName).get(0);
        click(fieldName);
        click(find(fieldHistory));
        String parent = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        sleep(5);
        String tableList = tableDataList(1);
        List<String> tableValues = getDocumentTextListByXpathJs(tableList);
        int size = tableValues.size();
        boolean Flag =false;
        if(size>1){
            Flag =true;
        }
        click(find(closeButton));
        switchToSpecificWindow(parent);
        return Flag;
    }

    public void updateBusbarInstalledValue(String option, String option1) throws Exception {
        waitForPageToLoad();
        WebElement operationConfiguration = selectionBoxBySname("CABE:VLT Operating Configuration").get(0);
        scrollToElement(operationConfiguration);
        selectDropdownOption(operationConfiguration, option);
        click(find(applyButton));
        waitForPageToLoad();
        sleep(5);
        WebElement busbarInstalled = selectionBoxBySname("CABE:VLT Is Busbar Installed").get(0);
        scrollToElement(busbarInstalled);
        selectDropdownOption(busbarInstalled, option1);
    }

    public void updatingFieldWithAboveMaxValue(String fieldName, String fieldName1) throws Exception {
        waitForPageToLoad();
        String getValue = inputBoxDataBySname(fieldName).getAttribute("origval");
        System.out.println(getValue);
        int updateValue = Integer.parseInt(getValue) + 1;
        String input = String.valueOf(updateValue);
        System.out.println(input);
        setText(inputBoxDataBySname(fieldName1), input);
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
}
