package web.mb.users;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import org.testng.annotations.Test;
import pages.web.Tracker.AddRingPage;
import pages.web.Tracker.RingTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class NonSuperUserTests extends BaseTest {

    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    RingTrackerPage ringTracker;
    AddRingPage addNewRing;
    String ringCode = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
    String ringCodeCancel = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();

    public NonSuperUserTests()
    {
        if(envURL == null) {envURL = 	"https://magentabuiltstg.t-mobile.com/Login.do";}
        if(testSuite == null) {testSuite = "sectorSet.xml";}

    }

    @Test(groups = {"Integration","healthCheck"},description = "login",priority = 1)
    public void login_NonSuperUser(Method method) throws Exception {
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
        mainSideMenu = loginPage.LoginAsUser(nonSuper);

    }

    @Test(groups = {"Integration","healthCheck"},description = "createRingAndUpdateStatusToNewFromDead",priority = 4)
    public void createRingAndUpdateStatusToDeadFromNew(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        ringTracker = mainSideMenu.goToRingTracker();
        ringCode="AUWMME5";
        ringTracker.searchForValue(ringCode, "R:Ring Code");
        softAssert.assertTrue(ringTracker.isDataPresentInTable(), "The ring should have been created");
        softAssert.assertTrue(ringTracker.isRingStatus("New Ring"), "The ring should have been created");
        addNewRing = ringTracker.selectEditOption();
        softAssert.assertTrue(!addNewRing.isRingStatusChangeable(), "The ring status should not be editable");
        ringTracker.refresh();
        ringTracker.searchForValue(ringCode, "R:Ring Code");
        softAssert.assertTrue(ringTracker.isDataPresentInTable(), "The ring should be present");
        softAssert.assertTrue(ringTracker.isRingStatus("New Ring"), "The ring should have been moved to NEW state");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration","healthCheck"},description = "createRingAndUpdateStatusToBlank",priority = 4)
    public void createRingAndUpdateStatusToBlank(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        ringTracker = mainSideMenu.goToRingTracker();
        ringCode = "AUWMME5";
        ringTracker.searchForValue(ringCode, "R:Ring Code");
        softAssert.assertTrue(ringTracker.isDataPresentInTable(), "The ring should have been created");
        softAssert.assertTrue(ringTracker.isRingStatus("New Ring"), "The ring should have been created");
        addNewRing = ringTracker.selectEditOption();

        softAssert.assertTrue(!addNewRing.isRingStatusChangeable(), "The ring status should not be editable");
        ringTracker.refresh();
        ringTracker.searchForValue(ringCode, "R:Ring Code");
        softAssert.assertTrue(ringTracker.isDataPresentInTable(), "The ring should be present");
        softAssert.assertTrue(ringTracker.isRingStatus("New Ring"), "The ring should have been moved to NEW state");
        softAssert.closeAssert();
    }
}
