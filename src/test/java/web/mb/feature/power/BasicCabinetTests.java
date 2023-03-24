package web.mb.feature.power;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Site;
import org.testng.annotations.Test;
import pages.web.Tracker.AddCabinetPage;
import pages.web.Tracker.CabinetTrackerPage;
import pages.web.Tracker.site.AddSitePage;
import pages.web.Tracker.site.SiteTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.power.PowerHelper;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;
import java.util.List;

public class BasicCabinetTests extends BaseTest {
    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    Site Site_Active;
    Site Site_active;
    Site site;
    Site cabinet_ID;
    Site cabinetID;
    Site cabinetID1;
    SiteTrackerPage siteTrackerPage;
    AddSitePage addNewSite;
    CabinetTrackerPage cabinetTrackerPage;
    AddCabinetPage addCabinetPage;
    SiteHelper siteHelper = new SiteHelper();
    PowerHelper powerHelper = new PowerHelper();


    public BasicCabinetTests() {
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
                "SY" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring_Active = new Ring("Active", ringId_Active, "Indoor Node");
        Site site_Active = new Site(ringId_Active, "Final Build", "Active Site");
        Site_Active =
                siteHelper.createActiveRingAndPrimaryActiveSite(ring_Active, site_Active);
        cabinetID = powerHelper.createNewCabinet(Site_Active,"Delta");
        powerHelper.updateCabinetModel(Site_Active,"471");

        String ringId_active =
                "SA" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring_active = new Ring("Active", ringId_active, "Indoor Node");
        Site site_active = new Site(ringId_active, "Final Build", "Active Site");
        Site_active =
                siteHelper.createActiveRingAndPrimaryActiveSite(ring_active, site_active);
        cabinet_ID = powerHelper.createNewCabinet(Site_active,"Commscope");

        String ringIdActive =
                "SZ" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActive, "Indoor Node");
        Site siteActive = new Site(ringId_Active, "Final Build", "Active Site");
        site =
                siteHelper.createActiveRingAndPrimaryActiveSite(ringActive, siteActive);
        cabinetID1 = powerHelper.createNewCabinet(site,"Delta");
        powerHelper.updateCabinetModel(site,"1188");
    }

    @Test(
            groups = {"Integration"},
            description = "Assign Mandatory Fields for cabinet",
            priority = 2
    )
    public void assignMandatoryFieldsForCabinet(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectAddNewCabinetOption();
        String response = cabinetTrackerPage.addMandatoryFields();
        softAssert.assertContains(response, site.siteId+"_C2", "Cabinet ID is created");
        cabinetTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify Cabinet Fields",
            priority = 3
    )
    public void verifyFieldsForCabinet(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinet_ID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Radio Cabinet Type"),"Field is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Number of Rectifiers Supported (max) "),"Field is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Number of Battery Strings Supported"),"Field is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:AC Load Breaker Slots / Poles Used"),"Field is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:DC Load Breaker Slots / Poles Used"),"Field is Locked");
        softAssert.assertFalse(addCabinetPage.validateFieldIsLocked("CAB:Cabinet Model"),"CAB:Cabinet Model field is Unlocked");
        softAssert.assertFalse(addCabinetPage.validateFieldIsLocked("CAB:Cabinet Installation Location"),"CAB:Cabinet Installation Location field is Unlocked");
        softAssert.assertFalse(addCabinetPage.validateFieldIsLocked("CAB:Cabinet Status"),"CAB:Cabinet Status field is Unlocked");
        softAssert.assertFalse(addCabinetPage.validateFieldIsLocked("CAB:Cabinet IP Address"),"CAB:Cabinet IP Address field is Unlocked");
        softAssert.assertFalse(addCabinetPage.validateFieldIsLocked("CAB:Cabinet Type/Purpose"),"CAB:Cabinet Type/Purpose field is Unlocked");
        softAssert.assertFalse(addCabinetPage.validateFieldIsLocked("CAB:Voltage"),"CAB:Voltage field is Unlocked");
        softAssert.assertFalse(addCabinetPage.validateFieldIsLocked("CAB:Cooling System"),"CAB:Cooling System field is Unlocked");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "verify Cabinet Field Values",
            priority = 4
    )
    public void verifyCabinetFieldValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinet_ID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertContains(addCabinetPage.getFieldValue("CAB:Cabinet ID"), Site_active.siteId+"_C1","CAB:Cabinet ID is Auto generated with Site Code+'_C'+auto-generated sequential number" );
        softAssert.assertContains(addCabinetPage.getFieldValue("CAB:Cabinet ID"),"C1","CAB:Cabinet Detail ID is updated with C+auto-generated sequential number without Site Code");
        softAssert.assertContains(addCabinetPage.getSelectionFieldValue("CAB:Cabinet Status"),"In Use","CAB:Cabinet Status is auto populated with In Use");
        softAssert.assertContains(addCabinetPage.getFieldValue("CAB:Number Of Voltage Boosters Installed"),"0","CAB:Number Of Voltage Boosters Installed is auto populated with 0");
        softAssert.assertContains(addCabinetPage.getFieldValue("CAB:Max PPC Amperage Capacity"),"0","CAB:Max PPC Amperage Capacity is auto populated with 0");
        softAssert.assertContains(addCabinetPage.getSelectionFieldValue("CAB:Active "),"Yes","CAB:Active is auto populated with Yes");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify Created By and Create Date field controls",
            priority = 5
    )
    public void validateCreatedByAndCreatedDateFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.validateFieldIsReadOnly("CAB:Created By"), "CAB:Created By is read only");
        softAssert.assertTrue(addCabinetPage.verifyDateTimeFormat("CAB:Created Date"),"Date format  month, date, year in mm/dd/yyyy  and Time hh:mm");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify Created By and Create Date is updated with User an Date",
            priority = 6
    )
    public void verifyCreatedByAndCreatedDateFieldUpdated(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.getCreatedByAndCreatedDate(superUser), "Created By and Created Date are Updated");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify CAB:Cabinet Vendor & CAB:Cabinet Model Fields should be locked when the fields populated",
            priority = 7
    )
    public void verifyCabinetVendorANdCabinetModelFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertDoesNotContains(addCabinetPage.getSelectionFieldValue("CAB:Cabinet Vendor"),"","Field is populated");
        softAssert.assertDoesNotContains(addCabinetPage.getFieldUpdatedValue("CAB:Cabinet Model"),"","Field is populated");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Cabinet Vendor"),"CAB:Cabinet Vendor is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Cabinet Model"),"CAB:Cabinet Model is Locked");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify Modified By and Modified Date fields controls",
            priority = 8
    )
    public void validateModifiedByAndModifiedDateFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.validateFieldIsReadOnly("CAB:Modified By"), "CAB:Created By is read only");
        softAssert.assertTrue(addCabinetPage.verifyDateTimeFormat("CAB:Modified Date"),"Date format  month, date, year in mm/dd/yyyy  and Time hh:mm");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify Modified By and Modified Date is updated with user and date ",
            priority = 9
    )
    public void verifyModifiedByAndModifiedDateFieldUpdated(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.getModifiedByAndModifiedDate(superUser), "Created By and Created Date are Updated");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify Cooling System Dropdown Values",
            priority = 10
    )
    public void verifyCoolingSystemDropDownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        List<String> options = addCabinetPage.fieldDropDownValues("CAB:Cooling System");
        softAssert.assertTrue(options.contains("Air Conditioner"), "Air Conditioner should be available");
        softAssert.assertTrue(options.contains("DC Fans"), "DC Fans should be available");
        softAssert.assertTrue(options.contains("Thermoelectric Cooler"), "Thermoelectric Cooler should be available");
        softAssert.assertTrue(options.contains("Heat Exchanger"), "Heat Exchanger should be available");
        softAssert.assertTrue(options.contains("N/A"), "N/A should be available");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify CAB: Cooling System  should update with selected value",
            priority = 11
    )
    public void updateCoolingSystemValue(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        String selectOption = "Thermoelectric Cooler";
        String response = addCabinetPage.updateDropdownFieldValue("CAB:Cooling System",selectOption);
        softAssert.assertTrue(response.contains(selectOption), "Cooling System is update with selected value");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify Cabinet IP address field Format",
            priority = 12
    )
    public void verifyCabinetIPV4Format(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.verifyCabinetIPAddressFormat(),"The IP address format is valid");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify the Cabinet Tracker Fields based on the Cabinet Type associated to that Module ",
            priority = 13
    )
    public void verifyCabinetTrackerFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertFalse(addCabinetPage.validateFieldIsLocked("CAB:Number of Rectifiers Supported (max) "),"CAB:Number of Rectifiers Supported (max) field should be unlocked");
        softAssert.assertFalse(addCabinetPage.validateFieldIsLocked("CAB:Number of Battery Strings Supported"),"CAB:Number of Battery Strings Supported field should be unlocked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:AC Load Breaker Slots / Poles Used"),"CAB:AC Load Breaker Slots / Poles Used field should be locked");
        softAssert.assertFalse(addCabinetPage.validateFieldIsLocked("CAB:DC Load Breaker Slots / Poles Used"),"CAB:DC Load Breaker Slots / Poles Used field should be unlocked");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify CAB: Number of Rectifiers Supported (max)\n" +
                    "it should allow numeric values ",
            priority = 14
    )
    public void verifyNumberOfRectifierSupportedField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertFalse(addCabinetPage.validateCabinetFieldAllowsText("CAB:Number of Rectifiers Supported (max) "), "Field doesn't allow Text input");
        softAssert.assertTrue(addCabinetPage.validateFieldAllowsNumeric("CAB:Number of Rectifiers Supported (max) "), "Field only allows Numeric Values");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify CAB:Number of Battery Strings Supported\n" +
                    "it should allow numeric values",
            priority = 15
    )
    public void verifyNumberOfBatteryStringsSupportedField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertFalse(addCabinetPage.validateCabinetFieldAllowsText("CAB:Number of Battery Strings Supported"), "Field doesn't allow Text input");
        softAssert.assertTrue(addCabinetPage.validateFieldAllowsNumeric("CAB:Number of Battery Strings Supported"), "Field only allows Numeric Values");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "CAB:DC Load Breaker Slots / Poles Used\n" +
                    "it should allow numeric values ",
            priority = 16
    )
    public void verifyDCLoadBreakerSlotsField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertFalse(addCabinetPage.validateCabinetFieldAllowsText("CAB:DC Load Breaker Slots / Poles Used"), "Field doesn't allow Text input");
        softAssert.assertTrue(addCabinetPage.validateFieldAllowsNumeric("CAB:DC Load Breaker Slots / Poles Used"), "Field only allows Numeric Values");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Update CAB: Number of Rectifiers Supported (max)",
            priority = 17
    )
    public void updateNumberOfRectifierSupportedField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.updateFieldValue("CAB:Max Rectifiers","CAB:Number of Rectifiers Supported (max) "),"Field is updated");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Update CAB:Number of Battery Strings Supported",
            priority = 18
    )
    public void updateNumberOfBatteryStringsSupportedField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.updateFieldValue("CAB:Max Battery Strings","CAB:Number of Battery Strings Supported"),"Field is updated");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();

    }

    @Test(
            groups = {"Integration"},
            description = "Update CAB:DC Load Breaker Slots / Poles Used",
            priority = 19
    )
    public void updateDCLoadBreakerSlotsField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.updateFieldValue("CAB:Max DC Load Breaker Slots / Poles Used","CAB:DC Load Breaker Slots / Poles Used"),"Field is updated");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify CAB:Cabinet Type/Purpose is other than Radio Cabinet then CAB:Radio Cabinet Type should be locked",
            priority = 20
    )
    public void verifyRadioCabinetTypeFieldIfTypeOrPurposeIsNotRadioCabinet(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertDoesNotContains(addCabinetPage.getFieldUpdatedValue("CAB:Cabinet Type/Purpose"),"Radio Cabinet","Field is Does not contain Radio Cabinet");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Radio Cabinet Type"),"Field is Locked");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify CAB:Cabinet Type/Purpose is Radio Cabinet then CAB:Radio Cabinet Type should be unlocked",
            priority = 21
    )
    public void verifyRadioCabinetTypeFieldIfTypeOrPurposeIsRadioCabinet(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        addCabinetPage.updateEllipsisValue("CAB:Cabinet Type/Purpose","005");
        softAssert.assertContains(addCabinetPage.getFieldUpdatedValue("CAB:Cabinet Type/Purpose"),"Radio Cabinet","Field is Updated with Radio Cabinet");
        softAssert.assertFalse(addCabinetPage.validateFieldIsLocked("CAB:Radio Cabinet Type"),"Field is Unlocked");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify CAB:Cabinet Type/Purpose is AC Only Small Cell then verify that fields should be locked",
            priority = 22
    )
    public void verifyFieldsIfTypeOrPurposeIsACOnlySmallCell(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        addCabinetPage.updateEllipsisValue("CAB:Cabinet Type/Purpose","010");
        softAssert.assertContains(addCabinetPage.getFieldUpdatedValue("CAB:Cabinet Type/Purpose"),"AC Only Small Cell","Field is Updated with AC Only Small Cell");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Radio Cabinet Type"),"Field is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Voltage"),"Field is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Cooling System"),"Field is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Number of Rectifiers Supported (max) "),"Field is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Number of Battery Strings Supported"),"Field is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:AC Load Breaker Slots / Poles Used"),"Field is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:DC Load Breaker Slots / Poles Used"),"Field is Locked");
        softAssert.assertContains(addCabinetPage.getFieldValue("CAB:Max PPC Amperage Capacity"),"0","Field is Default back to original value");
        softAssert.assertEquals(addCabinetPage.getFieldUpdatedValue("CAB:Cabinet Model"),"","CAB:Cabinet Model is updated to Null");
        softAssert.assertEquals(addCabinetPage.getFieldValue("CAB:Max Rectifiers"),"","CAB:Max Rectifiers is updated to Null");
        softAssert.assertEquals(addCabinetPage.getFieldValue("CAB:Max Battery Strings"),"","CAB:Max Battery Strings is updated to Null");
        softAssert.assertEquals(addCabinetPage.getFieldValue("CAB:Max AC Load Breaker Slots / Poles Used"),"","CAB:Max AC Load Breaker Slots / Poles Used is updated to Null");
        softAssert.assertEquals(addCabinetPage.getFieldValue("CAB:Max DC Load Breaker Slots / Poles Used"),"","CAB:Max DC Load Breaker Slots / Poles Used is updated to Null");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify CAB:Cabinet Type/Purpose is other than AC Only Small Cell then following fields should be unlocked ",
            priority = 23
    )
    public void verifyFieldsIfTypeOrPurposeIsOtherThanACOnlySmallCell(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        addCabinetPage.updateEllipsisValue("CAB:Cabinet Type/Purpose","008");
        softAssert.assertDoesNotContains(addCabinetPage.getFieldUpdatedValue("CAB:Cabinet Type/Purpose"),"AC Only Small Cell","Field does not contain with AC Only Small Cell");
        softAssert.assertFalse(addCabinetPage.validateFieldIsLocked("CAB:Voltage"),"CAB:Voltage Field is unlocked");
        softAssert.assertFalse(addCabinetPage.validateFieldIsLocked("CAB:Cooling System"),"CAB:Cooling System Field is unlocked");
        softAssert.assertFalse(addCabinetPage.validateFieldIsLocked("CAB:Cabinet Model"),"CAB:Cabinet Model field is unlocked");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "CAB:AC Load Breaker Slots / Poles Used\n" +
                    "it should allow numeric values ",
            priority = 24
    )
    public void verifyACLoadBreakerSlotsField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID1.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertFalse(addCabinetPage.validateCabinetFieldAllowsText("CAB:AC Load Breaker Slots / Poles Used"), "Field doesn't allow Text input");
        softAssert.assertTrue(addCabinetPage.validateFieldAllowsNumeric("CAB:AC Load Breaker Slots / Poles Used"), "Field only allows Numeric Values");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Update CAB:AC Load Breaker Slots / Poles Used",
            priority = 25
    )
    public void updateACLoadBreakerSlotsField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID1.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.updateACLoadBreakerSlotsFieldValue("CAB:AC Load Breaker Slots / Poles Used"),"Field is Updated");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify If PR:CAB TYPE Voltage Booster Required  =Yes then Voltage Booster can be added ",
            priority = 26
    )
    public void verifyCabTypeVoltageBoostedIsYes(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID1.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        addCabinetPage.updateEllipsisValue("CAB:Cabinet Type/Purpose","008");
        softAssert.assertTrue(addCabinetPage.addVoltageBooster("Voltage Booster"),"Voltage Booster was added");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify If PR:CAB TYPE Voltage Booster Required =No then Voltage Booster can be added ",
            priority = 27
    )
    public void verifyCabTypeVoltageBoostedIsNo(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID1.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        addCabinetPage.updateEllipsisValue("CAB:Cabinet Type/Purpose","006");
        softAssert.assertTrue(addCabinetPage.addVoltageBooster("Voltage Booster"),"Voltage Booster was added");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify If CABE:Active field is select with No, Battery Details fields are grey out.",
            priority = 28
    )
    public void verifyFieldsIfCabeActiveIsSelectedNo(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID1.cabinet, "CAB:Cabinet ID");
        System.out.println(cabinetID1.cabinet);
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        addCabinetPage.updateDropdownFieldValue("CAB:Active ", "No");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Cabinet Installation Location"), "CAB:Cabinet Installation Location is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Cabinet Status"), "CAB:Cabinet Status is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Cabinet IP Address"), "CAB:Cabinet IP Address is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Cabinet Type/Purpose"), "CAB:Cabinet Type/Purpose is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Voltage"), "CAB:Voltage is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Cooling System"), "CAB:Cooling System is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Number of Rectifiers Supported (max) "), "CAB:Number of Rectifiers Supported (max)  is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Number of Battery Strings Supported"), "CAB:Number of Battery Strings Supported is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:AC Load Breaker Slots / Poles Used"), "CAB:AC Load Breaker Slots / Poles Used is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:DC Load Breaker Slots / Poles Used"), "CAB:DC Load Breaker Slots / Poles Used is Locked");
        softAssert.assertTrue(addCabinetPage.validateFieldIsLocked("CAB:Active "), "CABE:Active is Locked");
        softAssert.assertContains(addCabinetPage.getFieldValue("CAB:Cabinet ID"),site.siteId+"_AC1","CAB:Cabinet ID is value updated with Site Code+'_AC'+number");
        softAssert.assertTrue(addCabinetPage.verifyDetailID("CAB:Cabinet Detail ID"),"CAB:Cabinet Detail ID is apprehended with A");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Verify Other Cabinet ID with the same Xitor Class, the value of the deleted cabinet record, will need to be re-generated",
            priority = 29
    )
    public void verifyOtherCabinetDetailIds(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID1.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.verifyOtherCabinetDetailIDValues("CAB:Cabinet Detail ID"),"The deleted Cabinet ID record has been re-generated for the other Cabinet");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
}