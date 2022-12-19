package pages.web.Tracker.site;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.Iterator;
import java.util.List;

public class SiteTrackerPage extends BasePage {
    public WebDriver driver;
    public SiteTrackerPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }


    public By AdminMenu = By.xpath("//input[@title='Admin Menu']");
    public By AppCenter = By.xpath("//div[text()='App Center']");
    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By searchTypeDropdown = By.xpath("(//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*)[1]");
    public String topDivSearchTypeDropdown = "//div[@class='component item_select'][normalize-space()='textName']";
    public By selectedOption = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public By editSite = By.xpath("//input[@id='btnEdit0']");
    public By searchButton = By.xpath("//input[@id='btnSearch0']");
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
    public By singleRowValueSearch = By.xpath("//div[contains(@class,'objbox customscroll')]//table[contains(@class,'obj')]//tr//td");
    public By activering = By.xpath("//td[text()='Active']");

    public AddSitePage selectEditOption() throws Exception {
        waitUntilVisibleElement(find(editSite));
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
        List<WebElement> tableHeaders = findAll(tableHeader);
        WebElement stillTableHeader = tableHeaders.get(0);
        WebElement slidingTableHeader = tableHeaders.get(1);
        List<WebElement> stillTableHeaderList = findAll(By.className("hdr_label"),stillTableHeader);
        int stillTableColumnCount = stillTableHeaderList.size();
        System.out.println("Header count in still table is "+stillTableColumnCount);
        for(int i = 0;i<stillTableHeaderList.size();i++){

            System.out.println("still col name :: "+getText(stillTableHeaderList.get(i)));
        }
        List<WebElement> slidingTableHeaderList = findAll(By.className("hdr_label"),slidingTableHeader);
        System.out.println("Header count in sliding table is "+slidingTableHeaderList.size());
        int counter = 0;
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
    }

    public void searchForValue(String data, String type) throws Exception {
        waitUntilVisibleElement(find(searchOption));
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitUntilVisibleElement(find(searchOption));
        fullScreen();
    }

    public void searchSiteTracker(String data, String type) throws Exception {
        waitUntilVisibleElement(find(searchButton));
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
        waitUntilVisibleElement(find(searchTypeDropdown));
        click(find(searchTypeDropdown));
        System.out.println("sss" + searchTypeDropdown);
        if (!find(selectedOption).getText().equals(type)) {
            waitUntilVisibleElement(find(editSite));
            dropDownButtons(topDivSearchTypeDropdown, type);
        }
    }

    public void navigateToAppCenter() throws Exception {
        click(find(AdminMenu));
        click(find(AppCenter));
    }

    public AddSitePage selectAddNewSiteOption() throws Exception {
        waitUntilVisibleElement(find(editSite));
        sleep(10);
        buttonClick("Add", 1);
        //sleep(10);
        return new AddSitePage(driver);
    }

    public void selectSiteWithRingStatusActive() throws Exception{
        waitUntilVisibleElement(find(editSite));
        click(findAll(activering).get(0));
    }

}
