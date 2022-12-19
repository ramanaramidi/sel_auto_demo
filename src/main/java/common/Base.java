package common;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
//import org.apache.logging.log4j.BasicConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by tahmmed1 on 12/12/2016.
 */
public class Base {
// This is the Base Class
    public WebDriver driver = null;

    // Global variable for Extent Reports
    protected ExtentReports extent;
    protected ExtentTest test;
    public String  rptFilePath = "C:\\Report\\test2.html";

    @Parameters({"selGrid","userName","nodeURL","os","browserName","browserVersion","url","rptFilePath"})

    // Primary Setup Method
    @BeforeMethod
    public void setUp(@Optional("false") boolean selGrid, @Optional("tanvirahmmed") String userName, @Optional("http://10.154.94.65:5557/wd/hub")
            String nodeURL, @Optional("Windows 7") String os, @Optional("chrome") String browserName, @Optional("")
                              String browserVersion, @Optional("http://www.t-mobile.com") String url,@Optional("C:\\Report\\test.html") String rptFilePathP)throws IOException {
        rptFilePath = rptFilePathP;
        //BasicConfigurator.configure();
        if(selGrid==true){
            //run in Selenium Grid/Remote
            getRemoteDriver(nodeURL,os,browserName,browserVersion);
        }else{
            getLocalDriver(browserName);
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get(url);

     //   Dimension dimension = new Dimension(414, 628);
       // driver.manage().window().setSize(dimension);

          driver.manage().window().maximize();



    }

    public WebDriver getLocalDriver(String browserName){
        if(browserName.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver","C:\\Users\\tahmmed1\\IdeaProjects\\T-Mobile-Live-Projects\\Generic\\selenium-browser-driver\\chromedriver.exe");
         driver = new ChromeDriver();

        }else if(browserName.equalsIgnoreCase("firefox")){


            System.setProperty("webdriver.gecko.driver","C:\\Users\\tahmmed1\\IdeaProjects\\T-Mobile-Live-Projects\\Generic\\selenium-browser-driver\\geckodriver.exe");

            driver = new FirefoxDriver();
        } else if(browserName.equalsIgnoreCase("ie")) {
            driver = new InternetExplorerDriver();
        }
        return driver;
    }

    public WebDriver getRemoteDriver(String nodeURL,String os, String browserName,
                                    String browserVersion)throws IOException {{
      //  System.setProperty("webdriver.chrome.driver","C:\\Users\\tahmmed1\\IdeaProjects\\T-Mobile-Live-Projects\\Generic\\selenium-browser-driver\\chromedriver.exe");
       // String nodeURL = "http://10.154.94.65:5557/wd/hub";
        ChromeOptions options = new ChromeOptions();
        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setBrowserName("chrome");
        capability.setPlatform(Platform.WINDOWS);
        options.merge(capability);
        driver = new RemoteWebDriver(new URL(nodeURL), options);
        return driver;
    }
    }
    @AfterMethod
    public void afterMethod(ITestResult result) throws Exception {
        if(result.getStatus()==ITestResult.FAILURE) {

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
            String methodName = result.getName();
         //   String fileName = "c:\\failure_screenshots\\"+methodName+"_"+formater.format(calendar.getTime())+".png";
            String fileName = "failure_screenshots\\"+methodName+".png";
            takeSnapShot(driver,fileName);
            Reporter.log(fileName);
        }

      /*  try {
            if (result.getStatus() == ITestResult.FAILURE) {
                test.log(LogStatus.FAIL, result.getThrowable());
            } else if (result.getStatus() == ITestResult.SKIP) {
                test.log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
            } else {
                test.log(LogStatus.PASS, "Test passed");
            }
        }
        catch (Exception e)
        {
         // test.log(e.toString());
        }
*/
     //   extent.endTest(test);
       // extent.flush();

        driver.quit();
    }


    @BeforeSuite
    public void beforeSuite() {

    }

    @AfterSuite
    protected void afterSuite() {
        //extent.close();
    }





    public void clickByCss(String locator) {
        driver.findElement(By.cssSelector(locator)).click();
    }

    public void clickById(String locator) {
        driver.findElement(By.id(locator)).click();
    }

    public void clickByXpath(String locator) {
        driver.findElement(By.xpath(locator)).click();
    }

    public void typeByCss(String locator, String value) {
        driver.findElement(By.cssSelector(locator)).sendKeys(value, Keys.ENTER);
    }
    public void typeByCssNEnter(String locator, String value) {
        driver.findElement(By.cssSelector(locator)).sendKeys(value, Keys.ENTER);
    }

    public void typeByXpath(String locator, String value) {
        driver.findElement(By.xpath(locator)).sendKeys(value);
    }

    public void takeEnterKeys(String locator) {
        driver.findElement(By.cssSelector(locator)).sendKeys(Keys.ENTER);
    }

    public void clearInputField(String locator){
        driver.findElement(By.cssSelector(locator)).clear();
    }
    public List<WebElement> getListOfWebElementsById(String locator) {
        List<WebElement> list = new ArrayList<WebElement>();
        list = driver.findElements(By.id(locator));
        return list;
    }
    public List<String> getTextFromWebElements(String locator){
        List<WebElement> element = new ArrayList<WebElement>();
        List<String> text = new ArrayList<String>();
        element = driver.findElements(By.cssSelector(locator));
        for(WebElement web:element){
            text.add(web.getText());
        }

        return text;
    }
    public List<WebElement> getListOfWebElementsByCss(String locator) {
        List<WebElement> list = new ArrayList<WebElement>();
        list = driver.findElements(By.cssSelector(locator));
        return list;
    }
    public String  getCurrentPageUrl(){
        String url = driver.getCurrentUrl();
        return url;
    }
    public void navigateBack(){
        driver.navigate().back();
    }
    public void navigateForward(){
        driver.navigate().forward();
    }
    public String getTextByCss(String locator){
        String st = driver.findElement(By.cssSelector(locator)).getText();
        return st;
    }
    public String getTextByXpath(String locator){
        String st = driver.findElement(By.xpath(locator)).getText();
        return st;
    }
    public String getTextById(String locator){
        return driver.findElement(By.id(locator)).getText();
    }
    public String getTextByName(String locator){
        String st = driver.findElement(By.name(locator)).getText();
        return st;
    }

    public List<String> getListOfString(List<WebElement> list) {
        List<String> items = new ArrayList<String>();
        for (WebElement element : list) {
            items.add(element.getText());
        }
        return items;
    }
    public void selectOptionByVisibleText(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }
    public void sleepFor(int sec)throws InterruptedException{
        Thread.sleep(sec * 1000);
    }
    public void mouseHoverByCSS(String locator){
        try {
            WebElement element = driver.findElement(By.cssSelector(locator));
            Actions action = new Actions(driver);
            Actions hover = action.moveToElement(element);
        }catch(Exception ex){
            System.out.println("First attempt has been done, This is second try");
            WebElement element = driver.findElement(By.cssSelector(locator));
            Actions action = new Actions(driver);
            action.moveToElement(element).perform();

        }

    }
    public void mouseHoverByXpath(String locator){
        try {
            WebElement element = driver.findElement(By.xpath(locator));
            Actions action = new Actions(driver);
            Actions hover = action.moveToElement(element);
        }catch(Exception ex){
            System.out.println("First attempt has been done, This is second try");
            WebElement element = driver.findElement(By.cssSelector(locator));
            Actions action = new Actions(driver);
            action.moveToElement(element).perform();

        }

    }
    //handling Alert
    public void okAlert(){
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }
    public void cancelAlert(){
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }

    //iFrame Handle
    public void iframeHandle(WebElement element){
        driver.switchTo().frame(element);
    }

    public void goBackToHomeWindow(){
        driver.switchTo().defaultContent();
    }

    //get Links
    public void getLinks(String locator){
        driver.findElement(By.linkText(locator)).findElement(By.tagName("a")).getText();
    }


    //Synchronization
    public void waitUntilClickAble(By locator){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    public void waitUntilVisible(By locator){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public void waitUntilSelectable(By locator){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        boolean element = wait.until(ExpectedConditions.elementToBeSelected(locator));
    }

    public void clearInput(String locator){
        driver.findElement(By.cssSelector(locator)).clear();
    }
    public void keysInput(String locator){
        driver.findElement(By.cssSelector(locator)).sendKeys(Keys.ENTER);
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



}


