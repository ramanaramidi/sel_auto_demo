package web.mb.users;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Project;
import commons.objects.Users;
import org.testng.annotations.Test;
import pages.web.Tracker.ProjectTrackerPage;
import pages.web.Tracker.RingTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import testData.UserData;
import utility.helper.AssertionsUtil;

import java.lang.reflect.Method;

public class SdmUserTests extends BaseTest {

    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    ProjectTrackerPage projectTrackerPage;
    RingTrackerPage ringTrackerPage;
    Project PROJECT_NEW;
    Users Dan_SDM = new Users();
    Users ATC_AGonzal = new Users();
    Users SBA_AMaldonado = new Users();

    public SdmUserTests()
    {
        if(envURL == null) {envURL = 	"https://magentabuiltstg.t-mobile.com/Login.do";}
        if(testSuite == null) {testSuite = 	"TestRunner.xml";}
        Dan_SDM = UserData.getSdm_CrownCastleUserDetail(Dan_SDM);
        ATC_AGonzal = UserData.getAtcUserDetail(ATC_AGonzal);
        SBA_AMaldonado = UserData.getSBAUserDetail(SBA_AMaldonado);
    }

    @Test(groups = {"Integration"}, description = "login", priority = 1)
    public void login_Sdm(Method method) throws Exception {
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
        mainSideMenu = loginPage.LoginAsUser(Dan_SDM);

    }


    @Test(groups = {"Integration"},description = "User should able to see only Project Tracker and import Data",priority = 2)
    public void sdmVendorTest(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        // mainSideMenu = loginPage.LoginAsUser(Dan_SDM);
        projectTrackerPage =  mainSideMenu.navigateToSDMVendor();
        softAssert.assertFalse(projectTrackerPage.buttonDisplayed(),"In Project Tracker SDM Vendor shouldn't see Add Button");
        mainSideMenu.clickMainLogo();
        softAssert.assertTrue(mainSideMenu.isProjectTrackerPresent(),"Project Tracker should be displayed");
        softAssert.assertTrue(mainSideMenu.importDataPresent(),"import Data should be displayed");
        softAssert.assertFalse(mainSideMenu.isRingTrackerPresent(),"Ring Tracker should not be displayed");
        softAssert.assertFalse(mainSideMenu.isSiteTrackerPresent(),"Site Tracker should not be displayed");
        softAssert.assertFalse(mainSideMenu.isPORTrackerPresent(),"POR Tracker should not be displayed");
        mainSideMenu.clickMainLogo();
        mainSideMenu.userLogoff();
        mainSideMenu.userLogin();
        softAssert.closeAssert();
    }

   @Test(groups = {"Integration"},description = "User should able to see only Project Tracker and import Data",priority = 3)
    public void ATCVendorTest(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        mainSideMenu = loginPage.LoginAsUser(ATC_AGonzal);
        sleepFor(20);
        projectTrackerPage =  mainSideMenu.navigateToSDMVendor();
        softAssert.assertFalse(projectTrackerPage.buttonDisplayed(),"In Project Tracker ATC Vendor shouldn't see Add Button");
        mainSideMenu.clickMainLogo();
        softAssert.assertFalse(mainSideMenu.isRingTrackerPresent(),"Ring Tracker should not be displayed");
        softAssert.assertFalse(mainSideMenu.isSiteTrackerPresent(),"Site Tracker should not be displayed");
        softAssert.assertFalse(mainSideMenu.isPORTrackerPresent(),"POR Tracker should not be displayed");
        softAssert.assertTrue(mainSideMenu.isProjectTrackerPresent(),"Project Tracker should be displayed");
        softAssert.assertFalse(mainSideMenu.importDataPresent(),"import Data should not be displayed");
        softAssert.closeAssert();
        mainSideMenu.clickMainLogo();
        mainSideMenu.userLogoff();
        mainSideMenu.userLogin();
    }

     @Test(groups = {"Integration"},description = "User should able to see only Project Tracker and import Data",priority = 4)
    public void SBAVendorTest(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        mainSideMenu = loginPage.LoginAsUser(SBA_AMaldonado);
        sleepFor(20);
        projectTrackerPage =  mainSideMenu.navigateToSDMVendor();
        softAssert.assertFalse(projectTrackerPage.buttonDisplayed(),"In Project Tracker SBA Vendor shouldn't see Add Button");
        mainSideMenu.clickMainLogo();
        softAssert.assertFalse(mainSideMenu.isRingTrackerPresent(),"Ring Tracker should not be displayed");
        softAssert.assertFalse(mainSideMenu.isSiteTrackerPresent(),"Site Tracker should not be displayed");
        softAssert.assertFalse(mainSideMenu.isPORTrackerPresent(),"POR Tracker should not be displayed");
        softAssert.assertTrue(mainSideMenu.isProjectTrackerPresent(),"Project Tracker should be displayed");
        softAssert.assertFalse(mainSideMenu.importDataPresent(),"import Data should not be displayed");
        softAssert.closeAssert();
        mainSideMenu.clickMainLogo();
        mainSideMenu.userLogoff();
        mainSideMenu.userLogin();
    }
}
