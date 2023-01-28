package web.mb.tracker.sector;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.*;
import org.testng.annotations.Test;
import pages.web.Tracker.MSMTrackerPage;
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

import java.lang.reflect.Method;
import java.util.List;

public class ProvisionFieldsTests extends BaseTest {
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
    Sector sector;
    String parentWindow;
    MSMTrackerPage MSMtrackerPage;
    MiscHelper miscHelper = new MiscHelper();
    public Users rfEngineer = new Users();
    // public Users sitedev = new Users();

    public ProvisionFieldsTests()
    {
        if(envURL == null) {envURL = 	"https://magentabuiltstg.t-mobile.com/Login.do";}
        if(testSuite == null) {testSuite = "sectorSet.xml";}
        rfEngineer = UserData.getRfEngUserDetails(rfEngineer);
    }

    @Test(groups = {"Integration"}, description = "login", priority = 1)
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
        mainSideMenu = loginPage.LoginAsUser(rfEngineer);
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

    @Test(groups = {"Integration"}, description = "Validate RF Sector/Cell-Small Cell For Non LTE ", priority = 2)
    public void validateRfSectorSmallCellForNonLte(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sector5G.sectorId, "SEC:Sector ID");
        rfSectorPage.editRFSector();
        String techField = rfSectorPage.getTechnologyField();
        softAssert.assertDoesNotContains(techField,"LTE", "sector Technology should not match");
        softAssert.assertFalse(rfSectorPage.validateLockIsEnabled("SEC:Small Cell e911 Auto Provision - Request"),"For Small Cell e911 Auto Provision - Request should be disabled");
        rfSectorPage.backToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Validate RF Sector/Cell-Small Cell For LTE Non Small Cell ", priority = 3)
    public void validateRfSectorForLteNonSmallCell(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sectorLTE.sectorId, "SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertContains(rfSectorPage.getTechnologyField(),"LTE", "sector Technology match");
        rfSectorPage.setSectorFieldName("SEC:Sector Type","Undefined");
        softAssert.assertFalse(rfSectorPage.validateLockIsEnabled("SEC:Address"),"SEC:Address should be disabled");
        softAssert.assertFalse(rfSectorPage.validateLockIsEnabled("SEC:Address 2"),"SEC:Address 2 should be disabled");
        softAssert.assertFalse(rfSectorPage.validateLockIsEnabled("SEC:City"),"SEC:City should be disabled");
        softAssert.assertFalse(rfSectorPage.validateLockIsEnabled("SEC:State"),"SEC:State should be disabled");
        softAssert.assertFalse(rfSectorPage.validateLockIsEnabled("SEC:Zip"),"SEC:Zip should be disabled");
        softAssert.assertFalse(rfSectorPage.validateLockIsEnabled("SEC:County"),"SEC:County should be disabled");
        softAssert.assertFalse(rfSectorPage.validateLockIsEnabled("SEC:Cross Street"),"SEC:Cross Street should be disabled");
        softAssert.assertFalse(rfSectorPage.validateLockIsEnabled("SEC:Jurisdiction"),"SEC:Jurisdiction should be disabled");
        softAssert.assertFalse(rfSectorPage.validateLockIsEnabled("SEC:Update Address Info (from Spectrum Spatial)"),"SEC:Update Address Info should be disabled");
        softAssert.assertFalse(rfSectorPage.validateLockIsEnabled("SEC:Small Cell e911 Auto Provision - Request"),"For Small Cell e911 Auto Provision - Request should be disabled");
        rfSectorPage.goToRfTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Validate RF Sector/Cell-Small Cell For LTE Small Cell ", priority = 4)
    public void validateRfSectorForLteSmallCell(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue(sectorLTE.sectorId, "SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertContains(rfSectorPage.getTechnologyField(),"LTE", "sector Technology match");
        rfSectorPage.setSectorFieldName("SEC:Sector Type","Small Cell");
        softAssert.assertTrue(rfSectorPage.validateLockIsEnabled("SEC:Address"),"SEC:Address should be Enabled");
        softAssert.assertTrue(rfSectorPage.validateLockIsEnabled("SEC:Address 2"),"SEC:Address 2 should be Enabled");
        softAssert.assertTrue(rfSectorPage.validateLockIsEnabled("SEC:City"),"SEC:City should be Enabled");
        softAssert.assertTrue(rfSectorPage.validateLockIsEnabled("SEC:State"),"SEC:State should be Enabled");
        softAssert.assertTrue(rfSectorPage.validateLockIsEnabled("SEC:Zip"),"SEC:Zip should be Enabled");
        softAssert.assertTrue(rfSectorPage.validateLockIsEnabled("SEC:County"),"SEC:County should be Enabled");
        softAssert.assertTrue(rfSectorPage.validateLockIsEnabled("SEC:Cross Street"),"SEC:Cross Street should be Enabled");
        softAssert.assertTrue(rfSectorPage.validateLockIsEnabled("SEC:Jurisdiction"),"SEC:Jurisdiction should be Enabled");
        softAssert.assertTrue(rfSectorPage.validateLockIsEnabled("SEC:Update Address Info (from Spectrum Spatial)"),"SEC:Update Address Info should be Enabled");
        softAssert.assertFalse(rfSectorPage.validateLockIsEnabled("SEC:Small Cell e911 Auto Provision - Request"),"For Small Cell e911 Auto Provision - Request should be disabled");
        rfSectorPage.goToRfTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Validate RF Sector/Cell-Small Cell For LTE Non Micro or Pico ", priority = 5)
    public void validateRfSectorSmallCellForLteNonMicro(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTrackerAsEng();
        String SectorID = "SAU1XTWO_13LAY";
        rfSectorPage.searchForValue(SectorID, "SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertContains(rfSectorPage.getTechnologyField(),"LTE", "sector Technology should match");
        softAssert.assertFalse(rfSectorPage.verifyFieldDropDown("R:Ring ID Description"),"Ring ID Description Should not match");
        softAssert.assertFalse(rfSectorPage.getCoverageTypeField("SEC:Coverage Type"),"coverage Type Should not match");
        softAssert.assertFalse(rfSectorPage.validateLockIsEnabled("SEC:Small Cell e911 Auto Provision - Request"),"For Small Cell e911 Auto Provision - Request should be disabled");
        rfSectorPage.backToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Validate RF Sector/Cell-Small Cell For LTE Micro or Pico", priority = 6)
    public void validateRfSectorSmallCellForLteMicro(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTrackerAsEng();
        String SectorID = "SAUXMYC8_21LAZ";
        rfSectorPage.searchForValue(SectorID, "SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertContains(rfSectorPage.getTechnologyField(),"LTE", "sector Technology should match");
        softAssert.assertTrue(rfSectorPage.verifyFieldDropDown("R:Ring ID Description"),"Ring ID Description Should match");
        softAssert.assertTrue(rfSectorPage.getCoverageTypeField("SEC:Coverage Type"),"coverage Type Should match");
        softAssert.assertTrue(rfSectorPage.validateLockIsEnabled("SEC:Small Cell e911 Auto Provision - Request"),"For Small Cell e911 Auto Provision - Request should be enabled");
        softAssert.assertTrue(rfSectorPage.uspsAddressValidation(),"SEC:USPS Address Validation - Results should = 'Pass' or Submit for Validation or Validation Failed");
        rfSectorPage.backToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "RF Sector Small Cell e911 Auto Provision Request Submit ", priority = 7)
    public void uspsValidationFailed(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTrackerAsEng();
        String SectorID = "SAUXMYC8_21LAZ";
        rfSectorPage.searchForValue(SectorID, "SEC:Sector ID");
        rfSectorPage.editRFSector();
        rfSectorPage.changeZipCode("500081");
        softAssert.assertNotEquals(rfSectorPage.getZipFieldValue("SEC:Zip"),rfSectorPage.getZipFieldValue("SEC:USPS Zip"),"Zip Should not match");
        softAssert.assertTrue(rfSectorPage.uspsAddressValidation(),"SEC:USPS Address Validation - Results should = 'Pass' or 'Failed'");
        softAssert.assertTrue(rfSectorPage.verifyE911DropDown("SEC:Small Cell e911 Auto Provision - Results"),"SEC:Small Cell e911 Auto Provision - Results");
        rfSectorPage.sendUspsZip();
        softAssert.assertContains(rfSectorPage.getZipFieldValue("SEC:Zip"),rfSectorPage.getZipFieldValue("SEC:USPS Zip"),"Zip Should match");
        softAssert.assertTrue(rfSectorPage.uspsAddressValidation(),"SEC:USPS Address Validation - Results should = 'Pass' or 'Failed'");
        softAssert.assertTrue(rfSectorPage.verifyE911DropDown("SEC:Small Cell e911 Auto Provision - Results"),"SEC:Small Cell e911 Auto Provision - Results");
        rfSectorPage.backToTrackerPage();
        softAssert.closeAssert();
    }/*
    @Test(groups = {"Integration"}, description = "RF Sector Small Cell e911 Auto Provision Request Submit ", priority = 8)
    public void autoProvisionRequestSubmit(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTrackerAsEng();
        String SectorID = "SAUPDFBH_13LAZ";
        rfSectorPage.searchForValue(SectorID, "SEC:Sector ID");
        rfSectorPage.editRFSector();
        rfSectorPage.switchToChildWindows();
        rfSectorPage.fullScreen();
        softAssert.assertTrue(rfSectorPage.uspsAddressValidation(),"SEC:USPS Address Validation - Results should = 'Pass' or Submit for Validation or Validation Failed");
        softAssert.assertTrue(rfSectorPage.validateLockIsEnabled("SEC:Small Cell e911 Auto Provision - Request"),"For Small Cell e911 Auto Provision - Request should be Enabled");
        softAssert.assertTrue(rfSectorPage.verifyE911DropDown("SEC:Small Cell e911 Auto Provision - Results"),"SEC:Small Cell e911 Auto Provision - Results should be Ready for Submission to Upsilon");
        rfSectorPage.clickCheckBox();
        rfSectorPage.goToRfTrackerPage();
        softAssert.closeAssert();
    }*/

    @Test(groups = {"Integration"}, description = "Verify SEC:Switch - Planned and only Switches within previously selected market", priority = 9)
    public void VerifySwitchesWithinMarket(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        MarketSwitch marketSwitch = new MarketSwitch();
        rfSectorPage = mainSideMenu.goToRFSectorTrackerAsEng();
        //String SectorID = "SAUTYHL9_13LAA";
        rfSectorPage.setSector(sector5G, "SAN FRANCISCO_TESTSANF_TEST AMF", "LAC000020406");
        sectorHelper.updateSectorWithPlannedValues(sector5G);
        rfSectorPage.searchForValue(sector5G.sectorId, "SEC:Sector ID");
        rfSectorPage.editRFSector();
        List<String> switchList = rfSectorPage.getTableValues("SEC:Switch - Planned", 3, "SAN FRANCISCO_");
        List<String> masterSwitchList = miscHelper.getSwitchMasterListForMarket(marketSwitch);
        switchList.removeAll(masterSwitchList);
        int size = switchList.size();
        softAssert.assertEquals(size, 0, "Validate Switches in the Market");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Verify SEC:AMF and only see AMF choices within Switch previously selected", priority = 10)
    public void verifyAmfValueWithinSwitch(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        MarketSwitch marketSwitch = new MarketSwitch();
        rfSectorPage = mainSideMenu.goToRFSectorTrackerAsEng();
        //String SectorID = "SAUTYHL9_12NEB";
        rfSectorPage.searchForValue(sector5G.sectorId, "SEC:Sector ID");
        rfSectorPage.editRFSector();
        marketSwitch.recordType = "2_AMF 5G, 2_MME 4G, 2_MGW 2G/3G, 2_MSC 2G/3G, 2_OTHER 2G/3G";
        marketSwitch.msmSwitch = "TESTSANF";
        List<String> amfList = rfSectorPage.getTableValues("SEC:MSC / MGW / MME / AMF - Planned", 4, "");
        List<String> masterAmfList = miscHelper.getAmfDetailsForSwitch(marketSwitch);
        amfList.removeAll(masterAmfList);
        int size = amfList.size();
        softAssert.assertEquals(size, 0, "Validate AMF Value in the Switch");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Verify SEC:MME and only see MME choices within Switch previously selected", priority = 11)
    public void VerifyMmeValueWithinSwitch(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTrackerAsEng();
        MarketSwitch marketSwitch = new MarketSwitch();
        //String SectorID = "SAUTYHL9_23TEA";
        rfSectorPage.setSector(sectorNBIOT, "SAN FRANCISCO_TESTSANF_TESTSMME", "LAC000020407");
        sectorHelper.updateSectorWithPlannedValues(sectorNBIOT);
        rfSectorPage.searchForValue(sectorNBIOT.sectorId, "SEC:Sector ID");
        rfSectorPage.editRFSector();
        marketSwitch.recordType = "2_AMF 5G, 2_MME 4G, 2_MGW 2G/3G, 2_MSC 2G/3G, 2_OTHER 2G/3G";
        marketSwitch.msmSwitch = "TESTSANF";
        List<String> mmeList = rfSectorPage.getTableValues("SEC:MSC / MGW / MME / AMF - Planned", 4, "");
        List<String> masterMmeList = miscHelper.getAmfDetailsForSwitch(marketSwitch);
        mmeList.removeAll(masterMmeList);
        int size = mmeList.size();
        softAssert.assertEquals(size, 0, "Validate MME Value in the Switch");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Verify SEC:MSC/MGW and only see MSC/MGW choices belonging to previously selected Switch", priority = 12)
    public void verifyMgwValueWithinSwitch(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        MarketSwitch marketSwitch = new MarketSwitch();
        rfSectorPage = mainSideMenu.goToRFSectorTrackerAsEng();
        //String SectorID = "SAUTYHL9_11UAD";
        rfSectorPage.setSector(sectorUMTS, "SAN FRANCISCO_TESTSANF_TESTSMGW", "LAC000020408");
        sectorHelper.updateSectorWithPlannedValues(sectorUMTS);
        rfSectorPage.searchForValue(sectorUMTS.sectorId, "SEC:Sector ID");
        rfSectorPage.editRFSector();
        marketSwitch.recordType = "2_AMF 5G, 2_MME 4G, 2_MGW 2G/3G, 2_MSC 2G/3G, 2_OTHER 2G/3G";
        marketSwitch.msmSwitch = "TESTSANF";
        List<String> mgwList = rfSectorPage.getTableValues("SEC:MSC / MGW / MME / AMF - Planned", 4, "");
        List<String> masterMgwList = miscHelper.getAmfDetailsForSwitch(marketSwitch);
        mgwList.removeAll(masterMgwList);
        int size = mgwList.size();
        softAssert.assertEquals(size, 0, "Validate MSC/MGW Value in the Switch");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Verify SEC:LAC/TAc and only see LACs within Switch and MSC/MGW/ combo that was previously selected", priority = 13)
    public void verifyLacValueWithinSwitchCombo(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTrackerAsEng();
        //Sector sector = new Sector("SAUTYHL9", "SAUTYHL9_12NEB");
        rfSectorPage.setSectorWithoutLac(sector5G, "SAN FRANCISCO_TESTSANF_TEST AMF1");
        sectorHelper.updateSectorWithAMFPlannedValues(sector5G);
        rfSectorPage.searchForValue(sector5G.sectorId, "SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertTrue(rfSectorPage.getFieldValue("SEC:LAC / TAC - Planned"), "LAC Value is not Present");
        rfSectorPage.goToRfTrackerPage();
        rfSectorPage.editRFSector();
        rfSectorPage.setSector(sector5G, "SAN FRANCISCO_TESTSANF_TEST AMF", "LAC000020406");
        sectorHelper.updateSectorWithPlannedValues(sector5G);
        softAssert.assertFalse(rfSectorPage.getFieldValue("SEC:LAC / TAC - Planned"), "Value Is Present");
        rfSectorPage.backToTrackerPage();
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Verify SEC:BSC/RNC and only see BNCs within Switch and MSC/MGW/ combo that was previously selected", priority = 14)
    public void verifyBscValueWithinSwitchCombo(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTrackerAsEng();
        //Sector sector = new Sector("SAUTYHL9", "SAUTYHL9_13GDC");
        rfSectorPage.setSector(sectorGSM, "SAN FRANCISCO_TESTSANF_TESTSMSC", "LAC000020413");
        sectorHelper.updateSectorWithAMFPlannedValues(sectorGSM);
        rfSectorPage.searchForValue(sectorGSM.sectorId, "SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertTrue(rfSectorPage.getFieldValue("SEC:BSC / RNC - Planned"), "BSC Value is not Present");
        rfSectorPage.goToRfTrackerPage();
        rfSectorPage.editRFSector();
        rfSectorPage.setSector(sectorGSM, "SAN FRANCISCO_TESTSANF_TESTSMGW", "LAC000020408");
        sectorHelper.updateSectorWithPlannedValues(sectorGSM);
        softAssert.assertFalse(rfSectorPage.setBscValue("SEC:BSC / RNC - Planned"), "BSC Value is Present");
        rfSectorPage.backToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Verify SEC:RAC and only see RACs within Switch and MSC/MGW/ combo that was previously selected", priority = 15)
    public void verifyRacValueWithinSwitchCombo(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTrackerAsEng();
        //Sector sector = new Sector("SAUTYHL9", "SAUTYHL9_13GDC");
        rfSectorPage.setSector(sectorGSM, "SAN FRANCISCO_TESTSANF_TESTSMSC", "LAC000020413");
        sectorHelper.updateSectorWithAMFPlannedValues(sectorGSM);
        rfSectorPage.searchForValue(sectorGSM.sectorId, "SEC:Sector ID");
        rfSectorPage.editRFSector();
        softAssert.assertTrue(rfSectorPage.getFieldValue("SEC:RAC - Planned"), "BSC Value is not Present");
        rfSectorPage.goToRfTrackerPage();
        rfSectorPage.editRFSector();
        rfSectorPage.setSector(sectorGSM, "SAN FRANCISCO_TESTSANF_TESTSMGW", "LAC000020408");
        sectorHelper.updateSectorWithPlannedValues(sectorGSM);
        softAssert.assertFalse(rfSectorPage.setBscValue("SEC:RAC - Planned"), "BSC Value is Present");
        rfSectorPage.backToTrackerPage();
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Add Rac By Clicking On Pencil Button As RfEng",priority = 16)

    public void addRacAsRfEng(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue("SAUTYHL9_11UAD", "SEC:Sector ID");
        rfSectorPage.selectEditOption();
        rfSectorPage.addRac();
        softAssert.assertTrue(rfSectorPage.racIdValidation(),"Rac Id Generated");
        String racId = rfSectorPage.racIdValue();
        rfSectorPage.goToSearchInTable();
        String response =rfSectorPage.getTableList();
        //String response =  rfSectorPage.searchForValueInGridExact("RAC:RAC ID","RAC:RAC ID", racId);
        System.out.println (response);
        softAssert.assertContains(response,racId,"Rac Value Created In Record");
        softAssert.closeAssert();
        rfSectorPage.backToRfSectorPage();
    }

    @Test(groups = {"Integration"},description = "Add Lac/Tac By Clicking On Pencil Button As RfEng",priority = 17)

    public void addLacTacAsRfEng(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue("SAUTYHL9_11UAD", "SEC:Sector ID");
        rfSectorPage.selectEditOption();
        rfSectorPage.addLac();
        softAssert.assertTrue(rfSectorPage.lacIdValidation(),"Lac Id Generated");
        String lacId = rfSectorPage.lacIdValue();
        rfSectorPage.goToSearchInTable();
        String response = rfSectorPage.searchForValueInGridExact("LAC:LAC ID","LAC:LAC ID", lacId);
        System.out.println (response);
        softAssert.assertContains(response,lacId,"lac Value Created In record");
        softAssert.closeAssert();
        rfSectorPage.backToRfSectorPage();
    }

    @Test(groups = {"Integration"},description = "Acs Info Verification For Sector As RfEng",priority = 18)
    public void acsInfoVerificationAsRfEng(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTrackerAsEng();
        rfSectorPage.searchForValue("SAUTYHL9_11UAD", "SEC:Sector ID");
        rfSectorPage.selectEditOption();
        rfSectorPage.rfSectorAcsInfo();
        softAssert.assertTrue(rfSectorPage.acsInfoVerification(),"Acs Info Fields Are Editable And Can Be Saved");
        softAssert.closeAssert();
        rfSectorPage.rfSectorPage();
    }

}
