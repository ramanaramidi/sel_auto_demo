package web.mb.tracker;

import common.BaseTest;
import commons.constants.Constants;
import commons.enums.LoginOptionEnum;
import commons.objects.Por;
import commons.objects.Project;
import commons.objects.Ring;
import commons.objects.Site;
import org.testng.annotations.Test;
import pages.web.Tracker.*;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.por.PorHelper;
import rest.project.ProjectHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class DevProjectTests extends BaseTest {
    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    ProjectTrackerPage projectTrackerPage;
    RFSectorPage rfSectorPage;
    PORTrackerPage porTrackerPage;
    AddPORPage addPORPage;
    PorHelper porHelper = new PorHelper();
    String VALID_RING_PRIMARY_BUILD_SITE = "";
    Site ACTIVE_RING_NEW_SITE;
    ProjectHelper projectHelper = new ProjectHelper();
    ACSIntakeTracker ACSTrackerPage;
    Project PROJECT_ACTIVE;

    public DevProjectTests() {
        if (envURL == null) {
            envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");
        }
        if (testSuite == null) {
            testSuite = "sectorSet.xml";
        }
    }

    @Test(groups = {"Integration"}, description = "login", priority = 1)
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
        generateTestData();
        mainSideMenu = loginPage.LoginAsUser(superUser);

    }
    private void generateTestData() {
        String ringIdActiveProjectBundleSingle = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActiveProjectBundleSingle = new Ring("Active", ringIdActiveProjectBundleSingle, "Indoor Node");
        Site siteActiveProjectBundleSingle = new Site(ringIdActiveProjectBundleSingle, "Primary", "Active Site");
        Por porDataBundleSingle = new Por("5GmmW_Stage 2_Site Mod", ringIdActiveProjectBundleSingle);
        PROJECT_ACTIVE = projectHelper.getActiveProject(false, ringActiveProjectBundleSingle, siteActiveProjectBundleSingle, porDataBundleSingle);
        System.out.println("Project __" + PROJECT_ACTIVE.projectId);
    }

    @Test(groups = {"Integration"},description = "validateValuesFromPORField",priority = 2)
    public void validateValuesFromPORField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue("00TESTOA-0002070764", "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        String ProjectCompletionObjectiveValues = projectTrackerPage.validateProjectCompletionObjective();
        softAssert.assertNotNull(ProjectCompletionObjectiveValues,"After Project Creation, values from POR field will get copied to Project Completion Objective");
        // softAssert.assertEquals(ObjectiveValuesFromPOR,ProjectCompletionObjectiveValues,"ObjectiveValues are equal");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();

    }

    @Test(groups = {"Integration"}, description = "validateObjectiveValuesNA_UNNA", priority = 3)
    public void validateObjectiveValuesNA_UNNA(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        //  projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectTrackerPage.searchForValue("00TESTOA-0002070764", "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        String ProjectCompletionObjectiveValues = projectTrackerPage.validateProjectCompletionObjective();
        softAssert.assertNotNull(ProjectCompletionObjectiveValues,"After Project Creation, values from POR field will get copied to Project Completion Objective");
        projectTrackerPage.goToTasksPage();
        projectTrackerPage.verifyTasksActualized_UnAct(ProjectCompletionObjectiveValues);
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "validateCountOfUnactualizedTasks", priority = 4)
    public void validateCountOfUnactualizedTasks(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        //  projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectTrackerPage.searchForValue("00TESTOA-0002070764", "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        String selectedTasks = projectTrackerPage.validateProjectCompletionObjective();
        softAssert.assertTrue(selectedTasks.length()>0,"For project selected Tasks count is displayed");
        String remainingTasks = projectTrackerPage.validateRemainingTasksCount();
        softAssert.assertTrue(remainingTasks.length()>0,"For project remaining Tasks count is displayed");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "validateProjectOffAirObjectiveField", priority = 9)
    public void validateProjectOffAirObjectiveField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        //  projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectTrackerPage.searchForValue("SAUKZVPG-0002249225", "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        boolean ProjectOffAirValues = projectTrackerPage.getOffAirObjectiveValues();
        boolean UnSelectedValues = projectTrackerPage.getUnSelectedTasks();
        softAssert.assertTrue(ProjectOffAirValues,"Selected Project OffAir Objective Values are Actualized-Un N/A'd");
        softAssert.assertTrue(UnSelectedValues,"UnSelected Values in Multi-Selecter are UnActualized and N/A");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

   // @Test(groups = {"Integration"}, description = "validatePJ_TotalSectors", priority = 5)
    public void validatePJ_TotalSectors(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        //  projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectTrackerPage.searchForValue("00TESTOA-0002070764", "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        String total_Sectors = projectTrackerPage.getPJSectorsTotalCount();
        softAssert.assertTrue(total_Sectors.length()>0,"For project Total Sectors count is displayed");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

   // @Test(groups = {"Integration"}, description = "validateSector_RelatedProjects", priority = 6)
    public void validateSector_RelatedProjects(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue("00TESTOA-0002070764","SEC:Project ID (Build)");
        rfSectorPage.selectEditOption();
        rfSectorPage.getPJ_Sectors();
        rfSectorPage.clickMainLogo();
        projectTrackerPage = rfSectorPage.clickingProjectTracker();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectTrackerPage.searchForValue("00TESTOA-0002070764","PJ:Project ID");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "validatePJ_RemainingSectors", priority = 7)
    public void validatePJ_RemainingSectors(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        //  projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectTrackerPage.searchForValue("00TESTOA-0002070764", "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        String remainCount = projectTrackerPage.getPJSectorsRemainingCount();
        softAssert.assertTrue(remainCount.length()>0,"For project remaining Sectors count should be displayed");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "displayOnAirTechnologies_CopiedOffAirTechnologies", priority = 8)
    public void displayOnAirTechnologies_CopiedOffAirTechnologies(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        // projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectTrackerPage.searchForValue("SAUKZVPG-0002249225", "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        boolean OnAir_OffAirTechValues = projectTrackerPage.getOnAirTechnologiesTo_OffAirField();
        softAssert.assertTrue(OnAir_OffAirTechValues,"On Air Technologies are copied to Off Air Technologies during the Project Creation");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "displaySector_ProjectLink", priority = 10)
    public void displaySector_ProjectLink(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        rfSectorPage = mainSideMenu.goToRFSectorTracker();
        rfSectorPage.searchForValue("00TESTOA_A1GPV","SEC:Sector ID");
        rfSectorPage.selectEditOption();
        String parentWindow = rfSectorPage.switchToProjectPage();
        String sectors = rfSectorPage.displaySector_ProjectLink();
        softAssert.assertTrue(sectors.length()>0,"For the Project selected under SEC:ProjectIds sectorIds are displayed");
        rfSectorPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "validateProjectOffAirObjective_Remaining", priority = 11)
    public void validateProjectOffAirObjective_Remaining(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        // projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectTrackerPage.searchForValue("SAUKZVPG-0002249225", "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectTrackerPage.getOffAirObjectiveValues();
        String remainingValues = projectTrackerPage.getOffAirObjectiveRemaining();
        softAssert.assertTrue(remainingValues.length()>0,"Remaining OffAir Objective Values Count is displayed");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "validateProjectOffAirObjective_Remaining", priority = 12)
    public void validateProjectOffAirObjective_Total(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        //  projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectTrackerPage.searchForValue("SAUKZVPG-0002249225", "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectTrackerPage.getOffAirObjectiveValues();
        String TotalValues = projectTrackerPage.getOffAirObjectiveTotal();
        softAssert.assertTrue(TotalValues.length()>0,"Total OffAir Objective Values Count is displayed");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "validateProjectOffAirObjective_Remaining", priority = 13)
    public void uploadMultipleEfiles(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_3800_PRE_NTP", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png"),"PJ_3800_PRE_NTP Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2475_FINAL_RFDS_ISSUED", Constants.IMAGE_FILE_UPLOAD,"Book1.xls"),"PJ_2475_FINAL_RFDS_ISSUED Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2275_FIN_CNST_DR_APP", Constants.IMAGE_FILE_UPLOAD,"cellplanned.txt"),"PJ_2275_FIN_CNST_DR_APP Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_3850_BOM_IN_BAT", Constants.IMAGE_FILE_UPLOAD,"TMO_Virtual_Desktop_Quick_Reference (1).pdf"),"PJ_3850_BOM_IN_BAT Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_1400_STRUCTURAL_COM", Constants.IMAGE_FILE_UPLOAD,"images.jfif"),"PJ_1400_STRUCTURAL_COM Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_1550_MNT_ANLSIS_RECD", Constants.IMAGE_FILE_UPLOAD,"Signature format.docx"),"PJ_1550_MNT_ANLSIS_RECD Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_PORT_MATRIX", Constants.IMAGE_FILE_UPLOAD,"samplepptx.pptx"),"PJ_PORT_MATRIX Document should be uploaded");
        softAssert.assertTrue(projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_3675_BP_APPROVED", Constants.IMAGE_FILE_UPLOAD,"TestReports.html"),"PJ_3675_BP_APPROVED Document should be uploaded");
    }

    //@Test(groups = {"Integration"}, description = "validateProjectOffAirObjective_Remaining", priority = 14)
    public void BulkUploadOfEfiles(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        ACSTrackerPage = mainSideMenu.goToACSIntakeTracker();
        ACSTrackerPage.searchForValue("ACS200082","ACS:ACS Intake Tracker ID");
        ACSTrackerPage.selectEditOption();
        String FilesCount = ACSTrackerPage.uploadBulkFiles();
        softAssert.assertTrue(FilesCount.length()>0,"Bulk files are uploaded");
    }
    @Test(groups = {"Integration"},description = "Verify Each Multi-selectors has it own Total Docs and Remaining Docs",priority = 2)
    public void verifyTotalAndRemainingDocsFields() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        projectTrackerPage.switchToProjectPage();
        softAssert.assertTrue(projectTrackerPage.isDocsCountAvailable("PJ:Leasing Docs Total"),"PJ:Leasing Docs Total field should be Available");
        softAssert.assertTrue(projectTrackerPage.isDocsCountAvailable("PJ:Leasing Docs Remaining"),"PJ:Leasing Docs Remaining field should be Available");
        softAssert.assertTrue(projectTrackerPage.isDocsCountAvailable("PJ:Regulatory Docs Total"),"PJ:Regulatory Docs Total field should be Available");
        softAssert.assertTrue(projectTrackerPage.isDocsCountAvailable("PJ:Regulatory Docs Remaining"),"PJ:Regulatory Docs Remaining field should be Available");
        softAssert.assertTrue(projectTrackerPage.isDocsCountAvailable("PJ:Zoning and Permitting Docs Total"),"PJ:Zoning and Permitting Docs Total field should be Available");
        softAssert.assertTrue(projectTrackerPage.isDocsCountAvailable("PJ:Zoning and Permitting Docs Remaining"),"PJ:Zoning and Permitting Docs Remaining field should be Available");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ:Leasing Docs Total"),"Should be readonly");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ:Leasing Docs Remaining"),"Should be readonly");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ:Regulatory Docs Total"),"Should be readonly");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ:Regulatory Docs Remaining"),"Should be readonly");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ:Zoning and Permitting Docs Total"),"Should be readonly");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ:Zoning and Permitting Docs Remaining"),"Should be readonly");
        projectTrackerPage.backToTrackerPage();
        softAssert.closeAssert();
    }
    //@Test(groups = {"Integration"},description = "Verify if Build To Suit is Blank then fields related to BTS should be Disabled",priority = 51)
    public void verifyBtsFieldIsBlank() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.goToView("G:Build to Suit");
        projectTrackerPage.searchForValueVendor(PROJECT_ACTIVE.projectId, "PJ:Project ID");
        projectTrackerPage.selectRowEditor();
        projectTrackerPage.btsField("");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 4275) BTS Vendor Construction Start"),"Should be Disabled");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(A 4275) BTS Vendor Construction Start"),"Should be Disabled");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 5150) BTS Vendor Construction Complete"),"Should be Disabled");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(A 5150) BTS Vendor Construction Complete"),"Should be Disabled");
        projectTrackerPage.backToTrackerPage();
        projectTrackerPage.goToView("General Info");
        softAssert.closeAssert();
    }
    //@Test(groups = {"Integration"},description = "Verify if Build To Suit is Yes then fields related to BTS should be Enabled",priority = 50)
    public void verifyBtsFieldIsYes() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.goToView("G:Build to Suit");
        projectTrackerPage.searchForValueVendor(PROJECT_ACTIVE.projectId, "PJ:Project ID");
        projectTrackerPage.selectRowEditor();
        projectTrackerPage.btsField("Yes");
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(P 4275) BTS Vendor Construction Start"),"Field Should be Enabled");
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(A 4275) BTS Vendor Construction Start"),"Field Should be Enabled");
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(P 5150) BTS Vendor Construction Complete"),"Field Should be Enabled");
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(A 5150) BTS Vendor Construction Complete"),"Field Should be Enabled");
        projectTrackerPage.backToTrackerPage();
        projectTrackerPage.goToView("General Info");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Verify Checkbox for Run Event Rule",priority = 5)
    public void verifyRunEventRuleCheckBox() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        softAssert.assertTrue(projectTrackerPage.eventRuleCheckbox(),"Checkbox should be Checked");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "check if total doc field updates when items are added \n" +
            "and if the fields unlock ",priority = 6)
    public void verifyTotalDocsField_Leasing() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        softAssert.assertTrue((projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_2025_AMEND1", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png")),"Document should be uploaded");
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        projectTrackerPage.selectDdtDeliverables("PJ:Leasing","Amendment 1","Amendment 2");
        projectTrackerPage.goToLeasingTab();
        softAssert.assertTrue(projectTrackerPage.validateLockIsEnabled("PJ:Amendment 1 (2010) [Doc]"),"Field Should be unlocked ");
        softAssert.assertTrue(projectTrackerPage.validateLockIsEnabled("PJ:Amendment 2 (2010) [Doc]"),"Field Should be unlocked ");
        softAssert.assertFalse(projectTrackerPage.validateLockIsEnabled("PJ:Consent Letter Approved by LL (2010)[Doc]"),"Field Should be locked ");
        int count = projectTrackerPage.docsCount("PJ:Leasing Docs Remaining");
        System.out.println("Docs Total Count::"+count);
        projectTrackerPage.updateStatus("PJ:Amendment 1 [Status]","Fully Executed / Approved");
        int count1 = projectTrackerPage.docsCount("PJ:Leasing Docs Remaining");
        System.out.println("Docs Remaining Count::"+count1);
        softAssert.assertTrue(count1<count,"Fields got auto actualised");
        projectTrackerPage.backToTrackerPage();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Validate fields when BackHaul Task Selection is AAV", priority = 2)
    public void validateFieldsWhenBackHaulIsAAV(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        String ProjectActive = "TEST11AA-0002249226";
        projectTrackerPage.searchForValue(ProjectActive, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        softAssert.assertTrue(projectTrackerPage.getBackHaulTaskSelection("AAV"),"BackHaul Task Selection should be AAV");
        projectTrackerPage.goToTransportTab();
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:AAV Notice Letter Sent (1627) [Doc]"), "Task is unNA'd");
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:AAV Amendment (2015) [Doc]"), "Task is unNA'd");
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:AAV Zoning Applied (3580) [Doc]"), "Task is unNA'd");
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(P 2420) AAV A and E Complete"), "Task is unNA'd");
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(P 3490) AAV Regulatory Complete "), "Task is unNA'd");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Validate AAV fields when BackHaul Task Selection is Null", priority = 3)
    public void validateAAVFieldsWhenBackHaulIsNull(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        String ProjectActive = "TEST11AA-0002249267";
        projectTrackerPage.searchForValue(ProjectActive, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        softAssert.assertTrue(projectTrackerPage.getBackHaulTaskSelection(""),"BackHaul Task Selection should be Null");
        projectTrackerPage.goToTransportTab();
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 1627) AAV Notice Letter Sent"), "Task is unNA'd");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 1727) AAV Consent Letter Approved by LL"), "Task is unNA'd");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 2420) AAV A and E Complete"), "Task is unNA'd");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 3490) AAV Regulatory Complete "), "Task is unNA'd");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 2080) AAV Telco Design Complete"), "Task is unNA'd");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Validate fields when BackHaul Task Selection is AAV", priority = 4)
    public void validateFieldsWhenBackHaulIsMicrowave(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        String ProjectActive = "TEST11AA-0002249261";
        projectTrackerPage.searchForValue(ProjectActive, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        softAssert.assertTrue(projectTrackerPage.getBackHaulTaskSelection("Microwave"),"BackHaul Task Selection should be Microwave");
        projectTrackerPage.goToTransportTab();
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:MW Path Designed (1250) [Doc]"), "Task is unNA'd");
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:MW Line of Sight Survey Completed (1275) [Doc]"), "Task is unNA'd");
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:MW Site Application Submitted (1300) [Doc]"), "Task is unNA'd");
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(P 1300) MW Site Application Submitted"), "Task is unNA'd");
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(P 1405) MW Structural Ordered"), "Task is unNA'd");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Validate AAV fields when BackHaul Task Selection is Null", priority = 5)
    public void validateMicrowaveFieldsWhenBackHaulIsNull(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        String ProjectActive = "TEST11AA-0002249267";
        projectTrackerPage.searchForValue(ProjectActive, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        softAssert.assertTrue(projectTrackerPage.getBackHaulTaskSelection(""),"BackHaul Task Selection should be Null");
        projectTrackerPage.goToTransportTab();
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 1250) MW Path Designed"), "Task is unNA'd");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 1275) MW Line of Sight Survey Completed"), "Task is unNA'd");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 1300) MW Site Application Submitted"), "Task is unNA'd");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 1425) MW FCC PCN Filed"), "Task is unNA'd");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 1405) MW Structural Ordered"), "Task is unNA'd");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Actualise the tasks manually it shouldn't throw an error", priority = 6)
    public void actualiseTheTasksManually(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        String ProjectActive = "TEST11AA-0002249267";
        projectTrackerPage.searchForValue(ProjectActive, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectTrackerPage.actualiseTaskManually();
        softAssert.assertFalse(projectTrackerPage.isPopALert(parentWindow),"Actualising the manually hasn't thrown Error");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Try to change child task  projected dates (5175) is less than parent Task (4225) then it should throw an error ", priority = 7)
    public void taskRelationship(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        String ProjectActive = "TEST11AA-0002249267";
        projectTrackerPage.searchForValue(ProjectActive, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectTrackerPage.set5175DateLessThan4225Date();
        softAssert.assertTrue(projectTrackerPage.isPopALert(parentWindow),"Pop-up Error Verified Successfully");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "UPload The Document To Site Scope  Field",priority = 2)
    public void documentUploadToSiteScopeFiled_Project() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue("AMEENA88-0002249115", "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        int oldDocCount = projectTrackerPage.uploadScipImage();
        System.out.println("old doc count ::" + oldDocCount);
        projectHelper.uploadDocument("1265913680", "PJ_1000_SITE_SCOPE_CPT", Constants.IMAGE_FILE_UPLOAD, "simpleImage.png");
        int newDocCount = projectTrackerPage.getDocumentIDCount();
        System.out.println("new doc count ::" + newDocCount);
        projectTrackerPage.deleteAddedDocument();
        projectTrackerPage.acceptAndGoToProjectTracker();
        softAssert.assertTrue(oldDocCount < newDocCount, "The doc is added");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Upload The Document And Verify The Site Scope Field",priority = 2)
    public void documentUploadAndSiteScopeValidaion_Project() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue("AMEENA88-0002249115", "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        int oldDocCount = projectTrackerPage.uploadScipImage();
        System.out.println("old doc count ::" + oldDocCount);
        projectHelper.uploadDocument("1265913680", "PJ_1000_SITE_SCOPE_CPT", Constants.IMAGE_FILE_UPLOAD, "simpleImage.png");
        int newDocCount = projectTrackerPage.getDocumentIDCount();
        System.out.println("new doc count ::" + newDocCount);
        softAssert.assertTrue(projectTrackerPage.siteScopeCompleteValidation(), "Status Field Updated With Default Status");
        projectTrackerPage.deleteAddedDocument();
        projectTrackerPage.acceptAndGoToProjectTracker();
        softAssert.assertTrue(oldDocCount < newDocCount, "The doc is added");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Upload The Document And Verify Current Verification CheckBox",priority = 2)
    public void documentUploadAndCurrentVertionverification_Project() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue("AMEENA88-0002249115", "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        int oldDocCount = projectTrackerPage.uploadScipImage();
        System.out.println("old doc count ::" + oldDocCount);
        projectHelper.uploadDocument("1265913680", "PJ_1000_SITE_SCOPE_CPT", Constants.IMAGE_FILE_UPLOAD, "simpleImage.png");
        int newDocCount = projectTrackerPage.getDocumentIDCount();
        System.out.println("new doc count ::" + newDocCount);
        projectTrackerPage.goToGeneralInfo();
        projectHelper.uploadDocument("1265913680", "PJ_1000_SITE_SCOPE_CPT", Constants.IMAGE_FILE_UPLOAD, "simpleImage.png");
        projectTrackerPage.goToDocumentsTab();
        softAssert.assertFalse(projectTrackerPage.currentVersionVerification(),"Check Box Is Not Selected");
        projectTrackerPage.deleteAddedDocument();
        projectTrackerPage.deleteAddedDocument();
        projectTrackerPage.acceptAndGoToProjectTracker();
        softAssert.closeAssert();
    }
}