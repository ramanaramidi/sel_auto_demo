package web.mb.tracker.ntp;

import common.BaseTest;
import commons.constants.Constants;
import commons.enums.LoginOptionEnum;
import commons.objects.*;
import org.testng.annotations.Test;
import pages.web.Tracker.ProjectNTPPage;
import pages.web.Tracker.ProjectTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.project.ProjectHelper;
import testData.UserData;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class ProjectNtpTests extends BaseTest {

    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    public EnvConfig envConfig = new EnvConfig();
    Project PROJECT_ACTIVE;
    Project PROJECT_ACTIVE_SITE_DEV;
    Project PROJECT_ACTIVE_REJECTION;
    Project PROJECT_DECOM;
    Project PROJECT_BAVV;
    ProjectTrackerPage projectTrackerPage;
    ProjectNTPPage projectNTPPage;
    ProjectHelper projectHelper = new ProjectHelper();

    public ProjectNtpTests()
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
       // generateTestData();
        mainSideMenu = loginPage.LoginAsUser(superUser);
    }

    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply.",priority = 2)
    public void createNewProjectForNtpConstructiontab(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue("00TESTOA-0002249088","PJ:Project ID");
       // projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        softAssert.assertTrue(projectTrackerPage.ntpConstruction_Validations(parentWindow),"ntp construction tab is displayed");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply.",priority = 3)
    public void createNewProjectForNtpApprovaltab(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue("00TESTOA-0002249088","PJ:Project ID");
      //  projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        softAssert.assertTrue(projectTrackerPage.ntpApproval_Validations(parentWindow),"ntp approval tab is displayed");
        softAssert.closeAssert();
    }
  /*  @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply.",priority = 4)
    public void createNewProjectAndSearchForBavv(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        // projectTrackerPage.searchForValue("B-AAV","PJ:Project Type");
        projectTrackerPage.searchForValue(PROJECT_BAVV.projectId, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        softAssert.assertFalse(projectTrackerPage.isNtpConstructionTabPresent(),"ntp construction tab is not displayed");
        softAssert.assertFalse(projectTrackerPage.isNtpApprovalTabPresent(),"ntp approval tab is not displayed");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply.",priority = 5)
    public void createNewProjectAndSearchForDecom(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        // projectTrackerPage.searchForValue("D-Decom","PJ:Project Type");
        projectTrackerPage.searchForValue(PROJECT_DECOM.projectId,"PJ:Project ID" );
        projectTrackerPage.selectEditOption();
        softAssert.assertFalse(projectTrackerPage.isNtpConstructionTabPresent(),"ntp construction tab is not displayed");
        softAssert.assertFalse(projectTrackerPage.isNtpApprovalTabPresent(),"ntp approval tab is not displayed");
        softAssert.closeAssert();
    }
*/
  /*  @Test(groups = {"Integration"},description = "uploadRegulatoryAndConstructionDoc",priority = 6)
    public void uploadRegulatoryAndConstructionDoc(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_3800_PRE_NTP", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_3800_PRE_NTP Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2475_FINAL_RFDS_ISSUED", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_2475_FINAL_RFDS_ISSUED Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2275_FIN_CNST_DR_APP", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_2275_FIN_CNST_DR_APP Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_3850_BOM_IN_BAT", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_3850_BOM_IN_BAT Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_1400_STRUCTURAL_COM", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_1400_STRUCTURAL_COM Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_1550_MNT_ANLSIS_RECD", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_1550_MNT_ANLSIS_RECD Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_PORT_MATRIX", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_PORT_MATRIX Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_3675_BP_APPROVED", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_3675_BP_APPROVED Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_4000_LL_NTP", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_4000_LL_NTP Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_GONOGO_CHKLST", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_GONOGO_CHKLST Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_ADD_NTP_DOC", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_ADD_NTP_DOC Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2525_REC_ULD_TAIR_ANS", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_2525_REC_ULD_TAIR_ANS Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2575_REC_UPD_NEPA_STDY", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_2575_REC_UPD_NEPA_STDY Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2600_SHPO", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_2600_SHPO Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2625_THPO", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_2625_THPO Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2650_PAL", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_2650_PAL Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2675_BIO_END_SPEC_STDY", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_2675_BIO_END_SPEC_STDY Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2725_WETLAND_DEL", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_2725_WETLAND_DEL Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2775_AM_SCREEN", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_2775_AM_SCREEN Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2825_REC_AM_DETUNE", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_2825_REC_AM_DETUNE Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2925_EME_SIGN_PLAN", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_2925_EME_SIGN_PLAN Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2950_FAA_CRANE_NOT", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_2950_FAA_CRANE_NOT Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2975_TOP_OUT_NOT", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_2975_TOP_OUT_NOT Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_3025_7460_1", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_3025_7460_1 Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_3050_FAA_DET", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_3050_FAA_DET Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_3075_FCC_COMP_LTR", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_3075_FCC_COMP_LTR Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_3100_TOWER_LIGHTING", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_3100_TOWER_LIGHTING Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_3250_PHASE_1_REC", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_3250_PHASE_1_REC Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_3475_REG_CERT", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_3475_REG_CERT Document should be uploaded");
        softAssert.closeAssert();
    }
*/
    @Test(groups = {"Integration"},description = "login",priority = 7)
    public void preNtpCreation(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue("00TESTOA-0002249088","PJ:Project ID");
       // projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectNTPPage = projectTrackerPage.goToNTPTabs();
        boolean fileDisplayed = projectNTPPage.createPreNtp(parentWindow);
        softAssert.assertTrue(fileDisplayed,"File should be zipped");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "verifyCXNTPCheckbox",priority = 8)
    public void verifyCXNTPCheckbox(Method method) throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue("00TESTOA-0002249088","PJ:Project ID");
      //  projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectNTPPage = projectTrackerPage.goToNTPTabs();
        projectNTPPage.checkNTPCheckBox();
        softAssert.assertTrue(projectNTPPage.verifyDevelopmentApprovalStatus(),"Development Approval Status should not be as Approved");
        softAssert.assertTrue(projectNTPPage.verifyApprovalTabsCheckboxes(),"In Approvals Tab all Approval checkboxes should be Unchecked");
        // projectNTPPage.switchToTracker(parentWindow);
        softAssert.assertTrue(projectNTPPage.switchToTrackerOnException(parentWindow),"");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "verifySetVendorStatus",priority = 9)
    public void verifySetVendorStatus(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue("00TESTOA-0002249088","PJ:Project ID");
     //   projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectNTPPage = projectTrackerPage.goToApprovalsTab();
        projectNTPPage.checkApprovalsCheckboxes();
        projectNTPPage = projectTrackerPage.goToApprovalsTab();
        projectNTPPage.selectAllApprovalsStatus();
        softAssert.assertTrue(projectNTPPage.verifyAllApprovalsStatusFields(),"In Approvals tab all Development Approval Status should be selected as Approved");
        softAssert.assertTrue(projectNTPPage.verifyVendorApprovalsStatus("Ready For Review"),"Vendor Approval Status Should be displayed as Ready for Review ");
        projectNTPPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "verifyApprovedStatus",priority = 10)
    public void verifyApprovedStatus(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue("00TESTOA-0002249088","PJ:Project ID");
       // projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectNTPPage = projectTrackerPage.goToApprovalsTab();
        softAssert.assertTrue(projectNTPPage.verifyApprovalTabsCheckboxes(),"In Approvals tab all checkboxes should be checked");
        projectNTPPage = projectTrackerPage.goToNTPTabs();
        projectNTPPage.selectDevelopmentApprovalStatus();
        projectNTPPage = projectTrackerPage.goToApprovalsTab();
        softAssert.assertTrue(projectNTPPage.verifyAllApprovalsStatusFields(),"In Approvals tab all Approval status fields should be displayed as Ready for Review");
        projectNTPPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "verifyNTPSubmittedField",priority=11)
    public void verifyNTPSubmittedField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue("00TESTOA-0002249088","PJ:Project ID");
       // projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectNTPPage = projectTrackerPage.goToApprovalsTab();
        projectNTPPage.selectAllApprovalsStatus();
        projectNTPPage = projectTrackerPage.goToApprovalsTab();
        softAssert.assertTrue(projectNTPPage.verifyAllApprovalsStatusFields(),"In Approvals tab all Development Approval Status should be selected as Approved");
        projectNTPPage = projectTrackerPage.goToApprovalsTab();
        projectNTPPage.selectVendorApproved();
        softAssert.assertTrue(projectNTPPage.verifyVendorApprovalsStatus("Approved"),"Vendor Approval Status Should be displayed as Approved ");
        projectNTPPage.navigateToNTPTabs();
        projectNTPPage.selectDevelopmentApprovalStatus();
        projectNTPPage = projectTrackerPage.goToApprovalsTab();
        projectNTPPage.selectAllApprovalsStatus();
        projectNTPPage.navigateToNTPTabs();
        // softAssert.assertContains(projectNTPPage.verifyDevelopmentApprovalName(),"Prasanna Yedrami","Development Approval Name should be displayed");
        softAssert.assertNotNull(projectNTPPage.verifyDevelopmentApprovalDate(),"Approval Date Should be displayed ");
        projectNTPPage.navigateToNTPTabs();
        boolean file = projectNTPPage.verifyPDF();
        softAssert.assertNotNull(file,"PDF merged document should be available");
        projectNTPPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups={"Integration"},description="Verify PJ:Construction NTP Documents(4075)",priority=12)
    public void verifyFiles4075() throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue("00TESTOA-0002249088","PJ:Project ID");
        //projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectNTPPage = projectTrackerPage.goToNTPTabs();
        softAssert.assertTrue(projectNTPPage.VerifyFileName("PJ:Cover Sheet PDF File(4075)").contains("pdf"),"Verify Cover Sheet PDF(4075) available");
        softAssert.assertTrue(projectNTPPage.VerifyFileName("PJ:Check List PDF FIle(4075)").contains("pdf"),"Verify check List PDF(4075) available");
        softAssert.assertTrue(projectNTPPage.VerifyFileName("PJ:NTP Documents Final 4075(Doc)").contains("zip"),"Verify NTP Documents Final 4075(DOC) available");
        softAssert.assertTrue(projectNTPPage.VerifyFileName("PJ:Create Final Zip(4075)").contains("zip"),"Verify Create Final Zip(4075) available");
        projectNTPPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "SelectDevelopmentRejectionStatus",priority = 13)
    public void selectDevelopmentRejection() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue("00TESTOA-0002246699","PJ:Project ID");
      //  projectTrackerPage.searchForValue(PROJECT_ACTIVE_REJECTION.projectId, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectNTPPage = projectTrackerPage.goToApprovalsTab();
        projectNTPPage.checkApprovalsCheckboxes();
        projectNTPPage = projectTrackerPage.goToNTPTabs();
        projectNTPPage.selectDevelopmentApprovalStatus();
        projectNTPPage = projectTrackerPage.goToApprovalsTab();
        //need to give comments in comments box when approvals status is Ready for Review
        projectNTPPage.addNotes();
        projectNTPPage.selectDevelopmentRejectionStatus();
        projectNTPPage = projectTrackerPage.goToNTPTabs();
        softAssert.assertTrue(projectNTPPage.verifyDevelopmentApprovalStatus(),"Development Approval Status should be Rejected");
        softAssert.assertFalse(projectNTPPage.verifyNTPCheckBox(),"CX NTP checkbox should be Unchecked");
        projectNTPPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Verify PJ:Construction NTP Submitted to GC (4100)", priority=14)
    public void VerifyNtpConstructionLock() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue("00TESTOA-0002249088","PJ:Project ID");
      //  projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectNTPPage = projectTrackerPage.goToNTPTabs();
        //softAssert.assertEquals(projectNTPPage.verifyLockNtp4100(), "field locked","Lock Should Be Present For 4100 Doc");
        softAssert.assertTrue(projectNTPPage.VerifyFileName("PJ:Construction NTP Accepted by GC (4100) [Doc]").contains("pdf"),"Construction NTP Accepted By GC (4100) Doc Available ");
        projectNTPPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups={"Integration"},description="Verify PJ:Construction NTP Documents(4100)",priority=15)
    public void verifyFiles4100() throws Exception{
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue("00TESTOA-0002249088","PJ:Project ID");
       // projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectNTPPage = projectTrackerPage.goToNTPTabs();
        softAssert.assertTrue(projectNTPPage.VerifyFileName("PJ:Cover Sheet PDF File(4100)").contains("pdf"),"Verify Cover Sheet PDF(4100) available");
        softAssert.assertTrue(projectNTPPage.VerifyFileName("PJ:Check List PDF(4100)").contains("pdf"),"Verify check List PDF(4100) available");
        softAssert.assertTrue(projectNTPPage.VerifyFileName("PJ:Create Final NTP 4100(Doc)").contains("zip"),"Verify NTP Documents Final 4100(DOC) available");
        softAssert.assertTrue(projectNTPPage.VerifyFileName("PJ:Create Final Zip(4100)").contains("zip"),"Verify Create Final Zip(4100) available");
        projectNTPPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply.",priority = 16)
    public void unActulizationOfTask4075Andverification(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue("00TESTOA-0002249088","PJ:Project ID");
     //   projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectNTPPage = projectTrackerPage.goToNTPTabs();
        projectNTPPage.unActualizationOf4075();
        softAssert.assertTrue(projectNTPPage.ntpConstruction4075Validation(),"ntp construction 4075 should empty");
        softAssert.assertTrue(projectNTPPage.coversheetpdffile4075validation(), "ntp cover sheet pdf 4075 should empty");
        softAssert.assertTrue(projectNTPPage.checklistpdffile4075validation(), "ntp check list pdf 4075 should empty");
        projectNTPPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply.",priority = 17)
    public void unActulizationOfTask4100AndVerification(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue("00TESTOA-0002249088","PJ:Project ID");
       // projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectNTPPage = projectTrackerPage.goToNTPTabs();
        projectNTPPage.unActualizationOf4100();
        softAssert.assertTrue(projectNTPPage.ntpConstruction4100Validation(),"ntp construction 4100 should empty");
        softAssert.assertTrue(projectNTPPage.coversheetpdffile4100validation(), "ntp cover sheet pdf 4100 should empty");
        softAssert.assertTrue(projectNTPPage.checklistpdffile4100validation(), "ntp check list pdf 4100 should empty");
        projectNTPPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    private void generateTestData(){
        String ringIdActiveProjectBundleSingle = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActiveProjectBundleSingle = new Ring("Active", ringIdActiveProjectBundleSingle, "Indoor Node");
        Site siteActiveProjectBundleSingle = new Site(ringIdActiveProjectBundleSingle,"Primary","Active Site");
        Por porDataBundleSingle = new Por("5GmmW_Stage 2_Site Mod", ringIdActiveProjectBundleSingle);
        PROJECT_ACTIVE = projectHelper.getActiveProject(false,ringActiveProjectBundleSingle,siteActiveProjectBundleSingle,porDataBundleSingle);
        System.out.println("Project __"+PROJECT_ACTIVE.projectId);

        String ringIdForSiteDev = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringForSiteDev = new Ring("Active", ringIdForSiteDev, "Indoor Node");
        Site siteForSiteDev = new Site(ringIdForSiteDev,"Primary","Active Site");
        Por porForSiteDev = new Por("5GmmW_Stage 2_Site Mod", ringIdForSiteDev);
        PROJECT_ACTIVE_SITE_DEV = projectHelper.getActiveProject(false,ringForSiteDev,siteForSiteDev,porForSiteDev);
        System.out.println("Project __"+PROJECT_ACTIVE_SITE_DEV.projectId);

        String ringIdForRejection = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringForRejection = new Ring("Active", ringIdForRejection, "Indoor Node");
        Site siteForRejection = new Site(ringIdForRejection,"Primary","Active Site");
        Por porForRejection = new Por("5GmmW_Stage 2_Site Mod", ringIdForRejection);
        PROJECT_ACTIVE_REJECTION = projectHelper.getActiveProject(false,ringForRejection,siteForRejection,porForRejection);
        System.out.println("Project __"+PROJECT_ACTIVE_REJECTION.projectId);

        ringIdActiveProjectBundleSingle = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        ringActiveProjectBundleSingle = new Ring("Active", ringIdActiveProjectBundleSingle, "Indoor Node");
        siteActiveProjectBundleSingle = new Site(ringIdActiveProjectBundleSingle,"Primary","Active Site");
        porDataBundleSingle = new Por("Backhaul Upgrade_ATT 10G Upgrade_Site Mod", ringIdActiveProjectBundleSingle);
        PROJECT_BAVV = projectHelper.getActiveProject(false,ringActiveProjectBundleSingle,siteActiveProjectBundleSingle,porDataBundleSingle);
        System.out.println("Project __BAVV"+PROJECT_BAVV.projectId);

        ringIdActiveProjectBundleSingle = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        ringActiveProjectBundleSingle = new Ring("Active", ringIdActiveProjectBundleSingle, "Indoor Node");
        siteActiveProjectBundleSingle = new Site(ringIdActiveProjectBundleSingle,"Primary","Active Site");
        porDataBundleSingle = new Por("Decom_ECX01_Decom", ringIdActiveProjectBundleSingle);
        PROJECT_DECOM = projectHelper.getActiveProject(false,ringActiveProjectBundleSingle,siteActiveProjectBundleSingle,porDataBundleSingle);
        System.out.println("Project __DECOM"+PROJECT_DECOM.projectId);

    }

}
