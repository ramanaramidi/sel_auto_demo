package web.mb.tracker;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.enums.MarketEnum;
import commons.enums.SeedingDataTypeEnum;
import commons.objects.Ring;
import commons.objects.Trackers;
import commons.objects.Users;
import org.testng.annotations.Test;
import pages.web.Tracker.AddRingPage;
import pages.web.Tracker.RingTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.ring.RingHelper;
import testData.DataCollector;
import testData.UserData;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class RingTests extends BaseTest {
    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    RingTrackerPage ringTracker;
    AddRingPage addNewRing;
    RingHelper ringHelper = new RingHelper();
    String ringCode = "XZ" + MiscHelpers.getRandomString(5, true).toUpperCase();
    String ringCodeCancel = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();

    public RingTests()
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
        mainSideMenu = loginPage.LoginAsUser(superUser);

    }
    @Test(groups = {"Integration"},description = "createNewRingTest",priority = 2)
    public void createNewRingTest_Ring(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        ringTracker = mainSideMenu.goToRingTracker();
        addNewRing = ringTracker.selectAddNewRingOption();
        Ring ring = new Ring("New Ring",ringCode);
        ringTracker = addNewRing.addingNewRingTracker(ring);
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        softAssert.assertTrue(ringTracker.isDataPresentInTable(), "The ring should have been created "+ring.ringId);
        softAssert.assertTrue(ringTracker.isRingStatus("New Ring"), "The ring should have been created in new state");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "addNewRingButCancel",priority = 3)
    public void addNewRingButCancel_Ring(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        ringTracker = mainSideMenu.goToRingTracker();
        addNewRing = ringTracker.selectAddNewRingOption();
        Ring ring = new Ring("New Ring",ringCodeCancel);
        ringTracker = addNewRing.addingNewRingAndCancel(ring);
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        softAssert.assertFalse(ringTracker.isDataPresentInTable(), "The ring should not have been created");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "addNewRingWithoutMarket",priority = 3)
    public void addNewRingWithoutMarket_Ring(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        ringTracker = mainSideMenu.goToRingTracker();
        addNewRing = ringTracker.selectAddNewRingOption();
        Ring ring = new Ring("New Ring",ringCodeCancel);
        ringTracker = addNewRing.addingRingWithoutMarket(ring);
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        softAssert.assertFalse(ringTracker.isDataPresentInTable(), "The ring should not have been created");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "moveRingToActiveState",priority = 3)
    public void moveRingToActiveState_Ring(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Ring ring = ringHelper.createNewRing(false);
        System.out.println("trackerID"+ring.ringId);
        ring.ringIdDescription = "Macro";
        ring.ringSubMarket = "Non-BTS Engineering Sites";
        ringTracker = mainSideMenu.goToRingTracker();
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        softAssert.assertTrue(ringTracker.isDataPresentInTable(), "The ring should have been created");
        addNewRing = ringTracker.selectEditOption();
        ringTracker = addNewRing.editRingMandatoryDetails(ring);
        softAssert.assertTrue(ringTracker.checkForPageLoad(),"ring tracker page loaded");
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        softAssert.assertTrue(ringTracker.isRingStatus("Active"), "The ring should be in active state");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "moveRingToActiveState",priority = 4)
    public void searchAndEditRing_Ring(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Ring ring = ringHelper.createNewRing(false);
        System.out.println("trackerID"+ring.ringId);
        ringTracker = mainSideMenu.goToRingTracker();
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        softAssert.assertTrue(ringTracker.isDataPresentInTable(), "The ring should have been created");
        addNewRing = ringTracker.selectEditOption();
        ringTracker = addNewRing.editRingDetails();
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        addNewRing = ringTracker.selectEditOption();
        softAssert.assertTrue(addNewRing.verifyRingDetailsUpdate(superUser),"user should be able to edit the ring and the details should match");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "createRingWithoutSpectrumSpatialCall",priority = 4)
    public void createRingWithoutSpectrumSpatialCall_Ring(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Ring ring = ringHelper.createNewRingWithOutGeoLocation();
        ringTracker = mainSideMenu.goToRingTracker();
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        softAssert.assertTrue(ringTracker.isDataPresentInTable(), "The ring should have been created");
        softAssert.assertTrue(ringTracker.isRingStatus("New Ring"), "The ring should have been created in New State");
        addNewRing = ringTracker.selectEditOption();
        addNewRing.setNoSpectrumCallTo("yes");
        addNewRing.addGeoLocation();
        softAssert.assertFalse(addNewRing.verifyGeoLocationDetailsIsPresent(),"should not be present");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "createRingWithSpectrumSpatialCall",priority = 4)
    public void createRingWithSpectrumSpatialCall_Ring(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Ring ring = ringHelper.createNewRing(true);
        ringTracker = mainSideMenu.goToRingTracker();
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        softAssert.assertTrue(ringTracker.isDataPresentInTable(), "The ring should have been created");
        softAssert.assertTrue(ringTracker.isRingStatus("New Ring"), "The ring should have been created");
        addNewRing = ringTracker.selectEditOption();
        softAssert.assertTrue(addNewRing.verifyDoNotUseSpectrumSpatialAPI(""),"should be null selected");
        softAssert.assertTrue(addNewRing.verifyGeoLocationDetailsIsPresent(),"geo details should be present");
        softAssert.assertTrue(ringTracker.checkForPageLoad(),"ring tracker page loaded");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "createRingAndUpdateStatusToNewFromDeadAsAdmin",priority = 4)
    public void createRingAndUpdateStatusToNewFromDeadAsAdmin_Ring(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();

        ringTracker = mainSideMenu.goToRingTracker();
        Ring ring = ringHelper.createNewRing(false);

        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        softAssert.assertTrue(ringTracker.isDataPresentInTable(), "The ring should have been created");
        softAssert.assertTrue(ringTracker.isRingStatus("New Ring"), "The ring should have been created");
        ring.ringStatus = "Dead";
        ring = ringHelper.updateRingStatus(ring);
        if(ring!=null){
            ringTracker.refresh();
            ringTracker.searchForValue(ring.ringId, "R:Ring Code");
            softAssert.assertTrue(ringTracker.isDataPresentInTable(), "The ring should be present");
            softAssert.assertTrue(ringTracker.isRingStatus("Dead"), "The ring should have been moved to DEAD state");
            ringTracker.selectEditOption();
            ringTracker = addNewRing.updateRingStatus("New Ring");
            ringTracker.refresh();
            ringTracker.searchForValue(ring.ringId, "R:Ring Code");
            softAssert.assertTrue(ringTracker.isDataPresentInTable(), "The ring should be present");
            softAssert.assertTrue(ringTracker.isRingStatus("New Ring"), "The ring should have been moved to NEW state");
        }
        else
            softAssert.assertTrue(false, "LOOKS LIKE THERE IS A DATA CREATION PROBLEM");

        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "createRingAndUpdateStatusToBlankAsAdmin",priority = 4)
    public void createRingAndUpdateStatusToBlankAsAdmin_Ring(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();

        ringTracker = mainSideMenu.goToRingTracker();
        Ring ring = ringHelper.createNewRing(false);
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        softAssert.assertTrue(ringTracker.isDataPresentInTable(), "The ring should have been created");
        softAssert.assertTrue(ringTracker.isRingStatus("New Ring"), "The ring should have been created");
        addNewRing = ringTracker.selectEditOption();
        ringTracker = addNewRing.updateRingStatus("");
        ringTracker.refresh();
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        // softAssert.assertTrue(ringTracker.isRingStatus(""), "The ring should have been created");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "ringCreationWithInitialSpectrumCallChecks",priority = 4)
    public void ringCreationWithInitialSpectrumCallChecks_Ring(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();

        Ring ring = ringHelper.createNewRing(true);
        ringTracker = mainSideMenu.goToRingTracker();
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        softAssert.assertTrue(ringTracker.isDataPresentInTable(), "The ring should have been created");
        softAssert.assertTrue(ringTracker.isRingStatus("New Ring"), "The ring should have been created");
        addNewRing = ringTracker.selectEditOption();
        boolean response = addNewRing.verifySpectrumCallDetails("Submitted Successfully",superUser);
        System.out.println("++"+response);
        softAssert.assertTrue(response,"Spectrum Calls Verified");
        softAssert.assertTrue(addNewRing.verifyGeoLocationDetailsIsPresent(),"should be present");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "createRingAndUpdateStatusToBlankAsAdmin",priority = 4)
    public void SACVendor_Ring(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        ringTracker = mainSideMenu.goToRingTracker();
        Ring ring = ringHelper.createNewRing(false);
        System.out.println("trackerID"+ring.ringId);
        ringTracker.searchForValue(ring.ringId, "R:Ring Code");
        softAssert.assertTrue(ringTracker.isDataPresentInTable(), "The ring should have been created");
        softAssert.assertTrue(ringTracker.isRingStatus("New Ring"), "The ring should have been created");
        addNewRing = ringTracker.selectEditOption();
        String SACVendorDate=addNewRing.EnterSACVendor("HR Manufaturing Company");
        System.out.println(SACVendorDate);
        softAssert.assertTrue(SACVendorDate.equals(MiscHelpers.currentDateTime("MM/dd/yyyy")),"Both Dates are equal");
        softAssert.closeAssert();
    }
}
