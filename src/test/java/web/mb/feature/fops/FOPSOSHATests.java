package web.mb.feature.fops;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Site;
import java.lang.reflect.Method;
import java.util.List;

import org.testng.annotations.Test;
import pages.web.Tracker.ProjectTrackerPage;
import pages.web.Tracker.site.AddSitePage;
import pages.web.Tracker.site.SiteFopsPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

public class FOPSOSHATests extends BaseTest {

    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    Site Site_Active;
    Site SiteActive;
    SiteHelper siteHelper = new SiteHelper();
    SiteFopsPage siteFopsPage;

    public FOPSOSHATests() {
        if (envURL == null) {
            envConfig.setWebUrl("https://magentabuiltstg.t-mobile.com/Login.do");
        }
        if (testSuite == null) {
            testSuite = "feature.xml";
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
        String ringId_Active =
                "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring_Active = new Ring("Active", ringId_Active, "Indoor Node");
        Site site_Active = new Site(ringId_Active, "Primary", "Active Site");
        Site_Active =
                siteHelper.createActiveRingAndPrimaryActiveSite(ring_Active, site_Active);

        String ringIdActive =
                "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActive, "Indoor Node");
        Site siteActive = new Site(
                ringIdActive,
                "Primary",
                "Building",
                "Roof Top Mount",
                "Build To Suit",
                "Active Site"
        );
        SiteActive =
                siteHelper.createActiveRingAndPrimaryActiveSite(ringActive, siteActive);
    }
    @Test(
            groups = { "Integration" },
            description = "Validate OSHA fields are displayed",
            priority = 2
    )
    public void validateOSHAFields(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(SiteActive.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        softAssert.assertTrue(
                siteFopsPage.complianceDocumentation_OSHA(),
                "OSHA Compliance documentation is located on the D:Document tab. is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:Rooftop BTS Risk Rank"),
                "S:Rooftop BTS Risk Rank is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:OSHA Remediation Complete"),
                "S:OSHA Remediation Complete is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed(
                        "S:General Contractor Required for Rooftop Site?"
                ),
                "S:General Contractor Required for Rooftop Site? is displayed"
        );
        softAssert.assertTrue(
                siteFopsPage.validateFieldIsDisplayed("S:OSHA Compliance Remediation"),
                "S:OSHA Compliance Remediation is displayed"
        );
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Validate OSHA field controls",
            priority = 3
    )
    public void validateFieldControls(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(SiteActive.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        softAssert.assertTrue(
                siteFopsPage.isFieldDropDown("S:Rooftop BTS Risk Rank"),
                "S:Rooftop BTS Risk Rank field is Dropdown"
        );
        softAssert.assertTrue(
                siteFopsPage.isFieldDropDown("S:OSHA Remediation Complete"),
                "S:OSHA Remediation Complete field is dropdown"
        );
        softAssert.assertContains(
                siteFopsPage.verifyFieldIsCheckbox(
                        "S:General Contractor Required for Rooftop Site?"
                ),
                "checkbox",
                "S:General Contractor Required for Rooftop Site? field is Checkbox"
        );
        softAssert.assertNotNull(
                siteFopsPage.validateFieldIsReadOnly("S:OSHA Compliance Remediation"),
                "S:OSHA Compliance Remediation field is Read only"
        );
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Validate S:Rooftop BTS Risk Rank field DropDown Values",
            priority = 4
    )
    public void validateRoofTopBTSDropDownValues(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(SiteActive.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        List<String> options = siteFopsPage.fieldDropDownValues(
                "S:Rooftop BTS Risk Rank"
        );
        softAssert.assertTrue(
                options.contains("Access to and Work at the BTS is permitted without personal fall protection."),
                "Value is Present"
        );
        softAssert.assertTrue(
                options.contains("Access to or Work at the BTS is only permitted by employees who have been trained/issued personal PPE for Fall Restraint activity."),
                "Value is Present"
        );
        softAssert.assertTrue(
                options.contains("GC only access. Minor modifications are required to accommodate restraint PPE and site safety plan."),
                "Value is Present"
        );
        softAssert.assertTrue(
                options.contains("GC only access. Significant work is required to remedy site, techs are not permitted to visit until remedied."),
                "Value is Present"
        );
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Update S:Rooftop BTS Risk Rank field without any Popup error",
            priority = 5
    )
    public void updateRoofTopBTSDropDown(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(SiteActive.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        siteFopsPage.selectDropDownValue(
                "S:Rooftop BTS Risk Rank",
                "Access to and Work at the BTS is permitted without personal fall protection."
        );
        softAssert.assertFalse(
                siteFopsPage.isPopAlertPresent(),
                "Updated the RoofTop BTS Risk Rank Successfully without Error"
        );
    }

    @Test(
            groups = { "Integration" },
            description = "Update S:OSHA Remediation Complete field without any Popup error",
            priority = 6
    )
    public void validateRoofTopBTSIsEnabledForSiteTypeBuilding(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(SiteActive.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        softAssert.assertNull(
                siteFopsPage.validateDrpFieldIsReadOnly("S:Rooftop BTS Risk Rank"),
                "S:Rooftop BTS Risk Rank field is Enabled"
        );
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Update S:OSHA Remediation Complete field without any Popup error",
            priority = 7
    )
    public void validateRoofTopBTSIsDisabledForSiteTypeNotBuilding(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(Site_Active.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
//        softAssert.assertNotNull(
//                siteFopsPage.validateDrpFieldIsReadOnly("S:Rooftop BTS Risk Rank"),
//                "S:Rooftop BTS Risk Rank field is Disabled"
//        );
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Validate S:OSHA Remediation Complete field DropDown Values",
            priority = 8
    )
    public void validateOSHARemediationCompleteDropDownValues(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(SiteActive.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        List<String> options = siteFopsPage.fieldDropDownValues(
                "S:Power Access 24x7"
        );
        softAssert.assertTrue(options.contains(""),  "Value is Present");
        softAssert.assertTrue(
                options.contains("Remediations Complete"),
                "Value is Present"
        );
        softAssert.assertTrue(
                options.contains("Site evaluated - determined no remediation to be done"),
                "Value is Present"
        );
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Update S:OSHA Remediation Complete field without any Popup error",
            priority = 9
    )
    public void updateOSHARemediationComplete(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(SiteActive.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        siteFopsPage.selectDropDownValue(
                "S:OSHA Remediation Complete",
                "Remediations Complete"
        );
        softAssert.assertFalse(
                siteFopsPage.isPopAlertPresent(),
                "Updated the S:OSHA Remediation Complete Successfully without Error"
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Validate S:OSHA Compliance Remediation is Complete when S:OSHA Remediation Complete is not Null",
            priority = 10
    )
    public void validateComplianceRemediationWhenRemediationCompleteIsNotNull(
            Method method
    ) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(SiteActive.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        siteFopsPage.selectDropDownValue(
                "S:OSHA Remediation Complete",
                "Remediations Complete"
        );
        String response = siteFopsPage.getComplianceRemediationText_OSHA(
                "S:OSHA Compliance Remediation"
        );
        softAssert.assertContains(
                response,
                "Complete",
                "S:OSHA Compliance Remediation is Complete when S:OSHA Remediation Complete is Null"
        );
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Validate S:OSHA Compliance Remediation is Incomplete when S:OSHA Remediation Complete is Null",
            priority = 11
    )
    public void validateComplianceRemediationWhenRemediationCompleteIsNull(
            Method method
    ) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(SiteActive.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        siteFopsPage.selectDropDownValue("S:OSHA Remediation Complete", "");
        String response = siteFopsPage.getComplianceRemediationText_OSHA(
                "S:OSHA Compliance Remediation"
        );
        softAssert.assertContains(
                response,
                "Incomplete",
                "S:OSHA Compliance Remediation is Incomplete when S:OSHA Remediation Complete is Null"
        );
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Check and Uncheck S:General Contractor Required for Rooftop Site?",
            priority = 12
    )
    public void verifyGeneralContractorCheckbox(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(SiteActive.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        boolean response = siteFopsPage.clickCheckbox_OSHA(
                "S:General Contractor Required for Rooftop Site?"
        );
        softAssert.assertTrue(
                response,
                "S:General Contractor Required for Rooftop Site? is checked"
        );
        boolean response1 = siteFopsPage.clickCheckbox_OSHA(
                "S:General Contractor Required for Rooftop Site?"
        );
        softAssert.assertFalse(
                response1,
                "S:General Contractor Required for Rooftop Site? is unchecked"
        );
        siteFopsPage.switchToTrackerPage();
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Click S:General Contractor Required for Rooftop Site? check box and save without any Popup error",
            priority = 13
    )
    public void updateGeneralContractorCheckbox(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        siteFopsPage = mainSideMenu.goToSiteTrackerFops();
        siteFopsPage.searchForValue(SiteActive.siteId, "S:Site Code");
        siteFopsPage.selectEditOption();
        siteFopsPage.goToFOPSTab();
        siteFopsPage.clickCheckbox_OSHA(
                "S:General Contractor Required for Rooftop Site?"
        );
        softAssert.assertFalse(
                siteFopsPage.isPopAlertPresent(),
                "S:General Contractor Required for Rooftop Site? is saved without error"
        );
        softAssert.closeAssert();
    }
}
