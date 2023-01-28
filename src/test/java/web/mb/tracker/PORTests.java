package web.mb.tracker;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Por;
import commons.objects.Ring;
import commons.objects.Site;

import java.lang.reflect.Method;
import org.testng.annotations.Test;
import pages.web.Tracker.AddPORPage;
import pages.web.Tracker.PORTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.por.PorHelper;
import rest.ring.RingHelper;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

public class PORTests extends BaseTest {

    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");

    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    PORTrackerPage porTrackerPage;
    AddPORPage addPORPage;
    PorHelper porHelper = new PorHelper();
    SiteHelper siteHelper = new SiteHelper();
    RingHelper ringHelper = new RingHelper();
    String VALID_RING_PRIMARY_BUILD_SITE = "";
    String ACTIVE_RING_AND_SITE = "";

    String NEW_RING_SITE = "";
    String NEW_RING = "";
    Por commonPor;

    Site ACTIVE_RING_NEW_SITE;

    public PORTests() {
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
        generateTestData();
        mainSideMenu = loginPage.LoginAsUser(superUser);
    }

    private void generateTestData() {
        // create a ring code for class
        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringId, "Indoor Node");
        Site site = new Site(ringId, "Primary", "Active Site");
        siteHelper.createActiveRingAndPrimaryActiveSite(ring, site);
        VALID_RING_PRIMARY_BUILD_SITE = ringId;
        System.out.println(
                "VALID_RING_PRIMARY_BUILD_SITE " + VALID_RING_PRIMARY_BUILD_SITE
        );

        ringId = "ZX" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringId, "Indoor Node");
        Site siteActive = new Site(ringId, "Primary", "Active Site");
        siteHelper.createActiveRingAndSite(ringActive, siteActive);
        ACTIVE_RING_AND_SITE = ringId;
        System.out.println("ACTIVE_RING_AND_SITE " + ACTIVE_RING_AND_SITE);

        ringId = "NR" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringNew = new Ring("Active", ringId, "Indoor Node");
        Site siteNew = new Site(ringId, "Primary", "Active Site");
        siteHelper.createNewRingAndSite(ringNew, siteNew);
        NEW_RING_SITE = ringId;
        System.out.println("NEW_RING_SITE " + NEW_RING_SITE);

        Ring ringNewOnly = ringHelper.createNewRing(false);
        NEW_RING = ringNewOnly.ringId;
        System.out.println("NEW_RING " + NEW_RING);

        ringId = "LX" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring activeRing = new Ring("Active", ringId, "Indoor Node");
        Site newSite = new Site(ringId, "Primary", "Active Site");
        ACTIVE_RING_NEW_SITE =
                siteHelper.createActiveRingNewSite(activeRing, newSite);

        String ringIdNewPor =
                "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringPor = new Ring("Active", ringIdNewPor, "Indoor Node");
        Site sitePor = new Site(ringIdNewPor, "Primary", "Active Site");
        siteHelper.createActiveRingAndSite(ringPor, sitePor);
        Por porData = new Por(ringPor.ringId);
        commonPor = porHelper.createAPor(porData);
        // String ringId = "NO" + MiscHelpers.getRandomString(5, true).toUpperCase();
        // Ring ring = new Ring("Active", ringId, "Indoor Node");
        // Site site = new Site(ringId,"Primary","Active Site");
        // siteHelper.createActiveRingAndSite(ring,site);
        // VALID_RING_NON_PRIMARY_BUILD_SITE = ringId;
    }

    @Test(
            groups = { "Integration" },
            description = "User should able to see all POR ID's",
            priority = 2
    )
    public void verifyAllPORID_Por() throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage = mainSideMenu.goToPorTrackerPage();
        porTrackerPage.searchForValue(commonPor.porId, "POR:POR ID");
        softAssert.assertTrue(
                porTrackerPage.getPORIDCount() > 0,
                "There is at-least one POR"
        );
        if (porTrackerPage.getPORIDCount() > 0) {
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:POR ID"),
                    "Value Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("M:Market"),
                    "Value Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:Program Name"),
                    "Value Should Be Present"
            );
        }
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Do NOT Enter the Mandatory fields in POR and click on Apply.",
            priority = 3
    )
    public void verifyMandatoryFieldsAlertMessages_Por(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage = mainSideMenu.goToPorTrackerPage();
        porTrackerPage.searchForValue(ACTIVE_RING_AND_SITE, "R:Ring Code");
        int initialPorCount = porTrackerPage.getPORIDCount();
        addPORPage = porTrackerPage.clickAddNewPORButton();
        porTrackerPage =
                addPORPage.verifyMandatoryMessageAlert(ACTIVE_RING_AND_SITE);
        softAssert.assertTrue(
                initialPorCount == porTrackerPage.getPORIDCount(),
                "POR Count Should Match"
        );
        softAssert.closeAssert();
    }

    @Test(
            dependsOnMethods = "login",
            groups = { "Integration" },
            description = "Enter the Mandatory fields in POR Add Page and click on Apply.",
            priority = 4
    )
    public void createNewPOR_Por(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage = mainSideMenu.goToPorTrackerPage();
        // // porTrackerPage.clickAddNewPORButton();
        addPORPage = porTrackerPage.clickAddNewPORButton();
        Por porData = new Por(ACTIVE_RING_AND_SITE);
        porData = addPORPage.addNewPORTracker(porData);
        if (porData != null) {
            porTrackerPage.searchForValue(porData.porId, "POR:POR ID");
            String response = porTrackerPage.searchForValueInGrid(
                    "POR:POR Request Type",
                    "POR:POR ID",
                    porData.porId
            );
            softAssert.assertContains(
                    response,
                    "Non-Governance POR",
                    "Request Type Should Match"
            );
            System.out.println(":::" + response);
            response =
                    porTrackerPage.searchForValueInGrid(
                            "POR:POR Request Queue",
                            "POR:POR ID",
                            porData.porId
                    );
            softAssert.assertContains(
                    response,
                    "POR Request Complete",
                    "Request Queue Should Match"
            );
            System.out.println(":::" + response);
            response =
                    porTrackerPage.searchForValueInGrid(
                            "POR:Created By",
                            "POR:POR ID",
                            porData.porId
                    );
            softAssert.assertContains(
                    response,
                    superUser.getNtCode(),
                    "created By Should Match::" + response
            );
            System.out.println(":::" + response);
            response =
                    porTrackerPage.searchForValueInGrid(
                            "POR:Created Date",
                            "POR:POR ID",
                            porData.porId
                    );
            softAssert.assertContains(
                    response,
                    MiscHelpers.currentDateTime("MM/dd/yyyy"),
                    "Created Date should Match::" + response
            );
            System.out.println(":::" + response);
            response =
                    porTrackerPage.searchForValueInGrid(
                            "POR:Last Modified By",
                            "POR:POR ID",
                            porData.porId
                    );
            softAssert.assertContains(
                    response,
                    superUser.getNtCode(),
                    "Last Modified By Should Match::" + response
            );
            System.out.println(":::" + response);
            response =
                    porTrackerPage.searchForValueInGrid(
                            "POR:Last Modified Date",
                            "POR:POR ID",
                            porData.porId
                    );
            // softAssert.assertContains(response, MiscHelpers.currentDateTime("MM/dd/yyyy"),"Last Modified Date Should
            // Match::"+response );
            System.out.println(":::" + response);
            // DELETE THE CREATED POR AS PART OF TEAR DOWN
            porHelper.getPorTrackerIdsAndDelete(porData);
            // porTrackerPage.deleteSelection();
        } else {
            softAssert.assertTrue(
                    false,
                    "Seems like an issue occurred when creating the POR"
            );
        }
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Enter the Mandatory fields in POR Add Page and click on Apply.",
            priority = 4
    )
    public void createNewPORAndUpdateRequestQueue_Por(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage = mainSideMenu.goToPorTrackerPage();
        Por porData = new Por(VALID_RING_PRIMARY_BUILD_SITE);
        porData = porHelper.createAPor(porData);
        if (porData != null) {
            porTrackerPage.searchForValue(porData.porId, "POR:POR ID");
            addPORPage = porTrackerPage.selectEditOption();
            porData.queueStatus = "POR Request Complete";
            String ProjectID = addPORPage.changePORRequestQueue1(porData);
            System.out.println("Project ID_" + ProjectID);
            porTrackerPage = addPORPage.returnToPORTracker();
            porTrackerPage.searchForValue(ProjectID, "PJ:Project ID");
            String response = porTrackerPage.searchForValueInGrid(
                    "POR:POR Request Type",
                    "POR:POR ID",
                    porData.porId
            );
            System.out.println(":::" + response);
            // softAssert.assertContains(response, "Live POR Record", "Request Type Should Match");
            response =
                    porTrackerPage.searchForValueInGrid(
                            "POR:POR Request Queue",
                            "POR:POR ID",
                            porData.porId
                    );
            System.out.println(":::" + response);
            softAssert.assertContains(
                    response,
                    "POR Request Complete",
                    "Request Queue Should Match"
            );
            System.out.println(":::" + response);
            response =
                    porTrackerPage.searchForValueInGrid(
                            "POR:Last Modified By",
                            "POR:POR ID",
                            porData.porId
                    );
            softAssert.assertContains(
                    response,
                    superUser.getNtCode(),
                    "Last Updated By Should Match::" + response
            );

            // DELETE THE CREATED POR AS PART OF TEAR DOWN
            porHelper.getPorTrackerIdsAndDelete(porData);
            // softAssert.assertTrue(!ProjectID.equals(""), "Project Id is created and displayed under Project ID field
            // Successfully.");
        } else {
            softAssert.assertTrue(
                    false,
                    "Seems like an issue occurred when creating the POR"
            );
        }
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Enter the Mandatory fields in POR Add Page and click on Apply",
            priority = 5
    )
    public void createNewPORWithBuildStatusNotPrimary_Por(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage = mainSideMenu.goToPorTrackerPage();
        Por porData = new Por(ACTIVE_RING_AND_SITE);
        porData = porHelper.createAPor(porData);
        if (porData != null) {
            porTrackerPage.searchForValue(porData.porId, "POR:POR ID");

            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:POR ID"),
                    "Value POR:POR ID Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("M:Market"),
                    "Value M:Market Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:Program Name"),
                    "Value POR:Program Name Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:POR Request Type"),
                    "Value POR:POR Request Type Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:POR Request Queue"),
                    "Value POR:POR Request Queue Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:Created By"),
                    "Value POR:Created By Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:Created Date"),
                    "Value POR:Created Date Should Be Present"
            );

            addPORPage = porTrackerPage.selectEditOption();
            porData.queueStatus = "POR Request Complete";
            String projectId = addPORPage.changePORRequestQueue1(porData);
            System.out.println("Actual Project ID_" + projectId);
            porTrackerPage = addPORPage.returnToPORTracker();
            String ExpectedProjectID = "";
            softAssert.assertTrue(
                    projectId.equals(ExpectedProjectID),
                    "Project ID not generated"
            );
            porHelper.getPorTrackerIdsAndDelete(porData);
        } else {
            softAssert.assertTrue(
                    false,
                    "Seems like an issue occurred when creating the POR"
            );
        }
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Enter the Mandatory fields in POR Add Page and click on Apply",
            priority = 5
    )
    public void createNewPORWithRingStatusNew_Por(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage = mainSideMenu.goToPorTrackerPage();
        Por porData = new Por(NEW_RING);
        porData = porHelper.createAPor(porData);
        if (porData != null) {
//      porTrackerPage.searchForValue(porData.porId, "POR:POR ID");
//
//      softAssert.assertTrue(
//        porTrackerPage.isValuePresentInGrid("POR:POR ID"),
//        "Value POR:POR ID Should Be Present"
//      );
//      softAssert.assertTrue(
//        porTrackerPage.isValuePresentInGrid("M:Market"),
//        "Value M:Market Should Be Present"
//      );
//      softAssert.assertTrue(
//        porTrackerPage.isValuePresentInGrid("POR:Program Name"),
//        "Value POR:Program Name Should Be Present"
//      );
//      softAssert.assertTrue(
//        porTrackerPage.isValuePresentInGrid("POR:POR Request Type"),
//        "Value POR:POR Request Type Should Be Present"
//      );
//      softAssert.assertTrue(
//        porTrackerPage.isValuePresentInGrid("POR:POR Request Queue"),
//        "Value POR:POR Request Queue Should Be Present"
//      );
//      softAssert.assertTrue(
//        porTrackerPage.isValuePresentInGrid("POR:Created By"),
//        "Value POR:Created By Should Be Present"
//      );
            softAssert.assertTrue(
                    false,
                    "POR should not be created"
            );
        } else {
            softAssert.assertTrue(
                    true,
                    "Seems like an issue occurred when creating the POR"
            );
        }
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Enter the Mandatory fields in POR Add Page and click on Apply",
            priority = 5
    )
    public void createNewPORWithRingStatusAndSiteStatusNew_Por(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage = mainSideMenu.goToPorTrackerPage();
        Por porData = new Por(NEW_RING_SITE);
        porData = porHelper.createAPor(porData);
        if (porData != null) {
            porTrackerPage.searchForValue(porData.porId, "POR:POR ID");

            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:POR ID"),
                    "Value POR:POR ID Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("M:Market"),
                    "Value M:Market Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:Program Name"),
                    "Value POR:Program Name Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:POR Request Type"),
                    "Value POR:POR Request Type Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:POR Request Queue"),
                    "Value POR:POR Request Queue Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:Created By"),
                    "Value POR:Created By Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:Created Date"),
                    "Value POR:Created Date Should Be Present"
            );

            addPORPage = porTrackerPage.selectEditOption();
            porData.queueStatus = "POR Request Complete";
            String projectId = addPORPage.changePORRequestQueue1(porData);
            System.out.println("Actual Project ID_" + projectId);
            porTrackerPage = addPORPage.returnToPORTracker();
            String ExpectedProjectID = "";
            porHelper.getPorTrackerIdsAndDelete(porData);
            softAssert.assertTrue(
                    projectId.equals(ExpectedProjectID),
                    "Project ID not generated"
            );
        } else {
            softAssert.assertTrue(
                    false,
                    "Seems like an issue occurred when creating the POR"
            );
        }
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Enter the Mandatory fields in POR Add Page and click on Apply",
            priority = 5
    )
    public void createNewPORWithRingStatusActiveAndSiteStatusNew_Por(
            Method method
    ) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage = mainSideMenu.goToPorTrackerPage();
        Por porData = new Por(ACTIVE_RING_NEW_SITE.ringId);
        porData = porHelper.createAPor(porData);
        if (porData != null) {
            porTrackerPage.searchForValue(porData.porId, "POR:POR ID");
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:POR ID"),
                    "Value POR:POR ID Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("M:Market"),
                    "Value M:Market Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:Program Name"),
                    "Value POR:Program Name Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:POR Request Type"),
                    "Value POR:POR Request Type Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:POR Request Queue"),
                    "Value POR:POR Request Queue Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:Created By"),
                    "Value POR:Created By Should Be Present"
            );
            softAssert.assertTrue(
                    porTrackerPage.isValuePresentInGrid("POR:Created Date"),
                    "Value POR:Created Date Should Be Present"
            );

            addPORPage = porTrackerPage.selectEditOption();
            porData.queueStatus = "POR Request Complete";
            String projectId = addPORPage.changePORRequestQueue1(porData);
            System.out.println("Actual Project ID_" + projectId);
            porTrackerPage = addPORPage.returnToPORTracker();
            String ExpectedProjectID = "";
            porHelper.getPorTrackerIdsAndDelete(porData);
            // porTrackerPage.deleteSelection();
            softAssert.assertTrue(
                    projectId.equals(ExpectedProjectID),
                    "Project ID not generated"
            );
        } else {
            softAssert.assertTrue(
                    false,
                    "Seems like an issue occurred when creating the POR"
            );
        }
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Enter the Mandatory fields in POR Add Page and click on Apply",
            priority = 5
    )
    public void createdPORDateByASC_Por(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage = mainSideMenu.goToPorTrackerPage();
        int PORIDCount = porTrackerPage.getPORIDCount();
        System.out.println("Actual POR ID Count Before ASC Sorting_" + PORIDCount);
        porTrackerPage.sortingASCOrder();
        int PORIDCount1 = porTrackerPage.getPORIDCount();
        System.out.println("Actual POR ID Count After ASC Sorting_" + PORIDCount1);
        softAssert.assertTrue(
                PORIDCount1 == PORIDCount,
                "POR  created Date Sorting Done Successfully"
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "Enter the Mandatory fields in POR Add Page and click on Apply",
            priority = 5
    )
    public void createdPORDateByDESC_Por(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        porTrackerPage = mainSideMenu.goToPorTrackerPage();
        int PORIDCount = porTrackerPage.getPORIDCount();

        System.out.println("Actual POR ID Count Before DESC Sorting_" + PORIDCount);
        porTrackerPage.sortingDescOrder();
        int PORIDCount1 = porTrackerPage.getPORIDCount();
        System.out.println("Actual POR ID Count After DESC Sorting_" + PORIDCount1);
        softAssert.assertTrue(
                PORIDCount1 == PORIDCount,
                "POR  created Date Sorting Done Successfully"
        );
        softAssert.closeAssert();
    }
}
