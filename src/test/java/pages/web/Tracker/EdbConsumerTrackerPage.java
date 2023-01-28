package pages.web.Tracker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import java.util.List;
public class EdbConsumerTrackerPage extends BasePage {

    public WebDriver driver;
    public EdbConsumerTrackerPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public By editButton = By.xpath("//input[@id='btnEdit0']");
    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By searchButton = By.xpath("//input[@id='btnSearch0']");
    public By searchTypeDropdown = By.xpath("//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*");
    public By selectedOption = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public String topDivSearchTypeDropdown = "//div[contains(@class,'component item_select')][normalize-space()='textName']";
    public By tableHeader = By.xpath("//table[@class='hdr']//tr//td");
    public By tableData = By.xpath("//table[@class='obj']//tr");
    public void searchForValue(String data, String type) throws Exception {
        sleep(5);
        waitUntilVisibleElement(find(editButton));
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitUntilVisibleElement(find(editButton));
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


}