package web.mb.tracker;

import common.BaseTest;
import commons.constants.Constants;
import commons.enums.LoginOptionEnum;
import commons.objects.*;
import org.testng.annotations.Test;
import pages.web.Tracker.AddPORPage;
import pages.web.Tracker.AddProjectPage;
import pages.web.Tracker.PORTrackerPage;
import pages.web.Tracker.ProjectTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.por.PorHelper;
import rest.project.ProjectHelper;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class ProjectTests extends BaseTest {

    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    ProjectTrackerPage projectTrackerPage;
    AddProjectPage addProjectPage;
    PORTrackerPage porTrackerPage;
    AddPORPage addPORPage;
    PorHelper porHelper = new PorHelper();
    Project PROJECT_WITH_WORK_PLAN;
    Project PROJECT_ACTIVE;
    Project PROJECT_NEW;
    Project activeProjectBundle;
    Project activeProjectBundleSingle;
    ProjectHelper projectHelper = new ProjectHelper();
    SiteHelper siteHelper = new SiteHelper();
    String VALID_RING_PRIMARY_BUILD_SITE = "";
    Site ACTIVE_RING_NEW_SITE ;
    public ProjectTests()
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
        commonDataGenerator();
        mainSideMenu = loginPage.LoginAsUser(superUser);

    }

    @Test(groups = {"Integration"},description = "User should able to see all POR ID's",priority = 2)
    public void verifyAllProjectID_Project() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_NEW.projectId,"PJ:Project ID");

        softAssert.assertTrue(projectTrackerPage.getProjectIDCount()>0, "There is at-least one POR");
        softAssert.assertTrue(projectTrackerPage.isValuePresentInGrid("S:Site Code"),"Value Should Be Present");
        softAssert.assertTrue(projectTrackerPage.isValuePresentInGrid("PJ:Project Status"),"Value Should Be Present");
        softAssert.assertTrue(projectTrackerPage.isValuePresentInGrid("PJ:Project Type"),"Value Should Be Present");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "User should able to see all POR ID's",priority = 2)
    public void updateDetailsOfExistingProject_Project() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_NEW.projectId,"PJ:Project ID");
        softAssert.assertTrue(projectTrackerPage.getProjectIDCount()>0, "There is at-least one POR");
        if(projectTrackerPage.getProjectIDCount()>0){
            softAssert.assertTrue(projectTrackerPage.isValuePresentInGrid("S:Site Code"),"Value Should Be Present");
            softAssert.assertTrue(projectTrackerPage.isValuePresentInGrid("PJ:Project Status"),"Value Should Be Present");
            softAssert.assertTrue(projectTrackerPage.isValuePresentInGrid("PJ:Project Type"),"Value Should Be Present");
            addProjectPage = projectTrackerPage.selectEditOption();
            addProjectPage.updateExistingProjectTracker("Updating the Existing Project");
            softAssert.assertTrue(addProjectPage.verifyUpdatedByAndDateDetails(superUser),"Details should match post update");
            projectTrackerPage = addProjectPage.returnToProjectTracker();
        }
        softAssert.closeAssert()
        ;
    }

    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply.",priority = 4)
    public void VerifyProjectTrackerNotGeneratedWhenPORQueueIncorrectStatus_Project(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorTrackerPage();

        Por porData = new Por(ACTIVE_RING_NEW_SITE.ringId);
        porData = porHelper.createAPor(porData);
        porTrackerPage.searchForValue(porData.porId,"POR:POR ID");
        addPORPage=porTrackerPage.selectEditOption();
        softAssert.assertTrue(!addPORPage.isProjectIdGenerated(),"Project ID will not be generated");
        //DELETE THE CREATED POR AS PART OF TEAR DOWN
        porHelper.getPorTrackerIdsAndDelete(porData);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply.",priority = 4)
    public void createNewProjectTrackerFromPOR_Project(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorTrackerPage();
        Por porData = new Por(VALID_RING_PRIMARY_BUILD_SITE);
        porData = porHelper.createAPor(porData);
        porTrackerPage.searchForValue(porData.porId,"POR:POR ID");
        addPORPage=porTrackerPage.selectEditOption();
        porData.queueStatus = "POR Request Complete";
        String projectId = addPORPage.changePORRequestQueue1(porData);
        porTrackerPage = addPORPage.returnToPORTracker();
        porTrackerPage.refresh();
        sleepFor(15);
        String response = porTrackerPage.searchForValueInGrid("PJ:Project ID","POR:POR ID",porData.porId);
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(response,"PJ:Project ID");
        softAssert.assertTrue(projectTrackerPage.getProjectIDCount()>0, "There is at-least one POR");
        if(projectTrackerPage.getProjectIDCount()>0){
            softAssert.assertTrue(projectTrackerPage.isValuePresentInGrid("S:Site Code"),"Value Should Be Present");
            softAssert.assertTrue(projectTrackerPage.isValuePresentInGrid("PJ:Project Status"),"Value Should Be Present");
            softAssert.assertTrue(projectTrackerPage.isValuePresentInGrid("PJ:Project Type"),"Value Should Be Present");
        }
        porTrackerPage = mainSideMenu.goToPorTrackerPage();
        porTrackerPage.searchForValue(porData.porId,"POR:POR ID");
        //DELETE THE CREATED POR AS PART OF TEAR DOWN
//        porHelper.getPorTrackerIdsAndDelete(porData);
//        projectHelper.deleteProjectTracker(projectId);
        softAssert.closeAssert();
    }
    ////
    @Test(groups = {"Integration"},description = "Add Colo App Submitted functionality",priority = 100)
    public void verifyColoAppSubmitted() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        System.out.println ("Project ID_"+PROJECT_ACTIVE.projectId);
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        //waitForActiveState();
//        projectTrackerPage.searchForValue(activeProject.projectId,"PJ:Project ID");
        String text= projectTrackerPage.projectIDClick();
        System.out.println(text);
        softAssert.assertTrue(text.equals("Colo App Submitted"), "Colo App should now be listed in the PJ: Leasing box");
        //porHelper.getPorTrackerIdsAndDelete(porData);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Upload Colo App Submitted Document",priority = 7)
    public void uploadColoAppSubmittedDoc_Project() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        //AU00KR1A-0002246676
        projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(),"PJ_1225_COLO_APP_FILE", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png");
        projectTrackerPage.selectEditOption();
        String coloApprovedFinishedDate=projectTrackerPage.coloAppImageUpload();
        System.out.println("COl Finish Date_"+coloApprovedFinishedDate);
        String FinishedDate= MiscHelpers.currentDateTime("MM/dd/yyyy");
        softAssert.assertTrue(coloApprovedFinishedDate.equals(FinishedDate),"colo Approved Finished Date generated" );
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Delete Colo App Submitted Document",priority = 8)
    public void deleteColoAppSubmittedDoc_Project() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_NEW.projectId,"PJ:Project ID");
        //AU00KR1A-0002246676
        projectTrackerPage.selectEditOption();
        String coloApprovedFinishedDate=projectTrackerPage.DeleteScipImage();
        System.out.println("COl Finish Date_"+coloApprovedFinishedDate);
        String FinishedDate="";
        softAssert.assertTrue(coloApprovedFinishedDate.equals(FinishedDate),"colo Approved Finished Date removed");
        softAssert.closeAssert();
    }



    @Test(groups = {"Integration"},description = "Adding Project Comments",priority = 9)
    public void AddProjectComments_Project() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_NEW.projectId,"PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String comments = "Automation Connection Testing";
        String ProjectCommentsHistoryText=projectTrackerPage.ProjectComments(comments);
        System.out.println("History text_"+ProjectCommentsHistoryText);
        softAssert.assertTrue(ProjectCommentsHistoryText.contains(comments),"The notes moved from the PJ:Project Comments field to the PJ:Project Comment History field :: "+ProjectCommentsHistoryText);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Adding Action Comments",priority = 10)
    public void AddActionComments_Project() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_NEW.projectId,"PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String comment = "Automation Connection Testing content in Action Item";
        String ActionCommentsHistoryText=projectTrackerPage.ActionComments(comment);
        System.out.println("Action History text_"+ActionCommentsHistoryText);
        softAssert.assertTrue(ActionCommentsHistoryText.contains(comment),"The notes moved from the PJ:Action Item Notes field to the PJ:Action Item History field");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Adding Structured Date in A & E Tab",priority = 11)
    public void AEStructredDate_Project() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_NEW.projectId,"PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String AETabDate=projectTrackerPage.AETabDate();
        System.out.println("Date_"+AETabDate);
        String TodayDate=MiscHelpers.currentDateTime("MM/dd/yyyy");
        //softAssert.assertTrue(AETabDate.equals(TodayDate),"Today Date Populated");
        softAssert.closeAssert();
    }


    ////    @Test(groups = {"Integration"},description = "Project Creation",priority = 12)
////    public void verifyProjectCreatedViaPOR() throws Exception {
////        AssertionsUtil softAssert = new AssertionsUtil();
////        porTrackerPage= mainSideMenu.goToPorTrackerPage();
////        Por porData = new Por(Constants.RING_CODE1);
////        porData = porHelper.createAPor(porData);
////        porTrackerPage.searchForValue(porData.porId,"POR:POR ID");
////        addPORPage=porTrackerPage.selectEditOption();
////        String ProjectID=addPORPage.changePORRequestQueue();
////        System.out.println ("Project ID_"+ProjectID);
////        projectTrackerPage= mainSideMenu.goToProjectTracker();
////        projectTrackerPage.searchForValue(ProjectID,"PJ:Project ID");
////        String response = projectTrackerPage.searchForValueInGrid("PJ:Project Type","PJ:Project ID",ProjectID);
////        softAssert.assertNotNull(response,"Request Type Should not be empty:: "+response);
////        response = projectTrackerPage.searchForValueInGrid("PJ:Project Status","PJ:Project ID",ProjectID);
////        softAssert.assertContains(response,"New","New Project Created");
////        System.out.println(":::"+response);
////        response = projectTrackerPage.searchForValueInGrid("PJ:Program Name","PJ:Project ID",ProjectID);
////        System.out.println("PJ:Program Name:::"+response);
////        response = projectTrackerPage.searchForValueInGrid("PJ:Program Name (Element Feed)","PJ:Project ID",ProjectID);
////        System.out.println("PJ:Program Name (Element Feed):::"+response);
////        response = projectTrackerPage.searchForValueInGrid("PJ:Project Status Timestamp","PJ:Project ID",ProjectID);
////        System.out.println("PJ:Project Status Timestamp:::"+response);
////        response = projectTrackerPage.searchForValueInGrid("PJ:Project Completion Objective","PJ:Project ID",ProjectID);
////        System.out.println("PJ:Project Completion Objective:::"+response);
////        response = projectTrackerPage.searchForValueInGrid("PJ:Planned Start Date","PJ:Project ID",ProjectID);
////        // softAssert.assertContains(response, MiscHelpers.currentDateTime("MM/dd/yyyy"),"Planned Start Date Matched::"+response );
////        System.out.println("PJ:Planned Start Date:::"+response);
////        projectTrackerPage.selectEditOption();
////        String Count= projectTrackerPage.porCount();
////        System.out.println("PORCount_"+Count);
////        porHelper.getPorTrackerIdsAndDelete(porData);
////        softAssert.closeAssert();
////    }
//
    @Test(groups = {"Integration"},description = "Project Status Active",priority = 1000)
    public void changeProjectStatusNewToActive_Project() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_NEW.projectId, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        projectTrackerPage.projectStatus();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
//        String response = waitForActiveState();
//        softAssert.assertContains(response,"Active"," Project Status Changed to Active");
//        System.out.println(":::"+response);
        PROJECT_NEW = null;
        // projectHelper.deleteProjectTracker(PROJECT_NEW.projectId);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Duplicate Work Plan creation Alert",priority = 14)
    public void duplicateWorkPlan_Project() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        System.out.println ("Project ID_"+PROJECT_ACTIVE.projectId);
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        softAssert.assertTrue(projectTrackerPage.WorkPlanCreation(),"User should not be able to add a duplicate work plan");
        //projectHelper.deleteProjectTracker(PROJECT_ACTIVE.projectId);
        //porHelper.getPorTrackerIdsAndDelete(porData);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Planned Start Date Validation",priority = 15)
    public void PlannedStartDateValidation_Project() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_NEW.projectId, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        projectTrackerPage.plannedStartDateValidation();
        softAssert.closeAssert();
    }


    @Test(groups = {"Integration"},description = "ProgramName Verification",priority = 16)
    public void verifyProgramNameInProject_Project() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String ProgramName=PROJECT_NEW.por.programName;
        System.out.println ("POR Program Name_"+ProgramName);
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_NEW.projectId,"PJ:Project ID");
        String response = projectTrackerPage.searchForValueInGrid("PJ:Project Status","PJ:Project ID",PROJECT_NEW.projectId);
        softAssert.assertContains(response,"New","New Project Created");
        System.out.println(":::"+response);
        response = projectTrackerPage.searchForValueInGrid("PJ:Program Name","PJ:Project ID",PROJECT_NEW.projectId);
        softAssert.assertContains(response,ProgramName ,"Both  Program names are equal::"+response );
        response = projectTrackerPage.searchForValueInGrid("PJ:Program Name (Element Feed)","PJ:Project ID",PROJECT_NEW.projectId);
//        softAssert.assertContains(ProgramName,response ,"Both Program Name and PJ: Program Name (Element Feed) names are equal::"+response );
//        System.out.println(":::"+response);
        softAssert.closeAssert();
    }
    ////
//////    @Test(groups = {"Integration"},description = "Project having single POR Program Name and Completion Objective change when Project is un-bundled from POR",priority = 17)
//////    public void UnBundleProjectFromPOR() throws Exception {
//////        AssertionsUtil softAssert = new AssertionsUtil();
//////        porTrackerPage= mainSideMenu.goToPorTrackerPage();
//////        Por porData = new Por(Constants.RING_CODE1);
//////        porData = porHelper.createAPor(porData);
//////        porData.queueStatus="POR Request Complete";
//////        porHelper.updatePorWithQueueStatus(porData);
//////        porTrackerPage.searchForValue(porData.porId,"POR:POR ID");
//////        String projectId = porTrackerPage.searchForValueInGrid("PJ:Project ID","POR:POR ID",porData.porId);
//////        System.out.println ("Project ID_"+projectId);
//////        projectTrackerPage= mainSideMenu.goToProjectTracker();
//////        projectTrackerPage.searchForValue(projectId,"PJ:Project ID");
//////        projectTrackerPage.selectEditOption();
//////        projectTrackerPage.projectStatus();
//////        projectTrackerPage= mainSideMenu.goToProjectTracker();
//////        projectTrackerPage.searchForValue(projectId,"PJ:Project ID");
//////        String response = projectTrackerPage.searchForValueInGrid("PJ:Project Status","PJ:Project ID",projectId);
//////        softAssert.assertContains(response,"Active"," Project Status Changed to Active");
//////        System.out.println(":::"+response);
//////        porHelper.getPorTrackerIdsAndDelete(porData);
//////        softAssert.closeAssert();
//////        }
////
////    @Test(groups = {"Integration"},description = "check if total doc field updates when items are added \n" +
////            "and if the fields unlock",priority = 18)
////    public void verifyTotalDocsField_Project() throws Exception {
////        AssertionsUtil softAssert = new AssertionsUtil();
////        projectTrackerPage= mainSideMenu.goToProjectTracker();
////        projectTrackerPage.searchForValue(PROJECT_ACTIVE.projectId, "PJ:Project ID");
////        //projectTrackerPage.selectEditOption();
////        String text= projectTrackerPage.LeasingDocsTotal();
////        System.out.println(text);
////        softAssert.assertTrue(text.equals("Amendment 1\n" +
////                "Consent Letter Approved by LL"), "Amendment 1 & Consent Letter Approved by LL listed in the PJ: Leasing box");
////        softAssert.closeAssert();
////    }
////
    @Test(groups = {"Integration"},description = "check Task Level Electronic Fields and Status fields part 1",priority = 14)
    public void verifyStructuralModificationDesignComplete() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        System.out.println ("Project ID_"+PROJECT_NEW.projectId);
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(PROJECT_NEW.projectId,"PJ:Project ID");

        projectHelper.uploadDocument(PROJECT_NEW.trackerId.toString(),"PJ_1375_STRUC_MOD_DES", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png");
        projectTrackerPage.selectEditOption();
        String text=projectTrackerPage.AEStructuralModificationDesign();
        System.out.println(text);
        softAssert.assertTrue(text.equals("Pending Review"), "The \"PJ:Structural Modification Design Complete [Status] field  populated with Pending Review.");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "check Task Level Electronic Fields and Status fields part 2",priority = 15)
    public void verifyStructuralModificationDesignStatusToApproved() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        System.out.println ("Project ID_"+activeProjectBundle.projectId);
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(activeProjectBundle.projectId,"PJ:Project ID");
        String response = projectTrackerPage.searchForValueInGrid("PJ:Project Status","PJ:Project ID",activeProjectBundle.projectId);
        softAssert.assertContains(response,"Active"," Project Status Changed to Active");
        System.out.println(":::"+response);

        projectHelper.uploadDocument(activeProjectBundle.trackerId.toString(),"PJ_1375_STRUC_MOD_DES", Constants.IMAGE_FILE_UPLOAD,"simpleImage.png");
        projectTrackerPage.selectEditOption();
        projectTrackerPage.AEStructuralModificationEnableDate();
        projectTrackerPage.selectEditOption();
        String text= projectTrackerPage.updateSiteStatus("Approved");
        System.out.println(text);
        String TodayDate=MiscHelpers.currentDateTime("MM/dd/yyyy");
        softAssert.assertTrue(text.contains(TodayDate),"Today Date Populated");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Project having single POR Program Name and Completion Objective change when Project is un-bundled from POR",priority = 16)
    public void UnBundleProjectFromSinglePOR() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorTrackerPage();
        porTrackerPage.searchForValue(activeProjectBundleSingle.porId,"POR:POR ID");
        addPORPage=porTrackerPage.selectEditOption();
        porTrackerPage = addPORPage.bundleProject();
//        String ProjectID=addPORPage.changePORRequestQueueforBundle();
        System.out.println ("Project ID_"+activeProjectBundleSingle.projectId);
        porTrackerPage.refresh();
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        projectTrackerPage.searchForValue(activeProjectBundleSingle.projectId,"PJ:Project ID");
        String  response = projectTrackerPage.searchForValueInGrid("PJ:Program Name","PJ:Project ID",activeProjectBundleSingle.projectId);
        String compareString="";
        softAssert.assertContains(response,compareString ,"Programe Name is Nill::"+response );
        response = projectTrackerPage.searchForValueInGrid("PJ:Program Name (Element Feed)","PJ:Project ID",activeProjectBundleSingle.projectId);
        softAssert.assertContains(response,compareString," PJ: Program Name (Element Feed) value is null::"+response );
        response = projectTrackerPage.searchForValueInGrid("PJ:Project Completion Objective","PJ:Project ID",activeProjectBundleSingle.projectId);
        softAssert.assertContains(response,compareString ," PJ:Project Completion Objective value is null::"+response );
        //porHelper.getPorTrackerIdsAndDelete(porData);
        softAssert.closeAssert();
    }

//    @Test(groups = {"Integration"},description = "Project having Multiple POR Program Name and Completion Objective change when Project is un-bundled from POR",priority = 17)
//    public void UnBundleProjectFromMultiplePOR() throws Exception {
//        AssertionsUtil softAssert = new AssertionsUtil();
////
////        String ringIdActiveProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
////        Ring ringActiveProject = new Ring("Active", ringIdActiveProject, "Indoor Node");
////        Site siteActiveProject = new Site(ringIdActiveProject,"Primary","Active Site");
////        Por porData = new Por("5GmmW_Stage 2_Site Mod", ringIdActiveProject);
////        Project activeProject = projectHelper.getActiveProject(false,ringActiveProject,siteActiveProject,porData);
//
//        Por secondPor = new Por("AAS_Regional Capacity_Site Mod", activeProjectBundle.ringId);
//        porTrackerPage= mainSideMenu.goToPorTrackerPage();
//        secondPor = porHelper.createAPor(secondPor);
//        porTrackerPage.searchForValue(secondPor.porId,"POR:POR ID");
//        addPORPage=porTrackerPage.selectEditOption();
//        porTrackerPage = addPORPage.BundleMultiplePOR(activeProjectBundle.projectId);
//        addPORPage=porTrackerPage.selectEditOption();
//        String ProjectID1 = addPORPage.changePORRequestQueueforBundle1();
//        System.out.println ("Project ID_"+ProjectID1);
//        driver.navigate().refresh();
//        projectTrackerPage= mainSideMenu.goToProjectTracker();
//        projectTrackerPage.searchForValue(activeProjectBundle.projectId,"PJ:Project ID");
//        String  response = projectTrackerPage.searchForValueInGrid("PJ:Program Name","PJ:Project ID",activeProjectBundle.projectId);
//        System.out.println (response);
//        softAssert.assertContains(response,activeProjectBundle.por.programName,"Program Name is belongs to first POR::"+response  +" __"+activeProjectBundle.por.programName);
//        response = projectTrackerPage.searchForValueInGrid("PJ:Program Name (Element Feed)","PJ:Project ID",activeProjectBundle.projectId);
//        String ProgrameNameElementFeed=activeProjectBundle.por.programName.replace("_Site Mod","");
//        System.out.println("Program Name Element Feed for first POR is_"+ProgrameNameElementFeed);
//        softAssert.assertContains(response,ProgrameNameElementFeed," PJ: Program Name (Element Feed) value is belongs to first POR::"+response );
//        // porHelper.getPorTrackerIdsAndDelete(activeProjectBundle.por);
//        porHelper.getPorTrackerIdsAndDelete(secondPor);
//        // projectHelper.deleteProjectTracker(ProjectID1);
//        softAssert.closeAssert();
//    }

    private String waitForActiveState() throws Exception {
        String response = projectTrackerPage.searchForValueInGrid("PJ:Project Status","PJ:Project ID",PROJECT_NEW.projectId);
        for(int i =0; i<10;i++){
            projectTrackerPage.searchForValue(PROJECT_NEW.projectId,"PJ:Project ID");
            response = projectTrackerPage.searchForValueInGrid("PJ:Project Status","PJ:Project ID",PROJECT_NEW.projectId);
            System.out.println(":::"+response);
            sleepFor(10);
            if(response.contains("Active"))
                break;
        }
        return response;
    }

    private void commonDataGenerator(){
        String ringIdActiveProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActiveProject = new Ring("Active", ringIdActiveProject, "Indoor Node");
        Site siteActiveProject = new Site(ringIdActiveProject,"Primary","Active Site");
        PROJECT_ACTIVE = projectHelper.getActiveProject(false,ringActiveProject,siteActiveProject);
        System.out.println("PROJECT_ACTIVE :: "+PROJECT_ACTIVE.trackerId);
        String ringIdNewProject = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringIdNewProject, "Indoor Node");
        Site site = new Site(ringIdNewProject,"Primary","Active Site");
        PROJECT_NEW = projectHelper.getNewProject(false,ring,site);

        System.out.println("PROJECTNEW :: "+PROJECT_NEW.trackerId);

        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringId, "Indoor Node");
        Site sitePrimary = new Site(ringId,"Primary","Active Site");
        siteHelper.createActiveRingAndPrimaryActiveSite(ringActive,sitePrimary);
        VALID_RING_PRIMARY_BUILD_SITE = ringId;
        System.out.println("VALID_RING_PRIMARY_BUILD_SITE "+VALID_RING_PRIMARY_BUILD_SITE);

        String ringIdActiveProjectBundle = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActiveProjectBundle = new Ring("Active", ringIdActiveProjectBundle, "Indoor Node");
        Site siteActiveProjectBundle = new Site(ringIdActiveProjectBundle,"Primary","Active Site");
        Por porDataBundle = new Por("5GmmW_Stage 2_Site Mod", ringIdActiveProjectBundle);
        activeProjectBundle = projectHelper.getActiveProject(false,ringActiveProjectBundle,siteActiveProjectBundle,porDataBundle);

        String ringIdActiveProjectBundleSingle = "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActiveProjectBundleSingle = new Ring("Active", ringIdActiveProjectBundleSingle, "Indoor Node");
        Site siteActiveProjectBundleSingle = new Site(ringIdActiveProjectBundleSingle,"Primary","Active Site");
        Por porDataBundleSingle = new Por("5GmmW_Stage 2_Site Mod", ringIdActiveProjectBundleSingle);
        activeProjectBundleSingle = projectHelper.getActiveProject(false,ringActiveProjectBundleSingle,siteActiveProjectBundleSingle,porDataBundleSingle);

        ringId = "LX" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring activeRing = new Ring("Active", ringId, "Indoor Node");
        Site newSite = new Site(ringId,"Primary","Active Site");
        ACTIVE_RING_NEW_SITE = siteHelper.createActiveRingNewSite(activeRing,newSite);
    }
}
