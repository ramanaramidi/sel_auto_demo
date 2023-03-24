package web.mb.feature.power;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Site;
import org.testng.annotations.Test;
import pages.web.Tracker.AddCabinetPage;
import pages.web.Tracker.CabinetEquipmentTrackerPage;
import pages.web.Tracker.CabinetTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.power.PowerHelper;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;
import java.util.List;

public class VoltageBoosterTests extends BaseTest {
    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    Site Site_Active;
    Site cabinetID;
    AddCabinetPage addCabinetPage;
    CabinetEquipmentTrackerPage cabinetEquipmentTrackerPage;
    CabinetTrackerPage cabinetTrackerPage;
    SiteHelper siteHelper = new SiteHelper();
    String CabinetEquipmentID;
    String CabinetID;
    PowerHelper powerHelper = new PowerHelper();


    public VoltageBoosterTests() {
        if (envURL == null) {
            envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");
        }
        if (testSuite == null) {
            testSuite = "feature.xml";
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
        System.out.print("Cabinet ID is"+cabinetID.cabinet);
    }
    @Test(
            groups = {"Integration"},
            description = "Update CAB:AC Load Breaker Slots / Poles Used",
            priority = 2
    )
    public void verifyVoltageBoostersInstalled(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.verifyVoltageBoosters(),"CAB:Number Of Voltage Boosters Installed default value is '0'");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Add VoltageBooster",
            priority = 3
    )
    public void addVoltageBooster(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.selectAddNewCabinetOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        CabinetEquipmentID = cabinetEquipmentTrackerPage.addBattery(cabinetID.cabinet, "Voltage Booster");
        softAssert.assertContains(CabinetEquipmentID, cabinetID.cabinet, "Cabinet Equipment ID is created for Voltage Booster");
        cabinetEquipmentTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify Voltage Booster Fields are displayed",
            priority = 4
    )
    public void validateVoltageBoosterFieldsDisplayed(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
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
        cabinetEquipmentTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Update CABE:VLT Manufacturer / Model field value",
            priority = 5
    )
    public void verifyVLTManufacturerTypeValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(cabinetID.cabinet, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        String options = cabinetEquipmentTrackerPage.getVLTModelValues();
        softAssert.assertContains(options,"025","Model is Available");
        softAssert.assertContains(options,"026","Model is Available");
        softAssert.assertContains(options,"027","Model is Available");
        softAssert.assertContains(options,"028","Model is Available");
        cabinetEquipmentTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Update CABE:VLT Manufacturer / Model field value",
            priority = 6
    )
    public void updateVLTManufacturerType(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        String text = "Raycap V1";
        cabinetEquipmentTrackerPage.updateVLTManufacturerValue(text);
        softAssert.assertTrue(cabinetEquipmentTrackerPage.isFieldUpdatedWithModel(text), "CABE:VLT Manufacturer / Model is updated with selected Model");
        cabinetEquipmentTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify IF PR:VLT Operating Configuration Required = 'No' THEN CABE:VLT Operating Configuration is Disabled ",
            priority = 7
    )
    public void verifyIfOperatingConfigurationRequiredIsNo(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        String text = "Delta";
        cabinetEquipmentTrackerPage.updateVLTManufacturerValue(text);
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisabled("CABE:VLT Operating Configuration"), "CABE:VLT Operating Configuration is Disabled");
        cabinetEquipmentTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify IF PR:VLT Operating Configuration Required = 'Yes' THEN CABE:VLT Operating Configuration is Enabled ",
            priority = 8
    )
    public void verifyIfOperatingConfigurationRequiredIsYes(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        String text = "Raycap V1";
        cabinetEquipmentTrackerPage.updateVLTManufacturerValue(text);
        softAssert.assertFalse(cabinetEquipmentTrackerPage.validateFieldIsDisabled("CABE:VLT Operating Configuration"), "CABE:VLT Operating Configuration is Enabled");
        cabinetEquipmentTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "verify CABE:VLT Status Dropdown Values",
            priority = 9
    )
    public void verifyVLTStatusDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        List<String> options = cabinetEquipmentTrackerPage.fieldDropDownValues("CABE:VLT Status");
        softAssert.assertTrue(options.contains("In Use"), "In Use should be available");
        softAssert.assertTrue(options.contains("Unused"), "Unused should be available");
        cabinetEquipmentTrackerPage.switchToTrackerPageByCancel(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Update CABE:VLT Status field Dropdown value",
            priority = 10
    )
    public void updateVLTStatusDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        String selectOption = "In Use";
        String response = cabinetEquipmentTrackerPage.updateDropdownFieldValue("CABE:VLT Status", selectOption);
        softAssert.assertTrue(response.contains(selectOption), "CABE:VLT Status is updated with selected value");
        cabinetEquipmentTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "verify CABE:VLT HCS (Hybrid Cable System) Type Dropdown Values",
            priority = 11
    )
    public void verifyVLTHCSTypeDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        List<String> options = cabinetEquipmentTrackerPage.fieldDropDownValues("CABE:VLT HCS (Hybrid Cable System) Type");
        softAssert.assertTrue(options.contains("HCS 1.0"), "HCS 1.0 should be available");
        softAssert.assertTrue(options.contains("HCS 2.0"), "HCS 2.0 should be available");
        softAssert.assertTrue(options.contains("HCS 6x24"), "HCS 2.0 should be available");
        cabinetEquipmentTrackerPage.switchToTrackerPageByCancel(parentWindow);
    }

    @Test(
            groups = {"Integration"},
            description = "Update CABE:VLT HCS (Hybrid Cable System) Type field Dropdown value",
            priority = 12
    )
    public void updateVLTHCSTypeDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        String selectOption = "HCS 1.0";
        String response = cabinetEquipmentTrackerPage.updateDropdownFieldValue("CABE:VLT HCS (Hybrid Cable System) Type", selectOption);
        softAssert.assertTrue(response.contains(selectOption), "CABE:VLT HCS (Hybrid Cable System) Type is updated with selected value");
        cabinetEquipmentTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify CABE:VLT Number Of Modules Installed\n" +
                    "it should allow numeric values ",
            priority = 13
    )
    public void verifyVLTNumberOfNumberOfModulesInstalledField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        softAssert.assertFalse(cabinetEquipmentTrackerPage.validateCabinetFieldAllowsText("CABE:VLT Number Of Modules Installed"), "Field doesn't allow Text input");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldAllowsNumeric("CABE:VLT Number Of Modules Installed"), "Field only allows Numeric Values");
        cabinetEquipmentTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Update CABE:VLT Number Of Modules Installed",
            priority = 14
    )
    public void verifyVLTMaxNumberOfModulesField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsReadOnly("CABE:VLT Max Number of Modules"), "CABE:VLT Max Number of Modules is read-only");
        softAssert.assertContains(cabinetEquipmentTrackerPage.getInputBoxFieldValue("CABE:VLT Max Number of Modules"), "3", "Field is auto-populated");
        cabinetTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Update CABE:VLT Number Of Modules Installed to '0' should thrown an error'",
            priority = 15
    )
    public void updateVLTNumberOfNumberOfModulesInstalledFieldWith0(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        cabinetEquipmentTrackerPage.updateNoOfModulesInstalled("CABE:VLT Number Of Modules Installed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.isPopAlertPresent(parentWindow), "CABE:VLT Number Of Modules Installed Field cannot be updated with 0");
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Update CABE:VLT Number Of Modules Installed",
            priority = 16
    )
    public void updateVLTNumberOfNumberOfModulesInstalledField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        softAssert.assertTrue(cabinetEquipmentTrackerPage.updateFieldValue("CABE:VLT Max Number of Modules", "CABE:VLT Number Of Modules Installed"), "Field is updated");
        cabinetEquipmentTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Updating CABE:VLT Number Of Modules Installed with above max value should throw an error",
            priority = 17
    )
    public void updateVLTNumberOfNumberOfModulesInstalledFieldWithAboveMaxValue(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        cabinetEquipmentTrackerPage.updatingFieldWithAboveMaxValue("CABE:VLT Max Number of Modules", "CABE:VLT Number Of Modules Installed");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.isPopAlertPresent(parentWindow), "CABE:VLT Number Of Modules Installed allows up to 3 only");
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify the Warning message is Displayed ",
            priority = 18
    )
    public void verifyWarningMessageIsDisplayed(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        softAssert.assertTrue(cabinetEquipmentTrackerPage.verifyWarningMessage(),"Warning Message is Displayed");
        cabinetEquipmentTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "verify CABE:VLT Operating Configuration Dropdown Values",
            priority = 19
    )
    public void verifyVLTOperatingConfigurationDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        List<String> options = cabinetEquipmentTrackerPage.fieldDropDownValues("CABE:VLT Operating Configuration");
        softAssert.assertTrue(options.contains("Single Mode"), "Single Mode should be available");
        softAssert.assertTrue(options.contains("Parallel Mode"), "Parallel Mode should be available");
        cabinetEquipmentTrackerPage.switchToTrackerPageByCancel(parentWindow);
    }

    @Test(
            groups = {"Integration"},
            description = "Update CABE:VLT Operating Configuration field Dropdown value",
            priority = 20
    )
    public void updateVLTOperatingConfigurationDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        String selectOption = "Parallel Mode";
        String response = cabinetEquipmentTrackerPage.updateDropdownFieldValue("CABE:VLT Operating Configuration", selectOption);
        softAssert.assertTrue(response.contains(selectOption), "CABE:VLT Operating Configuration is updated with selected value");
        cabinetEquipmentTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "verify CABE:VLT Is Busbar Installed Dropdown Values",
            priority = 21
    )
    public void verifyVLTIsBusbarInstalledDropdownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        List<String> options = cabinetEquipmentTrackerPage.fieldDropDownValues("CABE:VLT Is Busbar Installed");
        softAssert.assertTrue(options.contains("No"), "No should be available");
        softAssert.assertTrue(options.contains("Yes"), "Yes should be available");
        cabinetEquipmentTrackerPage.switchToTrackerPageByCancel(parentWindow);
    }
    @Test(
            groups = {"Integration"},
            description = "Verify If Operating Configuration is Single Mode - Busbar field value can be updated with 'No'",
            priority = 22
    )
    public void verifyVLTIsBusbarInstalledValueISNoForSingleMode(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        cabinetEquipmentTrackerPage.updateBusbarInstalledValue("Single Mode", "No");
        softAssert.assertFalse(cabinetEquipmentTrackerPage.isPopAlertPresent(parentWindow),"If Operating Configuration is Single Mode - Busbar field value can be updated with 'No'");
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify If Operating Configuration is Single Mode - Busbar field value cannot be updated with 'Yes'",
            priority = 23
    )
    public void verifyVLTIsBusbarInstalledValueISYesForSingleMode(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        cabinetEquipmentTrackerPage.updateBusbarInstalledValue("Single Mode", "Yes");
        softAssert.assertFalse(cabinetEquipmentTrackerPage.isPopAlertPresent(parentWindow),"If Operating Configuration is Single Mode - Busbar field value cannot be updated with 'Yes'");
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify If Operating Configuration is Single Mode - Busbar field value can be updated with 'No'",
            priority = 24
    )
    public void verifyVLTIsBusbarInstalledValueISYesForParallelMode(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        cabinetEquipmentTrackerPage.updateBusbarInstalledValue("Parallel Mode", "Yes");
        softAssert.assertFalse(cabinetEquipmentTrackerPage.isPopAlertPresent(parentWindow),"If Operating Configuration is Parallel Mode - Busbar field value can be updated with 'Yes'");
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify If Operating Configuration is Parallel Mode - Busbar field value cannot be updated with 'No'",
            priority = 25
    )
    public void verifyVLTIsBusbarInstalledValueISNoForParallelMode(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue(CabinetEquipmentID, "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        cabinetEquipmentTrackerPage.updateBusbarInstalledValue("Parallel Mode", "No");
        softAssert.assertFalse(cabinetEquipmentTrackerPage.isPopAlertPresent(parentWindow),"If Operating Configuration is Parallel Mode - Busbar field value cannot be updated with 'No'");
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify If CABE:Active field is select with No, Voltage Booster Details fields are grey out.",
            priority = 26
    )
    public void verifyIfCabeActiveIsSelectedNo(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue("SAUWP0UB_C1_AVB1_1", "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        cabinetEquipmentTrackerPage.updateDropdownFieldValue("CABE:Active","No");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateVLTManufactureFieldIsReadOnly(),"Field is Grayed out");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisabled("CABE:VLT Status"),"Field is Grayed out");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsReadOnly("CABE:VLT Number Of Modules Installed"),"Field is Grayed out");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisabled("CABE:VLT HCS (Hybrid Cable System) Type"),"Field is Grayed out");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisabled("CABE:VLT Operating Configuration"),"Field is Grayed out");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisabled("CABE:VLT Is Busbar Installed"),"Field is Grayed out");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.validateFieldIsDisabled("CABE:Active"),"Field is Grayed out");
        softAssert.assertTrue(cabinetEquipmentTrackerPage.verifyDetailID("CABE:VLT Voltage Detail ID"),"CABE:VLT Voltage Detail ID is apprehended with A");
        cabinetEquipmentTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify Other Cabinet Equipment with the same Xitor Class, the value of the deleted Voltage Booster record, will need to be re-generated",
            priority = 27
    )
    public void verifyOtherCabinetVoltageBooster(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetEquipmentTrackerPage = mainSideMenu.goToCabinetEquipmentTracker();
        cabinetEquipmentTrackerPage.searchForValue("SAUZGQNH_C1_VB1", "CABE:Cabinet Equipment ID");
        cabinetEquipmentTrackerPage.selectEditOption();
        String parentWindow = cabinetEquipmentTrackerPage.switchToCabinetPage();
        cabinetEquipmentTrackerPage.goToVoltageBoosterTab();
        softAssert.assertTrue(cabinetEquipmentTrackerPage.verifyOtherCabinetDetailIDValues("CABE:VLT Voltage Detail ID"),"The deleted Voltage Booster record has been re-generated for the other Cabinet Equipment ");
        cabinetEquipmentTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
}
