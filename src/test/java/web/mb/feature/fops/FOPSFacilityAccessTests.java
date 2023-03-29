package web.mb.feature.fops;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Site;
import java.lang.reflect.Method;
import org.testng.annotations.Test;
import pages.web.Tracker.site.SiteFopsPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

public class FOPSFacilityAccessTests extends BaseTest {

    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    Site Site_Active;
    SiteHelper siteHelper = new SiteHelper();
    SiteFopsPage siteFopsPage;

    public FOPSFacilityAccessTests() {
        if (envURL == null) {
            envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");
        }
        if (testSuite == null) {
            testSuite = "feature.xml";
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
    @Test(
            groups = { "Integration" },
            description = "Validate Facility Access fields are displayed",
            priority = 2
    )
    public void validateFacilityAccessFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        softAssert.assertTrue(
                siteFopsPage.validateField_IsDisplayed("S:Site Access Details"),
                "S:Site Access Details is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateField_IsDisplayed(
                        "S:Site Access Details Updated by "
                ),
                "S:Site Access Details Updated by  is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateField_IsDisplayed("S:Directions to Site"),
                "S:Directions to Site is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Facility Access Gate Combo"),
                "S:Facility Access Gate Combo is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Facility Access Keys"),
                "S:Facility Access Keys is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Facility Elevator Available"),
                "S:Facility Elevator Available is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Equipment Shelter at Site?"),
                "S:Equipment Shelter at Site? is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Vault Site"),
                "S:Vault Site is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Facility Access Description"),
                "S:Facility Access Description is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Facility Access Notes"),
                "S:Facility Access Notes is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Facility Access Notes History"),
                "S:Facility Access Notes History is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:24x7 Site Access?"),
                "S:24x7 Site Access? is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed(
                        "S:Facility Access Notification Required"
                ),
                "S:Facility Access Notification Required is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed(
                        "S:Facility Access Notification Period"
                ),
                "S:Facility Access Notification Period is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Facility Access Modified By"),
                "S:Facility Access Modified By is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Facility Access Modified Date"),
                "S:Facility Access Modified Date is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Facility Access Sunday - Start"),
                "S:Facility Access Sunday - Start is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Facility Access Monday - Start"),
                "S:Facility Access Monday - Start is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed(
                        "S:Facility Access Tuesday - Start"
                ),
                "S:Facility Access Tuesday - Start is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed(
                        "S:Facility Access Wednesday - Start"
                ),
                "S:Facility Access Wednesday - Start is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed(
                        "S:Facility Access Thursday - Start"
                ),
                "S:Facility Access Thursday - Start is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Facility Access Friday - Start"),
                "S:Facility Access Friday - Start is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed(
                        "S:Facility Access Saturday - Start"
                ),
                "S:Facility Access Saturday - Start is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Facility Access Sunday - End"),
                "S:Facility Access Sunday - End is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Facility Access Monday - End"),
                "S:Facility Access Monday - End is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Facility Access Tuesday - End"),
                "S:Facility Access Tuesday - End is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed(
                        "S:Facility Access Wednesday - End"
                ),
                "S:Facility Access Wednesday - End is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Facility Access Thursday - End"),
                "S:Facility Access Thursday - End is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Facility Access Friday - End"),
                "S:Facility Access Friday - End is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Facility Access Saturday - End"),
                "S:Facility Access Saturday - End is displayed"
        );
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Validate Facility Access Field Controls",
            priority = 3
    )
    public void validateFacilityAccessControls(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        softAssert.assertContains(
                siteFopsPage.verifyFieldIsText_Facility("S:Site Access Details"),
                "text",
                "S:Site Access Details is text field"
        );
        softAssert.assertContains(
                siteFopsPage.verifyInputBoxFieldIsText_Facility(
                        "S:Site Access Details Updated by "
                ),
                "text",
                "S:Site Access Details Updated by is text field"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldIsText_Facility("S:Directions to Site"),
                "text",
                "S:Directions to Site is text field"
        );
        boolean response = siteFopsPage.verifyTextareaFieldCharacterLimit_Facility(
                "S:Directions to Site",
                4000
        );
        softAssert.assertTrue(
                response,
                "S:Directions to Site text field limit is 4000 characters"
        );
        softAssert.assertContains(
                siteFopsPage.verifyInputBoxFieldIsText_Facility(
                        "S:Facility Access Gate Combo"
                ),
                "text",
                "S:Facility Access Gate Combo is text field"
        );
        boolean response1 = siteFopsPage.verifyTextFieldCharacterLimit_Facility(
                "S:Facility Access Gate Combo",
                150
        );
        softAssert.assertTrue(
                response1,
                "S:Facility Access Gate Combo text field limit is 150 characters"
        );
        softAssert.assertContains(
                siteFopsPage.verifyInputBoxFieldIsText_Facility("S:Facility Access Keys"),
                "text",
                "S:Facility Access Keys is text field"
        );
        boolean response2 = siteFopsPage.verifyTextFieldCharacterLimit_Facility(
                "S:Facility Access Keys",
                50
        );
        softAssert.assertTrue(
                response2,
                "S:Facility Access Keys text field limit is 150 characters "
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldIsCheckbox("S:Facility Elevator Available"),
                "checkbox",
                "S:Facility Elevator Available field is CheckBox"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldDropDown("S:Equipment Shelter at Site?"),
                "S:Equipment Shelter at Site? is Drop Down"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldDropDown("S:Vault Site"),
                "S:Vault Site field is drop down"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldIsText_Facility("S:Facility Access Description"),
                "text",
                "S:Facility Access Description is text field"
        );
        boolean response3 = siteFopsPage.verifyTextareaFieldCharacterLimit_Facility(
                "S:Facility Access Description",
                4000
        );
        softAssert.assertTrue(
                response3,
                "S:Facility Access Description text field limit is 4000 characters"
        );
        softAssert.assertContains(
                siteFopsPage.verifyInputBoxFieldIsText_Facility(
                        "S:Facility Access Notes"
                ),
                "text",
                "S:Facility Access Notes is text field"
        );
        softAssert.assertNotNull(
                siteFopsPage.validateTextAreaFieldIsReadOnly_Facility(
                        "S:Facility Access Notes History"
                ),
                "S:Facility Access Notes History field is read only"
        );
        boolean response4 = siteFopsPage.verifyTextareaFieldCharacterLimit_Facility(
                "S:Facility Access Notes History",
                4000
        );
        softAssert.assertTrue(
                response4,
                "S:Facility Access Notes History limit is 4000 characters"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldDropDown("S:24x7 Site Access?"),
                "S:24x7 Site Access? field is Drop down"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldIsCheckbox(
                        "S:Facility Access Notification Required"
                ),
                "checkbox",
                "S:Facility Access Notification Required is checkbox"
        );
        boolean response5 = siteFopsPage.verifyTextFieldCharacterLimit_Facility(
                "S:Facility Access Notification Period",
                50
        );
        softAssert.assertTrue(
                response5,
                "S:Facility Access Notification Period text field limit is 50 characters"
        );
        softAssert.assertNotNull(
                siteFopsPage.validateFieldIsReadOnly("S:Facility Access Modified By"),
                "S:Facility Access Modified By field is read only"
        );
        softAssert.assertNotNull(
                siteFopsPage.validateFieldIsReadOnly("S:Facility Access Modified Date"),
                "S:Facility Access Modified Date field is read only"
        );
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Verify Facility Access Description is saved with no error ",
            priority = 4
    )
    public void updateFacilityAccessDescription() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        String comment =
                "Automation Connection Testing content in Facility Access Description";
        siteFopsPage.addAccessDescriptionComments_Facility(comment);
        softAssert.assertFalse(
                siteFopsPage.isPopAlertPresent(),
                "Facility Access Description is saved with no error"
        );
        softAssert.closeAssert();
    }
    @Test(
            groups = { "Integration" },
            description = "Verify Facility Access Notes is saved with no error ",
            priority = 5
    )
    public void updateFacilityAccessNotes() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        String comment =
                "Automation Connection Testing content in Facility Access Notes";
        siteFopsPage.addAccessNotesComments_Facility(comment);
        softAssert.assertFalse(
                siteFopsPage.isPopAlertPresent(),
                "Facility Access Notes is saved with no error"
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Verify Facility Access Notes text input moved to Facility Notes History",
            priority = 6
    )
    public void verifyFacilityNotesHistoryForAccessNotesComments()
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        String comment =
                "Automation Connection Testing content in Facility Access Notes";
        siteFopsPage.addAccessNotesComments_Facility(comment);
        String FacilityAccessNotesHistoryText = siteFopsPage.getTextAccessNotesHistory_Facility();
        System.out.println(
                "Facility Access Notes History text_" + FacilityAccessNotesHistoryText
        );
        softAssert.assertTrue(
                FacilityAccessNotesHistoryText.contains(comment),
                "The notes moved from the S:Facility Access Notes field to the S:Facility Access Notes History field"
        );
        String currentDate = MiscHelpers.currentDateTime("MM/dd/yyyy");
        softAssert.assertTrue(
                FacilityAccessNotesHistoryText.contains(currentDate),
                "The notes moved from the S:Facility Access Notes field to the S:Facility Access Notes History field is Date and Time Stamped"
        );
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "S:Facility Access Modified By is auto populated with username",
            priority = 7
    )
    public void verifyAccessModifiedByIsAutoPopulated(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage = siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        softAssert.assertTrue(
                siteFopsPage.getModifiedByFieldText(superUser),
                "S:Facility Access Modified By is auto populated with username"
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "S:Facility Access Modified Date is auto populated with Current Date",
            priority = 8
    )
    public void verifyAccessModifiedDateIsAutoPopulated(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage = siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        softAssert.assertTrue(
                siteFopsPage.getModifiedDateFieldText(),
                "S:Facility Access Modified Date is auto populated with Current Date"
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "verify S:Facility Elevator Available is checked",
            priority = 9
    )
    public void verifyElevatorAvailableCheckbox(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage = siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        siteFopsPage.clickCheckbox_Facility(
                "S:Facility Elevator Available"
        );
        boolean response = siteFopsPage.verifyCheckboxIsSelected_Facility(
                "S:Facility Elevator Available"
        );
        softAssert.assertTrue(response, "S:Facility Elevator Available is checked");
        softAssert.assertFalse(
                siteFopsPage.isPopAlertPresent(),
                "S:Facility Elevator Available is saved with no error"
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "S:Facility Access Notification Required is saved without Access Notification Period",
            priority = 10
    )
    public void verifyNotificationRequiredIsSavedWithoutNotificationPeriod(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage = siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        siteFopsPage.clickCheckbox_Facility(
                "S:Facility Access Notification Required"
        );
        boolean response = siteFopsPage.verifyCheckboxIsSelected_Facility(
                "S:Facility Access Notification Required"
        );
        softAssert.assertTrue(
                response,
                "S:Facility Access Notification Required is checked"
        );
//        softAssert.assertTrue(
//                siteFopsPage.isPopAlertPresent(),
//                "S:Facility Access Notification Required Cannot be saved without Access Notification Period"
//        );
        softAssert.closeAssert();
    }
    @Test(
            groups = { "Integration" },
            description = "S:Facility Elevator Available is saved without Access Notification Period",
            priority = 11
    )
    public void verifyNotificationRequiredIsSavedWithNotificationPeriod(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage = siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        siteFopsPage.setNotificationPeriod();
        siteFopsPage.clickCheckbox_Facility(
                "S:Facility Access Notification Required"
        );
        boolean response = siteFopsPage.verifyCheckboxIsSelected_Facility(
                "S:Facility Access Notification Required"
        );
        softAssert.assertTrue(
                response,
                "S:Facility Access Notification Required is checked"
        );
        softAssert.assertFalse(
                siteFopsPage.isPopAlertPresent(),
                "S:Facility Access Notification Required is saved with Access Notification Period"
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Verify Facility Access field Controls",
            priority = 12
    )
    public void verifyFacilityAccessFieldControls(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage = siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        softAssert.assertContains(
                siteFopsPage.verifyFieldFormat_Facility(
                        "S:Facility Access Sunday - Start"
                ),
                "00:00",
                "Field Starts with time format 00:00 verified successfully"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldFormat_Facility(
                        "S:Facility Access Monday - Start"
                ),
                "00:00",
                "Field Starts with time format 00:00 verified successfully"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldFormat_Facility(
                        "S:Facility Access Tuesday - Start"
                ),
                "00:00",
                "Field Starts with time format 00:00 verified successfully"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldFormat_Facility(
                        "S:Facility Access Wednesday - Start"
                ),
                "00:00",
                "Field Starts with time format 00:00 verified successfully"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldFormat_Facility(
                        "S:Facility Access Thursday - Start"
                ),
                "00:00",
                "Field Starts with time format 00:00 verified successfully"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldFormat_Facility(
                        "S:Facility Access Friday - Start"
                ),
                "00:00",
                "Field Starts with time format 00:00 verified successfully"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldFormat_Facility(
                        "S:Facility Access Saturday - Start"
                ),
                "00:00",
                "Field Starts with time format 00:00 verified successfully"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldFormat_Facility("S:Facility Access Sunday - End"),
                "23:59",
                "Field ends with time format 23:59 verified successfully"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldFormat_Facility("S:Facility Access Monday - End"),
                "23:59",
                "Field ends with time format 23:59 verified successfully"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldFormat_Facility(
                        "S:Facility Access Tuesday - End"
                ),
                "23:59",
                "Field ends with time format 23:59 verified successfully"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldFormat_Facility(
                        "S:Facility Access Wednesday - End"
                ),
                "23:59",
                "Field ends with time format 23:59 verified successfully"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldFormat_Facility(
                        "S:Facility Access Thursday - End"
                ),
                "23:59",
                "Field ends with time format 23:59 verified successfully"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldFormat_Facility("S:Facility Access Friday - End"),
                "23:59",
                "Field ends with time format 23:59 verified successfully"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldFormat_Facility(
                        "S:Facility Access Saturday - End"
                ),
                "23:59",
                "Field ends with time format 23:59 verified successfully"
        );
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Update field Start time without error",
            priority = 13
    )
    public void updateStartTimeWithoutError(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage = siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        siteFopsPage.updateTime("S:Facility Access Sunday - Start");
        softAssert.assertFalse(
                siteFopsPage.isPopAlertPresent(),
                "Start Time is saved with no error"
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "update End time without error",
            priority = 14
    )
    public void updateEndTimeWithoutError(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage = siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        siteFopsPage.updateTime("S:Facility Access Sunday - End");
        softAssert.assertFalse(
                siteFopsPage.isPopAlertPresent(),
                "End Time is saved with no error"
        );
        softAssert.closeAssert();
    }
}
