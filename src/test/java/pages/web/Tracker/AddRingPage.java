package pages.web.Tracker;

import commons.constants.Constants;
import commons.enums.MarketEnum;
import commons.objects.Ring;
import commons.objects.Users;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import pages.web.Tracker.site.AcsPage;
import pages.web.Tracker.site.SiteTrackerPage;
import utility.helper.MiscHelpers;

import java.util.List;

public class AddRingPage extends BasePage {
    public WebDriver driver;
    public AddRingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    String parentWindowHolder = "";
    public By integrationAdminTable = By.id("cfTable_5");
    public By tableHeader = By.xpath("//table[@class='hdr']//tr//td");
    public By tableData = By.xpath("//table[@class='obj']//tr");
    public By edbTab = By.xpath("//span[@id='tabLabel6']");
    public By generalInfoTable = By.id("cfTable_1");
    public By applyButton  = By.xpath("//input[@id='btnApply']");
    public By okButton  = By.xpath("//input[@id='btnOK']");
    public By cancelButton  = By.xpath("//input[@id='btnCancel']");
    public By MarketSelection  = By.xpath("//input[@id='btnOK0']");
    public By intergationAdminSite =By.xpath("//div[@title='R:Integration Admin (Ring)']//div[@class='tab_name']");
    public By generalInfoTab  = By.xpath("//div[@title='R:General Info']//div[@class='tab_name']");
    public By zipLabel  = By.xpath("//label[normalize-space()='R:Ring Zip']");
    public By ringLatitudeGmapButton  = By.xpath("//input[@sname='R:Ring Latitude']//parent::*//following-sibling::div[@class = 'newFieldBtn']");
    public By ringNewCoordinateLatVal  = By.xpath("(//input[@id='newCoordinateLatVal'])[2]");
    public By ringNewCoordinateLongVal  = By.xpath("(//input[@id='newCoordinateLongVal'])[2]");
    public By finaBuildSiteLabel  = By.xpath("//label[contains(text(),'Final Build Si')]");
    public By finalBuildSite  = By.xpath("//label[contains(text(),'R:Ring has Active/Primary or Active/Final Build Si')]/parent::td/following-sibling::td");
    public By documentsTabCounter = By.id("tabCounter4");
    public By documentsTab = By.id("tabName4");
    public By generalInfoPage =By.xpath("//span[text()='General Info']");
    public By documentOptions =By.id("btnoptionsGroupOpener4");
    public By documentDeleteOption = By.xpath("//div[text()='Delete']");
    public By projectCountNav = By.xpath("//div[@id='navPager4']");
    public By totalProjectIDCount= By.xpath("//span[@id='gridStat4']");
    public By marketField= By.xpath("(//label[text()='M:Market']/../following-sibling::td/div/div/input)[1]");
    String parentWindow;
    public RingTrackerPage addingNewRingTracker(Ring ring) throws Exception {
        addNewRingTracker(ring);
        return new RingTrackerPage(driver);
    }
    public RingTrackerPage addingNewRingTrackerWithoutLocation(MarketEnum market, String ringCode) throws Exception {
        addNewRingTrackerWithoutLocation(market, ringCode);
        return new RingTrackerPage(driver);
    }

    public RingTrackerPage addingNewRingAndCancel(Ring ring) throws Exception {
        addNewRingAndCancel(ring);
        return new RingTrackerPage(driver);
    }

    public RingTrackerPage addingRingWithoutMarket(Ring ring) throws Exception {
        addRingWithoutMarket(ring);
        return new RingTrackerPage(driver);
    }

    public Boolean verifyDoNotUseSpectrumSpatialAPI(String value) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(intergationAdminSite));
        click(find(intergationAdminSite));
        WebElement spectrumSpatialApi = selectionBoxBySname(find(integrationAdminTable),"R:Do Not Use Spectrum Spatial API");
        scrollToElement(spectrumSpatialApi);
        //waitUntilVisibleElement(spectrumSpatialApi);
        String currentValue = spectrumSpatialApi.getText();
        System.out.println("spectrumSpatialVal "+currentValue);
        parentWindowHolder = parent1;
        return currentValue.contains(value);
    }

    public Boolean verifyGeoLocationDetailsIsPresent() throws Exception {
        click(find(generalInfoTab));
        scrollToElement(find(zipLabel));
        WebElement ringStateDropdown = selectionBoxBySname(find(generalInfoTable),"R:Ring State");
        WebElement ringCounty = inputBoxDataBySname("R:Ring County");
        WebElement ringZip = inputBoxDataBySname("R:Ring Zip");
        waitUntilVisibleElement(ringStateDropdown);
        String stateDropdownText=getFirstSelectedOptionInDropdown(ringStateDropdown);
        System.out.println("state"+stateDropdownText);
        String countyInputText=ringCounty.getAttribute("origval");
        System.out.println("county"+countyInputText);
        String zipInputText=ringZip.getAttribute("origval");
        System.out.println("zip"+zipInputText);
        click(find(okButton));
        switchToSpecificWindow(parentWindowHolder);
        sleep(5);
        boolean response = true;
        if(countyInputText.isEmpty() || zipInputText.isEmpty() || stateDropdownText.isEmpty())
            response = false;
        System.out.println("resp::: "+ response);
        return response;
    }

    public void setNoSpectrumCallTo(String value) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        click(find(intergationAdminSite));
        waitForPageToLoad();
        WebElement spectrumSpatialApi = selectionBoxBySname("R:Do Not Use Spectrum Spatial API").get(1);//integration option
        scrollToElement(spectrumSpatialApi);
        click(lockByLabelText("R:Do Not Use Spectrum Spatial API").get(1));
        if(value.equals("yes")){
            selectDropdownOption(spectrumSpatialApi,"Yes");
        }
        else{
            selectDropdownOption(spectrumSpatialApi,"No");
        }
        click(find(applyButton));
        parentWindowHolder = parent1;
    }

    public void addNewRingTracker(Ring ring) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(applyButton));
        dropDownDotsClick("M:Market");
        String parent2 = switchToChildWindows();
        waitUntilVisibleElement(find(MarketSelection));
        fullScreenChildWindow();
        waitUntilVisibleElement(find(MarketSelection));
        //radioButtonClick("M:Market", ring.market);
        radioButtonClick("M:Market", ring.market);
        waitUntilVisibleElement(find(MarketSelection));
        quickClick(find(MarketSelection));
        sleep(5);
        switchToSpecificWindow(parent2);
        WebElement ringCodeInput = inputBoxDataBySname("R:Ring Code");
        waitUntilVisibleElement(ringCodeInput);
        setText(ringCodeInput, ring.ringId);
        waitUntilVisibleElement(find(ringLatitudeGmapButton));
        find(ringLatitudeGmapButton).click();
        setText(find(ringNewCoordinateLatVal), ring.latitude);
        setText(find(ringNewCoordinateLongVal),ring.longitude);
        // inputTextBox("R:Desired RAD Center (in feet)", "30");
        buttonClick("OK", 4);
        click(find(applyButton));
        waitForPageToLoad();
        waitUntilVisibleElement(find(okButton));
        sleep(5);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
    }

    public void addNewRingTrackerWithoutLocation(MarketEnum market, String ringCode) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(applyButton));
        dropDownDotsClick("M:Market");
        String parent2 = switchToChildWindows();
        waitUntilVisibleElement(find(MarketSelection));
        fullScreenChildWindow();
        radioButtonClick("M:Market", market.name().toUpperCase());
        waitUntilVisibleElement(find(MarketSelection));
        quickClick(find(MarketSelection));
        sleep(5);
        switchToSpecificWindow(parent2);
        WebElement ringCodeInput = inputBoxDataBySname("R:Ring Code");
        waitUntilVisibleElement(ringCodeInput);
        setText(ringCodeInput, ringCode);
        waitUntilVisibleElement(find(okButton));
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
    }

    public void addRingWithoutMarket(Ring ring) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        setText(inputBoxDataBySname("R:Ring Code"), ring.ringId);
        sleep(5);
        clickCancelAndAlert(find(okButton), "accept");
        acceptAlert();
        sleep(5);
        clickCancelAndAlert(find(cancelButton), "accept");
        acceptAlert();
        sleep(5);
        switchToSpecificWindow(parent1);
        sleep(5);
    }

    public void addNewRingAndCancel(Ring ring) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        dropDownDotsClick("M:Market");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        radioButtonClick("M:Market", ring.market);
        waitUntilVisibleElement(find(MarketSelection));
        quickClick(find(MarketSelection));
        //sleep(5);
        switchToSpecificWindow(parent2);
        setText(inputBoxDataBySname("R:Ring Code"), ring.ringId);
        //sleep(5);
        clickCancelAndAlert(find(cancelButton), "accept");
        acceptAlert();
        // sleep(5);
        switchToSpecificWindow(parent1);
        sleep(5);
    }

    public RingTrackerPage editRingMandatoryDetails(Ring ring) throws Exception {
        String parent = setMandatoryDetails(ring.ringIdDescription, ring.ringSubMarket);
        applyChanges();
        waitUntilVisibleElement(find(okButton));
        click(find(okButton));
        switchToSpecificWindow(parent);
        sleep(20);
        return new RingTrackerPage(driver);
    }

    public String setMandatoryDetails(String description, String subMarket) throws Exception {
//        selectDropdownOption(descDropdown,description)
//        selectDropdownOption(subMarketDropdown,subMarket)
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(5);
        dropDownValueSelection("R:Ring ID Description", description);
        dropDownValueSelection("R:Ring Submarket", subMarket);
        inputTextBox("R:Ring Radius (in miles)", "20");
        return parent1;
    }

    public void applyChanges() throws Exception {
        click(find(applyButton));
    }

    public String getRingStatus() throws Exception {
        return getText(selectionBoxBySname(null,"R:Ring Status"));
    }

    public void addGeoLocation() throws Exception {
        click(find(generalInfoTab));
        scrollToElement(find(ringLatitudeGmapButton));
        click(lockByLabelText("R:Ring Latitude",find(generalInfoTab)));
        click(lockByLabelText("R:Ring Longitude",find(generalInfoTab)));
        click(find(ringLatitudeGmapButton));
        setText(find(ringNewCoordinateLatVal),Constants.VALID_LATITUDE);
        setText(find(ringNewCoordinateLongVal),Constants.VALID_LONGITUDE);
        buttonClick("OK", 4);
        click(find(applyButton));
    }

    public Boolean verifySpectrumCallDetails(String state, Users userDetails) throws Exception {

        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(intergationAdminSite));
        click(find(intergationAdminSite));
        String name = userDetails.getUser();
        String currentDate = MiscHelpers.currentDateTime("MM/dd/yyyy");
        waitForPageToLoad();
        WebElement ssApiResponseStatus = selectionBoxBySname(find(integrationAdminTable),"R:Spectrum Spatial API Response Status");
        waitUntilVisibleElement(ssApiResponseStatus);
        boolean apiCallState = state.contains( getFirstSelectedOptionInDropdown(ssApiResponseStatus));
        System.out.println("apiCall "+getFirstSelectedOptionInDropdown(ssApiResponseStatus));
        System.out.println("apiCallState "+apiCallState);
//        boolean dateState = find(ssDate).getText().contains(currentDate);
//        System.out.println("date "+find(ssDate).getText());
//        System.out.println("dateState "+dateState);
        WebElement ssCreatedName = inputBoxDataBySname(find(integrationAdminTable),"R:Created User Name");
        boolean createdNameState = name.contains( ssCreatedName.getAttribute("origval"));
        System.out.println("name "+ssCreatedName.getAttribute("origval"));
        System.out.println("createdNameState "+createdNameState);
        return !(!apiCallState||!createdNameState);
    }

    public Boolean verifyFinalBuildSiteLabelAndDate(String value, Users userDetails) throws Exception {
        sleep(5);
        String parent1 = switchToChildWindows();
        waitUntilVisibleElement(find(applyButton));
        fullScreenChildWindow();
        scrollToElement(find(finaBuildSiteLabel));
        String currentDate = MiscHelpers.currentDateTime("MM/dd/yyyy");
        String owner = userDetails.getNtCode();
        boolean date = inputBoxDataByTitle(currentDate);
        boolean createdBy = inputBoxDataByTitle(owner);
        boolean check = find(finalBuildSite).getText().contains(value);
        click(find(okButton));
        //sleep(15);
        switchToSpecificWindow(parent1);
        sleep(10);
        return  (date && createdBy && check);

    }

    public RingTrackerPage editRingDetails() throws Exception {
        sleep(5);
        String parent1 = switchToChildWindows();
        waitUntilVisibleElement(find(applyButton));
        fullScreenChildWindow();
//        String currentDate = MiscHelpers.currentDateTime("MM/dd/yyyy");
        inputTextBox("R:Ring Radius (in miles)", MiscHelpers.getRandomNumber(2));
        click(find(applyButton));
        waitUntilVisibleElement(find(applyButton));
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        return new RingTrackerPage(driver);
    }

    public boolean verifyRingDetailsUpdate(Users userDetails) throws Exception {
        sleep(5);
        String parent1 = switchToChildWindows();
        waitUntilVisibleElement(find(applyButton));
        fullScreenChildWindow();
//        String currentDate = MiscHelpers.currentDateTime("MM/dd/yyyy");
        String owner = userDetails.getNtCode();
        WebElement lastUpdatedBy = inputBoxDataBySname("R:Last Updated By");
        scrollToElement(lastUpdatedBy);
        System.out.println("Name::"+lastUpdatedBy.getAttribute("origval"));
        boolean updatedBy = lastUpdatedBy.getAttribute("origval").contains(owner);
        System.out.println("boolean:: "+updatedBy);
//        boolean updatedDate = find(lastUpdatedDate).getAttribute("title").contains(currentDate);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        return updatedBy ;
    }

    public boolean isRingStatusChangeable() throws Exception {
        String parent1 = switchToChildWindows();
        waitUntilVisibleElement(find(applyButton));
        fullScreenChildWindow();
        boolean ringStatusVal = selectionBoxBySname(null,"R:Ring Status").isEnabled();
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        return ringStatusVal;

    }

    public RingTrackerPage updateRingStatus(String status) throws Exception {
        String parent1 = switchToChildWindows();
        waitUntilVisibleElement(find(applyButton));
        fullScreenChildWindow();
        if(status.equals("")){
            selectDropdownOptionByIndex(selectionBoxBySname(null,"R:Ring Status"),0);
        }
        else
            selectDropdownOption(selectionBoxBySname(null,"R:Ring Status"),status);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        return new RingTrackerPage(driver);
    }

    public String EnterSACVendor(String data) throws Exception {

        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        inputBoxDataBySname("R:SAC Vendor",data);
        click(find(applyButton));
        waitForPageToLoad();
        waitUntilVisibleElement(inputBoxDataBySname("R:SAC Assigned Date"));
        String SACVendorDate = inputBoxDataBySname("R:SAC Assigned Date").getAttribute("value");
        System.out.println("SAC Vendor Date_"+SACVendorDate);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        return SACVendorDate;
    }

    public void goToEdbTab() throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        click(find(edbTab));
        waitForPageToLoad();
        int size = driver.findElements(By.tagName("iframe")).size();
        List<WebElement> elm = driver.findElements(By.tagName("iframe"));
        driver.switchTo().frame("ifrm");
        driver.switchTo().frame("ifrm2");
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

    public String searchForValueInGrid(String columnName) throws Exception {
        fullScreen();
        int columnToFind = getTableData(columnName);
        //int columnValueToMatch = getTableData(searchByColumn);
        List<WebElement> tableContents = findAll(tableData);
        if(columnToFind !=-1){

            List<WebElement> cellDataForTheRow = findAll(By.tagName("td"),tableContents.get(1));
            scrollToElement(cellDataForTheRow.get(1));
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

    public RingTrackerPage closeEditPage() throws Exception {
        driver.switchTo().frame(0);
        click(find(okButton));
        return new RingTrackerPage(driver);
    }

    public AcsPage goToAcsSection(){
        return new AcsPage(driver);
    }

    public int uploadScipImage() throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        int docCount =  Integer.parseInt(getText(find(documentsTabCounter)));
        waitUntilVisibleElement(find(applyButton));
        parentWindowHolder = parent1;
        return docCount;
        //switchToSpecificWindow(parent1);
    }
    //public int getDocumentsCount() throws Exception {
    // fullScreenChildWindow();
    //  applyChanges();
    // sleep(5);
    // click(find(documentsTab));
    // sleep(5);
    //  waitUntilVisibleElement(find(applyButton));
    //  click(find(generalInfoPage));
    // sleep(5);
    // return Integer.parseInt(getText(find(documentsTabCounter)));
    // }
    public int getDocumentIDCount() throws Exception {
        applyChanges();
        sleep(5);
        click(find(documentsTab));
        sleep(5);
        //waitUntilVisibleElement(find(applyButton));
        //waitForPageToLoad();
        waitUntilVisibleElement(find(projectCountNav));
        if(!getText(find(projectCountNav)).contains("No Data")){
            waitUntilVisibleElement(find(totalProjectIDCount));
            hoverOver(find(totalProjectIDCount));
            String documentIDCount=find(totalProjectIDCount).getText();
            System.out.println(documentIDCount);
            click(find(generalInfoPage));
            sleep(5);
            return  Integer.parseInt(documentIDCount);
        }
        return 0;
    }
    public void deleteAddedDocument() throws Exception {
        if(getDocumentIDCount()>0){
            fullScreenChildWindow();
            click(find(documentsTab));
            waitUntilVisibleElement(find(documentOptions));
            click(find(documentOptions));
            clickCancelAndAlert(find(documentDeleteOption),"accept");
            sleep(5);
            // waitUntilVisibleElement(find(optionsMenu));
            acceptAlert();
            fullScreenChildWindow();
            waitUntilVisibleElement(find(documentOptions));
        }
    }
    public RingTrackerPage acceptAndGoToSiteTracker() throws Exception {
        click(find(okButton));
        switchToSpecificWindow(parentWindowHolder);
        sleep(5);
        return new RingTrackerPage(driver);
    }
    public boolean scipSubmittedDateValidation() throws Exception{
        sleep(5);
        String value=getDocumentTextByIdJs(inputBoxDataBySname("R:Ring-Level SCIP Package Submitted Date").getAttribute("id"));
        if(!value.isEmpty()){
            return true;
        }
        return false;

    }

    public boolean marketFieldValidation() throws Exception{
        switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton));
        sleep(5);
        String value =  getDocumentTextByIdJs(find(marketField).getAttribute("id"));
        // String value=find(marketField).getAttribute("id");
        if(!value.isEmpty()){
            return true;
        }
        return false;
    }
    public boolean btaFieldValidation() throws Exception{
        sleep(5);
        WebElement ringBta = inputBoxDataBySname("R:Ring BTA");
        scrollToElement(ringBta);
        String value=ringBta.getAttribute("origval");
        if(!value.isEmpty()){
            return true;
        }
        return false;
    }
    public boolean marketFieldLockValidation() throws Exception{
        switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton));
        sleep(5);
        WebElement marketLock=find(marketField);
        return marketLock.isEnabled();
    }
    public boolean dateCreatedValidation() throws Exception{
        sleep(5);
        WebElement dateCreated = inputBoxDataBySname("R:Create Date");
        scrollToElement(dateCreated);
        String value=getDocumentTextByIdJs(inputBoxDataBySname("R:Create Date").getAttribute("id"));
        if(!value.isEmpty()){
            return true;
        }
        return false;
    }
    public boolean finalBuildSiteFieldValidation() throws Exception{
        sleep(5);
        String value = getFirstSelectedOptionInDropdown(selectionBoxBySname("R:Ring has Active/Primary or Active/Final Build Site?").get(0));
        // String value = selectionBoxBySname("R:Ring has Active/Primary or Active/Final Build Site?").get(0).getText();
        if(value.equals("No")){
            return true;
        }
        return false;
    }

    public int getCounterCountForTab(String tabName) throws Exception {
        String parent1 = switchToChildWindows();
        waitUntilVisibleElement(find(applyButton));
        fullScreenChildWindow();
        List<WebElement> counters = tabCounterByTabName(tabName);
        int count = Integer.parseInt(counters.get(0).getText());
        click(find(cancelButton));
        switchToSpecificWindow(parent1);
        return count;
    }
    public boolean verifyMarketField() throws Exception {
        String marketValue = getDocumentTextByIdJs(find(marketField).getAttribute("id"));
        System.out.println("Market Field Value - " + marketValue);
        if (marketValue.equalsIgnoreCase("TEST - LAB MARKET")){
            return true;
        }
        return false;
    }
    public String switchToRingPage(){
        parentWindow = switchToChildWindows();
        fullScreen();
        sleep(3);
        return parentWindow;
    }
    public void backToTrackerPage() throws Exception {
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
    }
}
