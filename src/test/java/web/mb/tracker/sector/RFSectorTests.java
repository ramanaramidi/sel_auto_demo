package web.mb.tracker.sector;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Sector;
import commons.objects.Site;
import org.testng.annotations.Test;
import pages.web.Tracker.*;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.sector.SectorHelper;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class RFSectorTests extends BaseTest {


    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    RFSectorPage rfSectorPage;

    Site siteForSector;
    SectorHelper sectorHelper = new SectorHelper();
    SiteHelper siteHelper = new SiteHelper();
    String RFSectorID1;
    String RFSectorID2;
    Sector sector5G;
    Sector sectorLTE;
    Sector sectorNBIOT;
    Sector sectorGSM;
    Sector sectorUMTS;
    String RFSectorIDLessthan14Characters = "AU00KR1A_35LA";

    public RFSectorTests()
    {
        if(envURL == null) {envURL = 	"https://magentabuiltstg.t-mobile.com/Login.do";}
        if(testSuite == null) {testSuite = "sectorSet.xml";}

    }


    @Test(groups = {"Integration"},description = "login",priority = 1)
    public void login_RfSectorSet(Method method) throws Exception {
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
        RFSectorID1 = siteForSector.siteId+"_21LPW";
        RFSectorID2 = siteForSector.siteId+"_22LPW";
        sector5G = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_12NEB"));
        sectorLTE = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_13LAA"));
        sectorNBIOT = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_23TEA"));
        sectorGSM = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_13GDC"));
        sectorUMTS = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_11UAD"));
    }

   @Test(groups = {"Integration"},description = "Sector Creation",priority = 2)
    public void SectorCreation() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTracker();
        rfSectorPage=rfSectorPage.addNewRFSector();
        rfSectorPage.verifyAddNewRFSector(RFSectorID1,siteForSector.siteId);
        rfSectorPage.searchForValue(RFSectorID1,"SEC:Sector ID");
        String  response = rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector ID"));
        System.out.println (response);
        softAssert.assertNotNull(response,"RF Sector created" );
        softAssert.closeAssert();
    }


    @Test(groups = {"Integration"},description = "Verify Sector Id Limitation",priority = 3)
    public void SectorIdLimitation() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTracker();
        rfSectorPage=rfSectorPage.addNewRFSector();
        softAssert.assertTrue(rfSectorPage.AddNewRFSectorLessThan14characters(RFSectorIDLessthan14Characters,siteForSector.siteId),"user should get a warning pop up");
        softAssert.closeAssert();
    }


     @Test(groups = {"Integration"},description = "Mandatory fields alert",priority = 4)
    public void MandatoryFieldsAlert() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTracker();
        rfSectorPage=rfSectorPage.addNewRFSector();
        softAssert.assertTrue(rfSectorPage.MandatoryFieldsAlert("RFSectorID1"),"User should get mandatory field alert");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 5)
    public void alertMessageOnCancelWithNoData() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTracker();
        rfSectorPage=rfSectorPage.addNewRFSector();
        softAssert.assertTrue(rfSectorPage.newSectorfromGUI(),"No Alert message upon cancel when no entry is made");
        softAssert.closeAssert();
    }


     @Test(groups = {"Integration"},description = "Sector Type - Small Cell",priority = 6)
    public void SectorTypeAsSmallCell() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTracker();
        rfSectorPage=rfSectorPage.addNewRFSector();
        rfSectorPage.verifyAddNewRFSector(RFSectorID2,siteForSector.siteId);
        rfSectorPage.searchForValue(RFSectorID2,"SEC:Sector ID");
        rfSectorPage=rfSectorPage.editRFSector();
        rfSectorPage.changeSectorTypeToSmallCell();
       // String  response = rfSectorPage.searchForValueInGrid("SEC:Sector Type","SEC:Sector ID",RFSectorID1);
         rfSectorPage.searchForValue(RFSectorID2,"SEC:Sector ID");
         softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector Type")),"Small Cell", "Small Cell should be set");
       // softAssert.assertContains(response,siteForSector.siteId,"RF Sector created" );
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 5)
    public void create5GSectorWithMainDetails() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(sector5G.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        rfSectorPage.addMainDetails(sector5G);
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector ID")),sector5G.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector Type")),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector Status")),"New", "sector Status should match");

        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector Code")),"12NEB", "sector Code should match");
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Technology")),"5G", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 7)
    public void createLTESectorWithMainDetails() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTracker();

        rfSectorPage.searchForValue(sectorLTE.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        rfSectorPage.addMainDetails(sectorLTE);
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector ID")),sectorLTE.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector Type")),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector Status")),"New", "sector Status should match");

        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector Code")),"13LAA", "sector Code should match");
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Technology")),"LTE", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 8)
    public void createNBIOTSectorWithMainDetails() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(sectorNBIOT.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        rfSectorPage.addMainDetails(sectorNBIOT);
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector ID")),sectorNBIOT.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector Type")),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector Status")),"New", "sector Status should match");

        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector Code")),"23TEA", "sector Code should match");
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Technology")),"NBIOT", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 9)
    public void createGSMSectorWithMainDetails() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTracker();
        sectorGSM.plannedSwitch="SNFCCAMM";
        rfSectorPage.searchForValue(sectorGSM.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        rfSectorPage.addMainDetails(sectorGSM);

        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector ID")),sectorGSM.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector Type")),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector Status")),"New", "sector Status should match");

        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector Code")),"13GDC", "sector Code should match");
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Technology")),"GSM", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Sector from GUI",priority = 10)
    public void createUMTSSectorWithMainDetails() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage= mainSideMenu.goToRFSectorTracker();
        sectorUMTS.plannedSwitch="SNFCCAMM";
        rfSectorPage.searchForValue(sectorUMTS.sectorId,"SEC:Sector ID");
        rfSectorPage.editRFSector();
        rfSectorPage.addMainDetails(sectorUMTS);

        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector ID")),sectorUMTS.sectorId, "sectorId should match");
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector Type")),"Undefined", "sector Type should match");
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector Status")),"New", "sector Status should match");

        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Sector Code")),"11UAD", "sector Code should match");
        softAssert.assertContains(rfSectorPage.getCellValue(rfSectorPage.tableHandle("SEC:Technology")),"UMTS", "sector Technology should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify 5G technology",priority = 11)
    public void Verify_5G_TechnologyField(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        String SectorID = sector5G.sectorId;
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(SectorID,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For 5G gNode field should be enabled");
        softAssert.assertTrue(rfSectorPage.getCellNamePlanned("5G",sector5G),"Based on the Technology field cellName Planned field value is displayed ");
        softAssert.assertTrue(rfSectorPage.getCGI_Name(sector5G),"CGIName-Planned field data is concatination of fields MCC,MNC,(eNodeBID8256),CellID");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

   @Test(groups = {"Integration"},description = "verify 5G technology",priority = 12)
    public void Verify_5G_TechnologyField_NF(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_12NFB"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For 5G gNode field should be enabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    //@Test(groups = {"Integration"},description = "verify 5G technology",priority = 13)
    public void Verify_5G_TechnologyField_NG(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_12NGB"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For 5G gNode field should be enabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    //@Test(groups = {"Integration"},description = "verify 5G technology",priority = 14)
    public void Verify_5G_TechnologyField_NH(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_12NHB"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For 5G gNode field should be enabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify 5G technology",priority = 15)
    public void Verify_5G_TechnologyField_NK(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_12NKB"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For 5G gNode field should be enabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify 5G technology",priority = 16)
    public void Verify_5G_TechnologyField_NL(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_12NLB"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For 5G gNode field should be enabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify 5G technology",priority = 17)
    public void Verify_5G_TechnologyField_NM(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_12NMB"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For 5G gNode field should be enabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify 5G technology",priority = 18)
    public void Verify_5G_TechnologyField_NN(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_12NNB"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For 5G gNode field should be enabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify 5G technology",priority = 19)
    public void Verify_5G_TechnologyField_NP(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_12NPB"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For 5G gNode field should be enabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify LTE technology",priority = 20)
    public void Verify_LTE_TechnologyField(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        String SectorID = sectorLTE.sectorId;
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(SectorID,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIseNodeEnabled(),"For LTE eNode field should be enabled");
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For LTE gNode field should be disabled");
        softAssert.assertTrue(rfSectorPage.getCellNamePlanned("LTE",sectorLTE),"Based on the Technology field cellName Planned field value is displayed ");
        softAssert.assertTrue(rfSectorPage.getCGI_Name(sectorLTE),"CGIName-Planned field data is concatination of fields MCC,MNC,(eNodeBID8256),CellID");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify LTE technology",priority = 21)
    public void Verify_LTE_TechnologyField_LC(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector Sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_13LCA"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(Sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIseNodeEnabled(),"For LTE eNode field should be enabled");
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For LTE gNode field should be disabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify LTE technology",priority = 22)
    public void Verify_LTE_TechnologyField_LD(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector Sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_13LDA"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(Sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIseNodeEnabled(),"For LTE eNode field should be enabled");
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For LTE gNode field should be disabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify LTE technology",priority = 23)
    public void Verify_LTE_TechnologyField_LE(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector Sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_13LEA"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(Sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIseNodeEnabled(),"For LTE eNode field should be enabled");
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For LTE gNode field should be disabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify LTE technology",priority = 24)
    public void Verify_LTE_TechnologyField_LF(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector Sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_13LFA"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(Sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIseNodeEnabled(),"For LTE eNode field should be enabled");
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For LTE gNode field should be disabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify LTE technology",priority = 25)
    public void Verify_LTE_TechnologyField_LG(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector Sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_13LGA"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(Sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIseNodeEnabled(),"For LTE eNode field should be enabled");
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For LTE gNode field should be disabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify LTE technology",priority = 26)
    public void Verify_LTE_TechnologyField_LH(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector Sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_13LHA"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(Sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIseNodeEnabled(),"For LTE eNode field should be enabled");
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For LTE gNode field should be disabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify LTE technology",priority = 27)
    public void Verify_LTE_TechnologyField_LK(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector Sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_13LKA"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(Sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIseNodeEnabled(),"For LTE eNode field should be enabled");
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For LTE gNode field should be disabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify LTE technology",priority = 28)
    public void Verify_LTE_TechnologyField_LP(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector Sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_13LPA"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(Sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIseNodeEnabled(),"For LTE eNode field should be enabled");
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For LTE gNode field should be disabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify NBIOT technology",priority = 29)
    public void Verify_NBIOT_TechnologyField(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        String SectorID = sectorNBIOT.sectorId;
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(SectorID,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIseNodeEnabled(),"For NBIOT eNode field should be enabled");
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For NBIOT gNode field should be disabled");
        softAssert.assertTrue(rfSectorPage.getCellNamePlanned("NBIOT",sectorNBIOT),"Based on the Technology field cellName Planned field value is displayed ");
        softAssert.assertTrue(rfSectorPage.getCGI_Name(sectorNBIOT),"CGIName-Planned field data is concatination of fields MCC,MNC,(eNodeBID8256),CellID");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify NBIOT technology",priority = 30)
    public void Verify_NBIOT_TechnologyField_TA(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector Sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_23TAA"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(Sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIseNodeEnabled(),"For NBIOT eNode field should be enabled");
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For NBIOT gNode field should be disabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify NBIOT technology",priority = 31)
    public void Verify_NBIOT_TechnologyField_TD(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector Sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_23TDA"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(Sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIseNodeEnabled(),"For NBIOT eNode field should be enabled");
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For NBIOT gNode field should be disabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify NBIOT technology",priority = 32)
    public void Verify_NBIOT_TechnologyField_TP(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector Sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_23TPA"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(Sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIseNodeEnabled(),"For NBIOT eNode field should be enabled");
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For NBIOT gNode field should be disabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify NBIOT technology",priority = 33)
    public void Verify_NBIOT_TechnologyField_TR(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        Sector Sector = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_23TRA"));
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(Sector.sectorId,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertTrue(rfSectorPage.verifyIseNodeEnabled(),"For NBIOT eNode field should be enabled");
        softAssert.assertTrue(rfSectorPage.verifyIsgNodeEnabled(),"For NBIOT gNode field should be disabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify GSM technology",priority = 34)
    public void Verify_GSM_TechnologyField(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        String SectorID = sectorGSM.sectorId;
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(SectorID,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertFalse(!(rfSectorPage.verifyIseNodeEnabled()),"For GSM eNode field should be disabled");
        softAssert.assertFalse(!(rfSectorPage.verifyIsgNodeEnabled()),"For GSM gNode field should be disabled");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verify UMTS technology",priority = 35)
    public void Verify_UMTS_TechnologyField(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        String SectorID = sectorUMTS.sectorId;
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(SectorID,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String techField = rfSectorPage.getSectorTechnologyField();
        softAssert.assertFalse(!(rfSectorPage.verifyIseNodeEnabled()),"For UMTS eNode field should be disabled");
        softAssert.assertFalse(!(rfSectorPage.verifyIsgNodeEnabled()),"For UMTS gNode field should be disabled");
        softAssert.assertTrue(rfSectorPage.getCellNamePlanned("UMTS",sectorUMTS),"Based on the Technology field cellName Planned field value is displayed ");
        softAssert.assertTrue(rfSectorPage.getCGI_Name(sectorUMTS),"CGIName-Planned field data is concatination of fields MCC,MNC,(eNodeBID8256),CellID");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

}
