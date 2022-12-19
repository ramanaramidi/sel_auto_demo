package utility;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 * Created by tahmmed1 on 12/12/2016.
 */
public class TestListners  implements ITestListener{
    public void onTestStart(ITestResult iTestResult) {

    }

    public void onTestSuccess(ITestResult iTestResult) {

    }

    public void onTestFailure(ITestResult iTestResult) {

      //  System.out.println("TEST CASE FAIL");
       // TakesScreenshot(driver, "c://test.png")
        Reporter.log("Taking Screenshort for fail test case");
        Reporter.log("SCREENSHORT <a href='../failure_screenshots/"+iTestResult.getMethod().getMethodName()+".png'>Screen Short</a>");

        //Reporter.log(" <img src='C:/failure_screenshots/"+iTestResult.getMethod().getMethodName()+".png'>");
    }

    public void onTestSkipped(ITestResult iTestResult) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    public void onStart(ITestContext iTestContext) {

    }

    public void onFinish(ITestContext iTestContext) {

    }


}
