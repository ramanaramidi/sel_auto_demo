package web.mb.feature.pier;

import common.BaseTest;
import commons.constants.Constants;
import commons.enums.LoginOptionEnum;
import commons.objects.*;
import org.testng.annotations.Test;
import pages.web.Tracker.AddProjectPage;
import pages.web.Tracker.EdbProducerTrackerPage;
import pages.web.Tracker.ProjectTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import pages.web.reports.ImportPage;
import rest.edb.EdbHelper;
import rest.misc.MiscHelper;
import rest.por.PorHelper;
import rest.project.ProjectHelper;
import rest.ring.RingHelper;
import rest.sector.SectorHelper;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class PierCrTests extends BaseTest {

  public String envURL = System.getProperty("TestEnv");
  public String testSuite = System.getProperty("TestRunner");

  LoginPage loginPage;
  MainSideMenu mainSideMenu;
  ImportPage importPage;
  SiteHelper siteHelper = new SiteHelper();
  EdbHelper edbHelper = new EdbHelper();
  RingHelper ringHelper = new RingHelper();
  PorHelper porHelper = new PorHelper();
  ProjectHelper projectHelper = new ProjectHelper();
  SectorHelper sectorHelper = new SectorHelper();
  EdbProducerTrackerPage edbProducerTrackerPage;
  ProjectTrackerPage projectTrackerPage;
  AddProjectPage addProjectPage;
  Site siteForSector;
  Ring ACTIVE_RING;
  Site ACTIVE_SITE;

  Ring NEW_RING;
  Site NEW_SITE;
  Por commonPor;
  Project PROJECT_ACTIVE;
  Project PROJECT_NEW;
  Project PROJECT_ACTIVE_M;
  Project PROJECT_ACTIVE_PRIMARY;
  MiscHelper miscHelperRest = new MiscHelper();
  Por projectActive;
  Por projectNew;

  public PierCrTests() {
    if (envURL == null) {
      envURL = "https://magentabuiltstg.t-mobile.com/Login.do";
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
    generateData();
    mainSideMenu = loginPage.LoginAsUser(superUser);
  }

  private void generateData() {
    String ringIdForSector =
      "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
    Ring ringActiveForSector = new Ring(
      "Active",
      ringIdForSector,
      "Indoor Node"
    );
    siteForSector = new Site(ringIdForSector, "Final Build", "ON-AIR");
    siteForSector =
      siteHelper.createActiveRingAndPrimaryActiveSite(
        ringActiveForSector,
        siteForSector
      );
    String key = edbHelper.updateElementOnAirOffAirSiteStatusEDB(siteForSector);
    System.out.println(siteForSector.siteId + "siteforsector");
    projectActive = porHelper.createAPor(
      new Por("Decom_N/A_Decom", ringIdForSector)
    );
    PROJECT_ACTIVE =
      projectHelper.createActiveProjectForExistingPor(
        projectActive,
        siteForSector.siteId
      );
    System.out.println("PROJECT_ACTIVE " + PROJECT_ACTIVE.projectId);

    // Second set of data with no project to check with import
    ringIdForSector = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
    Ring ring = new Ring("Active", ringIdForSector, "Indoor Node");
    siteForSector = new Site(ringIdForSector, "Final Build", "ON-AIR");
    siteForSector =
      siteHelper.createActiveRingAndPrimaryActiveSite(ring, siteForSector);
    edbHelper.updateElementOnAirOffAirSiteStatusEDB(siteForSector);
    System.out.println(siteForSector.siteId + "NOPROJECT");
    commonPor = porHelper.createAPor(
      new Por("Equipment Upgrade_Midcell Donor_Site Mod", ringIdForSector)
    );
    PROJECT_ACTIVE_M = projectHelper.createActiveProjectForExistingPor(commonPor,siteForSector.siteId);
        System.out.println("PROJECT_ACTIVE_PRIMARY " + PROJECT_ACTIVE_M.projectId);
    //Third set of data for project new

    ringIdForSector = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
    Ring ringNewProject = new Ring("Active", ringIdForSector, "Indoor Node");
    siteForSector = new Site(ringIdForSector, "Final Build", "ON-AIR");
    siteForSector =
      siteHelper.createActiveRingAndPrimaryActiveSite(
        ringNewProject,
        siteForSector
      );
    edbHelper.updateElementOnAirOffAirSiteStatusEDB(siteForSector);
    System.out.println(siteForSector.siteId + "siteforsector");
    projectNew = porHelper.createAPor(
      new Por("Decom_N/A_Decom", ringIdForSector)
    );
    PROJECT_NEW =
      projectHelper.createNewProjectForExistingPor(
              projectNew,
        siteForSector.siteId
      );
    System.out.println("PROJECT_New " + PROJECT_NEW.projectId);
  }

  @Test(
    groups = { "Integration" },
    description = "verifyDecomProjectCreation",
    priority = 2
  )
  public void verifyDecomNewProjectCreation(Method method) throws Exception {
    AssertionsUtil softAssert = new AssertionsUtil();
    edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
    edbProducerTrackerPage.searchForValue(
      PROJECT_NEW.projectId,
      "EPM:Tracker Key"
    );
    softAssert.assertFalse(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
      "Timestamp"
    );
    softAssert.assertFalse(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
      "User"
    );
    softAssert.assertFalse(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
      "Message Type"
    );
    softAssert.assertFalse(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
      "Tracker ID"
    );
    softAssert.assertFalse(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
      "Tracker Key"
    );
    softAssert.assertFalse(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
      "Producer Message"
    );
    softAssert.closeAssert();
  }

  @Test(
    groups = { "Integration" },
    description = "verifyDecomProjectActiveCreation",
    priority = 2
  )
  public void verifyDecomProjectActiveCreation(Method method) throws Exception {
    AssertionsUtil softAssert = new AssertionsUtil();
    edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
    edbProducerTrackerPage.searchForValue(
      PROJECT_ACTIVE.projectId,
      "EPM:Tracker Key"
    );
    sleepFor(20);
    softAssert.assertTrue(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
      "Timestamp"
    );
    softAssert.assertTrue(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
      "User"
    );
    softAssert.assertTrue(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
      "Message Type"
    );
    softAssert.assertTrue(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
      "Tracker ID"
    );
    softAssert.assertTrue(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
      "Tracker Key"
    );
    softAssert.assertTrue(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
      "Producer Message"
    );
    String messageType = edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type",1);
    softAssert.assertTrue(
            messageType.equals("Create"),
            "Producer Message  "+messageType
    );
    softAssert.closeAssert();
  }

  @Test(
    groups = { "Integration" },
    description = "verifyDecomProjectActiveCreation",
    priority = 2
  )
  public void verifyUpdate7075DecomProjectActiveCreation(Method method)
    throws Exception {
    HashMap<String, String> params = new HashMap<>();
    params.put("action", "UPDATE");
    LinkedHashMap<String, String> fieldValues = new LinkedHashMap<>();
    fieldValues.put("PROJECT_ID", PROJECT_ACTIVE.projectId);
    fieldValues.put(
      "PF7075",
      MiscHelpers.specificPastDateTime("MM/dd/YYYY", 10)
    );
    MiscHelpers.generateCsvSingleLineItem(
      fieldValues,
      Constants.PF7075_IMPORT_DATA
    );
    ImportFile importFile = new ImportFile(
      "10017766",
      "pf7075_imports.csv",
      Constants.PF7075_IMPORT_DATA
    );
    importFile =
      miscHelperRest.importFileAndGetStatusWithParam(importFile, params);
    AssertionsUtil softAssert = new AssertionsUtil();
    softAssert.assertNotNull(importFile, "File import was successful");
    edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
    edbProducerTrackerPage.searchForValue(
            PROJECT_ACTIVE.projectId,
            "EPM:Tracker Key"
    );
    sleepFor(15);
    String messageType = edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type",1);
    softAssert.assertTrue(
            messageType.equals("Create"),
            "Post date import  "+messageType
    );
    Project temp = projectHelper.sendToPierProject(PROJECT_ACTIVE);

    softAssert.assertNotNull(temp, "sent to pier");
    edbProducerTrackerPage.searchForValue(
            PROJECT_ACTIVE.projectId,
            "EPM:Tracker Key"
    );
    messageType = edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type",1);
    softAssert.assertTrue(
            messageType.equals("Update"),
            "Producer Message  "+messageType
    );
    messageType = edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type",2);
    softAssert.assertTrue(
            messageType.equals("Create"),
            "Producer Message  "+messageType
    );
    messageType = edbProducerTrackerPage.searchForValueInGrid("EPM:Tracker Key",2);
    softAssert.assertTrue(
            messageType.contains("PIER2"),
            "Tracker key  "+messageType
    );
    softAssert.assertTrue(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
      "Timestamp"
    );
    softAssert.assertTrue(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
      "User"
    );
    softAssert.assertTrue(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
      "Message Type"
    );
    softAssert.assertTrue(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
      "Tracker ID"
    );
    softAssert.assertTrue(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
      "Tracker Key"
    );
    softAssert.assertTrue(
      edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
      "Producer Message"
    );

    softAssert.closeAssert();
  }

  @Test(
          groups = { "Integration" },
          description = "verifyDecomProjectActiveCreation",
          priority = 2
  )
  public void verifyUpdate7075DecomProjectOnHoldCreation(Method method)
          throws Exception {
    HashMap<String, String> params = new HashMap<>();
    params.put("action", "UPDATE");
    LinkedHashMap<String, String> fieldValues = new LinkedHashMap<>();
    fieldValues.put("PROJECT_ID", PROJECT_ACTIVE.projectId);
    fieldValues.put(
            "PF7075",
            MiscHelpers.specificPastDateTime("MM/dd/YYYY", 10)
    );
    MiscHelpers.generateCsvSingleLineItem(
            fieldValues,
            Constants.PF7075_IMPORT_DATA
    );
    ImportFile importFile = new ImportFile(
            "10017766",
            "pf7075_imports.csv",
            Constants.PF7075_IMPORT_DATA
    );
    importFile =
            miscHelperRest.importFileAndGetStatusWithParam(importFile, params);
    AssertionsUtil softAssert = new AssertionsUtil();
    softAssert.assertNotNull(importFile, "File import was successful");
    edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
    edbProducerTrackerPage.searchForValue(
            PROJECT_ACTIVE.projectId,
            "EPM:Tracker Key"
    );
    String messageType = edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type",1);
    softAssert.assertTrue(
            messageType.equals("Update"),
            "Post date import  "+messageType
    );

    porHelper.updatePorWithUnBundling(projectActive,"Unbundled");
    edbProducerTrackerPage.searchForValue(
            PROJECT_ACTIVE.projectId,
            "EPM:Tracker Key"
    );
    messageType = edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type",1);
    softAssert.assertTrue(
            messageType.equals("Update"),
            "Producer Message  "+messageType
    );
    Project temp = projectHelper.sendToPierProject(PROJECT_ACTIVE);

    softAssert.assertNotNull(temp, "sent to pier");
//    edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
    edbProducerTrackerPage.searchForValue(
            PROJECT_ACTIVE.projectId,
            "EPM:Tracker Key"
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
            "Timestamp"
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
            "User"
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
            "Message Type"
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
            "Tracker ID"
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
            "Tracker Key"
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
            "Producer Message"
    );
    messageType = edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type",1);
    softAssert.assertTrue(
            messageType.equals("Update"),
            "Producer Message  "+messageType
    );
    softAssert.closeAssert();
  }

  @Test(
          groups = { "Integration" },
          description = "verifyUpdateMMidcellProjectActiveCreation",
          priority = 2
  )
  public void verifyUpdateMMidcellProjectActiveCreation(Method method)
          throws Exception {

    AssertionsUtil softAssert = new AssertionsUtil();
    edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
    edbProducerTrackerPage.searchForValue(
            PROJECT_ACTIVE_M.projectId,
            "EPM:Tracker Key"
    );
    sleepFor(15);
    String messageType = edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type",1);
    softAssert.assertTrue(
            messageType.equals("Create"),
            "Post date import  "+messageType
    );
    commonPor = porHelper.updatePorWithHcDate(commonPor);

    softAssert.assertNotNull(commonPor,"Por should not be null");
    edbProducerTrackerPage.searchForValue(
            PROJECT_ACTIVE_M.projectId,
            "EPM:Tracker Key"
    );
    messageType = edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type",1);
    softAssert.assertTrue(
            messageType.equals("Update"),
            "Producer Message  "+messageType
    );
    messageType = edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type",2);
    softAssert.assertTrue(
            messageType.equals("Create"),
            "Producer Message  "+messageType
    );
    messageType = edbProducerTrackerPage.searchForValueInGrid("EPM:Tracker Key",2);
    softAssert.assertTrue(
            messageType.contains("PIER2"),
            "Tracker key  "+messageType
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
            "Timestamp"
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
            "User"
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
            "Message Type"
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
            "Tracker ID"
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
            "Tracker Key"
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
            "Producer Message"
    );

    softAssert.closeAssert();
  }

  @Test(
          groups = { "Integration" },
          description = "verifyDecomProjectActiveCreation",
          priority = 2
  )
  public void verifyUpdateMmidcellProjectOnHoldCreation(Method method)
          throws Exception {

    AssertionsUtil softAssert = new AssertionsUtil();
    edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
    edbProducerTrackerPage.searchForValue(
            PROJECT_ACTIVE_M.projectId,
            "EPM:Tracker Key"
    );
    String messageType = edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type",1);
    softAssert.assertTrue(
            messageType.equals("Update"),
            "Post date import  "+messageType
    );

    porHelper.updatePorWithUnBundling(commonPor,"Unbundled");
    edbProducerTrackerPage.searchForValue(
            PROJECT_ACTIVE.projectId,
            "EPM:Tracker Key"
    );
    messageType = edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type",1);
    softAssert.assertTrue(
            messageType.equals("Update"),
            "Producer Message  "+messageType
    );
    Project temp = projectHelper.sendToPierProject(PROJECT_ACTIVE);

    softAssert.assertNotNull(temp, "sent to pier");
//    edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
    edbProducerTrackerPage.searchForValue(
            PROJECT_ACTIVE.projectId,
            "EPM:Tracker Key"
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
            "Timestamp"
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
            "User"
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
            "Message Type"
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
            "Tracker ID"
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
            "Tracker Key"
    );
    softAssert.assertTrue(
            edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
            "Producer Message"
    );
    messageType = edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type",1);
    softAssert.assertTrue(
            messageType.equals("Update"),
            "Producer Message  "+messageType
    );
    softAssert.closeAssert();
  }
}
