package sampleTestCases;

import com.relevantcodes.extentreports.LogStatus;
import common.BaseTest;
import common.ExtentTestManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TAhmmed1 on 5/24/2018.
 */
public class smokeTestCases extends BaseTest {


    public  String envURL = System.getProperty("TestEnv");
    public  String testSuite = System.getProperty("TestRunner");


    public smokeTestCases()
    {
        if(envURL == null) {envURL = 	"http://t-mobile.com";}
        if(testSuite == null) {testSuite = 	"TestRunner.xml";}

    }

//clean test -DTestRunner=regression.xml -DTestEnv=http://changerecord-site.stg.px-prd01.cf.t-mobile.com

    @Test(groups = {"Integration"},description = "SAMPLE-001: Verify Sample Integrations test Case - 1")
    public void verifyIntegrationsTestCase_1(Method method) throws InterruptedException {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println("TEST ENVIRONMENT # "+envURL);
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("TEST SUITE # : "+testSuite);
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("TEST EXECUTION START  @ " + dateFormat.format(date)+" : "+method.getAnnotation(Test.class).description());
        System.out.println("-----------------------------------------------------------------------------------------------");

        ExtentTestManager.getTest().getTest().setName(method.getAnnotation(Test.class).description());
        ExtentTestManager.getTest().assignCategory("Component:Calculation");
        // Sample Assertion
        Assert.assertTrue(true,"Unable to Verify Sample Integration test Case");
        // Wait 2 Seconds - just to show the progress.
        Thread.sleep(2000);
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("TEST EXECUTION END @ " + dateFormat.format(date)+" : "+method.getAnnotation(Test.class).description());
        ExtentTestManager.getTest().log(LogStatus.PASS, method.getAnnotation(Test.class).description(), "Sample");
    }
}