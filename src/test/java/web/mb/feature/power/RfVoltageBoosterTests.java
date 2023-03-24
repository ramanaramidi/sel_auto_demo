package web.mb.feature.power;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Site;
import commons.objects.Users;
import org.testng.annotations.Test;
import pages.web.Tracker.AddCabinetPage;
import pages.web.Tracker.CabinetEquipmentTrackerPage;
import pages.web.Tracker.CabinetTrackerPage;
import pages.web.Tracker.site.AddSitePage;
import pages.web.Tracker.site.SiteTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.power.PowerHelper;
import rest.site.SiteHelper;
import testData.UserData;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;
import java.util.List;

public class RfVoltageBoosterTests extends BaseTest {
    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    Site Site_Active;
    Site cabinetID;
    AddCabinetPage addCabinetPage;
    SiteTrackerPage siteTrackerPage;
    AddSitePage addNewSite;
    CabinetEquipmentTrackerPage cabinetEquipmentTrackerPage;
    CabinetTrackerPage cabinetTrackerPage;
    SiteHelper siteHelper = new SiteHelper();
    String CabinetEquipmentID;
    String CabinetID;
    PowerHelper powerHelper = new PowerHelper();
    Users rfEng = new Users();


    public RfVoltageBoosterTests() {
        if (envURL == null) {
            envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");
        }
        if (testSuite == null) {
            testSuite = "feature.xml";
        }
        rfEng = UserData.getSite_DevUserDetails(rfEng);
    }

    @Test(groups = {"Integration"}, description = "login", priority = 1)
    public void login(Method method) throws Exception {
        loginPage = new LoginPage(driver);
        if (alphaUser.getIsServiceAccount().equals("true")) {
            loginPage.doLogin(LoginOptionEnum.UN_EMAIL);
            loginPage.login(alphaUser);
        } else {
            loginPage.doLogin(LoginOptionEnum.SAML);
            String url = loginPage.getLoginUrl(alphaUser);
            if (url != null) {
                loginPage.launchUrl(url);
            }
        }
        generateCommonData();
        mainSideMenu = loginPage.LoginAsUser(rfEng);
    }

    private void generateCommonData() {
        String ringId_Active =
                "SA" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring_Active = new Ring("Active", ringId_Active, "Indoor Node");
        Site site_Active = new Site(ringId_Active, "Final Build", "Active Site");
        Site_Active =
                siteHelper.createActiveRingAndPrimaryActiveSite(ring_Active, site_Active);
        cabinetID = powerHelper.createNewCabinet(Site_Active, "Delta");
        powerHelper.updateCabinetModel(Site_Active, "471");
        System.out.print("Cabinet ID is" + cabinetID.cabinet);
    }

    @Test(
            groups = {"Integration"},
            description = "Update CAB:AC Load Breaker Slots / Poles Used",
            priority = 2
    )
    public void verifyVoltageBoostersInstalled(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        addCabinetPage = addNewSite.selectAddOption_CB();
        String parentWindow3 = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.verifyVoltageBoosters(), "CAB:Number Of Voltage Boosters Installed default value is '0'");
        cabinetTrackerPage = addCabinetPage.switchToCabinetTrackerPage(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow);
//        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Add VoltageBooster",
            priority = 2
    )
    public void addVoltageBooster(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectAddNewCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        CabinetEquipmentID = cabinetEquipmentTrackerPage.addBattery("Voltage Booster");
        softAssert.assertContains(CabinetEquipmentID, cabinetID.cabinet, "Cabinet Equipment ID is created for Voltage Booster");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByOkay(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify Voltage Booster Fields are displayed",
            priority = 3
    )
    public void validateVoltageBoosterFieldsDisplayed(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateIsFieldDisplayed("CAB:Cabinet ID"), "CAB:Cabinet ID is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateIsFieldDisplayed("CABE:Cabinet Equipment ID"), "CABE:Cabinet Equipment ID is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateIsFieldDisplayed("CABE:Cabinet Equipment Type"), "CABE:Cabinet Equipment Type is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CAB:Cabinet Vendor"), "CAB:Cabinet Vendor is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CAB:Cabinet Detail ID"), "CAB:Cabinet Detail ID is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:VLT Voltage Detail ID"), "CABE:VLT Voltage Detail ID is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CAB:Cabinet Type/Purpose"), "CAB:Cabinet Type/Purpose is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CAB:Cabinet Model"), "CAB:Cabinet Model is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:VLT Manufacturer / Model"), "CABE:VLT Manufacturer / Model is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:VLT Status"), "CABE:VLT Status is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:VLT Number Of Modules Installed"), "CABE:VLT Number Of Modules Installed is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:VLT HCS (Hybrid Cable System) Type"), "CABE:VLT HCS (Hybrid Cable System) Type is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:VLT Operating Configuration"), "CABE:VLT Operating Configuration is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:VLT Is Busbar Installed"), "CABE:VLT Is Busbar Installed is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:VLT Max Number of Modules"), "CABE:VLT Max Number of Modules is displayed");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByOkay(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        //softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Update CABE:VLT Manufacturer / Model field value",
            priority = 4
    )
    public void verifyVLTManufacturerTypeValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        String options = cabinetEquipmentTrackerPage.getVLTModelValues();
        softAssert.assertContains(options, "025", "Model is Available");
        softAssert.assertContains(options, "026", "Model is Available");
        softAssert.assertContains(options, "027", "Model is Available");
        softAssert.assertContains(options, "028", "Model is Available");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByOkay(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Update CABE:VLT Manufacturer / Model field value",
            priority = 5
    )
    public void updateVLTManufacturerType(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        String text = "Raycap V1";
        cabinetEquipmentTrackerPage.updateVLTManufacturerValue(text);
        softAssert.assertTrue(cabinetEquipmentTrackerPage.isFieldUpdatedWithModel(text), "CABE:VLT Manufacturer / Model is updated with selected Model");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByOkay(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        //softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "verify CABE:VLT Status Dropdown Values",
            priority = 6
    )
    public void verifyVLTStatusDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        List<String> options = cabinetEquipmentTrackerPage.fieldDropDownValues("CABE:VLT Status");
        softAssert.assertTrue(options.contains("In Use"), "In Use should be available");
        softAssert.assertTrue(options.contains("Unused"), "Unused should be available");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Update CABE:VLT Status field Dropdown value",
            priority = 7
    )
    public void updateVLTStatusDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        String selectOption = "In Use";
        String response = cabinetEquipmentTrackerPage.updateDropdownFieldValue("CABE:VLT Status", selectOption);
        softAssert.assertTrue(response.contains(selectOption), "CABE:VLT Status is updated with selected value");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByOkay(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "verify CABE:VLT HCS (Hybrid Cable System) Type Dropdown Values",
            priority = 8
    )
    public void verifyVLTHCSTypeDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        List<String> options = cabinetEquipmentTrackerPage.fieldDropDownValues("CABE:VLT HCS (Hybrid Cable System) Type");
        softAssert.assertTrue(options.contains("HCS 1.0"), "HCS 1.0 should be available");
        softAssert.assertTrue(options.contains("HCS 2.0"), "HCS 2.0 should be available");
        softAssert.assertTrue(options.contains("HCS 6x24"), "HCS 2.0 should be available");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Update CABE:VLT HCS (Hybrid Cable System) Type field Dropdown value",
            priority = 9
    )
    public void updateVLTHCSTypeDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        String selectOption = "HCS 1.0";
        String response = cabinetEquipmentTrackerPage.updateDropdownFieldValue("CABE:VLT HCS (Hybrid Cable System) Type", selectOption);
        softAssert.assertTrue(response.contains(selectOption), "CABE:VLT HCS (Hybrid Cable System) Type is updated with selected value");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByOkay(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify CABE:VLT Number Of Modules Installed\n" +
                    "it should allow numeric values ",
            priority = 10
    )
    public void verifyVLTNumberOfNumberOfModulesInstalledField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        softAssert.assertFalse(cabinetEquipmentTrackerPage.validateCabinetFieldAllowsText("CABE:VLT Number Of Modules Installed"), "Field doesn't allow Text input");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldAllowsNumeric("CABE:VLT Number Of Modules Installed"), "Field only allows Numeric Values");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByOkay(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Update CABE:VLT Number Of Modules Installed",
            priority = 11
    )
    public void updateVLTNumberOfNumberOfModulesInstalledField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        softAssert.assertTrue(cabinetEquipmentTrackerPage.updateFieldValue("CABE:VLT Max Number of Modules", "CABE:VLT Number Of Modules Installed"), "Field is updated");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByOkay(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Updating CABE:VLT Number Of Modules Installed with above max value should throw an error",
            priority = 12
    )
    public void updateVLTNumberOfNumberOfModulesInstalledFieldWithAboveMaxValue(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        cabinetEquipmentTrackerPage.updatingFieldWithAboveMaxValue("CABE:VLT Max Number of Modules", "CABE:VLT Number Of Modules Installed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.isPopAlertPresent(parentWindow3), "CABE:VLT Number Of Modules Installed allows up to 3 only");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow2);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "verify CABE:VLT Operating Configuration Dropdown Values",
            priority = 13
    )
    public void verifyVLTOperatingConfigurationDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        List<String> options = cabinetEquipmentTrackerPage.fieldDropDownValues("CABE:VLT Operating Configuration");
        softAssert.assertTrue(options.contains("Single Mode"), "Single Mode should be available");
        softAssert.assertTrue(options.contains("Parallel Mode"), "Parallel Mode should be available");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Update CABE:VLT Operating Configuration field Dropdown value",
            priority = 14
    )
    public void updateVLTOperatingConfigurationDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        String selectOption = "Parallel Mode";
        String response = cabinetEquipmentTrackerPage.updateDropdownFieldValue("CABE:VLT Operating Configuration", selectOption);
        softAssert.assertTrue(response.contains(selectOption), "CABE:VLT Operating Configuration is updated with selected value");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByOkay(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "verify CABE:VLT Is Busbar Installed Dropdown Values",
            priority = 15
    )
    public void verifyVLTIsBusbarInstalledDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        List<String> options = cabinetEquipmentTrackerPage.fieldDropDownValues("CABE:VLT Is Busbar Installed");
        softAssert.assertTrue(options.contains("No"), "No should be available");
        softAssert.assertTrue(options.contains("Yes"), "Yes should be available");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify If Operating Configuration is Single Mode - Busbar field value can be updated with 'No'",
            priority = 16
    )
    public void verifyVLTIsBusbarInstalledValueISNoForSingleMode(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        cabinetEquipmentTrackerPage.updateBusbarInstalledValue("Single Mode", "No");
        softAssert.assertFalse(cabinetEquipmentTrackerPage.isPopAlertPresent(parentWindow3), "If Operating Configuration is Single Mode - Busbar field value can be updated with 'No'");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow2);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify If Operating Configuration is Single Mode - Busbar field value cannot be updated with 'Yes'",
            priority = 17
    )
    public void verifyVLTIsBusbarInstalledValueISYesForSingleMode(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        cabinetEquipmentTrackerPage.updateBusbarInstalledValue("Single Mode", "Yes");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.isPopAlertPresent(parentWindow3), "If Operating Configuration is Single Mode - Busbar field value cannot be updated with 'Yes'");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow2);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify If Operating Configuration is Single Mode - Busbar field value can be updated with 'No'",
            priority = 18
    )
    public void verifyVLTIsBusbarInstalledValueISYesForParallelMode(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        cabinetEquipmentTrackerPage.updateBusbarInstalledValue("Parallel Mode", "Yes");
        softAssert.assertFalse(cabinetEquipmentTrackerPage.isPopAlertPresent(parentWindow3), "If Operating Configuration is Parallel Mode - Busbar field value can be updated with 'Yes'");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow2);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify If Operating Configuration is Parallel Mode - Busbar field value cannot be updated with 'No'",
            priority = 19
    )
    public void verifyVLTIsBusbarInstalledValueISNoForParallelMode(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        cabinetEquipmentTrackerPage.updateBusbarInstalledValue("Parallel Mode", "No");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.isPopAlertPresent(parentWindow3), "If Operating Configuration is Parallel Mode - Busbar field value cannot be updated with 'No'");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow2);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify If CABE:Active field is select with No, Voltage Booster Details fields are grey out.",
            priority = 19
    )
    public void verifyIfCabeActiveIsSelectedNo(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        cabinetEquipmentTrackerPage.updateDropdownFieldValue("CABE:Active", "No");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateVLTManufactureFieldIsReadOnly(), "Field is Grayed out");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisabled("CABE:VLT Status"), "Field is Grayed out");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsReadOnly("CABE:VLT Number Of Modules Installed"), "Field is Grayed out");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisabled("CABE:VLT HCS (Hybrid Cable System) Type"), "Field is Grayed out");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisabled("CABE:VLT Operating Configuration"), "Field is Grayed out");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisabled("CABE:VLT Is Busbar Installed"), "Field is Grayed out");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisabled("CABE:Active"), "Field is Grayed out");
        cabinetTrackerPage = cabinetEquipmentTrackerPage.switchToAddTrackerPageByOkay(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
       // softAssert.closeAssert();
    }
}