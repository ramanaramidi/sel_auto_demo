package web.mb.feature.power;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Site;
import org.testng.annotations.Test;
import pages.web.Tracker.AddCabinetPage;
import pages.web.Tracker.CabinetTrackerPage;
import pages.web.Tracker.PowerCabinetPage;
import pages.web.Tracker.site.SiteTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class PowerRectifierTests extends BaseTest {

    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    PowerCabinetPage powerCabinetPage;
    CabinetTrackerPage cabinetTrackerPage;
    AddCabinetPage addCabinetPage;
    Site Site_Active;
    SiteHelper siteHelper = new SiteHelper();
    SiteTrackerPage siteTrackerPage;
    public PowerRectifierTests() {
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
        }generateCommonData();
        mainSideMenu = loginPage.LoginAsUser(superUser);
    }

    private void generateCommonData() {
        String ringId_Active =
                "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring_Active = new Ring("Active", ringId_Active, "Indoor Node");
        Site site_Active = new Site(ringId_Active, "Final Build", "Active Site");
        Site_Active =
                siteHelper.createActiveRingAndPrimaryActiveSite(ring_Active, site_Active);
    }
    @Test(groups = {"Integration"}, description = "verifyCabinetTrackerRectifierField", priority = 2)
    public void verifyCabinetTrackerRectifierField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Number of Rectifiers Supported (max) "), "CAB:Number of Rectifiers Supported (max)  field is displayed in Cabinet Tracker");
        softAssert.assertTrue(addCabinetPage.validateFieldIsDisplayed("CAB:Max Rectifiers"), "CAB:Max Rectifiers field is displayed in Cabinet Tracker");
    }
    @Test(groups = {"Integration"}, description = "verifyCabinetTrackerRectifierField_Updated", priority = 3)
    public void verifyCabinetTrackerRectifierField_Updated(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(Site_Active.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String count = addCabinetPage.validateNumberOfRectifiersSupported_Updated("CAB:Number of Rectifiers Supported (max) ");
        softAssert.assertTrue(count.length()>0,"CAB:Number of Rectifiers Supported (max)  field is displayed in Cabinet Tracker");
        String MaxCount = addCabinetPage.validateMaxRectifiersCount_Updated("CAB:Max Rectifiers");
        softAssert.assertTrue(MaxCount.length()>0, "CAB:Max Rectifiers field is displayed in Cabinet Tracker");
    }
    @Test(groups = {"Integration"}, description = "verifyCABERectifier_VendorDropDownField", priority = 4)
    public void verifyCABERectifier_VendorDropDownField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        powerCabinetPage = mainSideMenu.goToCabinetEquipment_Tracker();
        powerCabinetPage.searchForValue("06TESTAA_1000130_CABE3000002","CABE:Cabinet Equipment ID");
        powerCabinetPage.clickEquipmentLink();
        String parentWindow = powerCabinetPage.switchToProjectPage();
        powerCabinetPage.clickRectifierTab();
        String value = "Nokia";
        softAssert.assertTrue(powerCabinetPage.verifyCABRectifier_VendorField(value),"Dropdown Field is Present");
        powerCabinetPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyCABERectifiers_VendorDropDownFields", priority = 5)
    public void verifyCABERectifiers_VendorDropDownFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        powerCabinetPage = mainSideMenu.goToCabinetEquipment_Tracker();
        String equipmentId = "6WP1208E_1000140_CABE3000000";
        powerCabinetPage.searchForValue(equipmentId, "CABE:Cabinet Equipment ID");
        powerCabinetPage.selectEditOption();
        String parentWindow = powerCabinetPage.switchToProjectPage();
        String value = "Nokia";
        String data = "355";
        String status = "In Use";
//        softAssert.assertTrue(powerCabinetPage.verifyCABRectifiers_VendorFields(value,data,status),"Dropdown Field is Present");
        powerCabinetPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyCabinetEquipmentTrackerFields", priority = 6)
    public void verifyCabinetEquipmentTrackerFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        powerCabinetPage = mainSideMenu.goToCabinetEquipment_Tracker();
        String equipmentId = "6WP1208E_1000140_CABE3000000";
        powerCabinetPage.searchForValue(equipmentId, "CABE:Cabinet Equipment ID");
        powerCabinetPage.selectEditOption();
        String parentWindow = powerCabinetPage.switchToProjectPage();
        softAssert.assertTrue(powerCabinetPage.validateFieldIsDisplayed("CAB:Cabinet ID"), "CAB:Cabinet ID field is displayed in Cabinet Equipment Tracker");
        softAssert.assertTrue(powerCabinetPage.validateFieldIsDisplayed("CABE:Cabinet Equipment ID"), "CABE:Cabinet Equipment ID field is displayed in Cabinet Equipment Tracker");
        softAssert.assertTrue(powerCabinetPage.validateFieldIsDisplayed("CABE:Cabinet Equipment Type"), "CABE:Cabinet Equipment Type field is displayed in Cabinet Equipment Tracker");
        powerCabinetPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyCabinetEquipmentType_dropdownFields", priority = 7)
    public void verifyCabinetEquipmentType_dropdownFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        powerCabinetPage = mainSideMenu.goToCabinetEquipment_Tracker();
        String equipmentId = "6WP1208E_1000140_CABE3000000";
        powerCabinetPage.searchForValue(equipmentId, "CABE:Cabinet Equipment ID");
        powerCabinetPage.selectEditOption();
        String parentWindow = powerCabinetPage.switchToProjectPage();
        String value = "Rectifier";
        softAssert.assertTrue(powerCabinetPage.verifyCabinetEquipmentType_dropdownField(value), "CABE:Cabinet Equipment Type field is displayed in Cabinet Equipment Tracker");
        powerCabinetPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyCabinetEquipmentTrackerPage_Buttons", priority = 8)
    public void verifyCabinetEquipmentTrackerPage_Buttons(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        powerCabinetPage = mainSideMenu.goToCabinetEquipment_Tracker();
        softAssert.assertTrue(powerCabinetPage.isEdit_buttonDisplayed(),"Edit button is displayed in Cabinet Equipment Tracker Page");
        softAssert.assertTrue(powerCabinetPage.isAdd_buttonDisplayed(),"Add button is displayed in Cabinet Equipment Tracker Page");
        softAssert.assertTrue(powerCabinetPage.isRowEditor_buttonDisplayed(),"Row Editor button is displayed in Cabinet Equipment Tracker Page");
        softAssert.assertTrue(powerCabinetPage.isExport_buttonDisplayed(),"Export button is displayed in Cabinet Equipment Tracker Page");
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyCabinetEquipmentTrackerPage_AddButton", priority = 9)
    public void verifyCabinetEquipmentTrackerPage_AddButton(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        powerCabinetPage = mainSideMenu.goToCabinetEquipment_Tracker();
        powerCabinetPage.clickAddButton();
        String parentWindow = powerCabinetPage.switchToProjectPage();
        softAssert.assertTrue(powerCabinetPage.validateField_IsDisplayed("CAB:Cabinet ID"), "CAB:Cabinet ID field is displayed in Cabinet Equipment Tracker");
        softAssert.assertTrue(powerCabinetPage.validateFieldIsDisplayed("CABE:Cabinet Equipment ID"), "CABE:Cabinet Equipment ID field is displayed in Cabinet Equipment Tracker");
        softAssert.assertTrue(powerCabinetPage.validateFieldIsDisplayed("CABE:Cabinet Equipment Type"), "CABE:Cabinet Equipment Type field is displayed in Cabinet Equipment Tracker");
        powerCabinetPage.switchToTracker_Cancel(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyCABE_CabinetIdValues", priority = 10)
    public void verifyCABE_CabinetIdValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        powerCabinetPage = mainSideMenu.goToCabinetEquipment_Tracker();
        powerCabinetPage.clickAddButton();
        String parentWindow = powerCabinetPage.switchToProjectPage();
        softAssert.assertContains(powerCabinetPage.verifyCabinetIdField(),"6WP1208E_1000140","Cabinet ID values are displayed in Cabinet Equipment Tracker");
        powerCabinetPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyCABEEquipment_RectifierDetails", priority = 11)
    public void verifyCABEEquipment_RectifierDetails(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        powerCabinetPage = mainSideMenu.goToCabinetEquipment_Tracker();
        powerCabinetPage.searchForValue("06TESTAA_1000130_CABE3000002","CABE:Cabinet Equipment ID");
        powerCabinetPage.clickEquipmentLink();
        String parentWindow = powerCabinetPage.switchToProjectPage();
        softAssert.assertContains(powerCabinetPage.verifyCabinetEquipmentType(),"Rectifier","Cabinet Equipment Type should be Rectifier");
        powerCabinetPage.clickRectifierTab();
        softAssert.assertTrue(powerCabinetPage.validateField_IsDisplayed("CAB:Cabinet Vendor"), "CAB:Cabinet Vendor * field is displayed in Cabinet Tracker");
        softAssert.assertTrue(powerCabinetPage.validateFieldIsDisplayed("CABE:Active"), "CABE:Active field is displayed in Cabinet Equipment Tracker");
        softAssert.assertTrue(powerCabinetPage.validateFieldIsDisplayed("CABE:REC Model"), "CABE:REC Model field is displayed in Cabinet Equipment Tracker");
        softAssert.assertTrue(powerCabinetPage.validateFieldIsDisplayed("CABE:REC RFDS ID"), "CABE:REC RFDS ID field is displayed in Cabinet Equipment Tracker");
        softAssert.assertTrue(powerCabinetPage.validateFieldIsDisplayed("CABE:REC Shelf"), "CABE:REC Shelf field is displayed in Cabinet Tracker");
        softAssert.assertTrue(powerCabinetPage.validateFieldIsDisplayed("CAB:Cabinet Detail ID"), "CAB:Cabinet Detail ID field is displayed in Cabinet Equipment Tracker");
        softAssert.assertTrue(powerCabinetPage.validateFieldIsDisplayed("CABE:REC Rectifier Detail ID"), "CABE:REC Rectifier Detail ID field is displayed in Cabinet Equipment Tracker");
        softAssert.assertTrue(powerCabinetPage.validateFieldIsDisplayed("CAB:Cabinet ID"), "CAB:Cabinet ID field is displayed in Cabinet Equipment Tracker");
        softAssert.assertTrue(powerCabinetPage.validateField_IsDisplayed("CABE:Cabinet Equipment ID"), "CABE:Cabinet Equipment ID field is displayed in Cabinet Equipment Tracker");
        powerCabinetPage.switchToTracker(parentWindow);
//        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "verifyCABERectifiers_VendorDropDownField", priority = 12)
    public void verifyCABERectifiers_ActiveStatusDropDownField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        powerCabinetPage = mainSideMenu.goToCabinetEquipment_Tracker();
        powerCabinetPage.searchForValue("06TESTAA_1000130_CABE3000002","CABE:Cabinet Equipment ID");
        powerCabinetPage.clickEquipmentLink();
        String parentWindow = powerCabinetPage.switchToProjectPage();
        powerCabinetPage.clickRectifierTab();
        String status = "No";
        softAssert.assertTrue(powerCabinetPage.verifyCABRectifiers_ActiveStatusField(status),"Dropdown Field is Present");
        powerCabinetPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyCABE_RECModelDropDownField", priority = 13)
    public void verifyCABE_RECModelDropDownField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        powerCabinetPage = mainSideMenu.goToCabinetEquipment_Tracker();
        powerCabinetPage.searchForValue("06TESTAA_1000130_CABE3000002","CABE:Cabinet Equipment ID");
        powerCabinetPage.clickEquipmentLink();
        String parentWindow = powerCabinetPage.switchToProjectPage();
        String data = "393";
        powerCabinetPage.clickRectifierTab();
        softAssert.assertContains(powerCabinetPage.verifyDotsDropDownField_CabinetModel(data),"020303A.101_Nokia Supreme Battery Backup System","Cabinet Model dropdown is displayed and allows to select a value from options");
        powerCabinetPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyCABE_REC_RFDSIDField", priority = 14)
    public void verifyCABE_REC_RFDSIDField(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        powerCabinetPage = mainSideMenu.goToCabinetEquipment_Tracker();
        powerCabinetPage.searchForValue("6WP1208E_1000140_CABE3000000", "CABE:Cabinet Equipment ID");
        powerCabinetPage.clickEquipmentLink();
        String parentWindow = powerCabinetPage.switchToProjectPage();
        powerCabinetPage.clickRectifierTab();
        softAssert.assertTrue(powerCabinetPage.verifyCABE_REC_RFDSIDField(),"CABE_REC_RFDSID field displays options");
        //  softAssert.assertContains(powerCabinetPage.verifyREC_RFDSIDfield(),"testing","CABE_REC_RFDSID field is updated with data");
        powerCabinetPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "verifyCABERectifier_RECShelfFieldInfo", priority = 15)
    public void verifyCABERectifier_RECShelfFieldInfo(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        powerCabinetPage = mainSideMenu.goToCabinetEquipment_Tracker();
        powerCabinetPage.searchForValue("6WP1208E_1000140_CABE3000000", "CABE:Cabinet Equipment ID");
        powerCabinetPage.clickEquipmentLink();
        String parentWindow = powerCabinetPage.switchToProjectPage();
        powerCabinetPage.clickRectifierTab();
        String value = "Field Info";
        softAssert.assertTrue(powerCabinetPage.verifyCABERectifierRECShelfField(),"CABERectifier_RECShelf field displays options");
        softAssert.assertTrue(powerCabinetPage.verifyCABERectifierRECShelfFieldInfo(value),"Field Info page is displayed");
        powerCabinetPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "verifyCABERectifier_RECShelfFieldHistory", priority = 16)
    public void verifyCABERectifier_RECShelfFieldHistory(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        powerCabinetPage = mainSideMenu.goToCabinetEquipment_Tracker();
        powerCabinetPage.searchForValue("6WP1208E_1000140_CABE3000000", "CABE:Cabinet Equipment ID");
        powerCabinetPage.clickEquipmentLink();
        String parentWindow = powerCabinetPage.switchToProjectPage();
        powerCabinetPage.clickRectifierTab();
        String value1 = "Field History";
        softAssert.assertTrue(powerCabinetPage.verifyCABERectifierRECShelfFieldHistory(value1),"Field History page is displayed");
        powerCabinetPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }
 /*  // @Test(groups = {"Integration"}, description = "verifyCABERectifier_RECShelfFieldComments", priority = 17)
    public void verifyCABERectifier_RECShelfFieldComments(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        powerCabinetPage = mainSideMenu.goToCabinetEquipment_Tracker();
        powerCabinetPage.searchForValue("6WP1208E_1000140_CABE3000000", "CABE:Cabinet Equipment ID");
        powerCabinetPage.clickEquipmentLink();
        String parentWindow = powerCabinetPage.switchToProjectPage();
        powerCabinetPage.clickRectifierTab();
        String value2 = "Comments";
        softAssert.assertContains(powerCabinetPage.verifyRectifierRECShelfCommentsField(value2),"testing","CABE_REC_RFDSID field is updated with data");
        powerCabinetPage.switchToTracker(parentWindow);
        softAssert.closeAssert();
    }*/
}
