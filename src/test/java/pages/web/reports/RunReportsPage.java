package pages.web.reports;

import org.openqa.selenium.*;
import pages.BasePage;
import utility.helper.MiscHelpers;

import java.util.List;

public class RunReportsPage extends BasePage {

    public By bellIcon = By.xpath("//input[@id='topPanelBtnEvents']");
    public By reportId= By.xpath("//*[@id='divPage1']/table/tbody/tr[3]/td[2]");

    public By okButton = By.xpath("//input[@id='btnOK']");
    public By DeploymentReport = By.xpath("//a[text()='Deployment Report']");
    public By searchBar = By.xpath("//div[@class='component panelForm panel_menu']//input[@id='search_']");
    public By taskVizion = By.xpath("//a[@href=\".\"][normalize-space()='TaskVizion Report']");
    public By marketDots = By.xpath("//input[@sname='Market']/preceding-sibling::div/div[2]/input");
    public By marketField = By.xpath("//input[@sname='Market']/preceding-sibling::div/input");
    public By projectStatusDots = By.xpath("//input[@sname='PJ:Project Status']/preceding-sibling::div/div[2]/input");
    public By projectStatusField = By.xpath("//input[@sname='PJ:Project Status']/preceding-sibling::div/input");
    public By activeCheckbox = By.xpath("(//div[@class='hdr_cell']//div[text()='PJ:Project Status']/ancestor::table/../following-sibling::div//label)[1]");
    public By projectTypeDots = By.xpath("//input[@sname='PJ:Project Type']/preceding-sibling::div/div[2]/input");
    public By projectTypeField = By.xpath("//input[@sname='PJ:Project Type']/preceding-sibling::div/input");
    public By projectTypeFirstCheckbox = By.xpath("(//div[@class='hdr_cell']//div[text()='PJ:Project Type']/ancestor::table/../following-sibling::div//label)[1]");
    public By projectIdDots = By.xpath("//input[@sname='PJ:Project ID']/preceding-sibling::div/div[2]/input");
    public By projectIdField = By.xpath("//input[@sname='PJ:Project ID']/preceding-sibling::div/input");
    public By projectIdFirstCheckbox = By.xpath("(//div[@class='hdr_cell']//div[text()='Project ID']/ancestor::table/../following-sibling::div//label)[1]");
    public By selectTaskDots = By.xpath("//input[@sname='Select Tasks']/preceding-sibling::div/div[2]/input");
    public By selectTaskField = By.xpath("//input[@sname='Select Tasks']/preceding-sibling::div/input");
    public By selectTaskFirstCheckbox = By.xpath("(//div[@class='hdr_cell']//div[text()='Select Tasks']/ancestor::table/../following-sibling::div//label)[1]");
    public By historyButton = By.xpath("//div[@title='Report History']");
    public By reportCheckBox = By.xpath("(//div[@class='hdr_cell']//div[@id='SelectCheckboxes0']/ancestor::table/../following-sibling::div//label)[2]");
    public By deleteOption = By.xpath("//input[@id='btnDelete0']");
    public By programDots = By.xpath("//input[@sname='Program']/preceding-sibling::div/div[2]/input");
    public By programTextField = By.xpath("//input[@sname='Program']/preceding-sibling::div/input");
    public By programFirstCheckbox = By.xpath("(//div[@class='hdr_cell']//div[text()='Program']/ancestor::table/../following-sibling::div//label)[1]");
    public By optionalField1Dots = By.xpath("(//input[@sname='Select Optional Field']/preceding-sibling::div/div[2]/input)[1]");
    public By optionalField1 = By.xpath("(//input[@sname='Select Optional Field']/preceding-sibling::div/input)[1]");
    public By optionalField2Dots = By.xpath("(//input[@sname='Select Optional Field']/preceding-sibling::div/div[2]/input)[2]");
    public By optionalField2 = By.xpath("(//input[@sname='Select Optional Field']/preceding-sibling::div/input)[2]");
    public By projectCountNav = By.xpath("//div[@id='navRange0']");
    public By totalProjectIDCount = By.xpath("//div[@id='navTotal0']");
    public By tableHeader = By.xpath("//table[@class='hdr']//tr//td");
    public By tableData = By.xpath("//table[@class='obj']//tr");
    public By specificTimeRadioButton = By.xpath("//input[@id='runTime']");
    public By Hours = By.xpath("//select[@id='hours']");
    public By Minutes = By.xpath("//select[@id='minutes']");
    public By runReportTableElement = By.xpath("//tr[contains(@class,' ev_dhx_skyblue rowselected')]//parent::*");
    public By deliveryDots = By.xpath("//input[@id='btnreportDelivery']");
    public By delieveryReportEmailCheckBox = By.xpath("(//div[@class='hdr_cell']//div[text()='Report Delivery']/ancestor::table/../following-sibling::div//label)[1]");
    public By cancelButton = By.xpath("//input[@id='btnCancel']");
    public By okButton1 = By.xpath("//input[@id='btnOK0']");
    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By searchButton = By.xpath("//input[@id='btnSearch0']");
    public By searchTypeDropdown = By.xpath("//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*");
    public By selectedOption = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public By closeButton = By.xpath("//input[@id='btnClose0']");
    public String topDivSearchTypeDropdown = "//div[@class='component item_select'][normalize-space()='textName']";
    public By report1 = By.xpath("//a[@href='.'][normalize-space()='Deployment Report']");
    public By checkBox = By.xpath("//input[@id='lblcb4516061']");
    public By deleteBtn = By.xpath("//input[@id='btnDelete0']");
    public By desButton = By.xpath("//*[@id='gridbox0']/div[1]/table/tbody/tr[2]/td[2]/div/div/div[2]");
    public By order = By.xpath("//*[@id='contextMenu']/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div");
    public By sortButton = By.xpath("//*[@id='gridbox0']/div[1]/table/tbody/tr[2]/td[2]/div/div/div[2]");
    public By descButton = By.xpath("//*[@id='contextMenu']/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div");
    String parentWindow;
    String parentWindow1;

    public WebDriver driver;

    public RunReportsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    public ReportSettingsPage goToReportSettingsPage(String report) throws Exception {
        clickTableLinkByText(report);
        return new ReportSettingsPage(driver);
    }

    public boolean verifyHeaderItemInNotification(String type,String name) throws Exception {
        List<WebElement> element = headerItemVerification(type,name);
        if(element.size()>0)
            return true;
        return false;
    }

    public boolean verifyBtDateAndStatus(String status,String type,String name) throws Exception {
        String date = MiscHelpers.currentDateTime("10/17/2022");
        List<WebElement> element = headerItemRootNotification(type,name);
        System.out.println("root size-"+element.size());
        List<WebElement> dataElements = dataItemVerification(date,status,element.get(0));
        System.out.println(" size-"+dataElements.size());
        if(dataElements.size()>0)
            return true;
        return false;
    }

    public Boolean verifyReportStatus(String status)throws Exception {
        String date = MiscHelpers.currentDateTime("MM/dd/yyyy");
        List<WebElement> panelData =  notificationPanelData(date,status);
        System.out.println("panelData size-"+panelData.size());
        sleep(10);
        if(panelData.size()>0){
            return  true;
        }
        return false;
    }

    public void clickBellIcon() throws Exception{
        waitUntilVisibleElement(find(bellIcon));
        click(find(bellIcon));
        sleep(5);

    }

    private void reportsList (String Report) throws Exception{
        List<WebElement> reportSelected = notificationPanelReportLink(Report);
        System.out.println("Report List size is- " +reportSelected.size());
        for (int i=0; i<reportSelected.size();i++){
            sleep(20);
            reportSelected = notificationPanelReportLink(Report);}
    }

    public String selectReport(String Report) throws Exception {
        List<WebElement> reportSelected = notificationPanelReportLink(Report);
        System.out.println("Report List size is- " +reportSelected.size());
        if(reportSelected.size()<=0) {
            reportsList(Report);
        }
        if(reportSelected.size()>0){
            scrollToElement(reportSelected.get(0));
            click(reportSelected.get(0));
            sleep(10);
        }
        String parent = switchToChildWindows();
        String ReportId = find(reportId).getText();
        System.out.println("Report ID is " + ReportId);
        sleep(5);
        click(find(okButton));
        switchToSpecificWindow(parent);
        return ReportId;
    }
    public RunReportsPage searchReport() throws Exception {
        sleep(4);
        search("Deployment Report");
        sleep(10);
        click(find(DeploymentReport));
        sleep(5);
        return new RunReportsPage(driver);
    }
    public void search(String option) throws Exception {
        setText(find(searchBar), option);
    }
    public ReportSettingsPage goToReport() throws Exception {
        click(find(report1));
        return new ReportSettingsPage(driver);
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
    public void deleteRecord() throws Exception {
        sleep(5);
        WebElement element = find(reportCheckBox);
        scrollToElement(element);
        sleep(4);
        click(find(reportCheckBox));
        sleep(5);
        click(find(deleteBtn));
        sleep(5);
        acceptAlert();
        sleep(3);
    }
    public String getCurrentDate() {
        String currentDate = MiscHelpers.currentDateTime("MM/dd/yyyy");
        return currentDate;
    }
    public boolean isValuePresentInGrid(String columnName) throws Exception {
        waitForPageToLoad();
        int columnToFind = getTableData(columnName);
        List<WebElement> tableContents = findAll(tableData);
        List<WebElement> cellDataForTheFirstRow = findAll(By.tagName("td"),tableContents.get(1));
        String cellData = getText(cellDataForTheFirstRow.get(columnToFind));
        return !cellData.isEmpty() && !cellData.equals(null) && !cellData.equals(" ");
    }
    public void goToTaskVizionReportsPage() throws Exception{
        waitForPageToLoad();
        //String taskVizion = "TaskVizion Report";
        click(find(taskVizion));
        sleep(5);
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton));
        waitForPageToLoad();
        // click(find(okButton));
        //switchToSpecificWindow(parentWindow);
    }

    public void goToRunReportPage() throws Exception{

        click(find(okButton));
        switchToSpecificWindow(parentWindow);
    }
    public void searchForValue(String data, String type) throws Exception {
        fullScreen();
        waitForPageToLoad();
        // waitUntilVisibleElement(find(searchOption));
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitForPageToLoad();
        // sleep(10);
        //waitUntilVisibleElement(find(editProject));
        // sleep(20);
    }


    public void selectSearchType(String type) throws Exception {
        WebElement searchDropdown = find(searchTypeDropdown);
        click(searchDropdown);
        System.out.println("sss" + searchDropdown);
        if (!find(selectedOption).getText().equals(type)) {
            dropDownButtons(topDivSearchTypeDropdown, type);
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

    public void addMandatoryDetails() throws Exception {
        click(find(marketDots));
        parentWindow1 = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton1));
        waitForPageToLoad();
        searchForValue("SAN FRANCISCO - SF","Select Market");
        waitForPageToLoad();
        click(find(okButton1));
        switchToSpecificWindow(parentWindow1);
        click(find(projectStatusDots));
        parentWindow1 = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton1));
        waitForPageToLoad();
        click(find(activeCheckbox));
        click(find(okButton1));
        switchToSpecificWindow(parentWindow1);
        click(find(projectTypeDots));
        parentWindow1 = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton1));
        waitForPageToLoad();
        click(find(projectTypeFirstCheckbox));
        click(find(okButton1));
        switchToSpecificWindow(parentWindow1);
        click(find(projectIdDots));
        parentWindow1 = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton1));
        waitForPageToLoad();
        click(find(projectIdFirstCheckbox));
        click(find(okButton1));
        switchToSpecificWindow(parentWindow1);
        click(find(selectTaskDots));
        parentWindow1 = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton1));
        waitForPageToLoad();
        click(find(selectTaskFirstCheckbox));
        click(find(okButton1));
        switchToSpecificWindow(parentWindow1);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        waitUntilVisibleElement(find(taskVizion));
        click(find(historyButton));
    }

    public void goToHistoryField() throws Exception{

        //waitUntilVisibleElement(find(taskVizion));
        sleep(5);
        click(find(historyButton));
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        sleep(10);
    }

    public void deleteReport() throws Exception{
        sleep(10);
        click(find(reportCheckBox));
        sleep(3);
        click(find(deleteOption));
        //clickCancelAndAlert(find(deleteOption),"accept");
        acceptAlert();

    }
    public void addOptionalDetails() throws Exception {
        click(find(programDots));
        parentWindow1 = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton1));
        waitForPageToLoad();
        click(find(programFirstCheckbox));
        click(find(okButton1));
        switchToSpecificWindow(parentWindow1);

        scrollToElement(find(optionalField1Dots));
        click(find(optionalField1Dots));
        parentWindow1 = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton1));
        waitForPageToLoad();
        searchForValue("Das OEM","Select Field");
        //click(find(programFirstCheckbox));
        click(find(okButton1));
        switchToSpecificWindow(parentWindow1);

        scrollToElement(find(optionalField2Dots));
        click(find(optionalField2Dots));
        parentWindow1 = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton1));
        waitForPageToLoad();
        searchForValue("area abbr","Select Field");
        //click(find(programFirstCheckbox));
        click(find(okButton1));
        switchToSpecificWindow(parentWindow1);

    }

    public void switchToRunReportpage(String parentWindow) throws Exception{
        click(find(closeButton));
        switchToSpecificWindow(parentWindow);
    }
    public void backToRunReportpage() throws Exception{
        click(find(closeButton));
        switchToSpecificWindow(parentWindow);
    }
    public void clearOptionalDetails() throws Exception{

        WebElement programField = find(programTextField);
        setText(programField,"");
        WebElement option1 = find(optionalField1);
        scrollToElement(option1);
        setText(option1,"");
        WebElement option2 = find(optionalField2);
        scrollToElement(option2);
        setText(option2,"");
    }

    public void setMarketForinitialProjectId() throws Exception{
        click(find(marketDots));
        parentWindow1 = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton1));
        waitForPageToLoad();
        searchForValue("ALASKA - AK","Select Market");
        waitForPageToLoad();
        click(find(okButton1));
        switchToSpecificWindow(parentWindow1);

        click(find(projectIdDots));
        parentWindow1 = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton1));
        waitForPageToLoad();
    }
    public int getProjectIDCount() throws Exception {
        waitForPageToLoad();
        waitUntilVisibleElement(find(projectCountNav));
        if (!getText(find(projectCountNav)).contains("No Data")) {
            waitUntilVisibleElement(find(totalProjectIDCount));
            hoverOver(find(totalProjectIDCount));
            String ProjectIDCount = find(totalProjectIDCount).getText();
            System.out.println(ProjectIDCount);
            String splitText = ProjectIDCount.split(" ")[2];
            return Integer.parseInt(splitText);
        }
        return 0;
    }

    public void setMarketForFinalProjectId() throws Exception{
        click(find(okButton1));
        switchToSpecificWindow(parentWindow1);
        click(find(marketDots));
        parentWindow1 = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton1));
        waitForPageToLoad();
        searchForValue("SAN FRANCISCO - SF","Select Market");
        waitForPageToLoad();
        click(find(okButton1));
        switchToSpecificWindow(parentWindow1);

        click(find(projectIdDots));
        parentWindow1 = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton1));
        waitForPageToLoad();
        click(find(projectIdFirstCheckbox));

    }
    public int getFinalProjectIDCount() throws Exception {
        waitForPageToLoad();
        waitUntilVisibleElement(find(projectCountNav));
        if (!getText(find(projectCountNav)).contains("No Data")) {
            waitUntilVisibleElement(find(totalProjectIDCount));
            hoverOver(find(totalProjectIDCount));
            String ProjectIDCount = find(totalProjectIDCount).getText();
            System.out.println(ProjectIDCount);
            String splitText = ProjectIDCount.split(" ")[2];
            return Integer.parseInt(splitText);
        }
        return 0;
    }
    public void backToParentWindow() throws Exception{
        click(find(okButton1));
        switchToSpecificWindow(parentWindow1);
        click(find(cancelButton));
        switchToSpecificWindow(parentWindow);
    }


    public void scheduleRunTime() throws Exception{
        click(find(specificTimeRadioButton));
        dropDownValueSelection("Refresh","Monday");
        WebElement hours = find(Hours);
        selectDropdownOptionByIndex(hours,23);
        WebElement minutes = find(Minutes);
        selectDropdownOptionByIndex(minutes,0);
        sleep(4);
        click(find(okButton));
        sleep(2);
        switchToSpecificWindow(parentWindow);

    }


    public void removeMandatoryFields() throws Exception {
        WebElement market = find(marketField);
        setText(market,"");
        WebElement projectStatus = find(projectStatusField);
        setText(projectStatus,"");
        WebElement projectType = find(projectTypeField);
        setText(projectType,"");
        WebElement projectId = find(projectIdField);
        setText(projectId,"");
        WebElement selectTask = find(selectTaskField);
        setText(selectTask,"");
        click(find(okButton));
        acceptAlert();
        click(find(cancelButton));
        acceptAlert();
        switchToSpecificWindow(parentWindow);
    }

    public void changeDeliveryType() throws Exception{
        click(find(deliveryDots));
        parentWindow1 = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton1));
        waitForPageToLoad();
        searchForValue("email","Report Delivery");
        click(find(delieveryReportEmailCheckBox));
        click(find(okButton1));
        switchToSpecificWindow(parentWindow1);
    }
    public Boolean isDataPresentInTable() throws Exception {
        waitUntilVisibleElement(find(runReportTableElement));
        List<WebElement> rows = getTableRows(find(runReportTableElement));
        List<WebElement> cellData = getTableCellsByRow(rows.get(1));
        System.out.println("Data" + cellData.get(1).getText());
        return !cellData.get(1).getText().isEmpty() && !cellData.get(1).getText().equals(" ");
    }
    public ReportSettingsPage searchForReport(String report) throws Exception {
        sleep(4);
        searchForValue(report,"Report Name");
        sleep(5);
        return new ReportSettingsPage(driver);
    }
    public void clickDescending() throws Exception {
        WebElement icon = find(sortButton);
        icon.click();
        find(descButton).click();
        sleep(5);
//        click(find(desButton));
//        sleep(3);
//        click(find(order));
//        sleep(2);
    }
}
