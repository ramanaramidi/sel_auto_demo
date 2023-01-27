package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utility.helper.WebHelper;

import java.time.LocalDateTime;
import java.util.List;

public class BasePage {
    public WebDriver driver;
    public WebHelper webHelper ;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        webHelper = new WebHelper(driver);
    }

    public void launch(String url) {
        driver.navigate().to(url);
    }

    public  List<WebElement> notificationPanelData(String date, String status) throws Exception {
        return webHelper.notificationPanelData(date,status);
    }

    public  List<WebElement> notificationPanelReportLink(String reportName) throws Exception {
        return webHelper.notificationPanelReportLink(reportName);
    }

    public WebElement find(By locataorQuery) throws Exception {
        return webHelper.find(locataorQuery, null,0);
    }

    public WebElement find(By locataorQuery, WebElement rootElement) throws Exception {
        return webHelper.find(locataorQuery, rootElement,0);
    }

    public void fullScreen()  {
        webHelper.fullScreen();
    }

    public List<WebElement> findAll(By locataorQuery) throws Exception {
        return webHelper.findAll(locataorQuery, null,0);
    }

    public List<WebElement> findAll(By locatorQuery, int maxRetries) throws Exception {
        return webHelper.findAll(locatorQuery, null,maxRetries);
    }

    public List<WebElement> findAll(By locataorQuery, WebElement rootElement) throws Exception {
        return webHelper.findAll(locataorQuery, rootElement,0);
    }

    public List<WebElement> findAll(By locataorQuery, WebElement rootElement,int retries) throws Exception {
        return webHelper.findAll(locataorQuery, rootElement,retries);
    }

    public void acceptAlert() {
        webHelper.acceptAlert();
    }

    public void click(WebElement element) throws Exception {
        webHelper.click(element);
    }

    public void clickCancelAndAlert(WebElement element, String alertAction) {
        webHelper.clickCancelAndAlert(element, alertAction);
    }

    public void quickClick(WebElement element) throws Exception {
        webHelper.click(element);
    }

    public void setText(WebElement element, String text) throws Exception {
        webHelper.setText(element, text);
    }

    public void clickTableLinkByText(String text) throws Exception {
        webHelper.clickTableLinkByText( text);
    }

    public List<WebElement> headerItemVerification(String type,String name) throws Exception {
        return  webHelper.headerItemVerification(type,name);
    }

    public List<WebElement> headerItemRootNotification(String type,String name) throws Exception {
        return webHelper.headerItemRootNotification(type,name);
    }

    public List<WebElement> tabCounterByTabName(String name) throws Exception {
        return webHelper.tabCounterByTabName(name);
    }

    public List<WebElement> dataItemVerification(String date,String status,WebElement root) throws Exception {
        return webHelper.dataItemVerification(date,status,root);
    }

    public void dropDownButtons(String sectionDiv, String text) throws Exception {
        webHelper.dropDownButtons(sectionDiv, text);
    }

    public void dropDownDotsClick(String labelName) throws Exception {
        webHelper.dotsClick(labelName);
    }
    public void radioButtonClick(String labelName, String text) throws Exception {
        webHelper.radioButtonSelection(labelName, text);
    }

    public void checkBoxSelection(String labelName, String text) throws Exception {
        webHelper.checkBoxSelection(labelName, text);
    }

    public void tableRadioButtonClickWithExactValue(String labelName, String text) throws Exception {
        webHelper.tableRadioButtonClickWithExactValue(labelName, text);
    }
    public void tableRadioButtonSelectionWithExactValue(String labelName, String text) throws Exception {
        webHelper.tableRadioButtonSelectionWithExactValue(labelName, text);
    }

    public void radioButtonValueSelectionByTd(String labelName) throws Exception {
        webHelper.radioButtonSelectionByTd(labelName);
    }

    public void inputTextBox(String labelName, String text) throws Exception {
        webHelper.inputTextBox(labelName, text);
    }

    public void dropDownValueSelection(String labelName, String text) throws Exception {
        webHelper.dropdownValueSelection(labelName, text);
    }

    public void buttonClick(String labelName, int index) throws Exception {
        webHelper.buttonClick(labelName, index);
    }

    public Boolean inputBoxDataByTitle(String value) {
        return webHelper.inputBoxDataByTitle(value);
    }

    public WebElement checkBoxByLabel(String label) throws Exception {
        return webHelper.checkBoxByLabel(label);
    }

    public WebElement checkBoxByBoldedLabel(String label) throws Exception {
        return webHelper.checkBoxByBoldedLabel(label);
    }
    public WebElement inputBoxDataLabel(String label) throws Exception {
        return webHelper.inputBoxDataLabel(label);
    }
    public String tableDataList(int index) throws Exception {
        return webHelper.tableDataList(index);
    }

    public void inputBoxDataBySname(String value,String text) throws Exception {
        webHelper.inputBoxDataBySname(value,text);
    }

    public WebElement inputBoxDataBySname(WebElement root,String value) throws Exception {
        return webHelper.inputBoxDataBySname(root,value);
    }

    public WebElement inputBoxDataBySname(String value) throws Exception {
        return webHelper.inputBoxDataBySname(value);
    }

    public String inputBoxXpathBySname(String value) throws Exception {
        return webHelper.inputBoxXpathBySname(value);
    }
    public List<WebElement> inputBoxDataBySnameByIndex(String value) throws Exception {
        return webHelper.inputBoxDataBySnameByIndex(value);
    }

    public WebElement textAreaByTitle(String value) throws Exception {
        return webHelper.textAreaByTitle(value);
    }

    public WebElement textAreaBySname(String value) throws Exception {
        return webHelper.textAreaBySname(value);
    }

    public WebElement clipButtonBySname(String value) throws Exception {
        return webHelper.clipButtonBySname(value);
    }

    public WebElement selectionBoxBySname(WebElement root, String value) throws Exception {
        return webHelper.selectionBoxBySname(root,value);
    }

    public List<WebElement> selectionBoxBySname(String value) throws Exception {
        return webHelper.selectionBoxBySname(value);
    }

    public WebElement selectionBoxBySname(WebElement root, String value, String selectionText) throws Exception {
        return webHelper.selectionBoxBySname(root,value,selectionText);
    }

    public WebElement lockByLabelText(String value, WebElement root) throws Exception {
        return webHelper.lockByLabelText(value,root);
    }
    public WebElement lockByLabel(String value) throws Exception {
        return webHelper.lockByLabel(value);
    }

    public List<WebElement> lockByLabelText(String value) throws Exception {
        return webHelper.lockByLabelText(value);
    }

    public String getText(WebElement element) throws Exception {
        return webHelper.getText(element);
    }

    public void waitUntilVisibleElement(WebElement element) {
        webHelper.waitUntilVisibleElement(element, WebHelper.MethodStore.maxTimeOut());
    }

    public String getValue(WebElement element) throws Exception {
        return webHelper.getValue(element);
    }

    public void selectDropdownOption(WebElement element, String visibleText) {
        webHelper.selectDropdownOption(element, visibleText);
    }

    public String getFirstSelectedOptionInDropdown(WebElement element) {
        return  webHelper.getFirstSelectedOptionInDropdown(element);
    }

    public void selectDropdownOptionByIndex(WebElement element, int index) {
        webHelper.selectDropdownOptionByIndex(element, index);
    }

    public Boolean isDisplayed(By locatorQuery) {
        return webHelper.isDisplayed(locatorQuery);
    }

    public void hoverOver(WebElement element) {
        webHelper.hoverOver(element);
    }

    public void scrollToElement(WebElement element) {
        webHelper.scrollToElement(element);
    }

    public String getDocumentTextByIdJs(String jsId){
        return webHelper.getDocumentTextByIdJs(jsId);
    }
    public List<String> getDocumentTextListByXpathJs(String jsXpath){
        return webHelper.getDocumentTextListByXpathJs(jsXpath);
    }

    public String getDocumentTextByXpathJs(String jsXpath){
        return webHelper.getDocumentTextByXpathJs(jsXpath);
    }

    public void jsClickById(String id){
        webHelper.jsClickById(id);
    }

    public void scrollRightHorizontalWithCoordinates( WebElement scrollArea) {
        webHelper.scrollRightHorizontalWithCoordinates(scrollArea);
    }

    public void sleep(long timeoutInSeconds) {
        long timeOut = 0;
        if (timeoutInSeconds==0)
            timeOut = WebHelper.MethodStore.maxTimeOut();
        else
            timeOut = timeoutInSeconds;
        WebHelper.MethodStore.sleep(timeOut);
    }

    public String getCurrentWindow(){
        return webHelper.getCurrentWindow();
    }

    public String switchToChildWindows() {
        return webHelper.switchToChildWindows();
    }

    public void switchToSpecificWindow(String windowHandle) {
        webHelper.switchToSpecificWindow(windowHandle);
    }

    public void fullScreenChildWindow() {
        webHelper.fullScreen();
    }

    public String getTitle() {
        return webHelper.getTitle();
    }

    public String getPageUrl() {
        return webHelper.getPageUrl();
    }

    public void clearInputBoxByElementAndSendKeys(WebElement inputBoxElement) {
        webHelper.clearInputBoxByElementAndSendKeys(inputBoxElement);
    }

    public void refresh() {
        WebHelper.MethodStore.refresh(driver);
    }

    public List<WebElement> getTableRows(WebElement tableElement) throws Exception {
        return webHelper.getTableRows(tableElement);
    }

    public int getElementCount(String element) throws Exception {
        return webHelper.getElementCount(element);
    }

    public int getColumnCountByName(String elementString, String columnName) throws Exception {
        return webHelper.getColumnCountByName(elementString, columnName);
    }

    public String systemDateNow() {
        return (LocalDateTime.now().toString());
    }

    public List<WebElement> getTableCellsByRow(WebElement row) throws Exception {
        return webHelper.getTableCellsByRow(row);
    }

    public boolean isAlertPresent(){
        return webHelper.isAlertPresent();
    }

    public List<WebElement> pencilIcon(String sname) throws Exception {
        return webHelper.pencilIcon(sname);
    }

    public void checkBoxCheckByJS(WebElement label) throws Exception{
        webHelper.checkBoxCheckByJS(label);
    }

    public boolean isCheckboxSelected(String id) throws Exception{
        return webHelper.isCheckBoxSelected(id);
    }

    public void waitForPageToLoad(){
        webHelper.waitForPageToLoad();
    }
}
