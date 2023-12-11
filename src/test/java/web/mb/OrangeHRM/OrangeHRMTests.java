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
    @Test(groups = {"Integration"},description = "VerifyUserRoleField",priority = 3)
    public void VerifyUserRoleField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        adminOrgHrmPage = mainSideMenu.goToAdminOrgHrm();
        adminOrgHrmPage.UserRoleFiled();
        softAssert.closeAssert();
    }


    @Test(groups = {"Integration"},description ="VerifyEmployeeNameField",priority=4)
    public void VerifyUserRole(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        adminOrgHrmPage = mainSideMenu.goToAdminOrgHrm();
        softAssert.assertTrue(adminOrgHrmPage.isEmployeeNameEnable(), "EmployeeName Field Is Enabled");
    }
    @Test(groups = {"Integration"},description = "VerifyStatusField",priority = 5)
    public void VerifyStatusField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        adminOrgHrmPage = mainSideMenu.goToAdminOrgHrm();
        adminOrgHrmPage.StatusField();
        softAssert.closeAssert();
    }

    @Test(groups =  {"Integration"}, description = "VerifyPimTabFields", priority = 6)
    public void VerifyPimTab() throws Exception
    {
        AssertionsUtil softAssert = new AssertionsUtil();
        adminOrgHrmPage = mainSideMenu.gotoPimOrgHrmPage();
        softAssert.assertTrue(adminOrgHrmPage.VerifyPimTab(), "PIM Tab contains Pim Fields");
        softAssert.assertTrue(adminOrgHrmPage.isEmpName1Enable(), "PIM Tab contains Employee Name");
        softAssert.assertTrue(adminOrgHrmPage.isEmployeeIdEnable(), "PIM Tab contains Employee Id");
        softAssert.assertTrue(adminOrgHrmPage.EmploymentStatus(), "PIM Tab contains Employment Status");
        softAssert.assertTrue(adminOrgHrmPage.Include(), "PIM Tab contains Include");
        softAssert.assertTrue(adminOrgHrmPage.isSupervisorNameEnable(), "PIM Tab contains Supervisor Name");
        softAssert.assertTrue(adminOrgHrmPage.JobTitle(), "PIM Tab contains Job Title");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description ="VerifyTimeTab",priority=7)
    public void VerifyTimeTab(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        adminOrgHrmPage = mainSideMenu.gotoTimeOrgHrmPage();
        softAssert.assertTrue(adminOrgHrmPage.VerifyTimeTab(), "Time Tab contains Time Fields");
    }
    @Test(groups = {"Integration"},description ="VerifyEmployeeName1Field",priority=8)
    public void VerifyEmployeeName1Field(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        adminOrgHrmPage = mainSideMenu.gotoTimeOrgHrmPage();
        softAssert.assertTrue(adminOrgHrmPage.isEmployeename1Enable(), "EmployeeName1 Field Is Enabled");
    }
    @Test(groups = {"Integration"},description ="VerifyViewButton",priority=9)
    public void VerifyViewButton(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        adminOrgHrmPage = mainSideMenu.gotoTimeOrgHrmPage();
        softAssert.assertTrue(adminOrgHrmPage.VerifyView(), "View Button  Is Enabled");
    }


    @Test(groups = {"Integration"},description ="VerifyPimTab1",priority=10)
    public void VerifyPimTab1(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        adminOrgHrmPage = mainSideMenu.gotoPimOrgHrmPage();
        softAssert.assertTrue(adminOrgHrmPage.VerifyPimTab1(), "PimTab is Enable");
    }


    @Test(groups = {"Integration"},description ="VerifyAdminTab1",priority=11)
    public void VerifyAdminTab1(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        adminOrgHrmPage = mainSideMenu.goToAdminOrgHrm();
        softAssert.assertTrue(adminOrgHrmPage.VerifyAdminTab1(), "admin Tab1 is Enable");
    }

    @Test(groups = {"Integration"},description ="VerifyPimTab2",priority=12)
    public void VerifyPimTab2(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        adminOrgHrmPage = mainSideMenu.gotoPimOrgHrmPage();
        softAssert.assertTrue(adminOrgHrmPage.VerifyPimTab2(), "Pim Tab2 is Enable");
    }


    @Test(groups = {"Integration"},description ="VerifyPimTab3",priority=13)
    public void VerifyPimTab3(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        adminOrgHrmPage = mainSideMenu.gotoPimOrgHrmPage();
        softAssert.assertTrue(adminOrgHrmPage.VerifyPimTab3(), "Pim Tab3 is Enable");
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
