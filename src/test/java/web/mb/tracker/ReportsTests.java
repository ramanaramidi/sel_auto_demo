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
    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    ReportSettingsPage reportSettingsPage;
    RunReportsPage runReportsPage;
    String reportName = "Ring_SW_Full";

    public ReportsTests() {
        if (envURL == null) {
            envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");
        }
        if (testSuite == null) {
            testSuite = "TestRunner.xml";
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
        mainSideMenu = loginPage.LoginAsUser(superUser);

    }

    @Test(groups = {"Integration"}, description = "create Report", priority = 2)
    public void generate_TB_Grid_SubmitReport(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "TB Grid with Submit";
        reportSettingsPage = runReportsPage.goToReportSettingsPage(reportName);
        runReportsPage = reportSettingsPage.addNewReport();
        runReportsPage.clickBellIcon();
        String ReportId = runReportsPage.selectReport(reportName);
        softAssert.assertTrue((ReportId) != null, "Report Id should not be null");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Pending"), "Report Status should be Pending");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("In Queue"), "Report status should be In Queue ");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "create Report", priority = 3)
    public void generate_Ring_SW_DeltaReport(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "Ring_SW_Delta";
        reportSettingsPage = runReportsPage.goToReportSettingsPage(reportName);
        runReportsPage = reportSettingsPage.addReport();
        runReportsPage.clickBellIcon();
        String ReportId = runReportsPage.selectReport(reportName);
        softAssert.assertTrue((ReportId) != null, "Report Id should not be null");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Pending"), "Report Status should be Pending");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("In Queue"), "Report status should be In Queue ");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "create Report", priority = 4)
    public void generate_Site_SW_FullReport(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "Ring_SW_Full";
        reportSettingsPage = runReportsPage.goToReportSettingsPage(reportName);
        runReportsPage = reportSettingsPage.addReport();
        runReportsPage.clickBellIcon();
        String ReportId = runReportsPage.selectReport(reportName);
        softAssert.assertTrue((ReportId) != null, "Report Id should not be null");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Pending"), "Report Status should be Pending");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("In Queue"), "Report status should be In Queue ");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "generate_DeploymentReport", priority = 5)
    public void generate_DeploymentReportForCurrentTime(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "Deployment Report";
        reportSettingsPage = runReportsPage.goToReportSettingsPage(reportName);
        runReportsPage = reportSettingsPage.generateReportForCurrentTime();
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String currentDate = runReportsPage.getCurrentDate();
        String response1 = runReportsPage.searchForValueInGrid("Submitted",2);
        System.out.println("Submitted  Date is - " + response1);
        softAssert.assertContains(response1,currentDate,"Report should be generated at current time and Submitted Date");
        String response2 = runReportsPage.searchForValueInGrid("Scheduled",2);
        System.out.println("Scheduled  Date is - " + response2);
        softAssert.assertContains(response2,currentDate,"Report should be generated at current time and Scheduled Date");
        String response3 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response3);
        softAssert.assertContains(response3,"Executed without Errors","Report should be generated at current time and status should be Executed without Errors");
        String response4 = runReportsPage.searchForValueInGrid("Delivery",2);
        System.out.println("Status is - " + response4);
        softAssert.assertContains(response4,"File","Report should be generated by Delivery selected as File");
        // runReportsPage.deleteRecord();
    }
    @Test(groups = {"Integration"}, description = "generate_DeploymentReportForSpecificTime", priority = 6)
    public void generate_DeploymentReportForSpecificTime(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "Deployment Report";
        reportSettingsPage = runReportsPage.goToReportSettingsPage(reportName);
        runReportsPage = reportSettingsPage.generateReportForSpecificTime();
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String response1 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response1);
        softAssert.assertContains(response1,"Pending","Report should be generated at specific time and status should be Pending");
        // runReportsPage.deleteRecord();
    }
    @Test(groups = {"Integration"}, description = "generate_DeploymentReportForMarket", priority = 6)
    public void generate_DeploymentReportForMarket(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "Deployment Report";
        reportSettingsPage = runReportsPage.goToReportSettingsPage(reportName);
        runReportsPage = reportSettingsPage.setMandatoryParameter_Market();

    }
    @Test(groups = {"Integration"}, description = "generate_DeploymentReportForFile", priority = 7)
    public void generate_DeploymentReportForFile(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "Deployment Report";
        reportSettingsPage = runReportsPage.goToReportSettingsPage(reportName);
        runReportsPage = reportSettingsPage.selectDeliveryFile();
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String currentDate = runReportsPage.getCurrentDate();
        String response1 = runReportsPage.searchForValueInGrid("Submitted",2);
        System.out.println("Submitted  Date is - " + response1);
        softAssert.assertContains(response1,currentDate,"Report should be generated at current time and Submitted Date");
        String response2 = runReportsPage.searchForValueInGrid("Scheduled",2);
        System.out.println("Scheduled  Date is - " + response2);
        softAssert.assertContains(response2,currentDate,"Report should be generated at current time and Scheduled Date");
        String response3 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response3);
        softAssert.assertContains(response3,"Executed without Errors","Report should be generated at current time and status should be Executed without Errors");
        String response4 = runReportsPage.searchForValueInGrid("Delivery",2);
        System.out.println("Status is - " + response4);
        softAssert.assertContains(response4,"File","Report should be generated by Delivery selected as File");
        //   runReportsPage.deleteRecord();
    }
    @Test(groups = {"Integration"}, description = "generate_DeploymentReportForEmail", priority = 8)
    public void generate_DeploymentReportForEmail(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "Deployment Report";
        reportSettingsPage = runReportsPage.goToReportSettingsPage(reportName);
        runReportsPage = reportSettingsPage.selectDeliveryEmail();
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String currentDate = runReportsPage.getCurrentDate();
        String response1 = runReportsPage.searchForValueInGrid("Submitted",2);
        System.out.println("Submitted  Date is - " + response1);
        softAssert.assertContains(response1,currentDate,"Report should be generated at current time and Submitted Date");
        String response2 = runReportsPage.searchForValueInGrid("Scheduled",2);
        System.out.println("Scheduled  Date is - " + response2);
        softAssert.assertContains(response2,currentDate,"Report should be generated at current time and Scheduled Date");
        String response3 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response3);
        softAssert.assertContains(response3,"Executed without Errors","Report should be generated at current time and status should be Executed without Errors");
        String response4 = runReportsPage.searchForValueInGrid("Delivery",2);
        System.out.println("Status is - " + response4);
        softAssert.assertContains(response4,"Email with Link","Report should be generated by Delivery selected as File");
        //  runReportsPage.deleteRecord();
    }
    @Test(groups = {"Integration"}, description = "generate_TB_Grid_SubmitReportMandatoryFile", priority = 9)
    public void generate_TB_Grid_SubmitReportMandatoryFile(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "TB Grid with Submit";
        reportSettingsPage = runReportsPage.goToReportSettingsPage(reportName);
        runReportsPage = reportSettingsPage.generateReportDeliveryAsFile();
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String currentDate = runReportsPage.getCurrentDate();
        String response1 = runReportsPage.searchForValueInGrid("Submitted",2);
        System.out.println("Submitted  Date is - " + response1);
        softAssert.assertContains(response1,currentDate,"Report should be generated at current time and Submitted Date");
        String response2 = runReportsPage.searchForValueInGrid("Scheduled",2);
        System.out.println("Scheduled  Date is - " + response2);
        softAssert.assertContains(response2,currentDate,"Report should be generated at current time and Scheduled Date");
        String response3 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response3);
        softAssert.assertContains(response3,"Executed without Errors","Report should be generated at current time and status should be Executed without Errors");
        String response4 = runReportsPage.searchForValueInGrid("Delivery",2);
        System.out.println("Status is - " + response4);
        softAssert.assertContains(response4,"File","Report should be generated by Delivery selected as File");
        //runReportsPage.deleteRecord();
    }
    @Test(groups = {"Integration"}, description = "generate_TB_Grid_SubmitReportMandatoryEmail", priority = 10)
    public void generate_TB_Grid_SubmitReportMandatoryEmail(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "TB Grid with Submit";
        reportSettingsPage = runReportsPage.goToReportSettingsPage(reportName);
        runReportsPage = reportSettingsPage.generateReportDeliveryAsEmail();
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String currentDate = runReportsPage.getCurrentDate();
        String response1 = runReportsPage.searchForValueInGrid("Submitted",2);
        System.out.println("Submitted  Date is - " + response1);
        softAssert.assertContains(response1,currentDate,"Report should be generated at current time and Submitted Date");
        String response2 = runReportsPage.searchForValueInGrid("Scheduled",2);
        System.out.println("Scheduled  Date is - " + response2);
        softAssert.assertContains(response2,currentDate,"Report should be generated at current time and Scheduled Date");
        String response3 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response3);
        softAssert.assertContains(response3,"Executed without Errors","Report should be generated at current time and status should be Executed without Errors");
        String response4 = runReportsPage.searchForValueInGrid("Delivery",2);
        System.out.println("Status is - " + response4);
        softAssert.assertContains(response4,"Email","Report should be generated by Delivery selected as Email");
        // runReportsPage.deleteRecord();
    }
    @Test(groups = {"Integration"}, description = "generate_TB_Grid_SubmitReportMandatoryEmailWithLink", priority = 11)
    public void generate_TB_Grid_SubmitReportMandatoryEmailWithLink(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "TB Grid with Submit";
        reportSettingsPage = runReportsPage.goToReportSettingsPage(reportName);
        runReportsPage = reportSettingsPage.generateReportDeliveryAsEmailWithLink();
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String currentDate = runReportsPage.getCurrentDate();
        String response1 = runReportsPage.searchForValueInGrid("Submitted",2);
        System.out.println("Submitted  Date is - " + response1);
        softAssert.assertContains(response1,currentDate,"Report should be generated at current time and Submitted Date");
        String response2 = runReportsPage.searchForValueInGrid("Scheduled",2);
        System.out.println("Scheduled  Date is - " + response2);
        softAssert.assertContains(response2,currentDate,"Report should be generated at current time and Scheduled Date");
        String response3 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response3);
        softAssert.assertContains(response3,"Executed without Errors","Report should be generated at current time and status should be Executed without Errors");
        String response4 = runReportsPage.searchForValueInGrid("Delivery",2);
        System.out.println("Status is - " + response4);
        softAssert.assertContains(response4,"Email with Link","Report should be generated by Delivery selected as Email with Link ");
        // runReportsPage.deleteRecord();
    }
    @Test(groups = {"Integration"}, description = "generate_TB_Grid_SubmitReportForSpecificTime", priority = 13)
    public void generate_TB_Grid_SubmitReportForSpecificTime(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "TB Grid with Submit";
        reportSettingsPage = runReportsPage.goToReportSettingsPage(reportName);
        runReportsPage = reportSettingsPage.generateReportForSpecificTime();
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String response1 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response1);
        softAssert.assertContains(response1,"Pending","Report should be generated at specific time and status should be Pending");
        // runReportsPage.deleteRecord();
    }
    @Test(groups = {"Integration"}, description = "generate_TB_Grid_SubmitReportForCurrentTime", priority = 14)
    public void generate_TB_Grid_SubmitReportForCurrentTime(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "TB Grid with Submit";
        reportSettingsPage = runReportsPage.goToReportSettingsPage(reportName);
        runReportsPage = reportSettingsPage.generateReportForCurrentTime();
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String response1 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response1);
        softAssert.assertContains(response1,"Executed without Errors","Report should be generated at current time");
        // runReportsPage.deleteRecord();
    }
}