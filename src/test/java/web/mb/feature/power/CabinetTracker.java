package web.mb.feature.power;

import common.BaseTest;
import commons.enums.LoginOptionEnum;
import commons.objects.Ring;
import commons.objects.Site;
import org.testng.annotations.Test;
import pages.web.Tracker.AddCabinetPage;
import pages.web.Tracker.CabinetTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.power.PowerHelper;
import rest.site.SiteHelper;
import utility.helper.AssertionsUtil;
import utility.helper.MiscHelpers;

import java.lang.reflect.Method;

public class CabinetTracker extends BaseTest {
    public String envURL = System.getProperty("TestEnv");
    public String testSuite = System.getProperty("TestRunner");
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    Site SiteActive;
    Site site;
    SiteHelper siteHelper = new SiteHelper();
    PowerHelper powerHelper = new PowerHelper();
    CabinetTrackerPage cabinetTrackerPage;
    AddCabinetPage addCabinetPage;

    public CabinetTracker() {
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
        String ringIdActive =
                "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActive, "Indoor Node");
        SiteActive =
                new Site(
                        ringIdActive,
                        "Final Build",
                        "Building",
                        "Roof Top Mount",
                        "Build To Suit",
                        "Active Site"
                );
        SiteActive =
                siteHelper.createActiveRingAndPrimaryActiveSite(ringActive, SiteActive);
        powerHelper.createNewCabinet(SiteActive,"Commscope");
        powerHelper.updateCabinetModel(SiteActive,"2102");

    }

    private Site generateSiteAndCabinet(String vendor, String model) {
        String ringIdActive =
                "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ringActive = new Ring("Active", ringIdActive, "Indoor Node");
        site =
                new Site(
                        ringIdActive,
                        "Final Build",
                        "Building",
                        "Roof Top Mount",
                        "Build To Suit",
                        "Active Site"
                );
        site =
                siteHelper.createActiveRingAndPrimaryActiveSite(ringActive, site);
        powerHelper.createNewCabinet(site,vendor);
        powerHelper.updateCabinetModel(site,model);
        return site;
    }

    @Test(groups = { "Integration" }, description = "alcatelLucentNorthstarVendor", priority = 2)
    public void alcatelLucentNorthstarVendor(Method method) throws Exception {
        AssertionsUtil softAssert = new AssertionsUtil();
        site = generateSiteAndCabinet("Alcatel Lucent-Northstar","379");
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"2","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"18","CAB:Max DC Load Breaker Slots / Poles Used");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Altergy", priority = 2)
    public void
    altergyVendor(Method method) throws Exception {
        generateSiteAndCabinet("Altergy","503");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"1","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "ASCO", priority = 2)
    public void ascoVendor(Method method) throws Exception {
        generateSiteAndCabinet("ASCO","483");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"0","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Atlantic Scientific", priority = 2)
    public void atlanticScientificVendor(Method method) throws Exception {
        generateSiteAndCabinet("Atlantic Scientific","470");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Charles", priority = 2)
    public void charlesVendor(Method method) throws Exception {
        generateSiteAndCabinet("Charles","378");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        System.out.println("rec_"+addCabinetPage.getValueInField("CAB:Max Rectifiers"));
        System.out.println("battery_"+addCabinetPage.getValueInField("CAB:Max Battery Strings"));
        System.out.println("breaker_"+addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"));
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"1","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Chattem", priority = 2)
    public void chattemVendor(Method method) throws Exception {
        generateSiteAndCabinet("Chattem","433");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"1","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"48","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Commscope", priority = 2)
    public void commscopeVendor(Method method) throws Exception {
        generateSiteAndCabinet("Commscope","380");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"2","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"18","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Delta", priority = 2)
    public void deltaVendor(Method method) throws Exception {
        generateSiteAndCabinet("Delta","369");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"4","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "acData", priority = 2)
    public void acDataVendor(Method method) throws Exception {
        generateSiteAndCabinet("AC Data","461");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"0","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Emerson / Vertiv / Northern Technologies", priority = 2)
    public void emersonVendor(Method method) throws Exception {
        generateSiteAndCabinet("Emerson / Vertiv / Northern Technologies","390");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"2","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"18","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Ericsson", priority = 2)
    public void ericssonVendor(Method method) throws Exception {
        generateSiteAndCabinet("Ericsson","354");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        System.out.println("rec_"+addCabinetPage.getValueInField("CAB:Max Rectifiers"));
        System.out.println("battery_"+addCabinetPage.getValueInField("CAB:Max Battery Strings"));
        System.out.println("breaker_"+addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"));
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"1","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Flextronics", priority = 2)
    public void flextronicsVendor(Method method) throws Exception {
        generateSiteAndCabinet("Flextronics","432");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"1","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Generic", priority = 2)
    public void GenericVendor(Method method) throws Exception {
        generateSiteAndCabinet("Generic","392");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"0","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"6","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Idatech", priority = 2)
    public void IdatechVendor(Method method) throws Exception {
        generateSiteAndCabinet("Idatech","504");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"1","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Intersect", priority = 2)
    public void intersectVendor(Method method) throws Exception {
        generateSiteAndCabinet("Intersect","475");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"0","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Magnatek", priority = 2)
    public void MagnatekVendor(Method method) throws Exception {
        generateSiteAndCabinet("Magnatek","457");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"1","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Milbank", priority = 2)
    public void milbankVendor(Method method) throws Exception {
        generateSiteAndCabinet("Milbank","487");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"0","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Marconi", priority = 2)
    public void marconiVendor(Method method) throws Exception {
        generateSiteAndCabinet("Marconi","473");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"0","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Multilink", priority = 2)
    public void multilinkVendor(Method method) throws Exception {
        generateSiteAndCabinet("Multilink","514");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        System.out.println("rec_"+addCabinetPage.getValueInField("CAB:Max Rectifiers"));
        System.out.println("battery_"+addCabinetPage.getValueInField("CAB:Max Battery Strings"));
        System.out.println("breaker_"+addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"));
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"0","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Myers", priority = 2)
    public void myersVendor(Method method) throws Exception {
        generateSiteAndCabinet("Myers","455");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        System.out.println("rec_"+addCabinetPage.getValueInField("CAB:Max Rectifiers"));
        System.out.println("battery_"+addCabinetPage.getValueInField("CAB:Max Battery Strings"));
        System.out.println("breaker_"+addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"));
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"1","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Nokia", priority = 2)
    public void nokiaVendor(Method method) throws Exception {
        generateSiteAndCabinet("Nokia","374");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        System.out.println("rec_"+addCabinetPage.getValueInField("CAB:Max Rectifiers"));
        System.out.println("battery_"+addCabinetPage.getValueInField("CAB:Max Battery Strings"));
        System.out.println("breaker_"+addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"));
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"0","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Nortel", priority = 2)
    public void nortelVendor(Method method) throws Exception {
        generateSiteAndCabinet("Nortel","440");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        System.out.println("rec_"+addCabinetPage.getValueInField("CAB:Max Rectifiers"));
        System.out.println("battery_"+addCabinetPage.getValueInField("CAB:Max Battery Strings"));
        System.out.println("breaker_"+addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"));
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"1","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Northstar", priority = 2)
    public void northstarVendor(Method method) throws Exception {
        generateSiteAndCabinet("Northstar","467");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        System.out.println("rec_"+addCabinetPage.getValueInField("CAB:Max Rectifiers"));
        System.out.println("battery_"+addCabinetPage.getValueInField("CAB:Max Battery Strings"));
        System.out.println("breaker_"+addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"));
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"2","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Pacific", priority = 2)
    public void PacificVendor(Method method) throws Exception {
        generateSiteAndCabinet("Pacific","375");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        System.out.println("rec_"+addCabinetPage.getValueInField("CAB:Max Rectifiers"));
        System.out.println("battery_"+addCabinetPage.getValueInField("CAB:Max Battery Strings"));
        System.out.println("breaker_"+addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"));
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"1","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Pantrol", priority = 2)
    public void PantrolVendor(Method method) throws Exception {
        generateSiteAndCabinet("Pantrol","453");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        System.out.println("rec_"+addCabinetPage.getValueInField("CAB:Max Rectifiers"));
        System.out.println("battery_"+addCabinetPage.getValueInField("CAB:Max Battery Strings"));
        System.out.println("breaker_"+addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"));
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"0","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Precision Quincy Corp", priority = 2)
    public void precisionQuincyCorpVendor(Method method) throws Exception {
        generateSiteAndCabinet("Precision Quincy Corp","482");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        System.out.println("rec_"+addCabinetPage.getValueInField("CAB:Max Rectifiers"));
        System.out.println("battery_"+addCabinetPage.getValueInField("CAB:Max Battery Strings"));
        System.out.println("breaker_"+addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"));
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"0","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Purcell", priority = 2)
    public void PurcellVendor(Method method) throws Exception {
        generateSiteAndCabinet("Purcell","396");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        System.out.println("rec_"+addCabinetPage.getValueInField("CAB:Max Rectifiers"));
        System.out.println("battery_"+addCabinetPage.getValueInField("CAB:Max Battery Strings"));
        System.out.println("breaker_"+addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"));
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"4","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"24","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"10","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Rack", priority = 2)
    public void RackVendor(Method method) throws Exception {
        generateSiteAndCabinet("Rack","424");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        System.out.println("rec_"+addCabinetPage.getValueInField("CAB:Max Rectifiers"));
        System.out.println("battery_"+addCabinetPage.getValueInField("CAB:Max Battery Strings"));
        System.out.println("breaker_"+addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"));
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"4","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"24","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"10","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }


    @Test(groups = { "Integration" }, description = "RBA", priority = 2)
    public void RBAVendor(Method method) throws Exception {
        generateSiteAndCabinet("RBA","377");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        System.out.println("rec_"+addCabinetPage.getValueInField("CAB:Max Rectifiers"));
        System.out.println("battery_"+addCabinetPage.getValueInField("CAB:Max Battery Strings"));
        System.out.println("breaker_"+addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"));
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Telect", priority = 2)
    public void telectVendor(Method method) throws Exception {
        generateSiteAndCabinet("Telect","413");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        System.out.println("rec_"+addCabinetPage.getValueInField("CAB:Max Rectifiers"));
        System.out.println("battery_"+addCabinetPage.getValueInField("CAB:Max Battery Strings"));
        System.out.println("breaker_"+addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"));
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"2","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"6","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"5","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Tesco", priority = 2)
    public void tescoVendor(Method method) throws Exception {
        generateSiteAndCabinet("Tesco","486");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        System.out.println("rec_"+addCabinetPage.getValueInField("CAB:Max Rectifiers"));
        System.out.println("battery_"+addCabinetPage.getValueInField("CAB:Max Battery Strings"));
        System.out.println("breaker_"+addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"));
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"0","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Transtector", priority = 2)
    public void transtectorVendor(Method method) throws Exception {
        generateSiteAndCabinet("Transtector","478");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        System.out.println("rec_"+addCabinetPage.getValueInField("CAB:Max Rectifiers"));
        System.out.println("battery_"+addCabinetPage.getValueInField("CAB:Max Battery Strings"));
        System.out.println("breaker_"+addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"));
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"0","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

    @Test(groups = { "Integration" }, description = "Westel", priority = 2)
    public void westelVendor(Method method) throws Exception {
        generateSiteAndCabinet("Westel","435");
        AssertionsUtil softAssert = new AssertionsUtil();
        cabinetTrackerPage = mainSideMenu.goToCabinetTrackerPage();
        cabinetTrackerPage.searchForValue(site.siteId, "S:Site Code");
        addCabinetPage = cabinetTrackerPage.selectEditOption();
        String parentWindow = addCabinetPage.switchToAddCabinetPage();
        System.out.println("rec_"+addCabinetPage.getValueInField("CAB:Max Rectifiers"));
        System.out.println("battery_"+addCabinetPage.getValueInField("CAB:Max Battery Strings"));
        System.out.println("breaker_"+addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"));
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Battery Strings"),"0","CAB:Max Battery Strings");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max DC Load Breaker Slots / Poles Used"),"0","CAB:Max DC Load Breaker Slots / Poles Used");
        softAssert.assertContains(addCabinetPage.getValueInField("CAB:Max Rectifiers"),"0","CAB:Max Rectifiers");
        addCabinetPage.closeAddCabinet(parentWindow);
        softAssert.closeAssert();
    }

}
