package web.mb.tracker.sector;

import common.BaseTest;
import commons.constants.Constants;
import commons.enums.LoginOptionEnum;
import commons.objects.*;
import org.testng.annotations.Test;
import pages.web.Tracker.RFSectorPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.misc.MiscHelper;
import rest.project.ProjectHelper;
import rest.sector.SectorHelper;
import rest.site.SiteHelper;
import testData.UserData;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class StatusFlows extends BaseTest {

    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    public Users alphaUser = new Users();
    public Users rfEngineer = new Users();
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    RFSectorPage rfSectorPage;

    Site siteForSector;
    SectorHelper sectorHelper = new SectorHelper();
    SiteHelper siteHelper = new SiteHelper();
    MiscHelper miscHelper = new MiscHelper();
    String RFSectorID1;
    Sector sector5G;
    Sector sector5G13;
    Sector sectorLTE;
    ProjectHelper projectHelper = new ProjectHelper();
    Sector sectorNBIOT;
    Sector sectorGSM;
    Sector sectorUMTS;

    public StatusFlows()
    {
        alphaUser = UserData.getAlphaUserDetails(alphaUser);
        rfEngineer = UserData.getRfEngUserDetails(rfEngineer);
        if(envURL == null) {envURL = 	"https://magentabuiltstg.t-mobile.com/Login.do";}
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
        generateData();
        mainSideMenu = loginPage.LoginAsUser(rfEngineer);
    }

    private void generateData(){
        String ringIdForSector = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActiveForSector = new Ring("Active", ringIdForSector, "Indoor Node");
        siteForSector = new Site(ringIdForSector,"Primary","Active Site");
        siteForSector = siteHelper.createActiveRingAndSite(ringActiveForSector,siteForSector);
        RFSectorID1 = siteForSector.siteId+"_21LPW";
        sector5G = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_12NEB"));
        sectorLTE = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_13LAA"));
        sector5G13 = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_13NEB"));
//        sectorNBIOT = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_23TEA"));
//        sectorGSM = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_13GDC"));
//        sectorUMTS = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_11UAD"));
    }

    private void generateSectorCsvData(Sector sector, String status) throws IOException {
        List<List<String>> records = Arrays.asList(
                Arrays.asList("SITE_ID", "SECTOR_ID", "SEC_SECTOR_STATUS"),
                Arrays.asList(sector.siteId, sector.sectorId, status)
        );
        Path path = Paths.get(Constants.SECTOR_IMPORT_DATA);
        Files.deleteIfExists(path);
        BufferedWriter writer = Files.newBufferedWriter(path);
        for (List<String> record : records) {
            writer.write(String.join(",", record));
            writer.newLine();
        }
        writer.close();
    }

    private void generateSectorUpdateStatusCsvData(Sector sector, String status) throws IOException {
        List<List<String>> records = Arrays.asList(
                Arrays.asList("SITE_ID", "SECTOR_ID", "SEC_SECTOR_STATUS","SEC_OFF_AIR_DATE"),
                Arrays.asList(sector.siteId, sector.sectorId, status,MiscHelpers.currentDateTime("MM/dd/yyyy"))
        );
        Path path = Paths.get(Constants.SECTOR_IMPORT_DATA);
        Files.deleteIfExists(path);
        BufferedWriter writer = Files.newBufferedWriter(path);
        for (List<String> record : records) {
            writer.write(String.join(",", record));
            writer.newLine();
        }
        writer.close();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 2)
    public void create5GSectorWithOutMainDetailsAndMoveToProvision() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Provision"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G.sectorId),sector5G.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G.sectorId),"New", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G.sectorId),"12NEB", "sector Code should match");
       // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 3)
    public void create5GSectorAndMoveToOnAirFromNew() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("On-Air"),"User should not be able to update");
        //softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G.sectorId),sector5G.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G.sectorId),"New", "sector Status should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G.sectorId),"12NEB", "sector Code should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 3)
    public void create5GSectorAndMoveToOffAirFromNew() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Off-Air"),"User should not be able to update");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G.sectorId),sector5G.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G.sectorId),"New", "sector Status should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G.sectorId),"12NEB", "sector Code should match");
        //softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 3)
    public void create5GSectorAndMoveToInactiveFromNew() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Inactive"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G.sectorId),sector5G.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G.sectorId),"New", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G.sectorId),"12NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 3)
    public void create5GSectorAndMoveToOffAirInProgressFromNew() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Off-Air in Progress"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G.sectorId),sector5G.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G.sectorId),"New", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G.sectorId),"12NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 4)
    public void create5GSectorAndMoveToErrorFromNew() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertTrue(rfSectorPage.setSectorStatus("Error"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G.sectorId),sector5G.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G.sectorId),"Error", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G.sectorId),"12NEB", "sector Code should match");
        //softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 5)
    public void create5GSectorAndMoveToNewFromError() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("New"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G.sectorId),sector5G.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G.sectorId),"Error", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G.sectorId),"12NEB", "sector Code should match");
        //softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 5)
    public void create5GSectorAndMoveToProvisionFromError() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Provision"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G.sectorId),sector5G.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G.sectorId),"Error", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G.sectorId),"12NEB", "sector Code should match");
        //softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 5)
    public void create5GSectorAndMoveToOnAirFromError() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("On-Air"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G.sectorId),sector5G.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G.sectorId),"Error", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G.sectorId),"12NEB", "sector Code should match");
        //softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 5)
    public void create5GSectorAndMoveToOffAirProgressFromError() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Off-Air in Progress"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G.sectorId),sector5G.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G.sectorId),"Error", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G.sectorId),"12NEB", "sector Code should match");
        //softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 5)
    public void create5GSectorAndMoveToOffAirFromError() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Off-Air"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G.sectorId),sector5G.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G.sectorId),"Error", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G.sectorId),"12NEB", "sector Code should match");
        //softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 5)
    public void create5GSectorAndMoveToInactiveFromError() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Inactive"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G.sectorId),sector5G.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G.sectorId),"Error", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G.sectorId),"12NEB", "sector Code should match");
        //softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 6)
    public void create5GSectorWithMainDetailsAndMoveToProvision() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        sector5G13.plannedCellId = "123";
        sector5G13.coverageType = "Indoor";
        sector5G13.plannedVendor = "Commscope";
        sector5G13.plannedSwitch = "SAN FRANCISCO_WSCRCAKE";
        sector5G13.plannedMME = "SAN FRANCISCO_WSCRCAKE_West Pool 2";
        sector5G13.plannedLacTac = "LAC000000085";
        sectorHelper.updateSectorWithPlannedValues(sector5G13);
        sectorHelper.createNodeAndAssignToSector(sector5G13);
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertTrue(rfSectorPage.setSectorStatus("Provision"),"User should not be able to update");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Provision", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 7)
    public void create5GSectorAndMoveToNewFromProvision() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("New"),"User should not be able to update");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Provision", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 7)
    public void create5GSectorAndMoveToOffAirInProgressFromProvision() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Off-Air in Progress"),"User should not be able to update");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Provision", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 7)
    public void create5GSectorAndMoveToOffAirFromProvision() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Off-Air"),"User should not be able to update");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Provision", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 7)
    public void create5GSectorAndMoveToInactiveFromProvision() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Inactive"),"User should not be able to update");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Provision", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 8)
    public void create5GSectorAndMoveToOnAirFromProvision() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertTrue(rfSectorPage.setSectorStatus("On-Air"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"On-Air", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 9)
    public void create5GSectorAndMoveToNewFromOnAir() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("New"),"User should not be able to update");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"On-Air", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 9)
    public void create5GSectorAndMoveToErrorFromOnAir() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Error"),"User should not be able to update");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"On-Air", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 9)
    public void create5GSectorAndMoveToProvisionFromOnAir() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Provision"),"User should not be able to update");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"On-Air", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 9)
    public void create5GSectorAndMoveToOffAirFromOnAir() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Off-Air"),"User should not be able to update");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"On-Air", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 10)
    public void create5GSectorAndMoveToInactiveFromOnAir() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertTrue(rfSectorPage.setSectorStatus("Inactive"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Inactive", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 11)
    public void create5GSectorAndMoveToNewFromInactive() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("New"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Inactive", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 11)
    public void create5GSectorAndMoveToProvisionFromInactive() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Provision"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Inactive", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 11)
    public void create5GSectorAndMoveToOffAirInProgressFromInactive() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Off-Air in Progress"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Inactive", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 11)
    public void create5GSectorAndMoveToOffAirFromInactive() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Off-Air"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Inactive", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 11)
    public void create5GSectorAndMoveToErrorFromInactive() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Error"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Inactive", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 12)
    public void create5GSectorAndMoveToOnAirFromInactive() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertTrue(rfSectorPage.setSectorStatus("On-Air"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"On-Air", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 13)
    public void create5GSectorAndMoveToOffAirInProgressFromOnAir() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertTrue(rfSectorPage.setSectorStatus("Off-Air in Progress"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Off-Air in Progress", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 14)
    public void create5GSectorAndMoveToNewFromOffAirInProgress() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("New"),"User should not be able to update");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Off-Air in Progress", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 14)
    public void create5GSectorAndMoveToProvisionFromOffAirInProgress() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Provision"),"User should not be able to update");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Off-Air in Progress", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 14)
    public void create5GSectorAndMoveToInactiveFromOffAirInProgress() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Inactive"),"User should not be able to update");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Off-Air in Progress", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 14)
    public void create5GSectorAndMoveToErrorFromOffAirInProgress() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Error"),"User should not be able to update");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Off-Air in Progress", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 15)
    public void create5GSectorAndMoveToOffAirFromOffAirInProgress() throws Exception {
        generateSectorCsvData(sector5G13,"Off-Air");
        ImportFile importFile = new ImportFile("10017339","sector_imports.csv");
        importFile = miscHelper.importFileAndGetStatus(importFile);
        AssertionsUtil softAssert = new AssertionsUtil();
        if(importFile!=null){
            rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
            rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
            rfSectorPage.editRFSector();
           // softAssert.assertTrue(rfSectorPage.setSectorStatus("Off-Air in Progress"),"User should not be able to update");

            softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
            softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
            softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Off-Air", "sector Status should match");

            softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
            // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
            softAssert.closeAssert();
        }
        else
            softAssert.assertTrue(false,"Looks like something went wrong in file import") ;
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 16)
    public void create5GSectorAndMoveToNewFromOffAir() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("New"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Off-Air", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 16)
    public void create5GSectorAndMoveToProvisionFromOffAir() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Provision"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Off-Air", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 16)
    public void create5GSectorAndMoveToOnAirFromOffAir() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("On-Air"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Off-Air", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 16)
    public void create5GSectorAndMoveToOffAirInProgressFromOffAir() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Off-Air in Progress"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Off-Air", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 16)
    public void create5GSectorAndMoveToInactiveFromOffAir() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Inactive"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Off-Air", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 16)
    public void create5GSectorAndMoveToErrorFromOffAir() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G13.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("Error"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sector5G13.sectorId),sector5G13.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sector5G13.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sector5G13.sectorId),"Off-Air", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sector5G13.sectorId),"13NEB", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 17)
    public void StatusUpdateWithAdminUpdateImport() throws Exception {
        generateSectorCsvData(sectorLTE,"Off-Air in Progress");
        ImportFile importFile = new ImportFile("10017339","sector_imports.csv");
        importFile = miscHelper.importFileAndGetStatus(importFile);
        AssertionsUtil softAssert = new AssertionsUtil();
        if(importFile!=null){
            rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
            rfSectorPage.searchForValue(sectorLTE.sectorId,"SEC:Sector ID");
            rfSectorPage.editRFSector();
            // softAssert.assertTrue(rfSectorPage.setSectorStatus("Off-Air in Progress"),"User should not be able to update");

            softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sectorLTE.sectorId),sectorLTE.sectorId, "sectorId should match");
            softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sectorLTE.sectorId),"Undefined", "sector Type should match");
            softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sectorLTE.sectorId),"Off-Air in Progress", "sector Status should match");

           // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sectorLTE.sectorId),"13LAA", "sector Code should match");
            // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
            softAssert.closeAssert();
        }
        else
            softAssert.assertTrue(false,"Looks like something went wrong in file import") ;
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 18)
    public void create5GSectorAndMoveToOnAirFromOffAirInProgress() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sectorLTE.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertTrue(rfSectorPage.setSectorStatus("On-Air"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sectorLTE.sectorId),sectorLTE.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sectorLTE.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sectorLTE.sectorId),"On-Air", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sectorLTE.sectorId),"13LAA", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 19)
    public void moveLTESectorToOffAirInProgressFromOnAirWithDate() throws Exception {
        generateSectorUpdateStatusCsvData(sectorLTE,"Off-Air in Progress");
        ImportFile importFile = new ImportFile("10017339","sector_imports.csv");
        importFile = miscHelper.importFileAndGetStatus(importFile);
        AssertionsUtil softAssert = new AssertionsUtil();
        if(importFile!=null){
            rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
            rfSectorPage.searchForValue(sectorLTE.sectorId,"SEC:Sector ID");
            rfSectorPage.editRFSector();
            // softAssert.assertTrue(rfSectorPage.setSectorStatus("Off-Air in Progress"),"User should not be able to update");

            softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sectorLTE.sectorId),sectorLTE.sectorId, "sectorId should match");
            softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sectorLTE.sectorId),"Undefined", "sector Type should match");
            softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sectorLTE.sectorId),"Off-Air in Progress", "sector Status should match");

            softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sectorLTE.sectorId),"13LAA", "sector Code should match");
            // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
            softAssert.closeAssert();
        }
        else
            softAssert.assertTrue(false,"Looks like something went wrong in file import") ;
    }

   // @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 20)
    public void MoveLTESectorToOnAirFromOffAirInProgressWithDate() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sectorLTE.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertFalse(rfSectorPage.setSectorStatus("On-Air"),"User should not be able to update");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector ID","SEC:Sector ID",sectorLTE.sectorId),sectorLTE.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",sectorLTE.sectorId),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Status","SEC:Sector ID",sectorLTE.sectorId),"Off-Air in Progress", "sector Status should match");

        softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Sector Code","SEC:Sector ID",sectorLTE.sectorId),"13LAA", "sector Code should match");
        // softAssert.assertContains(rfSectorPage.searchForValueInGrid("SEC:Technology","SEC:Sector ID",sector5G.sectorId),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

}
