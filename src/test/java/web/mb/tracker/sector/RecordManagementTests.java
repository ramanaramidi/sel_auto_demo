package web.mb.tracker.sector;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Sector;
import commons.objects.Site;
import org.testng.annotations.Test;
import pages.web.Tracker.MSMTrackerPage;
import pages.web.Tracker.RFSectorPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.misc.MiscHelper;
import rest.project.ProjectHelper;
import rest.sector.SectorHelper;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class RecordManagementTests extends BaseTest {

    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    RFSectorPage rfSectorPage;

    ProjectHelper projectHelper = new ProjectHelper();
    Site siteForSector;
    SectorHelper sectorHelper = new SectorHelper();
    SiteHelper siteHelper = new SiteHelper();
    String RFSectorID1;
    Sector sector5G;
    Sector sector5G1;
    Sector sectorLTE;
    Sector sectorNBIOT;
    Sector sectorGSM;
    Sector sectorUMTS;
    MiscHelper miscHelper = new MiscHelper();
    MSMTrackerPage MSMtrackerPage;

    public RecordManagementTests()
    {
        if(envURL == null) {envURL = 	"https://magentabuiltstg.t-mobile.com/Login.do";}
        if(testSuite == null) {testSuite = "sectorSet.xml";}
       // rfEngineer = UserData.getRfEngUserDetails(rfEngineer);
    }

    @Test(groups = {"Integration"},description = "login",priority = 1)
    public void login_RfSectorSet1(Method method) throws Exception {
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
    private void generateData() {
        String ringIdForSector = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActiveForSector = new Ring("Active", ringIdForSector, "Indoor Micro");
        siteForSector = new Site(ringIdForSector, "Primary", "Active Site");
        siteForSector = siteHelper.createActiveRingAndSite(ringActiveForSector, siteForSector);
        sector5G = sectorHelper.createNewSector(new Sector(siteForSector.siteId, siteForSector.siteId + "_12NEB"));
        sector5G1 = sectorHelper.createNewSector(new Sector(siteForSector.siteId, siteForSector.siteId + "_21NEB"));
        sectorLTE = sectorHelper.createNewSector(new Sector(siteForSector.siteId, siteForSector.siteId + "_13LAA"));
        sectorNBIOT = sectorHelper.createNewSector(new Sector(siteForSector.siteId, siteForSector.siteId + "_23TEA"));
        sectorGSM = sectorHelper.createNewSector(new Sector(siteForSector.siteId, siteForSector.siteId + "_13GDC"));
        sectorUMTS = sectorHelper.createNewSector(new Sector(siteForSector.siteId, siteForSector.siteId + "_11UAD"));
        RFSectorID1 = siteForSector.siteId+"_21LPW";
    }

    @Test(groups = {"Integration"},description = "Add Bnc And Rnc As Business Alpha",priority = 2)
    public void addBncRncAsBusinessAlpha(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
     //   mainSideMenu = loginPage.LoginAsUser(superUser);
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(sectorUMTS.sectorId, "SEC:Sector ID");
        rfSectorPage.selectEditOption();
        rfSectorPage.setSector(sectorUMTS, "SAN FRANCISCO_TESTSANF_TESTSMGW", "LAC000020408");
        sectorHelper.updateSectorWithPlannedValues(sectorUMTS);
        rfSectorPage.addBsc();
        softAssert.assertTrue(rfSectorPage.msmIdValidation(),"Msm Id Generated");
        softAssert.assertTrue(rfSectorPage.bscVerification(),"Msm Id Contains Bsc/RNc Value");
        rfSectorPage.goToMsmIdTablePage();
        String response = rfSectorPage.searchForValueInGridExact("MSM:BSC/RNC","MSM:BSC/RNC",rfSectorPage.RNC);
        System.out.println (response);
        softAssert.assertContains(response,rfSectorPage.RNC,"Bsc Value Created In record");
        softAssert.closeAssert();
        rfSectorPage.backToRfSectorPageAsAlphaUser();

    }

    @Test(groups = {"Integration"},description = "Sector ID Rule",priority = 3)
    public void validateSiteID_Match(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTracker();
        rfSectorPage=rfSectorPage.addNewRFSector();
        String siteId = "SAUF0AW5";
        String parentWindow = rfSectorPage.switchToProjectPage();
        rfSectorPage.validateAddNewRFSector(RFSectorID1,siteId);
        softAssert.assertTrue(rfSectorPage.getSiteId(RFSectorID1,siteForSector.siteId),"Sector ID First Part should be the SiteID");
        rfSectorPage.switchToTrackerOnException(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "createMSMRecord",priority = 4)
    public void createMSMRecord(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        MSMtrackerPage = mainSideMenu.goToMSMTracker();
        MSMtrackerPage = MSMtrackerPage.addNewMSMPage();
        String parentWindow = MSMtrackerPage.switchToProjectPage();
        MSMtrackerPage.createMSMRecord();
        softAssert.assertTrue(MSMtrackerPage.getMSMIdConcatMarket_Switch(),"MSM ID should be concatenated with the Market_Switch ");
        MSMtrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "createMSM_MSC_MGW_MME_AMFRecord",priority = 5)
    public void createMSM_MSC_MGW_MME_AMFRecord(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        MSMtrackerPage= mainSideMenu.goToMSMTracker();
        MSMtrackerPage = MSMtrackerPage.addNewMSMPage();
        String parentWindow = MSMtrackerPage.switchToProjectPage();
        MSMtrackerPage.createMSM_MSC_MGW_MME_AMFRecord();
        softAssert.assertTrue(MSMtrackerPage.getMSMIdConcatMSMMSCMMEMGWAMF(),"MSM ID should be concatenated with the MSM/MSC/MGW/MME/AMF ");
        MSMtrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "createMSM_MSC_MGW_MME_AMFRecord",priority = 6)
    public void createBSC_RNCRecord(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        MSMtrackerPage= mainSideMenu.goToMSMTracker();
        MSMtrackerPage = MSMtrackerPage.addNewMSMPage();
        String parentWindow = MSMtrackerPage.switchToProjectPage();
        MSMtrackerPage.createBSC_RNC_Record();
        softAssert.assertTrue(MSMtrackerPage.getMSMIdConcatBSC_RNC(),"MSM ID should be concatenated with the Market_Switch_MSM/MSC/MGW/MME/AMF_BSC/RNC");
        MSMtrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "createLAC_TACRecord",priority = 7)
    public void createLAC_TACRecord(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        MSMtrackerPage= mainSideMenu.goToMSMTracker();
        MSMtrackerPage = MSMtrackerPage.addNewMSMPage();
        String parentWindow = MSMtrackerPage.switchToProjectPage();
        String Lacid = MSMtrackerPage.createLAC_TAC_Record();
        softAssert.assertTrue(MSMtrackerPage.getLAC_TACRecord(Lacid,"LAC:LAC ID"),"LAC/TAC Record is created Successfully");
        MSMtrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "createRACRecord",priority = 8)
    public void createRACRecord(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        MSMtrackerPage= mainSideMenu.goToMSMTracker();
        MSMtrackerPage = MSMtrackerPage.addNewMSMPage();
        String parentWindow = MSMtrackerPage.switchToProjectPage();
        String Racid = MSMtrackerPage.createRACRecord();
        softAssert.assertTrue(MSMtrackerPage.getRACRecord(Racid,"RAC:RAC ID"),"RAC Record should be created");
        MSMtrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
}
