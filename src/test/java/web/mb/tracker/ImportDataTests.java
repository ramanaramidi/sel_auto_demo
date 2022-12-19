package web.mb.tracker;

import common.BaseTest;
import commons.constants.Constants;
import commons.enums.LoginOptionEnum;
import commons.objects.ImportFile;
import commons.objects.Ring;
import commons.objects.Sector;
import commons.objects.Site;
import org.testng.annotations.Test;
import pages.web.Tracker.AddPORPage;
import pages.web.Tracker.PORTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import pages.web.reports.ImportPage;
import rest.misc.MiscHelper;
import rest.por.PorHelper;
import rest.ring.RingHelper;
import rest.sector.SectorHelper;
import rest.site.SiteHelper;

import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ImportDataTests extends BaseTest {

    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");

    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    ImportFile importFile;
    String SWITCH = MiscHelpers.getRandomString(8, false);
    String AMF = MiscHelpers.getRandomString(8, false);
    String BNC = MiscHelpers.getRandomString(8, false);
    MiscHelper miscHelperRest = new MiscHelper();


    public ImportDataTests() {
        if (envURL == null) {
            envURL = "https://magentabuiltstg.t-mobile.com/Login.do";
        }
        if (testSuite == null) {
            testSuite = "TestRunner.xml";
        }
    }


    @Test(groups = {"Integration"}, description = "login", priority = 1)
    public void login(Method method) throws Exception {
        loginPage = new LoginPage(driver);
        loginPage.doLogin(LoginOptionEnum.SAML);
        String url = loginPage.getLoginUrl(alphaUser);
        if (url != null) {
            loginPage.launchUrl(url);
        }
        mainSideMenu = loginPage.LoginAsUser(superUser);
    }

    @Test(groups = {"Integration"}, description = "New Sector from GUI", priority = 2)
    public void createOneNewBasicSwitch_Import() throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "INSERT_UPDATE");
        HashMap<String, String> fieldValues = new HashMap<>();
        fieldValues.put("MSM_RECORD_TYPE", "1_Switch");
        fieldValues.put("MSM_MARKET", "TEST - LAB MARKET");
        fieldValues.put("MSM_SWITCH", SWITCH);
        fieldValues.put("MSM_REGION", "CORPORATE");
        fieldValues.put("MSM_MGWMMEAMF", SWITCH.toUpperCase());
        MiscHelpers.generateCsvSingleLineItem(fieldValues, Constants.MSM_IMPORT_DATA);
        ImportFile importFile = new ImportFile("10018387", "msm_imports.csv", Constants.MSM_IMPORT_DATA);
        importFile = miscHelperRest.importFileAndGetStatusWithParam(importFile, params);
        AssertionsUtil softAssert = new AssertionsUtil();
        softAssert.assertNotNull(importFile, "File import was successful");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "New Sector from GUI", priority = 3)
    public void createOneNewBasicAmf_Import() throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "INSERT_UPDATE");
        HashMap<String, String> fieldValues = new HashMap<>();
        fieldValues.put("MSM_RECORD_TYPE", "2_AMF 5G");
        fieldValues.put("MSM_MARKET", "TEST - LAB MARKET");
        fieldValues.put("MSM_SWITCH", SWITCH.toUpperCase());
        fieldValues.put("MSM_REGION", "CORPORATE");
        fieldValues.put("MSM_MGWMMEAMF", SWITCH.toUpperCase());
        MiscHelpers.generateCsvSingleLineItem(fieldValues, Constants.MSM_IMPORT_DATA);
        ImportFile importFile = new ImportFile("10018387", "msm_imports.csv", Constants.MSM_IMPORT_DATA);
        importFile = miscHelperRest.importFileAndGetStatusWithParam(importFile, params);
        AssertionsUtil softAssert = new AssertionsUtil();
        softAssert.assertNotNull(importFile, "File import was successful");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "New Sector from GUI", priority = 3)
    public void createOneNewLacTac_Import() throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "INSERT_UPDATE");
        HashMap<String, String> fieldValues = new HashMap<>();
        fieldValues.put("TRACKOR_KEY", "TEST - LAB MARKET_" + SWITCH);
        fieldValues.put("LAC_LACTAC", MiscHelpers.getRandomNumber(3));
        fieldValues.put("LAC_SELECTABLE", "Yes");
        MiscHelpers.generateCsvSingleLineItem(fieldValues, Constants.MSM_IMPORT_DATA);
        ImportFile importFile = new ImportFile("10018495", "msm_imports.csv", Constants.MSM_IMPORT_DATA);
        importFile = miscHelperRest.importFileAndGetStatusWithParam(importFile, params);
        AssertionsUtil softAssert = new AssertionsUtil();
        softAssert.assertNotNull(importFile, "File import was successful");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "New Sector from GUI", priority = 4)
    public void createOneNewRac_Import() throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "INSERT_UPDATE");
        HashMap<String, String> fieldValues = new HashMap<>();
        fieldValues.put("TRACKOR_KEY", "TEST - LAB MARKET_" + SWITCH + "_" + SWITCH);
        fieldValues.put("RAC_RAC", MiscHelpers.getRandomNumber(3));
        fieldValues.put("RAC_SELECTABLE", "Yes");
        MiscHelpers.generateCsvSingleLineItem(fieldValues, Constants.MSM_IMPORT_DATA);
        ImportFile importFile = new ImportFile("10018494", "msm_imports.csv", Constants.MSM_IMPORT_DATA);
        importFile = miscHelperRest.importFileAndGetStatusWithParam(importFile, params);
        AssertionsUtil softAssert = new AssertionsUtil();
        softAssert.assertNotNull(importFile, "File import was successful");
        softAssert.closeAssert();
    }

}





//TODO: Need to verify this functionality using backend api and needs a revisit

//    @Test(groups = {"Integration"},description = "Upload Colo App Submitted Document",priority = 7)
//    public void importSectorCsv() throws Exception {
//        AssertionsUtil softAssert = new AssertionsUtil();
//        importPage= mainSideMenu.goToImportDataPage();
//        importPage.searchForValue("Sector Create - ADMIN","Import Name");
//        importPage.clickOnImport();
//        softAssert.assertTrue(importPage.importFile(),"File Import was a success" );
//        softAssert.closeAssert();
//    }
//
//    @Test(groups = {"Integration"},description = "Upload Colo App Submitted Document",priority = 7)
//    public void createSectorImportOffAir() throws Exception {
//        AssertionsUtil softAssert = new AssertionsUtil();
//
//        importPage= mainSideMenu.goToImportDataPage();
//        importPage.searchForValue("Sector Create - ADMIN","Import Name");
//        importPage.clickOnImport();
//        softAssert.assertTrue(importPage.importFile(),"File Import was a success" );
//        softAssert.closeAssert();
//    }
//
//    private void generateSectorCsvData() throws IOException {
//        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
//        Ring ring = new Ring("Active", ringId, "Indoor Node");
//        Site site = new Site(ringId,"Primary","Active Site");
//        site = siteHelper.createActiveRingAndPrimaryActiveSite(ring,site);
//        ACTIVE_SITE_PRIMARY_BUILD_ = site;
//        System.out.println("ACTIVE_SITE_PRIMARY_BUILD_::::: "+site.siteId);
//
//        List<List<String>> records = Arrays.asList(
//                Arrays.asList("SITE_ID", "SECTOR_ID", "SEC_SECTOR_LATITUDE", "SEC_SECTOR_LONGITUDE"),
//                Arrays.asList(site.siteId, site.siteId+"_"+"21LPW", Constants.VALID_LATITUDE, Constants.VALID_LONGITUDE),
//                Arrays.asList(site.siteId, site.siteId+"_"+"21LAW", Constants.VALID_LATITUDE, Constants.VALID_LONGITUDE ),
//                Arrays.asList(site.siteId, site.siteId+"_"+"21NKW", Constants.VALID_LATITUDE, Constants.VALID_LONGITUDE )
//        );
//        Path path = Paths.get(Constants.SECTOR_IMPORT_DATA);
//        Files.deleteIfExists(path);
//        BufferedWriter writer = Files.newBufferedWriter(path);
//        for (List<String> record : records) {
//            writer.write(String.join(",", record));
//            writer.newLine();
//        }
//        writer.close();
//    }
//
//    private void generateData(){
//        String ringIdForSector = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
//        Ring ringActiveForSector = new Ring("Active", ringIdForSector, "Indoor Node");
//        Site siteForSector = new Site(ringIdForSector,"Primary","Active Site");
//        siteForSector = siteHelper.createActiveRingAndSite(ringActiveForSector,siteForSector);
//        SECTOR_NEW = sectorHelper.createNewSector(new Sector(siteForSector.siteId,siteForSector.siteId+"_21LPW"));
//    }

