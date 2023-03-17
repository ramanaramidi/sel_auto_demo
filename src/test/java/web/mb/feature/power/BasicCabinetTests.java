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
                "SA" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring_Active = new Ring("Active", ringId_Active, "Indoor Node");
        Site site_Active = new Site(ringId_Active, "Final Build", "Active Site");
        Site_Active =
                siteHelper.createActiveRingAndPrimaryActiveSite(ring_Active, site_Active);
        cabinetID = powerHelper.createNewCabinet(Site_Active,"Delta");
        powerHelper.updateCabinetModel(Site_Active,"471");
        cabinetID1 = powerHelper.createNewCabinet(Site_Active,"Delta");
        powerHelper.updateCabinetModel(Site_Active,"1188");
    }

    @Test(
            groups = {"Integration"},
            description = "Assign Mandatory Fields for cabinet",
            priority = 2
    )
    public void assignMandatoryFieldsForCabinet(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        addNewSite.goToCabinetTrackerTab();
        String parentWindow = addNewSite.getParentWindow();
        cabinetTrackerPage = addNewSite.selectAddNewCabinetOption();
        String response = cabinetTrackerPage.addMandatoryFields();
        softAssert.assertContains(response, Site_Active.siteId, "Cabinet ID is created");
        cabinetTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify Created By and Create Date field controls",
            priority = 3
    )
    public void validateCreatedByAndCreatedDateFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.validateFieldIsReadOnly("CAB:Created By"), "CAB:Created By is read only");
        softAssert.assertTrue(addCabinetPage.verifyDateTimeFormat("CAB:Created Date"),"Date format  month, date, year in mm/dd/yyyy  and Time hh:mm");
        cabinetTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify Created By and Create Date is updated with User an Date",
            priority = 4
    )
    public void verifyCreatedByAndCreatedDateFieldUpdated(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.getCreatedByAndCreatedDate(superUser), "Created By and Created Date are Updated");
        cabinetTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify Modified By and Modified Date fields controls",
            priority = 5
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
            priority = 6
    )
    public void verifyModifiedByAndModifiedDateFieldUpdated(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.getModifiedByAndModifiedDate(superUser), "Created By and Created Date are Updated");
        cabinetTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify Cooling System Dropdown Values",
            priority = 7
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
        cabinetTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify CAB: Cooling System  should update with selected value",
            priority = 8
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
        cabinetTrackerPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify CAB: Number of Rectifiers Supported (max)\n" +
                    "it should allow numeric values ",
            priority = 9
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
//        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Verify CAB:Number of Battery Strings Supported\n" +
                    "it should allow numeric values",
            priority = 10
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
//        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "CAB:DC Load Breaker Slots / Poles Used\n" +
                    "it should allow numeric values ",
            priority = 11
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
//        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "Update CAB: Number of Rectifiers Supported (max)",
            priority = 12
    )
    public void updateNumberOfRectifierSupportedField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
//        softAssert.assertTrue(addCabinetPage.updateFieldValue("CAB:Max Rectifiers","CAB:Number of Rectifiers Supported (max) "),"Field is updated");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = {"Integration"},
            description = "Update CAB:Number of Battery Strings Supported",
            priority = 13
    )
    public void updateNumberOfBatteryStringsSupportedField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
//        softAssert.assertTrue(addCabinetPage.updateFieldValue("CAB:Max Battery Strings","CAB:Number of Battery Strings Supported"),"Field is updated");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();

    }

    @Test(
            groups = {"Integration"},
            description = "Update CAB:DC Load Breaker Slots / Poles Used",
            priority = 14
    )
    public void updateDCLoadBreakerSlotsField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(cabinetID.cabinet, "CAB:Cabinet ID");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        //softAssert.assertTrue(addCabinetPage.updateFieldValue("CAB:Max DC Load Breaker Slots / Poles Used","CAB:DC Load Breaker Slots / Poles Used"),"Field is updated");
        addCabinetPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(
            groups = {"Integration"},
            description = "CAB:AC Load Breaker Slots / Poles Used\n" +
                    "it should allow numeric values ",
            priority = 15
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
            priority = 16
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
}
