package web.mb.tracker;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import org.testng.annotations.Test;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import pages.web.reports.ReportSettingsPage;
import pages.web.reports.RunReportsPage;
import rest.misc.MiscHelper;
import utility.helper.AssertionsUtil;

import java.lang.reflect.Method;
import java.util.LinkedList;

public class ReportVerificationTest extends BaseTest {
    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    RunReportsPage runReportsPage;
    ReportSettingsPage reportSettingsPage;
    MiscHelper miscHelper = new MiscHelper();
    String tbReportId = "1002971";
    LinkedList<String> processListTbGrid;
    int tbProcessCount;
    // String reportName = "Ring_SW_Full";

    public ReportVerificationTest()
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
        //  generateData();
        mainSideMenu = loginPage.LoginAsUser(superUser);
    }

    public void generateData(){
        processListTbGrid = miscHelper.getReportProcessIds(tbReportId);
        tbProcessCount = processListTbGrid.size();

    }

    @Test(groups = {"Integration"},description = "verify Report",priority = 2)
    public void verify_TB_Grid_SubmitReport(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        runReportsPage.clickBellIcon();
        String reportName = "TB Grid with Submit";
        String ReportId = runReportsPage.selectReport(reportName);
        softAssert.assertTrue((ReportId)!=null,"Report Id should not be null");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Pending"),"Report Status should be Pending");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("In Queue"),"Report status should be In Queue ");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Executed without Errors"),"Report status should be Executed without Errors ");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "verify Report",priority = 3)
    public void verify_Ring_SW_DeltaReport(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        runReportsPage.clickBellIcon();
        String reportName = "Ring_SW_Delta";
        String ReportId = runReportsPage.selectReport(reportName);
        softAssert.assertTrue((ReportId)!=null,"Report Id should not be null");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Pending"),"Report Status should be Pending");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("In Queue"),"Report status should be In Queue ");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Executed without Errors"),"Report status should be Executed without Errors ");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "verify Report",priority = 4)
    public void verify_Site_SW_FullReport(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "Ring_SW_Full";
        runReportsPage.clickBellIcon();
        String ReportId = runReportsPage.selectReport(reportName);
        softAssert.assertTrue((ReportId)!=null,"Report Id should not be null");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Pending"),"Report Status should be Pending");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("In Queue"),"Report status should be In Queue ");
        softAssert.assertTrue(runReportsPage.verifyReportStatus("Executed without Errors"),"Report status should be Executed without Errors ");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "generate_DeploymentReportForFile", priority = 5)
    public void generate_DeploymentReportForEmail(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "Deployment Report";
        reportSettingsPage = runReportsPage.searchForReport(reportName);
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String parentWindow = runReportsPage.switchToProjectPage();
        runReportsPage.clickDescending();
        String response3 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response3);
        softAssert.assertContains(response3,"Executed without Errors","Report should be generated at current time and status should be Executed without Errors");
        String response4 = runReportsPage.searchForValueInGrid("Delivery",2);
        System.out.println("Delivery is - " + response4);
        softAssert.assertContains(response4,"Email","Report should be generated by Delivery selected as Email");
        runReportsPage.deleteRecord();
        runReportsPage.switchToRunReportpage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "generate_DeploymentReportForFile", priority =6)
    public void generate_DeploymentReportForFile(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "Deployment Report";
        reportSettingsPage = runReportsPage.searchForReport(reportName);
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String parentWindow = runReportsPage.switchToProjectPage();
        runReportsPage.clickDescending();
        String response3 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response3);
        softAssert.assertContains(response3,"Executed without Errors","Report should be generated at current time and status should be Executed without Errors");
        String response4 = runReportsPage.searchForValueInGrid("Delivery",2);
        System.out.println("Status is - " + response4);
        softAssert.assertContains(response4,"File","Report should be generated by Delivery selected as File");
        runReportsPage.deleteRecord();
        runReportsPage.switchToRunReportpage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "generate_DeploymentReport", priority = 7)
    public void generate_DeploymentReportForSpecificTime(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "Deployment Report";
        reportSettingsPage = runReportsPage.searchForReport(reportName);
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String parentWindow = runReportsPage.switchToProjectPage();
        runReportsPage.clickDescending();
        String response1 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response1);
        softAssert.assertContains(response1,"Pending","Report should be generated at specific time and status should be Pending");
        runReportsPage.deleteRecord();
        runReportsPage.switchToRunReportpage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "generate_DeploymentReport", priority = 8)
    public void generate_DeploymentReportForCurrentTime(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "Deployment Report";
//        String processId = processListTbGrid.get(tbProcessCount-1);
//        System.out.println("___ID"+processId);
        reportSettingsPage = runReportsPage.searchForReport(reportName);
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String parentWindow = runReportsPage.switchToProjectPage();
        runReportsPage.clickDescending();
        String ProcessID = runReportsPage.searchForValueInGrid("Process ID",2);
        System.out.println("Process ID is - " + ProcessID);
        String response4 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response4);
        softAssert.assertContains(response4,"Executed without Errors","Report should be generated at current time and status should be Executed without Errors");
        runReportsPage.deleteRecord();
        runReportsPage.switchToRunReportpage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "verify Report",priority =9)
    public void verify_TB_Grid_SubmitReportMandatoryEmailWithLink(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "TB Grid with Submit";
        reportSettingsPage = runReportsPage.searchForReport(reportName);
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String parentWindow = runReportsPage.switchToProjectPage();
        runReportsPage.clickDescending();
        String response3 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response3);
        softAssert.assertContains(response3,"Executed without Errors","Report should be generated at current time and status should be Executed without Errors");
        runReportsPage.deleteRecord();
        runReportsPage.switchToRunReportpage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "verify Report",priority = 10)
    public void verify_TB_Grid_SubmitReportMandatoryEmail(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "TB Grid with Submit";
        reportSettingsPage = runReportsPage.searchForReport(reportName);
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String parentWindow = runReportsPage.switchToProjectPage();
        runReportsPage.clickDescending();
        String response3 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response3);
        softAssert.assertContains(response3,"Executed without Errors","Report should be generated at current time and status should be Executed without Errors");
        runReportsPage.deleteRecord();
        runReportsPage.switchToRunReportpage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "verify Report",priority = 11)
    public void verify_TB_Grid_SubmitReportMandatoryFile(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "TB Grid with Submit";
        reportSettingsPage = runReportsPage.searchForReport(reportName);
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String parentWindow = runReportsPage.switchToProjectPage();
        runReportsPage.clickDescending();
        String response3 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response3);
        softAssert.assertContains(response3,"Executed without Errors","Report should be generated at current time and status should be Executed without Errors");
        runReportsPage.deleteRecord();
        runReportsPage.switchToRunReportpage(parentWindow);
        softAssert.closeAssert();
    }


    @Test(groups = {"Integration"},description = "verify Report",priority = 12)
    public void verify_TB_Grid_SubmitReportForSpecificTime(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "TB Grid with Submit";
        reportSettingsPage = runReportsPage.searchForReport(reportName);
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String parentWindow = runReportsPage.switchToProjectPage();
        runReportsPage.clickDescending();
        String response3 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response3);
        softAssert.assertContains(response3,"Pending","Report should be generated at current time and status should be Pending");
        runReportsPage.deleteRecord();
        runReportsPage.switchToRunReportpage(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "verify Report",priority = 13)
    public void verify_TB_Grid_SubmitReportForCurrentTime(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReports();
        String reportName = "TB Grid with Submit";
        reportSettingsPage = runReportsPage.searchForReport(reportName);
        runReportsPage = reportSettingsPage.verifyRunHistory(reportName);
        String parentWindow = runReportsPage.switchToProjectPage();
        runReportsPage.clickDescending();
        String response3 = runReportsPage.searchForValueInGrid("Status",2);
        System.out.println("Status is - " + response3);
        softAssert.assertContains(response3,"Executed without Errors","Report should be generated at current time and status should be Executed without Errors");
        runReportsPage.deleteRecord();
        runReportsPage.switchToRunReportpage(parentWindow);
        softAssert.closeAssert();
    }
}
