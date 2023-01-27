package common;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
//import org.apache.log4j.BasicConfigurator;
//import org.apache.log4j.PropertyConfigurator;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.*;
import utility.TextDataWriterReader;
import utility.ZipUtils;

import javax.ws.rs.client.Client;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static utility.JdbcSQLServerConnection.getSqlConnection;
import static utility.JdbcSQLServerConnection.insertMonirotingLogToMsSql;


public abstract class BaseTest_V2 {
	public WebDriver driver = null;
	// Global Variable for JIRA Integrations
	public static Client client = null;
//	public static String projectId = null;
	//public static String build = null;
	public static String version = null;
	public static int cycleId =0;
	public static String baseJiraURL = null;
	public static String jiraUserId = null;
	public static boolean jiraIntregration ;



	// Public Variables
// New API Code
	public static String  projectId = "";
	public static String versionId = "";
	public static String cycleName = "";
	public static String cycleDescription = "";
	public static String cycleID ="0";
	public static String JiraBaseUrl = "";
	public static String userName = "";
	public static String password = "";
	public static String authString ="";
	public static String build ="";
	public static String sprintId ="";
	public static String ServiceURI ="";


//// End New API Code

	public static String testSessionName = null;
	String envURL = System.getProperty("TestEnv");
	Hashtable testResultData = new Hashtable();
	DateFormat dateFormatMonitoring= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");



	@Parameters({"selGrid","nodeURL", "os", "browserName", "url","localDriverPath"})
	@BeforeClass (alwaysRun = true)
	public void beforeClass(@Optional("false") boolean selGrid,@Optional("http://10.154.94.65:5557/wd/hub")
			String nodeURL, @Optional("Windows 7") String os, @Optional("chrome") String browserName,@Optional("http://www.t-mobile.com") String url,@Optional("C:/driver/chromedriver.exe") String localDriverPath) throws IOException, InterruptedException {
		//BasicConfigurator.configure();


		if (selGrid == true) {
			//run in Selenium Grid/Remote
			getRemoteDriver(nodeURL, os, browserName, "");
		} else {
			getLocalDriver(browserName,localDriverPath);
		}


		//getLocalDriver(browserName,localDriverPath);
	//	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	//	String url = "http://oneconsole.t-mobile.com/";
		// Set Environments Variables
		//String envURL = System.getProperty("TEST_ENV");
		if(envURL!=null)
		{
			url = 	envURL;
		}

		System.out.println(url);

		driver.get(url);

			//window()
		driver.manage().window().maximize();
		//Dimension dimension = new Dimension(1920, 1024);
	//	driver.manage().window().setSize(dimension);
		}

	@Parameters({"selGrid", "userName", "nodeURL", "os", "browserName", "browserVersion", "url", "rptFilePath","deviceType","localDriverPath","monitoringDB","jsonReports"})
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(@Optional("false") boolean selGrid, @Optional("tanvirahmmed") String userName, @Optional("http://10.154.94.65:5557/wd/hub")
			String nodeURL, @Optional("Windows 7") String os, @Optional("chrome") String browserName, @Optional("")
									 String browserVersion, @Optional("http://www.t-mobile.com") String url, @Optional("C:/Report/test.html") String rptFilePathP,Method method,@Optional("desktop") String deviceType,@Optional("D:/chromedriver.exe") String localDriverPath,@Optional("false") Boolean monitoringDB,@Optional("false") Boolean jsonReports) throws IOException, InterruptedException {

		driver.navigate().refresh();
		ExtentTestManager.startTest(""+method.getName());
		ExtentTestManager.getTest().log(LogStatus.INFO,"Open Browser and navigate to "+driver.getCurrentUrl().toString(),"Browser Name: "+browserName);
		//ExtentTestManager.extent..config().setAutoCreateRelativePathMedia(true);
		//ExtentTestManager.getTest().

		// Dashboard Intregration
		if(monitoringDB||jsonReports) {
			// FINAL CODE for Intregration

			try {
				Date testStartDate = new Date();
				// 1. transactions_id
				//testData.add("1");
				testResultData.put("transactions_id", testStartDate.getTime());
				//  Retrive the data from Log file
				String[] txtLog = TextDataWriterReader.getSessionLog();
				testResultData.put("session_start_time", txtLog[1]);
				testResultData.put("test_node", nodeURL);
				// Get the Test Method ID from Test Function Name
				String testId = (method.getName().toString().split("_"))[1];
				testResultData.put("test_id", testId);

				// get the feature ID
			    Test testClass = method.getAnnotation(Test.class);
				String featureID = testClass.groups()[1];
				String testDescription = testClass.description();
				testResultData.put("test_name", testDescription);

				testResultData.put("session_name", txtLog[0]+":"+testClass.groups()[2]);
				testResultData.put("application_name",testClass.groups()[2]);
				testResultData.put("component_name",testClass.groups()[3]);
				testResultData.put("component_display_name",testClass.groups()[4]);
				testResultData.put("feature_name",testClass.groups()[5]);
				testResultData.put("feature_display_name",testClass.groups()[6]);
				testResultData.put("feature_weight",Integer.parseInt(testClass.groups()[7]));
				testResultData.put("PerformanceThreshold",Integer.parseInt(testClass.groups()[8]));


				testResultData.put("test_start_time", dateFormatMonitoring.format(testStartDate));
				testResultData.put("created_by", "Automation Framework");
				testResultData.put("create_date", dateFormatMonitoring.format(testStartDate));
				testResultData.put("json_File_Path",txtLog[2].toString());
				Properties prop = LoadPropertiesFiles.loadProperties(System.getProperty("user.dir") + "/src/main/resources/reportsPathSettings.properties");
				testResultData.put("test_result_url", prop.getProperty("App.reportPublishUrl") + "" + txtLog[0].toString()+"/");

			}
			catch(Exception ex)
			{
				System.out.print("Test Case ERROR/ Format Did not match ");
			}
		}

	}
	@Parameters({"monitoringDB","jsonReports"})
	@AfterMethod(alwaysRun = true)
	protected void afterMethod(ITestResult result,Method method, @Optional("false") Boolean monitoringDB,@Optional("false") Boolean jsonReports) throws Exception {
		int testStatus = 0;
		String fileName = "FAIL  - Error Message Generated on Details Reports";
		String failMsg = "";
		System.out.println("Working Directory = " +
				System.getProperty("user.dir"));

		if (result.getStatus() == ITestResult.FAILURE) {

			Date now = new Date();
			DateFormat shortDf = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

			String currentDate = shortDf.format(now).toString().replace("/", "_");
			currentDate = currentDate.toString().replace(" ", "_");
			currentDate = currentDate.toString().replace(":", "_");
			String methodName = "SC_error__"+currentDate;
			 fileName = System.getProperty("user.dir")+"/Reports/failure_screenshots/"+methodName+".png";
			takeSnapShot(driver,fileName);
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Error Screenshort"+ ExtentTestManager.getTest().addScreenCapture("failure_screenshots/"+methodName+".png"));
			ExtentTestManager.getTest().log(LogStatus.FAIL, result.getThrowable());
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed");
			testStatus =2;
			//failMsg =  result.getThrowable().toString();
		} else if (result.getStatus() == ITestResult.SKIP) {
			ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
			testStatus =3;
		} else {
			ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
			testStatus =1;
		}

		ExtentManager.getReporter().endTest(ExtentTestManager.getTest());
		ExtentManager.getReporter().flush();

		//driver.quit();

		ExtentTestManager.getTest().log(LogStatus.INFO,"Browser Closed");


		if(monitoringDB||jsonReports) {
			try {

				testResultData.put("test_Status", testStatus);
				if (testStatus == 1) {
					testResultData.put("test_message", "PASS");
				} else {
					testResultData.put("test_message","FAIL  - Error Message Generated on Details Reports");
				}

				testResultData.put("screenshot_uri", fileName);

				Date endDateTime = new Date();

				testResultData.put("test_end_time", dateFormatMonitoring.format(endDateTime));
				testResultData.put("session_end_time", dateFormatMonitoring.format(endDateTime));


				//SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
				String dateStartInString = testResultData.get("test_start_time").toString();
				Date dateStart = dateFormatMonitoring.parse(dateStartInString);
                String dateEndInString = testResultData.get("test_end_time").toString();
				Date dateEnd = dateFormatMonitoring.parse(dateEndInString);
				long duration = dateEnd.getTime() - dateStart.getTime();
				testResultData.put("test_duration_ms", duration);

                String dateStartSessionInString = testResultData.get("session_start_time").toString();
                Date dateSessionStart = dateFormatMonitoring.parse(dateStartSessionInString);
                String dateEndSessionInString = testResultData.get("session_end_time").toString();
                Date dateEndSession = dateFormatMonitoring.parse(dateEndSessionInString);
                long durationSession = dateEndSession.getTime() - dateSessionStart.getTime();
				testResultData.put("session_duration_ms", durationSession);

                if(monitoringDB) {
                    try {
                        insertMonirotingLogToMsSql(getSqlConnection(), testResultData);
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Unable to Update log into database. may be Data issue or Database connectivity issue");
                    }
                }

                if(jsonReports) {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        // Writing to a file
                        File file = new File(testResultData.get("json_File_Path").toString());
                        testResultData.put("json_File_Path", "**hidden**");
                        //	file.createNewFile();
                        FileWriter fileWriter = new FileWriter(file, true);
                        fileWriter.append('\n');
                        fileWriter.append('[');
                        fileWriter.append('\n');
                        fileWriter.append(mapper.defaultPrettyPrintingWriter().writeValueAsString(testResultData));
                        fileWriter.append('\n');
                        fileWriter.append(']');
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (Exception e) {
                        System.out.println("Unable to Write Json file");
                    }
                }

			}
			catch(Exception ex)
			{
				System.out.println("Database ERROR ");
			}
		}

		// NEw Code for JIRA UPDATES

		if(jiraIntregration) {
			String methodId = (result.getMethod().getMethodName().split("_"))[1];
			System.out.println(methodId);

			Test testClass = method.getAnnotation(Test.class);
			String testDescription = testClass.description();
			String[] testIdDes = testClass.description().split(":");
			String TestId = testIdDes[0].trim().toString();
			// testResultData.put("test_name", testDescription);
			ArrayList<String> issueIds = new ArrayList<String>();
			issueIds.add(TestId);
			JSONObject addTestsObj = new JSONObject();
			addTestsObj.put("cycleId", cycleID);
			addTestsObj.put("issues", new JSONArray(issueIds));
			addTestsObj.put("method", "1");
			addTestsObj.put("projectId", projectId);
			addTestsObj.put("versionId", versionId);
			System.out.println(addTestsObj.toString());
			StringEntity addTestsJSON = null;
			try {
				addTestsJSON = new StringEntity(addTestsObj.toString());
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			String ID = addTestsToCycle(authString, JiraBaseUrl, addTestsJSON);

			//  Execution
			JSONObject addTestsObj2 = new JSONObject();
			addTestsObj2.put("cycleId", cycleID);
			addTestsObj2.put("issueId", methodId);
			addTestsObj2.put("projectId", projectId);
			addTestsObj2.put("versionId", versionId);
			addTestsObj2.put("assigneeType", "assignee");
			addTestsObj2.put("assignee", userName);
			System.out.println(addTestsObj2.toString());
			StringEntity addTestsJSONE = null;
			try {
				addTestsJSONE = new StringEntity(addTestsObj2.toString());
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			String executionID = TestCaseExecute(authString, JiraBaseUrl, addTestsJSONE);

			if (executionID != "0") {
				JSONObject addTestsObjUp = new JSONObject();
				addTestsObjUp.put("status", testStatus);
				System.out.println(addTestsObjUp.toString());
				StringEntity addTestsJSONUpData = null;
				try {
					addTestsJSONUpData = new StringEntity(addTestsObjUp.toString());
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				int statusID = UpdateTestResult(authString, JiraBaseUrl, addTestsJSONUpData, executionID);

			}
		}
	}
		/*// JIRA Updates
		if(jiraIntregration) {
			try {

				String methodId = (result.getMethod().getMethodName().split("_"))[1];
				// int methodId methodId = (int) methodNameArray;][1];

				Entity payloadE = Entity.json("{  \"cycleId\": \"" + cycleId + "\",  \"issueId\": \"" + methodId + "\",  \"projectId\": \"" + projectId + "\",  \"versionId\": \"" + version + "\",  \"assigneeType\": \"assignee\",  \"assignee\": \"" + jiraUserId + "\"}");
				Response responseE = client.target(baseJiraURL + "/rest/zapi/latest/execution")
						.request(MediaType.APPLICATION_JSON_TYPE)
						.post(payloadE);

				String[] array = responseE.readEntity(String.class).split(":");
				String resultRest = array[0].substring(2);
				resultRest = resultRest.substring(0, resultRest.length() - 1);

				Entity payload22 = Entity.json("{\"status\": \"" + testStatus + "\"}");
				Response response22 = client.target(baseJiraURL + "/rest/zapi/latest/execution/" + resultRest + "/execute")
						.request(MediaType.APPLICATION_JSON_TYPE)
						.put(payload22);
			} catch (Exception ex) {
				System.out.println("Problem with JIRA Intregration");
			}
		}

	}*/

	protected String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}


	// Before Class

	@AfterClass(alwaysRun = true)
		public void afterClass()
		{
		try {
			driver.quit();
		}
		catch(Exception ex)
		{

		}
	}
	// Function for Create Local Driver instance.

	public WebDriver getLocalDriver(String browserName, String localDriverPath) {
		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", localDriverPath);
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} else if (browserName.equalsIgnoreCase("firefox")) {

			System.setProperty("webdriver.gecko.driver", localDriverPath);

			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("ie")) {
			driver = new InternetExplorerDriver();
		}
		return driver;
	}

	public WebDriver getRemoteDriver(String nodeURL, String os, String browserName,
									 String browserVersion) throws IOException, InterruptedException {
		{
			ChromeOptions options = new ChromeOptions();
			DesiredCapabilities capability = new DesiredCapabilities();
			capability.setBrowserName(browserName);
			capability.setPlatform(Platform.extractFromSysProperty(os));
			options.merge(capability);
			driver = new RemoteWebDriver(new URL(nodeURL), options);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			return driver;
		}



	}
	public void sleepFor(int sec)throws InterruptedException{
		Thread.sleep(sec * 1000);
	}

	public static void takeSnapShot(WebDriver driver, String fileWithPath) throws Exception{

		//Convert web driver object to TakeScreenshot

		TakesScreenshot scrShot =((TakesScreenshot)driver);

		//Call getScreenshotAs method to create image file

		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

		//Move image file to new destination

		File DestFile=new File(fileWithPath);

		//Copy file at destination

		FileUtils.copyFile(SrcFile, DestFile);


	}
	// Methods for Accessing JIRA Intregrations.
	@Parameters({"updateOnJira","jiraPropertiesFile","jsonReports"})
	@BeforeSuite(alwaysRun = true)
	protected void createJiraTestCycle(@Optional("false") boolean updateOnJira, @Optional("") String jiraPropertiesFile,@Optional("false") boolean jsonReports) throws IOException {
		// LOG4J Configuration
		//PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/main/resources/Log4j.properties");
	//	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		// Create the session Name.
		DateFormat sessionNamePart1 = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date2 = new Date();
		testSessionName = sessionNamePart1.format(date2);
//////////////////////// Write to Log Files

if(jsonReports) {
    Properties prop2 = LoadPropertiesFiles.loadProperties(System.getProperty("user.dir") + "/src/main/resources/reportsPathSettings.properties");
    String jsonReportPath = prop2.getProperty("App.jsonOutputPath");
    String jsonFileNamePrefix = prop2.getProperty("App.jsonFileNamePrefix");
    String jsonFiles = jsonReportPath + "/" + jsonFileNamePrefix + "" + testSessionName + ".json";   //System.getProperty("user.dir")+"/Reports.zip";
    TextDataWriterReader.logCurrentSession(testSessionName, dateFormatMonitoring.format(date), jsonFiles);
    File file = new File(jsonFiles);
    file.createNewFile();
}
		/////////////////


		jiraIntregration = updateOnJira;

  // Common User ID ad Password for any kind of access
        userName = System.getProperty("USER_ID");
		password = System.getProperty("USER_PASS");

		if(userName==null || password==null) {

			// Read AppCredentials FROM Text File.
			try {
				String fileName = System.getProperty("user.dir") + "/AppCredentials.txt";
				String line = null;
				String[] logData = new String[4];

				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader);

				for (int i = 0; (line = bufferedReader.readLine()) != null; ++i) {
					logData[i] = line;
				}
				userName = logData[0];
				password = logData[1];

				bufferedReader.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}



		//////////////////


		Properties prop = LoadPropertiesFiles.loadProperties(System.getProperty("user.dir") + "/src/main/resources/JiraZephyr.properties");
		ServiceURI = prop.getProperty("AUT.ServiceURI");
		if(jiraIntregration)
		{
			try
			{
			//	userName = prop.getProperty("JIRA.UserName");
			//	password = prop.getProperty("JIRA.Password");
				projectId = prop.getProperty("AUT.ProjectId");
				versionId = prop.getProperty("AUT.VersionId");
				versionId = prop.getProperty("AUT.VersionId");
				build = prop.getProperty("AUT.Build");
				sprintId = prop.getProperty("AUT.SprintId");

				cycleDescription = prop.getProperty("AUT.TestCycleDes");
				JiraBaseUrl = prop.getProperty("JIRA.BaseJiraURL");
				String environment = prop.getProperty("AUT.Environment");
				String testCycleNamePreFix = prop.getProperty("AUT.TestCycleNamePreFix");
				String testCycleDes = prop.getProperty("AUT.TestCycleDes");
				Date dateobj = new Date();
				DateFormat df = new SimpleDateFormat("MMM_dd_yy_HHmmss");
				String startDate = (new SimpleDateFormat("dd/MMM/yy")).format(dateobj);
				String endDate = (new SimpleDateFormat("dd/MMM/yy")).format(dateobj);
				cycleName = testCycleNamePreFix + " [ " + df.format(dateobj)+" ]";

				// Create Authentication
				authString = createAuthString(userName,password);

				/** Cycle Object created - DO NOT EDIT **/
				org.json.simple.JSONObject createCycleObj = new org.json.simple.JSONObject();
				createCycleObj.put("name", cycleName);
				createCycleObj.put("build", build);
				createCycleObj.put("environment", environment);
				createCycleObj.put("description", cycleDescription);
				createCycleObj.put("startDate", startDate);
				createCycleObj.put("endDate", endDate);
				createCycleObj.put("projectId", projectId);
				createCycleObj.put("versionId", versionId);
				createCycleObj.put("sprintId", sprintId);

				System.out.println(createCycleObj.toString());
				StringEntity cycleJSON = null;
				try {
					cycleJSON = new StringEntity(createCycleObj.toString());
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				cycleID = createCycle(authString, JiraBaseUrl, cycleJSON);
				System.out.println("Cycle Created with Cycle Id :" + cycleID);
			}
			catch(Exception ex)
			{
				System.out.println("JIRA Intregration Error : "+ex.toString());
			}

		}

	}


	@Parameters({"ZipReports","webPublish"})
	@AfterSuite(alwaysRun = true)
	protected void zipReportsAction(@Optional("false") boolean ZipReports, @Optional("false") boolean webPublish)
	{
		try{
			ZipUtils zip = new ZipUtils();
			if(webPublish) {
				//DateFormat dateFormatFolder = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss_SSS");
				String[] txtLog = TextDataWriterReader.getSessionLog();
				Date dateRpt = new Date();
				Properties prop = LoadPropertiesFiles.loadProperties(System.getProperty("user.dir") + "/src/main/resources/reportsPathSettings.properties");
				File sourceLocation = new File(System.getProperty("user.dir") + "/Reports");
				File targetLocation = new File(prop.getProperty("App.reportPublishDir") + "/" + txtLog[0].toString());
				zip.copyDirectory(sourceLocation, targetLocation);
			}
			if(ZipReports)
			{
				zip.zipReports();;
			}

		}
		catch (Exception ex)
		{
			System.out.println(ex.toString());
		}
	}


	// All New API Methods

	// JIRA Authentication String Creator
	public static String createAuthString(String username, String password) {
		String name = username;
		String pwd = password;
		String authString = name + ":" + pwd;
		//  System.out.println("auth string: " + authString);
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		System.out.println("Base64 encoded auth string: " + authStringEnc);
		System.out.println("***********************************************************");
		return authStringEnc;
	}
	// Create A Cycle
	public static String createCycle(String authStr,String JiraBaseUrl, StringEntity cycleJSON)
			throws ClientProtocolException,URISyntaxException, JSONException {

		String uriStr = JiraBaseUrl+"/rest/zapi/latest/cycle/";
		URI uri = new URI(uriStr);
		System.out.println(uri.toString());


		HttpResponse response = null;
		HttpClient restClient = new DefaultHttpClient();

		HttpPost createCycleReq = new HttpPost(uri);
		createCycleReq.addHeader("Content-Type", "application/json");
		createCycleReq.addHeader("Authorization", "Basic "+authStr);
		createCycleReq.setEntity(cycleJSON);

		try {
			response = restClient.execute(createCycleReq);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}

		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		String cycleId = "-1";
		if (statusCode >= 200 && statusCode < 300) {
			HttpEntity entity = response.getEntity();
			String string = null;
			try {
				string = EntityUtils.toString(entity);
				JSONObject cycleObj = new JSONObject(string);
				cycleId = cycleObj.getString("id");
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			try {
				throw new ClientProtocolException("Unexpected response status: " + statusCode);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			}
		}
		return cycleId;
	}

	public static String addTestsToCycle(String authStr, String JiraBaseUrl,StringEntity addTestsJSON)
			throws URISyntaxException, JSONException, IllegalStateException, IOException {

		System.out.println("CYCLE DATA : "+addTestsJSON.toString());

		String uriStr = JiraBaseUrl + "/rest/zapi/latest/execution/addTestsToCycle";
		URI uri = new URI(uriStr);
		System.out.println(uri.toString());

		HttpResponse response = null;
		HttpClient restClient = new DefaultHttpClient();

		HttpPost addTestsReq = new HttpPost(uri);
		addTestsReq.addHeader("Content-Type", "application/json");
		addTestsReq.addHeader("Authorization", "Basic "+authStr);
		addTestsReq.setEntity(addTestsJSON);

		try {
			response = restClient.execute(addTestsReq);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int statusCode = response.getStatusLine().getStatusCode();

		String string = null;
      /*  HttpEntity entity1 = response.getEntity();
        try
        {
            String tokenObject = EntityUtils.toString(entity1);
            string = new JSONObject(tokenObject).getString("jobProgressToken");
        }
        catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int maxTryCount = 0;
        boolean checkJobProgress = true;

        while (checkJobProgress) {
            maxTryCount++;
            try {
                checkJobProgress = checkJobProgress(JiraBaseUrl,authStr, string);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (checkJobProgress) checkJobProgress = false;
            if (maxTryCount != 10) continue; checkJobProgress = false;
        }

        if ((statusCode < 200) || (statusCode >= 300)) {
            try {
                throw new ClientProtocolException("Unexpected response status: " + statusCode);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            }
        }
*/
		return string;
	}
	private static boolean checkJobProgress(String JiraBaseUrl,String authStr, String token)
	{
		try
		{
			TimeUnit.SECONDS.sleep(10L);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		boolean jobCompleted = false;
		HttpResponse response = null;

		String url = null;
		HttpClient restClient = new DefaultHttpClient();
		try
		{
			url = JiraBaseUrl+"/rest/zapi/latest/execution/jobProgress/"+token+"?type=add_tests_to_cycle_job_progress";
			URI uri = new URI(url);
			System.out.println(uri.toString());

			HttpGet jobProgressRequest = new HttpGet(uri);

			jobProgressRequest.addHeader("Content-Type", "application/json");
			jobProgressRequest.addHeader("Authorization", "Basic "+authStr);

			response = restClient.execute(jobProgressRequest);
			String result = EntityUtils.toString(response.getEntity());
			System.out.println(result);

			String progress = new JSONObject(result).get("progress").toString();

			if ((progress != null) && (progress.equals("1.0")))
				jobCompleted = true;

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return jobCompleted;
	}


	// Test Case Execution

	public static String TestCaseExecute(String authStr, String JiraBaseUrl,StringEntity addTestsJSON)
			throws URISyntaxException, JSONException, IllegalStateException, IOException {

		System.out.println(addTestsJSON.toString());

		String uriStr = JiraBaseUrl + "/rest/zapi/latest/execution";
		URI uri = new URI(uriStr);
		System.out.println(uri.toString());

		HttpResponse response = null;
		HttpClient restClient = new DefaultHttpClient();

		HttpPost addTestsReq = new HttpPost(uri);
		addTestsReq.addHeader("Content-Type", "application/json");
		addTestsReq.addHeader("Authorization", "Basic "+authStr);
		addTestsReq.setEntity(addTestsJSON);

		try {
			response = restClient.execute(addTestsReq);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//  int statusCode = response.getStatusLine().getStatusCode();


		int statusCode = response.getStatusLine().getStatusCode();
		// response.toString();

		System.out.println(statusCode);
		String EId = "0";
		if (statusCode >= 200 && statusCode < 300) {
			HttpEntity entity = response.getEntity();
			String string = null;
			try {
				string = EntityUtils.toString(entity);

				String[] array = string.split(":");
				String resultRest = array[0].substring(2);
				resultRest = resultRest.substring(0, resultRest.length() - 1);


				//  JSONObject cycleObj = new JSONObject(string);
				EId = resultRest;
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			try {
				throw new ClientProtocolException("Unexpected response status: " + statusCode);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Execution: ID : "+EId);



		return EId;
	}

	// Update Test Result

	public static int UpdateTestResult(String authStr, String JiraBaseUrl,StringEntity addTestsJSON,String eId)
			throws URISyntaxException, JSONException, IllegalStateException, IOException {

		System.out.println(addTestsJSON.toString());

		String uriStr = JiraBaseUrl + "/rest/zapi/latest/execution/"+eId+"/execute";
		URI uri = new URI(uriStr);
		System.out.println(uri.toString());

		HttpResponse response = null;
		HttpClient restClient = new DefaultHttpClient();

		HttpPut addTestsReq = new HttpPut(uri);
		addTestsReq.addHeader("Content-Type", "application/json");
		addTestsReq.addHeader("Authorization", "Basic "+authStr);
		addTestsReq.setEntity(addTestsJSON);

		try {
			response = restClient.execute(addTestsReq);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int statusCode = response.getStatusLine().getStatusCode();
		return statusCode;
	}

}