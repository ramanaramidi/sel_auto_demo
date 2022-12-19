package web.mb.seeder;

import com.jayway.jsonpath.JsonPath;
import common.BaseTest;
import commons.enums.SeedingDataTypeEnum;
import commons.objects.*;
import org.testng.annotations.Test;
import rest.project.ProjectHelper;
import rest.ring.RingHelper;
import rest.ring.RingService;
import pages.web.Tracker.AddRingPage;
import pages.web.Tracker.site.AddSitePage;
import pages.web.Tracker.RingTrackerPage;
import pages.web.Tracker.site.SiteTrackerPage;
import pages.web.components.MainSideMenu;
import pages.web.onboarding.LoginPage;
import rest.site.SiteHelper;
import testData.TrackerData;
import testData.UserData;
import utility.helper.MiscHelpers;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

public class TrackerSeeder extends BaseTest{
    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");
    public Users alphaUser = new Users();
    public Users superUser = new Users();
    LoginPage loginPage;
    MainSideMenu mainSideMenu;
    RingTrackerPage ringTracker;
    AddRingPage addNewRing;
    ArrayList<Trackers> dataObject;
    SiteTrackerPage siteTracker;
    AddSitePage addNewSite;
    SiteHelper siteHelper = new SiteHelper();
    RingHelper ringHelper = new RingHelper();
    ProjectHelper projectHelper = new ProjectHelper();

    public TrackerSeeder()
    {
        alphaUser = UserData.getAlphaUserDetails(alphaUser);
        superUser = UserData.getSuperUserDetails(superUser);
        dataObject = TrackerData.getInstance();
        if(envURL == null) {envURL = 	"https://magentabuiltstg.t-mobile.com/Login.do";}
        if(testSuite == null) {testSuite = 	"TestRunner.xml";}

    }

//    @Test(groups = {"Integration"},description = "login",priority = 1)
//    public void login(Method method) throws Exception {
//        loginPage = new LoginPage(driver);
//        loginPage.doLogin(LoginOptionEnum.SAML);
//        String url = loginPage.getLoginUrl("ALPHA");
//        loginPage.launchUrl(url);
//        mainSideMenu = loginPage.LoginAsUser(superUser);
//
//    }

    @Test
    void givenString_whenEncrypt_thenSuccess()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {

        String input = "Bearer ynB0rtJnNxjsvTLa4kmv:g1UFnFzplWac3R7ZWD4Khf8gnUC54wy84BC9X4dhDyEuYeGplUWfdHP4qlbGxvHaD3HPOZ";

        //SecretKey key = MiscHelpers.generateKey(128);
//        System.out.println("Key "+ Base64.getEncoder().encodeToString(key.getEncoded()));
//        System.out.println("KeyAlgo "+key.getAlgorithm());
        String key1 = "MMZbncy7/gnWBpoOyVENVg==";
        String s1 = "123456789asdfgtr";
        byte[] bytes = s1.getBytes();
        System.out.println("length "+bytes.length);
        final IvParameterSpec iv = new IvParameterSpec(bytes);
        String algorithm = "AES/CBC/PKCS5Padding";
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(key1), 0, Base64.getDecoder().decode(key1).length, "AES");
        String cipherText = "016uviW1rNM6YSMeMPoiLdIuEPDoLip3MupP0b2doOnPw330h95n5MrEa4CcTVbAtoZX6G29o9ZTl3cdpVt0KCEiIbJJxphhxbcu51lpNOU/oWyxUP+8tVmewVjsIAntydKwsqu84MfIERNTCV2maA==";
//        String cipherText = MiscHelpers.encrypt(algorithm, input, key, ivParameterSpec);
//        System.out.println("cipher "+ cipherText);
        String plainText = MiscHelpers.decrypt(algorithm, cipherText, key, iv);
        System.out.println("plainText "+ plainText);
    }

    @Test
    void givenString_whenEncrypt_thenSuccess1()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {

        String input = "Bearer sI5DcL5g0Joy7Xjbn0it:Tt49vtB5QRFWu3a6wj90dMQUmX1mYvGHIIjgx57N4NQAQeIevW2MFKDiA8gZAL9ggaRw76";
        SecretKey key = MiscHelpers.generateKey(128);
        System.out.println("Key "+ Base64.getEncoder().encodeToString(key.getEncoded()));
        System.out.println("KeyAlgo "+key.getAlgorithm());
        String s1 = "Magentabuiltapps";
        byte[] bytes = s1.getBytes();
        System.out.println("length "+bytes.length);
        final IvParameterSpec iv = new IvParameterSpec(bytes);
        IvParameterSpec ivParameterSpec = MiscHelpers.generateIv();
       // System.out.println("ivParameterSpecAlgo "+ Base64.getDecoder().decode(ivParameterSpec.getIV()));
        String algorithm = "AES/CBC/PKCS5Padding";
        String cipherText = MiscHelpers.encrypt(algorithm, input, key, iv);
        System.out.println("cipher "+ cipherText);
        String plainText = MiscHelpers.decrypt(algorithm, cipherText, key, iv);

    }

    @Test(groups = {"Integration"},description = "createNewRingTest",priority = 2,invocationCount = 1)
    public void createNewRingTest(Method method) throws Exception {
        RingService ringService = new RingService();
        ApiResponse response = ringService.createNewRing();
        String ringTracker = JsonPath.read(response.responseBody,"TRACKOR_KEY");
        System.out.println("data::::: "+response.responseBody);
        System.out.println("jsonPath:: "+ JsonPath.read(response.responseBody,"TRACKOR_KEY"));
        if(ringTracker !=null){
            Trackers ring = new Trackers(SeedingDataTypeEnum.NewRing,ringTracker,"RING",superUser.getNtCode());
            dataObject.add(ring);
            System.out.println(dataObject);
        }
    }

    @Test(groups = {"Integration"},description = "moveRingToActiveState",priority = 3,invocationCount = 5)
    public void moveRingToActiveState(Method method) throws Exception {
        String ringCode = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringCode, "Macro");
        ring = ringHelper.createAnActiveRing(ring);
        if(ring !=null){
            Trackers ringTracker = new Trackers(SeedingDataTypeEnum.ActiveRing,ring.ringId,"RING",superUser.getNtCode());
            dataObject.add(ringTracker);
            System.out.println(dataObject);
        }
    }

    @Test(groups = {"Integration"},description = "createNewSite",priority = 3,invocationCount = 1)
    public void createNewSite(Method method) throws Exception {
        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringId, "Macro");
        Site site = new Site(ringId,"Candidate","New Site");
        site = siteHelper.createNewRingAndSite(ring,site);
        System.out.println("data::::: "+site.siteId);
        if(site != null){
            Trackers siteTracker = new Trackers(SeedingDataTypeEnum.NewSite,site.siteId,"SITE",alphaUser.getNtCode());
                dataObject.add(siteTracker);
                System.out.println(dataObject);
        }
    }

    @Test(groups = {"Integration"},description = "createNewSite",priority = 3,invocationCount = 1)
    public void createActiveSite(Method method) throws Exception {
        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringId, "Macro");
        Site site = new Site(ringId,"Candidate","New Site");
        site = siteHelper.createActiveRingAndSite(ring,site);
        System.out.println("data::::: "+site.siteId);
        if(site != null){
            Trackers siteTracker = new Trackers(SeedingDataTypeEnum.ActiveSite,site.siteId,"SITE",alphaUser.getNtCode());
            dataObject.add(siteTracker);
            System.out.println(dataObject);
        }
    }

    @Test(groups = {"Integration"},description = "createNewSite",priority = 3,invocationCount = 1)
    public void createActivePrimarySite(Method method) throws Exception {
        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringId, "Indoor Node");
        Site site = new Site(ringId,"Primary","Active Site");
        site = siteHelper.createActiveRingAndPrimaryActiveSite(ring,site);
        System.out.println("data::::: "+site.siteId);
        if(site != null){
            Trackers siteTracker = new Trackers(SeedingDataTypeEnum.ActivePrimarySite,site.siteId,"SITE",alphaUser.getNtCode());
            dataObject.add(siteTracker);
            System.out.println(dataObject);
        }
    }

    @Test(groups = {"Integration"},description = "createNewSite",priority = 3,invocationCount = 1)
    public void createNewProjectFromScratch(Method method) throws Exception {
        String ringId = "AU" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringId, "Indoor Node");
        Site site = new Site(ringId,"Primary","Active Site");
        Project project = projectHelper.createNewProjectFromScratch(ring,site);
        System.out.println("data::::: "+project.projectId);
        if(project != null){
            Trackers projectTracker = new Trackers(SeedingDataTypeEnum.NewProject,site.siteId,"PROJECT",alphaUser.getNtCode());
            dataObject.add(projectTracker);
            System.out.println(dataObject);
        }
    }

    @Test(groups = {"Integration"},description = "createNewSite",priority = 3,invocationCount = 1)
    public void createActiveProjectFromScratch(Method method) throws Exception {
        String ringId = "AA" + MiscHelpers.getRandomString(5, true).toUpperCase();
        Ring ring = new Ring("Active", ringId, "Indoor Node");
        Site site = new Site(ringId,"Primary","Active Site");
        Project project = projectHelper.createActiveProjectFromScratch(ring,site);
        System.out.println("data::::: "+project.projectId);
        if(project != null){
            Trackers projectTracker = new Trackers(SeedingDataTypeEnum.NewProject,site.siteId,"PROJECT",alphaUser.getNtCode());
            dataObject.add(projectTracker);
            System.out.println(dataObject);
        }
    }

}
