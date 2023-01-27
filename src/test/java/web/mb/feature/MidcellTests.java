package web.mb.feature;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.*;
import org.testng.annotations.Test;
import pages.web.Tracker.AddPORPage;
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

public class MidcellTests extends BaseTest {
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    public EnvConfig envConfig = new EnvConfig();
    Project PROJECT_M_MIDCELL_Node;
    Project PROJECT_N_MIDCELL_Node;
    Project PROJECT_M_MIDCELL_Hub;
    Project PROJECT_N_MIDCELL_Hub;

    Por porHubM;
    Por porHubN;
    Por porNodeM;
    Por porNodeN;
    PORTrackerPage porTrackerPage;
    AddPORPage addPORPage;
    ProjectTrackerPage projectTrackerPage;
    ProjectHelper projectHelper = new ProjectHelper();
    PorHelper porHelper = new PorHelper();
    SiteHelper siteHelper = new SiteHelper();

    public MidcellTests()
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
        generateTestData();
        mainSideMenu = loginPage.LoginAsUser(superUser);

    }

    private void generateTestData(){
        String ringMHubId = "MD" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringMHub = new Ring("Active", ringMHubId, "Midcell");
        Site siteMHub = new Site(ringMHubId,"Primary","Active Site");
        siteMHub = siteHelper.createActiveRingAndSite(ringMHub,siteMHub);
        porHubM = new Por("Equipment Upgrade_Midcell Donor_Site Mod", ringMHubId);
//        PROJECT_M_MIDCELL_Hub = projectHelper.getActiveProject(false,ringActiveProjectBundleSingle,siteActiveProjectBundleSingle,porDataBundleSingle);
//        System.out.println("Project __"+PROJECT_M_MIDCELL_Hub.projectId);
        porHubM = porHelper.createAPor(porHubM);

        porHubN = new Por("N_midcell_hub_1_New Build", ringMHubId);
        porHubN = porHelper.createAPor(porHubN);

        porNodeN = new Por("New Build_Midcell_New Build", ringMHubId);
        porNodeN = porHelper.createAPor(porNodeN);

        porNodeM = new Por("MidCell Strategy_Atlanta_Site Mod", ringMHubId);
        porNodeM = porHelper.createAPor(porNodeM);

        //Need to be updated in the future

        PROJECT_M_MIDCELL_Node = new Project("PJMID01A-0002249239",null,null,null,null);
        PROJECT_N_MIDCELL_Node = new Project("PJMID01A-0002249238",null,null,null,null);
        PROJECT_M_MIDCELL_Hub = new Project("PJMID01A-0002249227",null,null,null,null);
        PROJECT_N_MIDCELL_Hub = new Project("PJMID01A-0002249242",null,null,null,null);

    }

    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply",priority = 5)
    public void cbePorCheckForMidcellHubM(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorTrackerPage();
        Por porData = porHubM;
        if(porData!=null){
            porTrackerPage.searchForValue(porData.porId,"POR:POR ID");
            softAssert.assertTrue(porTrackerPage.isValuePresentInGrid("POR:POR ID"),"Value POR:POR ID Should Be Present");
            addPORPage=porTrackerPage.selectEditOption();
            addPORPage.switchToProject();
            softAssert.assertContains(addPORPage.cbeForecastDetails("POR:Forecast Plan Type - POR"),"PT_EQUIPMENT_UPGRADE","POR:Forecast Plan Type - POR");
            softAssert.assertContains(addPORPage.cbeForecastDetails("POR:CBE Category - POR"),"PT_CAPACITY_OTHER","POR:CBE Category - POR");
            softAssert.assertContains(addPORPage.cbeForecastDetails("POR:OneStream FCPT - POR"),"PT_CAPACITY_OTHER_FC","POR:OneStream FCPT - POR");
            //softAssert.assertContains(addPORPage.cbeForecastDetails(""),"","");
            porTrackerPage = addPORPage.returnToPORTracker();
        }
        else {
            softAssert.assertTrue(false,"Seems like an issue occurred when creating the POR");
        }
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply",priority = 5)
    public void cbePorCheckForMidcellHubN(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorTrackerPage();
        Por porData = porHubN;
        if(porData!=null){
            porTrackerPage.searchForValue(porData.porId,"POR:POR ID");
            softAssert.assertTrue(porTrackerPage.isValuePresentInGrid("POR:POR ID"),"Value POR:POR ID Should Be Present");
            addPORPage=porTrackerPage.selectEditOption();
            addPORPage.switchToProject();
            softAssert.assertContains(addPORPage.cbeForecastDetails("POR:Forecast Plan Type - POR"),"PT_BACKHAUL_UPGRADE","POR:Forecast Plan Type - POR");
            softAssert.assertContains(addPORPage.cbeForecastDetails("POR:CBE Category - POR"),"PT_BACKHAUL","POR:CBE Category - POR");
            softAssert.assertContains(addPORPage.cbeForecastDetails("POR:OneStream FCPT - POR"),"PT_NEW_BUILD_FC","POR:OneStream FCPT - POR");
            //softAssert.assertContains(addPORPage.cbeForecastDetails(""),"","");
            porTrackerPage = addPORPage.returnToPORTracker();
        }
        else {
            softAssert.assertTrue(false,"Seems like an issue occurred when creating the POR");
        }
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply",priority = 5)
    public void cbePorCheckForMidcellNodeM(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorTrackerPage();
        Por porData = porNodeM;
        if(porData!=null){
            porTrackerPage.searchForValue(porData.porId,"POR:POR ID");
            softAssert.assertTrue(porTrackerPage.isValuePresentInGrid("POR:POR ID"),"Value POR:POR ID Should Be Present");
            addPORPage=porTrackerPage.selectEditOption();
            addPORPage.switchToProject();
            softAssert.assertContains(addPORPage.cbeForecastDetails("POR:Forecast Plan Type - POR"),"PT_EQUIPMENT_UPGRADE","POR:Forecast Plan Type - POR");
            softAssert.assertContains(addPORPage.cbeForecastDetails("POR:CBE Category - POR"),"PT_CAPACITY_OTHER","POR:CBE Category - POR");
            softAssert.assertContains(addPORPage.cbeForecastDetails("POR:OneStream FCPT - POR"),"PT_CAPACITY_OTHER_FC","POR:OneStream FCPT - POR");
            //softAssert.assertContains(addPORPage.cbeForecastDetails(""),"","");
            porTrackerPage = addPORPage.returnToPORTracker();
        }
        else {
            softAssert.assertTrue(false,"Seems like an issue occurred when creating the POR");
        }
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply",priority = 5)
    public void cbePorCheckForMidcellNodeN(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorTrackerPage();
        Por porData = porNodeN;
        if(porData!=null){
            porTrackerPage.searchForValue(porData.porId,"POR:POR ID");
            softAssert.assertTrue(porTrackerPage.isValuePresentInGrid("POR:POR ID"),"Value POR:POR ID Should Be Present");
            addPORPage=porTrackerPage.selectEditOption();
            addPORPage.switchToProject();
            softAssert.assertContains(addPORPage.cbeForecastDetails("POR:Forecast Plan Type - POR"),"PT_NSD_OTHER","POR:Forecast Plan Type - POR");
            softAssert.assertContains(addPORPage.cbeForecastDetails("POR:CBE Category - POR"),"PT_NEW_BUILD","POR:CBE Category - POR");
            softAssert.assertContains(addPORPage.cbeForecastDetails("POR:OneStream FCPT - POR"),"PT_NEW_BUILD_FC","POR:OneStream FCPT - POR");
            //softAssert.assertContains(addPORPage.cbeForecastDetails(""),"","");
            porTrackerPage = addPORPage.returnToPORTracker();
        }
        else {
            softAssert.assertTrue(false,"Seems like an issue occurred when creating the POR");
        }
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply",priority = 5)
    public void setFiberForMidcellHubM(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorTrackerPage();
        Por porData = porHubM;
        if(porData!=null){
            porTrackerPage.searchForValue(porData.porId,"POR:POR ID");
            softAssert.assertTrue(porTrackerPage.isValuePresentInGrid("POR:POR ID"),"Value POR:POR ID Should Be Present");
            addPORPage=porTrackerPage.selectEditOption();
            addPORPage.switchToProject();
            softAssert.assertTrue(addPORPage.getFiberDate().isEmpty(),"Date Should be Empty");
            addPORPage.setFiberOption("Fiber");
            softAssert.assertFalse(addPORPage.getFiberDate().isEmpty(),"Date Should Not be Empty");
            addPORPage.setFiberOption("No Fiber");
            //softAssert.assertTrue(addPORPage.getFiberDate().isEmpty(),"Date Should be Empty");
            //softAssert.assertTrue(addPORPage.setNotes("POR:Fiber Notes","test"),"Date Should be Empty");

            softAssert.assertTrue(addPORPage.getDarkFiberDate().isEmpty(),"Date Should be Empty");
            addPORPage.setDarkFiberOption("Dark Fiber");
            softAssert.assertFalse(addPORPage.getDarkFiberDate().isEmpty(),"Date Should Not be Empty");
            //softAssert.assertTrue(addPORPage.setNotes("POR:Dark Fiber Notes","test"),"Date Should be Empty");
            addPORPage.setDarkFiberOption("No Dark Fiber");
            softAssert.assertTrue(addPORPage.getDarkFiberDate().isEmpty(),"Date Should be Empty");

            porTrackerPage = addPORPage.returnToPORTracker();
        }
        else {
            softAssert.assertTrue(false,"Seems like an issue occurred when creating the POR");
        }
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply",priority = 5)
    public void setFiberForMidcellHubN(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorTrackerPage();
        Por porData = porHubN;
        if(porData!=null){
            porTrackerPage.searchForValue(porData.porId,"POR:POR ID");
            softAssert.assertTrue(porTrackerPage.isValuePresentInGrid("POR:POR ID"),"Value POR:POR ID Should Be Present");
            addPORPage=porTrackerPage.selectEditOption();
            addPORPage.switchToProject();
            softAssert.assertTrue(addPORPage.getFiberDate().isEmpty(),"Date Should be Empty");
            addPORPage.setFiberOption("Fiber");
            softAssert.assertFalse(addPORPage.getFiberDate().isEmpty(),"Date Should Not be Empty");
            addPORPage.setFiberOption("No Fiber");
           // softAssert.assertTrue(addPORPage.getFiberDate().isEmpty(),"Date Should be Empty");

            softAssert.assertTrue(addPORPage.getDarkFiberDate().isEmpty(),"Date Should be Empty");
            addPORPage.setDarkFiberOption("Dark Fiber");
            softAssert.assertFalse(addPORPage.getDarkFiberDate().isEmpty(),"Date Should Not be Empty");
            addPORPage.setDarkFiberOption("No Dark Fiber");
            softAssert.assertTrue(addPORPage.getDarkFiberDate().isEmpty(),"Date Should be Empty");
            porTrackerPage = addPORPage.returnToPORTracker();
        }
        else {
            softAssert.assertTrue(false,"Seems like an issue occurred when creating the POR");
        }
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply",priority = 5)
    public void setFiberForMidcellNodeN(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorTrackerPage();
        Por porData = porNodeN;
        if(porData!=null){
            porTrackerPage.searchForValue(porData.porId,"POR:POR ID");
            softAssert.assertTrue(porTrackerPage.isValuePresentInGrid("POR:POR ID"),"Value POR:POR ID Should Be Present");
            addPORPage=porTrackerPage.selectEditOption();
            addPORPage.switchToProject();
            softAssert.assertTrue(addPORPage.getFiberDate().isEmpty(),"Date Should be Empty");
            addPORPage.setFiberOption("Fiber");
            softAssert.assertFalse(addPORPage.getFiberDate().isEmpty(),"Date Should Not be Empty");
            addPORPage.setFiberOption("No Fiber");
          //  softAssert.assertTrue(addPORPage.getFiberDate().isEmpty(),"Date Should be Empty");

            softAssert.assertTrue(addPORPage.getDarkFiberDate().isEmpty(),"Date Should be Empty");
            addPORPage.setDarkFiberOption("Dark Fiber");
            softAssert.assertFalse(addPORPage.getDarkFiberDate().isEmpty(),"Date Should Not be Empty");
            addPORPage.setDarkFiberOption("No Dark Fiber");
            softAssert.assertTrue(addPORPage.getDarkFiberDate().isEmpty(),"Date Should be Empty");
            porTrackerPage = addPORPage.returnToPORTracker();
        }
        else {
            softAssert.assertTrue(false,"Seems like an issue occurred when creating the POR");
        }
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply",priority = 5)
    public void setFiberForMidcellNodeM(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorTrackerPage();
        Por porData = porNodeM;
        if(porData!=null){
            porTrackerPage.searchForValue(porData.porId,"POR:POR ID");
            softAssert.assertTrue(porTrackerPage.isValuePresentInGrid("POR:POR ID"),"Value POR:POR ID Should Be Present");
            addPORPage=porTrackerPage.selectEditOption();
            addPORPage.switchToProject();
            softAssert.assertTrue(addPORPage.getFiberDate().isEmpty(),"Date Should be Empty");
            addPORPage.setFiberOption("Fiber");
            softAssert.assertFalse(addPORPage.getFiberDate().isEmpty(),"Date Should Not be Empty");
            addPORPage.setFiberOption("No Fiber");
           // softAssert.assertTrue(addPORPage.getFiberDate().isEmpty(),"Date Should be Empty");

            softAssert.assertTrue(addPORPage.getDarkFiberDate().isEmpty(),"Date Should be Empty");
            addPORPage.setDarkFiberOption("Dark Fiber");
            softAssert.assertFalse(addPORPage.getDarkFiberDate().isEmpty(),"Date Should Not be Empty");
            addPORPage.setDarkFiberOption("No Dark Fiber");
            softAssert.assertTrue(addPORPage.getDarkFiberDate().isEmpty(),"Date Should be Empty");
            porTrackerPage = addPORPage.returnToPORTracker();
        }
        else {
            softAssert.assertTrue(false,"Seems like an issue occurred when creating the POR");
        }
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply",priority = 5)
    public void verifyMidcellHubMProject(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String projectId = PROJECT_M_MIDCELL_Hub.projectId;
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        if(projectId!=null){
            projectTrackerPage.searchForValue(projectId,"PJ:Project ID");
            projectTrackerPage.selectEditOption();

            projectTrackerPage.switchToProjectPage();

            softAssert.assertTrue(projectTrackerPage.verifyCurrentValueInTextField("PJ:Project ID").equals(projectId),"projectId should match");
            softAssert.assertTrue(projectTrackerPage.verifyCurrentValueInDropDownField("PJ:Project Type").equals("M-Midcell Hub"),"The Values Should Match");
            projectTrackerPage.returnToProjectTracker();
        }
        else {
            softAssert.assertTrue(false,"Seems like an issue occurred when creating the PROJECT");
        }
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply",priority = 5)
    public void verifyMidcellHubNProject(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String projectId = PROJECT_N_MIDCELL_Hub.projectId;
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        if(projectId!=null){
            projectTrackerPage.searchForValue(projectId,"PJ:Project ID");
            projectTrackerPage.selectEditOption();

            projectTrackerPage.switchToProjectPage();

            softAssert.assertTrue(projectTrackerPage.verifyCurrentValueInTextField("PJ:Project ID").equals(projectId),"projectId should match");
            softAssert.assertTrue(projectTrackerPage.verifyCurrentValueInDropDownField("PJ:Project Type").equals("N-Midcell Hub"),"The Values Should Match");
            projectTrackerPage.returnToProjectTracker();
        }
        else {
            softAssert.assertTrue(false,"Seems like an issue occurred when creating the PROJECT");
        }
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply",priority = 5)
    public void verifyMidcellNodeNProject(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String projectId = PROJECT_N_MIDCELL_Node.projectId;
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        if(projectId!=null){
            projectTrackerPage.searchForValue(projectId,"PJ:Project ID");
            projectTrackerPage.selectEditOption();

            projectTrackerPage.switchToProjectPage();

            softAssert.assertTrue(projectTrackerPage.verifyCurrentValueInTextField("PJ:Project ID").equals(projectId),"projectId should match");
            softAssert.assertTrue(projectTrackerPage.verifyCurrentValueInDropDownField("PJ:Project Type").equals("N-Midcell Node"),"The Values Should Match");
            projectTrackerPage.returnToProjectTracker();
        }
        else {
            softAssert.assertTrue(false,"Seems like an issue occurred when creating the PROJECT");
        }
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Add Page and click on Apply",priority = 5)
    public void verifyMidcellNodeMProject(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String projectId = PROJECT_M_MIDCELL_Node.projectId;
        projectTrackerPage= mainSideMenu.goToProjectTracker();
        if(projectId!=null){
            projectTrackerPage.searchForValue(projectId,"PJ:Project ID");
            projectTrackerPage.selectEditOption();

            projectTrackerPage.switchToProjectPage();

            softAssert.assertTrue(projectTrackerPage.verifyCurrentValueInTextField("PJ:Project ID").equals(projectId),"projectId should match");
            softAssert.assertTrue(projectTrackerPage.verifyCurrentValueInDropDownField("PJ:Project Type").equals("M-Midcell Node"),"The Values Should Match");
            projectTrackerPage.returnToProjectTracker();
        }
        else {
            softAssert.assertTrue(false,"Seems like an issue occurred when creating the PROJECT");
        }
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Validate Dark Fiber fields For N-MidCellHub", priority = 6)
    public void validateDarkFiberForNMidCellHub(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        String ProjectActive = "PJMID01A-0002249242";
        projectTrackerPage.searchForValue(ProjectActive, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        softAssert.assertTrue(projectTrackerPage.validateLockIsEnabled("PJ:Backhaul Task Selection"), "PJ:Backhaul Task Selection is Enabled");
        projectTrackerPage.setDarkFiber();
        projectTrackerPage.goToTransportTab();
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:Design Received (1380) [Doc]"), "Task is unNA'd");
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:Design Approved (1381) [Doc]"), "Task is unNA'd");
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(P 1865) Fiber Fronthaul Permits Submitted"), "Task is unNA'd");
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(P 1866) Fiber Fronthaul Permit Approved"), "Task is unNA'd");
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(P 2490) Pricing Received"), "Task is unNA'd");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Validate Dark Fiber fields For M-MidCellHub", priority = 7)
    public void validateDarkFiberForMMidCellHub(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        String ProjectActive = "PJMID01A-0002249227";
        projectTrackerPage.searchForValue(ProjectActive, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        softAssert.assertFalse(projectTrackerPage.validateLockIsEnabled("PJ:Backhaul Task Selection"), "Should be Locked");
        projectTrackerPage.goToTransportTab();
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 1865) Fiber Fronthaul Permits Submitted"), "Task is NA'd");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 1866) Fiber Fronthaul Permit Approved"), "Task is NA'd");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 2490) Pricing Received"), "Task is NA'd");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 3885) Date Submitted to NSC"), "Task is NA'd");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 4960) Fronthaul Construction Start"), "Task is NA'd");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Validate Dark Fiber fields For N-MidCellNode", priority = 8)
    public void validateDarkFiberForNMidCellNode(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        String ProjectActive = "PJMID01A-0002249238";
        projectTrackerPage.searchForValue(ProjectActive, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        softAssert.assertTrue(projectTrackerPage.validateLockIsEnabled("PJ:Backhaul Task Selection"), "PJ:Backhaul Task Selection is Enabled");
        projectTrackerPage.setDarkFiber();
        projectTrackerPage.goToTransportTab();
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:Design Received (1380) [Doc]"), "Task is unNA'd");
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:Design Approved (1381) [Doc]"), "Task is unNA'd");
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(P 1865) Fiber Fronthaul Permits Submitted"), "Task is unNA'd");
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(P 1866) Fiber Fronthaul Permit Approved"), "Task is unNA'd");
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(P 2490) Pricing Received"), "Task is unNA'd");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Validate Dark Fiber fields For M-MidCellNode", priority = 9)
    public void validateDarkFiberForMMidCellNode(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        String ProjectActive = "PJMID01A-0002249239";
        projectTrackerPage.searchForValue(ProjectActive, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        softAssert.assertFalse(projectTrackerPage.validateLockIsEnabled("PJ:Backhaul Task Selection"), "Should be Locked");
        projectTrackerPage.goToTransportTab();
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 1865) Fiber Fronthaul Permits Submitted"), "Task is NA'd");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 1866) Fiber Fronthaul Permit Approved"), "Task is NA'd");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 2490) Pricing Received"), "Task is NA'd");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 3885) Date Submitted to NSC"), "Task is NA'd");
        softAssert.assertNotNull(projectTrackerPage.isDocsReadOnly("PJ(P 4960) Fronthaul Construction Start"), "Task is NA'd");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Validate Tasks related to N-MidCell Hub", priority = 10)
    public void validateNMidCellHubFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        String ProjectActive = "PJMID01A-0002249242";
        projectTrackerPage.searchForValue(ProjectActive, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectTrackerPage.goToLeasingTab();
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:Lease Drawings Approved (1675) [Doc]"), "Task is unNA'd");
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:Notice Letter Sent (1625) [Doc]"), "Task is unNA'd");
        projectTrackerPage.goToZoningAndPermittingTab();
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:Zoning Applied (3550) [Doc]"), "Task is unNA'd");
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:Zoning Approved (3575) [Doc]"), "Task is unNA'd");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Validate Tasks related to M-MidCell Hub", priority = 11)
    public void validateMMidCellHubFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        String ProjectActive = "PJMID01A-0002249227";
        projectTrackerPage.searchForValue(ProjectActive, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectTrackerPage.goToLeasingTab();
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:Notice Letter Delivery Confirmed (1650) [Doc]"), "Task is unNA'd");
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ(P 1900) Lease Agreement Drafted / Received"), "Task is unNA'd");
        projectTrackerPage.goToZoningAndPermittingTab();
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:Building Permit Applied (3650) [Doc]"), "Task is unNA'd");
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:Building Permit Approved  (3675) [Doc]"), "Task is unNA'd");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Validate Tasks related to N-MidCell Node", priority = 12)
    public void validateNMidCellNodeFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        String ProjectActive = "PJMID01A-0002249238";
        projectTrackerPage.searchForValue(ProjectActive, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectTrackerPage.goToLeasingTab();
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(P 1925) Lease Executed by TMO"), "Task is unNA'd");
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(P 1950) Lease Executed by LL"), "Task is unNA'd");
        projectTrackerPage.goToZoningAndPermittingTab();
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:Receive/Upload Geotech Study (1775) [Doc]"), "Task is unNA'd");
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:Soils Resist Received (1875) [Doc]"), "Task is unNA'd");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Validate Tasks related to M-MidCell Node", priority = 13)
    public void validateMMidCellNodeFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        projectTrackerPage = mainSideMenu.goToProjectTracker();
        String ProjectActive = "PJMID01A-0002249239";
        projectTrackerPage.searchForValue(ProjectActive, "PJ:Project ID");
        projectTrackerPage.selectEditOption();
        String parentWindow = projectTrackerPage.switchToProjectPage();
        projectTrackerPage.goToLeasingTab();
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(P 1950) Lease Executed by LL"), "Task is unNA'd");
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:Consent Letter Approved by LL (2010)[Doc]"), "Task is unNA'd");
        projectTrackerPage.goToZoningAndPermittingTab();
        softAssert.assertNull(projectTrackerPage.isDocsReadOnly("PJ(P 1575) Zoning Drawings Ordered"), "Task is unNA'd");
        softAssert.assertTrue(projectTrackerPage.validateTaskField("PJ:Zoning Drawings Received (1600) [Doc]"), "Task is unNA'd");
        projectTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
}
