package pages.web.Tracker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import utility.helper.MiscHelpers;

import java.util.List;

public class ESRTrackerPage extends BasePage {

    public WebDriver driver;

    public ESRTrackerPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
    public By tableHeader = By.xpath("//table[@class='hdr']//tr//td");
    public By tableData = By.xpath("//table[@class='obj']//tr");
    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By  searchButton = By.xpath("//input[@id='btnSearch0']");
    public By  searchTypeDropdown  = By.xpath("//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*");
    public By  selectedOption  = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public String  topDivSearchTypeDropdown = "//div[@class='component item_select'][normalize-space()='textName']";
    public By EditButton = By.xpath("//input[@id='btnEdit0']");
    public By applyButton  = By.xpath("//input[@id='btnApply']");
    public By cancelButton = By.xpath("//input[@id='btnCancel']");
    public By okButton  = By.xpath("//input[@id='btnOK']");
    public By mainLogo = By.xpath("//img[@id='clientLogo']");
    public By hashLink = By.xpath("//td/a[contains(text(),'Description')]//following::a[1]");
    public By userNameText = By.xpath("//*[@id='topPanelUserName']");
    public By phoneNumber = By.xpath("//input[@name='phoneNumber']");
    public By mainLogo1 = By.xpath("//*[@id='mainLogo']");
    public By welcomePage = By.xpath("//u[text()='Welcome to MagentaBuilt!']");
    public By documentEFile = By.xpath("//label[contains(text(),'D:Document eFile')]");
    public By fieldInfo = By.xpath("//div[text()='Field Info']");
    public By fieldHistory = By.xpath("//div[text()='Field History']");
    public By imageSettingTab = By.xpath("//span[text()='Image Settings']");
    public By imageHeight = By.xpath("//a[contains(text(),'Image Height')]");
    public By componentFieldHistory = By.xpath("//label[text()='Component Field History']");
    public By getCellValue1 = By.xpath("//tr[@class=' ev_dhx_skyblue rowselected']//child::td[3]");
    public By getCellValue2 = By.xpath("//tr[@class=' odd_dhx_skyblue']//child::td[3]");
    public By refresh = By.xpath("//div[@class='hdr_cell']//div[text()='Refresh']");
    public By refreshSorting = By.xpath("//div[@class='hdr_cell']//div[text()='Refresh']//following-sibling::div");
    public By descOrder= By.xpath("//div[text()='Sort DESC']");
    public By AppCenter = By.xpath("//div[text()='App Center']");

    public By ascOrder= By.xpath("//div[text()='Sort ASC']");
    public By searchData= By.xpath("//div[@class='objbox customscroll']//table//tbody//tr//td[1]");
    public By mainMenuSticky = By.xpath("//select[@name='mainMenuSticky']");
    public By ntpDocumentField = By.xpath("//label[text()='PJ:Additional NTP Documents [Doc]']");
    public By label_NTPConstruction = By.xpath("//span[text()='NTP Construction']");
    public String reportFormatDropDown = "//select[@sname='Report Format']/option";
    public String fieldHistoryImages = "//div[contains(@class,'customscroll')]//child::td//table//a";

    String parentWindow;
    public void searchForValue(String data, String type) throws Exception
    {
        fullScreen();
        waitForPageToLoad();
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitForPageToLoad();
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
    public ESRTrackerPage selectEditOption() throws Exception {
        waitForPageToLoad();
        waitUntilVisibleElement(find(EditButton));
        sleep(5);
        buttonClick("Edit", 3);
        return new ESRTrackerPage(driver);
    }
    public ESRTrackerPage selectAddOption() throws Exception {
        waitForPageToLoad();
        waitUntilVisibleElement(find(EditButton));
        buttonClick("Add", 1);
        return new ESRTrackerPage(driver);
    }
    public boolean isLinkClickable() throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        scrollToElement(find(hashLink));
        find(hashLink).click();
        String parent1 = switchToChildWindows();
        fullScreen();
        String title = getTitle();
        boolean Flag =false;
        if(title.startsWith("Edit Label")){
            Flag =true;
        }
        find(okButton).click();
        switchToSpecificWindow(parent1);
        find(cancelButton).click();
        switchToSpecificWindow(parentWindow);
        return Flag;
    }
    public void setStickyMainMenu(String visibleText) throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        scrollToElement(find(mainMenuSticky));
        selectDropdownOption(find(mainMenuSticky),visibleText);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
    }

    public void updatePhoneNumber() throws Exception{
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        String lastDigits = MiscHelpers.getRandomNumber(7);
        setText(find(phoneNumber),"+1-123-"+lastDigits);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
    }
    public boolean getPhoneNumber() throws Exception{
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        String phoneNumberField = find(phoneNumber).getAttribute("value");
        System.out.println("Updated Phone Number is:: "+phoneNumberField);
        boolean cellValue = phoneNumberField.equals("");
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        return cellValue;
    }
    public void updatePhoneNumberExistingUser() throws Exception{
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        String lastDigits = MiscHelpers.getRandomNumber(7);
        setText(find(phoneNumber),"+1-123-"+lastDigits);
        click(find(applyButton));
        waitForPageToLoad();

    }
    public boolean getPhoneNumberExistingUser() throws Exception{
        String phoneNumberField = find(phoneNumber).getAttribute("value");
        System.out.println("Updated Phone Number is:: "+phoneNumberField);
        boolean cellValue = phoneNumberField.equals("");
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        return cellValue;
    }
    public void clickAppCenter() throws Exception{
        fullScreen();
        waitForPageToLoad();
        click(find(AppCenter));
    }
    public boolean verifyWelcomePage() throws Exception{
        click(find(mainLogo));
        driver.getTitle();
        System.out.println(driver.getTitle());
        if(find(welcomePage).isDisplayed()){
            return true;
        }else return false;
    }
    public boolean verifyComponentFieldHistory() throws Exception{
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        click(find(documentEFile));
        click(find(fieldInfo));
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        click(find(imageSettingTab));
        click(find(imageHeight));
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        boolean Flag = false;
        if(find(componentFieldHistory).isDisplayed()){
            Flag = true;
        }
        click(find(cancelButton));
        switchToSpecificWindow(parent2);
        click(find(cancelButton));
        switchToSpecificWindow(parent1);
        click(find(cancelButton));
        switchToSpecificWindow(parentWindow);
        return Flag;
    }
    public void sortingASCOrder() throws Exception {
        scrollToElement(find(refresh));
        hoverOver(find(refresh));
        click(find(refreshSorting));
        click(find(ascOrder));
        waitUntilVisibleElement(find(searchData));
        scrollToElement(find(refresh));
    }
    public void sortingDescOrder() throws Exception
    {
        scrollToElement(find(refresh));
        hoverOver(find(refresh));
        click(find(refreshSorting));
        click(find(descOrder));
        waitUntilVisibleElement(find(searchData));
        scrollToElement(find(refresh));
    }
    public boolean verifySortingOrder(boolean set_SmallToBig) throws Exception {
        waitForPageToLoad();
        scrollToElement(find(getCellValue1));
        String cellValue1 = find(getCellValue1).getText();
        System.out.println(cellValue1);
        scrollToElement(find(getCellValue2));
        String cellValue2 = find(getCellValue2).getText();
        System.out.println(cellValue2);
        int compare = cellValue1.compareTo(cellValue2);
        System.out.println(compare);
        if (cellValue1.equals(cellValue2)) {
            System.out.println(cellValue1 + " is equals to " + cellValue2);
            return true;
        }
        else {
            if (set_SmallToBig) {
                if (compare < 0){
                    return true;
                }
            }
            else if (compare > 0) {
                return true;
            }return false;
        }
    }
    public ESRTrackerPage goToNTPConstructionTab() throws Exception {
        parentWindow =switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        scrollToElement(find(label_NTPConstruction));
        sleep(8);
        click(find(label_NTPConstruction));
        return new ESRTrackerPage(driver);
    }
    public String getImagesListFieldHistory() throws Exception{
        waitForPageToLoad();
        scrollToElement(find(ntpDocumentField));
        click(find(ntpDocumentField));
        click(find(fieldHistory));
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(5);
        List<String> imageList = getDocumentTextListByXpathJs(fieldHistoryImages);
        int size = imageList.size();
        System.out.println("Total Images Uploaded::"+size);
        String imagesUploaded = imageList.toString();
        System.out.println(imagesUploaded);
        click(find(cancelButton));
        switchToSpecificWindow(parent1);
        click(find(cancelButton));
        switchToSpecificWindow(parentWindow);
        return imagesUploaded;
    }
    public List<String> validateDataTypePDF() throws Exception{
        parentWindow = switchToChildWindows();
        fullScreen();
        List<String> reportFormatList = getDocumentTextListByXpathJs(reportFormatDropDown);
        System.out.println(reportFormatList);
        click(find(cancelButton));
        switchToSpecificWindow(parentWindow);
        return reportFormatList;
    }
}
