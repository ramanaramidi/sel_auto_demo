package web.mb.feature.fops;

import common.BaseTest;
import commons.constants.Constants;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Site;
import java.lang.reflect.Method;
import java.util.List;

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

public class FOPSSafetyTests extends BaseTest {

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
    SiteHelper siteHelper = new SiteHelper();
    RingHelper ringHelper = new RingHelper();

    public FOPSSafetyTests() {
        if (envURL == null) {
            envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");
        }
        if (testSuite == null) {
            testSuite = "sectorSet.xml";
        }
    }

    @Test(groups = { "Integration" }, description = "login", priority = 1)
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
        site = new Site(ringId, "Candidate", "New Site");
        site = siteHelper.createNewRingAndSite(ring, site);
    }

  @Test(groups = { "Integration" }, description = "login", priority = 2)
  public void verifyingFieldTechSection_Safety(Method method) throws Exception {
    AssertionsUtil softAssert = new AssertionsUtil();
    siteFopsPage = mainSideMenu.goToSiteTrackerFops();
    siteFopsPage.searchForValue(site.siteId, "S:Site Code");
    siteFopsPage.selectEditOption();
    siteFopsPage.goToFopsTab();
    softAssert.assertTrue(
      siteFopsPage.validateFieldIsDisplayed("S:Site Security Issues"),
      "Site Security Issues Is Present"
    );
    softAssert.assertTrue(
      siteFopsPage.validateField_IsDisplayed("S:EME Audit Signage Expiration Date"),
      "EME Audit Signage Expiration Date Section Is Present"
    );
    softAssert.assertTrue(
      siteFopsPage.validateField_IsDisplayed("S:EME Audit Last Performed Date"),
      "EME Audit Last Performed Date Section Is Present"
    );
    softAssert.assertTrue(
      siteFopsPage.validateField_IsDisplayed("S:EME Signage Visit Required"),
      "EME Signage Visit Required Section Is Present"
    );
    softAssert.assertTrue(
      siteFopsPage.validateField_IsDisplayed("S:Site Backhaul Type"),
      "Site Backhaul Type Section Is Present"
    );
    softAssert.assertTrue(
      siteFopsPage.validateField_IsDisplayed("S:EME Document Uploaded By"),
      "EME Document Uploaded By Section Is Present"
    );
    softAssert.assertTrue(
      siteFopsPage.validateField_IsDisplayed("S:EME Document Uploaded User"),
      "EME Document Uploaded User Section Is Present"
    );
    softAssert.assertTrue(
      siteFopsPage.validateField_IsDisplayed("S:Copy EME To Doc Tracker Error"),
      "Copy EME To Doc Tracker Error Section Is Present"
    );
    softAssert.assertTrue(
      siteFopsPage.validateField_IsDisplayed("S:EME Audit Photos [Doc]"),
      "EME Audit Photos Is Present"
    );
    softAssert.assertTrue(
      siteFopsPage.validateFieldIsDisplayed(
        "S:Obstruction Lighting/Marking Required?"
      ),
      "Obstruction Lighting/Marking Required Section Is Present"
    );
    softAssert.assertTrue(
      siteFopsPage.validateFieldIsDisplayed("S:FAA Notam Contact"),
      "FAA Notam Contact Section Is Present"
    );
    softAssert.assertTrue(
      siteFopsPage.validateFieldIsDisplayed("S:Site Safety Comments"),
      "Site Safety Comments Section Is Present"
    );
    softAssert.assertTrue(
      siteFopsPage.validateFieldIsDisplayed("S:Site Environmental Hazards"),
      "Site Environmental Hazards Section Is Present"
    );
    siteFopsPage.switchToTrackerPageByCancel();
    softAssert.closeAssert();
  }

  @Test(groups = { "Integration" }, description = "login", priority = 3)
  public void verifyingSiteSecurityIssue_Safety(Method method)
    throws Exception {
    AssertionsUtil softAssert = new AssertionsUtil();
    siteFopsPage = mainSideMenu.goToSiteTrackerFops();
    siteFopsPage.searchForValue(site.siteId, "S:Site Code");
    siteFopsPage.selectEditOption();
    siteFopsPage.goToFopsTab();
    softAssert.assertTrue(
      siteFopsPage.veryfySiteSecuruity(),
      "Site Security DropDown Initial value is null"
    );
    siteFopsPage.switchToTrackerPageByCancel();
    softAssert.closeAssert();
  }
    @Test(groups = { "Integration" }, description = "login", priority = 4)
    public void verifyingSignageVisitRequired_Safety(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(
                siteFopsPage.verifySignageVisitInitialValue(),
                "Signage Visit Initial value is null"
        );
        siteFopsPage.switchToTrackerPageByCancel();
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "login", priority = 5)
    public void verifyingFields_Safety(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(
                siteFopsPage.isFieldDropDown("S:Site Security Issues"),
                ":Site Security Issues is a dropdown"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldDropDown("S:EME Signage Visit Required"),
                "signage visit is a drop down"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyDocumentUploadedByTextField(),
                "document uploaded ny is a text field"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyDocTrackerErrorTextField(),
                "doc tracker error is text field"
        );
        softAssert.assertTrue(
                siteFopsPage.verifySiteSafetyCommentTextField(),
                "site safety comment is text field"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyEnvironmentalHazardTextField(),
                "site Environmental hazard is text field"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyBackhaulTypeTextField(),
                "site Environmental hazard is text field"
        );
        softAssert.assertTrue(
                siteFopsPage.verifyFaaNotamTextField(),
                "site Environmental hazard is text field"
        );
        softAssert.assertNotNull(
                siteFopsPage.validateDocumentUploadedByUserFieldIsReadOnly(),
                "document uploaded by user is readonly"
        );
        softAssert.assertNotNull(
                siteFopsPage.validateDocTrackerErrorFieldIsReadOnly(),
                "DocTrackerErrorFieldIsReadOnly"
        );
        siteFopsPage.switchToTrackerPageByCancel();
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "login", priority = 6)
    public void verifyingSiteSecurityDropDownValue_Safety(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        List<String> options = siteFopsPage.fieldDropDownValues(
                "S:Site Security Issues"
        );
        softAssert.assertTrue(
                options.contains("Don’t go to the site during night"),
                "Value is Present"
        );
        softAssert.assertTrue(
                options.contains("Don’t go to the site during specified hours"),
                "Value is Present"
        );
        softAssert.assertTrue(
                options.contains("Security Escort Required"),
                "Value is Present"
        );
        softAssert.assertTrue(
                options.contains("Two Techs Required"),
                "Value is Present"
        );
        siteFopsPage.switchToTrackerPageByCancel();
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "login", priority = 7)
    public void verifyingSignageVisitRequiredDropDownValue_Safety(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId,"S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        List<String> options = siteFopsPage.fieldDropDownValues(
                "S:EME Signage Visit Required"
        );
        softAssert.assertTrue(
                options.contains("No"),
                "signage visit dropdown contain No value"
        );
        softAssert.assertTrue(
                options.contains("Yes"),
                "signage visit dropdown contain Yes value"
        );
        siteFopsPage.switchToTrackerPageByCancel();
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "login", priority = 8)
    public void validateAuditPhotosIcon_Safety(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId,"S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertContains(
                siteFopsPage.validateAuditPhotosIcon(),
                "icon",
                "audit photo icon is present"
        );
        siteFopsPage.switchToTrackerPageByCancel();
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "login", priority = 9)
    public void updatingSiteSecurityDropDownValue_Safety(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        siteFopsPage.updateMandatoryFields();
        softAssert.assertTrue(
                siteFopsPage.updateSiteSecurityWithFirstOption(),
                "Field has updated with first option"
        );
        softAssert.assertTrue(
                siteFopsPage.updateSiteSecurityWithSecondOption(),
                "Field has updated with second option"
        );
        softAssert.assertTrue(
                siteFopsPage.updateSiteSecurityWithThirdOption(),
                "Field has updated with third option"
        );
        softAssert.assertTrue(
                siteFopsPage.updateSiteSecurityWithFourthOption(),
                "Field has updated with fourth option"
        );
        siteFopsPage.switchToSiteTracker();
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "login", priority = 10)
    public void updatingSignageVisitDropDownValue_Safety(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        siteFopsPage.updateMandatoryFields();
        softAssert.assertTrue(
                siteFopsPage.updateSignageVisitWithFirstOption(),
                "Field has updated with first option"
        );
        softAssert.assertTrue(
                siteFopsPage.signageVisitLockVerification("S:EME Signage Visit Required"),
                "signage visit field has locked"
        );
        siteFopsPage.clickTheLockedIcon();
        softAssert.assertTrue(
                siteFopsPage.updateSignageVisitWithSecondOption(),
                "Field has updated with second option"
        );
        softAssert.assertTrue(
                siteFopsPage.signageVisitLockVerification("S:EME Signage Visit Required"),
                "signage visit field has locked"
        );
        siteFopsPage.switchToSiteTracker();
        softAssert.closeAssert();
    }


    @Test(groups = { "Integration" }, description = "login", priority = 11)
    public void validateSiteSafetyComments_Safety(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        siteFopsPage.updateMandatoryFields();
        softAssert.assertTrue(
                siteFopsPage.siteSafetyCommentsValidation(),
                "site safety comments field allowed 4000 characters"
        );
        siteFopsPage.switchToSiteTracker();
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "login", priority = 12)
    public void validateSiteEnvironmentalHazard_Safety(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        siteFopsPage.updateMandatoryFields();
        softAssert.assertTrue(
                siteFopsPage.siteEnvironmentalHazardValidation(),
                "site Environmental Hazard field allowed 4000 characters"
        );
        siteFopsPage.switchToSiteTracker();
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "login", priority = 13)
    public void verifyingFaaNotamContactIsEditable_Safety(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertNull(siteFopsPage.validateFaaNotamIsNotReadOnly_Safety(), "FAA Notam Contact Field Is Editable");
        siteFopsPage.switchToTrackerPageByCancel();
        softAssert.closeAssert();
    }
    @Test(groups = { "Integration" }, description = "login", priority = 14)
    public void updateObstructionLightingWithoutFaaNotam_Safety(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
//        softAssert.assertTrue(siteFopsPage.updateObstructionLighting_safety(),"can't update Obstruction Lighting Without Faa Notam");
        siteFopsPage.switchToTrackerPageByCancel();
        softAssert.closeAssert();

    }
    @Test(groups = { "Integration" }, description = "login", priority = 15)
    public void updateObstructionLightingWithFaaNotam_Safety(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(site.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFopsTab();
        softAssert.assertTrue(siteFopsPage.updateObstructionLightingWithFaaNotam_safety(), "can update Obstruction Lighting Without Faa Notam");
        siteFopsPage.switchToSiteTracker();
        softAssert.closeAssert();
    }

/*
  @Test(groups = { "Integration" }, description = "login", priority = 2)
  public void validateLastPerformedDate_Safety(Method method) throws Exception {
    projectHelper.uploadDocument(
            site.trackerId.toString(),
            "S_EME_AUDIT_DOCUMENT",
            Constants.IMAGE_FILE_UPLOAD,
            "simpleImage.png"
    );
    AssertionsUtil softAssert = new AssertionsUtil();
    siteFopsPage = mainSideMenu.goToSiteTrackerFops();
    siteFopsPage.searchForValue(site.siteId, "S:Site Code");
    siteFopsPage.selectEditOption();
    siteFopsPage.goToFopsTab();
    siteFopsPage.updateMandatoryFields();
    siteFopsPage.goToRiotTab();
    //assert for img in riot tab
    siteFopsPage.validateTheImageInRiotTab();

    //change tab and check


    softAssert.assertTrue(
            siteFopsPage.auditLastPerformedDateValidation(),
            "last performed date is auto populated"
    );

    softAssert.assertTrue(
            siteFopsPage.auditSignageExpirationDateValidation(),
            "Audit signage Expiration date is auto populated"
    );
    softAssert.assertTrue(
            siteFopsPage.expirationDateValidation(),
            "expiration date is 450 days after the last performed date"
    );

    softAssert.assertTrue(
            siteFopsPage.documentUploadedByValidation(),
            "document uploaded by is auto populated"
    );
    siteFopsPage.switchToSiteTracker();
    softAssert.closeAssert();
  }

 */
}
