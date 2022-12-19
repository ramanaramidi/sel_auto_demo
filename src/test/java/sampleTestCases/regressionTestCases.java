//package sampleTestCases;
//
//import common.BaseTest;
//import org.testng.annotations.Test;
//import pages.web.onboarding.LoginPage;
//
//import java.lang.reflect.Method;
//
///**
// * Created by TAhmmed1 on 5/24/2018.
// */
//public class regressionTestCases extends BaseTest {
//
//
//    public  String envURL = System.getProperty("TestEnv");
//    public  String testSuite = System.getProperty("TestRunner");
//    LoginPage loginPage;
//
//    public regressionTestCases()
//    {
//        if(envURL == null) {envURL = 	"https://magentabuiltstg.t-mobile.com/Login.do";}
//        if(testSuite == null) {testSuite = 	"TestRunner.xml";}
//
//    }
//
////clean test -DTestRunner=regression.xml -DTestEnv=http://changerecord-site.stg.px-prd01.cf.t-mobile.com
//
//    @Test(groups = {"Integration"},description = "SAMPLE-001: Verify Sample Integrations test Case - 1",priority = 1)
//    public void verifyIntegrationsTestCase_1(Method method) throws InterruptedException {
//        driver.manage().window().minimize();
//        loginPage = new LoginPage(driver);
//        loginPage.doLogin("SAML");
//        String url = loginPage.getLoginUrl();
//        loginPage.launchUrl(url);
//    }
//
//    @Test(groups = {"Integration"},description = "SAMPLE-001: Verify Sample Integrations test Case - 1",priority = 2)
//    public void verifyIntegrationsTestCase_2(Method method) throws InterruptedException {
//        driver.manage().window().minimize();
//        loginPage = new LoginPage(driver);
//        loginPage.doLogin("SAML");
//        String url = loginPage.getLoginUrl();
//        loginPage.launchUrl(url);
//
//    }
//
//    @Test(groups = {"Integration"},description = "SAMPLE-001: Verify Sample Integrations test Case - 1")
//    public void verifyIntegrationsTestCase_3(Method method) throws InterruptedException {
//        driver.manage().window().minimize();
//        loginPage = new LoginPage(driver);
//        loginPage.doLogin("SAML");
//        String url = loginPage.getLoginUrl();
//        loginPage.launchUrl(url);
//    }
//
//    @Test(groups = {"Integration"},description = "SAMPLE-001: Verify Sample Integrations test Case - 1")
//    public void verifyIntegrationsTestCase_4(Method method) throws InterruptedException {
//        driver.manage().window().minimize();
//        loginPage = new LoginPage(driver);
//        loginPage.doLogin("SAML");
//        String url = loginPage.getLoginUrl();
//        loginPage.launchUrl(url);
//    }
//
//    @Test(groups = {"Integration"},description = "SAMPLE-001: Verify Sample Integrations test Case - 1")
//    public void verifyIntegrationsTestCase_5(Method method) throws InterruptedException {
//        driver.manage().window().minimize();
//        loginPage = new LoginPage(driver);
//        loginPage.doLogin("SAML");
//        String url = loginPage.getLoginUrl();
//        loginPage.launchUrl(url);
//    }
//
//    @Test(groups = {"Integration"},description = "SAMPLE-001: Verify Sample Integrations test Case - 1")
//    public void verifyIntegrationsTestCase_6(Method method) throws InterruptedException {
//        driver.manage().window().minimize();
//        loginPage = new LoginPage(driver);
//        loginPage.doLogin("SAML");
//        String url = loginPage.getLoginUrl();
//        loginPage.launchUrl(url);
//    }
//}