package web.mb.OrangeHRM;

import common.BaseTest;
import commons.objects.Ring;
import org.testng.annotations.Test;
import pages.web.OrangeHRMPages.AdminOrgHrmPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;

import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class OrangeHRMTests extends BaseTest {

    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;


    AdminOrgHrmPage adminOrgHrmPage;
    String ringCode = "XZ" + MiscHelpers.getRandomString(5, true).toUpperCase();
    String ringCodeCancel = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
/*
    public OrangeHRMTests()
    {
        if(envURL == null) {envConfig.setWebUrl("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");}
        // if(testSuite == null) {testSuite = "sectorSet.xml";}
    }

 */

    @Test(groups = {"Integration"},description = "login",priority = 1)
    public void login_Ring(Method method) throws Exception {
        loginPage = new LoginPage(driver);
        if(alphaUser.getIsServiceAccount().equals("true")){
            // loginPage.doLogin(LoginOptionEnum.UN_EMAIL);
            loginPage.logInOrgHrm(alphaUser);
        }
        else{
            //  loginPage.doLogin(LoginOptionEnum.SAML);
            String url = loginPage.getLoginUrl(alphaUser);
            if(url!=null){
                loginPage.launchUrl(url);
            }
        }
        mainSideMenu = loginPage.LoginOrgHrm();

    }
    @Test(groups = {"Integration"},description = "VerifyUserNameField",priority = 2)
    public void VerifyUserNameField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        adminOrgHrmPage = mainSideMenu.goToAdminOrgHrm();
        softAssert.assertTrue(adminOrgHrmPage.isUserNameEnable(), "Username field Is Enabled");
        softAssert.closeAssert();
    }


/*
    @Test(groups = {"Integration"},description = "VerifyUserNameField",priority = 2)
    public void VerifyUserRoleField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        adminOrgHrmPage = mainSideMenu.goToAdminOrgHrm();
        adminOrgHrmPage.UserRoleFiled();
        softAssert.closeAssert();
    }

 */



}
