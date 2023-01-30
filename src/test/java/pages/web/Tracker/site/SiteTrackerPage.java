package pages.web.Tracker.site;

import commons.objects.Site;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pages.BasePage;
import utility.helper.MiscHelpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SiteTrackerPage extends BasePage {
    public WebDriver driver;
    public SiteTrackerPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    Site site;
    public By AdminMenu = By.xpath("//input[@title='Admin Menu']");
    public By AppCenter = By.xpath("//div[text()='App Center']");
    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By searchTypeDropdown = By.xpath("(//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*)[1]");
    public String topDivSearchTypeDropdown = "//div[@class='component item_select'][normalize-space()='textName']";
    public By selectedOption = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public By editSite = By.xpath("//input[@id='btnEdit0']");
    public By searchButton = By.xpath("//*[@id='btnSearch0']");
    // public By siteTrackerTableElement = By.xpath("//tr[contains(@class,'ev_dhx_skyblue ev_dhx_skyblue rowselected')]//parent::*");
    public By siteTrackerTableElement = By.xpath("//tr[contains(@class,'ev_dhx_skyblue ev_dhx_skyblue rowselected')]//parent::*");
    public By siteTrackerTableElement2 = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody");
    public String tableHeaderName = "//div[@class='hdr_label'][normalize-space()='THISISTHETEXT']//ancestor::table//tr//td//div[@class='hdr_label']";
    public String selectedRow = "//tr[@class=' ev_dhx_skyblue rowselected']//div[@class='unlocked'][normalize-space()='THISISTHETEXT']";
    public By siteTrackerTableElement1 = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[13]//div");
    public By buildStatus = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[12]//div");
    public By createdBy = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[59]");
    public By createdDate = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[58]");
    public By LastUpdatedBy = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[62]");
    public By LastUpdatedDate = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[61]");
    public By SpectumCall = By.xpath(" //label[@id='lblidx42']//span");
    public By totalSiteCount =By.xpath("//span[@id='gridStat0']");
    public By tableHeader = By.xpath("//div[@id='gridbox0']//child::div[@class = 'xhdr']//tr[2]");
    public By tableHeader1 = By.xpath("//div[contains(@class,'customscroll')]//child::td[11]//div//a");
    public By tableData = By.xpath("//table[@class='obj overlap']//tr");
    public By singleRowValueSearch = By.xpath("//div[contains(@class,'objbox customscroll')]//table[contains(@class,'obj')]//tr//td");
    public By activering = By.xpath("//td[text()='Active']");
    public By siteCodeSearchButton = By.xpath("//*[@id='btnSearch0']");
    public By siteCodeSearch = By.xpath("//input[@id='qsValue0']");
    public By siteCodeSelectionOKButton = By.xpath("//input[@id='btnOK0']");
    public By applyButton  = By.xpath("//input[@id='btnApply']");
    public By BBU_HUBSites = By.xpath("//*[@id='tab6']");
    public By cancelButton = By.xpath("//*[@id='btnCancel']");
    public By rfSectorTab = By.xpath("//span[@id='tabLabel6']");
    public By addButton = By.xpath("//*[@id='btnAdd6']");
    public By okButton = By.xpath("//*[@id='btnOK']");
    public By generalInfoTab = By.xpath("//span[@id='tabLabel1']");
    public By editButton = By.xpath("//*[@id='btnEdit6']");
    public By editButton1 = By.xpath("//*[@id='btnEdit0']");
    public By bbu_btsTabCounter = By.id("tabCounter6");
    public By okButton1 = By.xpath("//input[@id='btnOK0']");
    public By rfSectorTabCounter = By.id("tabCounter6");
    MiscHelpers miscHelpers;
    String parentWindow;
    public AddSitePage selectEditOption() throws Exception {
        waitUntilVisibleElement(find(editSite));
        sleep(10);
        click(find(editSite));
        sleep(5);
        return new AddSitePage(driver);
    }

//    public String siteStatusvalue() throws Exception
//    {
//        String parent1 = switchToChildWindows();
//        fullScreenChildWindow();
//        Select select=new Select(driver.findElement(By.xpath("//select[@id='idx6']")));
//        WebElement option=select.getFirstSelectedOption();
//        String siteStatusVaue=option.getText();
//        //String siteStatus1 = find(siteStatus).getAttribute("text");
//        System.out.println(siteStatusVaue);
//        click(find(okButton));
//        switchToSpecificWindow(parent1);
//        sleep(5);
//        return siteStatusVaue;
//
//    }


    public boolean checkForPageLoad() throws Exception {
        waitUntilVisibleElement(find(editSite));
        return true;
    }

    public int tableHandle(String columnName) throws Exception {
        fullScreen();
        waitForPageToLoad();
        sleep(10);
        List<WebElement> tableHeaders = findAll(tableHeader);
        // List<WebElement> tableHeaders = findAll(tableHeader1);
        WebElement stillTableHeader = tableHeaders.get(0);
        WebElement slidingTableHeader = tableHeaders.get(1);
        List<WebElement> stillTableHeaderList = findAll(By.className("hdr_label"),stillTableHeader);
        int stillTableColumnCount = stillTableHeaderList.size();
        System.out.println("Header count in still table is "+stillTableColumnCount);
        for(int i = 0;i<stillTableHeaderList.size();i++){

            System.out.println("still col name :: "+getText(stillTableHeaderList.get(i)));
        }
        waitForPageToLoad();
        List<WebElement> slidingTableHeaderList = findAll(By.className("hdr_label"),slidingTableHeader);
        System.out.println("Header count in sliding table is "+slidingTableHeaderList.size());
        int counter = 0;
        waitForPageToLoad();
        for(int i = stillTableColumnCount;i<slidingTableHeaderList.size();i++){
            scrollToElement(slidingTableHeaderList.get(i));
            String data = getText(slidingTableHeaderList.get(i));
            System.out.println("printing table data sliding col name :: "+data);
            if(data.contains(columnName)){
                return counter+stillTableColumnCount;
            }

            else {
                counter++;
                if(data.equals("")||data.isEmpty()||data.equals(null)){
                    scrollToElement(slidingTableHeaderList.get(i));
                    data = getText(slidingTableHeaderList.get(i));
                    System.out.println("printing again*** sliding col name :: "+data);
                    if(data.contains(columnName)){
                        return counter+stillTableColumnCount;
                    }
                }
            }
        }
        return -1;
    }

    public String getCellValue(int columnCount) throws Exception {
        waitForPageToLoad();
        sleep(5);
        List<WebElement> singleRowCellValues= findAll(singleRowValueSearch);
        scrollToElement(singleRowCellValues.get(columnCount-1));
        String cellData = getText(singleRowCellValues.get(columnCount));
        if(cellData.equals("")||cellData.isEmpty()||cellData.equals(null)){
            System.out.println("not a direct find");
            cellData = getText(find(By.tagName("div"),singleRowCellValues.get(columnCount)));
            if(cellData.equals("")||cellData.isEmpty()||cellData.equals(null)){
                System.out.println("not a div find");
                return "null";
            }
        }
        System.out.println("The Value u r looking for "+cellData);
        return cellData;
    }

    public String isRingCount() throws Exception {
        waitUntilVisibleElement(find(totalSiteCount));
        String ringCodeCount=getText(find(totalSiteCount));
        return ringCodeCount;
    }/*

    public void searchForValue(String data, String type) throws Exception {
        waitForPageToLoad();
        waitUntilVisibleElement(find(searchOption));
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        sleep(3);
    }*/
    public void searchForValue(String data, String type) throws Exception    {
        fullScreen();
        waitForPageToLoad();
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitForPageToLoad();
        sleep(3);
    }

    public void searchSiteTracker(String data, String type) throws Exception {
        sleep(5);
        waitUntilVisibleElement(find(searchButton));
        waitUntilVisibleElement(find(editButton1));
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(editButton1));
    }
    public void searchSiteTracker1(String data, String type) throws Exception {
        sleep(5);
        waitUntilVisibleElement(find(editButton1));
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
    }

    //    fun isDataPresentInTable1(): Boolean {
//        val rows = getTableRows(siteTrackerTableElement)
//        val cellData = getTableCellsByRow(rows[1])
//        println("Data" + cellData[1].text)
//        return !cellData[1].text.isNullOrEmpty() && !cellData[1].text.equals(" ")
//    }
    public Boolean isDataPresentInTable() throws Exception {
        waitUntilVisibleElement(find(siteTrackerTableElement));
        List<WebElement> rows = getTableRows(find(siteTrackerTableElement));
        List<WebElement> cellData = getTableCellsByRow(rows.get(1));
        System.out.println("Data" + cellData.get(1).getText());
        return !cellData.get(1).getText().isEmpty() && !cellData.get(1).getText().equals(" ");
    }

    public Boolean isSiteStatus1(String status,String siteBuildStatus) throws Exception {
        waitUntilVisibleElement(find(siteTrackerTableElement1));
        String text=getText(find(siteTrackerTableElement1));
        System.out.println("Actual Site Status value_" +text);
        System.out.println ("Expected Site Status Value_" +status);

        String buildStatusActualValue=getText(find(buildStatus));
        System.out.println("Actual Build Status value_" +buildStatusActualValue);
        System.out.println("Expected Build Status value_" +siteBuildStatus);

        String createdByUsername=getText(find(createdBy));
        System.out.println("Site Created by username is _" +createdByUsername);

        String createdBySiteDate=getText(find(createdDate));
        System.out.println("Site Created by Date is _" +createdBySiteDate);
        String createdDate=systemDateNow();
        if(createdBySiteDate.equals(createdDate));
        {
            System.out.println ("Date Prompted Successfully");
        }
        String lastUpdatedByUsername=getText(find(LastUpdatedBy));
        System.out.println("Site Last updated by username is _" +lastUpdatedByUsername);

        String lastUpdatedBySiteDate=getText(find(LastUpdatedDate));
        System.out.println("Site Last updated by Date is _" +lastUpdatedBySiteDate);

        if(lastUpdatedBySiteDate.equals(createdDate))
        {
            System.out.println ("Date Prompted Successfully");
        }

        return !(!text.equals(status) && !buildStatusActualValue.equals(siteBuildStatus));

    }

    public int getTableHeaderCount(String headerText) throws Exception {
        return getColumnCountByName(tableHeaderName, headerText);
    }

    public int isSiteStatus(String status) throws Exception {
        String selectedRowPath = selectedRow.replace("THISISTHETEXT", status);

        System.out.println("*$" + getElementCount(selectedRowPath));
        return getElementCount(selectedRowPath);
    }

    public Boolean isSiteStatus(String status, int columnNumber) throws Exception {
        List<WebElement> tableRows = getTableRows(find(siteTrackerTableElement2));
        Iterator<WebElement> allRows = tableRows.iterator();
        System.out.println("rows" + tableRows.size());
        allRows.next();
        while (allRows.hasNext()) {
            List<WebElement> cells = getTableCellsByRow(allRows.next());
//            for (cell in cells) {
//                System.out.println("^^" + cell.text);
//            }
            System.out.println("^^" + status);
            System.out.println("^^" + cells.get(columnNumber).getText());
            if (!cells.get(columnNumber).getText().equals(status));
            return false;
        }
        return true;
    }
    public void selectSearchType(String type) throws Exception {
        WebElement searchDropdown = find(searchTypeDropdown);
        click(searchDropdown);
        System.out.println("sss" + searchDropdown);
        if (!find(selectedOption).getText().equals(type)) {
            dropDownButtons(topDivSearchTypeDropdown, type);
        }
    }
    public void navigateToAppCenter() throws Exception {
        click(find(AdminMenu));
        click(find(AppCenter));
    }

    public AddSitePage selectAddNewSiteOption() throws Exception {
        waitUntilVisibleElement(find(editSite));
        sleep(20);
        buttonClick("Add", 1);
        //sleep(10);
        return new AddSitePage(driver);
    }

    public void selectSiteWithRingStatusActive() throws Exception{
        waitUntilVisibleElement(find(editSite));
        click(findAll(activering).get(0));
    }

    public void getSiteClass(String siteType) throws Exception {
        sleep(1);
        Select s = new Select(selectionBoxBySname("S:Site Type").get(0));
        String option = s.getFirstSelectedOption().getText();
        System.out.println("Selected Site Type is -  " + option);

        Select s1 = new Select(selectionBoxBySname("S:Site Class").get(0));
        if (option.equals(siteType)){
            s1.selectByIndex(1);
        }

//        WebElement status = selectionBoxBySname("S:Site Type").get(0);
//        scrollToElement(status);
//        getFirstSelectedOptionInDropdown(status);
//        selectDropdownOption(status, visibleText);

     /*   List<WebElement> siteTypes = selectionBoxBySname("S:Site Type");
        System.out.println("SiteTypes are - " + siteTypes);

        List<WebElement> siteClasses = selectionBoxBySname("S:Site Class");
        System.out.println("SiteClasses are - " + siteClasses);

        for (WebElement e: siteTypes) {
            String site_type = e.getText();
               if (site_type.contains(siteType)) {
                   for (WebElement e1: siteClasses) {
                       e1.getText();
                       e1.click();
                       System.out.println("SiteClasses are - " + e1.getText());
                       selectionBoxBySname("S:Site Class").get(0).click();
                       System.out.println("selected site class is - " + selectionBoxBySname("S:Site Class").get(0));
                   }
                }
            }*/

    }

    public void setSiteType(String siteType) throws Exception {
        switchToChildWindows();
        fullScreenChildWindow();
        sleep(2);
        dropDownValueSelection("S:Site Type",siteType);
        System.out.println("SiteType is - " + siteType);
        sleep(3);
    }

    public String verifyingBBU_BTSSectors_Hub(String siteId) throws Exception {
        switchToChildWindows();
        fullScreenChildWindow();
        sleep(5);
        WebElement element1 = inputBoxDataBySname("S:DAS OEM");
        scrollToElement(element1);
        sleep(4);
        dropDownValueSelection("S:Site Category","BBU/BTS");
        click(find(applyButton));
        sleep(6);
        dropDownDotsClick("S:Hub Site ID");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(4);
        click(find(siteCodeSearch));
        setText(find(siteCodeSearch),"SA01101C");
        click(find(siteCodeSearchButton));
        radioButtonClick("S:Site Code", "SA01101C");
        sleep(3);
        quickClick(find(siteCodeSelectionOKButton));
        switchToSpecificWindow(parent2);
        click(find(applyButton));
        sleep(10);
        click(pencilIcon("S:Hub Site ID").get(0));
        String parent3 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(2);
        String BBU_BTSSitesCount = inputBoxDataBySname("S:Count of BBU/BTS Sites Connected to Hub").getAttribute("origval");
        System.out.println("BBU/BTS Sites count at HUB - " + BBU_BTSSitesCount);
        click(find(okButton));
        sleep(3);
        switchToSpecificWindow(parent3);
        sleep(2);
        find(rfSectorTab).click();
        sleep(2);
        click(find(addButton));
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        inputBoxDataBySname("SEC:Sector ID", siteId+"_"+MiscHelpers.getRandomNumber(2)+"LAA");
        click(find(applyButton));
        sleep(4);
        dropDownValueSelection("SEC:Sector Status","Provision");
        click(find(applyButton));
        sleep(4);
        click(find(okButton));
        sleep(2);
        switchToSpecificWindow(parent3);
        // sleep(3);
        click(find(generalInfoTab));
        String count = inputBoxDataBySname("S:Count of BBU/BTS Sectors (New or Provision)").getText();
        System.out.println("RFSector Provision status sectors count " + count);
        click(find(rfSectorTab));
        click(find(editButton));
        sleep(3);
        switchToChildWindows();
        fullScreenChildWindow();
        dropDownValueSelection("SEC:Sector Status","On-Air");
        click(find(applyButton));
        click(find(okButton));
        sleep(4);
        switchToSpecificWindow(parent3);
        click(find(generalInfoTab));
        String count1 = inputBoxDataBySname("S:Count of BBU/BTS Sectors (On-Air)").getText();
        System.out.println("RFSECTOR On-Air status sectors count " + count1);
        return BBU_BTSSitesCount;
    }
    public String checkBBU_HUBSitesCount() throws Exception {
        sleep(3);
        WebElement element1 = inputBoxDataBySname("S:DAS OEM");
        scrollToElement(element1);
        sleep(4);
        dropDownValueSelection("S:Site Category","BBU/BTS");
        click(find(applyButton));
        sleep(10);
        dropDownDotsClick("S:Hub Site ID");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(4);
        click(find(siteCodeSearch));
        setText(find(siteCodeSearch),"AMRI010A");
        click(find(siteCodeSearchButton));
        radioButtonClick("S:Site Code", "AMRI010A");
        sleep(3);
        quickClick(find(siteCodeSelectionOKButton));
        switchToSpecificWindow(parent2);
        click(find(applyButton));
        sleep(10);
        click(pencilIcon("S:Hub Site ID").get(0));
        String parent3 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(2);
        click(find(BBU_HUBSites));
        sleep(2);
      /*  inputTextBox("qsValue0", "AMRI010C");
        click(find(searchButton));*/
        //  searchForValue("AMRI010C","S:Site Code");
        //  searchSiteTracker("AMRI010C","S:Site Code");
        //   sleep(5);
        // click(find(editButton1));
        //   find(By.linkText("AMRI010")).click();
        //    sleep(3);
        //  click(find(cancelButton));
        //   sleep(3);
        String bbuBtsCount = getText(find(bbu_btsTabCounter));
        System.out.println("BBU/BTS Sites Count is: " + bbuBtsCount);
        return bbuBtsCount;
    }
    public void switchToTracker(String parentWindow) throws Exception {
        // waitUntilVisibleElement(find(okButton));
        sleep(8);
        click(find(okButton));
        sleep(5);
        switchToSpecificWindow(parentWindow);
    }
    public String switchToProjectPage(){
        parentWindow = switchToChildWindows();
        fullScreen();
        sleep(3);
        return parentWindow;
    }
    public int getTableData(String columnName) throws Exception {
        List<WebElement> tableHeaders = findAll(tableHeader);
        scrollToElement(tableHeaders.get(1));
        for(int i = 0; i<tableHeaders.size(); i++){
            scrollToElement(tableHeaders.get(i));
            if(getText(find(By.className("hdr_label"),tableHeaders.get(i))).contains(columnName)){
                scrollToElement(tableHeaders.get(1));
                return i;
            }
        }
        return -1;
    }
    public boolean isValuePresentInGrid(String columnName) throws Exception {
        waitForPageToLoad();
        int columnToFind = getTableData(columnName);
        List<WebElement> tableContents = findAll(tableData);
        List<WebElement> cellDataForTheFirstRow = findAll(By.tagName("td"),tableContents.get(1));
        String cellData = getText(cellDataForTheFirstRow.get(columnToFind));
        return !cellData.isEmpty() && !cellData.equals(null) && !cellData.equals(" ");
    }

    /*   public List<String> getTableValues(String fieldName, int index) throws Exception{
           String parent = switchToChildWindows();
           fullScreenChildWindow();
         //  applyChanges();
         //  scrollToElement(selectionBoxBySname("SEC:Vendor - Planned").get(0));
           sleep(2);
          *//* dropDownDotsClick(fieldName);
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton1));
        sleep(5);*//*
        String tableList = tableDataList(index);
        List<String> switchList = getDocumentTextListByXpathJs(tableList);
        for (WebElement e switchList) {

        }
       *//* List<String> msmList = new ArrayList<>();
        System.out.println(switchList.size());
        for (int i = 0; i < switchList.size(); i++) {
            msmList.add(addString+switchList.get(i));
        }
        System.out.println("++++"+msmList);*//*
        sleep(2);
        click(find(okButton1));
        switchToSpecificWindow(parent);
        return switchList;
    }*/
    public void applyChanges() throws Exception {
        click(find(applyButton));
        sleep(5);
        waitUntilVisibleElement(find(okButton));
    }
    public void displaySector_SiteLink() throws Exception {
        sleep(2);
        WebElement element1 = inputBoxDataBySname("S:DAS OEM");
        scrollToElement(element1);
        sleep(4);
        WebElement siteCategory = selectionBoxBySname("S:Site Category").get(0);
        String site = siteCategory.getText();
        System.out.println("Site Category is - " + site);
        if (site.equalsIgnoreCase("BBU/BTS")) {
            find(rfSectorTab).click();
        }
        sleep(2);
        find(rfSectorTab).click();
        //in order to create sectors by clicking Add button navigate to RFSectorTracker--write code in test case not here
        click(find(addButton));
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        inputBoxDataBySname("SEC:Sector ID", "00TESTOD_"+MiscHelpers.getRandomNumber(2)+"LAA");
        click(find(applyButton));
        sleep(4);
        dropDownValueSelection("SEC:Sector Status","Provision");
        click(find(applyButton));
        sleep(4);
        switchToSpecificWindow(parent1);

    }

    public int getSite_SectorCount() throws Exception {
        String sectorsCount = getText(find(rfSectorTabCounter));
        System.out.println("BBU/BTS Sites Count is: " + sectorsCount);
        return Integer.parseInt(sectorsCount);
    }

}
