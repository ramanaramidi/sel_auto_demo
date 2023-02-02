package pages.web.Tracker;

import commons.constants.Constants;
import commons.objects.Ring;
import commons.objects.Sector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import rest.sector.SectorHelper;
import utility.helper.MiscHelpers;

import java.util.ArrayList;
import java.util.List;

public class RFSectorPage extends BasePage
{
    public WebDriver driver;
    SectorHelper sectorHelper = new SectorHelper();

    public RFSectorPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public String RNC = "TEST" + MiscHelpers.getRandomString(3, true).toUpperCase();

    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By  searchButton = By.xpath("//input[@id='btnSearch0']");
    public By  editRFSector  = By.xpath("//input[@id='btnEdit0']");
    public By firstOption = By.xpath("//tr[@class=' ev_dhx_skyblue rowselected']//input");
    public By singleRowValueSearch = By.xpath("//div[contains(@class,'objbox customscroll')]//table[contains(@class,'obj')]//tr//td");
    public By  searchTypeDropdown  = By.xpath("//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*");
    public By  selectedOption  = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public String  topDivSearchTypeDropdown = "//div[contains(@class,'component item_select')][normalize-space()='textName']";

    public By RFSectorIDCount= By.xpath("//span[@id='gridStat0']");
    public By RFSectorCountNav = By.xpath("//div[@id='navRange0']");
    public By tableHeaderD = By.xpath("//table[@class='hdr']//tr//td");
    public By tableHeader = By.xpath("//div[@id='gridbox0']//child::div[@class = 'xhdr']//tr[2]");
    public By tableData = By.xpath("//table[@class='obj']//tr//td");
    public By tableDetails = By.xpath("//table[@class='obj']//tr");
    public By okButton = By.xpath("//input[@id='btnOK']");
    public By cancel  = By.xpath("//input[@id='btnCancel']");
    public By technologyField = By.xpath("//*[@id='idx7']");
    public By okButton1 = By.xpath("//input[@id='btnOK0']");
    public By AddButton = By.xpath("//input[@id='btnAdd0']");
    public By EditButton = By.xpath("//input[@id='btnEdit0']");
    public By CancelButton = By.xpath("//input[@id='btnCancel']");
    public By SectorIDTextBox = By.xpath("//input[@id='idx2']");
    public By SiteCodeTextboxSearchButton = By.xpath("//input[@id='btnSearch0']");
    public By SiteCodeSearchTextbox = By.xpath("//input[@id='qsValue0']");
    public By SiteCodeRadioButton = By.xpath("//input[@id='rb1245875993']");
    public By ApplyButton = By.xpath("//input[@id='btnApply']");
    public By ringCodeSearchButton = By.xpath("//input[@id='btnSearch0']");
    public By ringCodeSearch = By.xpath(" //input[@id='qsValue0']");
    public By gnodeBID_field = By.xpath("//*[@id='idx49_disp']");
    public By editButton = By.xpath("//input[@id='btnEdit0']");
    public By bscAndRncPlanned = By.xpath("(//label[contains(text(),'SEC:BSC / RNC - Planned')]//following::td//div//div//input)[1]");
    public By bscAndRncPlannedDots = By.xpath("(//label[contains(text(),'SEC:BSC / RNC - Planned')]//following::td//div//div//input)[2]");

    public By racPlannedField = By.xpath("(//label[contains(text(),'SEC:RAC - Planned')]//following::td//div//div//input)[1]");
    public By siteCodeField = By.xpath("(//td//label[contains(text(),'S:Site Code')]//following::td//div//div//input)[1]");
    public By switchPlannedField = By.xpath("(//td//label[contains(text(),'SEC:Switch - Planned')]//following::td//div//div//input)[1]");
    public By mscPlannedField = By.xpath("(//td//label[contains(text(),'SEC:MSC / MGW / MME / AMF - Planned')]//following::td//div//div//input)[1]");
    public By lacTacPlannedField = By.xpath("(//td//label[contains(text(),'SEC:LAC / TAC - Planned')]//following::td//div//div//input)[1]");
    public By projectIdAcsInfo = By.xpath("//label[text()='SEC:Project ID']");
    public By bscField = By.xpath("//label[text()='SEC:BSC / RNC - Planned']");
    public By mscField = By.xpath("//label[text()='SEC:MSC / MGW / MME / AMF - Planned']");
    public By mscpencil = By.xpath("//label[text()='SEC:MSC / MGW / MME / AMF - Planned']/../following-sibling::td/div[2]");
    public By racTab = By.xpath("//div[@title='RAC:RAC']");
    public By racSearchButton = By.xpath("//input[@placeholder='Search for']/../preceding-sibling::div/div/input");
    public By racAddButton = By.xpath("//input[@value='Add']");
    public By tacTab = By.xpath("//div[@title='LAC:LAC/TAC']");
    public By threeDotClickForRfSectorPage = By.xpath("//input[@id='btnoptionsGroupOpener0']");
    public By DeleteButton = By.xpath("//div[text()='Delete']");
    public By firstOptionCheckBox = By.xpath("//tr[@class=' ev_dhx_skyblue rowselected']//label");
    public By cancleButton = By.xpath("//input[@id='btnCancel']");

    public By CheckBox = By.xpath("//table//*[text()='SEC:Small Cell e911 Auto Provision - Request']/parent::td/following-sibling::td//label");
    public By UspsAddressValidation = By.xpath("//textarea[@sname='SEC:USPS Address Validation - Results']");
    public By applyButton  = By.xpath("//input[@id='btnApply']");
    public By siteID = By.xpath("//*[@id='idx12_disp']");
    public By addButton = By.xpath("//*[@id='btnAdd0']");
    public By cancel_Button = By.xpath("//*[@id='btnCancel']");
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
    public By servingSectorsTab = By.xpath("//*[@id='tabName7']");
    public By check = By.xpath("//*[@id='lblcb1001860524']");
    public By check1 = By.xpath("//*[@id='lblcb1001888634']");
    public By siteLink = By.xpath("//*[@id='gridbox0']/div[2]/table/tbody/tr[2]/td[3]");
    public By DASTab = By.xpath("//div[@id='tabName15']");
    public By projectSearchButton = By.xpath("//input[@id='btnSearch0']");
    public By projectSearchTextBox = By.xpath("//input[@id='qsValue0']");
    public By mainLogo1 = By.xpath("//*[@id='mainLogo']");
    public By ProjectTracker = By.xpath("//div[text()='Project Tracker']");
    public By PJSectorsTab = By.xpath("//div[@title='PJ:Sectors']");
    public By sectorIdTextArea = By.xpath("(//td//label[contains(text(),'PJ:Sector IDs')]//following::td//following::textarea)[1]");
    String parentWindow;
    String parentWindow1;
    String parentWindow2;
    String LAC_ID;
    String RAC_ID;

    public void searchForValue(String data, String type) throws Exception {
        fullScreen();
        System.out.println(data);
        sleep(10);
        waitUntilVisibleElement(find(searchOption));
        clearInputBoxByElementAndSendKeys(find(searchOption));
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitUntilVisibleElement(find(editRFSector));
        System.out.println("search button clicked");
        sleep(10);
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

    public boolean isValuePresentInGrid(String columnName) throws Exception {

        int columnToFind = getTableData(columnName);
        List<WebElement> tableContents = findAll(tableData);
        List<WebElement> cellDataForTheFirstRow = findAll(By.tagName("td"), tableContents.get(1));
        String cellData = getText(cellDataForTheFirstRow.get(columnToFind));
        return !cellData.isEmpty() && !cellData.equals(null) && !cellData.equals(" ");
    }

    public int getColumnNumber(String columnName) throws Exception {
        List<WebElement> tableHeaders = findAll(tableHeaderD);
        for (int i = 0; i < tableHeaders.size(); i++) {
            scrollToElement(tableHeaders.get(i));
            if (getText(find(By.className("hdr_label"), tableHeaders.get(i))).contains(columnName))
                return i;
        }
        return -1;
    }

    public int getColumnNumberExact(String columnName) throws Exception {
        List<WebElement> tableHeaders = findAll(tableHeaderD);
        for (int i = 0; i < tableHeaders.size(); i++) {
            scrollToElement(tableHeaders.get(i));
            if (getText(find(By.className("hdr_label"), tableHeaders.get(i))).equals(columnName))
                return i;
        }
        return -1;
    }

    public int getTableData(String columnName) throws Exception {
        List<WebElement> tableHeaders = findAll(tableHeader);
        for (int i = 0; i < tableHeaders.size(); i++) {
            scrollToElement(tableHeaders.get(i));
            if (getText(find(By.className("hdr_label"), tableHeaders.get(i))).contains(columnName))
                return i;
        }
        return -1;
    }

    public String searchForValueInGrid(String columnName, String searchByColumn, String searchValue) throws Exception {
        int columnToFind = getColumnNumber(columnName);
        int columnValueToMatch = getColumnNumber(searchByColumn);
        List<WebElement> tableContents = findAll(tableDetails);
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
                    return cellValue;
                }
            }
        }
        return "ERROR OCCURRED";
    }

    public int getRFSectorIDCount() throws Exception {
        waitUntilVisibleElement(find(RFSectorCountNav));
        if (!getText(find(RFSectorCountNav)).contains("No Data")) {
            waitUntilVisibleElement(find(RFSectorIDCount));
            hoverOver(find(RFSectorIDCount));
            String RFTrackerIDCount = find(RFSectorIDCount).getText();
            System.out.println(RFTrackerIDCount);
            return Integer.parseInt(RFTrackerIDCount);
        }
        return 0;
    }

    public RFSectorPage addNewRFSector() throws Exception {
        waitUntilVisibleElement(find(AddButton));
        buttonClick("Add", 1);
        return new RFSectorPage(driver);
    }

    public RFSectorPage editRFSector() throws Exception {
        waitUntilVisibleElement(find(EditButton));
        buttonClick("Edit", 3);
        return new RFSectorPage(driver);
    }

    public boolean MandatoryFieldsAlert(String RFSectorID) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton));
        click(find(okButton));
        if (!isAlertPresent())
            return false;
        acceptAlert();
        inputBoxDataBySname("SEC:Sector ID", RFSectorID);
        click(find(ApplyButton));
        if (!isAlertPresent())
            return false;
        acceptAlert();
        click(find(CancelButton));
        if (!isAlertPresent())
            return false;
        acceptAlert();
        switchToSpecificWindow(parent1);
        waitUntilVisibleElement(find(EditButton));
        return true;
    }

    public boolean newSectorfromGUI() throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton));
        click(find(CancelButton));
        switchToSpecificWindow(parent1);
        waitUntilVisibleElement(find(EditButton));
        return true;
    }

    public void verifyAddNewRFSector(String RFSectorID, String SiteCode) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton));
        inputBoxDataBySname("SEC:Sector ID", RFSectorID);
        dropDownDotsClick("S:Site Code");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton1));
        click(find(SiteCodeSearchTextbox));
        setText(find(SiteCodeSearchTextbox), SiteCode);
        click(find(SiteCodeTextboxSearchButton));
        sleep(5);
        radioButtonClick("S:Site Code", SiteCode);
        click(find(okButton1));
        switchToSpecificWindow(parent2);
        waitUntilVisibleElement(find(ApplyButton));
        dropDownValueSelection("SEC:Sector Type", "Macro");
        click(find(ApplyButton));
        sleep(5);
        waitUntilVisibleElement(find(ApplyButton));
        click(find(okButton));
        switchToSpecificWindow(parent1);
        waitUntilVisibleElement(find(EditButton));
    }

    public void validateAddNewRFSector(String RFSectorID, String SiteCode) throws Exception {
        sleep(10);
        inputBoxDataBySname("SEC:Sector ID", RFSectorID);
        sleep(3);
        //String sector_id = inputBoxDataBySname("SEC:Sector ID").getAttribute("value");
        dropDownDotsClick("S:Site Code");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton1));
        click(find(SiteCodeSearchTextbox));
        setText(find(SiteCodeSearchTextbox), SiteCode);
        click(find(SiteCodeTextboxSearchButton));
        sleep(5);
        radioButtonClick("S:Site Code", SiteCode);
        click(find(okButton1));
        switchToSpecificWindow(parent2);
        sleep(4);
        // String site_ID = inputBoxDataBySname("S:Site Code").getText();
        waitUntilVisibleElement(find(ApplyButton));
        dropDownValueSelection("SEC:Sector Type", "Macro");
        click(find(ApplyButton));
        sleep(5);
        acceptAlert();
        sleep(4);
    }

    public boolean AddNewRFSectorLessThan14characters(String SectorID, String SiteCode) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton));
        inputBoxDataBySname("SEC:Sector ID", SectorID);
        System.out.println(SectorID);
        dropDownDotsClick("S:Site Code");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton1));
        click(find(SiteCodeSearchTextbox));
        setText(find(SiteCodeSearchTextbox), SiteCode);
        click(find(SiteCodeTextboxSearchButton));
        sleep(5);
        radioButtonClick("S:Site Code", SiteCode);
        click(find(okButton1));
        switchToSpecificWindow(parent2);
        waitUntilVisibleElement(find(ApplyButton));
        dropDownValueSelection("SEC:Sector Type", "Macro");
        sleep(2);
        click(find(ApplyButton));
        sleep(2);
        if (!isAlertPresent()) {
            sleep(5);
            click(find(CancelButton));
            if (isAlertPresent())
                acceptAlert();
            switchToSpecificWindow(parent1);
            waitUntilVisibleElement(find(EditButton));
            return false;
        } else {
            acceptAlert();
            sleep(5);
            click(find(CancelButton));
            if (isAlertPresent())
                acceptAlert();
            switchToSpecificWindow(parent1);
            waitUntilVisibleElement(find(EditButton));
            return true;
        }
    }
    //Small Cell


    public void changeSectorTypeToSmallCell() throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton));
        dropDownValueSelection("SEC:Sector Type", "Small Cell");
        click(find(ApplyButton));
        sleep(5);
        waitUntilVisibleElement(find(ApplyButton));
        click(find(okButton));
        switchToSpecificWindow(parent1);
        waitUntilVisibleElement(find(EditButton));
    }

    public RFSectorPage selectEditOption() throws Exception {
        waitUntilVisibleElement(find(editButton));
        click(find(editButton));
        sleep(10);
        return new RFSectorPage(driver);
    }

    public String getSectorTechnologyField() throws Exception {
        //  waitForPageToLoad();
       /* parentWindow = switchToChildWindows();
        fullScreen();*/
        sleep(5);
        WebElement element = inputBoxDataBySname("SEC:Technology");
        scrollToElement(element);
        String technology_Field = find(technologyField).getAttribute("origval");
        return technology_Field;
    }

    public boolean verifyIseNodeEnabled() throws Exception {
        WebElement element = inputBoxDataBySname("SEC:Technology");
        scrollToElement(find(gnodeBID_field));
        sleep(5);
        boolean eNode = lockByLabelText("SEC:eNode B ID - Planned", element).isEnabled();
        return eNode;
    }

    public boolean verifyIsgNodeEnabled() throws Exception {
        WebElement element = inputBoxDataBySname("SEC:Technology");
        scrollToElement(find(gnodeBID_field));
        sleep(5);
        boolean gNode = lockByLabelText("SEC:gNode B ID - Planned", element).isEnabled();
        return gNode;
    }

    public void backToTrackerPage() throws Exception {
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
    }

    public void setTextPlannedValues(Sector sector) throws Exception {
        waitUntilVisibleElement(find(ApplyButton));
        inputTextBox("SEC:Cell ID - Planned", sector.plannedCellId);
        dropDownValueSelection("SEC:Coverage Type - Planned", sector.coverageType);
        sleep(2);
        dropDownValueSelection("SEC:Vendor - Planned", sector.plannedVendor);
        click(find(ApplyButton));
        sleep(5);
        fullScreenChildWindow();
        scrollToElement(selectionBoxBySname("SEC:Vendor - Planned").get(0));
        sleep(12);
        dropDownDotsClick("SEC:Switch - Planned");
        String parent = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton1));
        click(find(ringCodeSearch));
        setText(find(ringCodeSearch), sector.plannedSwitch.toUpperCase());
        click(find(ringCodeSearchButton));
        sleep(5);
        waitUntilVisibleElement(find(okButton1));
        click(find(okButton1));
        switchToSpecificWindow(parent);
        fullScreenChildWindow();
        click(find(ApplyButton));
        sleep(10);
        dropDownDotsClick("SEC:MSC / MGW / MME / AMF - Planned");
        switchToChildWindows();
        fullScreenChildWindow();

        waitUntilVisibleElement(find(okButton1));
        sleep(5);
        click(find(firstOption));
        click(find(okButton1));
        //sleep(5);
        switchToSpecificWindow(parent);
        click(find(ApplyButton));
        sleep(10);
        dropDownDotsClick("SEC:LAC / TAC - Planned");
        parent = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton1));
        sleep(5);
        click(find(firstOption));
        click(find(okButton1));
        switchToSpecificWindow(parent);
        click(find(ApplyButton));
    }

    public void setTextPlannedValues_UMTS(Sector sector) throws Exception {
        waitUntilVisibleElement(find(ApplyButton));
        inputTextBox("SEC:Cell ID - Planned", sector.plannedCellId);
        dropDownValueSelection("SEC:Coverage Type - Planned", sector.coverageType);
        sleep(2);
        dropDownValueSelection("SEC:Vendor - Planned", sector.plannedVendor);
        click(find(ApplyButton));
        sleep(5);
        fullScreenChildWindow();
        scrollToElement(selectionBoxBySname("SEC:Vendor - Planned").get(0));
        sleep(12);
        dropDownDotsClick("SEC:Switch - Planned");
        String parent = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton1));
        click(find(ringCodeSearch));
        setText(find(ringCodeSearch), "SNFCCAMM");
        click(find(ringCodeSearchButton));
        sleep(5);
        waitUntilVisibleElement(find(okButton1));
        click(find(okButton1));
        switchToSpecificWindow(parent);
        fullScreenChildWindow();
        click(find(ApplyButton));
        sleep(15);
        dropDownDotsClick("SEC:MSC / MGW / MME / AMF - Planned");
        switchToChildWindows();
        fullScreenChildWindow();

        waitUntilVisibleElement(find(okButton1));
        sleep(5);
        click(find(firstOption));
        click(find(okButton1));
        //sleep(5);
        switchToSpecificWindow(parent);
        click(find(ApplyButton));
        sleep(10);
        dropDownDotsClick("SEC:LAC / TAC - Planned");
        parent = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton1));
        sleep(5);
        click(find(firstOption));
        click(find(okButton1));
        switchToSpecificWindow(parent);
        click(find(ApplyButton));
    }

    public void addNewSectorWithMainDetails(Sector sector) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton));
        inputBoxDataBySname("SEC:Sector ID", sector.sectorId);
        dropDownDotsClick("S:Site Code");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton1));
        click(find(SiteCodeSearchTextbox));
        setText(find(SiteCodeSearchTextbox), sector.siteId);
        click(find(SiteCodeTextboxSearchButton));
        waitUntilVisibleElement(find(okButton1));
        radioButtonClick("S:Site Code", sector.siteId);
        click(find(okButton1));
        switchToSpecificWindow(parent2);
        waitUntilVisibleElement(find(ApplyButton));
        setTextPlannedValues(sector);
        sleep(5);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        waitUntilVisibleElement(find(EditButton));
        sleep(10);
    }

    public int tableHandle(String columnName) throws Exception {
        fullScreen();
        List<WebElement> tableHeaders = findAll(tableHeader);
        WebElement stillTableHeader = tableHeaders.get(0);
        WebElement slidingTableHeader = tableHeaders.get(1);
        List<WebElement> stillTableHeaderList = findAll(By.className("hdr_label"), stillTableHeader);
        int stillTableColumnCount = stillTableHeaderList.size();
        System.out.println("Header count in still table is " + stillTableColumnCount);
        for (int i = 0; i < stillTableHeaderList.size(); i++) {

            System.out.println("still col name :: " + getText(stillTableHeaderList.get(i)));
        }
        List<WebElement> slidingTableHeaderList = findAll(By.className("hdr_label"), slidingTableHeader);
        System.out.println("Header count in sliding table is " + slidingTableHeaderList.size());
        int counter = 0;
        for (int i = stillTableColumnCount; i < slidingTableHeaderList.size(); i++) {
            scrollToElement(slidingTableHeaderList.get(i + 1));
            String data = getText(slidingTableHeaderList.get(i));
            System.out.println("printing table data sliding col name :: " + data);
            if (data.contains(columnName)) {
                System.out.println("The column count is " + counter + stillTableColumnCount);
                return counter + stillTableColumnCount;
            } else {
                counter++;
                if (data.equals("") || data.isEmpty() || data.equals(null)) {
                    scrollToElement(slidingTableHeaderList.get(i));
                    data = getText(slidingTableHeaderList.get(i));
                    System.out.println("printing again*** sliding col name :: " + data);
                    if (data.contains(columnName)) {
                        return counter + stillTableColumnCount;
                    }
                }
            }
        }
        return -1;
    }

    public String getCellValue(int columnCount) throws Exception {
        List<WebElement> singleRowCellValues = findAll(singleRowValueSearch);
        scrollToElement(singleRowCellValues.get(columnCount + 1));
        String cellData = getText(singleRowCellValues.get(columnCount));
        if (cellData.equals("") || cellData.isEmpty() || cellData.equals(null)) {
            System.out.println("not a direct find");
            cellData = getText(find(By.tagName("div"), singleRowCellValues.get(columnCount)));
            if (cellData.equals("") || cellData.isEmpty() || cellData.equals(null)) {
                System.out.println("not a div find");
                return "null";
            }
        }
        System.out.println("The Value u r looking for " + cellData);
        return cellData;
    }

    public void addMainDetails(Sector sector) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton));
        setTextPlannedValues(sector);
        sleep(5);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        waitUntilVisibleElement(find(EditButton));
        sleep(10);
    }

    public boolean setSectorStatus(String status) throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(ApplyButton));
        WebElement sectorStatus = selectionBoxBySname("SEC:Sector Status").get(0);
        selectDropdownOption(sectorStatus, status);
        waitUntilVisibleElement(find(okButton));
        click(find(ApplyButton));
        sleep(2);
        if (isAlertPresent()) {
            acceptAlert();
            click(find(CancelButton));
            sleep(2);
            if (isAlertPresent()) {
                acceptAlert();
            }
            switchToSpecificWindow(parentWindow);
            return false;
        } else {
            click(find(okButton));
            switchToSpecificWindow(parentWindow);
            return true;
        }
    }

    public boolean nccPlannedVerification() throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(ApplyButton));
        WebElement nccplanned = selectionBoxBySname("SEC:NCC - Planned").get(0);
        if (nccplanned.isEnabled()) {
            return true;
        } else return false;
    }

    public boolean bccPlannedVerification() throws Exception {
        waitUntilVisibleElement(find(ApplyButton));
        WebElement bccPlanned = selectionBoxBySname("SEC:BCC - Planned").get(0);
        if (bccPlanned.isEnabled()) {
            return true;
        } else return false;

    }

    public boolean bcchPlannedVerification() throws Exception {
        WebElement bcchplanned = inputBoxDataBySname("SEC:BCCH - Planned");
        if (bcchplanned.isEnabled()) {
            return true;
        } else return false;
    }

    public boolean btsPlannedVerification() throws Exception {
        WebElement btsplanned = inputBoxDataBySname("SEC:BTS - Planned");
        if (btsplanned.isEnabled()) {
            return true;
        } else return false;
    }

    public boolean radiosPlannedVerification() throws Exception {
        WebElement radiosplanned = inputBoxDataBySname("SEC:Radios - Planned");
        if (radiosplanned.isEnabled()) {
            return true;
        } else return false;
    }

    public boolean frequencyHoppingVerification() throws Exception {
        WebElement freequencyHopping = selectionBoxBySname("SEC:Frequency Hopping - Planned").get(0);
        if (freequencyHopping.isEnabled()) {
            return true;
        } else return false;
    }

    public boolean rbltPlannedTacVerification() throws Exception {
        WebElement rbaltPlanned = inputBoxDataBySname("SEC:RBLT - PCM - BCF - Planned");
        if (rbaltPlanned.isEnabled()) {
            return true;
        } else return false;
    }

    public boolean bscPlannedTacVerification() throws Exception {
        WebElement bscPlanned = find(bscAndRncPlanned);
        if (bscPlanned.isEnabled()) {
            return true;
        } else return false;
    }

    public boolean racPlannedTacVerification() throws Exception {
        WebElement racPlanned = find(racPlannedField);
        if (racPlanned.isEnabled()) {
            return true;
        } else return false;
    }

    public RFSectorPage goToRfSector() throws Exception {
        click(find(okButton));
        sleep(10);
        switchToSpecificWindow(parentWindow);
        return new RFSectorPage(driver);
    }

    public boolean siteCodeFieldVerification() throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(cancleButton));
        WebElement sitecode = find(siteCodeField);
        return sitecode.isEnabled();
        // if (sitecode.isEnabled()) {
        //return false;
        // } else return true;
    }

    public boolean sectorIdFieldVerification() throws Exception {
        WebElement sectorcode = inputBoxDataBySname("SEC:Sector ID");
        return sectorcode.isEnabled();
    }

    public boolean sectorTypeVerification() throws Exception {
        WebElement sectortype = selectionBoxBySname("SEC:Sector Type").get(0);
        if (sectortype.isEnabled()) {
            return false;
        } else return true;
    }

    public boolean sectorStatusVerification() throws Exception {
        WebElement sectorstatus = selectionBoxBySname("SEC:Sector Status").get(0);
        if (sectorstatus.isEnabled()) {
            return false;
        } else return true;
    }

    public boolean cellNamePlannedVerification() throws Exception {
        WebElement cellname = inputBoxDataBySname("SEC:Cell Name - Planned");
        return cellname.isEnabled();
    }

    public boolean cellIdPlannedVerification() throws Exception {
        WebElement cellid = inputBoxDataBySname("SEC:Cell ID - Planned");
        return cellid.isEnabled();

    }

    public boolean coverageTypePlannedVerification() throws Exception {
        WebElement coverageType = selectionBoxBySname("SEC:Coverage Type - Planned").get(0);
        if (coverageType.isEnabled()) {
            return false;
        } else return true;
    }

    public boolean vendorPlannedVerification() throws Exception {
        WebElement vendor = selectionBoxBySname("SEC:Vendor - Planned").get(0);
        if (vendor.isEnabled()) {
            return false;
        } else return true;
    }

    public boolean switchPlannedTacVerification() throws Exception {
        WebElement switchPlanned = find(switchPlannedField);
        return switchPlanned.isEnabled();

    }

    public boolean mscPlannedVerification() throws Exception {
        WebElement mscPlanned = find(mscPlannedField);
        return mscPlanned.isEnabled();

    }

    public boolean lacTacPlannedVerification() throws Exception {
        WebElement lacTacPlanned = find(lacTacPlannedField);
        return lacTacPlanned.isEnabled();

    }

    public boolean addVerificationForRfEng() throws Exception {
        if (findAll(AddButton).size() > 0) {
            return false;
        } else return true;
    }

    public boolean deleteOptionVerification() throws Exception {
        waitUntilVisibleElement(find(EditButton));
        click(find(threeDotClickForRfSectorPage));
        if (findAll(DeleteButton).size() > 0) {
            return false;
        } else return true;
    }

    public RFSectorPage goToRfSectorAsSiteDev() throws Exception {
        sleep(10);
        click(find(cancleButton));
        sleep(5);
        switchToSpecificWindow(parentWindow);
        return new RFSectorPage(driver);
    }

    public void addRac() throws Exception {
        sleep(5);
        parentWindow = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton));
        scrollToElement(find(mscField));
        click(find(mscpencil));
        parentWindow1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(cancleButton));
        click(find(racTab));
        waitUntilVisibleElement(find(racSearchButton));
        click(find(racAddButton));
        parentWindow2 = switchToChildWindows();
        waitUntilVisibleElement(find(ApplyButton));
        inputBoxDataBySname("RAC:RAC").sendKeys("0000012345");
        WebElement racDropdown = selectionBoxBySname("RAC:SELECTABLE").get(0);
        selectDropdownOption(racDropdown, "Yes");
        click(find(ApplyButton));
        waitUntilVisibleElement(find(ApplyButton));
        sleep(5);
        // racIdValue();
        //String racId = inputBoxDataBySname("RAC:RAC ID").getAttribute("origval");
        //click(find(okButton));
        // switchToSpecificWindow(parent2);
        // sleep(3);
    }
    public String racIdValue() throws Exception{
        String racId = inputBoxDataBySname("RAC:RAC ID").getAttribute("origval");
        return racId;
    }

    public boolean racIdValidation() throws Exception{
        String racId = inputBoxDataBySname("RAC:RAC ID").getAttribute("origval");
        if(!racId.isEmpty()){
            return true;
        }
        return false;

    }

    public void goToSearchInTable() throws Exception{
        click(find(okButton));
        sleep(5);
        switchToSpecificWindow(parentWindow2);
        sleep(3);
    }

    public String getTableList() throws Exception{
        String tableList = tableDataList(2);
        List<String> switchList = getDocumentTextListByXpathJs(tableList);
        String racList = switchList.toString();
        return racList;
    }
    public void backToRfSectorPage() throws Exception {
        click(find(cancleButton));
        switchToSpecificWindow(parentWindow1);
        click(find(ApplyButton));
        sleep(5);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);

    }

    public void addLac() throws Exception {
        sleep(5);
        parentWindow = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton));
        scrollToElement(find(mscField));
        click(find(mscpencil));
        parentWindow1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(cancleButton));
        // click(find(racTab));
        click(find(tacTab));
        waitUntilVisibleElement(find(racSearchButton));
        click(find(racAddButton));
        parentWindow2 = switchToChildWindows();
        waitUntilVisibleElement(find(ApplyButton));
        inputBoxDataBySname("LAC:LAC/TAC").sendKeys("345");
        WebElement racDropdown = selectionBoxBySname("LAC:SELECTABLE").get(0);
        selectDropdownOption(racDropdown, "Yes");
        click(find(ApplyButton));
        sleep(5);
        //click(find(okButton));
        //switchToSpecificWindow(parent2);
        //sleep(3);
    }
    public String lacIdValue() throws Exception{
        String lacId = inputBoxDataBySname("LAC:LAC ID").getAttribute("origval");
        return lacId;
    }
    public boolean lacIdValidation() throws Exception{
        String lacId = inputBoxDataBySname("LAC:LAC ID").getAttribute("origval");
        if(!lacId.isEmpty()){
            return true;
        }
        return false;

    }

    public String searchForValueInGridExact(String columnName, String searchByColumn, String searchValue) throws Exception {
        int columnToFind = getColumnNumberExact(columnName);
        int columnValueToMatch = getColumnNumberExact(searchByColumn);
        System.out.println("col ::" + columnToFind);
        List<WebElement> tableContents = findAll(tableDetails);
        if (columnValueToMatch != -1 && columnToFind != -1) {
            for (int row = 1; row < tableContents.size(); row++) {
                List<WebElement> cellDataForTheRow = findAll(By.tagName("td"), tableContents.get(row));
                scrollToElement(cellDataForTheRow.get(1));
                String valueOfCell = getText(cellDataForTheRow.get(columnValueToMatch));
                System.out.println("Column Val is :: " + valueOfCell);
                if (valueOfCell.contains(searchValue)) {
                    sleep(5);
                    scrollToElement(cellDataForTheRow.get(columnToFind));
                    String cellValue = getText(cellDataForTheRow.get(columnToFind));
                    if (cellValue.isEmpty()) {
                        cellValue = getText(find(By.tagName("div"), cellDataForTheRow.get(columnToFind)));
                    }
                    return cellValue;
                }
            }
        }
        return "ERROR OCCURRED";
    }

    public void rfSectorAcsInfo() throws Exception {

        sleep(5);
        parentWindow = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton));
        scrollToElement(find(projectIdAcsInfo));
        dropDownDotsClick("SEC:Project ID");
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton1));
        sleep(5);
        click(find(firstOption));
        click(find(okButton1));
        switchToSpecificWindow(parent1);
        dropDownDotsClick("SEC:Node ID(s)");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton1));
        sleep(5);
        click(find(firstOptionCheckBox));
        click(find(okButton1));
        switchToSpecificWindow(parent2);
        inputTextBox("SEC:Fronthaul Circuit ID 1", "TEST");
        inputTextBox("SEC:Fronthaul Circuit ID 2", "TEST123");
        inputTextBox("SEC:Pole Owner", "TESTOWNER1");
        inputTextBox("SEC:Power provider", "TESTPROVIDER1");
        WebElement yearEpuipmentBuild = selectionBoxBySname("SEC:Year Equipment Built").get(0);
        selectDropdownOption(yearEpuipmentBuild, "2020");
    }

    public boolean acsInfoVerification() throws Exception {
        sleep(2);
        click(find(ApplyButton));
        if (!isAlertPresent()) {
            sleep(2);
            return true;
        } else return false;
    }
    public void rfSectorPage() throws Exception{
        sleep(3);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
    }

    public void addBsc() throws Exception {
        sleep(5);
        parentWindow = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton));
        click(find(applyButton));
        waitUntilVisibleElement(find(okButton));
        sleep(10);
        scrollToElement(find(bscField));
        click(find(bscAndRncPlannedDots));
        parentWindow1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton1));
        click(find(AddButton));
        parentWindow2 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(ApplyButton));
        WebElement record = selectionBoxBySname("MSM:Record Type").get(0);
        selectDropdownOption(record, "3_BSC/RNC");
        WebElement region = selectionBoxBySname("MSM:Region").get(0);
        selectDropdownOption(region, "WEST");
        WebElement market = selectionBoxBySname("MSM:Market").get(0);
        selectDropdownOption(market, "SAN FRANCISCO");
        inputBoxDataBySname("MSM:Switch").sendKeys("TESTSANF");
        inputBoxDataBySname("MSM:MSC / MGW / MME / AMF").sendKeys("TESTSMGW");
        inputBoxDataBySname("MSM:BSC/RNC").sendKeys(RNC);
        WebElement selectable = selectionBoxBySname("MSM:Selectable").get(0);
        selectDropdownOption(selectable, "Yes");
        click(find(ApplyButton));
        sleep(5);
    }
    public boolean msmIdValidation() throws Exception{
        String msmId = inputBoxDataBySname("MSM:MSM ID").getAttribute("origval");
        if(!msmId.isEmpty()){
            return true;
        }
        return false;

    }
    public boolean bscVerification() throws Exception {
        String msmId = inputBoxDataBySname("MSM:MSM ID").getAttribute("origval");
        if(msmId.contains(RNC)){
            return true;
        } else return false;
    }
    public String msmIdValue() throws Exception{
        String msmId = inputBoxDataBySname("MSM:MSM ID").getAttribute("origval");
        return msmId;
    }
    public void goToMsmIdTablePage() throws Exception{
        click(find(okButton));
        switchToSpecificWindow(parentWindow2);
        sleep(3);
    }
    public void backToRfSectorPageAsAlphaUser() throws Exception {
        click(find(okButton1));
        switchToSpecificWindow(parentWindow1);
        click(find(ApplyButton));
        sleep(5);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
    }

    public boolean getCoverageTypeField(String fieldName) throws Exception {
        waitForPageToLoad();
        WebElement element = inputBoxDataBySname(fieldName);
        String sector_Field = element.getAttribute("value");
        return sector_Field.contains("Indoor Micro") || sector_Field.contains("Indoor Pico");
    }
    public boolean verifyFieldDropDown(String field) throws Exception {
        waitForPageToLoad();
        WebElement status = selectionBoxBySname(field).get(0);
        String dropdownName = getFirstSelectedOptionInDropdown(status);
        return dropdownName.equals("Indoor Micro")||dropdownName.equals("Indoor Pico");
    }
    public boolean verifyE911DropDown(String field) throws Exception {
        waitForPageToLoad();
        WebElement status = selectionBoxBySname(field).get(0);
        scrollToElement(status);
        String dropdownName = getFirstSelectedOptionInDropdown(status);
        System.out.println("e911 Auto Provision Results:" +dropdownName);
        return dropdownName.equals("Ready for Submission to Upsilon")||dropdownName.equals("USPS - Validation Failed");
    }
    public boolean uspsAddressValidation() throws Exception {
        waitForPageToLoad();
        WebElement element = find(UspsAddressValidation);
        scrollToElement(find(UspsAddressValidation));
        String sector_Field = element.getAttribute("value");
        System.out.println("USPS Address Validaion Results:" +sector_Field);
        return sector_Field.contains("Pass")||sector_Field.contains("Failed");
    }
    public void setSectorFieldName(String fieldName,String drpValue) throws Exception {
        WebElement element = selectionBoxBySname(fieldName).get(0);
        waitUntilVisibleElement(element);
        selectDropdownOption(element,drpValue);
        click(find(ApplyButton));
        sleep(5);
    }
    public boolean validateLockIsEnabled(String secInfo) throws Exception{
        waitForPageToLoad();
        WebElement lock = lockByLabel(secInfo);
        System.out.println(lock.getAttribute("class"));
        if(lock.getAttribute("class").contains("lock-icon locked")){
            return false;
        }else {
            return true;
        }
    }
    public void clickCheckBox() throws Exception{
        waitUntilVisibleElement(find(CheckBox));
        scrollToElement(find(CheckBox));
        click(find(CheckBox));
        sleep(5);
        //click(find(ApplyButton));
    }
    public void changeZipCode(String zip) throws Exception{
        parentWindow = switchToChildWindows();
        fullScreen();
        WebElement zipCode = inputBoxDataBySname("SEC:Zip");
        scrollToElement(zipCode);
        clearInputBoxByElementAndSendKeys(zipCode);
        inputTextBox("SEC:Zip",zip);
        click(find(ApplyButton));
        sleep(10);
        waitUntilVisibleElement(find(okButton));
    }
    public void sendUspsZip() throws Exception{
        WebElement zipCode = inputBoxDataBySname("SEC:Zip");
        scrollToElement(zipCode);
        clearInputBoxByElementAndSendKeys(zipCode);
        sleep(2);
        WebElement element = inputBoxDataBySname("SEC:USPS Zip");
        String uspsZip = element.getAttribute("origval");
        inputTextBox("SEC:Zip",uspsZip);
        click(find(ApplyButton));
        sleep(10);
        waitUntilVisibleElement(find(okButton));
    }
    public String getZipFieldValue(String fieldName) throws Exception {
        waitForPageToLoad();
        //waitUntilVisibleElement(find(okButton));
        WebElement element = inputBoxDataBySname(fieldName);
        //waitUntilVisibleElement(element);
        scrollToElement(element);
        //sleep(2);
        String sector_Field = element.getAttribute("origval");
        return sector_Field;
    }
    public void goToRfTrackerPage() throws Exception {
        waitUntilVisibleElement(find(CancelButton));
        find(CancelButton).click();
        sleep(5);
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
    }
    public void applyChanges() throws Exception {
        click(find(applyButton));
        sleep(5);
        waitUntilVisibleElement(find(okButton));
    }
    public List<String> getTableValues(String fieldName, int index,String addString) throws Exception{
        String parent = switchToChildWindows();
        fullScreenChildWindow();
        applyChanges();
        scrollToElement(selectionBoxBySname("SEC:Vendor - Planned").get(0));
        sleep(2);
        dropDownDotsClick(fieldName);
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton1));
        sleep(5);
        String tableList = tableDataList(index);
        List<String> switchList = getDocumentTextListByXpathJs(tableList);
        List<String> msmList = new ArrayList<>();
        System.out.println(switchList.size());
        for (int i = 0; i < switchList.size(); i++) {
            msmList.add(addString+switchList.get(i));
        }
        System.out.println("++++"+msmList);
        sleep(2);
        click(find(okButton1));
        switchToSpecificWindow(parent1);
        fullScreenChildWindow();
        click(find(okButton));
        sleep(10);
        switchToSpecificWindow(parent);
        return msmList;
    }
    public boolean getFieldValue(String labelName) throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        click(find(applyButton));
        sleep(10);
        waitUntilVisibleElement(find(okButton));
        WebElement fieldValue = inputBoxDataLabel(labelName);
        scrollToElement(fieldValue);
        sleep(2);
        String cellVal = getDocumentTextByIdJs(fieldValue.getAttribute("id"));
        boolean cellValue = getDocumentTextByIdJs(fieldValue.getAttribute("id")).equals("");
        System.out.println(cellVal);
        return cellValue;
    }
    public boolean setBscValue(String fieldName ) throws Exception{
        parentWindow = switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton));
        WebElement fieldValue = inputBoxDataLabel(fieldName);
        scrollToElement(fieldValue);
        sleep(2);
        dropDownDotsClick(fieldName);
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton1));
        click(find(firstOption));
        click(find(okButton1));
        sleep(5);
        switchToSpecificWindow(parent1);
        click(find(ApplyButton));
        sleep(10);
        WebElement fieldValue1 = inputBoxDataLabel(fieldName);
        scrollToElement(fieldValue1);
        sleep(3);
        String cellVal = getDocumentTextByIdJs(fieldValue1.getAttribute("id"));
        boolean cellValue = getDocumentTextByIdJs(fieldValue1.getAttribute("id")).equals("");
        System.out.println(cellVal);
        return cellValue;
    }
    public void setSector(Sector sector,String plannedMME,String plannedLacTac ) throws Exception{
        sector.plannedCellId = "123";
        sector.coverageType = "Indoor";
        sector.plannedVendor = "Commscope";
        sector.plannedSwitch = "SAN FRANCISCO_TESTSANF";
        sector.plannedMME = plannedMME;
        sector.plannedLacTac = plannedLacTac;
    }
    public void setSectorWithoutLac(Sector sector,String plannedMME ) throws Exception{
        sector.plannedCellId = "123";
        sector.coverageType = "Indoor";
        sector.plannedVendor = "Commscope";
        sector.plannedSwitch = "SAN FRANCISCO_TESTSANF";
        sector.plannedMME = plannedMME;
    }

    public String getCellPlannedNameLTE() throws Exception {
        String SectorId = inputBoxDataBySname("SEC:Sector ID").getAttribute("value");
        String siteId = find(siteID).getAttribute("value");
        String suffix = SectorId.substring(9, 11);
        System.out.println("suffix is" + suffix);
        String prefix = SectorId.substring(11, 13);
        System.out.println("prefix is" + prefix);
        switch (prefix) {
            case "LA":
                prefix = "L";
                break;
            case "LC":
                prefix = "C";
                break;
            case "LD":
                prefix = "D";
                break;
            case "LE":
                prefix = "E";
                break;
            case "LF":
                prefix = "F";
                break;
            case "LG":
                prefix = "G";
                break;
            case "LH":
                prefix = "R";
                break;
            case "LK":
                prefix = "T";
                break;
            case "LP":
                prefix = "B";
                break;
            default:
                prefix = SectorId.substring(11, 13);
        }
        return prefix + siteId + suffix;
    }

    public String getCellPlannedName5G() throws Exception {
        String SectorId = inputBoxDataBySname("SEC:Sector ID").getAttribute("value");
        String siteId = find(siteID).getAttribute("value");
        String suffix = SectorId.substring(9, 11);
        System.out.println("suffix is" + suffix);
        String prefix = SectorId.substring(11, 13);
        System.out.println("prefix is" + prefix);
        switch (prefix) {
            case "NE":
                prefix = "K";
                break;
            case "NF":
                prefix = "I";
                break;
            case "NG":
                prefix = "Q";
                break;
            case "NH":
                prefix = "H";
                break;
            case "NK":
                prefix = "A";
                break;
            case "NL":
                prefix = "O";
                break;
            case "NM":
                prefix = "M";
                break;
            case "NN":
                prefix = "N";
                break;
            case "NP":
                prefix = "J";
                break;
            default:
                prefix = SectorId.substring(11, 13);
        }
       /* if (prefix.startsWith("NF")) {
            prefix = "I";
        } else {
            prefix = SectorId.substring(11, 13);
        }*/
        return prefix + siteId + suffix;
    }

    public String getCellPlannedNameUMTS() throws Exception {
        String SectorId = inputBoxDataBySname("SEC:Sector ID").getAttribute("value");
        String siteId = find(siteID).getAttribute("value");
        String suffix = SectorId.substring(9, 11);
        System.out.println("suffix is" + suffix);
        String prefix = SectorId.substring(11, 13);
        System.out.println("prefix is" + prefix);
        if (prefix.startsWith("UA")) {
            prefix = "U";
        } else {
            prefix = SectorId.substring(11, 13);
        }
        return prefix + siteId + suffix;
    }

    public String getCellPlannedNameNBIOT() throws Exception {
        String SectorId = inputBoxDataBySname("SEC:Sector ID").getAttribute("value");
        String siteId = find(siteID).getAttribute("value");
        String suffix = SectorId.substring(9, 11);
        System.out.println("sectorIDSubString1 is" + suffix);
        String prefix = SectorId.substring(11, 13);
        System.out.println("sectorIDSubString2 is" + prefix);
        switch (prefix) {
            case "TA":
                prefix = "Z";
                break;
            case "TD":
                prefix = "X";
                break;
            case "TE":
                prefix = "W";
                break;
            case "TP":
                prefix = "Y";
                break;
            case "TR":
                prefix = "V";
                break;
            default:
                prefix = SectorId.substring(11, 13);
        }
      /*  if (prefix.startsWith("TP")) {
            prefix = "Y";
        } else {
            prefix = SectorId.substring(11, 13);
        }*/
        return prefix + siteId + suffix;
    }

    public boolean getCellNamePlanned(String type, Sector sector) throws Exception {
        sleep(5);
        String cellNamePlanned = inputBoxDataBySname("SEC:Cell Name - Planned").getAttribute("value");
        System.out.println("Cell Name Planned " + cellNamePlanned);
        String derivedCellPlannedName;
        switch (type) {
            case "LTE":
                derivedCellPlannedName = getCellPlannedNameLTE();
                System.out.println("derivedCellPlannedName" + derivedCellPlannedName);
                break;
            case "5G":
                derivedCellPlannedName = getCellPlannedName5G();
                break;
            case "NBIOT":
                derivedCellPlannedName = getCellPlannedNameNBIOT();
                break;
            case "UMTS":
                derivedCellPlannedName = getCellPlannedNameUMTS();
                break;
            default:
                derivedCellPlannedName = "HAVING ERRORS";
        }
        if (cellNamePlanned.equalsIgnoreCase(derivedCellPlannedName))
            return true;
        else
            return false;
    }

    public boolean getCellNamePlannedNull() throws Exception {
        String technology_Field = getSectorTechnologyField();
        String cellNamePlanned = inputBoxDataBySname("SEC:Cell Name - Planned").getAttribute("value");
        if (!(technology_Field.equals("LTE")) && (!(technology_Field.equals("5G")) && (!(technology_Field.equals("NBIOT")) && (!(technology_Field.equals("UMTS")))))) {
            cellNamePlanned.isEmpty();
            return true;
        } else {
            return false;
        }
    }

    public boolean getCGI_Name(Sector sector) throws Exception {
        // String parent = switchToChildWindows();
        String technology_Field = getSectorTechnologyField();
        String CGI_Planned = inputBoxDataBySname("SEC:CGI - Planned").getAttribute("value");
        if ((technology_Field.equals("LTE")) || (technology_Field.equals("NBIOT"))) {
            setTextPlannedValues(sector);
            sleep(6);
            conCatCGINameFields_LTE_NBIOT(sector);
            return true;
        } else if ((technology_Field.equals("UMTS")) || (technology_Field.equals("GSM"))) {
            setTextPlannedValues_UMTS(sector);
            sleep(6);
            conCatCGINameFields_UMTS_GSM();
            return true;
        } else if (technology_Field.equals("5G")) {
            setTextPlannedValues(sector);
            sleep(6);
            conCatCGINameFields_5G(sector);
            return true;
        }
        return false;
    }

   /* public void addGNodeDetails(String siteId) throws Exception {
        String parent = switchToChildWindows();
        fullScreenChildWindow();
        click(find(addButton));
        sleep(5);
        switchToChildWindows();
        fullScreen();
        sleep(5);
        //Add details here cabinetid....
        inputBoxDataBySname("NBID:NodeB Cabinet ID").sendKeys("1234_" + siteId + "_" + siteId);
        inputBoxDataBySname("NBID:Cabinet").sendKeys("4321");
        inputBoxDataBySname("NBID:Cabinet").sendKeys(siteId);
        dropDownValueSelection("NBID:NodeB Type", "gNodeB");
        inputBoxDataBySname("NBID:NodeB ID").sendKeys("345");
        click(find(ApplyButton));
        sleep(4);
        click(find(okButton));
        switchToSpecificWindow(parent);

    }*/

    public boolean conCatCGINameFields_LTE_NBIOT(Sector sector) throws Exception {
        sleep(5);
        /*switchToChildWindows();
        fullScreenChildWindow();*/
        String cellID = inputBoxDataBySname("SEC:Cell ID - Planned").getAttribute("value");
        System.out.println("value for cellID is: " + cellID);

        List<WebElement> fieldMCC = selectionBoxBySname("SEC:MCC - Planned");
        String MCC = getFirstSelectedOptionInDropdown(fieldMCC.get(0));
        System.out.println("value for fieldMCC is: " + MCC);

        List<WebElement> fieldMNC = selectionBoxBySname("SEC:MNC - Planned");
        String MNC = getFirstSelectedOptionInDropdown(fieldMNC.get(0));
        System.out.println("value for fieldMNC is: " + MNC);

        Sector gNodeBID = sectorHelper.createNodeAndAssignToSector(sector);
        String gNodeB_id = gNodeBID.nodeInitialID;
        System.out.println("value for gNodeBID is: " + gNodeB_id);
        sleep(3);
        int n2 = 256;
        int e_node_256 = (Integer.parseInt(gNodeB_id)) * n2;
        System.out.println("value for e_node_256 is: " + e_node_256);

        String fields_MCC_MNCConcatValue = fieldMCC.toString() + "-" + fieldMNC.toString();
        System.out.println("value for fields_MCC_MNCConcatValue is: " + fields_MCC_MNCConcatValue);

        String fieldCGIName_LTE_NBIOT = fields_MCC_MNCConcatValue + (e_node_256 + Integer.parseInt(cellID));
        System.out.println("value for fieldCGIName_5G is: " + fieldCGIName_LTE_NBIOT);

        String CGI_Planned = inputBoxDataBySname("SEC:CGI - Planned").getText();
        System.out.println("CGI_Planned value for LTE_NBIOT is: " + CGI_Planned);
        if (CGI_Planned.equals(fieldCGIName_LTE_NBIOT)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean conCatCGINameFields_UMTS_GSM() throws Exception {
        sleep(5);
        String CGI_Planned = inputBoxDataBySname("SEC:CGI - Planned").getAttribute("value");
        String fieldMCC = selectionBoxBySname("SEC:MCC - Planned").get(0).getAttribute("value");
        String fieldMNC = selectionBoxBySname("SEC:MNC - Planned").get(0).getAttribute("value");
        String fieldLAC = inputBoxDataBySname("SEC:LAC / TAC - Planned").getAttribute("value");
        String cellID = inputBoxDataBySname("SEC:Cell ID - Planned").getAttribute("value");
        String fieldCGIName_UMTS_GSM = fieldMCC + fieldMNC + fieldLAC + cellID;
        if (CGI_Planned.equals(fieldCGIName_UMTS_GSM)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean conCatCGINameFields_5G( Sector sector) throws Exception {
        sleep(5);
        String cellID = inputBoxDataBySname("SEC:Cell ID - Planned").getAttribute("value");
        System.out.println("value for cellID is: " + cellID);

        List<WebElement> fieldMCC = selectionBoxBySname("SEC:MCC - Planned");
        String MCC = getFirstSelectedOptionInDropdown(fieldMCC.get(0));
        System.out.println("value for fieldMCC is: " + MCC);

        String fieldMNC = selectionBoxBySname("SEC:MNC - Planned").get(0).getText();

        // String MNC = getFirstSelectedOptionInDropdown(fieldMNC.get(0));
        System.out.println("value for fieldMNC is: " + fieldMNC);

        Sector gNodeBID = sectorHelper.createNodeAndAssignToSector(sector);
        String gNodeB_id = gNodeBID.nodeInitialID;
        System.out.println("value for gNodeBID is: " + gNodeB_id);
        sleep(3);
        int n2 = 4096;
        int g_node_4096 = (Integer.parseInt((gNodeB_id))) * n2;
        System.out.println("value for g_node_4096 is: " + g_node_4096);

        String fields_MCC_MNCConcatValue = fieldMCC.toString() + "-" + fieldMNC.toString();
        System.out.println("value for fields_MCC_MNCConcatValue is: " + fields_MCC_MNCConcatValue);

        String fieldCGIName_5G = fields_MCC_MNCConcatValue + (g_node_4096 + Integer.parseInt(cellID));
        System.out.println("value for fieldCGIName_5G is: " + fieldCGIName_5G);
        sleep(5);
        String CGI_Planned = inputBoxDataBySname("SEC:CGI - Planned").getAttribute("value");
        System.out.println("CGI_Planned value for 5G is: " + CGI_Planned);
        if (CGI_Planned.equals(fieldCGIName_5G)) {
            return true;
        } else {
            return false;
        }
    }

    public String getGnodeValue() throws Exception {
        sleep(10);
        dropDownDotsClick("SEC:gNode B ID - Planned");
        sleep(3);
        String parent = switchToChildWindows();
        sleep(3);
        //  click(find(gNodeValue));
        click(find(okButton1));
        sleep(5);
        switchToSpecificWindow(parent);
        sleep(5);
        scrollToElement(find(gnodeBID_field));
        String gNodeBID = inputBoxDataBySname("SEC:gNode B ID - Planned").getAttribute("value");
        System.out.println("CGI_Planned value for 5G is: " + gNodeBID);
        click(find(ApplyButton));
        sleep(5);
        return gNodeBID;
    }

    public boolean getSiteId(String RFSectorID, String siteId) throws Exception {
        sleep(5);
        boolean sector_SiteId = RFSectorID.startsWith(siteId);
        System.out.println("Sector SiteId while Creation: " + sector_SiteId);
        String Id = RFSectorID.substring(0,8);
        if (siteId.equals(Id)) {
            return true;
        } else {
            return false;
        }
    }
    public String switchToProjectPage(){
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
    public String getTechnologyField() throws Exception {
        waitForPageToLoad();
        parentWindow = switchToChildWindows();
        fullScreen();
        scrollToElement(find(technologyField));
        String technology_Field = find(technologyField).getAttribute("value");
        return technology_Field;
    }

    public void displayNodeSectors() throws Exception {
        sleep(4);
        WebElement element = inputBoxDataBySname("SEC:Pole Owner");
        scrollToElement(element);
        sleep(4);
        dropDownDotsClick("SEC:Node ID(s)");
        String parent = switchToChildWindows();
        fullScreenChildWindow();
        sleep(4);
//        click(find(SiteCodeSearchTextbox));
//        setText(find(SiteCodeSearchTextbox),"00TESTOA");
//        click(find(SiteCodeTextboxSearchButton));
//        click(find(check));
//        sleep(2);
        click(find(SiteCodeSearchTextbox));
        setText(find(SiteCodeSearchTextbox),"5TC1903A");
        click(find(SiteCodeTextboxSearchButton));
        click(find(check1));
        sleep(2);
        quickClick(find(okButton1));
        switchToSpecificWindow(parent);
        click(find(applyButton));
        sleep(10);
        click(pencilIcon("SEC:Node ID(s)").get(0));
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        //WebElement element1 = inputBoxDataBySname("S:DAS OEM");
        //scrollToElement(element1);
        //sleep(4);
        WebElement siteCategory = selectionBoxBySname("S:Site Category").get(0);
        scrollToElement(siteCategory);
        String selectedOption = getFirstSelectedOptionInDropdown(siteCategory);
        System.out.println("Selected Option in drop down is -" + selectedOption);
        if (selectedOption.equalsIgnoreCase("Node")){
            click(find(servingSectorsTab));
            sleep(3);
        }
        else {
            switchToSpecificWindow(parent1);
        }
    }

    public String displaySector_ProjectLink() throws Exception {
        sleep(3);
        WebElement element = inputBoxDataBySname("SEC:Pole Owner");
        scrollToElement(element);
        sleep(4);
        dropDownDotsClick("SEC:Project ID");
        String parent = switchToChildWindows();
        fullScreenChildWindow();
        sleep(4);
        click(find(projectSearchTextBox));
        setText(find(projectSearchTextBox),"00TESTOA-0002037960");
        click(find(projectSearchButton));
        radioButtonClick("PJ:Project ID", "00TESTOA-0002037960");
        click(find(okButton1));
        switchToSpecificWindow(parent);
        sleep(3);
        click(pencilIcon("SEC:Project ID").get(0));
        String parent1 =  switchToChildWindows();
        fullScreenChildWindow();
        click(find(DASTab));
        waitForPageToLoad();
        String sectorIds = find(sectorIdTextArea).getAttribute("value");
        System.out.println("For this Project the SectorIDs are - " + sectorIds);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        return sectorIds;
    }
    public boolean sectorIdValidations() throws Exception{
        sleep(5);
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton));
        WebElement sectorId = inputBoxDataBySname("SEC:Sector ID");
        String value = sectorId.getAttribute("origval");
        String splitValue = value.split("_")[1];
        if(splitValue.length()==5) {
            return true;
        }
        return false;
    }
    public boolean sectorIdValidationsForSiteId(String siteId) throws Exception{
        WebElement sectorId = inputBoxDataBySname("SEC:Sector ID");
        String value = sectorId.getAttribute("origval");
        String splitText = value.split("_")[0];
        if(splitText.contains(siteId)){
            return true;
        } else return false;

    }

    public boolean sectorIndetifierVerification() throws Exception {

        WebElement sectorIdentifier = inputBoxDataBySname("SEC:Sector Identifier");
        String value = sectorIdentifier.getAttribute("origval");
        if (!value.isEmpty()) {
            return true;
        }
        return false;
    }
    public boolean enterPriseSmallCellVerification() throws Exception {

        WebElement sectorIdentifier = inputBoxDataBySname("SEC:Enterprise Small Cell Radio Node #");
        String value = sectorIdentifier.getAttribute("origval");
        if (!value.isEmpty()) {
            return true;
        }
        return false;
    }

    public void getPJ_Sectors() throws Exception {
        String parent0 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(3);
        WebElement element = inputBoxDataBySname("SEC:Pole Owner");
        scrollToElement(element);
        sleep(4);
        dropDownDotsClick("SEC:Project ID");
        String parent = switchToChildWindows();
        fullScreenChildWindow();
        sleep(4);
        click(find(projectSearchTextBox));
        setText(find(projectSearchTextBox),"00TESTOA-0002037960");
        click(find(projectSearchButton));
        radioButtonClick("PJ:Project ID", "00TESTOA-0002037960");
        click(find(okButton1));
        switchToSpecificWindow(parent);
        sleep(3);
        click(pencilIcon("SEC:Project ID").get(0));
        String parent1 =  switchToChildWindows();
        fullScreenChildWindow();
        click(find(PJSectorsTab));
        // searchForValue("00TESTOA-0002070764","SEC:Project ID");
        // searchForValue("00TESTOA_A1GPV","SEC:Sector ID");
        click(find(okButton));
        sleep(2);
        switchToSpecificWindow(parent1);
        click(find(okButton));
        sleep(2);
        switchToSpecificWindow(parent0);
        sleep(2);
    }
    public void clickMainLogo() throws Exception {
        click(find(mainLogo1));
    }
    public ProjectTrackerPage clickingProjectTracker() throws Exception {
        waitUntilVisibleElement(find(ProjectTracker));
        click(find(ProjectTracker));
        return new ProjectTrackerPage(driver);
    }
}
