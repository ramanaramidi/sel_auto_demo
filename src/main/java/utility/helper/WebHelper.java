package utility.helper;

import common.ExtentTestManager;
import io.netty.util.concurrent.Promise;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.awt.SystemColor.window;

public class WebHelper {
    public WebDriver driver;
    public String className = WebHelper.class.getCanonicalName();

    public static final MethodStore MethodStore = new MethodStore();
    Logger logReport = LoggerFactory.getLogger(WebHelper.class);
    public WebHelper(WebDriver driver) {
        this.driver = driver;
    }


    public WebElement find(By locatorQuery, WebElement rootElement, int maximumTries) throws Exception {
        WebElement element;
        int maxTries;
        if (maximumTries == 0)
            maxTries = VARS.getMaxRetries();
        else
            maxTries = maximumTries;
        for (int i = 0;i<maxTries;i++) {
            try {
                if(rootElement == null){
                    element = driver.findElement(locatorQuery);
                }
                else{
                    element = rootElement.findElement(locatorQuery);
                }

                if (element != null) {
                    ExtentTestManager.printReport("Found element with locationQuery: "+locatorQuery+" within {"+ MethodStore.maxTimeOut() * i+"} seconds","info",WebHelper.class);
                    return element;
                }
            } catch (Exception e) {
                logReport.warn("LOGGER::Couldn't find an element with locationQuery: "+locatorQuery+" . Trying again...");
                // Logger.warn(className,new Exception("Couldn't find an element with locationQuery: "+locatorQuery+" . Trying again..."));
                //Logger(WebHelper::class.java.canonicalName).warn("Couldn't find an element with locationQuery: $locatorQuery. Trying again...")
            }
        }
        String errorMessage = "Couldn't find an element with locationQuery: "+locatorQuery+" within {"+ MethodStore.maxTimeOut() +"} seconds";
        ExtentTestManager.printReport(errorMessage,"failed",WebHelper.class);
        logReport.error("LOGGER::Couldn't find an element with locationQuery: "+locatorQuery+" . Stopping retries..."+errorMessage);
        throw new Exception(errorMessage);
    }

    public void fullScreen() {
        driver.manage().window().fullscreen();
    }


    public List<WebElement> findAll(By locatorQuery, WebElement rootElement, int maximumTries) throws Exception {
        List<WebElement> elements;
        int maxTries;
        if (maximumTries == 0)
            maxTries = VARS.getMaxRetries();
        else
            maxTries = maximumTries;

        for (int i = 0;i<maxTries;i++) {
            try {
                if(rootElement == null){
                    elements = driver.findElements(locatorQuery);
                }
                else{
                    elements = rootElement.findElements(locatorQuery);
                }

                if (elements != null) {
                    ExtentTestManager.printReport("Found element with locationQuery: "+locatorQuery+" within {"+MethodStore.maxTimeOut() * i+"} seconds","info",WebHelper.class);
                    return elements;
                }
            } catch (Exception e) {
                MethodStore.sleep(MethodStore.retryWait());
                logReport.warn("LOGGER::Couldn't find an element with locationQuery: "+locatorQuery+" . Trying again...");
            }
        }
        String errorMessage = "Couldn't find an element with locationQuery: "+locatorQuery+" within {"+MethodStore.maxTimeOut()+"} seconds";
        ExtentTestManager.printReport(errorMessage,"failed",WebHelper.class);
        logReport.error(errorMessage);
        throw new Exception(errorMessage);
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }


    public final void click(WebElement element) throws Exception {
        int maxTries = VARS.getMaxRetries();
        for (int i = 0;i<maxTries;i++) {
            try {
                Actions actions = new Actions(driver);
                actions.moveToElement(element);
                actions.perform();
                element.click();
                ExtentTestManager.printReport("Click on button/ element - "+element+" ","info",WebHelper.class);
                return;
            } catch (StaleElementReferenceException e) {
                // Referencing The below StackOverflow article, catching the stale reference and clicking again will refresh the dom
                // https://stackoverflow.com/questions/18225997/stale-element-reference-element-is-not-attached-to-the-page-document
                // TODO: Investigate this further
                logReport.warn("Stale element reference, couldn't click on {"+element.getTagName()+"}"+"{"+element.getText()+"}, refresh the DOM");
                // Logger(WebHelper::class.java.canonicalName).warn("Stale element reference, couldn't click on ${element.tagName}.${element.text}, refresh the DOM")
                Actions actions = new Actions(driver);
                actions.moveToElement(element);
                actions.perform();
                element.click();
            } catch (Exception e) {
                MethodStore.sleep(MethodStore.retryWait());
                logReport.warn("Couldn't click on $element. Trying again...");
            }
        }
        String errorMessage = "Couldn't click on button/ element "+element +" in" +maxTries+" attempts";
        ExtentTestManager.printReport(errorMessage,"failed",WebHelper.class);
        logReport.error(errorMessage);
        // Logger(WebHelper::class.java.canonicalName).error(errorMessage)
        throw new Exception(errorMessage);
    }
    public void sleep(long timeoutInSeconds) {
        long timeOut = 0;
        if (timeoutInSeconds==0)
            timeOut = WebHelper.MethodStore.maxTimeOut();
        else
            timeOut = timeoutInSeconds;
        WebHelper.MethodStore.sleep(timeOut);
    }


    public void clickCancelAndAlert(WebElement element, String alertAction) {
        try {
            click(element);
            sleep(10);

        } catch (Exception f) {
            try {

                Alert alert = driver.switchTo().alert();
                String alertText = alert.getText();
                System.out.println("Alert data: $alertText");
                if (alertAction.equals("accept"))
                    alert.accept();
                else
                    alert.dismiss();
            } catch (NoAlertPresentException e) {
                e.printStackTrace();
            }
        }
    }

    public void clickTableLinkByText(String text) throws Exception {
        String baseLocator = "//a[@href=\".\"][normalize-space()='TOBEREPLACED']";
        baseLocator = baseLocator.replace("TOBEREPLACED",text);
        click(find(By.xpath(baseLocator),null,0));
    }

    public void setText(WebElement element, String text)  {
        int maxTries = 1 ;// VARS.MAX_RETRIES
        try{
            clearInputBoxByElementAndActions(element);
            element.sendKeys(text);
        }
        catch (Exception e){
            String errorMessage = "Couldn't send the right text " + text +" to "+ element +" in "+maxTries+" attempts";
            System.out.println(errorMessage);
        }

//        for (int i = 0;i<maxTries;i++) {
//            try {
//                element.sendKeys(text);
////               (driver as JavascriptExecutor).executeScript("arguments[0].innerText=arguments[1];", element, text)
//                String elementText = getElementTextSomehow(element);
//                if (elementText == text) {
//                    //report.printReport("Set Text '$text' to text box/ element - $element")
//                    return;
//                } else {
////                    Logger(WebHelper::class.java.canonicalName).trace(("Equality Log: \n Element text - $elementText \n Set Text - $text "))
//                    // This wasn't effectively clearing anything causing an automatic failure any time this statement was hit.
//                    // element.clear()
//                    clearInputBoxByElementAndActions(element);
//                }
//            } catch (Exception e) {
//                MethodStore.sleep(MethodStore.retryWait());
//                //Logger(WebHelper::class.java.canonicalName).warn("Couldn't send the right text to $element. Trying again...")
//            }
//        }

        // report.printReport(errorMessage)
        //Logger(WebHelper::class.java.canonicalName).error(errorMessage)
        //throw new Exception(errorMessage);
    }

    public void clearInputBoxByElementAndActions(WebElement inputBoxElement ) {
        Actions builder = new Actions(this.driver);
        inputBoxElement.click();
        builder.keyDown(inputBoxElement, Keys.CONTROL)
                .sendKeys(inputBoxElement, "A")
                .keyUp(inputBoxElement, Keys.CONTROL)
                .sendKeys(inputBoxElement, Keys.DELETE).perform();
    }

    public String getElementTextSomehow(WebElement element) {
        String text = getElementText(element);
        String value = getElementValueAttr(element);
        if (text == "") {
            return value;
        } else {
            return text;
        }
    }

    private String getElementText(WebElement element) {
        try {
            return element.getText();
        } catch (Exception e) {
            return "";
        }
    }

    private String getElementValueAttr(WebElement element) {
        try {
            return element.getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    public void dropDownButtons(String sectionDiv, String text) throws Exception {
        WebElement selectionDivElement = find(By.xpath(sectionDiv.replace("textName", text)),null,0);
        click(selectionDivElement);
    }

    public void dotsClick(String labelName) throws Exception {
        WebElement element;
        MethodStore.sleep(MethodStore.retryWait());
        element = find(By.xpath("(//td//label[contains(text(),'" + labelName + "')]//following::td//following::div//input)[1]"),null,0);
        MethodStore.sleep(MethodStore.retryWait());
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
        element.click();
    }

    public void tableRadioButtonClickWithExactValue(String labelName, String text) throws Exception {
        WebElement element;
        MethodStore.sleep(MethodStore.retryWait());
        element = find(By.xpath("//table//*[text()='" + labelName + "']/..//following::td[text()='" + text + "']//parent::tr//input[@type='radio']"),null,0);
        MethodStore.sleep(MethodStore.retryWait());
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
        element.click();
    }
    public void tableRadioButtonSelectionWithExactValue(String labelName, String text) throws Exception {
        WebElement element;
        MethodStore.sleep(MethodStore.retryWait());
        element = find(By.xpath("//table//*[text()='" + labelName + "']/..//following::a[contains(text(),'" + text + "')]//ancestor::tr//input[@type='radio']"),null,0);
        MethodStore.sleep(MethodStore.retryWait());
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
        element.click();
    }

    public void radioButtonSelection(String labelName, String text) throws Exception {
        WebElement element;
        MethodStore.sleep(MethodStore.retryWait());
        element = find(By.xpath("(//table//*[text()='" + labelName + "']/..//following::a[contains(text(),'" + text + "')]//parent::div)//ancestor::tr//input[@type='radio']"),null,0);
        MethodStore.sleep(MethodStore.retryWait());
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
        element.click();
    }

    public void checkBoxSelection(String labelName, String text) throws Exception {
        WebElement element;
        MethodStore.sleep(MethodStore.retryWait());
        element = find(By.xpath("(//table//*[text()='" + labelName + "']/..//following::a[contains(text(),'" + text + "')]//parent::td)//ancestor::tr//label"),null,0);
        MethodStore.sleep(MethodStore.retryWait());
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
        element.click();
    }

    public void radioButtonSelectionByTd(String labelName) throws Exception {
        WebElement element;
        MethodStore.sleep(MethodStore.retryWait());
        element = find(By.xpath("(//table//*[text()='" + labelName + "']/..//following::a//parent::td)//ancestor::tr//input[@type='radio']"),null,0);
        MethodStore.sleep(MethodStore.retryWait());
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
        element.click();
    }

    public void inputTextBox(String labelName, String text) throws Exception {
        WebElement element;
        MethodStore.sleep(MethodStore.retryWait());
        element = find(By.xpath("(//label[contains(text(),'" + labelName + "')]/following::input)[1]"),null,0);
        MethodStore.sleep(MethodStore.retryWait());
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
        element.click();
        setText(element, text);
    }

    public void dropdownValueSelection(String labelName, String text) throws Exception {
        WebElement element;
        WebElement dropdownValue;
        MethodStore.sleep(MethodStore.retryWait());
        element = find(By.xpath("((//label[normalize-space(text())='" + labelName + "'])[1])/following::select"),null,0);
        MethodStore.sleep(MethodStore.retryWait());
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
        element.click();
        dropdownValue = find(By.xpath("((//label[normalize-space(text())='" + labelName + "'])[1])/following::select//option[text()='" + text + "']"),null,0);
        dropdownValue.click();
    }

    public void buttonClick(String labelName, int index) throws Exception {
        WebElement element;
        MethodStore.sleep(MethodStore.retryWait());
        element = find(By.xpath("(//input[@value='" + labelName + "'])[" + index + "]"),null,0);
        MethodStore.sleep(MethodStore.retryWait());
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
        element.click();
    }

    public Boolean inputBoxDataByTitle(String value) {
        String locatorFormat = "//input[contains(@title,'THISISTOCHANGE')]";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        return isDisplayed(By.xpath(locator));
    }

    public WebElement checkBoxByLabel(String value) throws Exception {
        String locatorFormat = "//label[text()='THISISTOCHANGE']//parent::td//following-sibling::td//child::label";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        return find(By.xpath(locator),null,0);
    }
    public WebElement checkBoxByLabelInput(String value) throws Exception {
        String locatorFormat =
                "//label[text()='THISISTOCHANGE']//parent::td//following-sibling::td//child::label//input";
        String locator = locatorFormat.replace("THISISTOCHANGE", value);
        return find(By.xpath(locator), null, 0);
    }

    public WebElement checkBoxByBoldedLabel(String value) throws Exception {
        String locatorFormat = "//b[normalize-space()='THISISTOCHANGE']//parent::label//parent::td//following-sibling::td//child::label";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        return find(By.xpath(locator),null,0);
    }
    public WebElement inputBoxDataLabel(String labelName) throws Exception {
        String locatorFormat = "(//label[contains(text(),'" + labelName + "')]//following::input)[1]";
        String locator = locatorFormat.replace("THISISTOCHANGE",labelName);
        return find(By.xpath(locator),null,0);
    }
    public String tableDataList(int index) throws Exception {
        String locatorFormat = "//div[contains(@class,'customscroll')]//child::td["+ index +"]";
        return locatorFormat;
    }

    public void inputBoxDataBySname(String value,String text) throws Exception {
        String locatorFormat = "//input[@sname='THISISTOCHANGE']";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        setText(find(By.xpath(locator),null,0),text);
    }

    public void clipImageButtonClick(String value) throws Exception {
        String locatorFormat = "//input[@sname='THISISTOCHANGE']";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        click(find(By.xpath(locator),null,0));
    }

    public WebElement inputBoxDataBySname(WebElement root, String value) throws Exception {
        String locatorFormat = "//input[@sname='THISISTOCHANGE']";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        return find(By.xpath(locator),root,0);
    }

    public WebElement inputBoxDataBySname(String value) throws Exception {
        String locatorFormat = "//input[@sname='THISISTOCHANGE']";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        return find(By.xpath(locator),null,0);
    }

    public String inputBoxXpathBySname(String value) throws Exception {
        String locatorFormat = "//input[@sname='THISISTOCHANGE']";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        return locator;
    }
    public List<WebElement> inputBoxDataBySnameByIndex(String value) throws Exception {
        String locatorFormat = "//input[@sname='THISISTOCHANGE']";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        return findAll(By.xpath(locator),null,0);
    }

    public WebElement textAreaByTitle(String value) throws Exception {
        String locatorFormat = "//textarea[@title='THISISTOCHANGE']";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        return find(By.xpath(locator),null,0);
    }

    public WebElement textAreaBySname(String value) throws Exception {
        String locatorFormat = "//textarea[@sname='THISISTOCHANGE']";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        return find(By.xpath(locator),null,0);
    }
    public List<WebElement> textAreaBySnameAll(String value) throws Exception {
        String locatorFormat = "//textarea[@sname='THISISTOCHANGE']";
        String locator = locatorFormat.replace("THISISTOCHANGE", value);
        return findAll(By.xpath(locator), null, 0);
    }

    public WebElement clipButtonBySname(String value) throws Exception {
        String locatorFormat = "//input[@sname='THISISTOCHANGE']//parent::div//following-sibling::div[@class = 'newFieldBtn']";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        return find(By.xpath(locator),null,0);
    }

    public WebElement selectionBoxBySname(WebElement root,String value) throws Exception {
        String locatorFormat = "//select[@sname='THISISTOCHANGE']";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        return find(By.xpath(locator),root,0);
    }

    public List<WebElement> selectionBoxBySname(String value) throws Exception {
        String locatorFormat = "//select[@sname='THISISTOCHANGE']";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        return findAll(By.xpath(locator),null,0);
    }

    public WebElement getDivElementByText(String option) throws Exception {
        String locatorFormat = "//select[@sname='THISISTOCHANGE']";
        String locator = locatorFormat.replace("THISISTOCHANGE",option);
        return find(By.xpath(locator),null,0);
    }

    public WebElement lockByLabelText(String value,WebElement root) throws Exception {
        String locatorFormat = "//label[text()='THISISTOCHANGE']//following-sibling::div";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        return find(By.xpath(locator),root,0);
    }
    public WebElement lockByLabel(String value) throws Exception {
        String locatorFormat = "//label[text()='THISISTOCHANGE']/parent::td/div";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        return find(By.xpath(locator),null,0);
    }

    public List<WebElement> lockByLabelText(String value) throws Exception {
        String locatorFormat = "//label[text()='THISISTOCHANGE']//following-sibling::div";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        return findAll(By.xpath(locator),null,0);
    }
    public WebElement fieldByLabelText(String value) throws Exception {
        String locatorFormat = "//label[text()='THISISTOCHANGE']";
        String locator = locatorFormat.replace("THISISTOCHANGE", value);
        return find(By.xpath(locator), null, 0);
    }
    public List<WebElement> fieldByLabelTextIndex(String value) throws Exception {
        String locatorFormat = "//label[text()='THISISTOCHANGE']";
        String locator = locatorFormat.replace("THISISTOCHANGE", value);
        return findAll(By.xpath(locator), null, 0);
    }
    public WebElement sectionsByLabelText(String value) throws Exception {
        String locatorFormat = "//p[contains(text(),'THISISTOCHANGE ')]";
        String locator = locatorFormat.replace("THISISTOCHANGE", value);
        return find(By.xpath(locator), null, 0);
    }
    public WebElement selectionBoxBySname(WebElement root,String value,String selectionText) throws Exception {
        String locatorFormat = "//select[@sname='THISISTOCHANGE']//option[text()='SELECTIONTEXT']";
        String locator = locatorFormat.replace("THISISTOCHANGE",value);
        locator = locator.replace("SELECTIONTEXT",selectionText);
        System.out.println(locator);
        return find(By.xpath(locator),root,0);
    }

    public Boolean isDisplayed(By locatorQuery) {
        Boolean isDisplayed = false;
        try {
            setCustomImplicitTimeout(MethodStore.maxTimeOut());
            isDisplayed = driver.findElement(locatorQuery).isDisplayed();
        } catch (Exception e) {
            throw e;
        } finally {
            resetImplicitTimeoutToDefault();
        }
        return isDisplayed;
    }

    public void setCustomImplicitTimeout(Long timeOutInSeconds) {
        driver.manage().timeouts().implicitlyWait(timeOutInSeconds, TimeUnit.SECONDS);
    }

    public void resetImplicitTimeoutToDefault() {
        driver.manage().timeouts().implicitlyWait(MethodStore.maxTimeOut(), TimeUnit.SECONDS);
    }

    public String getText(WebElement element) throws Exception {
        int maxTries = VARS.getMaxRetries();
        String text = null;
        for (int i = 0;i<maxTries;i++) {
            try {
                text = element.getText();
                ExtentTestManager.printReport("Get text '"+text+"' from text box/ element - "+element+" ","info",WebHelper.class);
                return text ;
            } catch (Exception e) {
                MethodStore.sleep(MethodStore.retryWait());
                logReport.warn("Couldn't read text from $element. Trying again...");
            }
        }
        if (text == null) {
            String errorMessage = "Couldn't read text from "+ element+" in"+ maxTries+" attempts";
            logReport.error(errorMessage);
            ExtentTestManager.printReport(errorMessage,"failed",WebHelper.class);
            throw new Exception(errorMessage);
        }
        return text;
    }

    public void waitUntilVisibleElement(WebElement webElement, Long timeout) {
        for(int i = 0;i<VARS.getMaxRetries();i++){
            try {
                new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(webElement));
                ExtentTestManager.printReport("Element with locationQuery: locatorQuery was visible within ${maxTimeOut()}","info",WebHelper.class);
                return;
            } catch (StaleElementReferenceException e) {
                logReport.warn("Element: {"+webElement.getText()+"} wasn't visible within "+timeout);
                new WebDriverWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(webElement));
            } catch (Exception e) {
                String errorMessage = "Not even one element with locationQuery: "+webElement.getAccessibleName()+" was visible within "+"{"+MethodStore.maxTimeOut()+"}";
                ExtentTestManager.printReport(errorMessage,"info",WebHelper.class);
                logReport.warn("Element: {"+webElement.getText()+"} wasn't visible within "+timeout);
            }
            String errorMessage = "Not even one element with locationQuery: $locatorQuery was visible within ${maxTimeOut()}";
            ExtentTestManager.printReport(errorMessage,"info",WebHelper.class);
        }

    }

    public String getValue(WebElement element) throws Exception {
        int maxTries = VARS.getMaxRetries();
        String text = null;
        for (int i = 0;i<maxTries;i++) {
            try {
                text = element.getAttribute("value");
                ExtentTestManager.printReport("Get Value '"+text+"' from element - "+element+" ","info",WebHelper.class);
                return text ;
            } catch (Exception e) {
                MethodStore.sleep(MethodStore.retryWait());
                logReport.warn("Couldn't read text from "+element+". Trying again...");
                //  Logger(WebHelper::class.java.canonicalName).warn("Couldn't read text from $element. Trying again...")
            }
        }
        if (text == null) {
            String errorMessage = "Couldn't read text from $element in $maxTries attempts";
            ExtentTestManager.printReport(errorMessage,"failed",WebHelper.class);
            logReport.error(errorMessage);
            // Logger(WebHelper::class.java.canonicalName).error(errorMessage)
            throw new Exception(errorMessage);
        }
        return text;
    }

    public void selectDropdownOption(WebElement element, String visibleText) {
        Select selectDropdown = new Select(element);
        selectDropdown.selectByVisibleText(visibleText);
    }
    public List<WebElement> selectGetDropdownOptions(WebElement element) {
        Select selectDropdown = new Select(element);
        return selectDropdown.getOptions();
    }

    public String getFirstSelectedOptionInDropdown(WebElement element) {
        Select selectDropdown = new Select(element);
        return selectDropdown.getFirstSelectedOption().getText();
    }

    public void selectDropdownOptionByIndex(WebElement element, int index) {
        Select selectDropdown = new Select(element);
        selectDropdown.selectByIndex(index);
    }

    public void hoverOver(WebElement element) {
        (new Actions(this.driver)).moveToElement(element).perform();
    }

    public void scrollToElement(WebElement element) {
        WebDriver driver1 = this.driver;
        if (driver1 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.openqa.selenium.JavascriptExecutor");
        } else {
            ((JavascriptExecutor)driver1).executeScript("arguments[0].scrollIntoView();", new Object[]{element});
        }
    }

    public String getDocumentTextByIdJs(String jsId){
        WebDriver driver1 = this.driver;
        if (driver1 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.openqa.selenium.JavascriptExecutor");
        } else {
            JavascriptExecutor js = (JavascriptExecutor) driver1;
            String method = "return document.getElementById('" + jsId + "').value;";
            return js.executeScript(method).toString();
        }
    }

    public String getDocumentTextByXpathJs(String xpath){
        WebDriver driver1 = this.driver;
        if (driver1 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.openqa.selenium.JavascriptExecutor");
        } else {
            JavascriptExecutor js = (JavascriptExecutor) driver1;
            String method = "return document.evaluate("+'"' + xpath + '"'+",document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.id;";
            String id =  js.executeScript(method).toString();
            return getDocumentTextByIdJs(id);
        }
    }
    public List<String> getDocumentTextListByXpathJs(String xpath){
        WebDriver driver1 = this.driver;
        if (driver1 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.openqa.selenium.JavascriptExecutor");
        } else {
            JavascriptExecutor js = (JavascriptExecutor) driver1;
            String method = "var iterator = document.evaluate("+'"'+xpath+'"'+", document, null, XPathResult.UNORDERED_NODE_ITERATOR_TYPE, null);\n" +
                    "\n" +
                    "  var thisNode = iterator.iterateNext();\n" +
                    "var array = new Array();\n" +
                    "  while (thisNode) {\n" +
                    "      array.push(thisNode.textContent);\n" +
                    "    thisNode = iterator.iterateNext();\n" +
                    "  } \n" +
                    "    return array";
            List<String> optionsList =  (List<String>)js.executeScript(method);
            return optionsList;
        }
    }


    public void scrollRightHorizontalWithCoordinates(WebElement scrollArea) {
        WebDriver driver1 = this.driver;
        if (driver1 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.openqa.selenium.JavascriptExecutor");
        } else {
            ((JavascriptExecutor)driver1).executeScript("arguments[0].scrollLeft += 250",scrollArea);
        }
    }

    public void switchToSpecificWindow(String windowHandle) {
        driver.switchTo().window(windowHandle);
        driver.manage().window().fullscreen();
    }

    public String getCurrentWindow(){
        return this.driver.getWindowHandle();
    }

    public String switchToChildWindows() {
        String MainWindow = this.driver.getWindowHandle();
        Set windows = this.driver.getWindowHandles();
        Iterator allOpenWindows = windows.iterator();

        while(allOpenWindows.hasNext()) {
            String ChildWindow = (String)allOpenWindows.next();
            if (!MainWindow.equalsIgnoreCase(ChildWindow)) {
                this.driver.switchTo().window(ChildWindow);
            }
        }

        return MainWindow;
    }

    public String getTitle() {
        String pageTitle = driver.getTitle();
        return pageTitle;
    }

    public String getPageUrl() {
        return driver.getCurrentUrl();
    }

    public void clearInputBoxByElementAndSendKeys(WebElement inputBoxElement) {
        int length = inputBoxElement.getAttribute("value").length();

        for(int i = 0; i < Math.round(length * 1.5); ++i) {
            inputBoxElement.click();
            inputBoxElement.sendKeys(Keys.chord(Keys.CONTROL,"A"));
            inputBoxElement.sendKeys((Keys.BACK_SPACE));
        }
    }

    public List<WebElement> getTableRows(WebElement tableElement) throws Exception {
        return findAll(By.tagName("tr"), tableElement,0);
    }

    public int getElementCount(String element) throws Exception {
        return findAll(By.xpath(element),null,0).size();
    }

    public int getColumnCountByName(String elementString, String columnName) throws Exception {
        String header = elementString.replace("THISISTHETEXT", columnName);
        List<WebElement> elements = findAll(By.xpath(header),null,0);
        for (int i=0; i<elements.size();i++) {
            System.out.println("___" + getText(elements.get(i)));
            System.out.println("___" + columnName);
            if (getText(elements.get(i)) == columnName)
                return i - 1;
        }
        return -1;
    }

    public List<WebElement> getTableCellsByRow(WebElement row) throws Exception {
        return findAll(By.tagName("td"), row,0);
    }


    public boolean isAlertPresent() {
//        WebDriverWait wait = new WebDriverWait(driver,10);
//        return wait.until(ExpectedConditions.alertIsPresent()) != null;

        try{
            driver.switchTo().alert();
            return true;
        }
        catch (NoAlertPresentException ex){
            return false;
        }
    }

    public List<WebElement> notificationPanelData(String date, String status) throws Exception {
        String  baseLocator = "//span[contains(normalize-space(.), \"REPLACEWITHDATE\")]//following-sibling::span[contains(normalize-space(.), \"REPLACEWITHSTATUS\")]";
        baseLocator = baseLocator.replace("REPLACEWITHDATE",date);
        baseLocator = baseLocator.replace("REPLACEWITHSTATUS",status);
        return findAll(By.xpath(baseLocator),null,0);
    }

    public List<WebElement> notificationPanelReportLink(String reportName) throws Exception {
        String  baseLocator = "//span[contains(normalize-space(.), \"Report\")]//following-sibling::a[contains(normalize-space(.), \"REPLACEWITHREPORTNAME\")]";
        baseLocator = baseLocator.replace("REPLACEWITHREPORTNAME",reportName);
        return findAll(By.xpath(baseLocator),null,0);
    }
    public void jsClickById(String id) {
        WebDriver driver1 = this.driver;
        if (driver1 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.openqa.selenium.JavascriptExecutor");
        } else {
            JavascriptExecutor js = (JavascriptExecutor) driver1;
            String method = "document.getElementById('" + id + "').click();";
            js.executeScript(method);
        }
    }

    public List<WebElement> headerItemVerification(String type, String name) throws Exception {
        String baseLocator = "//div[@class='ge_info']//span[normalize-space()='REPLACEWITHTYPE']//following-sibling::a[normalize-space()='REPLACEWITHNAME']";
        baseLocator = baseLocator.replace("REPLACEWITHTYPE",type);
        baseLocator = baseLocator.replace("REPLACEWITHNAME",name);
        return findAll(By.xpath(baseLocator),null,0);
    }

    public List<WebElement> headerItemRootNotification(String type, String name) throws Exception {
        String baseLocator = "//div[@class='ge_info']//span[normalize-space()='REPLACEWITHTYPE']//following-sibling::a[normalize-space()='REPLACEWITHNAME']//ancestor::div[@class='component abstract_group group_event status_StatusSuccess']";
        baseLocator = baseLocator.replace("REPLACEWITHTYPE",type);
        baseLocator = baseLocator.replace("REPLACEWITHNAME",name);
        return findAll(By.xpath(baseLocator),null,0);
    }

    public List<WebElement> tabCounterByTabName(String name) throws Exception {
        String baseLocator = "//div[@title='REPLACEWITHNAME']//span[@class='tab_counter']";
        baseLocator = baseLocator.replace("REPLACEWITHNAME",name);
        return findAll(By.xpath(baseLocator),null,0);
    }

    public List<WebElement> dataItemVerification(String date, String status, WebElement root) throws Exception {
        String locator = "//span[contains(normalize-space(.), \"REPLACEWITHDATE\")]//following-sibling::span[contains(normalize-space(.), \"REPLACEWITHSTATUS\")]";
        locator = locator.replace("REPLACEWITHDATE",date);
        locator = locator.replace("REPLACEWITHSTATUS",status);
        return findAll(By.xpath(locator),root,0);
    }

    public List<WebElement> pencilIcon(String sname) throws Exception {
        String commonLocator = "//input[@sname='REPLACEWITHSNAME']//parent::*//parent::*//child::input[@title='Click for detailed info']";
        commonLocator = commonLocator.replace("REPLACEWITHSNAME",sname);
        return findAll(By.xpath(commonLocator),null,0);
    }


    public static final class MethodStore{
        public Long retryWait() {
            return (long)VARS.getDefaultRetryWaitInSeconds();
        }

        public Long maxTimeOut() {
            return (long)(VARS.getDefaultTimeoutInSeconds());
        }


        public void sleep(Long timeoutInSeconds) {
            try {
                // Logger(WebHelper::class.java.canonicalName).info("Sleeping for $timeoutInSeconds second(s)")
                Thread.sleep(timeoutInSeconds * 1000);
            } catch (InterruptedException e) {
                // Logger(WebHelper::class.java.canonicalName).error("Sleeping beauty did not sleep well. ", e)
                e.printStackTrace();
            }
        }
        public void navigateBackwards(WebDriver driver) {
            driver.navigate().back();
        }
        public void navigateForwards(WebDriver driver) {
            driver.navigate().forward();
        }
        public void refresh(WebDriver driver) {
            driver.navigate().refresh();
            sleep(maxTimeOut());
        }
    }
    public void checkBoxCheckByJS(WebElement value) {
        WebDriver driver1 = this.driver;
        if (driver1 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.openqa.selenium.JavascriptExecutor");
        } else {
            JavascriptExecutor js = (JavascriptExecutor) driver1;
            js.executeScript("arguments[0].click();", value);
        }
    }
    public boolean isCheckBoxSelected(String jsId) {
        WebDriver driver1 = this.driver;
        if (driver1 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.openqa.selenium.JavascriptExecutor");
        } else {
            JavascriptExecutor js = (JavascriptExecutor) driver1;
            String method ="return document.getElementById('" + jsId + "').checked;";
            Object value = js.executeScript(method);
            System.out.println((boolean)value);
            return (boolean) value;
        }
    }

    public void waitForPageToLoad(){
        WebDriver driver1 = this.driver;
        JavascriptExecutor js = (JavascriptExecutor)driver1;
        int i=0;

        while(i!=10){
            String state = (String)js.executeScript("return document.readyState;");
            System.out.println(state);

            if(state.equals("complete"))
                break;
            else
                wait(2);

            i++;
        }
        wait(2);// wait of 2 sec between page status and jquery
        // check for jquery status
        i=0;
        while(i!=10){

            Boolean result= (Boolean) js.executeScript("return window.jQuery != undefined && jQuery.active == 0;");
            System.out.println(result);
            if(result )
                break;
            else
                wait(2);
            i++;

        }

    }
    public void wait(int time){
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
   /* Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
            .withTimeout(Duration.ofSeconds(30))
            .pollingEvery(Duration.ofSeconds(5))
            .ignoring(NoSuchElementException.class);*/


/*
    public void wait_for_PageLoad(){
        JavascriptExecutor js = (JavascriptExecutor)driver;

        window.onload = setInterval(function(){
            alert("Loaded!")
            document.write("This page is loaded now!")
        }, 3000);
        document.addEventListener(event, function)
    }
*/
  /*  function delay(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }*/
}
