package pages.web.Tracker;

import commons.objects.Por;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pages.BasePage;

import java.util.List;

public  class AddPORPage extends BasePage {
    public WebDriver driver;
    public AddPORPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    String parentWindowHolder = "";


    public By okButton =By.xpath("//input[@id='btnOK']");
    public By cancelButton = By.xpath("//input[@id='btnCancel']");
    public By objectivePor = By.id("idx11");
    public By planStatusPor = By.id("idx9");
    public By requestQueuePor = By.id("idx61");
    public By ringCodeSearchButton = By.xpath("//input[@id='btnSearch0']");
    public By ringCodeSearch  = By.xpath(" //input[@id='qsValue0']");
    public By ringCodeSelectionOKButton  = By.xpath("//input[@id='btnOK0']");
    public By applyButton  = By.xpath("//input[@id='btnApply']");
    public By editPOR  = By.xpath("//input[@id='btnEdit0']");
    public By ProjectIDValueIcon =By.xpath("//input[@sname='PJ:Project ID']//parent::*//parent::*//child::input[@title='Click for detailed info']");
    public By ProjectIDValuetext =By.xpath(" //input[@id='idx3']");
    public String ProjectIDValueField = "idx3_disp";
    public By ringCodeSelectionEditOption = By.xpath("//input[@id='btnEdit0']");
    public By porRequestQueue = By.xpath("//label[text()='POR:POR Request Queue']");
    public String parentWindow = "";
    public By OKButton1 = By.xpath("//input[@id='btnOK0']");

    public By ProjectSearchTextbox = By.xpath("//input[@id='qsValue0']");

    public By ProjectSearchTextboxSearchButton = By.xpath("//input[@id='btnSearch0']");

    public By ProjectSearchRadioButton = By.xpath("//input[@name='rb0']");

    public AddPORPage addingNewPORTracker(String ringCode) throws Exception {
        addNewPORTracker( new Por(ringCode));
        return new AddPORPage(driver);
    }

    public PORTrackerPage verifyMandatoryMessageAlert(String ringCode) throws Exception {
        verifyMandatoryMessages(ringCode);
        return new PORTrackerPage(driver);
    }

//    public AddPORPage verifyPORID() throws Exception {
//        isPORIDCount();
//        return new AddPORPage(driver);
//    }

    public AddPORPage verifyAddPORFunctionality(String ringCode) throws Exception {
        addNewPORTracker(new Por(ringCode));
        return new AddPORPage(driver);
    }

    public AddPORPage changePORReuquest() throws Exception {
        changePORRequestQueue();
        return new AddPORPage(driver);
    }

    public void verifyMandatoryMessages(String ringCode) throws Exception
    {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(5);
        dropDownDotsClick("R:Ring Code");
        String parent2 = switchToChildWindows();
        waitUntilVisibleElement(find(ringCodeSelectionEditOption));
        fullScreenChildWindow();
        setText(find(ringCodeSearch), ringCode.toUpperCase());
        click(find(ringCodeSearchButton));
        radioButtonClick("R:Ring Code", ringCode.toUpperCase());
        quickClick(find(ringCodeSelectionOKButton));
        switchToSpecificWindow(parent2);
        waitUntilVisibleElement(find(okButton));
        clickCancelAndAlert(find(okButton), "accept");
        acceptAlert();
        waitUntilVisibleElement(find(okButton));
        clickCancelAndAlert(find(cancelButton), "accept");
        acceptAlert();
        switchToSpecificWindow(parent1);
        sleep(5);
    }

    public Por addNewPORTracker(Por por) throws Exception
    {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        dropDownDotsClick("R:Ring Code");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        // ringCodeSearch.click();
        click(find(ringCodeSearch));
        setText(find(ringCodeSearch), por.ringID.toUpperCase());
        //ringCodeSearch.sendKeys(ringCode.name.uppercase());
        //ringCodeSearchButton.click();
        click(find(ringCodeSearchButton));
        radioButtonClick("R:Ring Code", por.ringID.toUpperCase());
        quickClick(find(ringCodeSelectionOKButton));
        switchToSpecificWindow(parent2);
        dropDownDotsClick("POR:Program Name");
        String parent3 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(ringCodeSearchButton));
        radioButtonClick("PAT:Program Dropdown",por.programName);
        buttonClick("OK", 3);
        switchToSpecificWindow(parent3);
        selectDropdownOption(selectionBoxBySname("POR:Requested Plan Status - New Request").get(0),por.planStatus);
        selectDropdownOption(selectionBoxBySname("POR:Objective").get(0),por.objective);
//        dropDownValueSelection("POR:Objective", por.objective);
//        dropDownValueSelection("POR:Requested Plan Status - New Request", por.planStatus);
        click(find(applyButton));
        if(isAlertPresent()){
            //clickCancelAndAlert(find(okButton), "accept");
            acceptAlert();
            return null;
        }

        sleep(10);
        waitUntilVisibleElement(inputBoxDataBySname("POR:POR ID"));
        por.porId =inputBoxDataBySname("POR:POR ID").getAttribute("origval");
        System.out.println("PORID_"+por.porId);
        if(isAlertPresent())
            return null;
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        return por;
    }


    public String changePORRequestQueue() throws Exception
    {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(5);
        scrollToElement(find(porRequestQueue));
        dropDownValueSelection("POR:POR Request Queue","POR Request Complete");
        buttonClick("Apply",1);
        sleep(20);
        scrollToElement(pencilIcon("PJ:Project ID").get(0));
        sleep(10);

        click(pencilIcon("PJ:Project ID").get(0));
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        // String ProjectIDValueText= getDocumentTextByIdJs(ProjectIDValueField);
        String ProjectIDValueText=inputBoxDataBySname("PJ:Project ID").getAttribute("origval");
        System.out.println("ProjectIDValue_" +ProjectIDValueText);
        click(find(cancelButton));
        switchToSpecificWindow(parent2);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        return ProjectIDValueText;
    }

    public String changePORRequestQueue1(Por por) throws Exception
    {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(5);
        scrollToElement(find(porRequestQueue));
        selectDropdownOption(selectionBoxBySname("POR:POR Request Queue").get(0),por.queueStatus);
        //dropDownValueSelection("POR:POR Request Queue",por.queueStatus);
        buttonClick("Apply",1);
        waitUntilVisibleElement(find(porRequestQueue));
        scrollToElement(pencilIcon("PJ:Project ID").get(0));
        sleep(10);
        waitUntilVisibleElement(pencilIcon("PJ:Project ID").get(0));
        //TODO update the JS option
        sleep(10);

        String ProjectIDValueText= getDocumentTextByIdJs(ProjectIDValueField);
        // String ProjectIDValueText1=find(ProjectIDValuetext).getAttribute("origval");
        System.out.println("ProjectIDValue_" +ProjectIDValueText);
        parentWindow = parent1;
        if(ProjectIDValueText == null||ProjectIDValueText.isEmpty())
            return "";
        return ProjectIDValueText;
    }

    public PORTrackerPage returnToPORTracker() throws Exception {
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        waitUntilVisibleElement(find(editPOR));
        return new PORTrackerPage(driver);
    }

    public boolean isProjectIdGenerated() throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(5);
        scrollToElement(find(porRequestQueue));
        dropDownValueSelection("POR:POR Request Queue","POR Request Complete");
        buttonClick("Apply",1);
        waitUntilVisibleElement(find(porRequestQueue));
        scrollToElement(pencilIcon("PJ:Project ID").get(0));
        String projectIDValueText= getDocumentTextByIdJs(ProjectIDValueField);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        System.out.println("ProjectIDValue_" +projectIDValueText);
        if(projectIDValueText==null||projectIDValueText.isEmpty()){
            return false;
        }
        return true;
    }

    public PORTrackerPage bundleProject() throws Exception {
        parentWindow  = switchToChildWindows();
        fullScreenChildWindow();
        scrollToElement(lockByLabelText("POR:Bundling Disposition").get(0));
        WebElement lock=lockByLabelText("POR:Bundling Disposition").get(0);
        String locktext=lock.getAttribute("class");
        System.out.println(locktext);
        if( locktext.equalsIgnoreCase("lock-icon"))
        {
            System.out.println("Bundling Disposition field is  unlocked");
        }
        else
        {
            System.out.println("Bundling Disposition field is  unlocked");
            scrollToElement(lockByLabelText("POR:Bundling Disposition").get(0));
            click(lockByLabelText("POR:Bundling Disposition").get(0));
        }
        // sleep(30);
        dropDownValueSelection("POR:Bundling Disposition","Unbundled");

        WebElement lock1=lockByLabelText("POR:Bundled Project ID").get(0);
        String lockText1=lock1.getAttribute("class");
        System.out.println(lockText1);
        if( lockText1.equalsIgnoreCase("lock-icon"))
        {
            System.out.println("Bundling Project field is  unlocked");
        }
        else
        {
            System.out.println("Bundling Project field is  unlocked");
            scrollToElement(lockByLabelText("POR:Bundled Project ID").get(0));
            click(lockByLabelText("POR:Bundled Project ID").get(0));
        }
        //sleep(30);
        waitUntilVisibleElement(find(applyButton));
        click(find(applyButton));
        sleep(10);
        waitUntilVisibleElement(find(okButton));
        //click(find(okButton));
        sleep(10);
        // switchToSpecificWindow(parent1);
        return returnToPORTracker();
    }

    public String changePORRequestQueueforBundle() throws Exception
    {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        //sleep(5);
        waitUntilVisibleElement(find(porRequestQueue));
        scrollToElement(find(porRequestQueue));
        dropDownValueSelection("POR:POR Request Queue","POR Request Complete");
        buttonClick("Apply",1);
        // sleep(20);
        waitUntilVisibleElement(pencilIcon("PJ:Project ID").get(0));
        scrollToElement(pencilIcon("PJ:Project ID").get(0));
        // sleep(10);
        click(pencilIcon("PJ:Project ID").get(0));
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        // String ProjectIDValueText= getDocumentTextByIdJs(ProjectIDValueField);
        String ProjectIDValueText=inputBoxDataBySname("PJ:Project ID").getAttribute("origval");
        System.out.println("ProjectIDValue_" +ProjectIDValueText);
        click(find(cancelButton));
        switchToSpecificWindow(parent2);
        // sleep(20);
        scrollToElement(lockByLabelText("POR:Bundling Disposition").get(0));
        WebElement lock=lockByLabelText("POR:Bundling Disposition").get(0);
        String locktext=lock.getAttribute("class");
        System.out.println(locktext);
        if( locktext.equalsIgnoreCase("lock-icon"))
        {
            System.out.println("Bundling Disposition field is  unlocked");
        }
        else
        {
            System.out.println("Bundling Disposition field is  unlocked");
            scrollToElement(lockByLabelText("POR:Bundling Disposition").get(0));
            click(lockByLabelText("POR:Bundling Disposition").get(0));
        }
        // sleep(30);
        dropDownValueSelection("POR:Bundling Disposition","Unbundled");
        WebElement lock1=driver.findElement(By.xpath("//div[@id='idx93_imglock']"));
        String locktext1=lock1.getAttribute("class");
        System.out.println(locktext1);
        if( locktext1.equalsIgnoreCase("lock-icon"))
        {
            System.out.println("Bundling Project field is  unlocked");
        }
        else
        {
            System.out.println("Bundling Project field is  unlocked");
            scrollToElement(lockByLabelText("POR:Bundled Project ID").get(0));
            click(lockByLabelText("POR:Bundled Project ID").get(0));
        }
        //sleep(30);
        waitUntilVisibleElement(find(applyButton));
        click(find(applyButton));
        // sleep(30);
        waitUntilVisibleElement(find(okButton));
        click(find(okButton));
        sleep(60);
        switchToSpecificWindow(parent1);
        return ProjectIDValueText;
    }


    public String changePORRequestQueueforBundle1() throws Exception
    {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        //sleep(5);
        scrollToElement(lockByLabelText("POR:Bundling Disposition").get(0));
        WebElement lock=lockByLabelText("POR:Bundling Disposition").get(0);
        String locktext=lock.getAttribute("class");
        System.out.println(locktext);
        if( locktext.equalsIgnoreCase("lock-icon"))
        {
            System.out.println("Bundling Disposition field is  unlocked");
        }
        else
        {
            System.out.println("Bundling Disposition field is  unlocked");
            scrollToElement(lockByLabelText("POR:Bundling Disposition").get(0));
            click(lockByLabelText("POR:Bundling Disposition").get(0));
        }
        //  sleep(30);
        dropDownValueSelection("POR:Bundling Disposition","Unbundled");
        WebElement lock1=lockByLabelText("POR:Bundled Project ID").get(0);
        String lockText1=lock1.getAttribute("class");
        System.out.println(lockText1);
        if( lockText1.equalsIgnoreCase("lock-icon"))
        {
            System.out.println("Bundling Project field is  unlocked");
        }
        else
        {
            System.out.println("Bundling Project field is  unlocked");
            scrollToElement(lockByLabelText("POR:Bundled Project ID").get(0));
            click(lockByLabelText("POR:Bundled Project ID").get(0));
        }
        waitUntilVisibleElement(find(applyButton));
        //sleep(30);
        click(find(applyButton));
        waitUntilVisibleElement(find(okButton));
        String ProjectIDValueText=inputBoxDataBySname("PJ:Project ID").getAttribute("origval");
        click(find(okButton));
        switchToSpecificWindow(parent1);
        System.out.println("ProjectIDValue_" +ProjectIDValueText);
        parentWindow = parent1;
        if(ProjectIDValueText == null||ProjectIDValueText.isEmpty())
            return "";
        return ProjectIDValueText;

    }


    public PORTrackerPage BundleMultiplePOR(String ProjectID) throws Exception
    {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(5);
        scrollToElement(lockByLabelText("POR:Bundling Disposition").get(0));
        //waitUntilVisibleElement(lockByLabelText("POR:Bundling Disposition").get(0));
        WebElement lock=lockByLabelText("POR:Bundling Disposition").get(0);
        String lockText=lock.getAttribute("class");
        System.out.println(lockText);
        if( lockText.equalsIgnoreCase("lock-icon"))
        {
            System.out.println("Bundling Disposition field is  unlocked");
        }
        else
        {
            System.out.println("Bundling Disposition field is  locked");
            scrollToElement(lockByLabelText("POR:Bundling Disposition").get(0));
            click(lockByLabelText("POR:Bundling Disposition").get(0));
        }

        dropDownValueSelection("POR:Bundling Disposition","Bundled");
        WebElement lock1 = lockByLabelText("POR:Bundled Project ID").get(0);
        String lockText1=lock1.getAttribute("class");
        System.out.println(lockText1);
        if( lockText1.equalsIgnoreCase("lock-icon"))
        {
            System.out.println("Bundling Project field is  unlocked");
        }
        else
        {
            System.out.println("Bundling Project field is  locked");
            scrollToElement(lockByLabelText("POR:Bundled Project ID").get(0));
            click(lockByLabelText("POR:Bundled Project ID").get(0));
        }
        dropDownDotsClick("POR:Bundled Project ID");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(10);
//        waitUntilVisibleElement(find(OKButton1));
//        click(find(ProjectSearchTextbox));
//        setText(find(ProjectSearchTextbox),ProjectID);
//        //sleep(10);
//        click(find(ProjectSearchTextboxSearchButton));
//        waitUntilVisibleElement(find(ProjectSearchRadioButton));
//        radioButtonClick("PJ:Project ID", ProjectID);
//        click(find(OKButton1));
        waitForProject(ProjectID);
        radioButtonClick("PJ:Project ID", ProjectID);
        click(find(OKButton1));
        switchToSpecificWindow(parent2);
        click(find(applyButton));
        //sleep(30);
        waitUntilVisibleElement(find(okButton));
        click(find(okButton));
        switchToSpecificWindow(parent1);
        return new PORTrackerPage(driver);
    }

    public void waitForProject(String ProjectID) throws Exception {
        waitUntilVisibleElement(find(OKButton1));
        click(find(ProjectSearchTextbox));
        setText(find(ProjectSearchTextbox),ProjectID);
        //sleep(10);
        click(find(ProjectSearchTextboxSearchButton));
        List<WebElement>  button = findAll(ProjectSearchRadioButton);
        for(int i=0;i<10;i++){
            if(button.size()>0)
                return;
            else{
                sleep(20);
                button = findAll(ProjectSearchRadioButton);
            }
        }
        waitUntilVisibleElement(find(ProjectSearchRadioButton));
    }

}
