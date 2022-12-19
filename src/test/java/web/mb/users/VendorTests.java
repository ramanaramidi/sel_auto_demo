package web.mb.users;

import common.BaseTest;
import commons.constants.Constants;
import commons.enums.LoginOptionEnum;
import commons.objects.*;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.web.Tracker.ProjectTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.project.ProjectHelper;
import testData.UserData;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class VendorTests extends BaseTest {
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    ProjectTrackerPage projectTrackerPage;
    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    public EnvConfig envConfig = new EnvConfig();
    Project PROJECT_ACTIVE;
    Users ATC_MViscon = new Users();
    ProjectHelper projectHelper = new ProjectHelper();

    public VendorTests() {
        if (envURL == null) {
            envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");
        }
        if (testSuite == null) {
            testSuite = "TestRunner.xml";
        }
        ATC_MViscon = UserData.getAtcUserDetails(ATC_MViscon);
    }


    @Test(groups = {"Integration"}, description = "login", priority = 1)
    public void login(Method method) throws Exception {
        loginPage = new LoginPage(driver);
        loginPage.doLogin(LoginOptionEnum.SAML);
        String url = loginPage.getLoginUrl(alphaUser);
        if (url != null) {
            loginPage.launchUrl(url);
        }
        generateTestData();
        mainSideMenu = loginPage.LoginAsUser(nonSuper);
    }
    private void generateTestData() {
        String ringIdActiveProjectBundleSingle = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActiveProjectBundleSingle = new Ring("Active", ringIdActiveProjectBundleSingle, "Indoor Node");
        Site siteActiveProjectBundleSingle = new Site(ringIdActiveProjectBundleSingle, "Primary", "Active Site");
        Por porDataBundleSingle = new Por("5GmmW_Stage 2_Site Mod", ringIdActiveProjectBundleSingle);
        PROJECT_ACTIVE = projectHelper.getActiveProject(false, ringActiveProjectBundleSingle, siteActiveProjectBundleSingle, porDataBundleSingle);

        System.out.println("Project __" + PROJECT_ACTIVE.projectId);
        projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(), "PJ_3800_PRE_NTP", Constants.IMAGE_FILE_UPLOAD, "simpleImage.png");
    }
    @Test(groups = {"Integration"}, description = "Assigning Vendor To A Project Vendor User", priority = 2)
    public void assigningVendorNonSuperUser() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parent = projectTrackerPage.switchToChildWindows();
        projectTrackerPage.fullScreen();
        softAssert.assertFalse(!(projectTrackerPage.verifyVendorIsEnabled()),"Vendor Field is Disabled");
        //softAssert.assertTrue(projectTrackerPage.isPopALert(parent),"Assigning Vendor Pop-up Verified Successfully");
        projectTrackerPage.vendorCancel();
        projectTrackerPage.switchToSpecificWindow(parent);
        softAssert.closeAssert();
        mainSideMenu.userLogoff();
        mainSideMenu.userLogin();
    }
    @Test(groups = {"Integration"}, description = "Assigning Vendor To A Project Super User", priority = 3)
    public void assigningVendorSuperUser() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        mainSideMenu = loginPage.LoginAsUser(superUser);
        sleepFor(5);
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parent = projectTrackerPage.switchToChildWindows();
        projectTrackerPage.fullScreen();
        projectTrackerPage.selectVendor("American Tower MB");
        softAssert.assertFalse(projectTrackerPage.isPopALert(parent),"Assigning Vendor Successfully Done");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Validate Vendor Info Tab", priority = 4)
    public void docUploadSuperUser() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_1075_PRELM_RFDS", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"File Upload");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_1200_COLO_APP_SUB", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"File Upload");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_1225_COLO_APP_FILE", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"File Upload");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_1400_STRUCTURAL_COM", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"File Upload");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_1550_MNT_ANLSIS_RECD", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"File Upload");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2025_AMEND1", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"File Upload");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Update Status of the Uploaded Documents", priority = 5)
    public void updateStatus() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parent = projectTrackerPage.switchToChildWindows();
        projectTrackerPage.fullScreen();
        projectTrackerPage.gotoVendorTab();
        projectTrackerPage.changeStatus("PJ:Structural Complete [Status]","Approved",1);
        projectTrackerPage.changeStatus("PJ:Mount Analysis Received [Status]","Approved",0);
        softAssert.assertTrue(projectTrackerPage.getStatusValue("PJ:Mount Analysis Received [Status]",0).equals("Approved"),"Check Approval Status");
        softAssert.assertTrue(projectTrackerPage.getStatusValue("PJ:Structural Complete [Status]",1).equals("Approved"),"Check Approval Status");
        projectTrackerPage.viewOk();
        projectTrackerPage.switchToSpecificWindow(parent);
        softAssert.closeAssert();
        mainSideMenu.userLogoff();
        mainSideMenu.userLogin();
    }
    @Test(groups = {"Integration"}, description = "Upload the Required Documents", priority = 6)
    public void docUploadVendor() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        mainSideMenu = loginPage.LoginAsUser(ATC_MViscon);
        sleepFor(5);
        projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_1000_SITE_SCOPE_CPT", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png");
        projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_1050_TITLE_APPROVED", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png");
        projectTrackerPage = mainSideMenu.goToVendorProjectTracker();
        projectTrackerPage.goToView("Vendor Doc Upload");
        projectTrackerPage.searchForValueVendor(PROJECT_ACTIVE.projectId, "PJ:Project ID");
        softAssert.assertTrue(projectTrackerPage.searchForImgInGrid("PJ:Site Scope Complete (1000) [Doc]","PJ:Project ID",PROJECT_ACTIVE.projectId).contains("thumbnail"),"Check File Upload");
        softAssert.assertTrue(projectTrackerPage.searchForImgInGrid("PJ:Title Approved (1050) [Doc]","PJ:Project ID",PROJECT_ACTIVE.projectId).contains("thumbnail"),"Check File Upload");
        //projectTrackerPage.goToView("General Info");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Re-Upload of Revised Document", priority = 7)
    public void editDocVendor() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_1000_SITE_SCOPE_CPT", Constants.EXCEL_FILE_UPLOAD,"sample.xlsx"),"File Upload");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_1050_TITLE_APPROVED", Constants.EXCEL_FILE_UPLOAD,"sample.xlsx"),"File Upload");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Review Status Of the Documents Vendor User", priority = 8)
    public void reviewStatusVendorUser() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToVendorProjectTracker();
        //projectTrackerPage.goToView("Vendor Doc Upload");
        projectTrackerPage.searchForValueVendor(PROJECT_ACTIVE.projectId, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parent = projectTrackerPage.switchToChildWindows();
        projectTrackerPage.fullScreen();
        projectTrackerPage.gotoVendorTab();
        projectTrackerPage.changeStatus("PJ:Site Scope Complete [Status]","Approved",0);
        softAssert.assertTrue(projectTrackerPage.isPopALert(parent),"Update Status Pop-up Verified Successfully");
        projectTrackerPage.switchToSpecificWindow(parent);
        projectTrackerPage.goToView("General Info");
        softAssert.closeAssert();
        mainSideMenu.userLogoff();
        mainSideMenu.userLogin();
    }
    @Test(groups = {"Integration"}, description = "Review Status Of the Documents Super User", priority = 9)
    public void reviewStatusFromGridSuperUser() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        mainSideMenu = loginPage.LoginAsUser(superUser);
        sleepFor(5);
        projectTrackerPage = mainSideMenu.goToVendorProjectTracker();
        projectTrackerPage.goToView("Vendor Doc Upload");
        projectTrackerPage.searchForValueVendor(PROJECT_ACTIVE.projectId, "PJ:Project ID");
        projectTrackerPage.modifyStatusFromGrid("Approved",2);
        String response = projectTrackerPage.searchForValueInGrid("PJ:Site Scope Complete [Status]","PJ:Project ID",PROJECT_ACTIVE.projectId);
        softAssert.assertTrue(response.equals("Approved"),"Check Approval Status");
        projectTrackerPage.goToView("General Info");
        softAssert.closeAssert();
    }
}