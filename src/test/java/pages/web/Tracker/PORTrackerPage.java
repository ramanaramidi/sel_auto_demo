package pages.web.Tracker;

import ch.qos.logback.classic.pattern.DateConverter;
import commons.objects.Ring;
import commons.objects.Users;
import org.apache.commons.exec.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import utility.helper.MiscHelpers;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class PORTrackerPage extends BasePage {

    public WebDriver driver;

    public PORTrackerPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public By AdminMenu = By.xpath("//input[@title='Admin Menu']");
    public By editPOR = By.xpath("//input[@id='btnEdit0']");
    public By AppCenter = By.xpath("//div[text()='App Center']");
    public By addPORButton = By.xpath(" //input[@value='Add']");
    public By PORRequestType = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[35]//div");
    public By PORRequestQueue = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[39]");
    public By createdPORBy = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[68]");
    public By createdPORDate = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[70]");
    public By LastPORUpdatedBy = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[72]");
    public By LastPORUpdatedDate = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[74]");
    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By searchButton = By.xpath("//input[@id='btnSearch0']");
    public By searchTypeDropdown = By.xpath("(//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*)[1]");
    public String topDivSearchTypeDropdown = "//div[@class='component item_select'][normalize-space()='textName']";
    public By selectedOption = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public By projectIDClick = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[4]//a");
    public By getProjectIDClickNTP = By.xpath("//div[@class='objbox customscroll']//table//tbody//tr[2]//td[4]//a");
    public By projectIDValueText = By.xpath("//input[@id='idx3']");
    public By okButton = By.xpath("//input[@id='btnOK']");
    public By totalPORIDCount = By.xpath("//span[@id='gridStat0']");
    public By porCountNav = By.xpath("//div[@id='navRange0']");
    public By tableHeader = By.xpath("//table[@class='hdr']//tr//td");
    public By tableData1 = By.xpath("//table[@class='obj']//tr");
    public By tableData = By.xpath("//table[@class='obj overlap']//tr");
    public By optionsMenu = By.id("btnoptionsGroupOpener0");
    public By addButton = By.xpath("//*[@id='btnAdd0']");
    public By applyButton = By.xpath("//input[@id='btnApply']");
    public By deleteOption = By.xpath("//div[@id='itemDelete0']//div[@class='ibtn_value']");
    public By programSearchButton = By.xpath("//input[@id='btnSearch0']");
    public By gridStatsOption = By.xpath("//div[@id='itemGridStats0']//div[@class='ibtn_value']");
    public By gridStatsSelectionBox = By.xpath("//div[@class='fieldsLblDiv']//select[@name='fields']");
    public By statsValues = By.xpath("//table[@id='RecordCountTable']//child::td[3]");
    public By cancel = By.xpath("//input[@id='btnCancel']");
    public By porAdminProgramName = By.xpath("//div[@class='hdr_cell']//div[text()='PAT:Program Name']");
    public By porAdminProgramNameSorting = By.xpath("//div[@class='hdr_cell']//div[text()='PAT:Program Name']//following-sibling::div");
    public By patCreatedDate = By.xpath("//div[@class='hdr_cell']//div[text()='PAT:Created Date']");
    public By patCreatedDateSorting = By.xpath("//div[@class='hdr_cell']//div[text()='PAT:Created Date']//following-sibling::div");
    public By pORCreatedDate = By.xpath("//div[@class='hdr_cell']//div[text()='POR:Created Date']");
    public By pORCreatedDateSorting = By.xpath("//div[@class='hdr_cell']//div[text()='POR:Created Date']//following-sibling::div");

    public By descOrder = By.xpath("//div[text()='Sort DESC']");

    public By ascOrder = By.xpath("//div[text()='Sort ASC']");
    public By searchData = By.xpath("//div[@class='objbox customscroll']//table//tbody//tr//td[1]");

    String parentWindow;
    public String Program_dropdown;


    public void navigateToAppCenter() throws Exception {
        click(find(AdminMenu));
        click(find(AppCenter));
    }

    public int getTableData(String columnName) throws Exception {
        List<WebElement> tableHeaders = findAll(tableHeader);
        scrollToElement(tableHeaders.get(1));
        for (int i = 0; i < tableHeaders.size() - 1; i++) {
            scrollToElement(tableHeaders.get(i + 1));
            if (getText(find(By.className("hdr_label"), tableHeaders.get(i))).contains(columnName))
                return i;
        }
        return -1;
    }

    public String searchForValueInGrid(String columnName, String searchByColumn, String searchValue) throws Exception {
        fullScreen();
        int columnToFind = getTableData(columnName);
        int columnValueToMatch = getTableData(searchByColumn);
        List<WebElement> tableContents = findAll(tableData);
        if (columnValueToMatch != -1 && columnToFind != -1) {
            for (int row = 1; row < tableContents.size(); row++) {
                List<WebElement> cellDataForTheRow = findAll(By.tagName("td"), tableContents.get(row));
                scrollToElement(cellDataForTheRow.get(1));
                if (getText(cellDataForTheRow.get(columnValueToMatch)).contains(searchValue)) {
                    sleep(1);
                    scrollToElement(cellDataForTheRow.get(columnToFind));
                    String cellValue = getText(cellDataForTheRow.get(columnToFind));
                    if (cellValue.isEmpty()) {
                        cellValue = getText(find(By.tagName("div"), cellDataForTheRow.get(columnToFind)));
                    }
                    if (cellValue.isEmpty()) {
                        List<WebElement> elements = findAll(By.tagName("a"), cellDataForTheRow.get(columnToFind));
                        if (elements.size() > 0) {
                            cellValue = getText(elements.get(0));
                        }
                    }
                    return cellValue;
                }
            }
        }
        return "ERROR OCCURRED";
    }
    public String searchForValueInGrid(String columnName,int row) throws Exception {
        fullScreen();
        waitForPageToLoad();
        int columnToFind = getTableData(columnName);
        List<WebElement> tableContents = findAll(tableData1);
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

    public AddPORPage selectEditOption() throws Exception {
        waitUntilVisibleElement(find(editPOR));
        click(find(editPOR));
        // sleep(5);
        return new AddPORPage(driver);
    }

    public void deleteSelection() throws Exception {
        //fullScreen();
        waitUntilVisibleElement(find(optionsMenu));
        click(find(optionsMenu));
        clickCancelAndAlert(find(deleteOption), "accept");
        sleep(5);
        //waitUntilVisibleElement(find(optionsMenu));
        acceptAlert();
        waitUntilVisibleElement(find(optionsMenu));
    }

    public boolean isValuePresentInGrid(String columnName) throws Exception {
        waitForPageToLoad();
        int columnToFind = getTableData(columnName);
        if (columnToFind == -1) {
            System.out.println("COULD NOT FIND THE COLUMN");
            return false;
        }

        List<WebElement> tableContents = findAll(tableData);
        List<WebElement> cellDataForTheFirstRow = findAll(By.tagName("td"), tableContents.get(1));
        scrollToElement(cellDataForTheFirstRow.get(columnToFind));
        String cellData = getText(cellDataForTheFirstRow.get(columnToFind));
        if (cellData.isEmpty() || cellData.equals(" ") || cellData == null) {

            cellData = getText(find(By.tagName("a"), cellDataForTheFirstRow.get(columnToFind)));
        }
        return !cellData.isEmpty() && !cellData.equals(null) && !cellData.equals(" ");
    }

    public int getPORIDCount() throws Exception {
        waitUntilVisibleElement(find(porCountNav));
        if (!getText(find(porCountNav)).contains("No Data")) {
            waitUntilVisibleElement(find(totalPORIDCount));
            hoverOver(find(totalPORIDCount));
            String PORTrackerIDCount = find(totalPORIDCount).getText();
            System.out.println(PORTrackerIDCount);
            return Integer.parseInt(PORTrackerIDCount);
        }
        return 0;
    }


    public AddPORPage clickAddNewPORButton() throws Exception {
        waitUntilVisibleElement(find(addPORButton));
        buttonClick("Add", 1);
        // sleep(15);
        return new AddPORPage(driver);
    }


    public Boolean isPORStatus(String requestType, String requestQueue) throws Exception {
        sleep(30);
        String text = getText(find(PORRequestType));
        System.out.println("Actual POR Request Type value_" + text);
        System.out.println("Expected POR Request Type Value_" + requestType);

        String PORRequestQueueValue = getText(find(PORRequestQueue));
        System.out.println("Actual POR Request Queue value_" + PORRequestQueueValue);
        System.out.println("Expected POR Request Queue value_" + requestQueue);

        String createdByUsername = getText(find(createdPORBy));
        System.out.println("Site Created by username is _" + createdByUsername);

        //String name = userDetails.getUser();

        String createdBySiteDate = getText(find(createdPORDate));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formatted = createdBySiteDate.format(String.valueOf(formatter));
        System.out.println("Site Created by Date is _" + formatted);
        String createdDate = MiscHelpers.currentDateTime("MM/dd/yyyy");
        System.out.println("Present Date is_" + createdDate);
        if (createdBySiteDate.equals(createdDate)) {
            System.out.println("POR  creation Date Verification Done");
        }
        String lastUpdatedByUsername = getText(find(LastPORUpdatedBy));
        System.out.println("Site Last updated by username is _" + lastUpdatedByUsername);

        String lastUpdatedBySiteDate = getText(find(LastPORUpdatedDate));
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formatted1 = lastUpdatedBySiteDate.format(String.valueOf(formatter1));
        System.out.println("Site Last updated by Date is _" + formatted1);

        if (lastUpdatedBySiteDate.equals(createdDate)) {
            System.out.println("POR updation Date Verification Done");
        }
        if (!text.equals(requestType) && !PORRequestQueueValue.equals(requestQueue)) {
            return false;
        } else {
            return true;
        }

    }


    public void searchForValue(String data, String type) throws Exception {
        waitUntilVisibleElement(find(searchOption));
        fullScreen();
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitUntilVisibleElement(find(addPORButton));
        sleep(10);
    }


    public void selectSearchType(String type) throws Exception {
        WebElement searchDropdown = find(searchTypeDropdown);
        click(searchDropdown);
        System.out.println("sss" + searchDropdown);
        if (!find(selectedOption).getText().equals(type)) {
            dropDownButtons(topDivSearchTypeDropdown, type);
        }
    }

    public String projectIDClick() throws Exception {
        sleep(5);
        click(find(projectIDClick));
        sleep(5);
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(5);
        String ProjectIDValueText = find(projectIDValueText).getAttribute("title");
        //getValueTitle(projectIDValueText)
        System.out.println("ProjectIDValue_" + ProjectIDValueText);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        return ProjectIDValueText;
    }

    public void sortingASCOrder() throws Exception {
        scrollToElement(find(pORCreatedDate));
        hoverOver(find(pORCreatedDate));
        click(find(pORCreatedDateSorting));
        click(find(ascOrder));
        waitUntilVisibleElement(find(searchData));
        scrollToElement(find(pORCreatedDate));

    }

    public void sortingDescOrder() throws Exception {
        scrollToElement(find(pORCreatedDate));
        hoverOver(find(pORCreatedDate));
        click(find(pORCreatedDateSorting));
        click(find(descOrder));
        waitUntilVisibleElement(find(searchData));
        scrollToElement(find(pORCreatedDate));
    }

    public PORTrackerPage addNewProgramPage() throws Exception {
        fullScreen();
        waitUntilVisibleElement(find(addButton));
        click(find(addButton));
        return new PORTrackerPage(driver);
    }

    public String switchToTrackerPage() {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        sleep(3);
        return parentWindow;
    }

    public void switchToTracker(String parentWindow) throws Exception {
        click(find(okButton));
        sleep(5);
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
    }

    public void switchToTrackerCancel(String parentWindow) throws Exception {
        click(find(cancel));
        sleep(5);
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
    }

    public void addNewProgram() throws Exception {
        setText(inputBoxDataBySname("PAT:Plan Type"), "Test_POR");
        setText(inputBoxDataBySname("PAT:Release Version"), "Test"+MiscHelpers.getRandomNumber(3));
        selectDropdownOption(selectionBoxBySname("PAT:Category").get(0), "New Build");
        selectDropdownOption(selectionBoxBySname("PAT:Program Status").get(0), "Inactive");
        click(find(applyButton));
        waitForPageToLoad();
    }

    public Boolean getProgramConcat() throws Exception {
        fullScreen();
        String Plan_Type = inputBoxDataBySname("PAT:Plan Type").getAttribute("origval");
        System.out.println("PAT:Plan Type is:" + Plan_Type);
        String Release_Version = inputBoxDataBySname("PAT:Release Version").getAttribute("origval");
        System.out.println("PAT:Release Version is:" + Release_Version);
        WebElement Category = selectionBoxBySname("PAT:Category").get(0);
        String category = getFirstSelectedOptionInDropdown(Category);
        System.out.println("PAT:Category is:" + category);
        String Program_DropDown_Concat = Plan_Type + "_" + Release_Version + "_" + category;
        System.out.println("Program_Dropdown_concat is:" + Program_DropDown_Concat);
        Program_dropdown = inputBoxDataBySname("PAT:Program Dropdown").getAttribute("origval");
        System.out.println("PAT:Program Dropdown is: " + Program_dropdown);
        sleep(5);
        System.out.println("value is " + Program_dropdown.equals(Program_DropDown_Concat));
        return Program_dropdown.equals(Program_DropDown_Concat);
    }

    public void updateFields() throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        setText(inputBoxDataBySname("PAT:Plan Description"), "Test Record");
        selectDropdownOption(selectionBoxBySname("PAT:Governance").get(0), "Yes");
        selectDropdownOption(selectionBoxBySname("PAT:POR Process/Family").get(0), "Testing Only");
        selectDropdownOption(selectionBoxBySname("PAT:Small Cell Plan Type?").get(0), "Yes");
        selectDropdownOption(selectionBoxBySname("PAT:Decom Ring field required?").get(0), "Yes, must NOT match Ring ID");
        setText(inputBoxDataBySname("PAT:POR Ops Comments"), "Test Update");
        selectDropdownOption(selectionBoxBySname("PAT:Project Type").get(0), "M-Modification General");
        click(find(applyButton));
        sleep(5);
    }

    public boolean verifyProgramDetailsUpdate() throws Exception {
        sleep(5);
        waitUntilVisibleElement(find(applyButton));
        String currentDate = MiscHelpers.currentDateTime("MM/dd/yyyy");
        System.out.println("Current Date::" + currentDate);
        WebElement lastUpdatedBy = inputBoxDataBySname("PAT:Last Changed By");
        scrollToElement(lastUpdatedBy);
        String updated = lastUpdatedBy.getText();
        System.out.println("Name::" + updated);
        boolean updatedBy = updated.equals("");
        System.out.println("boolean:: " + updatedBy);
        WebElement lastUpdatedDate = inputBoxDataBySname("PAT:Last Changed Date");
        scrollToElement(lastUpdatedDate);
        String getDate = getDocumentTextByIdJs(lastUpdatedDate.getAttribute("id"));
        System.out.println("Date::" + getDate);
        boolean updatedDate = getDate.contains(currentDate);
        System.out.println("boolean:: " + updatedDate);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        sleep(5);
        return updatedBy && updatedDate;
    }

    public PORTrackerPage selectGridStats() throws Exception {
        fullScreen();
        waitUntilVisibleElement(find(optionsMenu));
        click(find(optionsMenu));
        click(find(gridStatsOption));
        return new PORTrackerPage(driver);
    }

    public boolean verifyStats() throws Exception {
        waitUntilVisibleElement(find(cancel));
        boolean stats = find(statsValues).getText().equals("");
        System.out.println("Stats::" + stats);
        return stats;
    }

    public void selectPlanType() throws Exception {
        waitUntilVisibleElement(find(cancel));
        selectDropdownOption(find(gridStatsSelectionBox), "PAT:Plan Type");
        sleep(10);
    }
    public String VerifyProgramNameInPORTracker(String program) throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        dropDownDotsClick("POR:Program Name");
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(programSearchButton));
        setText(find(searchOption), program);
        click(find(searchButton));
        sleep(3);
        String tableList = tableDataList(3);
        List<String> programDropDown = getDocumentTextListByXpathJs(tableList);
        String programName = programDropDown.toString();
        System.out.println("Program:: " + programName);
        buttonClick("Cancel", 2);
        switchToSpecificWindow(parent1);
        click(find(cancel));
        switchToSpecificWindow(parentWindow);
        return programName;
    }

    public void sortingASCOrderDatePat() throws Exception {
        scrollToElement(find(patCreatedDate));
        hoverOver(find(patCreatedDate));
        click(find(patCreatedDateSorting));
        click(find(ascOrder));
        waitUntilVisibleElement(find(searchData));
        scrollToElement(find(patCreatedDate));
    }

    public boolean verifyDatesSortingOrder(String cellDate1, String cellDate2, boolean set_SmallToBig) throws Exception {
        waitForPageToLoad();
        String dateStr1 = cellDate1;
        System.out.println(dateStr1);
        String dateStr2 = cellDate2;
        System.out.println(dateStr2);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date1 = formatter.parse(dateStr1);
        Date date2 = formatter.parse(dateStr2);
        if (date1.equals(date2)) {
            System.out.println(dateStr1 + " is equals to " + dateStr2);
            return true;
        }
        else {
            if (set_SmallToBig) {
                if (date1.before(date2)) {
                    System.out.println(dateStr1 + " is before " + dateStr2);
                    return true;
                }
            }
            else if (date1.after(date2)) {
                System.out.println(dateStr1 + " is after " + dateStr2);
                return true;
            }
        }
        return false;
    }

    public void sortingDSCOrderDatePat() throws Exception{
        scrollToElement(find(patCreatedDate));
        hoverOver(find(patCreatedDate));
        click(find(patCreatedDateSorting));
        click(find(descOrder));
        waitUntilVisibleElement(find(searchData));
        scrollToElement(find(patCreatedDate));
    }
    public void sortingASCOrderPat() throws Exception {
        scrollToElement(find(porAdminProgramName));
        hoverOver(find(porAdminProgramName));
        click(find(porAdminProgramNameSorting));
        click(find(ascOrder));
        waitUntilVisibleElement(find(searchData));
        scrollToElement(find(porAdminProgramName));
    }
    public boolean verifyProgramNameSortingOrder(String cellValue1, String cellValue2, boolean set_SmallToBig) throws Exception {
        waitForPageToLoad();
        String programName1 = cellValue1;
        System.out.println(programName1);
        String programName2 = cellValue2;
        System.out.println(programName2);
        int compare = programName1.compareTo(programName2);
        System.out.println(compare);
        if (set_SmallToBig) {
            if (compare < 0){
                return true;
            }
        }
        else if (compare > 0) {
            return true;
        }return false;
    }
    public void sortingDSCOrderPat() throws Exception {
        scrollToElement(find(porAdminProgramName));
        hoverOver(find(porAdminProgramName));
        click(find(porAdminProgramNameSorting));
        click(find(descOrder));
        waitUntilVisibleElement(find(searchData));
        scrollToElement(find(porAdminProgramName));
    }
}

