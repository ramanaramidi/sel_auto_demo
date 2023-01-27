package web.mb.tracker;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import org.testng.annotations.Test;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import pages.web.reports.RunReportsPage;
import utility.helper.AssertionsUtil;

import java.lang.reflect.Method;

public class ReportVerificationTest extends BaseTest {
    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    RunReportsPage runReportsPage;
    String reportName = "Ring_SW_Full";

    public ReportVerificationTest()
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

    @Test(groups = {"Integration"},description = "verify Report",priority = 2)
    public void verify_TB_Grid_SubmitReport(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        runReportsPage.clickBellIcon();
        String ReportId = runReportsPage.selectReport("TB Grid with Submit");
        softAssert.assertTrue((ReportId)!=null,"Report Id should not be null");
        softAssert.assertTrue(runReportsPage.verifyBtDateAndStatus("Pending","Report","TB Grid with Submit"),"");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Pending"),"Report Status should be Pending");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("In Queue"),"Report status should be In Queue ");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Executed without Errors"),"Report status should be Executed without Errors ");
        softAssert.closeAssert();
    }
    //  @Test(groups = {"Integration"},description = "verify Report",priority = 3)
    public void verify_Ring_SW_DeltaReport(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        runReportsPage.clickBellIcon();
        String ReportId = runReportsPage.selectReport(reportName);
        softAssert.assertTrue((ReportId)!=null,"Report Id should not be null");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Pending"),"Report Status should be Pending");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("In Queue"),"Report status should be In Queue ");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Executed without Errors"),"Report status should be Executed without Errors ");
        softAssert.closeAssert();
    }
    // @Test(groups = {"Integration"},description = "verify Report",priority = 4)
    public void verify_Site_SW_FullReport(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        runReportsPage.clickBellIcon();
        String ReportId = runReportsPage.selectReport(reportName);
        softAssert.assertTrue((ReportId)!=null,"Report Id should not be null");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Pending"),"Report Status should be Pending");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("In Queue"),"Report status should be In Queue ");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Executed without Errors"),"Report status should be Executed without Errors ");
        softAssert.closeAssert();
    }
}
