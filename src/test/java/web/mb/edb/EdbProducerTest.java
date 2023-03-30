package web.mb.edb;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.*;
import java.lang.reflect.Method;
import org.testng.annotations.Test;
import pages.web.Tracker.EdbProducerTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import pages.web.reports.ImportPage;
import rest.por.PorHelper;
import rest.project.ProjectHelper;
import rest.ring.RingHelper;
import rest.sector.SectorHelper;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

public class EdbProducerTest extends BaseTest {

    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");

    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    ImportPage importPage;
    SiteHelper siteHelper = new SiteHelper();
    RingHelper ringHelper = new RingHelper();
    PorHelper porHelper = new PorHelper();
    ProjectHelper projectHelper = new ProjectHelper();
    SectorHelper sectorHelper = new SectorHelper();
    EdbProducerTrackerPage edbProducerTrackerPage;
    Ring ACTIVE_RING;
    Site ACTIVE_SITE;

    Ring NEW_RING;
    Site NEW_SITE;
    Por commonPor;
    Project PROJECT_ACTIVE;
    Project PROJECT_NEW;
    Sector SECTOR_NEW;

    public EdbProducerTest() {
        if (envURL == null) {
            envURL = "https://magentabuiltstg.t-mobile.com/Login.do";
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
        generateData();
        mainSideMenu = loginPage.LoginAsUser(superUser);
    }

    private void generateData() {
        String ringIdActiveProject =
                "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActiveProject = new Ring(
                "Active",
                ringIdActiveProject,
                "Indoor Node"
        );
        Site siteActiveProject = new Site(
                ringIdActiveProject,
                "Primary",
                "Active Site"
        );
        PROJECT_ACTIVE =
                projectHelper.getActiveProject(
                        false,
                        ringActiveProject,
                        siteActiveProject
                );

        String ringIdNewProject =
                "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringIdNewProject, "Indoor Node");
        Site site = new Site(ringIdNewProject, "Primary", "Active Site");
        PROJECT_NEW = projectHelper.getNewProject(false, ring, site);

        String ringCode = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring1 = new Ring("Active", ringCode, "Macro");
        ACTIVE_RING = ringHelper.createAnActiveRing(ring1);

        NEW_RING = ringHelper.createNewRing(true);

        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringId, "Indoor Node");
        Site site1 = new Site(ringId, "Primary", "Active Site");
        ACTIVE_SITE = siteHelper.createActiveRingAndSite(ringActive, site1);

        String newSiteRingId =
                "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        NEW_SITE =
                siteHelper.createNewRingAndSite(
                        new Ring("Active", newSiteRingId, "Macro"),
                        new Site(newSiteRingId, "Candidate", "New Site")
                );

        String ringIdNewPor =
                "PP" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringPor = new Ring("Active", ringIdNewPor, "Indoor Node");
        Site sitePor = new Site(ringIdNewPor, "Primary", "Active Site");
        siteHelper.createActiveRingAndSite(ringPor, sitePor);
        Por porData = new Por(ringPor.ringId);
        commonPor = porHelper.createAPor(porData);

        String ringIdForSector =
                "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActiveForSector = new Ring(
                "Active",
                ringIdForSector,
                "Indoor Node"
        );
        Site siteForSector = new Site(ringIdForSector, "Primary", "Active Site");
        siteForSector =
                siteHelper.createActiveRingAndSite(ringActiveForSector, siteForSector);
        SECTOR_NEW =
                sectorHelper.createNewSector(
                        new Sector(siteForSector.siteId, siteForSector.siteId + "_21LPW")
                );

        System.out.println("ACTIVE_RING " + ACTIVE_RING.ringId);
        System.out.println(" ACTIVE_SITE " + ACTIVE_SITE.siteId);
        System.out.println("NEW_SITE " + NEW_SITE.siteId);
        System.out.println("NEW_RING " + NEW_RING.ringId);
        System.out.println("commonPor " + commonPor.porId);
        System.out.println("PROJECT_NEW " + PROJECT_NEW.projectId);
        System.out.println("PROJECT_ACTIVE " + PROJECT_ACTIVE.projectId);
        System.out.println("SECTOR_NEW " + SECTOR_NEW.sectorId);
    }

    @Test(
            groups = { "Integration" },
            description = "verifyEdbMessagePostRingCreation_Edb",
            priority = 2
    )
    public void verifyEdbMessagePostNewRingCreation_Edb(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
        edbProducerTrackerPage.searchForValue(NEW_RING.ringId, "EPM:Tracker Key");
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
                "Timestamp"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
                "User"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
                "Message Type"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
                "Tracker ID"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
                "Tracker Key"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
                "Producer Message"
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "verifyEdbMessagePostRingCreation_Edb",
            priority = 3
    )
    public void verifyEdbMessagePostActiveRingCreation_Edb(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
        edbProducerTrackerPage.searchForValue(
                ACTIVE_RING.ringId,
                "EPM:Tracker Key"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
                "Timestamp"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
                "User"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
                "Message Type"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
                "Tracker ID"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
                "Tracker Key"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
                "Producer Message"
        );
        System.out.println(
                " EPM:Created Timestamp (PT)" +
                        edbProducerTrackerPage.searchForValueInGrid(
                                "EPM:Created Timestamp (PT)",
                                1
                        )
        );
        System.out.println(
                "User ID " + edbProducerTrackerPage.searchForValueInGrid("User ID", 1)
        );
        System.out.println(
                "EPM:Message Type " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type", 1)
        );
        System.out.println(
                "EPM:Producer Message " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Producer Message", 1)
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "verifyEdbMessageBadRingData_Edb",
            priority = 4
    )
    public void verifyEdbMessageBadRingData_Edb(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
        edbProducerTrackerPage.searchForValue(
                ACTIVE_RING.ringId,
                "EPM:Tracker Key"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
                "Timestamp"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
                "User"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
                "Message Type"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
                "Tracker ID"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
                "Tracker Key"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
                "Producer Message"
        );
        System.out.println(
                " EPM:Created Timestamp (PT)" +
                        edbProducerTrackerPage.searchForValueInGrid(
                                "EPM:Created Timestamp (PT)",
                                1
                        )
        );
        System.out.println(
                "User ID " + edbProducerTrackerPage.searchForValueInGrid("User ID", 1)
        );
        System.out.println(
                "EPM:Message Type " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type", 1)
        );
        System.out.println(
                "EPM:Producer Message " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Producer Message", 1)
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "verifyEdbMessageBadRingData_Edb",
            priority = 4
    )
    public void verifyEdbMessageOnHoldRingData_Edb(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        NEW_RING.ringStatus = "On Hold";
        ringHelper.updateRingStatus(NEW_RING);
        edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
        edbProducerTrackerPage.searchForValue(NEW_RING.ringId, "EPM:Tracker Key");
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
                "Timestamp"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
                "User"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
                "Message Type"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
                "Tracker ID"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
                "Tracker Key"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
                "Producer Message"
        );
        System.out.println(
                " EPM:Created Timestamp (PT)" +
                        edbProducerTrackerPage.searchForValueInGrid(
                                "EPM:Created Timestamp (PT)",
                                1
                        )
        );
        System.out.println(
                "User ID " + edbProducerTrackerPage.searchForValueInGrid("User ID", 1)
        );
        System.out.println(
                "EPM:Message Type " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type", 1)
        );
        System.out.println(
                "EPM:Producer Message " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Producer Message", 1)
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "verifyEdbMessageBadRingData_Edb",
            priority = 4
    )
    public void verifyEdbMessageDeadRingData_Edb(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Ring NEW_RING = ringHelper.createNewRing(true);
        NEW_RING.ringStatus = "Dead";
        ringHelper.updateRingStatus(NEW_RING);
        edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
        edbProducerTrackerPage.searchForValue(NEW_RING.ringId, "EPM:Tracker Key");
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
                "Timestamp"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
                "User"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
                "Message Type"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
                "Tracker ID"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
                "Tracker Key"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
                "Producer Message"
        );
        System.out.println(
                " EPM:Created Timestamp (PT)" +
                        edbProducerTrackerPage.searchForValueInGrid(
                                "EPM:Created Timestamp (PT)",
                                1
                        )
        );
        System.out.println(
                "User ID " + edbProducerTrackerPage.searchForValueInGrid("User ID", 1)
        );
        System.out.println(
                "EPM:Message Type " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type", 1)
        );
        System.out.println(
                "EPM:Producer Message " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Producer Message", 1)
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "verifyEdbMessageBadRingData_Edb",
            priority = 4
    )
    public void verifyEdbMessageRetiredRingData_Edb(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Ring NEW_RING = ringHelper.createNewRing(true);
        NEW_RING.ringStatus = "Retired";
        ringHelper.updateRingStatus(NEW_RING);
        edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
        edbProducerTrackerPage.searchForValue(NEW_RING.ringId, "EPM:Tracker Key");
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
                "Timestamp"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
                "User"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
                "Message Type"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
                "Tracker ID"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
                "Tracker Key"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
                "Producer Message"
        );
        System.out.println(
                " EPM:Created Timestamp (PT)" +
                        edbProducerTrackerPage.searchForValueInGrid(
                                "EPM:Created Timestamp (PT)",
                                1
                        )
        );
        System.out.println(
                "User ID " + edbProducerTrackerPage.searchForValueInGrid("User ID", 1)
        );
        System.out.println(
                "EPM:Message Type " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type", 1)
        );
        System.out.println(
                "EPM:Producer Message " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Producer Message", 1)
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "verifyEdbMessageBadRingData_Edb",
            priority = 4
    )
    public void verifyEdbMessageDecommissionedRingData_Edb(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Ring NEW_RING = ringHelper.createNewRing(true);
        NEW_RING.ringStatus = "Decommissioned";
        ringHelper.updateRingStatus(NEW_RING);
        edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
        edbProducerTrackerPage.searchForValue(NEW_RING.ringId, "EPM:Tracker Key");
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
                "Timestamp"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
                "User"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
                "Message Type"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
                "Tracker ID"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
                "Tracker Key"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
                "Producer Message"
        );
        System.out.println(
                " EPM:Created Timestamp (PT)" +
                        edbProducerTrackerPage.searchForValueInGrid(
                                "EPM:Created Timestamp (PT)",
                                1
                        )
        );
        System.out.println(
                "User ID " + edbProducerTrackerPage.searchForValueInGrid("User ID", 1)
        );
        System.out.println(
                "EPM:Message Type " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type", 1)
        );
        System.out.println(
                "EPM:Producer Message " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Producer Message", 1)
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "verifyEdbMessagePostSiteCreation_Edb",
            priority = 2
    )
    public void verifyEdbMessagePostDeadRingSiteCreation_Edb(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        Ring RING = new Ring("Dead", NEW_SITE.ringId);
        ringHelper.updateRingStatus(RING);
        edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
        edbProducerTrackerPage.searchForValue(NEW_SITE.siteId, "EPM:Tracker Key");
        edbProducerTrackerPage.searchForValue(NEW_SITE.siteId, "EPM:Tracker Key");
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
                "Timestamp"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
                "User"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
                "Message Type"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
                "Tracker ID"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
                "Tracker Key"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
                "Producer Message"
        );
        System.out.println(
                " EPM:Created Timestamp (PT)" +
                        edbProducerTrackerPage.searchForValueInGrid(
                                "EPM:Created Timestamp (PT)",
                                1
                        )
        );
        System.out.println(
                "User ID " + edbProducerTrackerPage.searchForValueInGrid("User ID", 1)
        );
        System.out.println(
                "EPM:Message Type " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type", 1)
        );
        System.out.println(
                "EPM:Producer Message " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Producer Message", 1)
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "verifyEdbMessagePostSiteCreation_Edb",
            priority = 2
    )
    public void verifyEdbMessagePostRetiredRingSiteCreation_Edb(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String newSiteRingId =
                "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Site NEW_SITE = siteHelper.createNewRingAndSite(
                new Ring("Active", newSiteRingId, "Macro"),
                new Site(newSiteRingId, "Candidate", "New Site")
        );

        Ring RING = new Ring("Retired", NEW_SITE.ringId);
        ringHelper.updateRingStatus(RING);
        edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
        sleepFor(10);
        edbProducerTrackerPage.searchForValue(NEW_SITE.siteId, "EPM:Tracker Key");
        edbProducerTrackerPage.searchForValue(NEW_SITE.siteId, "EPM:Tracker Key");
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
                "Timestamp"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
                "User"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
                "Message Type"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
                "Tracker ID"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
                "Tracker Key"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
                "Producer Message"
        );
        System.out.println(
                " EPM:Created Timestamp (PT)" +
                        edbProducerTrackerPage.searchForValueInGrid(
                                "EPM:Created Timestamp (PT)",
                                1
                        )
        );
        System.out.println(
                "User ID " + edbProducerTrackerPage.searchForValueInGrid("User ID", 1)
        );
        System.out.println(
                "EPM:Message Type " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type", 1)
        );
        System.out.println(
                "EPM:Producer Message " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Producer Message", 1)
        );
        softAssert.closeAssert();
    }/*

    @Test(
            groups = { "Integration" },
            description = "verifyEdbMessagePostSiteCreation_Edb",
            priority = 2
    )
    public void verifyEdbMessagePostDecommissionedRingSiteCreation_Edb(
            Method method
    ) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        String newSiteRingId =
                "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Site NEW_SITE = siteHelper.createNewRingAndSite(
                new Ring("Active", newSiteRingId, "Macro"),
                new Site(newSiteRingId, "Candidate", "New Site")
        );

        Ring RING = new Ring("Decommissioned", NEW_SITE.ringId);
        ringHelper.updateRingStatus(RING);
        edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
        edbProducerTrackerPage.searchForValue(NEW_SITE.siteId, "EPM:Tracker Key");
        edbProducerTrackerPage.searchForValue(NEW_SITE.siteId, "EPM:Tracker Key");
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
                "Timestamp"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
                "User"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
                "Message Type"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
                "Tracker ID"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
                "Tracker Key"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
                "Producer Message"
        );
        System.out.println(
                " EPM:Created Timestamp (PT)" +
                        edbProducerTrackerPage.searchForValueInGrid(
                                "EPM:Created Timestamp (PT)",
                                1
                        )
        );
        System.out.println(
                "User ID " + edbProducerTrackerPage.searchForValueInGrid("User ID", 1)
        );
        System.out.println(
                "EPM:Message Type " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type", 1)
        );
        System.out.println(
                "EPM:Producer Message " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Producer Message", 1)
        );
        softAssert.closeAssert();
    }*/

    @Test(
            groups = { "Integration" },
            description = "verifyEdbMessagePostSiteCreation_Edb",
            priority = 2
    )
    public void verifyEdbMessagePostActiveSiteCreation_Edb(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
        edbProducerTrackerPage.searchForValue(
                ACTIVE_SITE.siteId,
                "EPM:Tracker Key"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
                "Timestamp"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
                "User"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
                "Message Type"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
                "Tracker ID"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
                "Tracker Key"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
                "Producer Message"
        );
        System.out.println(
                " EPM:Created Timestamp (PT)" +
                        edbProducerTrackerPage.searchForValueInGrid(
                                "EPM:Created Timestamp (PT)",
                                1
                        )
        );
        System.out.println(
                "User ID " + edbProducerTrackerPage.searchForValueInGrid("User ID", 1)
        );
        System.out.println(
                "EPM:Message Type " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type", 1)
        );
        System.out.println(
                "EPM:Producer Message " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Producer Message", 1)
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "verifyEdbMessagePostSiteUpdate_Edb",
            priority = 4
    )
    public void verifyEdbMessagePostSiteUpdate_Edb(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
        siteHelper.updateToPrimaryActiveSite(ACTIVE_SITE);
        sleepFor(10);
        edbProducerTrackerPage.searchForValue(
                ACTIVE_SITE.siteId,
                "EPM:Tracker Key"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
                "Timestamp"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
                "User"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
                "Message Type"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
                "Tracker ID"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
                "Tracker Key"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
                "Producer Message"
        );
        System.out.println(
                " EPM:Created Timestamp (PT)" +
                        edbProducerTrackerPage.searchForValueInGrid(
                                "EPM:Created Timestamp (PT)",
                                2
                        )
        );
        System.out.println(
                "User ID " + edbProducerTrackerPage.searchForValueInGrid("User ID", 2)
        );
        System.out.println(
                "EPM:Message Type " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type", 2)
        );
        System.out.println(
                "EPM:Producer Message " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Producer Message", 2)
        );
        softAssert.closeAssert();
    }

    // @Test(groups = {"Integration"},description = "verifyEdbMessagePostNewSiteCreation_Edb",priority = 4)
    // public void verifyEdbMessagePostNewSiteCreation_Edb(Method method) throws Exception {
    // AssertionsUtil softAssert = new AssertionsUtil();
    // edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
    // sleepFor(10);
    // edbProducerTrackerPage.searchForValue(NEW_SITE.siteId, "EPM:Tracker Key");
    // softAssert.assertFalse(edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),"Timestamp");
    // softAssert.assertFalse(edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),"User");
    // softAssert.assertFalse(edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),"Message Type");
    // softAssert.assertFalse(edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),"Tracker ID");
    // softAssert.assertFalse(edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),"Tracker Key");
    // softAssert.assertFalse(edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),"Producer Message");
    // softAssert.closeAssert();
    // }

    @Test(
            groups = { "Integration" },
            description = "verifyEdbMessagePostNewSiteCreation_Edb",
            priority = 4
    )
    public void verifyEdbMessagePostNewPorCreation_Edb(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
        sleepFor(10);
        edbProducerTrackerPage.searchForValue(commonPor.porId, "EPM:Tracker Key");
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
                "Timestamp"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
                "User"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
                "Message Type"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
                "Tracker ID"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
                "Tracker Key"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
                "Producer Message"
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "verifyEdbMessagePostNewSiteCreation_Edb",
            priority = 4
    )
    public void verifyEdbMessagePostUpdatePor_Edb(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        commonPor.queueStatus = "POR Request Complete";
        commonPor = porHelper.updatePorWithQueueStatus(commonPor);
        edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
        sleepFor(10);
        edbProducerTrackerPage.searchForValue(commonPor.porId, "EPM:Tracker Key");
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
                "Timestamp"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
                "User"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
                "Message Type"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
                "Tracker ID"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
                "Tracker Key"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
                "Producer Message"
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "verifyEdbMessagePostRingCreation_Edb",
            priority = 40
    )
    public void verifyEdbMessagePostActiveProjectCreation_Edb(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
        edbProducerTrackerPage.searchForValue(
                PROJECT_ACTIVE.projectId,
                "EPM:Tracker Key"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
                "Timestamp"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
                "User"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
                "Message Type"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
                "Tracker ID"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
                "Tracker Key"
        );
        softAssert.assertTrue(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
                "Producer Message"
        );
        System.out.println(
                " EPM:Created Timestamp (PT)" +
                        edbProducerTrackerPage.searchForValueInGrid(
                                "EPM:Created Timestamp (PT)",
                                1
                        )
        );
        System.out.println(
                "User ID " + edbProducerTrackerPage.searchForValueInGrid("User ID", 1)
        );
        System.out.println(
                "EPM:Message Type " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type", 1)
        );
        System.out.println(
                "EPM:Producer Message " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Producer Message", 1)
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "verifyEdbMessagePostNewSiteCreation_Edb",
            priority = 4
    )
    public void verifyEdbMessagePostNewProject_Edb(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
        sleepFor(10);
        edbProducerTrackerPage.searchForValue(
                PROJECT_NEW.projectId,
                "EPM:Tracker Key"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
                "Timestamp"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
                "User"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
                "Message Type"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
                "Tracker ID"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
                "Tracker Key"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
                "Producer Message"
        );
        System.out.println(
                " EPM:Created Timestamp (PT)" +
                        edbProducerTrackerPage.searchForValueInGrid(
                                "EPM:Created Timestamp (PT)",
                                1
                        )
        );
        System.out.println(
                "User ID " + edbProducerTrackerPage.searchForValueInGrid("User ID", 1)
        );
        System.out.println(
                "EPM:Message Type " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Message Type", 1)
        );
        System.out.println(
                "EPM:Producer Message " +
                        edbProducerTrackerPage.searchForValueInGrid("EPM:Producer Message", 1)
        );
        softAssert.closeAssert();
    }

    @Test(
            groups = { "Integration" },
            description = "verifyEdbMessagePostNewSiteCreation_Edb",
            priority = 4
    )
    public void verifyEdbMessagePostNewSector_Edb(Method method)
            throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        edbProducerTrackerPage = mainSideMenu.goToEdbProducerPage();
        sleepFor(10);
        edbProducerTrackerPage.searchForValue(
                SECTOR_NEW.sectorId,
                "EPM:Tracker Key"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Created Timestamp (PT)"),
                "Timestamp"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:User ID"),
                "User"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Message Type"),
                "Message Type"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker ID"),
                "Tracker ID"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Tracker Key"),
                "Tracker Key"
        );
        softAssert.assertFalse(
                edbProducerTrackerPage.isValuePresentInGrid("EPM:Producer Message"),
                "Producer Message"
        );
        softAssert.closeAssert();
    }
}
