package pages.web.Tracker.site;

import commons.constants.Constants;
import commons.objects.Site;
import commons.objects.Users;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import pages.web.Tracker.CabinetTrackerPage;
import pages.web.Tracker.site.SiteTrackerPage;
import rest.sector.SectorRequestBuilder;
import utility.helper.MiscHelpers;

import java.util.List;

public class AddSitePage extends BasePage {
    public WebDriver driver;
    public AddSitePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
    String parentWindowHolder = "";
    String parentWindow;
    public By applyButton = By.xpath("//input[@id='btnApply']");
    public By okButton = By.xpath("//input[@id='btnOK']");
    public By cancelButton = By.xpath("//input[@id='btnCancel']");
    public By cancelButton1 = By.xpath("//input[@id='btnClose0']");
    public By siteCodeInput = By.xpath("//input[@id='idx5']");
    public By ringCodeSelectionOKButton = By.xpath("//input[@id='btnOK0']");
    public By siteLatitudeGmapButton = By.xpath("//input[@sname='S:Site Latitude']//parent::*//following-sibling::div[@class = 'newFieldBtn']");
    public By siteNewCoordinateLatVal = By.xpath("(//input[@id='newCoordinateLatVal'])[2]");
    public By siteNewCoordinateLongVal = By.xpath("(//input[@id='newCoordinateLongVal'])[2]");
    public By ringCodeSearchButton = By.xpath("//input[@id='btnSearch0']");
    public By ringCodeSearch = By.xpath(" //input[@id='qsValue0']");
    public By preferredLandLordInfo = By.xpath("//label[normalize-space()='S:Preferred Landlord/Site ID']");
    public By SpecturmCall = By.xpath("//label[@id='lblidx42']//following-sibling::span");
    public By intergationAdminSite =By.xpath("//span[text()=' Integration Admin (Site)']");
    public By spectrumSpatialApiYes =By.xpath(" //select[@id='idx216']//option[text()='Yes']");
    public By generalInfoPage =By.xpath("//span[text()='General Info']");
    public By documentOptions = By.id("btnoptionsGroupOpener7");
    public By documentDeleteOption = By.xpath("//div[text()='Delete']");
    public By documentsTab = By.xpath("//span[text()='Document']");
    public By documentsTabCounter = By.xpath("(//span[text()='Document']//following::div//span)[1]");
    public By firstOption = By.xpath("//tr[@class=' ev_dhx_skyblue rowselected']//input");
    public By okButton1 = By.xpath("//input[@id='btnOK0']");
    public By onAirOffAirTab = By.xpath("//span[@id='tabLabel2']");
    public By bbuBtsTab = By.xpath("//span[@id='tabLabel6']");
    public By rfSectorTab = By.xpath("//span[@id='tabLabel7']");
    public By  siteTrackerTableElement = By.xpath("//tr[contains(@class,'ev_dhx_skyblue rowselected')]//parent::*");
    public By fopsTab = By.xpath("//div[@title='S:FOPS Info']");
    public By cabinetTrackerTab = By.xpath("//div[@title='CAB:Cabinet Tracker']");
    public By editOption = By.xpath("(//input[@value='Edit'])[3]");
    public By addOption = By.xpath("//input[@value='Add']");
    String parentWindow1;

    public SiteTrackerPage addNewSiteWith9CharactersSiteCode(String ringCode, String siteCode) throws Exception {
        addNewSiteTrackerWith9CharactersSiteCode(ringCode,siteCode);
        return new SiteTrackerPage(driver);
    }

    public void addNewSiteTrackerWith9CharactersSiteCode(String ringCode, String siteCode) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        dropDownDotsClick("R:Ring Code");
        sleep(5);
        String parent2 = switchToChildWindows();

        fullScreenChildWindow();
        sleep(5);
        click(find(ringCodeSearch));

        setText(find(ringCodeSearch),ringCode);
        click(find(ringCodeSearchButton));
        radioButtonClick("R:Ring Code", ringCode);
        quickClick(find(ringCodeSelectionOKButton));
        sleep(5);
        switchToSpecificWindow(parent2);
        sleep(5);
        setText(find(siteCodeInput),siteCode);

        click(find(siteLatitudeGmapButton));
        inputBoxDataBySname("S:Site Latitude", Constants.VALID_LATITUDE);
        inputBoxDataBySname("S:Site Longitude", Constants.VALID_LONGITUDE);
        sleep(5);
        //buttonClick("OK",4);
        click(find(okButton));
        clickCancelAndAlert(find(cancelButton),"accept");
        acceptAlert();
        sleep(5);
        switchToSpecificWindow(parent1);
        sleep(5);

    }


    public SiteTrackerPage addingNewSiteTracker(Site site) throws Exception {
        addNewSiteTracker(site);
        return new SiteTrackerPage(driver);
    }

    public SiteTrackerPage addingNewSiteTrackerWithoutGeoLocation(String ringCode, String siteCode) throws Exception {
        addNewSiteTrackerWithoutGeoLocation(ringCode, siteCode);
        return new SiteTrackerPage(driver);
    }

    public SiteTrackerPage addingNewSiteAndCancel(String ringCode, String siteCode) throws Exception {
        addNewSiteAndCancel(ringCode, siteCode);
        return new SiteTrackerPage(driver);
    }

    public Boolean verifyDoNotUseSpectrumSpatialAPI(String value) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(intergationAdminSite));
        click(find(intergationAdminSite));
        waitForPageToLoad();
        scrollToElement(selectionBoxBySname("S:Do Not Use Spectrum Spatial API").get(0));
        //waitUntilVisibleElement(selectionBoxBySname("S:Do Not Use Spectrum Spatial API").get(0));
        String currentValue = selectionBoxBySname("S:Do Not Use Spectrum Spatial API").get(0).getText();
        parentWindowHolder = parent1;
        return currentValue.contains(value);
    }

    public void setNoSpectrumCallTo(String value) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        click(find(intergationAdminSite));
        waitForPageToLoad();
        scrollToElement(lockByLabelText("S:Do Not Use Spectrum Spatial API").get(1));
        click(lockByLabelText("S:Do Not Use Spectrum Spatial API").get(1));
        click(selectionBoxBySname("S:Do Not Use Spectrum Spatial API").get(1));
        if(value.equals("yes")){
            selectDropdownOption(selectionBoxBySname("S:Do Not Use Spectrum Spatial API").get(1),"Yes");

        }
        else{
            selectDropdownOption(selectionBoxBySname("S:Do Not Use Spectrum Spatial API").get(1),"No");
        }
        click(find(applyButton));
        parentWindowHolder = parent1;
    }

    public void addGeoLocation() throws Exception {
        click(find(generalInfoPage));
        scrollToElement(lockByLabelText("S:Site Latitude").get(0));
        click(lockByLabelText("S:Site Latitude").get(0));
        click(lockByLabelText("S:Site Longitude").get(0));
        click(find(siteLatitudeGmapButton));
        setText(find(siteNewCoordinateLatVal),Constants.VALID_LATITUDE);
        setText(find(siteNewCoordinateLongVal),Constants.VALID_LONGITUDE);
        buttonClick("OK", 4);
        click(find(applyButton));
    }

    public Boolean verifyGeoLocationDetailsIsPresentWithAlert() throws Exception {
        click(find(generalInfoPage));
        scrollToElement(selectionBoxBySname("S:State").get(0));
        waitUntilVisibleElement(selectionBoxBySname("S:State").get(0));
        String stateDropdownText=getFirstSelectedOptionInDropdown(selectionBoxBySname("S:State").get(0));
        System.out.println("state");
        String countyInputText=inputBoxDataBySname("S:County").getAttribute("origval");
        System.out.println("county"+countyInputText);
        String zipInputText=inputBoxDataBySname("S:Zip").getAttribute("origval");
        System.out.println("zip"+zipInputText);
        click(find(okButton));
        sleep(5);
        if(isAlertPresent()){
            acceptAlert();
            click(find(cancelButton));
            acceptAlert();
        }
        switchToSpecificWindow(parentWindowHolder);
        sleep(5);
        boolean response = true;
        if(countyInputText.isEmpty() || zipInputText.isEmpty() || stateDropdownText.isEmpty())
            response = false;
        System.out.println("resp::: "+ response);
        return response;
    }
    public Boolean verifyGeoLocationDetailsIsPresentWithoutAlert() throws Exception {
        click(find(generalInfoPage));
        scrollToElement(selectionBoxBySname("S:State").get(0));
        waitUntilVisibleElement(selectionBoxBySname("S:State").get(0));
        String stateDropdownText=getFirstSelectedOptionInDropdown(selectionBoxBySname("S:State").get(0));
        System.out.println("state");
        String countyInputText=inputBoxDataBySname("S:County").getAttribute("origval");
        System.out.println("county"+countyInputText);
        String zipInputText=inputBoxDataBySname("S:Zip").getAttribute("origval");
        System.out.println("zip"+zipInputText);
        click(find(okButton));
        sleep(5);
        switchToSpecificWindow(parentWindowHolder);
        sleep(5);
        boolean response = true;
        if(countyInputText.isEmpty() || zipInputText.isEmpty() || stateDropdownText.isEmpty())
            response = false;
        System.out.println("resp::: "+ response);
        return response;
    }

    public Boolean checkSpectrumSpacialCall() throws Exception {
        boolean initialSpacialCall = false;
        boolean finalSpacialCall = false;
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(10);
        hoverOver(find(SpecturmCall));
        sleep(10);
        //click(SpecturmCall)
        // checkBox(SpecturmCall)
        // buttonClick("Apply",1)
        //sleep(30)
        String stateDropdownText=getText(selectionBoxBySname("S:State").get(0));
        System.out.println(stateDropdownText);
        String countyInputText=getText(inputBoxDataBySname("S:County"));
        System.out.println(countyInputText);
        String zipInputText=getText(inputBoxDataBySname("S:Zip"));
        System.out.println(zipInputText);
        boolean stateDetails = stateDropdownText == "";
        boolean countryDetails = countyInputText == "";
        boolean zipDetails = zipInputText == "";
        if(!stateDetails  ||!countryDetails || !zipDetails)
        {
            System.out.println("Spectrum Spatial call is working properly");
            initialSpacialCall = true;
        }
        else
        {
            System.out.println("Spectrum Spatial call is not working properly");
            initialSpacialCall = false;
        }

        click(find(intergationAdminSite));
        hoverOver(lockByLabelText("S:Do Not Use Spectrum Spatial API").get(1));
        click(lockByLabelText("S:Do Not Use Spectrum Spatial API").get(1));
        click(selectionBoxBySname("S:Do Not Use Spectrum Spatial API").get(1));
        //dropDownValueSelection("S:Do Not Use Spectrum Spatial API","Yes")
        click(find(spectrumSpatialApiYes));
        buttonClick("Apply",1);
        click(find(generalInfoPage));
        // checkBox(SpecturmCall)
        stateDropdownText=getText(selectionBoxBySname("S:State").get(0));
        System.out.println(stateDropdownText);
        countyInputText =getText(inputBoxDataBySname("S:County"));
        System.out.println(countyInputText);
        zipInputText =getText(inputBoxDataBySname("S:Zip"));
        System.out.println(zipInputText);
        stateDetails = stateDropdownText == "";
        countryDetails = countyInputText == "";
        zipDetails = zipInputText == "";
        if(!stateDetails  ||!countryDetails || zipDetails)
        {
            System.out.println("Spectrum Spatial call  is not working properly");
            finalSpacialCall = false;
        }
        else
        {
            System.out.println("Spectrum Spatial call is  working properly");
            finalSpacialCall = true;
        }
        buttonClick("OK",3);
        switchToSpecificWindow(parent1);
        sleep(5);
        return initialSpacialCall && finalSpacialCall;
    }

    public SiteTrackerPage withOutsetMandatoryDetails() throws Exception {

        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        String currentWindow = getCurrentWindow();
        sleep(5);
        dropDownValueSelection("S:Site Status","Active");
        buttonClick("Apply",1);
        sleep(5);
        if(!isAlertPresent())
            return null;
        //clickCancelAndAlert(find(cancelButton),"accept");
        acceptAlert();
        sleep(5);
        switchToSpecificWindow(currentWindow);
        click(find(cancelButton));
        if(!isAlertPresent())
            return null;
        acceptAlert();
        sleep(5);
        // sleep(5)
        //clickCancelAndAlert(cancelButton,"accept")
        switchToSpecificWindow(parent1);
        //sleep(5);
        return new SiteTrackerPage(driver);
    }

    public void addNewSiteTracker(Site site) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        dropDownDotsClick("R:Ring Code");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        click(find(ringCodeSearch));
        setText(find(ringCodeSearch),site.ringId);
        click(find(ringCodeSearchButton));
        radioButtonClick("R:Ring Code", site.ringId);
        quickClick(find(ringCodeSelectionOKButton));
        switchToSpecificWindow(parent2);
        setText(find(siteCodeInput), site.siteId);
        scrollToElement(inputBoxDataBySname("S:County"));
        inputBoxDataBySname("S:County").sendKeys("USA");
        click(find(siteLatitudeGmapButton));
        setText(find(siteNewCoordinateLatVal), site.latitude);
        setText(find(siteNewCoordinateLongVal), site.longitude);
        buttonClick("OK", 4);
        click(find(applyButton));
        waitForPageToLoad();
        waitUntilVisibleElement(find(okButton));
        sleep(9);
        click(find(okButton));
        sleep(4);
        switchToSpecificWindow(parent1);
        sleep(5);
    }

    public void addNewSiteTrackerWithoutGeoLocation(String ringCode, String siteCode) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        dropDownDotsClick("R:Ring Code");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        click(find(ringCodeSearch));
        setText(find(ringCodeSearch),ringCode);
        click(find(ringCodeSearchButton));
        radioButtonClick("R:Ring Code", ringCode);
        quickClick(find(ringCodeSelectionOKButton));
        switchToSpecificWindow(parent2);
        setText(find(siteCodeInput), siteCode);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
    }

    public void addSiteWithoutRingCode(String siteCode) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        setText(find(siteCodeInput), siteCode);
        clickCancelAndAlert(find(okButton), "accept");
        acceptAlert();
        clickCancelAndAlert(find(cancelButton), "accept");
        acceptAlert();
        switchToSpecificWindow(parent1);
    }

    public void addNewSiteAndCancel(String ringCode, String siteCode) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        dropDownDotsClick("R:Ring Code");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        click(find(ringCodeSearch));
        setText(find(ringCodeSearch),ringCode);
        click(find(ringCodeSearchButton));
        radioButtonClick("R:Ring Code", ringCode);
        quickClick(find(ringCodeSelectionOKButton));
        switchToSpecificWindow(parent2);
        setText(find(siteCodeInput), siteCode);
        clickCancelAndAlert(find(cancelButton), "accept");
        acceptAlert();
        switchToSpecificWindow(parent1);
    }

    public SiteTrackerPage editSiteMandatoryDetails(Site site) throws Exception {
        String parent = setMandatoryDetails(site);
        applyChanges();
        waitUntilVisibleElement(find(okButton));
        sleep(5);
        click(find(okButton));
        switchToSpecificWindow(parent);
        sleep(5);
        return new SiteTrackerPage(driver);
    }

    public String setMandatoryDetails(Site site) throws Exception {

        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        dropDownValueSelection("S:Site Type", site.siteType);
        sleep(5);
        dropDownValueSelection("S:Site Class", site.siteClass);
        inputTextBox("S:Site Name", "Test");
        dropDownValueSelection("S:Build-out Type", site.siteBuildOutType);
        inputTextBox("S:Address", site.siteAddress);
        inputTextBox("S:City", site.siteCity);

        scrollToElement(find(preferredLandLordInfo));
        dropDownDotsClick("S:Preferred Landlord/Site ID");
        String parent3 = switchToChildWindows();
        radioButtonValueSelectionByTd("PS:Preferred Landlord/Site ID");
        buttonClick("OK", 3);
        switchToSpecificWindow(parent3);
        return parent1;
    }

    public void applyChanges() throws Exception {
        click(find(applyButton));
    }

    public String getSiteStatus() throws Exception {

        return getText(selectionBoxBySname("S:Site Status").get(0));
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

    public int getDocumentsCount() throws Exception {
        fullScreenChildWindow();
        applyChanges();
        sleep(5);
        click(find(documentsTab));
        waitUntilVisibleElement(find(documentOptions));
        click(find(generalInfoPage));
        return Integer.parseInt(getText(find(documentsTabCounter)));
    }

    public void deleteAddedDocument() throws Exception {
        if(getDocumentsCount()>0){
            fullScreenChildWindow();
            click(find(documentsTab));
            waitUntilVisibleElement(find(documentOptions));
            click(find(documentOptions));
            clickCancelAndAlert(find(documentDeleteOption),"accept");
            sleep(5);
            //waitUntilVisibleElement(find(optionsMenu));
            acceptAlert();
            fullScreenChildWindow();
            waitUntilVisibleElement(find(documentOptions));
        }
    }

    public SiteTrackerPage acceptAndGoToSiteTracker() throws Exception {
        click(find(okButton));
        switchToSpecificWindow(parentWindowHolder);
        sleep(5);
        return new SiteTrackerPage(driver);
    }

    public SiteTrackerPage updateSiteStatus(String status) throws Exception {
        String parent1 = switchToChildWindows();
        waitUntilVisibleElement(find(applyButton));
        fullScreenChildWindow();
        if(status.equals("")){

            selectDropdownOptionByIndex(selectionBoxBySname("S:Site Status").get(0),0);
        }
        else
            selectDropdownOption(selectionBoxBySname("S:Site Status").get(0),status);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        return new SiteTrackerPage(driver);
    }

    public AcsPage goToAcsSection(){
        return new AcsPage(driver);
    }
    public boolean zipCodeValidation() throws Exception{
        switchToChildWindows();
        fullScreen();
        sleep(5);
        WebElement zipCode = inputBoxDataBySname("S:Zip");
        scrollToElement(zipCode);
        String value=inputBoxDataBySname("S:Zip").getAttribute("origval");
        if(!value.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean stateFieldValidation() throws Exception{
        sleep(5);
        scrollToElement(selectionBoxBySname("S:State").get(0));
        String value = getFirstSelectedOptionInDropdown(selectionBoxBySname("S:State").get(0));
        if(!value.isEmpty()){
            return true;
        }
        return false;
    }
    public boolean countryFieldValidation() throws Exception{
        sleep(5);
        String value = getFirstSelectedOptionInDropdown(selectionBoxBySname("S:Country").get(0));
        if(!value.isEmpty()){
            return true;
        }
        return false;
    }

    public void selectSiteCategoryBbuBts1() throws Exception{
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        WebElement sitecategory = selectionBoxBySname("S:Site Category").get(0);
        scrollToElement(sitecategory);
        selectDropdownOption(sitecategory, "BBU/BTS");
        click(find(applyButton));
        sleep(5);
        dropDownDotsClick("S:Hub Site ID");
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton1));
        click(find(firstOption));
        click(find(okButton1));
        sleep(5);
        switchToSpecificWindow(parent1);
        click(find(applyButton));
        sleep(10);

    }
    public void selectSiteCategoryNode() throws Exception{
        String parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        WebElement sitecategory = selectionBoxBySname("S:Site Category").get(0);
        scrollToElement(sitecategory);
        selectDropdownOption(sitecategory, "Node");
        click(find(applyButton));
        sleep(5);
        dropDownDotsClick("S:Hub Site ID");
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(okButton1));
        click(find(firstOption));
        click(find(okButton1));
        sleep(5);
        switchToSpecificWindow(parent1);
        click(find(applyButton));
        sleep(10);

    }
    public boolean hubClusterIdVerification() throws Exception {
        WebElement hubClusterId = inputBoxDataBySname("S:Hub Cluster ID");
        return hubClusterId.isEnabled();

    }
    public void goToOnAirOffAirTab() throws Exception{
        switchToChildWindows();
        fullScreen();
        waitUntilVisibleElement(find(okButton));
        click(find(onAirOffAirTab));
        sleep(10);

    }
    public boolean L600TechnologyValidation(String fieldName) throws Exception{
        WebElement fieldObj = inputBoxDataBySname("S:On-Air Date - "+fieldName);
        String value = getDocumentTextByIdJs(fieldObj.getAttribute("id"));
        if(!value.isEmpty()) {
            scrollToElement(find(By.xpath("//textarea[contains(text(),'" + fieldName + "')]")));
            return isDisplayed(By.xpath("//textarea[contains(text(),'" + fieldName + "')]"));
        }
        return false;
    }
    public boolean L700TechnologyValidation(String fieldName) throws Exception{
        WebElement fieldObj = inputBoxDataBySname("S:On-Air Date - "+fieldName);
        String value = getDocumentTextByIdJs(fieldObj.getAttribute("id"));
        if(!value.isEmpty()) {
            return isDisplayed(By.xpath("//textarea[contains(text(),'" + fieldName + "')]"));
        }
        return false;
    }
    public boolean L1900TechnologyValidation(String fieldName) throws Exception{
        WebElement fieldObj = inputBoxDataBySname("S:On-Air Date - "+fieldName);
        String value = getDocumentTextByIdJs(fieldObj.getAttribute("id"));
        if(!value.isEmpty()) {
            return isDisplayed(By.xpath("//textarea[contains(text(),'" + fieldName + "')]"));
        }
        return false;
    }
    public boolean L2100TechnologyValidation(String fieldName) throws Exception{
        WebElement fieldObj = inputBoxDataBySname("S:On-Air Date - "+fieldName);
        String value = getDocumentTextByIdJs(fieldObj.getAttribute("id"));
        if(!value.isEmpty()) {
            return isDisplayed(By.xpath("//textarea[contains(text(),'" + fieldName + "')]"));
        }
        return false;
    }
    public boolean L2100Aws3TechnologyValidation(String fieldName) throws Exception{
        WebElement fieldObj = inputBoxDataBySname("S:On-Air Date - "+fieldName);
        String value = getDocumentTextByIdJs(fieldObj.getAttribute("id"));
        if(!value.isEmpty()) {
            return isDisplayed(By.xpath("//textarea[contains(text(),'" + fieldName + "')]"));
        }
        return false;
    }
    public boolean L2500TechnologyValidation(String fieldName) throws Exception{
        WebElement fieldObj = inputBoxDataBySname("S:On-Air Date - "+fieldName);
        String value = getDocumentTextByIdJs(fieldObj.getAttribute("id"));
        if(!value.isEmpty()) {
            return isDisplayed(By.xpath("//textarea[contains(text(),'" + fieldName + "')]"));
        }
        return false;
    }
    public boolean N600TechnologyValidation(String fieldName) throws Exception{
        WebElement fieldObj = inputBoxDataBySname("S:On-Air Date - "+fieldName);
        String value = getDocumentTextByIdJs(fieldObj.getAttribute("id"));
        if(!value.isEmpty()) {
            return isDisplayed(By.xpath("//textarea[contains(text(),'" + fieldName + "')]"));
        }
        return false;
    }
    public boolean N2500TechnologyValidation(String fieldName) throws Exception{
        WebElement fieldObj = inputBoxDataBySname("S:On-Air Date - "+fieldName);
        String value = getDocumentTextByIdJs(fieldObj.getAttribute("id"));
        if(!value.isEmpty()) {
            return isDisplayed(By.xpath("//textarea[contains(text(),'" + fieldName + "')]"));
        }
        return false;
    }
    public boolean U1900TechnologyValidation(String fieldName) throws Exception{
        WebElement fieldObj = inputBoxDataBySname("S:On-Air Date - "+fieldName);
        String value = getDocumentTextByIdJs(fieldObj.getAttribute("id"));
        if(!value.isEmpty()) {
            return isDisplayed(By.xpath("//textarea[contains(text(),'" + fieldName + "')]"));
        }
        return false;
    }
    public boolean gsmTechnologyValidation(String fieldName) throws Exception{
        WebElement fieldObj = inputBoxDataBySname("S:On-Air Date - "+fieldName);
        String value = getDocumentTextByIdJs(fieldObj.getAttribute("id"));
        if(!value.isEmpty()) {
            return isDisplayed(By.xpath("//textarea[contains(text(),'" + fieldName + "')]"));
        }
        return false;
    }
    public boolean U2100TechnologyValidation(String fieldName) throws Exception {
        scrollToElement(inputBoxDataBySname("S:On-Air Date - " + fieldName));
        WebElement fieldObj = inputBoxDataBySname("S:On-Air Date - " + fieldName);
        String value = getDocumentTextByIdJs(fieldObj.getAttribute("id"));
        WebElement fieldObj1 = inputBoxDataBySname("S:Off-Air Date - " + fieldName);
        String value1 = getDocumentTextByIdJs(fieldObj1.getAttribute("id"));
        if (!value.isEmpty() && !value1.isEmpty()) {
            return true;
        } else {
            scrollToElement(find(By.xpath("//textarea[contains(text(),'" + fieldName + "')]")));
            return isDisplayed(By.xpath("//textarea[contains(text(),'" + fieldName + "')]"));
        }
    }

    public void updateSiteName() throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        WebElement siteName = inputBoxDataBySname("S:Site Name");
        scrollToElement(siteName);
        clearInputBoxByElementAndSendKeys(siteName);
        setText(siteName,"Test");
        click(find(applyButton));
        waitUntilVisibleElement(find(okButton));
    }
    public boolean verifySiteDetailsUpdate(Users userDetails) throws Exception {
        waitForPageToLoad();
        String owner = userDetails.getNtCode();
        String currentDate = MiscHelpers.currentDateTime("MM/dd/yyyy");
        WebElement lastUpdatedBy = inputBoxDataBySname("S:Last Updated By");
        scrollToElement(lastUpdatedBy);
        String updated = getDocumentTextByIdJs(lastUpdatedBy.getAttribute("id"));
        System.out.println("Name::"+updated);
        boolean updatedBy = updated.contains(owner);
        System.out.println("boolean:: "+updatedBy);
        WebElement lastUpdatedDate = inputBoxDataBySname("S:Last Updated Date");
        scrollToElement(lastUpdatedDate);
        String getDate = getDocumentTextByIdJs(lastUpdatedDate.getAttribute("id"));
        System.out.println("Date::"+getDate);
        boolean updatedDate = getDate.contains(currentDate);
        System.out.println("boolean:: "+updatedDate);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        sleep(5);
        return updatedBy && updatedDate ;
    }
    public int setSiteCategory(String category) throws Exception {
        String parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        WebElement siteCategory = selectionBoxBySname("S:Site Category").get(0);
        scrollToElement(siteCategory);
        selectDropdownOption(siteCategory, category);
        click(find(applyButton));
        sleep(5);
        WebElement allSiteIds = inputBoxDataBySname("S:Count of BBU/BTS Sites Connected to Hub");
        int count = Integer.parseInt(allSiteIds.getAttribute("origval"));
        //int count = allSiteIds.getAttribute("origval").length();
        System.out.println(count);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
        return count;
    }
    public void assignSiteWithHub(String siteId) throws Exception {
        String parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        WebElement siteCategory = selectionBoxBySname("S:Site Category").get(0);
        scrollToElement(siteCategory);
        selectDropdownOption(siteCategory, "BBU/BTS");
        click(find(applyButton));
        sleep(5);
        dropDownDotsClick("S:Hub Site ID");
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        click(find(ringCodeSearch));
        setText(find(ringCodeSearch),siteId);
        click(find(ringCodeSearchButton));
        radioButtonClick("S:Site Code", siteId);
        quickClick(find(ringCodeSelectionOKButton));
        switchToSpecificWindow(parent1);
        click(find(applyButton));
        sleep(5);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
    }
    public int bbuCount() throws Exception{
        String parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        WebElement allSiteIds = inputBoxDataBySname("S:Count of BBU/BTS Sites Connected to Hub");
        int count = Integer.parseInt(allSiteIds.getAttribute("origval"));
        System.out.println("BBU Count After::"+count);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
        return count;
    }
    public String assignSiteWithAggregateRouter(String siteId) throws Exception {
        String parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        WebElement siteCategory = selectionBoxBySname("S:Site Category").get(0);
        scrollToElement(siteCategory);
        selectDropdownOption(siteCategory, "BBU/BTS");
        click(find(applyButton));
        sleep(5);
        dropDownDotsClick("S:Hub Site ID");
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        click(find(ringCodeSearch));
        setText(find(ringCodeSearch),siteId);
        click(find(ringCodeSearchButton));
        String tableList = tableDataList(3);
        List<String> siteCode = getDocumentTextListByXpathJs(tableList);
        String siteCodeId = siteCode.toString();
        System.out.println("Site Code is:: " + siteCodeId);
        click(find(cancelButton1));
        switchToSpecificWindow(parent1);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
        return siteCodeId;
    }
    public void selectSiteCategoryHub() throws Exception {
        parentWindow = switchToChildWindows();
        waitForPageToLoad();
        fullScreenChildWindow();
        WebElement sitecategory = selectionBoxBySname("S:Site Category").get(0);
        scrollToElement(sitecategory);
        selectDropdownOption(sitecategory, "Hub");
        click(find(applyButton));
        waitForPageToLoad();
    }

    public boolean hubClusterIdValidation() throws Exception {
        sleep(5);
        String value = inputBoxDataBySname("S:Hub Cluster ID").getAttribute("origval");
        if (!value.isEmpty()) {
            return true;
        }
        return false;

    }

    public boolean bbuBtsTabValidation() throws Exception {
        if (findAll(bbuBtsTab).size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean rfSectorTabValidation() throws Exception {
        if (findAll(rfSectorTab).size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public SiteTrackerPage goToSiteTracker() throws Exception {
        waitForPageToLoad();
        click(find(okButton));
        sleep(5);
        switchToSpecificWindow(parentWindow);
        return new SiteTrackerPage(driver);
    }

    public void selectSiteCategoryAggregateRouter() throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        WebElement sitecategory = selectionBoxBySname("S:Site Category").get(0);
        scrollToElement(sitecategory);
        selectDropdownOption(sitecategory, "Aggregate Router");
        click(find(applyButton));
        sleep(3);
    }

    public void assignSiteWithHub1(String siteId) throws Exception {
        waitForPageToLoad();
        WebElement sitecategory = selectionBoxBySname("S:Site Category").get(0);
        scrollToElement(sitecategory);
        dropDownDotsClick("S:Hub Site ID");
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        click(find(ringCodeSearch));
        setText(find(ringCodeSearch), siteId);
        click(find(ringCodeSearchButton));
        radioButtonClick("S:Site Code", siteId);
        quickClick(find(ringCodeSelectionOKButton));
        switchToSpecificWindow(parent1);
        click(find(applyButton));
        waitForPageToLoad();
    }

    public boolean allSiteIdAtHubIdValidation(String siteId, String siteId1) throws Exception {
        sleep(5);
        String value = inputBoxDataBySname("S:All Site IDs at Hub").getAttribute("origval");
        String value1 = siteId;
        String value2 = siteId1;
        if (value.contains(value1) && value.contains(value2)) {
            return true;
        } return false;
    }
    public void selectSiteCategoryBbuBts() throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        WebElement sitecategory = selectionBoxBySname("S:Site Category").get(0);
        scrollToElement(sitecategory);
        selectDropdownOption(sitecategory, "BBU/BTS");
        click(find(applyButton));
        waitForPageToLoad();
    }
    public int bbuBtsCount() throws Exception {
        WebElement allSiteIds = inputBoxDataBySname("S:Count of BBU/BTS Sites Connected to Hub");
        scrollToElement(allSiteIds);
        int count = Integer.parseInt(allSiteIds.getAttribute("origval"));
        System.out.println(count);
        return count;
    }
    public void assignBbuBtsSiteWithHub(String siteId) throws Exception {
        String parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        WebElement sitecategory = selectionBoxBySname("S:Site Category").get(0);
        scrollToElement(sitecategory);
        selectDropdownOption(sitecategory, "BBU/BTS");
        click(find(applyButton));
        sleep(5);
        dropDownDotsClick("S:Hub Site ID");
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        click(find(ringCodeSearch));
        setText(find(ringCodeSearch),siteId);
        click(find(ringCodeSearchButton));
        radioButtonClick("S:Site Code", siteId);
        quickClick(find(ringCodeSelectionOKButton));
        switchToSpecificWindow(parent1);
        click(find(applyButton));
        sleep(5);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
    }
    public int finalBbuBtsCount() throws Exception{
        waitForPageToLoad();
        WebElement allSiteIds = inputBoxDataBySname("S:Count of BBU/BTS Sites Connected to Hub");
        scrollToElement(allSiteIds);
        int count = Integer.parseInt(allSiteIds.getAttribute("origval"));
        System.out.println("BBU Count After::"+count);
        return count;
    }

    public void assignSiteWithRouter(String siteId) throws Exception {
        WebElement sitecategory = selectionBoxBySname("S:Site Category").get(0);
        scrollToElement(sitecategory);
        dropDownDotsClick("S:Hub Site ID");
        parentWindow1 = switchToChildWindows();
        fullScreenChildWindow();
        click(find(ringCodeSearch));
        setText(find(ringCodeSearch), siteId);
        click(find(ringCodeSearchButton));
        waitForPageToLoad();
    }
    public Boolean isDataPresentInTable() throws Exception {
        waitUntilVisibleElement(find(siteTrackerTableElement));
        WebElement ringTrackerTbElement = find(siteTrackerTableElement);
        List<WebElement> rows = getTableRows(ringTrackerTbElement);
        List<WebElement> cellData = getTableCellsByRow(rows.get(1));
        System.out.println("Data" + cellData.get(1).getText());
        //check if null check needed
        return !cellData.get(1).getText().isEmpty() && !cellData.get(1).getText().equals(" ");
    }
    public SiteTrackerPage goToSiteTrackerPage() throws Exception {
        click(find(ringCodeSelectionOKButton));
        switchToSpecificWindow(parentWindow1);
        click(find(applyButton));
        sleep(5);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        return new SiteTrackerPage(driver);
    }
    public SiteFopsPage goToFopsTab() throws Exception {
        parentWindow = switchToChildWindows();
        waitUntilVisibleElement(find(applyButton));
        sleep(5);
        click(find(fopsTab));
        waitForPageToLoad();
        fullScreen();
        return new SiteFopsPage(driver);
    }

    public String getParentWindow() {
        return parentWindow;
    }

    public void goToCabinetTrackerTab() throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        waitUntilVisibleElement(find(applyButton));
        scrollToElement(find(cabinetTrackerTab));
        click(find(cabinetTrackerTab));
        waitForPageToLoad();
    }

    public CabinetTrackerPage selectEditOption_CB() throws Exception {
        waitForPageToLoad();
        waitUntilVisibleElement(find(editOption));
        click(find(editOption));
        sleep(5);
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        return  new CabinetTrackerPage(driver);
    }

    public CabinetTrackerPage selectAddNewCabinetOption() throws Exception {
        waitForPageToLoad();
        waitUntilVisibleElement(find(addOption));
        click(find(addOption));
        return  new CabinetTrackerPage(driver);
    }


}
