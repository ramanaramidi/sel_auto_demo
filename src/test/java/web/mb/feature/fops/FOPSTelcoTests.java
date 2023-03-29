package web.mb.feature.fops;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import java.lang.reflect.Method;

import commons.objects.Ring;
import commons.objects.Site;
import org.testng.annotations.Test;
import pages.web.Tracker.site.SiteFopsPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

public class FOPSTelcoTests extends BaseTest {

    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    SiteFopsPage siteFopsPage;
    MiscHelpers miscHelpers;
    Site Site_Active;
    SiteHelper siteHelper = new SiteHelper();

    public FOPSTelcoTests() {
        if (envURL == null) {
            envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");
        }
        if (testSuite == null) {
            testSuite = "TestRunner.xml";
        }
    }

    @Test(groups = { "Integration" }, description = "login", priority = 1)
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
        Site site_Active = new Site(ringId_Active, "Primary", "Active Site");
        Site_Active =
                siteHelper.createActiveRingAndPrimaryActiveSite(ring_Active, site_Active);
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoAccessFields", priority = 2)
    public void verifyTelcoAccessFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Gate Combo"), "Telco Access Gate Combo field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Keys"), "S:Telco Access Keys field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Demarc Location"), "S:Telco Demarc Location field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Provider Company Name"), "S:Telco Provider Company Name field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Description"), "S:Telco Access Description field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Notes"), "S:Telco Access Notes field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Notes History"), "S:Telco Access Notes History field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco AAV NID Location"), "S:Telco AAV NID Location field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco AAV Combo/Keys"), "S:Telco AAV Combo/Keys field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:24x7 Telco Access?"), "S:24x7 Telco Access? field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Notification Required"), "S:Telco Access Notification Required field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Notification Period"), "S:Telco Access Notification Period field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Modified By"), "S:Telco Access Modified By field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Modified Date"), "S:Telco Access Modified Date field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Provider Contact Number"), "S:Telco Provider Contact Number field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco TMO NID Location"), "S:Telco TMO NID Location field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco TMO Combo/Keys"), "S:Telco TMO Combo/Keys field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Notify FOPS for Transport Outage"), "S:Telco Notify FOPS for Transport Outage field is displayed in Telco Access Details section");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoAccessDateFields", priority = 3)
    public void verifyTelcoAccessDateFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Sunday - Start"),"S:Telco Access Sunday - Start field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Monday - Start"),"S:Telco Access Monday - Start field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Tuesday - Start"),"S:Telco Access Tuesday - Start field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Wednesday - Start"),"S:Telco Access Wednesday - Start field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Thursday - Start"),"S:Telco Access Thursday - Start field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Friday - Start"),"S:Telco Access Friday - Start field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Saturday - Start"),"S:Telco Access Saturday - Start field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Sunday - End"),"S:Telco Access Sunday - End field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Monday - End"),"S:Telco Access Monday - End field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Tuesday - End"),"S:Telco Access Tuesday - End field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Wednesday - End"),"S:Telco Access Wednesday - End field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Thursday - End"),"S:Telco Access Thursday - End field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Friday - End"),"S:Telco Access Friday - End field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Telco Access Saturday - End"),"S:Telco Access Saturday - End field is displayed in Telco Access Details section");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoAccessGateComboField", priority = 4)
    public void verifyTelcoAccessGateComboField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.verifyTextField("S:Telco Access Gate Combo"),"Text Field should ne 150 characters limit");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoAccessKeysField", priority = 5)
    public void verifyTelcoAccessKeysField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.verifyTextField_TelcoAccessKeys(),"Text Field should ne 50 characters limit");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoDemarcLocationField", priority = 6)
    public void verifyTelcoDemarcLocationField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.verifyTextField_TelcoDemarcLocation(),"Text Field should ne 50 characters limit");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoProviderCompanyNameField", priority = 7)
    public void verifyTelcoProviderCompanyNameField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.verifyTextField_TelcoProviderCompanyName(),"Text Field is Present");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoAccessDescriptionField", priority = 8)
    public void verifyTelcoAccessDescriptionField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        String text = "Testing";
        siteFopsPage.updateTextArea_TelcoAccessDescription(text);
        softAssert.assertTrue(siteFopsPage.verifyTextArea_TelcoAccessDescription(),"Text Field is Present");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoAccessNotesField", priority = 9)
    public void verifyTelcoAccessNotesField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        String text = "Testing";
        softAssert.assertTrue(siteFopsPage.verifyTextField_NotesField("S:Telco Access Notes",text),"Text Field limit Max Length is 4000 characters");
        softAssert.assertTrue(siteFopsPage.verifyTextArea_TelcoAccessNotesHistory(text),"Text from Telco Notes field is moved into Telco Notes History field");
        softAssert.assertTrue(siteFopsPage.verifyDateTimeStamped("S:Telco Access Notes History"),"Telco Notes History contains Username, Date and Time stamped");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoAccessNotesHistoryField", priority = 10)
    public void verifyTelcoAccessNotesHistoryField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        String text ="";
        softAssert.assertTrue(siteFopsPage.verifyTextArea_NotesHistory("S:Telco Access Notes History",text),"Text Area should be readonly");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoAAVNIDLocationField", priority = 11)
    public void verifyTelcoAAVNIDLocationField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.verifyTextField_TelcoAAVNIDLocation(),"Text Field should ne 50 characters limit");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoAAVComboKeysField", priority = 12)
    public void verifyTelcoAAVComboKeysField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.verifyTextField_TelcoAAVComboKeys(),"Text Field should ne 50 characters limit");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyS247TelcoAccess", priority = 13)
    public void verifyS247TelcoAccess(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.isFieldDropDown("S:24x7 Telco Access?"),"Dropdown Field is Present");
        softAssert.assertNotNull(siteFopsPage.fieldDropDownValues("S:24x7 Telco Access?"),"S:PWR Door Alarm Configured should not be null");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoAccessNotificationRequiredCheckBox", priority = 14)
    public void verifyTelcoAccessNotificationRequiredCheckBox(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertContains(siteFopsPage.verifyNotificationCheckBox("S:Telco Access Notification Required"),"checkbox","Telco Access Notification Required CheckBox is Present");
        softAssert.assertTrue(siteFopsPage.verifyCheckBoxChecked_TelcoAccess("S:Telco Access Notification Required"),"Telco Notification checkBox checked and saved");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoAccessNotificationPeriodField", priority = 15)
    public void verifyTelcoAccessNotificationPeriodField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.verifyTextField_TelcoAccessNotificationPeriod(),"Text Field is present");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoAccessModifiedByField", priority = 16)
    public void verifyTelcoAccessModifiedByField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.verifyModifiedBy("S:Telco Access Modified By",superUser),"Telco Access ModifiedBy field should be pre populated with data");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoAccessModifiedDateField", priority = 17)
    public void verifyTelcoAccessModifiedDateField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.verifyModifiedDate("S:Telco Access Modified Date"),"Telco Access Modified Date field should be pre populated with data");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoProviderContactNumberField", priority = 18)
    public void verifyTelcoProviderContactNumberField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.verifyTextField_TelcoProviderContactNumber(),"Text Field is Present");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoTMONIDLocationField", priority = 19)
    public void verifyTelcoTMONIDLocationField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.verifyTextField_TelcoTMONIDLocation(),"Text Field should ne 50 characters limit");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyTelcoTMOComboKeysField", priority = 20)
    public void verifyTelcoTMOComboKeysField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.verifyTextField_TelcoTMOComboKeysField(),"Text Field should ne 50 characters limit");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "verifyTelcoNotifyFopsForTransportOutageField", priority = 21)
    public void verifyTelcoNotifyFopsForTransportOutageField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertContains(siteFopsPage.verifyNotificationCheckBox("S:Telco Notify FOPS for Transport Outage"),"checkbox","Telco Access Notification Required CheckBox is Present");
        softAssert.assertTrue(siteFopsPage.verifyCheckBoxChecked_TelcoAccess("S:Telco Notify FOPS for Transport Outage"),"Telco Notify FOPS checkBox checked and saved");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "verifyTelcoAccessDatesField",
            priority = 22
    )
    public void verifyTelcoAccessDatesField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Telco Access Sunday - Start"),
                "00:00",
                "Telco Access Sunday start field starts with time 00:00"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Telco Access Monday - Start"),
                "00:00",
                "Telco Access Monday start field starts with time 00:00"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Telco Access Tuesday - Start"),
                "00:00",
                "Telco Access Tuesday start field starts with time 00:00"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Telco Access Wednesday - Start"),
                "00:00",
                "Telco Access Wednesday start field starts with time 00:00"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Telco Access Thursday - Start"),
                "00:00",
                "Telco Access Thursday start field starts with time 00:00"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Telco Access Friday - Start"),
                "00:00",
                "Telco Access Friday start field starts with time 00:00"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Telco Access Saturday - Start"),
                "00:00",
                "Telco Access Saturday start field starts with time 00:00"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Telco Access Sunday - End"),
                "23:59",
                "Telco Access Sunday End field ends with time 23:59"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Telco Access Monday - End"),
                "23:59",
                "Telco Access Monday End field ends with time 23:59"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Telco Access Tuesday - End"),
                "23:59",
                "Telco Access Tuesday End field ends with time 23:59"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Telco Access Wednesday - End"),
                "23:59",
                "Telco Access Wednesday End field ends with time 23:59"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Telco Access Thursday - End"),
                "23:59",
                "Telco Access Thursday End field ends with time 23:59"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Telco Access Friday - End"),
                "23:59",
                "Telco Access Friday End field ends with time 23:59"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Telco Access Saturday - End"),
                "23:59",
                "Telco Access Saturday End field ends with time 23:59"
        );
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(
            groups = { "Integration" },
            description = "verifyTelcoAccessStartDateField_Updated",
            priority = 23
    )
    public void verifyTelcoAccessStartDateField_Updated(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        siteFopsPage.updateFieldStartDate("S:Telco Access Sunday - Start");
        softAssert.assertContains(
                siteFopsPage.verifyFieldStartDate("S:Telco Access Sunday - Start"),
                "14:00",
                "Telco Access Sunday start field starts with time 14:00"
        );
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(
            groups = { "Integration" },
            description = "verifyTelcoAccessEndDateField_Updated",
            priority = 24
    )
    public void verifyTelcoAccessEndDateField_Updated(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        siteFopsPage.updateFieldEndDate("S:Telco Access Sunday - End");
        softAssert.assertContains(
                siteFopsPage.verifyFieldEndDate("S:Telco Access Sunday - End"),
                "22:00",
                "Telco Access Sunday start field starts with time 22:00"
        );
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
}
