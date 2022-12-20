package web.mb.tracker;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import org.testng.annotations.Test;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import pages.web.reports.ReportSettingsPage;
import pages.web.reports.RunReportsPage;
import utility.helper.AssertionsUtil;

import java.lang.reflect.Method;

import static javax.print.attribute.Size2DSyntax.MM;
import static javax.swing.text.html.HTML.Tag.DD;


public class ReportsTests extends BaseTest {
    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    ReportSettingsPage reportSettingsPage;
    RunReportsPage runReportsPage;
    String reportName = "Ring_SW_Full";

    public ReportsTests()
    {
        if(envURL == null) {envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");}
        if(testSuite == null) {testSuite = 	"TestRunner.xml";}
    }

    @Test(groups = {"Integration"},description = "login",priority = 1)
    public void login(Method method) throws Exception {
        loginPage = new LoginPage(driver);
        loginPage.doLogin(LoginOptionEnum.SAML);
        String url = loginPage.getLoginUrl(alphaUser);
        if(url!=null){
            loginPage.launchUrl(url);
        }
        mainSideMenu = loginPage.LoginAsUser(superUser);
    }

    @Test(groups = {"Integration"},description = "create Report",priority = 2)
    public void generate_TB_Grid_SubmitReport(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "TB Grid with Submit";
        reportSettingsPage = runReportsPage.goToReportSettingsPage(reportName);
        runReportsPage = reportSettingsPage.addNewReport();
        runReportsPage.clickBellIcon();
        String ReportId = runReportsPage.selectReport(reportName);
        softAssert.assertTrue((ReportId)!=null,"Report Id should not be null");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Pending"),"Report Status should be Pending");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("In Queue"),"Report status should be In Queue ");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "create Report",priority = 3)
    public void generate_Ring_SW_DeltaReport(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "Ring_SW_Delta";
        reportSettingsPage = runReportsPage.goToReportSettingsPage(reportName);
        runReportsPage = reportSettingsPage.addReport();
        runReportsPage.clickBellIcon();
        String ReportId = runReportsPage.selectReport(reportName);
        softAssert.assertTrue((ReportId)!=null,"Report Id should not be null");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Pending"),"Report Status should be Pending");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("In Queue"),"Report status should be In Queue ");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "create Report",priority = 4)
    public void generate_Site_SW_FullReport(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "Ring_SW_Full";
        reportSettingsPage = runReportsPage.goToReportSettingsPage(reportName);
        runReportsPage = reportSettingsPage.addReport();
        runReportsPage.clickBellIcon();
        String ReportId = runReportsPage.selectReport(reportName);
        softAssert.assertTrue((ReportId)!=null,"Report Id should not be null");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Pending"),"Report Status should be Pending");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("In Queue"),"Report status should be In Queue ");
        softAssert.closeAssert();
    }
}