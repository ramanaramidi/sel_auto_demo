package web.mb.feature.fops;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Site;
import org.testng.annotations.Test;
import pages.web.Tracker.site.SiteFopsPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class FOPSOtherAccessTests extends BaseTest {

    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    SiteFopsPage siteFopsPage;
    MiscHelpers miscHelpers;
    Site Site_Active;
    SiteHelper siteHelper = new SiteHelper();

    public FOPSOtherAccessTests() {
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
    @Test(groups = {"Integration"}, description = "verifyOtherAccessFields", priority = 2)
    public void verifyOtherAccessFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Type (Name)"), "S:Other Access Type (Name) field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Gate Combo"), "S:Other Access Gate Combo field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Keys"), "S:Other Access Keys field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Description"), "S:Other Access Description field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Notes"), "S:Other Access Notes field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Notes History"), "S:Other Access Notes History field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access 24x7"), "S:Other Access 24x7 field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Notification Required"), "S:Other Access Notification Required field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Notification Period"), "S:Other Access Notification Period field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Modified By"), "S:Other Access Modified By field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Modified Date"), "S:Other Access Modified Date field is displayed in Telco Access Details section");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyOtherAccessDateFields", priority = 3)
    public void verifyOtherAccessDateFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Sunday - Start"),"S:Other Access Sunday - Start - Start field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Monday - Start"),"S:Other Access Monday - Start field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Tuesday - Start"),"S:Other Access Tuesday - Start field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Wednesday - Start"),"S:Other Access Wednesday - Start field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Thursday - Start"),"S:Other Access Thursday - Start field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Friday - Start"),"S:Other Access Friday - Start field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Saturday - Start"),"S:Other Access Saturday - Start field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Sunday - End"),"S:Other Access Sunday - End field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Monday - End"),"S:Other Access Monday - End field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Tuesday - End"),"S:Other Access Tuesday - End field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Wednesday - End"),"S:Other Access Wednesday - End field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Thursday - End"),"S:Other Access Thursday - End field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Friday - End"),"S:Other Access Friday - End field is displayed in Telco Access Details section");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:Other Access Saturday - End"),"S:Other Access Saturday - End field is displayed in Telco Access Details section");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyOtherAccess_TextFields", priority = 4)
    public void verifyOtherAccess_TextFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertContains(siteFopsPage.verifyTextFields("S:Other Access Type (Name)"),"FOPS Other Access Testing","S:Other Access Type (Name) is Text Field");
        softAssert.assertContains(siteFopsPage.verifyTextFields("S:Other Access Gate Combo"),"FOPS Other Access Testing","S:Other Access Gate Combo is Text Field");
        softAssert.assertContains(siteFopsPage.verifyTextFields("S:Other Access Keys"),"FOPS Other Access Testing","S:Other Access Keys is Text Field");
        softAssert.assertTrue(siteFopsPage.isFieldTextArea("S:Other Access Description"),"S:Other Access Description is Text Area Field");
        softAssert.assertContains(siteFopsPage.verifyTextFields("S:Other Access Notes"),"FOPS Other Access Testing","S:Other Access Notes is Text Field");
        softAssert.assertContains(siteFopsPage.verifyTextArea_OtherAccessFields("S:Other Access Notes History"),"Testing","S:Other Access Notes History is Text Area Field");
        softAssert.assertContains(siteFopsPage.verifyTextFields("S:Other Access Notification Period"),"FOPS Other Access Testing","S:Other Access Notification Period isText Field");
        softAssert.assertNotNull(siteFopsPage.validateFieldIsReadOnly("S:Other Access Modified By"),"S:Other Access Modified By is Read only");
        softAssert.assertContains(siteFopsPage.verifyTextFields("S:Other Access Modified Date"),MiscHelpers.currentDateTime("MM/dd/yyyy"),"S:Other Access Modified Date is Text Field");
        softAssert.assertTrue(siteFopsPage.verifyDropDownField_S247OtherAccess(),"$24*7 Other Access Drop Down");
        softAssert.assertContains(siteFopsPage.verifyNotificationCheckBox("S:Other Access Notification Required"),"checkbox","Other Access Notification Required CheckBox is Present");
        softAssert.assertTrue(siteFopsPage.verifyCheckBoxChecked_OtherAccess("S:Other Access Notification Required"),"Other Notification checkBox checked and saved");
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
                siteFopsPage.verifyFieldDate("S:Other Access Sunday - Start"),
                "00:00",
                "Telco Access Sunday start field starts with time 00:00"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Other Access Monday - Start"),
                "00:00",
                "Telco Access Monday start field starts with time 00:00"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Other Access Tuesday - Start"),
                "00:00",
                "Telco Access Tuesday start field starts with time 00:00"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Other Access Wednesday - Start"),
                "00:00",
                "Telco Access Wednesday start field starts with time 00:00"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Other Access Thursday - Start"),
                "00:00",
                "Telco Access Thursday start field starts with time 00:00"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Other Access Friday - Start"),
                "00:00",
                "Telco Access Friday start field starts with time 00:00"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Other Access Saturday - Start"),
                "00:00",
                "Telco Access Saturday start field starts with time 00:00"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Other Access Sunday - End"),
                "23:59",
                "Telco Access Sunday End field ends with time 23:59"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Other Access Monday - End"),
                "23:59",
                "Telco Access Monday End field ends with time 23:59"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Other Access Tuesday - End"),
                "23:59",
                "Telco Access Tuesday End field ends with time 23:59"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Other Access Wednesday - End"),
                "23:59",
                "Telco Access Wednesday End field ends with time 23:59"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Other Access Thursday - End"),
                "23:59",
                "Telco Access Thursday End field ends with time 23:59"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Other Access Friday - End"),
                "23:59",
                "Telco Access Friday End field ends with time 23:59"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldDate("S:Other Access Saturday - End"),
                "23:59",
                "Telco Access Saturday End field ends with time 23:59"
        );
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyOtherAccessDescriptionField", priority = 8)
    public void verifyOtherAccessDescriptionField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.verifyTextArea_OtherAccessDescription(),"Text Field is Present");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyOtherAccessNotesField", priority = 9)
    public void verifyOtherAccessNotesField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        String text = "Testing";
        softAssert.assertTrue(siteFopsPage.verifyTextField_NotesField("S:Other Access Notes",text),"Text Field limit Max Length is 4000 characters");
        softAssert.assertTrue(siteFopsPage.verifyTextArea_NotesHistory("S:Other Access Notes History",text),"Text from Other Access Notes field is moved into OtherAccess Notes History field");
        softAssert.assertTrue(siteFopsPage.verifyDateTimeStamped("S:Other Access Notes History"),"Other Access Notes History contains Username, Date and Time stamped");
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
        softAssert.assertTrue(siteFopsPage.verifyModifiedBy("S:Other Access Modified By",superUser),"Other Access ModifiedBy field should be pre populated with data");
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
        softAssert.assertTrue(siteFopsPage.verifyModifiedDate("S:Other Access Modified Date"),"Other Access Modified Date field should be pre populated with data");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyOtherAccessNotificationRequiredCheckBox", priority = 14)
    public void verifyOtherAccessNotificationRequiredCheckBox(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertContains(siteFopsPage.verifyNotificationCheckBox("S:Other Access Notification Required"),"checkbox","Other Access Notification Required CheckBox is Present");
        softAssert.assertTrue(siteFopsPage.verifyCheckBoxChecked_OtherAccess("S:Other Access Notification Required"),"Other Notification checkBox checked and saved");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyS247TelcoAccess", priority = 13)
    public void verifyS247OtherAccess(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.verifyDropDownField_S247OtherAccess(),"Dropdown Field is Present");
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }
}
