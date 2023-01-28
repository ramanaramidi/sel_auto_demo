package web.mb.tracker.sector;

import common.BaseTest;
import commons.constants.Constants;
import commons.enums.LoginOptionEnum;
import commons.objects.*;
import org.testng.annotations.Test;
import pages.web.Tracker.ProcessesPage;
import pages.web.Tracker.RFSectorPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import pages.web.reports.ImportPage;
import rest.misc.MiscHelper;
import rest.ring.RingHelper;
import rest.sector.SectorHelper;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;
import java.util.HashMap;

public class DevSectorTests extends BaseTest {


    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");

    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    RFSectorPage rfSectorPage;
    Sector importSector;
    Site importSite;

    Site siteForSector;
    SectorHelper sectorHelper = new SectorHelper();
    SiteHelper siteHelper = new SiteHelper();
    RingHelper ringHelper = new RingHelper();
    MiscHelper miscHelperRest = new MiscHelper();
    String RFSectorID1;
    ImportPage importPage;
    ProcessesPage processesPage;
    Site site;
    Sector sector5G;
    Sector sectorLTE;
    Sector sectorNBIOT;
    Sector sectorGSM;
    Sector sectorUMTS;
    String RFSectorIDLessthan14Characters = "AU00KR1A_35LA";

    public DevSectorTests() {
        if (envURL == null) {
            envURL = "https://magentabuiltstg.t-mobile.com/Login.do";
        }
        if (testSuite == null) {
            testSuite = "sectorSet.xml";
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
        generateData();
        mainSideMenu = loginPage.LoginAsUser(superUser);

    }

    private void generateData() {
        String ringIdForSector = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActiveForSector = new Ring("Active", ringIdForSector, "Indoor Node");
        siteForSector = new Site(ringIdForSector, "Primary", "Active Site");
        siteForSector = siteHelper.createActiveRingAndSite(ringActiveForSector, siteForSector);
        RFSectorID1 = siteForSector.siteId + "_21LAU";

        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringId, "Macro");
        site = new Site(ringId, "Candidate", "New Site");
        site = siteHelper.createNewRingAndSite(ring, site);
    }
    @Test(groups = {"Integration"},description = "New Ring Creation By Import",priority = 2)
    public void createSector_Import() throws Exception {
        HashMap<String, String> params = new HashMap<>();

        params.put("action", "INSERT");
        HashMap<String, String> fieldValues = new HashMap<>();
        fieldValues.put("SEC_SECTOR_LATITUDE", siteForSector.latitude);
        fieldValues.put("R_SECTOR_LONGITUDE", siteForSector.longitude);
        fieldValues.put("SITE_ID", siteForSector.siteId);
        fieldValues.put("SECTOR_ID",RFSectorID1);
        MiscHelpers.generateCsvSingleLineItem(fieldValues, Constants.SECTOR_IMPORT_DATA);
        ImportFile importFile = new ImportFile("10017331", "sector_imports.csv", Constants.SECTOR_IMPORT_DATA);
        importFile = miscHelperRest.importFileAndGetStatusWithParam(importFile, params);
        AssertionsUtil softAssert = new AssertionsUtil();
        softAssert.assertNotNull(importFile, "File import was successful");
        rfSectorPage= mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue(RFSectorID1,"SEC:Sector ID");
        rfSectorPage.selectEditOption();
        softAssert.assertTrue(rfSectorPage.sectorIdValidations(),"Sector Id Contains Five Digits After '_'");
        softAssert.assertTrue(rfSectorPage.sectorIdValidationsForSiteId(siteForSector.siteId),"Sector Id Contains Site Id");
        softAssert.assertFalse(rfSectorPage.sectorIndetifierVerification(),"SectorIdentifier Field Is Empty");
        softAssert.assertTrue(rfSectorPage.enterPriseSmallCellVerification(),"EnterPrise Small Cell Field Is Populated");
        rfSectorPage = rfSectorPage.goToRfSector();
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Create Site Without Some Of The Mandatory Fields", priority = 2)
    public void createSiteWithoutSomeOfMandatoryFields_Import() throws Exception {
        HashMap<String, String> params = new HashMap<>();

        params.put("action", "INSERT_UPDATE");
        HashMap<String, String> fieldValues = new HashMap<>();
        fieldValues.put("S_SITE_LATITUDE", site.latitude);
        fieldValues.put("S_SITE_LONGITUDE", site.longitude);
        fieldValues.put("SITE_ID", site.siteId);
        fieldValues.put("RING_ID", site.ringId);
        MiscHelpers.generateCsvSingleLineItem(fieldValues, Constants.Site_IMPORT_DATA);
        ImportFile importFile = new ImportFile(" 10017328", "site_imports.csv", Constants.Site_IMPORT_DATA);
        importFile = miscHelperRest.importFileAndGetStatusWithParam(importFile, params);
        System.out.println("processId Is::" + importFile.processId);
        AssertionsUtil softAssert = new AssertionsUtil();
        softAssert.assertNotNull(importFile, "File import was successful");
        importPage = mainSideMenu.goToImportDataPage();
        importPage.searchForValue("site", "Import Name");
        importPage.goToSiteImportHistory();
        importPage.searchForProcessId(importFile.processId, "Process ID");
        importPage.goToImportDetails();
        String dbMessage = importPage.searchForValueInGrid("DB Message", 1);
        softAssert.assertNotNull(dbMessage, "Site Is Not Created By Import");
        importPage.backToParentWindow();
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Create Site Without Some Of The Mandatory Fields", priority = 2)
    public void createSiteWithoutSomeOfMandatoryFieldsAndVerifyInProcessPage_Import() throws Exception {
        HashMap<String, String> params = new HashMap<>();

        params.put("action", "INSERT_UPDATE");
        HashMap<String, String> fieldValues = new HashMap<>();
        fieldValues.put("S_SITE_LATITUDE", site.latitude);
        fieldValues.put("S_SITE_LONGITUDE", site.longitude);
        fieldValues.put("SITE_ID", site.siteId);
        fieldValues.put("RING_ID", site.ringId);
        MiscHelpers.generateCsvSingleLineItem(fieldValues, Constants.Site_IMPORT_DATA);
        ImportFile importFile = new ImportFile(" 10017328", "site_imports.csv", Constants.Site_IMPORT_DATA);
        importFile = miscHelperRest.importFileAndGetStatusWithParam(importFile, params);
        System.out.println("processId Is::" + importFile.processId);
        AssertionsUtil softAssert = new AssertionsUtil();
        softAssert.assertNotNull(importFile, "File import was successful");
        processesPage = mainSideMenu.goToProcessesPage();
        processesPage.searchForProcessId(importFile.processId, "Process ID");
        processesPage.goToImportDetails();
        String dbMessage = processesPage.searchForValueInGrid("DB Message", 1);
        softAssert.assertNotNull(dbMessage, "Site Is Not Created By Import");
        processesPage.backToParentWindow();
        softAssert.closeAssert();
    }

}


