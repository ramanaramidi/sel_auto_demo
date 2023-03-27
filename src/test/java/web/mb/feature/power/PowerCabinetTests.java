package web.mb.feature.power;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Site;
import org.testng.annotations.Test;
import pages.web.Tracker.AddCabinetPage;
import pages.web.Tracker.CabinetTrackerPage;
import pages.web.Tracker.PowerCabinetPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.power.PowerHelper;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class PowerCabinetTests extends BaseTest {

    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    Site Site_Active;
    Site cabinetID;
    Site cabinetID1;
    SiteHelper siteHelper = new SiteHelper();
    PowerHelper powerHelper = new PowerHelper();
    CabinetTrackerPage cabinetTrackerPage;
    AddCabinetPage addCabinetPage;

    public PowerCabinetTests() {
        if (envURL == null) {
            envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");
        }
        if (testSuite == null) {
            testSuite = "TestRunner.xml";
        }
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
        mainSideMenu = loginPage.LoginAsUser(superUser);
    }
    private void generateCommonData() {
        String ringId_Active =
                "SA" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring_Active = new Ring("Active", ringId_Active, "Indoor Node");
        Site site_Active = new Site(ringId_Active, "Final Build", "Active Site");
        Site_Active =
                siteHelper.createActiveRingAndPrimaryActiveSite(ring_Active, site_Active);
        cabinetID = powerHelper.createNewCabinet(Site_Active,"Delta");
        powerHelper.updateCabinetModel(Site_Active,"471");
        cabinetID1 = powerHelper.createNewCabinet(Site_Active,"Ericsson");
        System.out.print("Cabinet ID is"+cabinetID.cabinet);
    }

    @Test(groups = {"Integration"}, description = "verifyCabinetTrackerFields", priority = 2)
    public void verifyCabinetTrackerFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("S:Site Code"), "SiteCode field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Cabinet ID"), "CAB:Cabinet ID field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Cabinet Detail ID"), "CAB:Cabinet Detail ID field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateField_IsDisplayed("CAB:Cabinet Vendor"), "CAB:Cabinet Vendor * field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Cabinet Type/Purpose"), "CAB:Cabinet Type/Purpose field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Cabinet Model"), "CAB:Cabinet Model field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Radio Cabinet Type"), "CAB:Radio Cabinet Type field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Cabinet Installation Location"), "CAB:Cabinet Installation Location field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Voltage"), "CAB:Voltage field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Cabinet Status"), "CAB:Cabinet Status field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Cooling System"), "CAB:Cooling System field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Cabinet IP Address"), "CAB:Cabinet IP Address field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Number of Rectifiers Supported (max) "), "CAB:Number of Rectifiers Supported (max)  field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Number of Battery Strings Supported"), "CAB:Number of Battery Strings Supported field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Max Rectifiers"), "CAB:Max Rectifiers field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Max Battery Strings"), "CAB:Max Battery Strings field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Max AC Load Breaker Slots / Poles Used"), "CAB:Max AC Load Breaker Slots / Poles Used field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Max DC Load Breaker Slots / Poles Used"), "CAB:Max DC Load Breaker Slots / Poles Used field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Number Of Voltage Boosters Installed"), "CAB:Number Of Voltage Boosters Installed field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Max PPC Amperage Capacity"), "CAB:Max PPC Amperage Capacity field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Created By"), "CAB:Created By field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Created Date"), "CAB:Created Date field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Modified By"), "CAB:Modified By field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Modified Date"), "CAB:Modified Date field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Active "), "CAB:Active  field is displayed in Cabinet Tracker");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyAddButton_CabinetFields", priority = 3)
    public void verifyAddButton_CabinetFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.clickAddButton();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.validateField_IsDisplayed("S:Site Code"), "SiteCode field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Cabinet ID"), "CAB:Cabinet ID field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Cabinet Detail ID"), "CAB:Cabinet Detail ID field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateField_IsDisplayed("CAB:Cabinet Vendor"), "CAB:Cabinet Vendor field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Cabinet Type/Purpose"), "CAB:Cabinet Type/Purpose field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Cabinet Model"), "CAB:Cabinet Model field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Radio Cabinet Type"), "CAB:Radio Cabinet Type field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Cabinet Installation Location"), "CAB:Cabinet Installation Location field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Voltage"), "CAB:Voltage field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Cabinet Status"), "CAB:Cabinet Status field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Cooling System"), "CAB:Cooling System field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Cabinet IP Address"), "CAB:Cabinet IP Address field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Number of Rectifiers Supported (max) "), "CAB:Number of Rectifiers Supported (max)  field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Number of Battery Strings Supported"), "CAB:Number of Battery Strings Supported field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Max Rectifiers"), "CAB:Max Rectifiers field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Max Battery Strings"), "CAB:Max Battery Strings field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Max AC Load Breaker Slots / Poles Used"), "CAB:Max AC Load Breaker Slots / Poles Used field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Max DC Load Breaker Slots / Poles Used"), "CAB:Max DC Load Breaker Slots / Poles Used field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Number Of Voltage Boosters Installed"), "CAB:Number Of Voltage Boosters Installed field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Max PPC Amperage Capacity"), "CAB:Max PPC Amperage Capacity field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Created By"), "CAB:Created By field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Created Date"), "CAB:Created Date field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Modified By"), "CAB:Modified By field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Modified Date"), "CAB:Modified Date field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Active "), "CAB:Active  field is displayed in Cabinet Tracker");
        addCabinetPage.isPopALert(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyCabinetVendorDropDownField", priority = 4)
    public void verifyCabinetVendorDropDownField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        String value = "Ericsson";
        softAssert.assertTrue(addCabinetPage.verifyDropDownField_CabinetVendor(value),"Dropdown Field is Present");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyCabinetModelDropDownField", priority = 5)
    public void verifyCabinetModelDropDownField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        String data = "354";
        softAssert.assertContains(addCabinetPage.verifyDotsDropDownField_CabinetModel(data),"2102","Cabinet Model dropdown is displayed and allows to select a value from options");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyCabinetInstallationLocationDropDownField", priority = 6)
    public void verifyCabinetInstallationLocationDropDownField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.verifyDropDownField_CabinetInstallationLocation(),"Cabinet Installation Location dropdown is displayed and allows to select a value from options");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyCabinetStatusDropDownField", priority = 7)
    public void verifyCabinetStatusDropDownField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        String status = "In Use";
        softAssert.assertTrue(addCabinetPage.verifyDropDownField_CabinetStatus(status),"Cabinet Status dropdown is displayed and allows to select a value from options");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyCabinetTypePurposeDropDownField", priority = 8)
    public void verifyCabinetTypePurposeDropDownField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        String data = "006";
        softAssert.assertContains(addCabinetPage.verifyDotsDropDownField_CabinetTypePurpose(data),"Battery Backup Unit","Cabinet Type Purpose dropdown is displayed and allows to select a value from options");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyRadioCabinetTypeDropDownField", priority = 9)
    public void verifyRadioCabinetTypeDropDownField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        String type = "Indoor";
        softAssert.assertTrue(addCabinetPage.verifyDropDownField_RadioCabinetType(type),"Cabinet Status dropdown is displayed and allows to select a value from options");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyCabinetVoltageDropDownField", priority = 10)
    public void verifyCabinetVoltageDropDownField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        String option = "24";
        softAssert.assertTrue(addCabinetPage.verifyDropDownField_CabinetVoltage(option),"Cabinet Voltage dropdown is displayed and allows to select a value from options");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }
}

