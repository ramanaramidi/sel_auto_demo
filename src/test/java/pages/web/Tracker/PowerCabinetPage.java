package pages.web.Tracker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PowerCabinetPage extends BasePage {
    public By SiteTracker = By.xpath("//div[text()='Site Tracker']");
    public By mainLogo1 = By.xpath("//*[@id='mainLogo']");
    public By okButton1 = By.xpath("//input[@id='btnOK0']");
    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By searchOption_CB = By.xpath("//input[@id='qsValue23']");
    public By searchButton_CB = By.xpath("//input[@id='btnSearch23']");
    public By searchButton = By.xpath("//input[@id='btnSearch0']");
    public By searchTypeDropdown = By.xpath(
            "//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*"
    );
    public By editButton = By.xpath("//input[@id='btnEdit0']");
    public By selectedOption = By.xpath(
            "//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']"
    );
    public String topDivSearchTypeDropdown =
            "//div[@class='component item_select'][normalize-space()='textName']";
    public By editSite = By.xpath("//input[@id='btnEdit0']");
    public By editSite_CB = By.xpath("//input[@id='btnEdit23']");
    public By cabinetTab = By.xpath("//div[@title='CAB:Cabinet Tracker']");
    public By applyButton = By.xpath("//input[@id='btnApply']");
    public By okButton = By.xpath("//input[@id='btnOK']");
    public By cancelButton = By.xpath("//input[@id='btnCancel']");
    public By searchTypeDropdown_CB = By.xpath(
            "//div[@id='qsField23']//div[@class='component button u_button_invert btn_raised btn_white']//child::*"
    );
    public By selectedOption_CB = By.xpath(
            "//div[@id='qsField23']//div[@class='l_items']//div[@class='component item_select selected']"
    );
    public String topDivSearchTypeDropdown_CB =
            "//div[@class='component item_select'][normalize-space()='textName']";
    public By cabinetVendor = By.xpath("//select[@sname='CAB:Cabinet Vendor']//option");
    public By cabinetModel = By.xpath("(//label[text()='CAB:Cabinet Model']/../following-sibling::td/div/div/input)[2]");
    public By cabinet_Model = By.xpath("(//label[text()='CAB:Cabinet Model']/../following-sibling::td/div/div/input)[1]");
    public By cabinetInstallation = By.xpath("//select[@sname='CAB:Cabinet Installation Location']//option");
    public By cabinetStatus = By.xpath("//select[@sname='CAB:Cabinet Status']//option");
    public By CabinetStatus = By.xpath("//select[@sname='CABE:Active']//option");
    public By cabinetEquipmentType = By.xpath("//select[@sname='CABE:Cabinet Equipment Type']//option");
    public By cabinetId = By.xpath("//table/tbody//div[@class='newFieldBtn']/input");
    public By cabinet_Id = By.xpath("//table/tbody/tr/td/div/div[@class='wrapper']/input[1]");
    //(//label[text()='CAB:Cabinet ID']/../following-sibling::td/div/div/input)[1]
    public By rectifierTab = By.xpath("//div[@title='CABE:Rectifier ']");
    public By equipmentLink = By.xpath("//table/tbody/tr[2]/td[2]/a");
    public By equipmentType = By.xpath("//table/tbody/tr[2]/td[3]/a");
    public By RFDSID = By.xpath("//label[text()='CABE:REC RFDS ID']");
    public By RECShelf = By.xpath("//label[text()='CABE:REC Shelf']");
    public By tableData = By.xpath("//table[@class='dhtmlxMebu_SubLevelArea_Tbl']/tbody");
    public By tableData_FieldInfo = By.xpath("//table[@class='dhtmlxMebu_SubLevelArea_Tbl']/tbody/tr[1]/td[2]/div");
    public By tableData_FieldHistory = By.xpath("//table[@class='dhtmlxMebu_SubLevelArea_Tbl']/tbody/tr[2]/td[2]/div");
    public By tableData_Comments = By.xpath("//table[@class='dhtmlxMebu_SubLevelArea_Tbl']/tbody/tr[7]/td/div");
    public By addButton = By.xpath("//input[@id='btnSubmit']");
    public By tableHeader = By.xpath("//table[@class='hdr']//tr//td");
    public By textArea_Comments = By.xpath("//table//tbody/tr[2]/td/textarea");
    public By closeButton = By.xpath("//*[@title='Close']");
    String parentWindow;

    public PowerCabinetPage(WebDriver driver) {
        super(driver);
    }

    public void clickingSiteTracker() throws Exception {
        waitUntilVisibleElement(find(SiteTracker));
        find(SiteTracker).click();
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

    public void selectSearchType_CB(String type) throws Exception {
        WebElement searchDropdown = find(searchTypeDropdown_CB);
        click(searchDropdown);
        System.out.println("sss" + searchDropdown);
        if (!find(selectedOption_CB).getText().equals(type)) {
            dropDownButtons(topDivSearchTypeDropdown_CB, type);
        }
    }

    public void searchForValue_Cabinet(String data, String type) throws Exception {
        fullScreen();
        waitForPageToLoad();
        setText(find(searchOption_CB), data);
        selectSearchType_CB(type);
        click(find(searchButton_CB));
        waitForPageToLoad();
    }

    public boolean isCabinetTabExists() throws Exception {
        sleep(3);
        if (findAll(cabinetTab).size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void goToCabinetTab() throws Exception {
        waitUntilVisibleElement(find(applyButton));
        sleep(5);
        click(find(cabinetTab));
        waitForPageToLoad();
    }

    public String switchToProjectPage() {
        parentWindow = switchToChildWindows();
        fullScreen();
        sleep(3);
        return parentWindow;
    }

    public void switchToTracker(String parentWindow) throws Exception {
        sleep(8);
        click(find(okButton));
        sleep(5);
        switchToSpecificWindow(parentWindow);
    }
    public void switchToTracker_Cancel(String parentWindow) throws Exception {
        sleep(8);
        click(find(cancelButton));
        sleep(5);
        switchToSpecificWindow(parentWindow);
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


    public boolean verifyCABRectifier_VendorField(String value) throws Exception {
        waitForPageToLoad();
        sleep(4);
        //  scrollToElement(selectionBoxBySname("CAB:Cabinet Vendor").get(0));
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
        sleep(4);
        boolean Flag = false;
        if (dropDownField.size() > 0) {
            System.out.println(dropDownField.size());
            Flag = true;
        }
        return Flag;
    }

    public boolean verifyCABRectifiers_VendorFields(String value, String data, String status) throws Exception {
        waitForPageToLoad();
        sleep(10);
        // click(find(Rectifiers_Max));
        //  setText(find(Rectifiers_Max),"20");
        inputBoxDataBySname("CAB:Number of Rectifiers Supported (max) ", "20");
        //setText(numberOfRectifiers, "20");
        sleep(4);
        scrollToElement(selectionBoxBySname("CAB:Cabinet Vendor").get(0));
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

        scrollToElement(inputBoxDataBySname("CAB:Cabinet Model"));
        sleep(6);
        find(cabinetModel).click();
        sleep(8);
        String parent = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        sleep(10);
        searchForValue("393", "PR:Power Reference ID");
        sleep(4);
        click(find(okButton1));
        sleep(5);
        switchToSpecificWindow(parent);
        String selectedOption = find(cabinet_Model).getAttribute("title");
        System.out.println("Selected Option is " + selectedOption);

        scrollToElement(selectionBoxBySname("CAB:Cabinet Status").get(0));
        List<WebElement> dropDownField1 = findAll(cabinetStatus);
        System.out.println("DropDown Options are - " + dropDownField1);
        for (WebElement e : dropDownField1
        ) {
            String option = e.getText();
            System.out.println("Option is -" + option);
            if (e.getText().equals(status)) {
                e.click();
            }
        }
        click(find(applyButton));
        sleep(4);
        boolean Flag = false;
        if (dropDownField1.size() > 0) {
            System.out.println(dropDownField1.size());
            Flag = true;
        }
        return Flag;
    }

    public Boolean verifyCABRectifiers_ActiveStatusField(String status) throws Exception {
        waitForPageToLoad();
        sleep(5);
        List<WebElement> dropDownField = findAll(CabinetStatus);
        System.out.println("DropDown Options are - " + dropDownField);
        for (WebElement e : dropDownField
        ) {
            String option = e.getAttribute("title");
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

    public boolean verifyCabinetEquipmentType_dropdownField(String value) throws Exception {
        waitForPageToLoad();
        scrollToElement(selectionBoxBySname("CABE:Cabinet Equipment Type").get(0));
        List<WebElement> dropDownField = findAll(cabinetEquipmentType);
        System.out.println("DropDown Options are - " + dropDownField);
        for (WebElement e : dropDownField
        ) {
            String option = e.getText();
            System.out.println("Option is -" + option);
            if (e.getText().equals(value)) {
                e.click();
                System.out.println("selected option is - " + e.getText());
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

    public boolean isEdit_buttonDisplayed() throws Exception {
        waitForPageToLoad();
        sleep(10);
        List<WebElement> buttons = findAll(By.xpath("//input[@id='btnEdit0']"));
        if (buttons.size() > 0 && buttons.get(0).isDisplayed()) {
            System.out.println("Edit Button is displayed" + buttons.get(0).isDisplayed());
            return true;
        }
        return false;
    }
    public boolean isAdd_buttonDisplayed() throws Exception {
        waitForPageToLoad();
        sleep(10);
        List<WebElement> buttons = findAll(By.xpath("//input[@id='btnAdd0']"));
        if (buttons.size() > 0 && buttons.get(0).isDisplayed()) {
            System.out.println("Add Button is displayed" + buttons.get(0).isDisplayed());
            return true;
        }
        return false;
    }

    public boolean isRowEditor_buttonDisplayed() throws Exception {
        waitForPageToLoad();
        sleep(10);
        List<WebElement> buttons = findAll(By.xpath("//input[@id='btnEditRow0']"));
        if (buttons.size() > 0 && buttons.get(0).isDisplayed()) {
            System.out.println("RowEditor Button is displayed" + buttons.get(0).isDisplayed());
            return true;
        }
        return false;
    }

    public boolean isExport_buttonDisplayed() throws Exception {
        waitForPageToLoad();
        sleep(10);
        List<WebElement> buttons = findAll(By.xpath("//input[@id='btnGridExport0']"));
        if (buttons.size() > 0 && buttons.get(0).isDisplayed()) {
            System.out.println("Export Button is displayed" + buttons.get(0).isDisplayed());
            return true;
        }
        return false;
    }

    public String verifyCabinetIdField() throws Exception {
        waitForPageToLoad();
        sleep(6);
        click(find(cabinetId));
        sleep(8);
        String parent = switchToChildWindows();
        fullScreenChildWindow();
        sleep(8);
        String data = "6WP1208E_1000140";
        searchForValue(data, "CAB:Cabinet ID");
        sleep(4);
        click(find(okButton1));
        switchToSpecificWindow(parent);
        String selectedOption = find(cabinet_Id).getAttribute("title");
        System.out.println("Selected Option is " + selectedOption);
        click(find(applyButton));
        sleep(5);
        return selectedOption;
    }

    public void clickRectifierTab() throws Exception {
        waitForPageToLoad();
        sleep(4);
        click(find(rectifierTab));
        waitForPageToLoad();
        sleep(2);
    }

    public void clickEquipmentLink() throws Exception {
        waitForPageToLoad();
        sleep(4);
        click(find(equipmentLink));
        sleep(4);
    }

    public String verifyCabinetEquipmentType() throws Exception {
        waitForPageToLoad();
        sleep(4);
        WebElement dropDownField = selectionBoxBySname("CABE:Cabinet Equipment Type").get(0);
        String text = getFirstSelectedOptionInDropdown(dropDownField);
        System.out.println("DropDown Option is - " + text);
        sleep(6);
        return text;
    }

    public boolean verifyCABE_REC_RFDSIDField() throws Exception {
        waitForPageToLoad();
        sleep(4);
        click(find(RFDSID));
        sleep(3);
        List<WebElement> options = findAll(tableData);
        System.out.println("DropDown Options are - " + options);
        for (WebElement e : options
        ) {
            String option = e.getText();
            System.out.println("Option is -" + option);
        }
        boolean Flag = false;
        if (options.size() > 0) {
            System.out.println(options.size());
            Flag = true;
        }
        return Flag;
    }

    public Boolean verifyCABERectifierRECShelfField() throws Exception {
        waitForPageToLoad();
        sleep(4);
        click(find(RECShelf));
        sleep(3);
        List<WebElement> options = findAll(tableData);
        System.out.println("DropDown Options are - " + options);
        for (WebElement e : options
        ) {
            String option = e.getText();
            System.out.println("Option is -" + option);
        }
        boolean Flag = false;
        if (options.size() > 0) {
            System.out.println(options.size());
            Flag = true;
        }
        return Flag;
    }
    public Boolean verifyCABERectifierRECShelfFieldInfo(String value) throws Exception {
        waitForPageToLoad();
        sleep(4);
        click(find(RECShelf));
        sleep(3);
        List<WebElement> options = findAll(tableData_FieldInfo);
        System.out.println("DropDown Options are - " + options);
        sleep(6);
        for (WebElement e : options
        ) {
            String option = e.getText();
            System.out.println("Option is -" + option);
            System.out.println("selected option is - " + e.getText());
            if (e.getText().equals(value)) {
                e.click();
                sleep(5);
                switchToChildWindows();
                fullScreenChildWindow();
                sleep(8);
                String fieldText = inputBoxDataBySname("Field Label").getAttribute("value");
                System.out.println("field text is - " + fieldText);
                if (fieldText.equals("REC Shelf")){
                    click(find(cancelButton));
                }
            }
        }
        // click(find(cancelButton));
        boolean Flag = false;
        if (options.size() > 0) {
            System.out.println(options.size());
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


    public Boolean verifyCABERectifierRECShelfFieldHistory(String value) throws Exception {
        sleep(4);
        click(find(RECShelf));
        sleep(3);
        List<WebElement> options = findAll(tableData_FieldHistory);
        System.out.println("DropDown Options are - " + options);
        sleep(6);
        for (WebElement e : options) {
            String option = e.getText();
            System.out.println("Option is -" + option);
            System.out.println("selected option is - " + e.getText());
            if (e.getText().equals(value)) {
                e.click();
                sleep(8);
                String parent = switchToChildWindows();
                fullScreenChildWindow();
                sleep(5);
               /* String response1 = searchForValueInGrid("Value",1);
                System.out.println("Value is - " + response1);
                String response2 = searchForValueInGrid("Date/Time",1);
                System.out.println("Value is - " + response2);
                String response3 = searchForValueInGrid("Author",1);
                System.out.println("Value is - " + response3);
                String response4 = searchForValueInGrid("Author's Email",1);
                System.out.println("Value is - " + response4);*/
                click(find(cancelButton));
                sleep(4);
                switchToSpecificWindow(parent);
            }

        }
        boolean Flag = false;
        if (options.size() > 0) {
            System.out.println(options.size());
            Flag = true;
        }
        return Flag;
    }
    public boolean validateFieldIs_Displayed(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldNameText = fieldByLabelTextIndex(name).get(1);
        scrollToElement(fieldNameText);
        boolean fieldName = fieldNameText.isDisplayed();
        return fieldName;
    }

    public String searchForValueInGrid(String columnName,int row) throws Exception {
        sleep(4);
        fullScreen();
        waitForPageToLoad();
        sleep(15);
        int columnToFind = getTableData(columnName);
        //int columnValueToMatch = getTableData(searchByColumn);
        List<WebElement> tableContents = findAll(tableData);
        if(columnToFind !=-1){

            List<WebElement> cellDataForTheRow = findAll(By.tagName("td"),tableContents.get(row));
            scrollToElement(cellDataForTheRow.get(row));
            sleep(1);
            scrollToElement(cellDataForTheRow.get(columnToFind));
            String cellValue = getText(cellDataForTheRow.get(columnToFind));
            if(cellValue.isEmpty()){
                cellValue = getText(find(By.tagName("div"),cellDataForTheRow.get(columnToFind)));
            }
            if(cellValue.isEmpty()){
                List<WebElement> elements = findAll(By.tagName("a"),cellDataForTheRow.get(columnToFind));
                if(elements.size()>0){
                    cellValue = getText(elements.get(0));
                }
            }
            return cellValue;
        }
        return "ERROR OCCURRED";
    }
    public int getTableData(String columnName) throws Exception {
        List<WebElement> tableHeaders = findAll(tableHeader);
        scrollToElement(tableHeaders.get(1));
        for(int i = 0; i<tableHeaders.size()-1; i++){
            scrollToElement(tableHeaders.get(i+1));
            if(getText(find(By.className("hdr_label"),tableHeaders.get(i))).contains(columnName))
                return i;
        }
        return -1;
    }


    public AddCabinetPage clickAddButton() throws Exception {
        waitForPageToLoad();
        sleep(5);
        buttonClick("Add", 1);
        sleep(8);
        return new AddCabinetPage(driver);
    }

    public void selectEditOption() throws Exception {
        waitUntilVisibleElement(find(editButton));
        sleep(10);
        click(find(editButton));
        sleep(5);
    }
}

