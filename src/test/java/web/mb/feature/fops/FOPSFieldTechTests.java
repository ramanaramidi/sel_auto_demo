package web.mb.feature.fops;

import common.BaseTest;
import commons.constants.Constants;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Site;
import java.lang.reflect.Method;
import org.testng.annotations.Test;
import pages.web.Tracker.AddRingPage;
import pages.web.Tracker.site.SiteFopsPage;
import pages.web.Tracker.RingTrackerPage;
import pages.web.Tracker.site.AddSitePage;
import pages.web.Tracker.site.SiteTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.project.ProjectHelper;
import rest.ring.RingHelper;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

public class FOPSFieldTechTests extends BaseTest {

    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    RingTrackerPage ringTracker;
    SiteTrackerPage siteTracker;
    AddSitePage addNewSite;

    SiteFopsPage siteFopsPage;
    AddRingPage addNewRing;
    String ringCode = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
    String siteCodeCancel =
            "SAU" + MiscHelpers.getRandomString(5, true).toUpperCase();
    ProjectHelper projectHelper = new ProjectHelper();
    String siteCode = "AU" + MiscHelpers.getRandomString(6, true).toUpperCase();
    String siteCodeWith9Characters = ringCode + "AB";
    String commonRingId;
    Site site;
    SiteHelper siteHelper = new SiteHelper();
    RingHelper ringHelper = new RingHelper();

    public FOPSFieldTechTests() {
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
        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringId, "Macro");
        site = new Site(ringId, "Candidate", "New Site");
        site = siteHelper.createNewRingAndSite(ring, site);
    }

    @Test(groups = { "Integration" }, description = "login", priority = 2)
    public void verifyingFieldTechSection_FieldTech(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(
                siteFopsPage.validateFieldTechName(),
                "Field Tech name Section Is Present"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Field Tech Phone"),
                "Field Tech Phone Section Is Present"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Field Tech Email"),
                "field Tech Email Section Is Present"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Field Tech Manager Name"),
                "Field Tech Manager Name Section Is Present"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Field Tech Manager Phone"),
                "Field Tech Manager Phone Section Is Present"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Field Tech Manager Email"),
                "Field Tech Manager Email Section Is Present"
        );
        siteFopsPage.switchToTrackerPageByCancel();
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "login", priority = 2)
    public void verifyingFieldTechSectionIsReadOnly_FieldTech(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertNotNull(
                siteFopsPage.validateFieldTechNameIsReadOnly(),
                "Field Tech name Field Is In Read Only Mode"
        );

        softAssert.assertNotNull(
                siteFopsPage.validateFieldIsReadOnly("S:Field Tech Phone"),
                "Field Tech Phone Field Is In Read Only Mode"
        );
        softAssert.assertNotNull(
                siteFopsPage.validateFieldIsReadOnly("S:Field Tech Email"),
                "Field Tech Email Field Is In Read Only Mode"
        );
        softAssert.assertNotNull(
                siteFopsPage.validateFieldIsReadOnly("S:Field Tech Manager Name"),
                "Field Tech Email Field Is In Read Only Mode"
        );
        softAssert.assertNotNull(
                siteFopsPage.validateFieldIsReadOnly("S:Field Tech Manager Phone"),
                "Field Tech Email Field Is In Read Only Mode"
        );
        softAssert.assertNotNull(
                siteFopsPage.validateFieldIsReadOnly("S:Field Tech Manager Email"),
                "Field Tech Email Field Is In Read Only Mode"
        );
        siteFopsPage.switchToTrackerPageByCancel();
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "login", priority = 2)
    public void verifyingFieldTechSectionIsNotMandatory_FieldTech(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        siteFopsPage.updateMandatoryFields();
        softAssert.assertTrue(
                siteFopsPage.setFieldTechSectionEmptyAndValidate(),
                "Tech Field Is Not Mandatory"
        );
        siteFopsPage.switchToSiteTracker();
        softAssert.closeAssert();
    }
}
