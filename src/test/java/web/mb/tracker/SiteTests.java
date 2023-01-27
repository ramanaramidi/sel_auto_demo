package web.mb.tracker;

import common.BaseTest;
import commons.constants.Constants;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Site;
import org.testng.annotations.Test;
import pages.web.Tracker.AddRingPage;
import pages.web.Tracker.site.AddSitePage;
import pages.web.Tracker.RingTrackerPage;
import pages.web.Tracker.site.SiteTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.project.ProjectHelper;
import rest.ring.RingHelper;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;
import java.lang.reflect.Method;

public class SiteTests extends BaseTest {
    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    RingTrackerPage ringTracker;
    SiteTrackerPage siteTracker;
    AddSitePage addNewSite;
    AddRingPage addNewRing;
    String ringCode = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
    String siteCodeCancel = "SAU" + MiscHelpers.getRandomString(5, true).toUpperCase();
    ProjectHelper projectHelper = new ProjectHelper();
    String siteCode = "AU" + MiscHelpers.getRandomString(6, true).toUpperCase();
    String siteCodeWith9Characters = ringCode+"AB";
    String commonRingId;
    Site site;
    SiteHelper siteHelper = new SiteHelper();
    RingHelper ringHelper = new RingHelper();


    public SiteTests()
    {
        if(envURL == null) {envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");}
        if(testSuite == null) {testSuite = 	"TestRunner.xml";}

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


    private void generateCommonData() {
        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringId, "Macro");
        site = new Site(ringId,"Candidate","New Site");
        site = siteHelper.createNewRingAndSite(ring,site);
    }

    @Test(groups = {"Integration"},description = "login",priority = 2)
    public void addNewSiteMoreThan8Characters_Site(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        addNewSite = siteTracker.selectAddNewSiteOption();
        siteTracker = addNewSite.addNewSiteWith9CharactersSiteCode(Constants.RING_CODE,siteCodeWith9Characters);
        siteTracker.searchForValue(siteCodeWith9Characters,"S:Site Code");
        softAssert.assertFalse(siteTracker.isDataPresentInTable(),"The Site should not have been created");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "login",priority = 2)
    public void createNewSiteTest_Site(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Site siteNew = new Site(site.ringId,"Candidate","New Site");
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteNew.ringId, "R:Ring Code");
        String ringCodeCount=siteTracker.isRingCount();
        System.out.println(ringCodeCount);
        addNewSite = siteTracker.selectAddNewSiteOption();
        siteTracker = addNewSite.addingNewSiteTracker(siteNew);
        siteTracker.searchForValue(siteNew.ringId, "R:Ring Code");
        softAssert.assertTrue(siteTracker.isDataPresentInTable(), "The site should have been created");
        softAssert.assertTrue(siteTracker.isSiteStatus("New Site")> 0, "Status should match");
        //softAssert.assertContains(siteTracker.getCellValue(siteTracker.tableHandle("S:Site Status")),"New Site", "Status should match");
        softAssert.assertContains(siteTracker.getCellValue(siteTracker.tableHandle("S:Build Status")),"Candidate", "Build Status should match");
        siteTracker.searchForValue(siteNew.ringId, "R:Ring Code");
        String ringCodeCountAfterAddingNewSite=siteTracker.isRingCount();
        System.out.println(ringCodeCountAfterAddingNewSite);
        softAssert.assertTrue(!ringCodeCountAfterAddingNewSite.equals(ringCodeCount),"Ring count should not match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "login",priority = 3)
    public void activeStateValidations_Site(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(site.siteId,"S:Site Code");
        softAssert.assertTrue(siteTracker.isDataPresentInTable(),"The site should have been created");
        if(siteTracker.isDataPresentInTable()){
            addNewSite = siteTracker.selectEditOption();
            siteTracker = addNewSite.withOutsetMandatoryDetails();
            if(siteTracker != null){
                siteTracker.searchForValue(site.siteId, "S:Site Code");
                softAssert.assertTrue(siteTracker.isDataPresentInTable(), "The site should have been created");
                softAssert.assertTrue(siteTracker.isSiteStatus("New Site")> 0, "Status should match");
                // softAssert.assertContains(siteTracker.getCellValue(siteTracker.tableHandle("S:Site Status")),"New Site", "Status should match");
                softAssert.assertContains(siteTracker.getCellValue(siteTracker.tableHandle("S:Build Status")),"Candidate", "Build Status should match");
            }
            else
                softAssert.assertTrue(false,"Looks Like something went wrong with alerts");
        }
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "login",priority = 3)
    public void spectrumCallValidations_Site(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Site siteNew = new Site(site.ringId,"Candidate","New Site");
        siteNew = siteHelper.createNewSite(siteNew);
        if(siteNew!=null){
            siteTracker = mainSideMenu.goToSiteTracker();
            siteTracker.searchForValue(siteNew.siteId,"S:Site Code");
            softAssert.assertTrue(siteTracker.isDataPresentInTable(),"The site should have been created");
            addNewSite = siteTracker.selectEditOption();
            softAssert.assertTrue(addNewSite.verifyDoNotUseSpectrumSpatialAPI(""),"should be null selected");
            softAssert.assertTrue(addNewSite.verifyGeoLocationDetailsIsPresentWithoutAlert(),"geo details should be present");
            softAssert.assertTrue(siteTracker.checkForPageLoad(),"ring tracker page loaded");
        }
        else
            softAssert.assertTrue(false,"SITE GENERATION IS AN ISSUE");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "siteCreationWithoutSpectrumSpacialCall",priority = 3)
    public void siteCreationWithoutSpectrumSpacialCall_Site(Method method) throws Exception {
        Site siteNew = new Site(site.ringId,"Candidate","New Site");
        siteNew = siteHelper.createNewSiteWithoutGeoLocation(siteNew);
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(siteNew.siteId,"S:Site Code");
        softAssert.assertTrue(siteTracker.isDataPresentInTable(),"The site should have been created");
        addNewSite = siteTracker.selectEditOption();
        addNewSite.setNoSpectrumCallTo("yes");
        addNewSite.addGeoLocation();
        softAssert.assertFalse(addNewSite.verifyGeoLocationDetailsIsPresentWithAlert(),"should not be present");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "login",priority = 4)
    public void moveSiteToActiveState_Site(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        System.out.println("SITE_CODE::"+ site.siteId);
        siteTracker.searchForValue(site.siteId, "S:Site Code");
        softAssert.assertTrue(siteTracker.isDataPresentInTable(), "The site should have been created");
        if(siteTracker.isDataPresentInTable()){
            addNewSite = siteTracker.selectEditOption();
            addNewSite.editSiteMandatoryDetails(site);
            siteTracker.searchSiteTracker(site.siteId, "S:Site Code");
            softAssert.assertTrue(siteTracker.isDataPresentInTable(), "The site should have been created");
            softAssert.assertTrue(siteTracker.isSiteStatus("Active")> 0, "Status should match");
            softAssert.assertContains(siteTracker.getCellValue(siteTracker.tableHandle("S:Site Status")),"Active", "Status should match");
            softAssert.assertContains(siteTracker.getCellValue(siteTracker.tableHandle("S:Build Status")),"Candidate", "Build Status should match");
        }
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "login",priority = 3)
    public void addNewSiteButCancel_Site(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        addNewSite = siteTracker.selectAddNewSiteOption();
        siteTracker = addNewSite.addingNewSiteAndCancel(site.ringId, siteCodeCancel);
        siteTracker.searchForValue(siteCodeCancel, "S:Site Code");
        softAssert.assertFalse(siteTracker.isDataPresentInTable(), "The Site should not have been created");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "login",priority = 3)
    public void addNewSiteWithoutRingCode_Site(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        addNewSite = siteTracker.selectAddNewSiteOption();
        addNewSite.addSiteWithoutRingCode(siteCodeCancel);
        siteTracker.searchForValue(siteCodeCancel, "S:Site Code");
        softAssert.assertFalse(siteTracker.isDataPresentInTable(), "The site should not have been created");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "login",priority = 2)
    public void scipPhotoUploadForSite_Site(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        //siteCode = Constants.SITE_CODE_SCIP;
        siteTracker.searchForValue(site.siteId, "S:Site Code");
        softAssert.assertTrue(siteTracker.isDataPresentInTable(), "The site should have been created");
        addNewSite = siteTracker.selectEditOption();
        int oldDocCount = addNewSite.uploadScipImage();
        System.out.println("old doc count ::"+oldDocCount);
        projectHelper.uploadDocument(site.trackerId.toString(),"S_SCIP_FORM_PHOTOS", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png");
        int newDocCount = addNewSite.getDocumentsCount();
        System.out.println("new doc count ::"+newDocCount);
        addNewSite.deleteAddedDocument();
        siteTracker = addNewSite.acceptAndGoToSiteTracker();
        softAssert.assertTrue(oldDocCount<newDocCount,"The doc is added");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "When the Ring Status is Changed to ON-Hold,Dead",priority = 4)
    public void RingStatusONHold_Site(Method method) throws Exception
    {
        AssertionsUtil softAssert = new AssertionsUtil();
        ringTracker = mainSideMenu.goToRingTracker();
        //Use Api to create ring and site
        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringId, "Macro");
        Site site = new Site(ringId,"Candidate","New Site");
        site = siteHelper.createNewRingAndSite(ring,site);
        System.out.println("data::::: "+site.siteId);
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        addNewRing = ringTracker.selectEditOption();
        ringTracker = addNewRing.updateRingStatus("On Hold");
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(ring.ringId, "R:Ring Code");
        softAssert.assertContains(siteTracker.getCellValue(siteTracker.tableHandle("S:Site Status")),"On Hold", "The Site should be in Inactive state");
        softAssert.closeAssert();
    }



    @Test(groups = {"Integration"},description = "When the Ring Status is Changed to ON-Hold,Dead",priority = 4)
    public void RingStatusDead_Site(Method method) throws Exception
    {
        AssertionsUtil softAssert = new AssertionsUtil();
        ringTracker = mainSideMenu.goToRingTracker();
        //Use Api to create ring and site
        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringId, "Macro");
        Site site = new Site(ringId,"Candidate","New Site");
        site = siteHelper.createNewRingAndSite(ring,site);
        System.out.println("data::::: "+site.siteId);
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        addNewRing = ringTracker.selectEditOption();
        ringTracker = addNewRing.updateRingStatus("Dead");
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(ring.ringId, "R:Ring Code");
        softAssert.assertContains(siteTracker.getCellValue(siteTracker.tableHandle("S:Site Status")),"Dead", "The Site should be in Inactive state");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "When the Ring Status is Changed to ON-Hold,Dead",priority = 4)
    public void SiteStatusInactiveButRingStatusDead_Site(Method method) throws Exception
    {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteTracker = mainSideMenu.goToSiteTracker();
        //Use Api to create ring and site
        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringId, "Macro");
        Site site = new Site(ringId,"Candidate","New Site");
        site = siteHelper.createNewRingAndSite(ring,site);
        System.out.println("data::::: "+site.siteId);
        siteTracker.searchForValue(site.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        siteTracker=addNewSite.updateSiteStatus("Inactive");
        ringTracker = mainSideMenu.goToRingTracker();
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        addNewRing = ringTracker.selectEditOption();
        ringTracker = addNewRing.updateRingStatus("Dead");
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(ring.ringId, "R:Ring Code");
        softAssert.assertContains(siteTracker.getCellValue(siteTracker.tableHandle("S:Site Status")),"Inactive", "The Site should be in Inactive state");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "When the Ring Status is Changed to ON-Hold,Dead",priority = 4)
    public void SiteStatusInactiveButRingStatusOnHold_Site(Method method) throws Exception
    {
        AssertionsUtil softAssert = new AssertionsUtil();



        siteTracker = mainSideMenu.goToSiteTracker();
        //Use Api to create ring and site
        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringId, "Macro");
        Site site = new Site(ringId,"Candidate","New Site");
        site = siteHelper.createNewRingAndSite(ring,site);
        System.out.println("data::::: "+site.siteId);
        siteTracker.searchForValue(site.siteId, "S:Site Code");
        addNewSite = siteTracker.selectEditOption();
        siteTracker=addNewSite.updateSiteStatus("Inactive");
        ringTracker = mainSideMenu.goToRingTracker();
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        addNewRing = ringTracker.selectEditOption();
        ringTracker = addNewRing.updateRingStatus("On Hold");
        siteTracker = mainSideMenu.goToSiteTracker();
        siteTracker.searchForValue(ring.ringId, "R:Ring Code");
        softAssert.assertContains(siteTracker.getCellValue(siteTracker.tableHandle("S:Site Status")),"Inactive", "The Site should be in Inactive state");
        softAssert.closeAssert();
    }


}
