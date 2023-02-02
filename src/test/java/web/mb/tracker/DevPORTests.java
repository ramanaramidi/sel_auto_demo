package web.mb.tracker;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import org.testng.annotations.Test;
import pages.web.Tracker.AddPORPage;
import pages.web.Tracker.PORTrackerPage;
import pages.web.Tracker.RFSectorPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import utility.helper.AssertionsUtil;

import java.lang.reflect.Method;

public class DevPORTests  extends BaseTest {

    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    PORTrackerPage porTrackerPage;
    AddPORPage addPORPage;
    RFSectorPage rfSectorPage;
    boolean smallToBig;


    public DevPORTests() {
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
        mainSideMenu = loginPage.LoginAsUser(superUser);

    }
    @Test(groups = {"Integration"},description = "Create New Program",priority = 2)
    public void createNewProgram() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage = mainSideMenu.goToPorAdminTrackerPage();
        porTrackerPage = porTrackerPage.addNewProgramPage();
        String parentWindow = porTrackerPage.switchToTrackerPage();
        porTrackerPage.addNewProgram();
        softAssert.assertTrue(porTrackerPage.getProgramConcat(),"Program Dropdown should be concatenated with the PlanType_ReleaseVersion_Category ");
        porTrackerPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Edit the Program with required fields",priority = 3)
    public void updateFieldsForProgram() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorAdminTrackerPage();
        porTrackerPage.searchForValue(porTrackerPage.Program_dropdown,"PAT:Program Dropdown");
        porTrackerPage.selectEditOption();
        porTrackerPage.updateFields();
        softAssert.assertTrue(porTrackerPage.verifyProgramDetailsUpdate(),"user should be able to edit the program and the details should match");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Verify POR Admin Tracker is Present",priority = 4)
    public void verifyPORAdminTracker() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorTrackerPage();
        softAssert.assertTrue(mainSideMenu.isPORAdminTrackerPresent(),"POR Admin Tracker should be displayed");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Select Grid stats from Grid Options",priority = 5)
    public void selectGridStatsFromGridOptions() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorAdminTrackerPage();
        porTrackerPage = porTrackerPage.selectGridStats();
        String parentWindow = porTrackerPage.switchToTrackerPage();
        softAssert.assertTrue(porTrackerPage.verifyStats(),"Stats Should be Null");
        porTrackerPage.selectPlanType();
        softAssert.assertFalse(porTrackerPage.verifyStats(),"Stats will be Displayed");
        porTrackerPage.switchToTrackerCancel(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Admin Add Page and click on Apply",priority = 6)
    public void porAdminProgramNameByASC_Por(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorAdminTrackerPage();
        porTrackerPage.sortingASCOrderPat();
        String response1 = porTrackerPage.searchForValueInGrid("PAT:Program Name",1);
        String response2 = porTrackerPage.searchForValueInGrid("PAT:Program Name",2);
        softAssert.assertTrue(porTrackerPage.verifyProgramNameSortingOrder(response1,response2,smallToBig=true),"POR Program created Date Sorting ascending Done Successfully");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Admin Add Page and click on Apply",priority = 7)
    public void porAdminProgramNameByDSC_Por(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorAdminTrackerPage();
        porTrackerPage.sortingDSCOrderPat();
        String response1 = porTrackerPage.searchForValueInGrid("PAT:Program Name",1);
        String response2 = porTrackerPage.searchForValueInGrid("PAT:Program Name",2);
        softAssert.assertTrue(porTrackerPage.verifyProgramNameSortingOrder(response1,response2,smallToBig=false),"POR Program created Date Sorting ascending Done Successfully");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Admin Add Page and click on Apply",priority = 8)
    public void createdPORProgramDateByASC_Por(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorAdminTrackerPage();
        porTrackerPage.sortingASCOrderDatePat();
        String response1 = porTrackerPage.searchForValueInGrid("PAT:Created Date",1);
        String response2 = porTrackerPage.searchForValueInGrid("PAT:Created Date",2);
        softAssert.assertTrue(porTrackerPage.verifyDatesSortingOrder(response1,response2,smallToBig=true),"POR Program created Date Sorting ascending Done Successfully");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Enter the Mandatory fields in POR Admin Add Page and click on Apply",priority = 9)
    public void createdPORProgramDateByDESC_Por(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorAdminTrackerPage();
        porTrackerPage.sortingDSCOrderDatePat();
        String response1 = porTrackerPage.searchForValueInGrid("PAT:Created Date",1);
        String response2 = porTrackerPage.searchForValueInGrid("PAT:Created Date",2);
        softAssert.assertTrue(porTrackerPage.verifyDatesSortingOrder(response1,response2,smallToBig=false),"POR Program created Date Sorting descending Done Successfully");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Verify Active Program Name present in POR Tracker",priority = 10)
    public void searchActiveProgramNameInPORTracker() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorTrackerPage();
        porTrackerPage.fullScreen();
        porTrackerPage.clickAddNewPORButton();
        String programName =porTrackerPage.VerifyProgramNameInPORTracker("test_101_New Build");
        softAssert.assertContains(programName,"test_101_New Build","Should be Available in the POR Tracker");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Verify Inactive Program Name present in POR Tracker",priority = 11)
    public void searchInactiveProgramNameInPORTracker() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorTrackerPage();
        porTrackerPage.fullScreen();
        porTrackerPage.clickAddNewPORButton();
        String programName =porTrackerPage.VerifyProgramNameInPORTracker(porTrackerPage.Program_dropdown);
        softAssert.assertNotEquals(programName,porTrackerPage.Program_dropdown,"Should not be available in the POR Tracker");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Delete Inactive Program",priority = 12)
    public void deleteInactiveProgram() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage= mainSideMenu.goToPorAdminTrackerPage();
        porTrackerPage.searchForValue(porTrackerPage.Program_dropdown,"PAT:Program Dropdown");
        porTrackerPage.deleteSelection();
        softAssert.closeAssert();
    }
}