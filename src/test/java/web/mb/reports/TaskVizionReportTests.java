package web.mb.reports;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import org.testng.annotations.Test;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import pages.web.reports.RunReportsPage;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class TaskVizionReportTests extends BaseTest {

    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    RunReportsPage runReportsPage;

    public TaskVizionReportTests() {
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
    @Test(groups = {"Integration"}, description = "generate the report without mandatory fields", priority = 2)
    public void generateReportWthoutMandatoryFields() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReportTracker();
        runReportsPage.searchForValue(" TaskVizion Report", "Report Name");
        runReportsPage.goToTaskVizionReportsPage();
        runReportsPage.removeMandatoryFields();
        runReportsPage.goToHistoryField();
        softAssert.assertFalse(runReportsPage.isDataPresentInTable(),"Report Is Not Created");
        runReportsPage.backToRunReportpage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "generation of taskvizion report with mandatory fields", priority = 3)
    public void generatedReportWithMandatoryFields() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReportTracker();
        runReportsPage.searchForValue("TaskVizion Report", "Report Name");
        runReportsPage.goToTaskVizionReportsPage();
        runReportsPage.goToRunReportPage();
        runReportsPage.goToHistoryField();
        String currentDate = MiscHelpers.currentDateTime("MM/dd/yyyy");
        String submittedDate = runReportsPage.searchForValueInGrid("Submitted",2);
        String scheduledDate = runReportsPage.searchForValueInGrid("Scheduled",2);
        softAssert.assertContains(submittedDate,currentDate,"Current Date And Submitted Date Both Are Same");
        softAssert.assertContains(scheduledDate,currentDate,"Current date And Finished Date Both Are Same");
        String actualFormat = runReportsPage.searchForValueInGrid("Format",2);
        String expectedFormat = "Excel with VBA submit";
        softAssert.assertContains(actualFormat,expectedFormat,"Report Has Generated In Excel Format");
        String actualStatus = runReportsPage.searchForValueInGrid("Status",2);
        String expectedStatus = "Executed without Errors";
        softAssert.assertContains(actualStatus,expectedStatus,"Report Has Executed Without Errors");
        runReportsPage.deleteReport();
        runReportsPage.backToRunReportpage();
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "generation of taskvizion report with mandatory and optional fields", priority = 4)
    public void generatedReportWithMandatoryAndOptionalFields() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReportTracker();
        runReportsPage.searchForValue(" TaskVizion Report", "Report Name");
        runReportsPage.goToTaskVizionReportsPage();
        runReportsPage.addOptionalDetails();
        runReportsPage.goToRunReportPage();
        runReportsPage.goToHistoryField();
        String currentDate = MiscHelpers.currentDateTime("MM/dd/yyyy");
        String submittedDate = runReportsPage.searchForValueInGrid("Submitted",2);
        String scheduledDate = runReportsPage.searchForValueInGrid("Scheduled",2);
        softAssert.assertContains(submittedDate,currentDate,"Current Date And Submitted Date Both Are Same");
        softAssert.assertContains(scheduledDate,currentDate,"Current date And Finished Date Both Are Same");
        String actualFormat = runReportsPage.searchForValueInGrid("Format",2);
        String expectedFormat = "Excel with VBA submit";
        softAssert.assertContains(actualFormat,expectedFormat,"Report Has Generated In Excel Format");
        String actualStatus = runReportsPage.searchForValueInGrid("Status",2);
        String expectedStatus = "Executed without Errors";
        softAssert.assertContains(actualStatus,expectedStatus,"Report Has Executed Without Errors");
        runReportsPage.deleteReport();
        runReportsPage.backToRunReportpage();
        runReportsPage.goToTaskVizionReportsPage();
        runReportsPage.clearOptionalDetails();
        runReportsPage.goToRunReportPage();
        runReportsPage.goToHistoryField();
        runReportsPage.deleteReport();
        runReportsPage.backToRunReportpage();
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "verification of project id based on market ", priority = 5)
    public void projectIdVerificationBasedOnMarket() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReportTracker();
        runReportsPage.searchForValue(" TaskVizion Report", "Report Name");
        runReportsPage.goToTaskVizionReportsPage();
        runReportsPage.setMarketForinitialProjectId();
        int projectid1 = runReportsPage.getProjectIDCount();
        runReportsPage.setMarketForFinalProjectId();
        int projectid2 = runReportsPage.getFinalProjectIDCount();
        softAssert.assertNotEquals(projectid1,projectid2,"Markets contains different project id's ");
        runReportsPage.backToParentWindow();
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "generate the Report On ScheduleTime", priority = 6)
    public void generateReportOnScheduleTime() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReportTracker();
        runReportsPage.searchForValue(" TaskVizion Report", "Report Name");
        runReportsPage.goToTaskVizionReportsPage();
        runReportsPage.scheduleRunTime();
        runReportsPage.goToHistoryField();
        String actual = runReportsPage.searchForValueInGrid("Status",2);
        String expected = "Pending";
        softAssert.assertContains(actual,expected,"Report Is Not generated Immediately");
        runReportsPage.deleteReport();
        runReportsPage.backToRunReportpage();
        softAssert.closeAssert();

    }


    @Test(groups = {"Integration"}, description = "generate the report ans verify finished date", priority = 7)
    public void generatedReportVerifyFinishedDate() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReportTracker();
        runReportsPage.searchForValue("TaskVizion Report", "Report Name");
        runReportsPage.goToTaskVizionReportsPage();
        runReportsPage.goToRunReportPage();
        runReportsPage.goToHistoryField();
        String currentDate = MiscHelpers.currentDateTime("MM/dd/yyyy");
        String finishedDate = runReportsPage.searchForValueInGrid("Finished", 2);
        softAssert.assertContains(finishedDate, currentDate, "Current date And Finished Date Both Are Same");
        runReportsPage.deleteReport();
        runReportsPage.backToRunReportpage();
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Generate the report and verify the link which can download the excel report", priority = 8)
    public void generateReportVerifyExcelLink() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        runReportsPage = mainSideMenu.goToRunReportTracker();
        runReportsPage.searchForValue(" TaskVizion Report", "Report Name");
        runReportsPage.goToTaskVizionReportsPage();
        runReportsPage.changeDeliveryType();
        runReportsPage.goToRunReportPage();
        runReportsPage.goToHistoryField();
        softAssert.assertNotNull(runReportsPage.searchForValueInGrid("Output File Name",2),"Link is available to download the excel report");
        runReportsPage.deleteReport();
        runReportsPage.backToRunReportpage();
        runReportsPage.goToTaskVizionReportsPage();
        runReportsPage.changeDeliveryType();
        runReportsPage.goToRunReportPage();
        runReportsPage.goToHistoryField();
        runReportsPage.deleteReport();
        runReportsPage.backToRunReportpage();
        softAssert.closeAssert();
    }
}
