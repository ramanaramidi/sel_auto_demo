package web.mb.tracker;
import common.BaseTest;
import commons.constants.Constants;
import commons.enums.LoginOptionEnum;
import commons.objects.ImportFile;
import commons.objects.Ring;
import org.testng.annotations.Test;
import pages.web.Tracker.AddRingPage;
import pages.web.Tracker.RingTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.misc.MiscHelper;
import rest.project.ProjectHelper;
import rest.ring.RingHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;
import java.util.HashMap;

public class DevRingTests extends BaseTest {
    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    RingTrackerPage ringTracker;
    AddRingPage addNewRing;
    RingHelper ringHelper = new RingHelper();
    ProjectHelper projectHelper = new ProjectHelper();
    Ring ring;
    Ring   ringActive;
    Ring importRing;
    String ringCode = "XZ" + MiscHelpers.getRandomString(5, true).toUpperCase();
    String ringCodeCancel = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
    String SWITCH = MiscHelpers.getRandomString(8,false);
    MiscHelper miscHelperRest = new MiscHelper();

    public DevRingTests()
    {
        if(envURL == null) {envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");}
        if(testSuite == null) {testSuite = "sectorSet.xml";}
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
        importRing  = new Ring("Active", ringCode, "Macro");
        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        ring = new Ring("Active", ringId, "Macro");
        ringActive = ringHelper.createAnActiveRing(ring);
        // System.out.println(RingActive);
    }

    @Test(groups = {"Integration"},description = "Scip Photo Upload to For Ring",priority = 2)
    public void scipPhotoUploadForRing_Ring(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        ringTracker = mainSideMenu.goToRingTracker();
        ringTracker.searchForValue(ringActive.ringId, "R:Ring Code");
        addNewRing = ringTracker.selectEditOption();
        int oldDocCount = addNewRing.uploadScipImage();
        System.out.println("old doc count ::" + oldDocCount);
        projectHelper.uploadDocument(ringActive.trackerId.toString(), "R_SCIP_PACKAGE_UPLOAD", Constants.IMAGE_FILE_UPLOAD, "simpleImage.png");
        int newDocCount = addNewRing.getDocumentIDCount();
        System.out.println("new doc count ::" + newDocCount);
        softAssert.assertTrue(addNewRing.scipSubmittedDateValidation(),"Scip Submitted Date Generated");
        addNewRing.deleteAddedDocument();
        ringTracker = addNewRing.acceptAndGoToSiteTracker();
        softAssert.assertTrue(oldDocCount < newDocCount, "The doc is added");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Create New Ring And verify BTA Fields",priority = 2)
    public void createNewRingAndVerifyBtaFields_Ring(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        ringTracker = mainSideMenu.goToRingTracker();
        addNewRing = ringTracker.selectAddNewRingOption();
        Ring ring = new Ring("New Ring",ringCode);
        ringTracker = addNewRing.addingNewRingTracker(ring);
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        ringTracker.selectEditOption();
        softAssert.assertTrue(addNewRing.marketFieldValidation(), "Market Field Populated");
        softAssert.assertTrue(addNewRing.btaFieldValidation() ,"Bta Field Populated");
        ringTracker = addNewRing.acceptAndGoToSiteTracker();
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "New Ring Creation By Import",priority = 2)
    public void createRing_Import() throws Exception {
        HashMap<String,String> params = new HashMap<>();

        params.put("action","INSERT_UPDATE");
        HashMap<String,String> fieldValues = new HashMap<>();
        fieldValues.put("R_RING_LATITUDE", importRing.latitude);
        fieldValues.put("R_RING_LONGITUDE",importRing.longitude);
        fieldValues.put("MARKET_ID",importRing.market);
        fieldValues.put("RING_ID",ringCode);
        MiscHelpers.generateCsvSingleLineItem(fieldValues,Constants.Ring_IMPORT_DATA);
        ImportFile importFile = new ImportFile("10017335","ring_imports.csv",Constants.Ring_IMPORT_DATA);
        importFile = miscHelperRest.importFileAndGetStatusWithParam(importFile,params);
        AssertionsUtil softAssert = new AssertionsUtil();
        softAssert.assertNotNull(importFile,"File import was successful");
        ringTracker = mainSideMenu.goToRingTracker();
        ringTracker.searchForValue(ringCode, "R:Ring Code");
        softAssert.assertTrue(ringTracker.isDataPresentInTable(), "The ring should have been created "+ringCode);
        softAssert.assertTrue(ringTracker.isRingStatus("New Ring"), "The ring should have been created in new state");
        addNewRing = ringTracker.selectEditOption();
        softAssert.assertFalse(!(addNewRing.marketFieldLockValidation()),"Market Filed Is Locked");
        softAssert.assertTrue(addNewRing.dateCreatedValidation(),"Date And Time Has Created");
        softAssert.assertTrue(addNewRing.finalBuildSiteFieldValidation(),"Final Build Status Is Auto Generated As No");
        ringTracker = addNewRing.acceptAndGoToSiteTracker();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "New Ring Creation By Import And Fields Validation",priority = 3)
    public void createRingAndValidations_Import() throws Exception {
        HashMap<String,String> params = new HashMap<>();
        params.put("action","INSERT_UPDATE");
        HashMap<String,String> fieldValues = new HashMap<>();
        fieldValues.put("R_RING_LATITUDE","37");
        fieldValues.put("R_RING_LONGITUDE","-122.09686581");
        fieldValues.put("MARKET_ID","SAN FRANCISCO");
        fieldValues.put("RING_ID",ringCode);
        MiscHelpers.generateCsvSingleLineItem(fieldValues,Constants.Ring_IMPORT_DATA);
        ImportFile importFile = new ImportFile("10017335","ring_imports.csv",Constants.Ring_IMPORT_DATA);
        importFile = miscHelperRest.importFileAndGetStatusWithParam(importFile,params);
        AssertionsUtil softAssert = new AssertionsUtil();
        softAssert.assertNotNull(importFile,"File import was successful");
        ringTracker = mainSideMenu.goToRingTracker();
        ringTracker.searchForValue(ringCode, "R:Ring Code");
        addNewRing = ringTracker.selectEditOption();
        softAssert.assertFalse(!(addNewRing.marketFieldLockValidation()),"Market Filed Is Locked");
        softAssert.assertTrue(addNewRing.dateCreatedValidation(),"Date And Time Has Created");
        softAssert.assertTrue(addNewRing.finalBuildSiteFieldValidation(),"Final Build Status Is Auto Generated As No");
        ringTracker = addNewRing.acceptAndGoToSiteTracker();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "verifyIncorrectMarket_Ring",priority = 2)
    public void verifyIncorrectMarket_Ring(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Ring ring = ringHelper.createNewRingWithOutGeoLocation();
        ringTracker = mainSideMenu.goToRingTracker();
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        addNewRing = ringTracker.selectEditOption();
        addNewRing.switchToRingPage();
        addNewRing.addGeoLocation();
        boolean market =  addNewRing.verifyMarketField();
        softAssert.assertTrue(market,"Market value is changed based upon the Latitude and Longitude Values");
        addNewRing.backToTrackerPage();
        softAssert.closeAssert();
    }
}
