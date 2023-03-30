package web.mb.edb;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Sector;
import commons.objects.Site;
import org.testng.annotations.Test;
import pages.web.Tracker.EdbConsumerTrackerPage;
import pages.web.Tracker.RingTrackerPage;
import pages.web.Tracker.site.SiteTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.edb.EdbHelper;
import rest.sector.SectorHelper;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EdbConsumerTest extends BaseTest {
    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");

    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    Site siteForSector;
    RingTrackerPage ringTracker;
    SiteHelper siteHelper = new SiteHelper();
    SectorHelper sectorHelper = new SectorHelper();
    Sector sector5G;
    Ring ringNew;
    SiteTrackerPage siteTrackerPage;
    EdbHelper edbHelper = new EdbHelper();
    EdbConsumerTrackerPage edbConsumerTrackerPage ;
    Map<String,String> keys = new HashMap<>();
    Map<String,String> trackers = new HashMap<>();
    public EdbConsumerTest()
    {
        if(envURL == null) {envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");}
        if(testSuite == null) {testSuite = "sectorSet.xml";}
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
        generateData();
        mainSideMenu = loginPage.LoginAsUser(superUser);

    }

    private void generateData(){
        String ringIdForSector = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActiveForSector = new Ring("Active", ringIdForSector, "Indoor Node");
        siteForSector = new Site(ringIdForSector,"Primary","Active Site");
        siteForSector = siteHelper.createActiveRingAndSite(ringActiveForSector,siteForSector);
        sector5G = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_12NCB"));
        String test = edbHelper.createNewEdbCEntryForUpdatingExistingSector(sector5G);
        System.out.println(test+"____________");
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 2)
    public void createNewEdbCEntryForUpdatingExistingSector(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String key = edbHelper.createNewEdbCEntryForUpdatingExistingSector(sector5G);
        System.out.println(key+"____________");
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Interface","ECM:Message Id",key);
        softAssert.assertContains(response,"Asset","ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Consumer Message","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Created Timestamp (PT)","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        keys.put("UpdateExistingSector",key);
        trackers.put("UpdateExistingSector",sector5G.sectorId);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 2)
    public void createNewEdbCEntryForAddingExistingSector(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String key = edbHelper.addToExistingSectorEDB(sector5G,superUser);
        System.out.println(key+"____________");
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Interface","ECM:Message Id",key);
        softAssert.assertContains(response,"Asset","ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Consumer Message","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Created Timestamp (PT)","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        keys.put("AddingExistingSector",key);
        trackers.put("AddingExistingSector",sector5G.sectorId);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 2)
    public void createNewEdbCEntryForCreatingNewRing(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String ringIdNewProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringIdNewProject, "Indoor Node");
        String key = edbHelper.createNewRingEDB(ring,superUser);
        ringNew = ring;
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Interface","ECM:Message Id",key);
        softAssert.assertContains(response,"Asset","ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Consumer Message","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Created Timestamp (PT)","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        keys.put("CreateNewRing",key);
        trackers.put("CreateNewRing",ring.ringId);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 3)
    public void createNewEdbCEntryForUpdatingRingLatLong(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String ringIdNewProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringIdNewProject, "Indoor Node");
        String key = edbHelper.updateRingLatLongEDB(ring,superUser);
        ringNew = ring;
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Interface","ECM:Message Id",key);
        softAssert.assertContains(response,"Asset","ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Consumer Message","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Created Timestamp (PT)","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        keys.put("UpdateLatLongRing",key);
        trackers.put("UpdateLatLongRing",ring.ringId);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 3)
    public void createNewEdbCEntryForUpdatingSiteLatLong(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String key = edbHelper.updateSiteLatLongEDB(siteForSector,superUser);
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Interface","ECM:Message Id",key);
        softAssert.assertContains(response,"Asset","ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Consumer Message","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Created Timestamp (PT)","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        keys.put("UpdateLatLongSite",key);
        trackers.put("UpdateLatLongSite",siteForSector.siteId);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "createNewEdbCEntryForUpdatingSiteOnAirOffAir",priority = 3)
    public void createNewEdbCEntryForUpdatingSiteOnAirOffAir(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String key = edbHelper.updateElementOnAirOffAirSiteEDB(siteForSector);
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Interface","ECM:Message Id",key);
        softAssert.assertContains(response,"Element","ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Consumer Message","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Created Timestamp (PT)","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        keys.put("UpdateOnAirOffAirDates",key);
        trackers.put("UpdateOnAirOffAirDates",siteForSector.siteId);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "createNewEdbCEntryForUpdatingSiteOnAirOffAir",priority = 3)
    public void createNewEdbCEntryForSiteWithError(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Site site = new Site(siteForSector.ringId,"Primary","Active Site");
        site.siteId = site.siteId+"A";
        String key = edbHelper.createNewSiteEDB(site,superUser);
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Interface","ECM:Message Id",key);
        softAssert.assertContains(response,"Asset","ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Consumer Message","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Created Timestamp (PT)","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        keys.put("incorrectSite",key);
        trackers.put("incorrectSite",site.siteId);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "createNewEdbCEntryForUpdatingSiteOnAirOffAir",priority = 3)
    public void createNewEdbCEntryForSiteWithNoRingInMB(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Site site = new Site(siteForSector.ringId,"Primary","Active Site");
        site.siteId = site.siteId+"A";
        String key = edbHelper.createNewSiteEDB(site,superUser);
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Interface","ECM:Message Id",key);
        softAssert.assertContains(response,"Asset","ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Consumer Message","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Created Timestamp (PT)","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        keys.put("incorrectSite",key);
        trackers.put("incorrectSite",site.siteId);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 2)
    public void createNewEdbCEntryForCreatingNewSector(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector sectorLTE = new Sector(siteForSector.siteId,siteForSector.siteId+"_12LA");
        String key = edbHelper.createNewSectorEDB(sectorLTE,superUser);
        System.out.println(key+"____________");
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Interface","ECM:Message Id",key);
        softAssert.assertContains(response,"Asset","ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Consumer Message","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Created Timestamp (PT)","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        keys.put("CreateNewSector",key);
        trackers.put("CreateNewSector",sectorLTE.sectorId);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 2)
    public void createNewEdbCEntryForCreatingNewSite(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Site site = new Site(siteForSector.ringId,"Candidate","New Site");
        site.siteId = siteForSector.ringId+"A";
        String key = edbHelper.createNewSiteEDB(site,superUser);
        System.out.println(key+"____________");
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Interface","ECM:Message Id",key);
        softAssert.assertContains(response,"Asset","ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Consumer Message","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Created Timestamp (PT)","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        keys.put("CreateNewSite",key);
        trackers.put("CreateNewSite",site.siteId);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 2)
    public void createElementEventSiteEDB(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String key = edbHelper.createElementEventSiteEDB(siteForSector,superUser);
        System.out.println(key+"____________");
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Interface","ECM:Message Id",key);
        softAssert.assertContains(response,"Element","ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Consumer Message","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Created Timestamp (PT)","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        keys.put("MovingExistingSite",key);
        trackers.put("MovingExistingSite",siteForSector.siteId);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 2)
    public void createPierMessageEDB(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String key = edbHelper.createPierMessageEDB(siteForSector,superUser);
        System.out.println(key+"____________");
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Interface","ECM:Message Id",key);
        softAssert.assertContains(response,"Pier","ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Consumer Message","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Created Timestamp (PT)","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        keys.put("MovingExistingSite",key);
        trackers.put("MovingExistingSite",siteForSector.siteId);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 2)
    public void createElementEventSectorEDB(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String key = edbHelper.createElementEventSectorEDB(siteForSector,superUser);
        System.out.println(key+"____________");
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Interface","ECM:Message Id",key);
        softAssert.assertContains(response,"Element-sector","ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Consumer Message","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Created Timestamp (PT)","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        keys.put("MovingExistingSite",key);
        trackers.put("MovingExistingSite",siteForSector.siteId);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 2)
    public void createNewEdbCEntryForMovingExistingSite(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String key = edbHelper.moveExistingSiteEDB(siteForSector,superUser);
        System.out.println(key+"____________");
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Interface","ECM:Message Id",key);
        softAssert.assertContains(response,"Asset","ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Consumer Message","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Created Timestamp (PT)","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        keys.put("MovingExistingSite",key);
        trackers.put("MovingExistingSite",siteForSector.siteId);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 2)
    public void createNewEdbCEntryForMovingNonExistingSite(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Site site = new Site(siteForSector.ringId,"Primary","Active Site");
        site.siteId = siteForSector.ringId+"Z";
        String key = edbHelper.moveExistingSiteEDB(site,superUser);
        System.out.println(key+"____________");
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Interface","ECM:Message Id",key);
        softAssert.assertContains(response,"Asset","ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Consumer Message","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Created Timestamp (PT)","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        keys.put("MovingNonExistingSite",key);
        trackers.put("MovingNonExistingSite",site.siteId);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 2)
    public void createNewEdbCEntryForCreatingSiteWithoutRing(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Site site = new Site("ZZ" + MiscHelpers.getRandomString(5, true).toUpperCase(),"Primary","Active Site");
        site.siteId = site.ringId+"Z";
        String key = edbHelper.moveExistingSiteEDB(site,superUser);
        System.out.println(key+"____________");
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Interface","ECM:Message Id",key);
        softAssert.assertContains(response,"Asset","ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Consumer Message","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Created Timestamp (PT)","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        keys.put("MovingNonExistingRing",key);
        trackers.put("MovingNonExistingRing",site.siteId);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 20)
    public void verifyEdbCEntryForNewRing(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String key = keys.get("CreateNewRing");
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Asset Operation","ECM:Message Id",key);
        softAssert.assertContains(response,"Created","ECM:Asset Operation Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Asset Record Type","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Asset Created By","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Asset Created By Should Match");
        System.out.println(":::"+response);
        ringTracker = mainSideMenu.goToRingTracker();
        ringTracker.searchForValue(ringNew.ringId, "R:Ring Code");
        softAssert.assertTrue(ringTracker.isDataPresentInTable(), "The ring should have been created");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 20)
    public void verifyEdbCEntryForNewIncorrectSite(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String key = trackers.get("incorrectSite");
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(key ,"S:Site Code");
        softAssert.assertFalse(siteTrackerPage.isDataPresentInTable(),"The Site should not have been created");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 20)
    public void verifyEdbCEntryForCreateNewSite(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String key = keys.get("CreateNewSite");
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Asset Operation","ECM:Message Id",key);
        softAssert.assertContains(response,"Created","ECM:Asset Operation Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Asset Record Type","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Asset Created By","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Asset Created By Should Match");
        System.out.println(":::"+response);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 20)
    public void verifyEdbCEntryForCreateNewSector(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String key = keys.get("CreateNewSector");
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Asset Operation","ECM:Message Id",key);
        softAssert.assertContains(response,"Created","ECM:Asset Operation Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Asset Record Type","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Asset Created By","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Asset Created By Should Match");
        System.out.println(":::"+response);
        softAssert.closeAssert();
    }

    //@Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 30)
    public void
    verifyEdbCEntryForMovingExistingSite(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String key = keys.get("MovingExistingSite");
        edbConsumerTrackerPage= mainSideMenu.goToEdbConsumerPage();
        edbConsumerTrackerPage.searchForValue(key ,"ECM:Message Id");
        String response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Asset Operation","ECM:Message Id",key);
        softAssert.assertContains(response,"Created","ECM:Asset Operation Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Asset Record Type","ECM:Message Id",key);
        softAssert.assertNotNull(response,"ECM:Interface Should Match");
        System.out.println(":::"+response);
        response = edbConsumerTrackerPage.searchForValueInGrid("ECM:Asset Created By","ECM:Message Id",key);
        //softAssert.assertNotNull(response,"ECM:Asset Created By Should Match");
        System.out.println(":::"+response);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 21)
    public void verifyEdbCEntryMoveForNonExistingSite(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String key = trackers.get("MovingNonExistingSite");
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(key ,"S:Site Code");
        softAssert.assertTrue(siteTrackerPage.isDataPresentInTable(),"The Site should not have been created");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyEdbMessagePostSiteCreation_Edb",priority = 20)
    public void verifyEdbCEntryMoveForNonExistingRing(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String key = trackers.get("MovingNonExistingRing");
        siteTrackerPage = mainSideMenu.goToSiteTracker();
        siteTrackerPage.searchForValue(key ,"S:Site Code");
        softAssert.assertFalse(siteTrackerPage.isDataPresentInTable(),"The Site should not have been created");
        softAssert.closeAssert();
    }

}
