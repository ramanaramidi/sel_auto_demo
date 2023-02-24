package web.mb.feature.fops;

import common.BaseTest;
import commons.constants.Constants;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Site;
import java.lang.reflect.Method;
import org.testng.annotations.Test;
import pages.web.Tracker.*;
import pages.web.Tracker.site.AddSitePage;
import pages.web.Tracker.site.SiteFopsPage;
import pages.web.Tracker.site.SiteTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.project.ProjectHelper;
import rest.ring.RingHelper;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

public class FOPSRiotTests extends BaseTest {

    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    RingTrackerPage ringTracker;
    SiteTrackerPage siteTracker;
    AddSitePage addNewSite;

    SiteFopsPage siteFopsPage;
    AddRingPage addNewRing;
    String ringCode = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
    String siteCodeCancel =
            "SAU" + MiscHelpers.getRandomString(5, true).toUpperCase();
    ProjectHelper projectHelper = new ProjectHelper();
    String siteCode = "AU" + MiscHelpers.getRandomString(6, true).toUpperCase();
    String siteCodeWith9Characters = ringCode + "AB";
    String commonRingId;
    Site site;
    Site site1;
    Site site2;
    Site site3;
    Site site4;
    Site site5;
    SiteHelper siteHelper = new SiteHelper();
    RingHelper ringHelper = new RingHelper();

    public FOPSRiotTests() {
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
        generateCommonData();
        mainSideMenu = loginPage.LoginAsUser(superUser);
    }

    private void generateCommonData() {
        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringId, "Macro");
        site = new Site(ringId, "Candidate", "Active");
        site = siteHelper.createNewRingAndSite(ring, site);

        String ringId1 = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring1 = new Ring("Active", ringId1, "Macro");
        site1 = new Site(ringId1, "Candidate", "Active");
        site1 = siteHelper.createNewRingAndSite(ring1, site1);

        String ringId2 = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring2 = new Ring("Active", ringId2, "Macro");
        site2 = new Site(ringId2, "Candidate", "Active");
        site2 = siteHelper.createNewRingAndSite(ring2, site2);

        String ringId3 = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring3 = new Ring("Active", ringId3, "Macro");
        site3 = new Site(ringId3, "Candidate", "Active");
        site3 = siteHelper.createNewRingAndSite(ring3, site3);

        String ringId4 = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring4 = new Ring("Active", ringId4, "Macro");
        site4 = new Site(ringId4, "Candidate", "Active");
        site4 = siteHelper.createNewRingAndSite(ring4, site4);

        String ringId5 = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring5 = new Ring("Active", ringId5, "Macro");
        site5 = new Site(ringId2, "Candidate", "Active");
        site5 = siteHelper.createNewRingAndSite(ring5, site5);
    }

    @Test(groups = {"Integration"}, description = "login", priority = 2)
    public void verifyingFieldTechSection_Riot(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        softAssert.assertTrue(siteFopsPage.riotTabVerification(), "riot tab is present");
        siteFopsPage.goToSiteTracker();
        softAssert.closeAssert();
    }

    @Test(groups = {"Integration"}, description = "login", priority = 2)
    public void verifyingRiotTabSection_fops(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToRiotTab_SiteRiot();
        softAssert.assertTrue(siteFopsPage.verifySieRiotIsDisplayed_SiteRiot(), "Site riot Section Is Present");
        softAssert.assertTrue(siteFopsPage.verifyRiotPhotoUploadIsDisplayed_SiteRiot(), "riot photo upload Section Is Present");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed_SiteRiot("S:EME Audit Photos [Doc]"), "EME Audit Photos [Doc] field Is Present");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed_SiteRiot("S:EME Audit Signage Expiration Date"), "EME Audit Signage Expiration Date field Is Present");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed_SiteRiot("S:EME Audit Last Performed Date"), "EME Audit Last Performed Date field Is Present");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed_SiteRiot("S:EME Document Uploaded By"), "EME Document Uploaded field Is Present");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:RIOT Photo Project Values"), "RIOT Photo Project Values field Is Present");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:RIOT Site Photo Category"), "RIOT Site Photo Category field Is Present");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:RIOT Site Photo Status"), "RIOT Site Photo Status field Is Present");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:RIOT Site Photo"), "RIOT Site Photo field Is Present");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:RIOT Photo Uploaded By"), "RIOT Photo Uploaded By field Is Present");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:RIOT Document Upload Error"), "RIOT Document Upload Error field Is Present");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:RIOT Site Photo Type"), "S:RIOT Site Photo Type field Is Present");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:RIOT Photo Project"), "S:RIOT Photo Project field Is Present");
        softAssert.assertTrue(siteFopsPage.validateFieldIsDisplayed("S:RIOT User Photo Upload Date"), "S:RIOT User Photo Upload Date field Is Present");
        siteFopsPage.switchToTrackerPageByCancel();
        softAssert.closeAssert();
    }
    @Test(groups = {"Integration"}, description = "login", priority = 2)
    public void verifyingSiteRiotFieldsReadOnly_SiteRiot(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToRiotTab_SiteRiot();
        softAssert.assertNotNull(siteFopsPage.validateAuditLastPerformedDate_SiteRiot(), "Audit last performed Field Is readonly");
        softAssert.assertNotNull(siteFopsPage.validateDocumentUploadedBy_SiteRiot(), "Document uploaded by Field Is readonly");
        softAssert.assertNotNull(siteFopsPage.validateAuditSignageExpirationDate_SiteRiot(), "Audit Signage Expiration date Field Is readonly");
        siteFopsPage.switchToTrackerPageByCancel();
        softAssert.closeAssert();
    }

    //@Test(groups = {"Integration"}, description = "login", priority = 2)
    public void verifyingSiteRiotFields_SiteRiot(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToRiotTab_SiteRiot();
        softAssert.assertNull(siteFopsPage.validateAuditPhotosIsEditable_SiteRiot(), "Audit Photos Field Is Editable");
        projectHelper.uploadDocument(site.trackerId.toString(), "S_EME_AUDIT_DOCUMENT", Constants.IMAGE_FILE_UPLOAD, "simpleImage.png");
        softAssert.assertTrue(siteFopsPage.auditPhotosValidation_SiteRiot(),"photo uploaded without any error");
        softAssert.assertTrue(siteFopsPage.documentUploadedByValidation_SiteRiot(), "document uploaded by field is auto populated");
        softAssert.assertTrue(siteFopsPage.lastPerformedDateValidation_SiteRiot(), "last Performed field is auto populated");
        softAssert.assertTrue(siteFopsPage.auditSignageExpirationDateValidation_SiteRiot(), "audit Signage Expiration Date field is auto populated");
        siteFopsPage.switchToSiteTracker();
        softAssert.closeAssert();
    }


/*
    @Test(groups = {"Integration"}, description = "login", priority = 2)
    public void verifyingAuditPhotoDoc_SiteRiot(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToRiotTab_SiteRiot();
        projectHelper.uploadDocument(site.trackerId.toString(), "S_EME_AUDIT_DOCUMENT", Constants.IMAGE_FILE_UPLOAD, "simpleImage.png");
        softAssert.assertTrue(siteFopsPage.auditPhotosValidation_SiteRiot(),"photo uploaded without any error");
        siteFopsPage.switchToSiteTracker();
        softAssert.closeAssert();
    }

 */

    //@Test(groups = {"Integration"}, description = "login", priority = 2)
    public void verifyingCurrentVersion_SiteRiot(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site1.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToRiotTab_SiteRiot();
        projectHelper.uploadDocument(site1.trackerId.toString(), "S_EME_AUDIT_DOCUMENT", Constants.IMAGE_FILE_UPLOAD, "simpleImage.png");
        siteFopsPage.clickTheLockedIcon_riot();
        projectHelper.uploadDocument(site1.trackerId.toString(), "S_EME_AUDIT_DOCUMENT", Constants.IMAGE_FILE_UPLOAD, "simpleImage.png");
        siteFopsPage.goToDocumentTab();
        softAssert.assertTrue(siteFopsPage.currentVersionValidation_SiteRiot(), "current Version is checked");
        siteFopsPage.switchToSiteTracker();
        softAssert.closeAssert();
    }
    //@Test(groups = {"Integration"}, description = "login", priority = 2)
    public void verifyingLastPerformedDate_SiteRiot(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site2.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToRiotTab_SiteRiot();
        projectHelper.uploadDocument(site2.trackerId.toString(), "S_EME_AUDIT_DOCUMENT", Constants.IMAGE_FILE_UPLOAD, "simpleImage.png");
        String firstValue = siteFopsPage.getFirstDocumentText_SiteRiot();
        sleepFor(60);
        projectHelper.uploadDocument(site2.trackerId.toString(), "S_EME_AUDIT_DOCUMENT", Constants.IMAGE_FILE_UPLOAD, "simpleImage.png");
        String secondValue = siteFopsPage.getSecondDocumentText_SiteRiot();
        softAssert.assertNotEquals(firstValue,secondValue,"last performed field has refreshed when different document has uploaded");
        siteFopsPage.goToSiteTracker();
        softAssert.closeAssert();
    }
    //@Test(groups = {"Integration"}, description = "login", priority = 2)
    public void verifyingAuditExpirationDate_SiteRiot(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site3.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToRiotTab_SiteRiot();
        projectHelper.uploadDocument(site3.trackerId.toString(), "S_EME_AUDIT_DOCUMENT", Constants.IMAGE_FILE_UPLOAD, "simpleImage.png");
        softAssert.assertTrue(siteFopsPage.auditExpirationValidation_SiteRiot(),"audit Expiration date is auto populated");
        softAssert.assertTrue(siteFopsPage.expirationDateValidation_SiteRiot(),"expiration date is 450 days after the last performed date");
        String firstValue = siteFopsPage.getFirstDocumentExpiryDate_SiteRiot();
        sleepFor(60);
        projectHelper.uploadDocument(site3.trackerId.toString(), "S_EME_AUDIT_DOCUMENT", Constants.IMAGE_FILE_UPLOAD, "simpleImage.png");
        String secondValue = siteFopsPage.getSecondDocumentExpiryDate_SiteRiot();
        softAssert.assertNotEquals(firstValue,secondValue,"Audit Expiry Date field has refreshed when different document has uploaded");
        siteFopsPage.goToSiteTracker();
        softAssert.closeAssert();

    }
    //@Test(groups = {"Integration"}, description = "login", priority = 2)
    public void verifyingDocumentUploadedBy_SiteRiot(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site4.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToRiotTab_SiteRiot();
        projectHelper.uploadDocument(site4.trackerId.toString(), "S_EME_AUDIT_DOCUMENT", Constants.IMAGE_FILE_UPLOAD, "simpleImage.png");
        softAssert.assertTrue(siteFopsPage.documentUploadedByValidation_SiteRiot(),"audit Expiration date is auto populated");
        String firstValue = siteFopsPage.getFirstDocumentUploadedBy_SiteRiot();
        siteFopsPage.applyAndGoToSiteTracker();

        loginPage = mainSideMenu.userLogoff();
        loginPage.userLogin(alphaUser);

        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site5.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToRiotTab_SiteRiot();
        projectHelper.uploadDocument(site5.trackerId.toString(), "S_EME_AUDIT_DOCUMENT", Constants.IMAGE_FILE_UPLOAD, "simpleImage.png");
        String secondValue = siteFopsPage.getSecondDocumentUploadedBy_SiteRiot();
        softAssert.assertNotEquals(firstValue,secondValue,"Audit Expiry Date field has refreshed when different document has uploaded");
        siteFopsPage.goToSiteTracker();
        softAssert.closeAssert();
    }





}
