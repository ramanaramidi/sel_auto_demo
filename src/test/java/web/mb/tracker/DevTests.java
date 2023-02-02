package web.mb.tracker;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Site;
import org.testng.annotations.Test;
import pages.web.Tracker.AddRingPage;
import pages.web.Tracker.ProjectTrackerPage;
import pages.web.Tracker.RingTrackerPage;
import pages.web.Tracker.site.AddSitePage;
import pages.web.Tracker.site.SiteTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.misc.MiscHelper;
import rest.project.ProjectHelper;
import rest.ring.RingHelper;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class DevTests extends BaseTest {

  public String envURL = System.getProperty("TestEnv");
  public String testSuite = System.getProperty("TestRunner");
  LoginPage loginPage;
  MainSideMenu mainSideMenu;
  RingTrackerPage ringTracker;
  SiteTrackerPage siteTracker;
  ProjectTrackerPage projectTrackerPage;
  AddSitePage addNewSite;
  AddRingPage addNewRing;
  String ringCode = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
  String siteCodeCancel =
    "SAU" + MiscHelpers.getRandomString(5, true).toUpperCase();
  ProjectHelper projectHelper = new ProjectHelper();
  String siteCode = "AU" + MiscHelpers.getRandomString(6, true).toUpperCase();
  String siteCodeWith9Characters = ringCode + "AB";
  String commonRingId;
  Site site;
  Site Active_Site;
  Site siteActive;
  Site importSite;
  String VALID_RING_PRIMARY_BUILD_SITE = "";
  SiteHelper siteHelper = new SiteHelper();
  RingHelper ringHelper = new RingHelper();
  MiscHelper miscHelperRest = new MiscHelper();

  public DevTests() {
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
    if(alphaUser.getIsServiceAccount().equals("true")){
      loginPage.doLogin(LoginOptionEnum.UN_EMAIL);
      loginPage.login(alphaUser);
    }
    else{
      loginPage.doLogin(LoginOptionEnum.SAML);
      String url = loginPage.getLoginUrl(alphaUser);
      if(url!=null){
        loginPage.launchUrl(url);
      }
    }
    generateCommonData();
    mainSideMenu = loginPage.LoginAsUser(superUser);

  }

  private void generateCommonData() {
    importSite = new Site(ringCode, "Candidate", "New Site");
    String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
    Ring ring = new Ring("Active", ringId, "Macro");
    site = new Site(ringId, "Candidate", "New Site");
    site = siteHelper.createNewRingAndSite(ring, site);
  }

  @Test(
    groups = { "Integration" },
    description = "Create New Site And Validations",
    priority = 2
  )
  public void createNewSiteTest_Site(Method method) throws Exception {
    AssertionsUtil softAssert = new AssertionsUtil();
    Site siteNew = new Site(site.ringId, "Candidate", "New Site");
    siteTracker = mainSideMenu.goToSiteTracker();
    addNewSite = siteTracker.selectAddNewSiteOption();
    siteTracker = addNewSite.addingNewSiteTracker(siteNew);
    siteTracker.searchForValue(siteNew.ringId, "R:Ring Code");
    addNewSite = siteTracker.selectEditOption();
    softAssert.assertTrue(addNewSite.zipCodeValidation(), "Zip Code Populated");
    softAssert.assertTrue(
      addNewSite.stateFieldValidation(),
      "State Field Populated"
    );
    softAssert.assertTrue(
      addNewSite.countryFieldValidation(),
      "Country Field Populated"
    );
    addNewSite.acceptAndGoToSiteTracker();
    softAssert.closeAssert();
  }

  @Test(
    groups = { "Integration" },
    description = "Acs Test For Bbu_Bts Validations",
    priority = 2
  )
  public void siteCategoryBbuBtsValidation(Method method) throws Exception {
    String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
    Ring ring = new Ring("Active", ringId, "Indoor Node");
    Site siteActive = new Site(ringId, "Primary", "Active Site");
    Active_Site =
      siteHelper.createActiveRingAndPrimaryActiveSite(ring, siteActive);

    AssertionsUtil softAssert = new AssertionsUtil();
    siteTracker = mainSideMenu.goToSiteTracker();
    siteTracker.searchForValue(Active_Site.siteId, "S:Site Code");
    addNewSite = siteTracker.selectEditOption();
    addNewSite.selectSiteCategoryBbuBts();
    softAssert.assertFalse(
      !(addNewSite.hubClusterIdVerification()),
      "Hub Cluster Id Field Is In Read Only Mode"
    );
    addNewSite.acceptAndGoToSiteTracker();
    softAssert.closeAssert();
  }

  @Test(
    groups = { "Integration" },
    description = "Acs Test For Node Validations",
    priority = 2
  )
  public void siteCategoryNodeValidations(Method method) throws Exception {
    String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
    Ring ring = new Ring("Active", ringId, "Indoor Node");
    Site siteActive = new Site(ringId, "Primary", "Active Site");
    Active_Site =
      siteHelper.createActiveRingAndPrimaryActiveSite(ring, siteActive);

    AssertionsUtil softAssert = new AssertionsUtil();
    siteTracker = mainSideMenu.goToSiteTracker();
    siteTracker.searchForValue(Active_Site.siteId, "S:Site Code");
    addNewSite = siteTracker.selectEditOption();
    addNewSite.selectSiteCategoryNode();
    softAssert.assertFalse(
      !(addNewSite.hubClusterIdVerification()),
      "Hub Cluster Id Field Is In Read Only Mode"
    );
    addNewSite.acceptAndGoToSiteTracker();
    softAssert.closeAssert();
  }

  @Test(
    groups = { "Integration" },
    description = "On Air Off Air Dates Validation",
    priority = 2
  )
  public void onAirDatesValidation(Method method) throws Exception {
    AssertionsUtil softAssert = new AssertionsUtil();
    siteTracker = mainSideMenu.goToSiteTracker();
    siteTracker.searchForValue("SAUKZVPG", "S:Site Code");
    addNewSite = siteTracker.selectEditOption();
    addNewSite.goToOnAirOffAirTab();
    softAssert.assertTrue(
      addNewSite.L600TechnologyValidation("L600"),
      "On Air Technology Field Contains L600 Technology"
    );
    softAssert.assertTrue(
      addNewSite.L700TechnologyValidation("L700"),
      "On Air Technology Field Contains L700 Technology"
    );
    softAssert.assertTrue(
      addNewSite.L1900TechnologyValidation("L1900"),
      "On Air Technology Field Contains L1900 Technology"
    );
    softAssert.assertTrue(
      addNewSite.L2100TechnologyValidation("L2100"),
      "On Air Technology Field Contains L2100 Technology"
    );
    softAssert.assertTrue(
      addNewSite.L2100Aws3TechnologyValidation("L2100 AWS3"),
      "On Air Technology Field Contains L2100 AWS3 Technology"
    );
    softAssert.assertTrue(
      addNewSite.L2500TechnologyValidation("L2500"),
      "On Air Technology Field Contains L2500 Technology"
    );
    softAssert.assertTrue(
      addNewSite.N600TechnologyValidation("N600"),
      "On Air Technology Field Contains N600 Technology"
    );
    softAssert.assertTrue(
      addNewSite.N2500TechnologyValidation("N2500"),
      "On Air Technology Field Contains N2500 Technology"
    );
    softAssert.assertTrue(
      addNewSite.U1900TechnologyValidation("U1900"),
      "On Air Technology Field Contains U1900 Technology"
    );
    softAssert.assertTrue(
      addNewSite.gsmTechnologyValidation("GSM"),
      "On Air Technology Field Contains GSM Technology"
    );
    softAssert.closeAssert();
  }

  @Test(
    groups = { "Integration" },
    description = "On Air Off Air Dates Validation",
    priority = 2
  )
  public void onAirOffAirDatesValidation(Method method) throws Exception {
    AssertionsUtil softAssert = new AssertionsUtil();
    siteTracker = mainSideMenu.goToSiteTracker();
    siteTracker.searchForValue("SAUKZVPG", "S:Site Code");
    addNewSite = siteTracker.selectEditOption();
    addNewSite.goToOnAirOffAirTab();
    softAssert.assertTrue(
      addNewSite.U2100TechnologyValidation("U2100"),
      "On Air Technology Field Doesn'tContains U2100 Technology"
    );
    softAssert.closeAssert();
  }

  @Test(
    groups = { "Integration" },
    description = "validateObjectiveValuesNA_UNNA",
    priority = 3
  )
  public void projectCompletionObjectiveValidation(Method method)
    throws Exception {
    AssertionsUtil softAssert = new AssertionsUtil();
    projectTrackerPage = mainSideMenu.goToProjectTracker();
    projectTrackerPage.searchForValue("AMEENAA3-0002249179", "PJ:Project ID");
    projectTrackerPage.selectEditOption();
    projectTrackerPage.switchToProjectPage();
    projectTrackerPage.goToTasksPage();
    softAssert.assertFalse(
      projectTrackerPage.taskCheckBoxValidation(),
      "checkBox Is Not Selected"
    );
    projectTrackerPage.backToProjectTracker();
    softAssert.closeAssert();
  }
}
