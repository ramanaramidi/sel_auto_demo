package web.mb.feature;

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

public class MidCellAcsTests extends BaseTest {

    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    SiteTrackerPage siteTracker;
    RingTrackerPage ringTracker;

    AddSitePage addNewSite;
    AddRingPage addNewRing;
    AcsPage acsPage;
    SiteHelper siteHelper = new SiteHelper();


    public MidCellAcsTests() {
        if (envURL == null) {
            envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");
        }
        if (testSuite == null) {
            testSuite = "TestRunner.xml";
        }
    }


    @Test(groups = {"Integration"}, description = "login", priority = 1)
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
        mainSideMenu = loginPage.LoginAsUser(superUser);
    }

    @Test(groups = {"Integration"}, description = "Acs Test For Small Cell Hub With Hub Site Id", priority = 2)
    public void AcsMidCellWithSiteCategoryHub(Method method) throws Exception {
        //DATA CREATION
        String ringIdActiveProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActiveProject, "Midcell");
        Site siteActiveFinal = new Site(ringIdActiveProject, "Final Build", "Active Site");
        siteActiveFinal = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive, siteActiveFinal);


        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        addNewSite.selectSiteCategoryHub();
        softAssert.assertTrue(addNewSite.hubClusterIdValidation(),"cluster id Field Is Populated");
        softAssert.assertTrue(addNewSite.bbuBtsTabValidation(),"Bbu/Bts Tab is present");
        softAssert.assertTrue(addNewSite.rfSectorTabValidation(),"RfSector Tab Is Present");
        siteTracker = addNewSite.goToSiteTracker();
        softAssert.closeAssert();
    }



    @Test(groups = {"Integration"}, description = "Acs Test For Small Cell Hub With Hub Site Id", priority = 2)
    public void AcsMidCellWithSiteCategoryAgregateRouter(Method method) throws Exception {
        //DATA CREATION

        String ringIdActiveProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        String ringIdActiveProject1 = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActiveProject, "Midcell");
        Ring ringActive1 = new Ring("Active", ringIdActiveProject1, "Midcell");
        Site siteActiveFinal = new Site(ringIdActiveProject, "Final Build", "Active Site");
        siteActiveFinal = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive, siteActiveFinal);
        Site siteActiveFinal1 = new Site(ringIdActiveProject1, "Final Build", "Active Site");
        siteActiveFinal1 = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive1, siteActiveFinal1);

        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        addNewSite.selectSiteCategoryHub();
        siteTracker = addNewSite.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal1.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        addNewSite.selectSiteCategoryAggregateRouter();
        addNewSite.assignSiteWithHub1(siteActiveFinal.siteId);
        softAssert.assertTrue(addNewSite.hubClusterIdValidation(),"cluster id Field Is Populated");
        softAssert.assertTrue(addNewSite.allSiteIdAtHubIdValidation(siteActiveFinal.siteId,siteActiveFinal1.siteId),"All Site Id's At Hub Field Is Populated");
        siteTracker = addNewSite.goToSiteTracker();
        softAssert.closeAssert();
    }



    @Test(groups = {"Integration"}, description = "Acs Test For Small Cell Hub With Hub Site Id", priority = 2)
    public void AcsMidCellWithSiteCategoryHubWithBbuBts(Method method) throws Exception {
        //DATA CREATION

        String ringIdActiveProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        String ringIdActiveProject1 = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActiveProject, "Midcell");
        Ring ringActive1 = new Ring("Active", ringIdActiveProject1, "Midcell");
        Site siteActiveFinal = new Site(ringIdActiveProject, "Final Build", "Active Site");
        siteActiveFinal = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive, siteActiveFinal);
        Site siteActiveFinal1 = new Site(ringIdActiveProject1, "Final Build", "Active Site");
        siteActiveFinal1 = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive1, siteActiveFinal1);

        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        addNewSite.selectSiteCategoryHub();
        int response =   addNewSite.bbuBtsCount();
        System.out.println("BBU Count Before::"+response );
        siteTracker = addNewSite.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal1.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        addNewSite.selectSiteCategoryBbuBts1();
        addNewSite.assignSiteWithHub1(siteActiveFinal.siteId);
        softAssert.assertTrue(addNewSite.finalBbuBtsCount()>response,"Bbu?Bts Site Connected To Hub");
        softAssert.assertTrue(addNewSite.hubClusterIdValidation(),"cluster id Field Is Populated");
        softAssert.assertTrue(addNewSite.rfSectorTabValidation(),"RfSector Tab Is Present");
        softAssert.assertTrue(addNewSite.allSiteIdAtHubIdValidation(siteActiveFinal.siteId,siteActiveFinal1.siteId),"All Site Id's At Hub Field Is Populated");
        siteTracker = addNewSite.goToSiteTracker();
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Acs Test For Small Cell Hub With Hub Site Id", priority = 2)
    public void AcsMidCellWithSiteCategoryBbuBtsWithRouter(Method method) throws Exception {
        //DATA CREATION
        String ringIdActiveProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        String ringIdActiveProject1 = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActiveProject, "Midcell");
        Ring ringActive1 = new Ring("Active", ringIdActiveProject1, "Midcell");
        Site siteActiveFinal = new Site(ringIdActiveProject, "Final Build", "Active Site");
        siteActiveFinal = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive, siteActiveFinal);
        Site siteActiveFinal1 = new Site(ringIdActiveProject1, "Final Build", "Active Site");
        siteActiveFinal1 = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive1, siteActiveFinal1);
        // siteActiveFinal1 = siteHelper.createActiveSite(siteActiveFinal1);

        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        addNewSite.selectSiteCategoryAggregateRouter();
        siteTracker = addNewSite.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal1.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        addNewSite.selectSiteCategoryBbuBts1();
        addNewSite.assignSiteWithRouter(siteActiveFinal.siteId);
        softAssert.assertFalse(addNewSite.isDataPresentInTable(), "Table Doesn't contains the Router SiteId's");
        siteTracker = addNewSite.goToSiteTrackerPage();
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Acs Test For Small Cell Hub With Hub Site Id", priority = 2)
    public void AcsMidCellWithSiteCategoryAgregateRouterAndBts(Method method) throws Exception {
        //DATA CREATION
        String ringIdActiveProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        String ringIdActiveProject1 = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        String ringIdActiveProject2 = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActiveProject, "Midcell");
        Ring ringActive1 = new Ring("Active", ringIdActiveProject1, "Midcell");
        Ring ringActive2 = new Ring("Active", ringIdActiveProject2, "Midcell");
        Site siteActiveFinal = new Site(ringIdActiveProject, "Final Build", "Active Site");
        siteActiveFinal = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive, siteActiveFinal);
        Site siteActiveFinal1 = new Site(ringIdActiveProject1, "Final Build", "Active Site");
        siteActiveFinal1 = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive1, siteActiveFinal1);
        Site siteActiveFinal2 = new Site(ringIdActiveProject2, "Final Build", "Active Site");
        siteActiveFinal2 = siteHelper.createActiveRingAndPrimaryActiveSite(ringActive2, siteActiveFinal2);

        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        addNewSite.selectSiteCategoryHub();
        int response =   addNewSite.bbuBtsCount();
        siteTracker = addNewSite.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal1.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        addNewSite.selectSiteCategoryAggregateRouter();
        addNewSite.assignSiteWithHub1(siteActiveFinal.siteId);
        softAssert.assertTrue(addNewSite.hubClusterIdValidation(),"cluster id Field Is Populated");
        softAssert.assertTrue(addNewSite.allSiteIdAtHubIdValidation(siteActiveFinal.siteId,siteActiveFinal1.siteId),"All Site Id's At Hub Field Is Populated");
        siteTracker = addNewSite.goToSiteTracker();
        siteTracker.searchForValue(siteActiveFinal2.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        addNewSite.selectSiteCategoryBbuBts1();
        addNewSite.assignSiteWithHub1(siteActiveFinal.siteId);
        softAssert.assertTrue(addNewSite.finalBbuBtsCount()>response,"Bbu?Bts Site Connected To Hub");
        softAssert.assertTrue(addNewSite.hubClusterIdValidation(),"cluster id Field Is Populated");
        softAssert.assertTrue(addNewSite.rfSectorTabValidation(),"RfSector Tab Is Present");
        softAssert.assertTrue(addNewSite.allSiteIdAtHubIdValidation(siteActiveFinal.siteId,siteActiveFinal2.siteId),"All Site Id's At Hub Field Is Populated");
        siteTracker = addNewSite.goToSiteTracker();
        softAssert.closeAssert();
    }


}
