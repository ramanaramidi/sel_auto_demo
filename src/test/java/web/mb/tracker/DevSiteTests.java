package web.mb.tracker;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Site;
import org.testng.annotations.Test;
import pages.web.Tracker.AddPORPage;
import pages.web.Tracker.PORTrackerPage;
import pages.web.Tracker.ProjectTrackerPage;
import pages.web.Tracker.RFSectorPage;
import pages.web.Tracker.site.AddSitePage;
import pages.web.Tracker.site.SiteTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.por.PorHelper;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class DevSiteTests extends BaseTest {

    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    RFSectorPage rfSectorPage;
    Site Site_Active;
    Site Site_Active1;
    ProjectTrackerPage projectTrackerPage;
    PORTrackerPage porTrackerPage;
    AddPORPage addPORPage;
    PorHelper porHelper = new PorHelper();
    String VALID_RING_PRIMARY_BUILD_SITE = "";
    Site ACTIVE_RING_NEW_SITE;
    Site site;
    SiteHelper siteHelper = new SiteHelper();
    SiteTrackerPage siteTracker;
    AddSitePage addNewSite;

    public DevSiteTests() {
        if (envURL == null) {
            envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");
        }
        if (testSuite == null) {
            testSuite = "TestRunner.xml";
        }
    }
    private void generateCommonData() {
        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringId, "Macro");
        site = new Site(ringId,"Candidate","New Site");
        site = siteHelper.createNewRingAndSite(ring,site);

        String ringIdActive = "SD" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActive, "Indoor Node");
        Site siteActive = new Site(ringIdActive,"Primary","Active Site");
        Site_Active = siteHelper.createActiveRingAndSite(ringActive,siteActive);

        String ringId_Active = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring_Active = new Ring("Active", ringId_Active, "Indoor Node");
        Site site_Active = new Site(ringId_Active,"Primary","Active Site");
        Site_Active1 = siteHelper.createActiveRingAndPrimaryActiveSite(ring_Active,site_Active);
    }

    @Test(groups = {"Integration"},description = "login",priority = 1)
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

    @Test(groups = {"Integration"}, description = "login", priority = 2)
    public void validateSiteClass(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Site siteNew = new Site(site.ringId,"Candidate","New Site");
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteNew.ringId, "R:Ring Code");
        addNewSite = siteTracker.selectAddNewSiteOption();
        siteTracker = addNewSite.addingNewSiteTracker(siteNew);
        siteTracker.searchForValue(site.siteId,"S:Site Code");
        siteTracker.selectEditOption();
        String siteType = site.siteType;
        siteTracker.setSiteType(siteType);
        siteTracker.getSiteClass(siteType);

    }

    @Test(groups = {"Integration"}, description = "validateBBU_BTSSectors_HUBCount", priority = 3)
    public void validateBBU_BTSSectors_HUBCount(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Site siteNew = new Site(site.ringId,"Candidate","New Site");
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteNew.ringId, "R:Ring Code");
        addNewSite = siteTracker.selectAddNewSiteOption();
        siteTracker = addNewSite.addingNewSiteTracker(siteNew);
        siteTracker.searchForValue(site.siteId,"S:Site Code");
        siteTracker.selectEditOption();
        String bbu_btsSitesCount = siteTracker.verifyingBBU_BTSSectors_Hub(site.siteId);
        softAssert.assertNotNull(bbu_btsSitesCount,"BBU/BTS Site displays BBU/BTS Sites at HUB count");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "validateBBU_BTSSectors_OnAirCount", priority = 4)
    public void validateBBU_BTSSectors_OnAirCount(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Site siteNew = new Site(site.ringId,"Candidate","New Site");
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteNew.ringId, "R:Ring Code");
        addNewSite = siteTracker.selectAddNewSiteOption();
        siteTracker = addNewSite.addingNewSiteTracker(siteNew);
        siteTracker.searchForValue(site.siteId,"S:Site Code");
        siteTracker.selectEditOption();
        String bbu_btsSitesCount = siteTracker.verifyingBBU_BTSSectors_Hub(site.siteId);
        softAssert.assertNotNull(bbu_btsSitesCount,"BBU/BTS Site displays On-Air sectors count");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "validateBBU_BTSSectors_OnAirCount", priority = 5)
    public void validateBBU_HUBSectors(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue("AMRI010C","S:Site Code");
        siteTracker.selectEditOption();
        String parentWindow = siteTracker.switchToProjectPage();
        String hubSectorsCount = siteTracker.checkBBU_HUBSitesCount();
        softAssert.assertTrue((hubSectorsCount)!=null,"BBU/BTS sites count is displayed");
        //softAssert.assertTrue(siteTracker.isDataPresentInTable(),"Site Tracker has BBU/BTS sites");
        // softAssert.assertTrue(siteTracker.isSiteStatus("Active")> 0, "Status should match");
        // String values = siteTracker.getCellValue(siteTracker.tableHandle("S:Site Code"));
        // System.out.println("table value site code is - " + values);
        // softAssert.assertTrue(siteTracker.isValuePresentInGrid("S:Site Code"),"Site Code value is present");
        siteTracker.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "displayOnly_NodeSectors", priority = 6)
    public void displayOnly_NodeSectors(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue("ARY4001A","S:Site Code");//00TESTOC_11TAC--sectorId
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        rfSectorPage.displayNodeSectors();
        rfSectorPage.switchToTracker(parentWindow);
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchSiteTracker1("00TESTOA", "S:Site Code");
        //rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "displaySector_SiteLink", priority = 7)
    public void displaySector_SiteLink(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        //change view to select BBU sites--write code
        siteTracker.searchForValue("00TESTOD","S:Site Code");
        siteTracker.selectEditOption();
        String parentWindow = siteTracker.switchToProjectPage();
        siteTracker.displaySector_SiteLink();
        int Site_SectorsCount =  siteTracker.getSite_SectorCount();
        softAssert.assertTrue(Site_SectorsCount > 0,"For the Particular Parent Site ID with BBU/BTS Site Category only can create Sectors");
        //siteTracker.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Last Updated By and Last Updated Date", priority = 2)
    public void getLastUpdatedDateAndLastUpdatedBy(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        addNewSite.updateSiteName();
        softAssert.assertTrue(addNewSite.verifySiteDetailsUpdate(superUser),"user should be able to edit the Site and the details should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Get S:Count of BBU/BTS Sites", priority = 2)
    public void sitesWithBbuBts(Method method) throws Exception {
        String ringIdActive = "SD" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActive, "Indoor Node");
        Site siteActive = new Site(ringIdActive,"Primary","Active Site");
        Site_Active = siteHelper.createActiveRingAndSite(ringActive,siteActive);

        String ringId_Active = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring_Active = new Ring("Active", ringId_Active, "Indoor Node");
        Site site_Active = new Site(ringId_Active,"Primary","Active Site");
        Site_Active1 = siteHelper.createActiveRingAndPrimaryActiveSite(ring_Active,site_Active);

        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(Site_Active1.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        int response = addNewSite.setSiteCategory("Hub");
        System.out.println("BBU Count Before::"+response );
        siteTracker.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        addNewSite.assignSiteWithHub(Site_Active1.siteId);
        siteTracker.searchForValue(Site_Active1.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        softAssert.assertTrue(addNewSite.bbuCount()>response,"Hub has been assigned to the Site");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Validate Aggregate Router is visible in S:Hub Site ID", priority = 2)
    public void validateAggregateRouterForSite(Method method) throws Exception {
        String ringIdActive = "SD" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActive, "Indoor Node");
        Site siteActive = new Site(ringIdActive,"Primary","Active Site");
        Site_Active = siteHelper.createActiveRingAndSite(ringActive,siteActive);

        String ringId_Active = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring_Active = new Ring("Active", ringId_Active, "Indoor Node");
        Site site_Active = new Site(ringId_Active,"Primary","Active Site");
        Site_Active1 = siteHelper.createActiveRingAndPrimaryActiveSite(ring_Active,site_Active);

        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(Site_Active1.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        addNewSite.setSiteCategory("Aggregate Router");
        siteTracker.searchForValue(Site_Active.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        String hudId = addNewSite.assignSiteWithAggregateRouter(Site_Active1.siteId);
        softAssert.assertNotEquals(hudId,Site_Active1.siteId,"SIte with Aggregate router is not displayed");
        softAssert.closeAssert();
    }
}
