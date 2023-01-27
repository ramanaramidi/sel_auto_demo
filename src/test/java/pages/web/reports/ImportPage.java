package pages.web.reports;

import commons.constants.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.io.IOException;
import java.util.List;

public class ImportPage extends BasePage {

    public ImportPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By importButton = By.xpath("//input[@value='Import']");
    public By  searchButton = By.xpath("//input[@id='btnSearch0']");
    public By  editProject  = By.xpath("//input[@id='btnEdit0']");
    public By chooseFile = By.xpath("//body[1]/div[10]/div[1]/div[2]/div[1]/div[1]/form[1]/div[1]/fieldset[1]/table[1]/tbody[1]/tr[1]/td[2]");
    public By okButton = By.xpath("//input[@name='btnOK']");
    public By  searchTypeDropdown  = By.xpath("//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*");
    public By  selectedOption  = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public String  topDivSearchTypeDropdown = "//div[@class='component item_select'][normalize-space()='textName']";
    public By siteImportHistory = By.xpath("(//div[@title='Import History'])[2]");
    public By editOption = By.xpath("//input[@id='btnEdit0']");
    public By importLogTab = By.xpath("//span[@id='tabLabel2']");
    public By editOptionInImportLog = By.xpath("//input[@id='btnEdit2']");
    public By closeButton = By.xpath("//input[@id='btnClose0']");
    public By tableData = By.xpath("//table[@class='obj']//tr");
    public By tableHeader = By.xpath("//table[@class='hdr']//tr//td");
    String parentWindow;
    String parentWindow1;
    public void searchForValue(String data, String type) throws Exception
    {
        fullScreen();
        waitUntilVisibleElement(find(searchOption));
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitUntilVisibleElement(find(importButton));
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

    public void clickOnImport() throws Exception {
        waitUntilVisibleElement(find(importButton));
        click(find(importButton));
        sleep(5);
    }

    public boolean importFile() throws Exception {
        String parent1 = switchToChildWindows();
        jsClickById("impFile");
        click(find(chooseFile));
        sleep(5);
        //  Runtime.getRuntime().exec(Constants.SECTOR_IMPORT_EXE);
//        waitUntilVisibleElement(find(chooseFile));
        sleep(10);
        if(findAll(okButton).size()>0){
            click(find(okButton));
            switchToSpecificWindow(parent1);
            return true;
        }
        return false;
    }
    public void goToSiteImportHistory() throws Exception{
        waitForPageToLoad();
        click(find(siteImportHistory));
    }

    public void searchForProcessId(String data, String type) throws Exception{
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        fullScreen();
        waitUntilVisibleElement(find(searchOption));
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitUntilVisibleElement(find(editOption));
        sleep(5);
        click(find(editOption));

    }
    public void goToImportDetails() throws Exception {
        parentWindow1 = switchToChildWindows();
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
        switchToSpecificWindow(parentWindow1);
        click(find(closeButton));
        switchToSpecificWindow(parentWindow);


    }

}
