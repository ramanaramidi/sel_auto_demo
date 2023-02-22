package pages.web.Tracker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.List;

public class EdbProducerTrackerPage extends BasePage
{

    public WebDriver driver;

    public EdbProducerTrackerPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    public By tableHeader = By.xpath("//table[@class='hdr']//tr//td");
    public By tableData = By.xpath("//table[@class='obj']//tr");
    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By  searchTypeDropdown  = By.xpath("//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*");
    public String  topDivSearchTypeDropdown = "//div[@class='component item_select'][normalize-space()='textName']";
    public By  selectedOption  = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public By  searchButton = By.xpath("//input[@id='btnSearch0']");
    //public By  ringTrackerTableElement  = By.xpath("//tr[contains(@class,'ev_dhx_skyblue ev_dhx_skyblue rowselected')]//parent::*");
    public By  editRing  = By.xpath("//input[@id='btnEdit0']");


    public boolean isValuePresentInGrid(String columnName) throws Exception {
        waitForPageToLoad();
        sleep(10);
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
            if(findAll(By.tagName("a"),cellDataForTheFirstRow.get(columnToFind),0).size()>0){
                cellData = getText(find(By.tagName("a"),cellDataForTheFirstRow.get(columnToFind)));
            }

        }
        return !cellData.isEmpty() && !cellData.equals(null) && !cellData.equals(" ");
    }

    public int getTableData(String columnName) throws Exception {
        waitForPageToLoad();
        List<WebElement> tableHeaders = findAll(tableHeader);
        scrollToElement(tableHeaders.get(1));
        for(int i = 0; i<tableHeaders.size()-1; i++){
            scrollToElement(tableHeaders.get(i+1));
            if(getText(find(By.className("hdr_label"),tableHeaders.get(i))).contains(columnName))
                return i;
        }
        return -1;
    }

    public String searchForValueInGrid(String columnName,int row) throws Exception {
        waitForPageToLoad();
        fullScreen();
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

    public void searchForValue(String data, String type) throws Exception {
        sleep(5);
        waitUntilVisibleElement(find(editRing));
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitUntilVisibleElement(find(editRing));
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
}
