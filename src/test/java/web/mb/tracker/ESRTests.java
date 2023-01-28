package web.mb.tracker;

import common.BaseTest;
import commons.constants.Constants;
import commons.enums.LoginOptionEnum;
import commons.objects.Por;
import commons.objects.Project;
import commons.objects.Ring;
import commons.objects.Site;
import org.testng.annotations.Test;
import pages.web.Tracker.ESRTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.project.ProjectHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;
import java.util.List;

public class ESRTests extends BaseTest {

    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    ESRTrackerPage esrTrackerPage;
    ProjectHelper projectHelper = new ProjectHelper();
    Project PROJECT_ACTIVE;
    boolean smallToBig;


    public ESRTests() {
        {
            if (envURL == null) {
                envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");
            }
            if (testSuite == null) {
                testSuite = "sectorSet.xml";
            }
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
    @Test(groups = {"Integration"},description = "Verify Description ID Navigation",priority = 2)
    public void verifyDescIdNavigation(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        esrTrackerPage = mainSideMenu.goToFields();
        esrTrackerPage.searchForValue("Memo","Data Type");
        esrTrackerPage.selectEditOption();
        softAssert.assertTrue(esrTrackerPage.isLinkClickable(),"Hash link next to Description is Clickable");
        softAssert.closeAssert();
    }
    //@Test(groups = {"Integration"}, description = "Update Phone Number for Current User", priority = 3)
    public void updatePhoneNumber(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        esrTrackerPage = mainSideMenu.userSettings();
        esrTrackerPage.updatePhoneNumber();
        esrTrackerPage = mainSideMenu.userSettings();
        softAssert.assertFalse(esrTrackerPage.getPhoneNumber(), "Phone Number should be Displayed");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Update Phone number for existing user", priority = 4)
    public void updatePhoneNumberExistingUser(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        esrTrackerPage = mainSideMenu.goToSystemUsers();
        esrTrackerPage.searchForValue("100189678", "User ID");
        esrTrackerPage.selectEditOption();
        esrTrackerPage.updatePhoneNumberExistingUser();
        softAssert.assertFalse(esrTrackerPage.getPhoneNumberExistingUser(), "Phone Number should be Displayed");
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "Verify Welcome Page is displayed after clicking main side menu T-Mobile Icon", priority = 5)
    public void verifyWelcomePageNavigation(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        esrTrackerPage = mainSideMenu.clickMainSideLogo();
        softAssert.assertTrue(esrTrackerPage.verifyWelcomePage(), "Welcome Page is Displayed");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "VNavigation to Component field History Dialog", priority = 6)
    public void verifyComponentHistory(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        esrTrackerPage = mainSideMenu.goToDocument();
        esrTrackerPage.selectEditOption();
        softAssert.assertTrue(esrTrackerPage.verifyComponentFieldHistory(), "Component Field History dialog is Displayed");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Verify refresh Column is selectable",priority = 7)
    public void refreshColumnByASC(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        esrTrackerPage= mainSideMenu.goToProcesses();
        esrTrackerPage.searchForValue("Report","Process Type");
        esrTrackerPage.sortingASCOrder();
        softAssert.assertTrue(esrTrackerPage.verifySortingOrder(smallToBig=true),"Refresh Column is now selectable and able get order in ASC");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"},description = "Verify refresh Column is selectable",priority = 8)
    public void refreshColumnByDSC(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        esrTrackerPage= mainSideMenu.goToProcesses();
        esrTrackerPage.searchForValue("Report","Process Type");
        esrTrackerPage.sortingDescOrder();
        softAssert.assertTrue(esrTrackerPage.verifySortingOrder(smallToBig=false),"Refresh Column is now Clickable and able get order in DSC");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Verify Sticky Menu", priority = 20)
    public void verifyStickyMainMenu(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        loginPage = mainSideMenu.userLogoff();
        loginPage.userLogin(alphaUser);
        mainSideMenu = loginPage.LoginAsUser(nonSuper);
        esrTrackerPage = mainSideMenu.userSettings();
        esrTrackerPage.setStickyMainMenu("Yes");
        esrTrackerPage.clickAppCenter();
        softAssert.assertTrue(mainSideMenu.isProjectTrackerPresent(),"Project Tracker should be displayed");
        softAssert.assertTrue(mainSideMenu.isRingTrackerPresent(),"Ring Tracker should be displayed");
        softAssert.assertTrue(mainSideMenu.isPORTrackerPresent(),"POR Tracker should be displayed");
        mainSideMenu.userSettings();
        esrTrackerPage.setStickyMainMenu("No");
        softAssert.assertFalse(mainSideMenu.isProjectTrackerPresent(),"Project Tracker should not be displayed");
        softAssert.assertFalse(mainSideMenu.isRingTrackerPresent(),"Ring Tracker should not be displayed");
        softAssert.assertFalse(mainSideMenu.isPORTrackerPresent(),"POR Tracker should not be displayed");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Deprecate Creation of new reports with PDF", priority = 10)
    public void deprecateCreationOfNewPDF(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        esrTrackerPage = mainSideMenu.goToReports();
        esrTrackerPage.selectAddOption();
        List<String> reportFormatList = esrTrackerPage.validateDataTypePDF();
        int sizeBefore = reportFormatList.size();
        System.out.println(sizeBefore);
        reportFormatList.remove("PDF");
        int sizeAfter = reportFormatList.size();
        System.out.println(sizeAfter);
        softAssert.assertEquals(sizeBefore,sizeAfter,"Deprecate Creation of New report with PDF is verified Successfully");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "Verify Image is available in Field history after it was added", priority = 10)
    public void verifyImagesInFieldHistory(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        esrTrackerPage = mainSideMenu.goToProject();
        esrTrackerPage.searchForValue(PROJECT_ACTIVE.projectId,"PJ:Project ID");
        projectHelper.uploadDocument(PROJECT_ACTIVE.trackerId.toString(), "PJ_ADD_NTP_DOC", Constants.IMAGE_FILE_UPLOAD, "simpleImage.png");
        esrTrackerPage.selectEditOption();
        esrTrackerPage.goToNTPConstructionTab();
        softAssert.assertContains(esrTrackerPage.getImagesListFieldHistory(),"png","Image is now Available in Field History");
        softAssert.closeAssert();
    }
}