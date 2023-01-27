package pages.web.Tracker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.List;

public class ProcessesPage extends BasePage {

    public ProcessesPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public By  searchTypeDropdown  = By.xpath("//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*");
    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By  searchButton = By.xpath("//input[@id='btnSearch0']");
    public By detailsOption = By.xpath("//input[@id='btnDetails0']");
    public By  selectedOption  = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public String  topDivSearchTypeDropdown = ("//div[@class='component item_select'][normalize-space()='textName']");
    public By importLogTab = By.xpath("//span[@id='tabLabel2']");
    public By editOptionInImportLog = By.xpath("//input[@id='btnEdit2']");
    public By tableHeader = By.xpath("//table[@class='hdr']//tr//td");
    public By tableData = By.xpath("//table[@class='obj']//tr");
    public By okButton = By.xpath("//input[@name='btnOK']");

    String parentWindow;





    public void searchForProcessId(String data, String type) throws Exception{
        // parentWindow = switchToChildWindows();
        //fullScreenChildWindow();
        fullScreen();
        waitUntilVisibleElement(find(searchOption));
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitUntilVisibleElement(find(detailsOption));
        sleep(5);
        click(find(detailsOption));

    }
    public void selectSearchType(String type) throws Exception {
        WebElement searchDropdown = find(searchTypeDropdown);
        click(searchDropdown);
        System.out.println("sss" + searchDropdown);
        if (!find(selectedOption).getText().equals(type)) {
            dropDownButtons(topDivSearchTypeDropdown, type);
        }
    }
    public void goToImportDetails() throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        click(find(importLogTab));
        waitUntilVisibleElement(find(editOptionInImportLog));
    }
    public String searchForValueInGrid(String columnName,int row) throws Exception {
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
    public void backToParentWindow() throws Exception{
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
    }
}
