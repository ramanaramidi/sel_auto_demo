package web.mb.tracker.sector;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.*;
import org.testng.annotations.Test;
import pages.web.Tracker.RFSectorPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.project.ProjectHelper;
import rest.sector.SectorHelper;
import rest.site.SiteHelper;
import testData.UserData;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class RFSectorTests2 extends BaseTest {
    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    SectorHelper sectorHelper = new SectorHelper();
    SiteHelper siteHelper = new SiteHelper();
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    RFSectorPage rfSectorPage;
    String RFSectorID1;
    Site siteForSector;
    Sector sector5G;
    Project projectForSector;
    Sector sectorLTE;
    Sector sectorNBIOT;
    Sector sectorGSM;
    Sector sectorUMTS;
    Sector sectorIMS;
    Sector sectorWiFi;
    ProjectHelper projectHelper = new ProjectHelper();
    public Users rfEngineer = new Users();
   public Users sitedev = new Users();

    public RFSectorTests2()
    {
        if(envURL == null) {envURL = 	"https://magentabuiltstg.t-mobile.com/Login.do";}
        if(testSuite == null) {testSuite = "sectorSet.xml";}
        rfEngineer = UserData.getRfEngUserDetails(rfEngineer);
        sitedev = UserData.getSite_DevUserDetails(sitedev);
    }

    @Test(groups = {"Integration"},description = "login",priority = 1)
    public void login_RfSectorSet2(Method method) throws Exception {
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
        mainSideMenu = loginPage.LoginAsUser(rfEngineer);

    }

    private void generateData(){
        String ringIdForSector = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActiveForSector = new Ring("Active", ringIdForSector, "Indoor Node");
        siteForSector = new Site(ringIdForSector,"Primary","Active Site");
        siteForSector = siteHelper.createActiveRingAndSite(ringActiveForSector,siteForSector);
       // projectForSector = projectHelper.getActiveProject(false,ringActiveForSector,siteForSector);
        RFSectorID1 = siteForSector.siteId+"_21LPW";
        sector5G = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_12NCB"));
        sectorLTE = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_13LAA"));
        sectorNBIOT = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_23TFA"));
        sectorGSM = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_13GDC"));
        sectorUMTS = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_11UED"));
        sectorIMS = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_13IAA"));
        sectorWiFi = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_12WCB"));
    }
    @Test(groups = {"Integration"},description = "Sector Record Update By Rf Eng User",priority = 2)
    public void addTheSectorRecordsAsRfeng(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        softAssert.assertTrue(rfSectorPage.addVerificationForRfEng(), "add option should not present");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Sector Record Delete By Rf Eng User",priority = 3)
    public void deleteTheSectorRecordsAsRfeng(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        softAssert.assertTrue(rfSectorPage.deleteOptionVerification(),"delete option should not present");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Planned Value Verification For Gsm Technology",priority = 4)

    public void plannedValueFieldsVerificationForGsmTechnology(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sectorGSM.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        softAssert.assertTrue(rfSectorPage.nccPlannedVerification(),"Ncc Planned Field Is Unlocked And Able To Edit");
        softAssert.assertTrue(rfSectorPage.bccPlannedVerification(),"Bcc Planned Field Is Unlocked And Able To Edit");
        softAssert.assertTrue(rfSectorPage.bcchPlannedVerification(),"Bcch Planned Field Is Unlocked And Able To Edit");
        softAssert.assertTrue(rfSectorPage.btsPlannedVerification(),"Bts Planned Field Is Unlocked And Able To Edit");
        softAssert.assertTrue(rfSectorPage.radiosPlannedVerification(),"Radios Planned Field Is Unlocked And Able To Edit");
        softAssert.assertTrue(rfSectorPage.frequencyHoppingVerification(),"Frequency Hopping Field Is Unlocked And Able To Edit");
        softAssert.assertTrue(rfSectorPage.rbltPlannedTacVerification(),"Rblt Planned Field Is Unlocked And Able To Edit");
        softAssert.assertTrue(rfSectorPage.bscPlannedTacVerification(),"Bcs Planned Field Is Unlocked And Able To Edit");
        softAssert.assertTrue(rfSectorPage.racPlannedTacVerification(),"Rac Planned Field Is Unlocked And Able To Edit");
        rfSectorPage.goToRfSector();
        softAssert.closeAssert();
        mainSideMenu.userLogoff();
        mainSideMenu.userLogin();
    }

    @Test(groups = {"Integration"},description = "Sector Record Update By Site Dev User",priority =5 )
    public void updateTheSectorRecordsAsSiteDev(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        mainSideMenu = loginPage.LoginAsUser(sitedev);
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsSiteDev();
        rfSectorPage.selectEditOption();
        softAssert.assertFalse(!(rfSectorPage.siteCodeFieldVerification()), "Site Code Field is Disable");
        softAssert.assertFalse(!(rfSectorPage.sectorIdFieldVerification()), "Sector Id Field is Disable");
        softAssert.assertTrue(rfSectorPage. sectorTypeVerification(), "Sector Type Field is Disable");
        softAssert.assertTrue(rfSectorPage.sectorStatusVerification(), "Sector Status Field is Disable");
        softAssert.assertFalse(!(rfSectorPage.cellNamePlannedVerification()), "Cell Name Planned Field is Disable");
        softAssert.assertFalse(!(rfSectorPage.cellIdPlannedVerification()), "Cell Id Field is Disable");
        softAssert.assertTrue(rfSectorPage.coverageTypePlannedVerification(), "Coverage Type Field is Disable");
        softAssert.assertTrue(rfSectorPage.vendorPlannedVerification(), "Vendor PlannedField is Disable");
        softAssert.assertFalse(!(rfSectorPage.switchPlannedTacVerification()), "Switch Planned Field is Disable");
        softAssert.assertFalse(!(rfSectorPage.mscPlannedVerification()), "Msc Planned Filed is Disable");
        softAssert.assertFalse(!(rfSectorPage.lacTacPlannedVerification()), "LacTac Field is Disable");
        rfSectorPage.goToRfSectorAsSiteDev();
        softAssert.closeAssert();
        mainSideMenu.userLogoff();
        mainSideMenu.userLogin();
    }

}
