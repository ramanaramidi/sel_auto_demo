package common;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {  // new
    static Map extentTestMap = new HashMap();
    static ExtentReports extent = ExtentManager.getReporter();

    public static synchronized ExtentTest getTest() {
        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized void endTest() {
        extent.endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));
    }

    public static synchronized ExtentTest startTest(String testName) {
        return startTest(testName, "");
    }

    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extent.startTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);

        return test;
    }

    public static void printReport(String step, String status, Class log) {
        if(log==null){
            log = ExtentTestManager.class;
        }
        Class loggerReportClass = log;
        Logger logReport = LoggerFactory.getLogger(loggerReportClass);
        LogStatus printStatus;
        switch (status){
            case "passed":
            case "PASSED":
                printStatus = LogStatus.PASS;
                break;
            case "failed":
            case "FAILED":
                printStatus = LogStatus.FAIL;
                break;
            case "info":
            case "INFO":
                printStatus = LogStatus.INFO;
                break;
            case "warn":
            case "WARN":
                printStatus = LogStatus.WARNING;
                break;
            default: printStatus = LogStatus.UNKNOWN;
                break;
        }

        logReport.info(printStatus.toString() + ": " + step +
                "\n **********************************************************************************************************");

        getTest().log(printStatus,step);
    }

    public static void printAssertion(String Method,String Status,String Description,Object Left,Object Right,Class log){
        String statusDescription = Method + " '" + Status + "' : " + Description + " - Left : " + Left + " - Right : " + Right;
        printReport(statusDescription, Status, log);
    }
}