package web.mb.tracker;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Site;
import org.testng.annotations.Test;
import pages.web.Tracker.AddRingPage;
import pages.web.Tracker.RingTrackerPage;
import pages.web.Tracker.site.AcsPage;
import pages.web.Tracker.site.AddSitePage;
import pages.web.Tracker.site.SiteTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class AcsTests extends BaseTest {

    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    SiteTrackerPage siteTracker;
    RingTrackerPage ringTracker;

    AddSitePage addNewSite;
    AddRingPage addNewRing;
    AcsPage acsPage;
    SiteHelper siteHelper = new SiteHelper();
   ;
    public AcsTests()
    {
        if(envURL == null) {envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");}
        if(testSuite == null) {testSuite = 	"TestRunner.xml";}
    }


    @Test(groups = {"Integration"},description = "login",priority = 1)
    public void login(Method method) throws Exception {
        loginPage = new LoginPage(driver);
        loginPage.doLogin(LoginOptionEnum.SAML);
        String url = loginPage.getLoginUrl(alphaUser);
        if(url!=null){
            loginPage.launchUrl(url);
        }
       //  commonDataGenerator();
         mainSideMenu = loginPage.LoginAsUser(superUser);
    }
    @Test(groups = {"Integration"},description = "Acs Test For Small Cell Hub With Hub Site Id",priority = 2)
    public void smallCellHubActiveRingAndSiteAcsWithHubSiteId(Method method) throws Exception {
        //DATA CREATION
        String ringIdActiveProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActiveProject, "Macro");
        Site siteActiveFinal = new Site(ringIdActiveProject,"Final Build","Active Site");
        siteActiveFinal = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive,siteActiveFinal);
        Site siteActiveCandidate = new Site(ringIdActiveProject,"Candidate","New Site");
        siteActiveCandidate = siteHelper.createActiveSite(siteActiveCandidate);


        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteActiveCandidate.siteId,"S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        acsPage = addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryAggregateRouter();
        siteTracker = acsPage.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal.siteId,"S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        acsPage= addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryHub();
        acsPage.getSiteCode(siteActiveCandidate.siteId);
        softAssert.assertTrue(acsPage.hubClusterIdValidation(),"cluster id should display");
        acsPage.goToSiteTracker();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Acs Test For Small Cell Hub Without Hub Site Id",priority = 3)
    public void smallCellHubActiveRingAndSiteAcsWithoutHubSiteId(Method method) throws Exception {
        String ringIdActiveProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActiveProject, "Macro");
        Site siteActiveFinal = new Site(ringIdActiveProject,"Final Build","Active Site");
        siteActiveFinal = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive,siteActiveFinal);

        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal.siteId,"S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        acsPage = addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryHubForHubClusterId();
        softAssert.assertFalse(acsPage.hubClusterIdValidation(), "cluster id should Not display");
        acsPage.goToSiteTracker();
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Acs Test For Small Cell Hub With New Ring And Site Hub Site Id",priority = 4)
    public void smallCellHubNewRingAndSiteAcsWithHubSiteId(Method method) throws Exception {
        //DATA CREATION
        String ringIdActiveProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActiveProject, "Aggregate Router");
        Site siteActiveFinal = new Site(ringIdActiveProject,"Primary","Active Site");
        siteActiveFinal = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive,siteActiveFinal);
        Site siteActiveCandidate = new Site(ringIdActiveProject,"Candidate","New Site");
        siteActiveCandidate = siteHelper.createActiveSite(siteActiveCandidate);


        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteActiveCandidate.siteId,"S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        acsPage = addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryAggregateRouter();
        siteTracker = acsPage.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal.siteId,"S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        acsPage= addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryHub();
        acsPage.getSiteCode(siteActiveCandidate.siteId);
        softAssert.assertTrue(acsPage.hubClusterIdValidation(),"cluster id should display");
        acsPage.goToSiteTracker();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Acs Test For Small Cell Bbu With New Ring And Site Id",priority = 5)
    public void smallCellBbuNewRingAndSiteAcs(Method method) throws Exception {
        String ringIdActiveProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActiveProject, "Outdoor Micro");
        Site siteActiveFinal = new Site(ringIdActiveProject,"Primary","Active Site");
        siteActiveFinal = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive,siteActiveFinal);
        Site siteActiveCandidate = new Site(ringIdActiveProject,"Candidate","New Site");
        siteActiveCandidate = siteHelper.createActiveSite(siteActiveCandidate);


        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteActiveCandidate.siteId,"S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        acsPage = addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryAggregateRouter();
        siteTracker = acsPage.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal.siteId,"S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        acsPage = addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryBbu();
        acsPage.getSiteCode(siteActiveCandidate.siteId);
        acsPage.selectInstallationProject();
        softAssert.assertTrue(acsPage.hubClusterIdValidation(),"cluster id should display");
        acsPage.goToSiteTracker();
        softAssert.closeAssert();
      }
    @Test(groups = {"Integration"},description = "Acs Test For Das Hub With Hub Site Id",priority = 6)
    public void dasHubActiveRingAndSiteAcsWithHubSiteId(Method method) throws Exception {
        //DATA CREATION
        String ringIdActiveProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActiveProject, "Macro");
        Site siteActiveFinal = new Site(ringIdActiveProject,"Final Build","Active Site");
        siteActiveFinal = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive,siteActiveFinal);
        Site siteActiveCandidate = new Site(ringIdActiveProject,"Candidate","New Site");
        siteActiveCandidate = siteHelper.createActiveSite(siteActiveCandidate);


        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteActiveCandidate.siteId,"S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        acsPage = addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryAggregateRouter();
        siteTracker = acsPage.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal.siteId,"S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        acsPage= addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryHub();
        acsPage.getSiteCode(siteActiveCandidate.siteId);
        softAssert.assertTrue(acsPage.hubClusterIdValidation(),"cluster id should display");
        acsPage.goToSiteTracker();
        softAssert.closeAssert();
    }


    @Test(groups = {"Integration"},description = "Acs Test For Das Hub Without Hub Site Id",priority = 7)
    public void dasHubActiveRingAndSiteAcsWithoutHubSiteId(Method method) throws Exception {
        String ringIdActiveProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActiveProject, "Macro");
        Site siteActiveFinal = new Site(ringIdActiveProject,"Final Build","Active Site");
        siteActiveFinal = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive,siteActiveFinal);

        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal.siteId,"S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        acsPage = addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryHubForHubClusterId();
        softAssert.assertFalse(acsPage.hubClusterIdValidation(), "cluster id should Not display");
        acsPage.goToSiteTracker();
        softAssert.closeAssert();
    }


    @Test(groups = {"Integration"},description = "Acs Test For Das Hub With New Ring And Site With Hub Site Id",priority = 8)
    public void dasHubNewRingAndSiteAcsWithHubSiteId(Method method) throws Exception {
        //DATA CREATION
        String ringIdActiveProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActiveProject, "Outdoor DAS Hotel");
        Site siteActiveFinal = new Site(ringIdActiveProject,"Final Build","Active Site");
        siteActiveFinal = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive,siteActiveFinal);
        Site siteActiveCandidate = new Site(ringIdActiveProject,"Candidate","New Site");
        siteActiveCandidate = siteHelper.createActiveSite(siteActiveCandidate);


        AssertionsUtil softAssert = new AssertionsUtil();
        ringTracker = mainSideMenu.goToRingTracker();
        ringTracker.searchForValue(ringActive.ringId,"R:Ring Code");
        addNewRing = ringTracker.selectEditOption();
        acsPage = addNewRing.goToAcsSection();
        acsPage.addVenueCategoryAndNameToTheRing();
        siteTracker = acsPage.goToSiteTracker();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteActiveCandidate.siteId,"S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        acsPage = addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryAggregateRouter();
        siteTracker = acsPage.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal.siteId,"S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        acsPage= addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryHub();
        acsPage.getSiteCode(siteActiveCandidate.siteId);
        softAssert.assertTrue(acsPage.hubClusterIdValidation(),"cluster id should display");
        acsPage.goToSiteTracker();
        softAssert.closeAssert();
    }


    @Test(groups = {"Integration"},description = "Acs Test For Das Bts With New Ring And Site Id ",priority = 9)
    public void dasBtsNewRingAndSiteAcs(Method method) throws Exception {
        String ringIdActiveProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActiveProject, "Outdoor DAS");
        Site siteActiveFinal = new Site(ringIdActiveProject,"Primary","Active Site");
        siteActiveFinal = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive,siteActiveFinal);
        Site siteActiveCandidate = new Site(ringIdActiveProject,"Candidate","New Site");
        siteActiveCandidate = siteHelper.createActiveSite(siteActiveCandidate);


        AssertionsUtil softAssert = new AssertionsUtil();
        ringTracker = mainSideMenu.goToRingTracker();
        ringTracker.searchForValue(ringActive.ringId,"R:Ring Code");
        addNewRing = ringTracker.selectEditOption();
        acsPage = addNewRing.goToAcsSection();
        acsPage.addVenueCategoryAndNameToTheRing();
        siteTracker = acsPage.goToSiteTracker();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteActiveCandidate.siteId,"S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        acsPage = addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryAggregateRouter();
        siteTracker = acsPage.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal.siteId,"S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        acsPage = addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryBbu();
        acsPage.getSiteCode(siteActiveCandidate.siteId);
        acsPage.selectInstallationProject();
        softAssert.assertTrue(acsPage.hubClusterIdValidation(),"cluster id should display");
        acsPage.goToSiteTracker();
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Acs Test For Small Cell Hub With Site Category Aggregate Router",priority = 10)
    public void smallCellHubNewRingAndSiteAcsWithSiteCategoryAggregateRouter(Method method) throws Exception {
        //DATA CREATION
        String ringIdActiveProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActiveProject, "Aggregate Router");
        Site siteActiveFinal = new Site(ringIdActiveProject, "Primary", "Active Site");
        siteActiveFinal = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive, siteActiveFinal);

        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        acsPage = addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryAggregateRouter();
        acsPage.acsSiteFields();
        //acsPage.clickApply();
        softAssert.assertTrue(acsPage.hubClusterIdValidation(), "cluster id should display");
        acsPage.goToSiteTracker();
        softAssert.closeAssert();

    }

    @Test(groups = {"Integration"},description = "Acs Test For DAs Hub With Site Category Aggregate Router",priority = 11)
    public void dasHubNewRingAndSiteAcsWithSiteCategoryAggregateRouter(Method method) throws Exception {
        //DATA CREATION
        String ringIdActiveProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActiveProject, "Aggregate Router");
        Site siteActiveFinal = new Site(ringIdActiveProject, "Primary", "Active Site");
        siteActiveFinal = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive, siteActiveFinal);

        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        acsPage = addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryAggregateRouter();
        acsPage.acsSiteFields();
      //  acsPage.clickApply();
        softAssert.assertTrue(acsPage.hubClusterIdValidation(), "cluster id should display");
        acsPage.goToSiteTracker();
        softAssert.closeAssert();

    }


    @Test(groups = {"Integration"},description = "Acs Test For Small Cell Hub With EXisting Ring And Site With Site Category Aggregate Router",priority = 12)
    public void smallCellHubExistingRingAndSiteAcs(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue("Macro", "R:Ring ID Description");
        siteTracker.selectSiteWithRingStatusActive();
        addNewSite = siteTracker.selectEditOption();
        acsPage = addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryAggregateRouter();
        acsPage.acsSiteFields();
        softAssert.assertTrue(acsPage.hubClusterIdValidation(), "cluster id should display");
        acsPage.goToSiteTracker();
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Acs Test For Das Hub With EXisting Ring And Site With Site Category Aggregate Router",priority = 13)
    public void dasHubExistingRingAndSiteAcs(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue("Macro", "R:Ring ID Description");
        siteTracker.selectSiteWithRingStatusActive();
        addNewSite = siteTracker.selectEditOption();
        acsPage = addNewSite.goToAcsSection();
        acsPage.selectSiteCategoryAggregateRouter();
        acsPage.acsSiteFields();
        softAssert.assertTrue(acsPage.hubClusterIdValidation(), "cluster id should display");
        acsPage.goToSiteTracker();
        softAssert.closeAssert();

    }

}
