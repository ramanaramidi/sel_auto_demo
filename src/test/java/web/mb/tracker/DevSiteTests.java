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
    Site site;
    SiteHelper siteHelper = new SiteHelper();
    SiteTrackerPage siteTracker;
    AddSitePage addNewSite;

    public DevSiteTests() {
        if (envURL == null) {
            envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");
        }
        if (testSuite == null) {
            testSuite = "sectorSet.xml";
        }
    }
    private void generateCommonData() {
        String ringId = "SL" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringId, "Macro");
        site = new Site(ringId,"Candidate","New Site");
        site = siteHelper.createNewRingAndSite(ring,site);

        String ringId_Active = "ZU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring_Active = new Ring("Active", ringId_Active, "Indoor Node");
        Site site_Active = new Site(ringId_Active,"Primary","Active Site");
        Site_Active = siteHelper.createActiveRingAndPrimaryActiveSite(ring_Active,site_Active);
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
        addNewSite = siteTracker.selectAddNewSiteOption();
        siteTracker = addNewSite.addingNewSiteTracker(siteNew);
        siteTracker.searchForValue(site.siteId,"S:Site Code");
        siteTracker.selectEditOption();
        String siteType = site.siteType;
        siteTracker.setSiteType(siteType);
        siteTracker.getSiteClass(siteType);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "validateBBU_BTSSectors_HUBCount", priority = 3)
    public void validateBBU_BTSSectors_HUBCount(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(Site_Active.siteId,"S:Site Code");
        siteTracker.selectEditOption();
        String siteCode = "";
        String bbu_btsSitesCount = siteTracker.verifyingBBU_BTSSectors_Hub(Site_Active.siteId,siteCode);
        softAssert.assertNotNull(bbu_btsSitesCount,"BBU/BTS Site displays BBU/BTS Sites at HUB count");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "validateBBU_BTSSectors_OnAirCount", priority = 4)
    public void validateBBU_BTSSectors_OnAirCount(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(Site_Active.siteId,"S:Site Code");
        siteTracker.selectEditOption();
        String siteCode = "SA01101C";
        String bbu_btsSitesCount = siteTracker.verifyingBBU_BTSSectors_Hub(Site_Active.siteId,siteCode);
        softAssert.assertNotNull(bbu_btsSitesCount,"BBU/BTS Site displays On-Air sectors count");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "validateBBU_HUBSectors", priority = 5)
    public void validateBBU_HUBSectors(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(Site_Active.siteId,"S:Site Code");
        siteTracker.selectEditOption();
        String parentWindow = siteTracker.switchToProjectPage();
        String siteCode1 = "AMRI010A";
        String hubSectorsCount = siteTracker.checkBBU_HUBSitesCount(siteCode1);
        softAssert.assertTrue((hubSectorsCount)!=null,"BBU/BTS sites count is displayed");
        siteTracker.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "displayOnly_NodeSectors", priority = 6)
    public void displayOnly_NodeSectors(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue("ARY4001A","S:Site Code");//00TESTOC_11TAC--sectorId
        rfSectorPage.selectEditOption();
        String siteCode = "5TC1903A";
        rfSectorPage.displayNodeSectors(siteCode);
        rfSectorPage.clickMainLogo();
        siteTracker = rfSectorPage.clickingSiteTracker();
        siteTracker.searchSiteTracker1("00TESTOA", "S:Site Code");
//        String response1 = siteTracker.searchForValueInGrid("S:Site Code",1);
//        System.out.println("Site Code is - " + response1);
//        softAssert.assertContains(response1,"00TESTOA","Site Code is Present");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "displaySector_SiteLink", priority = 7)
    public void displaySector_SiteLink(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(Site_Active.siteId,"S:Site Code");
        siteTracker.selectEditOption();
        siteTracker.switchToProjectPage();
        siteTracker.displaySector_SiteLink(Site_Active.siteId);
        int Site_SectorsCount =  siteTracker.getSite_SectorCount();
        softAssert.assertTrue(Site_SectorsCount > 0,"For the Particular Parent Site ID with BBU/BTS Site Category only can create Sectors");
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
        String BBU_RingID = "SD" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring BBU_Ring = new Ring("Active", BBU_RingID, "Indoor Node");
        Site BBU_Site = new Site(BBU_RingID,"Primary","Active Site");
        BBU_Site = siteHelper.createActiveRingAndSite(BBU_Ring,BBU_Site);

        String Hub_RingID = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring Hub_Ring = new Ring("Active", Hub_RingID, "Indoor Node");
        Site Hub_Site = new Site(Hub_RingID,"Primary","Active Site");
        Hub_Site = siteHelper.createActiveRingAndPrimaryActiveSite(Hub_Ring,Hub_Site);

        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(Hub_Site.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        int response = addNewSite.setSiteCategory("Hub");
        System.out.println("BBU Count Before::"+response );
        siteTracker.searchForValue(BBU_Site.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        addNewSite.assignSiteWithHub(Hub_Site.siteId);
        siteTracker.searchForValue(Hub_Site.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        softAssert.assertTrue(addNewSite.bbuCount()>response,"Hub has been assigned to the Site");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Validate Aggregate Router is visible in S:Hub Site ID", priority = 2)
    public void validateAggregateRouterForSite(Method method) throws Exception {
        String Bbu_RingID = "SY" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring Bbu_Ring = new Ring("Active", Bbu_RingID, "Indoor Node");
        Site Bbu_Site = new Site(Bbu_RingID,"Primary","Active Site");
        Bbu_Site = siteHelper.createActiveRingAndSite(Bbu_Ring,Bbu_Site);

        String AR_RingID = "BU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring AR_Ring = new Ring("Active", AR_RingID, "Indoor Node");
        Site AR_Site = new Site(AR_RingID,"Primary","Active Site");
        AR_Site = siteHelper.createActiveRingAndPrimaryActiveSite(AR_Ring,AR_Site);

        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(AR_Site.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        addNewSite.setSiteCategory("Aggregate Router");
        siteTracker.searchForValue(Bbu_Site.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        String hudId = addNewSite.assignSiteWithAggregateRouter(AR_Site.siteId);
        softAssert.assertNotEquals(hudId,AR_Site.siteId,"SIte with Aggregate router is not displayed");
        softAssert.closeAssert();
    }
}
