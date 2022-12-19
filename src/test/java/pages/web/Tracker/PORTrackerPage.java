package pages.web.Tracker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import utility.helper.MiscHelpers;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class PORTrackerPage extends BasePage
{

    public WebDriver driver;

    public PORTrackerPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }
    public By AdminMenu  = By.xpath("//input[@title='Admin Menu']");
    public By editPOR  = By.xpath("//input[@id='btnEdit0']");
    public By  AppCenter  = By.xpath("//div[text()='App Center']");
    public By addPORButton  = By.xpath(" //input[@value='Add']");
    public By PORRequestType  = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[35]//div");
    public By PORRequestQueue  = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[39]");
    public By createdPORBy  = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[68]");
    public By createdPORDate  = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[70]");
    public By LastPORUpdatedBy  = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[72]");
    public By LastPORUpdatedDate  = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[74]");
    public By searchOption  = By.xpath("//input[@id='qsValue0']");
    public By searchButton  = By.xpath("//input[@id='btnSearch0']");
    public By searchTypeDropdown  = By.xpath("(//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*)[1]");
    public String topDivSearchTypeDropdown = "//div[@class='component item_select'][normalize-space()='textName']";
    public By selectedOption  = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public By projectIDClick  = By.xpath("//div[@class='objbox customscroll']//table[@class='obj overlap']//tbody//tr[2]//td[4]//a");
    public By getProjectIDClickNTP = By.xpath("//div[@class='objbox customscroll']//table//tbody//tr[2]//td[4]//a");
    public By projectIDValueText  = By.xpath("//input[@id='idx3']");
    public By okButton  = By.xpath("//input[@id='btnOK']");
    public By totalPORIDCount= By.xpath("//span[@id='gridStat0']");
    public By porCountNav = By.xpath("//div[@id='navRange0']");
    public By tableHeader = By.xpath("//table[@class='hdr']//tr//td");
    public By tableData = By.xpath("//table[@class='obj']//tr");
    public By optionsMenu = By.id("btnoptionsGroupOpener0");
    public By deleteOption = By.xpath("//div[@id='itemDelete0']//div[@class='ibtn_value']");
    public By pORCreatedDate = By.xpath("//div[@class='hdr_cell']//div[text()='POR:Created Date']");

    public By pORCreatedDateSorting= By.xpath("//div[@class='hdr_cell']//div[text()='POR:Created Date']//following-sibling::div");

    public By descOrder= By.xpath("//div[text()='Sort DESC']");

    public By ascOrder= By.xpath("//div[text()='Sort ASC']");
    public By searchData= By.xpath("//div[@class='objbox customscroll']//table//tbody//tr//td[1]");



    public void navigateToAppCenter() throws Exception
    {
        click(find(AdminMenu));
        click(find(AppCenter));
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

    public String searchForValueInGrid(String columnName, String searchByColumn, String searchValue) throws Exception {
        fullScreen();
        int columnToFind = getTableData(columnName);
        int columnValueToMatch = getTableData(searchByColumn);
        List<WebElement> tableContents = findAll(tableData);
        if(columnValueToMatch !=-1 && columnToFind !=-1){
            for (int row = 1; row<tableContents.size(); row++){
                List<WebElement> cellDataForTheRow = findAll(By.tagName("td"),tableContents.get(row));
                scrollToElement(cellDataForTheRow.get(1));
                if(getText(cellDataForTheRow.get(columnValueToMatch)).contains(searchValue)){
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
            }
        }
        return "ERROR OCCURRED";
    }

    public AddPORPage selectEditOption() throws Exception
    {
        waitUntilVisibleElement(find(editPOR));
        click(find(editPOR));
        // sleep(5);
        return new AddPORPage(driver);
    }
    public void deleteSelection() throws Exception {
        fullScreen();
        waitUntilVisibleElement(find(optionsMenu));
        click(find(optionsMenu));
        clickCancelAndAlert(find(deleteOption),"accept");
        sleep(5);
        //waitUntilVisibleElement(find(optionsMenu));
        acceptAlert();
        waitUntilVisibleElement(find(optionsMenu));
    }

    public boolean isValuePresentInGrid(String columnName) throws Exception {
        int columnToFind = getTableData(columnName);
        if(columnToFind == -1){
            System.out.println("COULD NOT FIND THE COLUMN");
            return false;
        }

        List<WebElement> tableContents = findAll(tableData);
        List<WebElement> cellDataForTheFirstRow = findAll(By.tagName("td"),tableContents.get(1));
        scrollToElement(cellDataForTheFirstRow.get(columnToFind));
        String cellData = getText(cellDataForTheFirstRow.get(columnToFind));
        if(cellData.isEmpty()||cellData.equals(" ")||cellData==null){

            cellData = getText(find(By.tagName("a"),cellDataForTheFirstRow.get(columnToFind)));
        }
        return !cellData.isEmpty() && !cellData.equals(null) && !cellData.equals(" ");
    }

    public int getPORIDCount() throws Exception
    {
        waitUntilVisibleElement(find(porCountNav));
        if(!getText(find(porCountNav)).contains("No Data")){
            waitUntilVisibleElement(find(totalPORIDCount));
            hoverOver(find(totalPORIDCount));
            String PORTrackerIDCount=find(totalPORIDCount).getText();
            System.out.println(PORTrackerIDCount);
            return  Integer.parseInt(PORTrackerIDCount);
        }
        return 0;
    }


    public AddPORPage clickAddNewPORButton() throws Exception
    {
        waitUntilVisibleElement(find(addPORButton));
        buttonClick("Add", 1);
        // sleep(15);
        return new AddPORPage(driver);
    }


    public Boolean  isPORStatus(String requestType,String requestQueue) throws Exception
    {
        sleep(30);
        String text=getText(find(PORRequestType));
        System.out.println("Actual POR Request Type value_" +text);
        System.out.println ("Expected POR Request Type Value_" +requestType);

        String PORRequestQueueValue=getText(find(PORRequestQueue));
        System.out.println("Actual POR Request Queue value_" +PORRequestQueueValue);
        System.out.println("Expected POR Request Queue value_" +requestQueue);

        String createdByUsername=getText(find(createdPORBy));
        System.out. println("Site Created by username is _" +createdByUsername);

        //String name = userDetails.getUser();

        String createdBySiteDate=getText(find(createdPORDate));
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formatted=createdBySiteDate.format(String.valueOf(formatter));
        System.out.println("Site Created by Date is _" +formatted);
        String createdDate= MiscHelpers.currentDateTime("MM/dd/yyyy");
        System.out.println("Present Date is_" + createdDate);
        if(createdBySiteDate.equals(createdDate))
        {
            System.out.println("POR  creation Date Verification Done");
        }
        String lastUpdatedByUsername=getText(find(LastPORUpdatedBy));
        System.out.println("Site Last updated by username is _" +lastUpdatedByUsername);

        String lastUpdatedBySiteDate=getText(find(LastPORUpdatedDate));
        DateTimeFormatter formatter1= DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formatted1=lastUpdatedBySiteDate.format(String.valueOf(formatter1));
        System.out.println("Site Last updated by Date is _" +formatted1);

        if(lastUpdatedBySiteDate.equals(createdDate))
        {
            System.out.println ("POR updation Date Verification Done");
        }
        if (!text.equals(requestType) && !PORRequestQueueValue.equals(requestQueue))
        {
            return false;
        }
        else
        {
            return true;
        }

    }


    public void searchForValue(String data, String type) throws Exception
    {
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
    public String projectIDClick() throws Exception
    {
        sleep(5);
        click(find(projectIDClick));
        sleep(5);
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(5);
        String ProjectIDValueText=find(projectIDValueText).getAttribute("title");
        //getValueTitle(projectIDValueText)
        System.out.println("ProjectIDValue_" +ProjectIDValueText);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        return ProjectIDValueText;
    }

    public void sortingASCOrder() throws Exception
    {
        scrollToElement(find(pORCreatedDate));
        hoverOver(find(pORCreatedDate));
        click(find(pORCreatedDateSorting));
        click(find(ascOrder));
        waitUntilVisibleElement(find(searchData));
        scrollToElement(find(pORCreatedDate));

    }
    public void sortingDescOrder() throws Exception
    {
        scrollToElement(find(pORCreatedDate));
        hoverOver(find(pORCreatedDate));
        click(find(pORCreatedDateSorting));
        click(find(descOrder));
        waitUntilVisibleElement(find(searchData));
        scrollToElement(find(pORCreatedDate));
    }
}

