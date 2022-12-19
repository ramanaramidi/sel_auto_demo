package pages.web.Tracker.site;

import commons.constants.Constants;
import commons.objects.Site;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;
import pages.web.Tracker.site.SiteTrackerPage;

public class AddSitePage extends BasePage {
    public WebDriver driver;
    public AddSitePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
    String parentWindowHolder = "";
    public By applyButton = By.xpath("//input[@id='btnApply']");
    public By okButton = By.xpath("//input[@id='btnOK']");
    public By cancelButton = By.xpath("//input[@id='btnCancel']");
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
    public By documentOptions =By.id("btnoptionsGroupOpener5");
    public By documentDeleteOption = By.id("itemDelete5");
    public By documentsTab = By.id("tabName5");
    public By documentsTabCounter = By.id("tabCounter5");

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
        buttonClick("OK",4);
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
        waitUntilVisibleElement(selectionBoxBySname("S:Do Not Use Spectrum Spatial API").get(0));
        String currentValue = selectionBoxBySname("S:Do Not Use Spectrum Spatial API").get(0).getText();
        parentWindowHolder = parent1;
        return currentValue.contains(value);
    }

    public void setNoSpectrumCallTo(String value) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(10);
        click(find(intergationAdminSite));
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
        click(lockByLabelText("S:S:Site Longitude").get(0));
        click(find(siteLatitudeGmapButton));
        setText(find(siteNewCoordinateLatVal),Constants.VALID_LATITUDE);
        setText(find(siteNewCoordinateLongVal),Constants.VALID_LONGITUDE);
        buttonClick("OK", 4);
        click(find(applyButton));
    }

    public Boolean verifyGeoLocationDetailsIsPresent() throws Exception {
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


        click(find(okButton));
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

}
