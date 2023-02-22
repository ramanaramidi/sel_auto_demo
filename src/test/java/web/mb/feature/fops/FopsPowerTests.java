package web.mb.feature.fops;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Site;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.Test;
import pages.web.Tracker.ProjectTrackerPage;
import pages.web.Tracker.site.AddSitePage;
import pages.web.Tracker.site.SiteFopsPage;
import pages.web.Tracker.site.SiteTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

public class FopsPowerTests extends BaseTest {

    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    SiteTrackerPage siteTrackerPage;
    Site Site_Active;
    Site SiteActive;
    Site site;
    SiteHelper siteHelper = new SiteHelper();
    SiteFopsPage siteFopsPage;
    ProjectTrackerPage projectTrackerPage;
    AddSitePage addNewSite;

    public FopsPowerTests() {
        if (envURL == null) {
            envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");
        }
        if (testSuite == null) {
            testSuite = "sectorSet.xml";
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
        //        String ringId_Active = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        //        Ring ring_Active = new Ring("Active", ringId_Active, "Indoor Node");
        //        Site_Active = new Site(ringId_Active, "Primary", "Active Site");
        //        Site_Active = siteHelper.createActiveRingAndPrimaryActiveSite(ring_Active, Site_Active);
        //        siteHelper.updateSiteForFops(Site_Active);

        String ringIdActive =
                "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActive, "Indoor Node");
        SiteActive =
                new Site(
                        ringIdActive,
                        "Primary",
                        "Building",
                        "Roof Top Mount",
                        "Build To Suit",
                        "Active Site"
                );
        SiteActive =
                siteHelper.createActiveRingAndPrimaryActiveSite(ringActive, SiteActive);
        siteHelper.updateSiteForFops(SiteActive);
    }

    @Test(
            groups = { "Integration" },
            description = "Validate Power fields are displayed",
            priority = 3
    )
    public void validatePowerFieldsDisplayed(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(SiteActive.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        siteFopsPage = addNewSite.goToFopsTab();
        String parentWindow = addNewSite.getParentWindow();
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Gate Combo"),
                "S:Power Access Gate Combo"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed(
                        "S:Power Access Notification Required"
                ),
                "S:Power Access Notification Required"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Keys"),
                "S:Power Access Keys"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed(
                        "S:PWR Site Portable Generator Plug Type"
                ),
                "S:PWR Site Portable Generator Plug Type"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Provider Company Name"),
                "S:Power Provider Company Name"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Electrical Account Number"),
                "S:Power Electrical Account Number"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Electrical Meter Number"),
                "S:Power Electrical Meter Number"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Description"),
                "S:Power Access Description"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Notes"),
                "S:Power Access Notes"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Notes History"),
                "S:Power Access Notes History"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access 24x7"),
                "S:Power Access 24x7"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Sunday - Start"),
                "S:Power Access Sunday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Monday - Start"),
                "S:Power Access Monday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Tuesday - Start"),
                "S:Power Access Tuesday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Wednesday - Start"),
                "S:Power Access Wednesday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Thursday - Start"),
                "S:Power Access Thursday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Friday - Start"),
                "S:Power Access Friday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Saturday - Start"),
                "S:Power Access Saturday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed(
                        "S:Power Access Notification Period"
                ),
                "S:Power Access Notification Period"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Modified By"),
                "S:Power Access Modified By"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Modified Date"),
                "S:Power Access Modified Date"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Provider Contact Number"),
                "S:Power Provider Contact Number"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Service Point Location"),
                "S:Power Service Point Location"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed(
                        "S:Power Notify FOPS for Power Outage"
                ),
                "S:Power Notify FOPS for Power Outage"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Sunday - End"),
                "S:Power Access Sunday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Monday - End"),
                "S:Power Access Monday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Tuesday - End"),
                "S:Power Access Tuesday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Wednesday - End"),
                "S:Power Access Wednesday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Thursday - End"),
                "S:Power Access Thursday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Friday - End"),
                "S:Power Access Friday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Saturday - End"),
                "S:Power Access Saturday - End"
        );
        siteFopsPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "validatePowerFieldsTypeAndValue",
            priority = 4
    )
    public void validatePowerFieldsTypeAndValue(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(SiteActive.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        siteFopsPage = addNewSite.goToFopsTab();
        String parentWindow = addNewSite.getParentWindow();
        softAssert.assertTrue(
                siteFopsPage.isFieldInputBox("S:Power Access Gate Combo"),
                "S:Power Access Gate Combo"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldCheckbox("S:Power Access Notification Required"),
                "S:Power Access Notification Required"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldInputBox("S:Power Access Keys"),
                "S:Power Access Keys"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldDropDown("S:PWR Site Portable Generator Plug Type"),
                "S:PWR Site Portable Generator Plug Type"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldInputBox("S:Power Provider Company Name"),
                "S:Power Provider Company Name"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldInputBox("S:Power Electrical Account Number"),
                "S:Power Electrical Account Number"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldInputBox("S:Power Electrical Meter Number"),
                "S:Power Electrical Meter Number"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldInputBox("S:Power Access Notes"),
                "S:Power Access Notes"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldDropDown("S:Power Access 24x7"),
                "S:Power Access 24x7"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Sunday - Start",
                        "00:00"
                ),
                "S:Power Access Sunday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Monday - Start",
                        "00:00"
                ),
                "S:Power Access Monday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Tuesday - Start",
                        "00:00"
                ),
                "S:Power Access Tuesday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Wednesday - Start",
                        "00:00"
                ),
                "S:Power Access Wednesday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Thursday - Start",
                        "00:00"
                ),
                "S:Power Access Thursday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Friday - Start",
                        "00:00"
                ),
                "S:Power Access Friday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Saturday - Start",
                        "00:00"
                ),
                "S:Power Access Saturday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldInputBox("S:Power Access Notification Period"),
                "S:Power Access Notification Period"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldInputBox("S:Power Access Modified By"),
                "S:Power Access Modified By"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldInputBox("S:Power Access Modified Date"),
                "S:Power Access Modified Date"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldInputBox("S:Power Provider Contact Number"),
                "S:Power Provider Contact Number"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldInputBox("S:Power Service Point Location"),
                "S:Power Service Point Location"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldCheckbox("S:Power Notify FOPS for Power Outage"),
                "S:Power Notify FOPS for Power Outage"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Sunday - End",
                        "23:59"
                ),
                "S:Power Access Sunday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Monday - End",
                        "23:59"
                ),
                "S:Power Access Monday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Tuesday - End",
                        "23:59"
                ),
                "S:Power Access Tuesday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Wednesday - End",
                        "23:59"
                ),
                "S:Power Access Wednesday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Thursday - End",
                        "23:59"
                ),
                "S:Power Access Thursday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Friday - End",
                        "23:59"
                ),
                "S:Power Access Friday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Saturday - End",
                        "23:59"
                ),
                "S:Power Access Saturday - End"
        );
        siteFopsPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "updatePowerAccessNotes",
            priority = 5
    )
    public void updatePowerAccessNotes(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(SiteActive.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        siteFopsPage = addNewSite.goToFopsTab();
        String parentWindow = addNewSite.getParentWindow();
        softAssert.assertTrue(
                siteFopsPage.isFieldInputBox("S:Power Access Notes"),
                "S:Power Access Notes"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Notes History"),
                "S:Power Access Notes History"
        );
        softAssert.assertTrue(
                siteFopsPage.updateInputBoxText(
                        "S:Power Access Notes",
                        "Testing under  S:Power Access Notes field"
                ),
                "S:Power Access Notes"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyTextInTextArea(
                        "S:Power Access Notes History",
                        "Testing under  S:Power Access Notes field"
                ),
                "Testing under  S:Power Access Notes History field"
        );
        siteFopsPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "updatePowerAccessNotes",
            priority = 6
    )
    public void verifyPowerAccessNotesHistory(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(SiteActive.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        siteFopsPage = addNewSite.goToFopsTab();
        String parentWindow = addNewSite.getParentWindow();
        softAssert.assertTrue(
                siteFopsPage.isFieldInputBox("S:Power Access Notes"),
                "S:Power Access Notes"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Notes History"),
                "S:Power Access Notes History"
        );
        softAssert.assertTrue(
                siteFopsPage.updateInputBoxText(
                        "S:Power Access Notes",
                        "Testing under  S:Power Access Notes field2"
                ),
                "S:Power Access Notes"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyTextInTextArea(
                        "S:Power Access Notes History",
                        "Testing under  S:Power Access Notes field"
                ),
                "Testing under  S:Power Access Notes History field"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyTextInTextArea(
                        "S:Power Access Notes History",
                        "Testing under  S:Power Access Notes field2"
                ),
                "Testing under  S:Power Access Notes History field"
        );
        siteFopsPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "updatePowerAccessNotes",
            priority = 7
    )
    public void verifyPowerAccessNotesMax(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(SiteActive.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        siteFopsPage = addNewSite.goToFopsTab();
        String parentWindow = addNewSite.getParentWindow();
        softAssert.assertTrue(
                siteFopsPage.isFieldInputBox("S:Power Access Notes"),
                "S:Power Access Notes"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Notes History"),
                "S:Power Access Notes History"
        );
        String text = MiscHelpers.getRandomString(2000, false);
        softAssert.assertTrue(
                siteFopsPage.updateInputBoxText("S:Power Access Notes", text),
                "S:Power Access Notes"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyTextInTextArea("S:Power Access Notes History", text),
                "Testing under  S:Power Access Notes History field"
        );
        siteFopsPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "updatePowerAccessNotes",
            priority = 8
    )
    public void verifyPowerAccessNotesHistoryData(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(SiteActive.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        siteFopsPage = addNewSite.goToFopsTab();
        String parentWindow = addNewSite.getParentWindow();
        softAssert.assertTrue(
                siteFopsPage.isFieldInputBox("S:Power Access Notes"),
                "S:Power Access Notes"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Power Access Notes History"),
                "S:Power Access Notes History"
        );
        //  softAssert.assertTrue(siteFopsPage.verifyTextInTextArea("S:Power Access Notes History",alphaUser.getNtCode()),"Testing under  S:Power Access Notes History field");
        softAssert.assertTrue(
                siteFopsPage.verifyTextInTextArea(
                        "S:Power Access Notes History",
                        MiscHelpers.currentDateTime("MM/dd/yyyy")
                ),
                "Testing under  S:Power Access Notes History field"
        );
        siteFopsPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "updatePowerAccessNotes",
            priority = 9
    )
    public void updatePowerAccessDesc(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(SiteActive.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        siteFopsPage = addNewSite.goToFopsTab();
        String parentWindow = addNewSite.getParentWindow();
        softAssert.assertTrue(
                siteFopsPage.isFieldTextArea("S:Power Access Description"),
                "S:Power Access Description"
        );
        softAssert.assertTrue(
                siteFopsPage.updateInputTextArea(
                        "S:Power Access Description",
                        "Testing under  S:Power Access Notes field"
                ),
                "S:Power Access Description"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyTextInTextArea(
                        "S:Power Access Description",
                        "Testing under  S:Power Access Notes field"
                ),
                "S:Power Access Description verify text"
        );
        siteFopsPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "updatePowerAccessNotes",
            priority = 10
    )
    public void verifyPowerAccessDescHistory(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(SiteActive.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        siteFopsPage = addNewSite.goToFopsTab();
        String parentWindow = addNewSite.getParentWindow();
        softAssert.assertTrue(
                siteFopsPage.isFieldTextArea("S:Power Access Description"),
                "S:Power Access Description"
        );
        softAssert.assertTrue(
                siteFopsPage.updateInputTextArea(
                        "S:Power Access Description",
                        "Testing under  S:Power Access Notes field2"
                ),
                "S:Power Access Description"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyTextInTextArea(
                        "S:Power Access Description",
                        "Testing under  S:Power Access Notes field2"
                ),
                "S:Power Access Description verify text"
        );
        siteFopsPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "updatePowerAccessNotes",
            priority = 11
    )
    public void verifyPowerAccessDescMax(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(SiteActive.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        siteFopsPage = addNewSite.goToFopsTab();
        String parentWindow = addNewSite.getParentWindow();
        String text = MiscHelpers.getRandomString(2000, false);
        softAssert.assertTrue(
                siteFopsPage.updateInputTextArea("S:Power Access Description", text),
                "S:Power Access Description"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyTextInTextArea("S:Power Access Description", text),
                "S:Power Access Description text limit check"
        );
        siteFopsPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "updatePowerAccessNotes",
            priority = 11
    )
    public void verifyPowerAccessReadOnlyFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(SiteActive.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        siteFopsPage = addNewSite.goToFopsTab();
        String parentWindow = addNewSite.getParentWindow();
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsReadOnly_Power("S:Power Access Modified By"),
                "S:Power Access Modified By"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsReadOnly_Power("S:Power Access Modified Date"),
                "S:Power Access Modified Date"
        );
        softAssert.assertTrue(
                siteFopsPage.validateTextAreaFieldIsReadOnly(
                        "S:Power Access Notes History"
                ),
                "S:Power Access Notes History"
        );
        siteFopsPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "updatePowerAccessNotes",
            priority = 11
    )
    public void verify24X7SiteAccessTime(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(SiteActive.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        siteFopsPage = addNewSite.goToFopsTab();
        String parentWindow = addNewSite.getParentWindow();
        softAssert.assertTrue(
                siteFopsPage.isFieldDropDown("S:Power Access 24x7"),
                "S:Power Access 24x7"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Sunday - Start",
                        "00:00"
                ),
                "S:Power Access Sunday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Monday - Start",
                        "00:00"
                ),
                "S:Power Access Monday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Tuesday - Start",
                        "00:00"
                ),
                "S:Power Access Tuesday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Wednesday - Start",
                        "00:00"
                ),
                "S:Power Access Wednesday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Thursday - Start",
                        "00:00"
                ),
                "S:Power Access Thursday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Friday - Start",
                        "00:00"
                ),
                "S:Power Access Friday - Start"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Saturday - Start",
                        "00:00"
                ),
                "S:Power Access Saturday - Start"
        );

        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Sunday - End",
                        "23:59"
                ),
                "S:Power Access Sunday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Monday - End",
                        "23:59"
                ),
                "S:Power Access Monday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Tuesday - End",
                        "23:59"
                ),
                "S:Power Access Tuesday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Wednesday - End",
                        "23:59"
                ),
                "S:Power Access Wednesday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Thursday - End",
                        "23:59"
                ),
                "S:Power Access Thursday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Friday - End",
                        "23:59"
                ),
                "S:Power Access Friday - End"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyInputBoxAndValue(
                        "S:Power Access Saturday - End",
                        "23:59"
                ),
                "S:Power Access Saturday - End"
        );

        siteFopsPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "updatePowerAccessNotes",
            priority = 11
    )
    public void verifyPowerAccessNotification(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(SiteActive.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        siteFopsPage = addNewSite.goToFopsTab();
        String parentWindow = addNewSite.getParentWindow();
        softAssert.assertTrue(
                siteFopsPage.isFieldCheckbox("S:Power Access Notification Required"),
                "S:Power Access Notification Required"
        );
        siteFopsPage.checkUncheckCheckBox("S:Power Access Notification Required");
        softAssert.assertTrue(
                siteFopsPage.applySelection(),
                "S:Power Access Notification Required"
        );
        siteFopsPage.checkUncheckCheckBox("S:Power Access Notification Required");
        softAssert.assertTrue(
                siteFopsPage.applySelection(),
                "S:Power Access Notification Required"
        );
        siteFopsPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "updatePowerAccessNotes",
            priority = 11
    )
    public void verifyPowerAccessOutage(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(SiteActive.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        siteFopsPage = addNewSite.goToFopsTab();
        String parentWindow = addNewSite.getParentWindow();
        softAssert.assertTrue(
                siteFopsPage.isFieldCheckbox("S:Power Notify FOPS for Power Outage"),
                "S:Power Notify FOPS for Power Outage"
        );
        siteFopsPage.checkUncheckCheckBox("S:Power Notify FOPS for Power Outage");
        softAssert.assertTrue(
                siteFopsPage.applySelection(),
                "S:Power Notify FOPS for Power Outage"
        );
        siteFopsPage.checkUncheckCheckBox("S:Power Notify FOPS for Power Outage");
        softAssert.assertTrue(
                siteFopsPage.applySelection(),
                "S:Power Notify FOPS for Power Outage"
        );
        siteFopsPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "updatePowerAccessNotes",
            priority = 11
    )
    public void verify24X7SiteAccess(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(SiteActive.siteId, "S:Site Code");
        addNewSite = siteTrackerPage.selectEditOption();
        siteFopsPage = addNewSite.goToFopsTab();
        String parentWindow = addNewSite.getParentWindow();
        softAssert.assertTrue(
                siteFopsPage.isFieldDropDown("S:Power Access 24x7"),
                "S:Power Access 24x7"
        );
        softAssert.assertNotNull(
                siteFopsPage.fieldDropDownValues("S:Power Access 24x7"),
                "S:Power Access 24x7 should not be null"
        );
        List<String> options = siteFopsPage.fieldDropDownValues(
                "S:Power Access 24x7"
        );
        softAssert.assertTrue(options.contains("No"), "No should be available");
        softAssert.assertTrue(options.contains("Yes"), "Yes should be available");
        siteFopsPage.selectDropDownOption("S:Power Access 24x7", "No");
        softAssert.assertTrue(siteFopsPage.applySelection(), "S:Power Access 24x7");
        siteFopsPage.switchToTrackerPage(parentWindow);
        softAssert.closeAssert();
    }
}
