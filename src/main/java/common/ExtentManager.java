package common;

import com.relevantcodes.extentreports.ExtentReports;

import java.io.File;

public class ExtentManager {

	static ExtentReports extent;
//	final static String filePath = "Reports/TestReports.html";
	//final static String configFilePath = "Reports/config.xml";

	final static String filePath = System.getProperty("user.dir")+"/reports/TestReports.html";
	final static String configFilePath = System.getProperty("user.dir")+"/src/main/resources/config.xml";

	public synchronized static ExtentReports getReporter() {
		if (extent == null) {
			extent = new ExtentReports(filePath, true);

			extent.addSystemInfo("t-mobile.com","QA Enviroment");
			extent.addSystemInfo("Author ","Tanvir Ahmmed");
			extent.loadConfig(new File(configFilePath));
			//loadConfig(new File("C:\HelloWorld\extent-config.xml"));
			// extent.loadConfig(new File(configfilePath));

		}

		return extent;
	}

}
