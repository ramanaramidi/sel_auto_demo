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

public class RfCabinetTests  extends BaseTest {
    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    Site Site_Active;
    Site cabinetID;
    PowerHelper powerHelper = new PowerHelper();
    SiteTrackerPage siteTrackerPage;
    AddSitePage addNewSite;
    CabinetEquipmentTrackerPage cabinetEquipmentTrackerPage;
    CabinetTrackerPage cabinetTrackerPage;
    AddCabinetPage addCabinetPage;
    SiteHelper siteHelper = new SiteHelper();
    Users rfEng = new Users();
    String CabinetEquipmentID;


    public RfCabinetTests() {
        if (envURL == null) {
            envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");
        }
        if (testSuite == null) {
            testSuite = "feature.xml";
        }
        rfEng = UserData.getRfEngUserDetails(rfEng);
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
                "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring_Active = new Ring("Active", ringId_Active, "Indoor Node");
        Site site_Active = new Site(ringId_Active, "Final Build", "Active Site");
        Site_Active =
                siteHelper.createActiveRingAndPrimaryActiveSite(ring_Active, site_Active);
        cabinetID = powerHelper.createNewCabinet(Site_Active,"Delta");
        powerHelper.updateCabinetModel(Site_Active,"471");
        System.out.print("Cabinet ID is"+cabinetID.cabinet);
    }
    @Test(
            groups = {"Integration"},
            description = "verify CABE:Cabinet Equipment Tracker is Displayed",
            priority = 2
    )
    public void validateCabinetEquipmentTrackerIsDisplayed(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        softAssert.assertTrue(cabinetTrackerPage.cabinetEquipmentTrackerTabVerification(parentWindow2), "CABE:Cabinet Equipment Tracker is Displayed");
        cabinetTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "verify Cabinet Equipment Tracker fields are Displayed",
            priority = 3
    )
    public void validateCabinetEquipmentTrackerFields(Method method) throws Exception {
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
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateCabinetIDFieldIsDisplayed(), "CAB:Cabinet ID is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:Cabinet Equipment ID"), "CABE:Cabinet Equipment ID is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:Cabinet Equipment Type"), "CABE:Cabinet Equipment Type is displayed");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "verify Cabinet Equipment Type Dropdown Values",
            priority = 4
    )
    public void verifyCabinetEquipmentTypeDropdownValues(Method method) throws Exception {
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
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        List<String> options = cabinetEquipmentTrackerPage.fieldDropDownValues("CABE:Cabinet Equipment Type");
        softAssert.assertTrue(options.contains("Battery"), "Battery should be available");
        softAssert.assertTrue(options.contains("Rectifier"), "Rectifier should be available");
        softAssert.assertTrue(options.contains("Voltage Booster"), "Voltage Booster should be available");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Validate Add Battery without giving value in Number of Battery Strings Supported field",
            priority = 5
    )
    public void validateAddBatteryWithoutNumberOfBatteryStringsSupported(Method method) throws Exception {
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
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.verifyAddBattery( "Battery");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.isPopAlertPresent(parentWindow3),"Battery cannot be created without CAB:Number of Battery Strings Supported given");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow2);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow);
        //siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Update CAB:Number of Battery Strings Supported",
            priority = 6
    )
    public void updateNumberOfBatteryStringsSupportedField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        addCabinetPage = addNewSite.selectAddOption_CB();
        String parentWindow3 = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.validateFieldAllowsNumeric("CAB:Number of Battery Strings Supported"),"Field is updated");
        cabinetTrackerPage = addCabinetPage.switchToCabinetTrackerPage(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Add Battery",
            priority = 7
    )
    public void addBattery(Method method) throws Exception {
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
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        CabinetEquipmentID = cabinetEquipmentTrackerPage.addBattery( "Battery");
        softAssert.assertContains(CabinetEquipmentID, cabinetID.cabinet, "Cabinet Equipment ID is created for Battery");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Click on Cabinet equipment ID should redirect to CABE:General Info ",
            priority = 9
    )
    public void clickOnCabinetEquipmentID(Method method) throws Exception {
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
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        softAssert.assertTrue(cabinetEquipmentTrackerPage.generalInfoVerification(),"Clicking on CabinetEquipment ID is redirected to CABE:General Info");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "verify Active field Dropdown values",
            priority = 10
    )
    public void verifyActiveDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToBatteryTab();
        List<String> options = cabinetEquipmentTrackerPage.fieldDropDownValues("CABE:Active");
        softAssert.assertTrue(options.contains("No"), "No should be available");
        softAssert.assertTrue(options.contains("Yes"), "Yes should be available");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

//    @Test(
//            groups = {"Integration"},
//            description = "Update Active field Dropdown value",
//            priority = 11
//    )
//    public void updateActiveDropdownValues(Method method) throws Exception {
//        AssertionsUtil softAssert = new AssertionsUtil();
//        siteTrackerPage = mainSideMenu.goToS
//        iteTrackerPageSiteDev();
//        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
//        addNewSite = siteTrackerPage.selectEditOption();
//        addNewSite.goToCabinetTrackerTab();
//        String parentWindow = addNewSite.getParentWindow();
//        cabinetTrackerPage = addNewSite.selectEditOption_CB();
//        String parentWindow2 = addNewSite.getParentWindow();
//        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
//        cabinetEquipmentTrackerPage.selectEditCabinetOption();
//        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
//        cabinetEquipmentTrackerPage.goToBatteryTab();
//        String selectOption = "Yes";
//        String response = cabinetEquipmentTrackerPage.updateDropdownFieldValue("CABE:Active", selectOption);
//        softAssert.assertTrue(response.contains(selectOption), "CABE:Active is updated with selected value");
//        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
//        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
//        siteTrackerPage.switchToTracker(parentWindow);
//        softAssert.closeAssert();
//    }

    @Test(
            groups = {"Integration"},
            description = "Verify Battery Fields are displayed",
            priority = 12
    )
    public void validateBatteryFieldsDisplayed(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToBatteryTab();
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateIsFieldDisplayed("CAB:Cabinet ID"), "CAB:Cabinet ID is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateIsFieldDisplayed("CABE:Cabinet Equipment ID"), "CABE:Cabinet Equipment ID is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateIsFieldDisplayed("CABE:Cabinet Equipment Type"), "CABE:Cabinet Equipment Type is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CAB:Cabinet Vendor"), "CAB:Cabinet Vendor is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CAB:Cabinet Detail ID"), "CAB:Cabinet Detail ID is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:BAT Battery String ID"), "CABE:BAT Battery String ID is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CAB:Cabinet Type/Purpose"), "CAB:Cabinet Type/Purpose is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CAB:Cabinet Model"), "CAB:Cabinet Model is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:BAT String Type"), "CABE:BAT String Type is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:BAT Manufacturer"), "CABE:BAT Manufacturer is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:BAT Model"), "CABE:BAT Model is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:BAT Connected To"), "CABE:BAT Connected To is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:BAT Connected To Cabinets"), "CABE:BAT Connected To Cabinets is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:BAT Date of Manufacture"), "CABE:BAT Date of Manufacture is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:BAT Date of Installation"), "CABE:BAT Date of Installation is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:BAT Reason for Installation"), "CABE:BAT Reason for Installation is displayed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisplayed("CABE:BAT Status"), "CABE:BAT Status is displayed");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Update CABE:BAT String Type field value",
            priority = 13
    )
    public void updateBATStringType(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToBatteryTab();
        cabinetEquipmentTrackerPage.updateBATStringValue();
        softAssert.assertTrue(cabinetEquipmentTrackerPage.isValueUpdated("CABE:BAT String Type"), "CABE:BAT String Type is updated");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "verify CABE:BAT Manufacturer field dropdown values",
            priority = 14
    )
    public void verifyBATManufacturerDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToBatteryTab();
        List<String> options = cabinetEquipmentTrackerPage.fieldDropDownValues("CABE:BAT Manufacturer");
        softAssert.assertTrue(options.contains("Narada"), "Narada should be available");
        softAssert.assertTrue(options.contains("Enersys PowerSafe"), "Enersys PowerSafe should be available");
        softAssert.assertTrue(options.contains("Alpine"), "Alpine should be available");
        softAssert.assertTrue(options.contains("Northstar"), "Northstar should be available");
        softAssert.assertTrue(options.contains("GNB"), "GNB should be available");
        softAssert.assertTrue(options.contains("Unknown"), "Unknown should be available");
        softAssert.assertTrue(options.contains("GS"), "GS should be available");
        softAssert.assertTrue(options.contains("Marathon"), "Marathon should be available");
        softAssert.assertTrue(options.contains("BatteryCorp"), "BatteryCorp should be available");
        softAssert.assertTrue(options.contains("Deka Unigy"), "Deka Unigy should be available");
        softAssert.assertTrue(options.contains("FIAMM"), "FIAMM should be available");
        softAssert.assertTrue(options.contains("Telecel -PB (LiFePO)"), "Telecel -PB (LiFePO) should be available");
        softAssert.assertTrue(options.contains("Telecom Series"), "Telecom Series should be available");
        softAssert.assertTrue(options.contains("PowerSafe"), "PowerSafe should be available");
        softAssert.assertTrue(options.contains("Leoch"), "Leoch should be available");
        softAssert.assertTrue(options.contains("C&D"), "C&D should be available");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "update CABE:BAT Manufacturer field value",
            priority = 15
    )
    public void updateBATManufacturerDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToBatteryTab();
        String selectOption = "Narada";
        String response = cabinetEquipmentTrackerPage.updateDropdownFieldValue("CABE:BAT Manufacturer", selectOption);
        softAssert.assertTrue(response.contains(selectOption), "CABE:BAT Manufacturer is updated with selected value");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Update CABE:BAT Model field Value",
            priority = 16
    )
    public void updateBATModelValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToBatteryTab();
        cabinetEquipmentTrackerPage.updateBATModelValue();
        softAssert.assertTrue(cabinetEquipmentTrackerPage.isValueUpdated("CABE:BAT Model"),"CABE:BAT Model is updated");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    //    @Test(
//            groups = {"Integration"},
//            description = "Verify CABE:BAT Connected To field Values",
//            priority = 17
//    )
//    public void verifyBATConnectedToValues(Method method) throws Exception {
//        AssertionsUtil softAssert = new AssertionsUtil();
//        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
//        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
//        addNewSite = siteTrackerPage.selectEditOption();
//        addNewSite.goToCabinetTrackerTab();
//        String parentWindow = addNewSite.getParentWindow();
//        cabinetTrackerPage = addNewSite.selectEditOption_CB();
//        String parentWindow2 = addNewSite.getParentWindow();
//        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
//        cabinetEquipmentTrackerPage.selectEditCabinetOption();
//        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
//        cabinetEquipmentTrackerPage.goToBatteryTab();
//        String options =cabinetEquipmentTrackerPage.getBATConnectedToValues();
//        softAssert.assertContains(options,"N/A","N/A is Available");
//        softAssert.assertContains(options,"Not Connected","Not Connected is Available");
//        softAssert.assertContains(options,"C1","C1 is Available");
//        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
//        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
//        siteTrackerPage.switchToTracker(parentWindow);
//        softAssert.closeAssert();
//    }
    @Test(
            groups = {"Integration"},
            description = "Update CABE:BAT Connected To field Values",
            priority = 18
    )
    public void updateBATConnectedToValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToBatteryTab();
        String response = cabinetEquipmentTrackerPage.updateBATConnectedTo(Site_Active.siteId);
        softAssert.assertContains(response,"C1","CABE:BAT Connected To has updated with selected value");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "verify CABE:BAT Status Dropdown Values",
            priority = 19
    )
    public void verifyBATStatusDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToBatteryTab();
        List<String> options = cabinetEquipmentTrackerPage.fieldDropDownValues("CABE:BAT Status");
        softAssert.assertTrue(options.contains("In Use"), "In Use should be available");
        softAssert.assertTrue(options.contains("Unused"), "Unused should be available");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Update CABE:BAT Status field Dropdown value",
            priority = 20
    )
    public void updateBATStatusDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToBatteryTab();
        String selectOption = "In Use";
        String response = cabinetEquipmentTrackerPage.updateDropdownFieldValue("CABE:BAT Status", selectOption);
        softAssert.assertTrue(response.contains(selectOption), "CABE:BAT Status is updated with selected value");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify CABE:BAT Date of Manufacture that on clicking the date field, a calendar widget should open",
            priority = 21
    )
    public void verifyBATDateOfManufactureClickOpenCalender(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToBatteryTab();
        softAssert.assertTrue(cabinetEquipmentTrackerPage.clickingDateIconOpensCalendarWidget("CABE:BAT Date of Manufacture"),"After Clicking CABE:BAT Date of Manufacture icon Calendar widget opens");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify selecting the date from calendar gets displayed in the date field.",
            priority = 22
    )
    public void selectBATDateOfManufacture(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToBatteryTab();
        softAssert.assertTrue(cabinetEquipmentTrackerPage.selectDate("CABE:BAT Date of Manufacture"),"Date has been updated with selected Date");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify CABE:BAT Date of Manufacture is in date format ",
            priority = 23
    )
    public void verifyBATDateOfManufactureFieldFormat(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToBatteryTab();
        softAssert.assertTrue(cabinetEquipmentTrackerPage.verifyDateFormat("CABE:BAT Date of Manufacture"),"CABE:BAT Date of Manufacture is in date format");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify CABE:BAT Date of Installation that on clicking the date field, a calendar widget should open",
            priority = 24
    )
    public void verifyBATDateOfInstallationClickOpenCalender(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToBatteryTab();
        softAssert.assertTrue(cabinetEquipmentTrackerPage.clickingDateIconOpensCalendarWidget("CABE:BAT Date of Installation"),"After Clicking CABE:BAT Date of Manufacture icon Calendar widget opens");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify selecting the date from calendar gets displayed in the date field",
            priority = 25
    )
    public void selectBATDateOfInstallation(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToBatteryTab();
        softAssert.assertTrue(cabinetEquipmentTrackerPage.selectCurrentDate("CABE:BAT Date of Installation"),"Date has been selected");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify CABE:BAT Date of Manufacture is in date format ",
            priority = 26
    )
    public void verifyBATDateOfInstallationFieldFormat(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToBatteryTab();
        softAssert.assertTrue(cabinetEquipmentTrackerPage.verifyDateFormat("CABE:BAT Date of Installation"),"CABE:BAT Date of Installation is in date format");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "verify CABE:BAT Reason for Installation Dropdown Values",
            priority = 27
    )
    public void verifyBATReasonForInstallationDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToBatteryTab();
        List<String> options = cabinetEquipmentTrackerPage.fieldDropDownValues("CABE:BAT Reason for Installation");
        softAssert.assertTrue(options.contains("Aged"), "Aged should be available");
        softAssert.assertTrue(options.contains("Damaged"), "Damaged should be available");
        softAssert.assertTrue(options.contains("Stolen"), "Stolen should be available");
        softAssert.assertTrue(options.contains("New"), "New should be available");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Update CABE:BAT Reason for Installation field Dropdown value",
            priority = 28
    )
    public void updateBATReasonForInstallationDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTrackerPageSiteDev();
        siteTrackerPage.searchForValue(cabinetID.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectEditOption_CB();
        String parentWindow2 = addNewSite.getParentWindow();
        cabinetEquipmentTrackerPage = cabinetTrackerPage.cabinetEquipmentTrackerTab();
        cabinetEquipmentTrackerPage.selectEditCabinetOption();
        String parentWindow3 =cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToBatteryTab();
        String selectOption = "Damaged";
        String response = cabinetEquipmentTrackerPage.updateDropdownFieldValue("CABE:BAT Reason for Installation", selectOption);
        softAssert.assertTrue(response.contains(selectOption), "CABE:BAT Reason for Installation is updated with selected value");
        cabinetTrackerPage =  cabinetEquipmentTrackerPage.switchToAddTrackerPageByCancel(parentWindow3);
        siteTrackerPage = cabinetTrackerPage.switchToAddTrackerPage(parentWindow2);
        siteTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
}