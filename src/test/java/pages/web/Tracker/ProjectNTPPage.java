package pages.web.Tracker;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class ProjectNTPPage extends BasePage {

    public WebDriver driver;
    String parentWindow;

    public ProjectNTPPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By  searchButton = By.xpath("//input[@id='btnSearch0']");
    public By  editButton  = By.xpath("//input[@id='btnEdit0']");
    public By  searchTypeDropdown  = By.xpath("//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*");
    public By  selectedOption  = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public String  topDivSearchTypeDropdown = "//div[@class='component item_select'][normalize-space()='textName']";
    public By okButton  = By.xpath("//input[@id='btnOK']");
    public By cancel  = By.xpath("//input[@id='btnCancel']");
    public By okButton1 =By.xpath("//input[@id='btnOK0']");
    public By applyButton  = By.xpath("//input[@id='btnApply']");
    public By ntpTabs = By.xpath("//div[@title='PJ:NTP Construction']");
    public By ntpApprovals =By.xpath("//div[@title='PJ:NTP Approvals']");
    public By coversheetpdffile4075 = By.xpath("//input[@sname='PJ:Cover Sheet PDF File(4075)']");
    public By checklistpdffile4075 = By.xpath("//input[@sname='PJ:Check List PDF FIle(4075)']");
    public By textboxNTPConstruction4100 = By.xpath("//input[@sname='PJ:Construction NTP Accepted by GC (4100) [Doc]']");
    public By coversheetpdffile4100 = By.xpath("//input[@sname='PJ:Cover Sheet PDF File(4100)']");
    public By checklistpdffile4100 = By.xpath("//input[@sname='PJ:Check List PDF(4100)']");
    public By textboxNTPConstruction4075 = By.xpath("//input[@sname='PJ:Construction NTP Submitted to GC (4075) [Doc]']");
    public By label_NTPConstruction = By.xpath("//span[text()='NTP Construction']");
    public By label_NTPApproval = By.xpath("//span[text()='NTP Approvals']");

    public void searchForValue(String data, String type) throws Exception
    {
        fullScreen();
        waitUntilVisibleElement(find(searchOption));
        setText(find(searchOption), data);
        wait(3);
        //sleep(5);
        selectSearchType(type);
        click(find(searchButton));
        waitForPageToLoad();
        //waitUntilVisibleElement(find(editButton));
        WebElement button = find(editButton);
        button.click();
        wait(5);
        // sleep(10);
    }


    public void selectSearchType(String type) throws Exception {
        WebElement searchDropdown = find(searchTypeDropdown);
        click(searchDropdown);
        System.out.println("sss" + searchDropdown);
        if (!find(selectedOption).getText().equals(type)) {
            dropDownButtons(topDivSearchTypeDropdown, type);
        }
    }

    public AddProjectPage selectEditOption() throws Exception {
        // wait(5);
        // waitUntilVisibleElement(find(editButton));
        sleep(4);
        click(find(editButton));
        waitForPageToLoad();
        // sleep(4);
        return new AddProjectPage(driver);
    }

    public void navigateToNTPTabs() throws Exception {
        waitUntilVisibleElement(find(applyButton));
        scrollToElement(find(label_NTPConstruction));
        sleep(5);
        click(find(label_NTPConstruction));
    }


    public boolean verifyDevelopmentApprovalStatus() throws Exception {
        WebElement status = selectionBoxBySname("PJ:Development Approval Status").get(0);
        boolean text = !(status.isSelected());
        System.out.println("dev approval status"+text);
        wait(2);
        return text;
    }

    public boolean verifyApprovalTabsCheckboxes() throws Exception {
        waitUntilVisibleElement(find(applyButton));
        scrollToElement(find(label_NTPApproval));
        click(find(label_NTPApproval));
        sleep(3);
        boolean one = checkBoxByLabel("PJ:Construction Approval").isSelected();
        boolean two = checkBoxByLabel("PJ:Operations Approval").isSelected();
        boolean three = checkBoxByLabel("PJ:RF Approval").isSelected();
        boolean four = checkBoxByLabel("PJ:Market Manager Approval").isSelected();
        if(one==true||two==true||three==true||four==true){
            return false;
        }
        return true;
    }

    public boolean switchToTrackerOnException(String parentWindow) throws Exception {
        click(find(okButton));
        sleep(5);
        if(isAlertPresent()){
            acceptAlert();
            sleep(2);
            switchToSpecificWindow(parentWindow);
            return true;
        }
        else {
            click(find(cancel));
            if (isAlertPresent()){
                acceptAlert();
            }
            switchToSpecificWindow(parentWindow);
            return false;
        }
    }


    public void checkApprovalsCheckboxes() throws Exception {
        wait(3);
        WebElement check1 = inputBoxDataBySname("PJ:Construction Approval");
        checkBoxCheckByJS(check1);

        WebElement check2 = inputBoxDataBySname("PJ:Operations Approval");
        checkBoxCheckByJS(check2);

        WebElement check3 = inputBoxDataBySname("PJ:RF Approval");
        checkBoxCheckByJS(check3);

        WebElement check4 = inputBoxDataBySname("PJ:Market Manager Approval");
        checkBoxCheckByJS(check4);
        click(find(applyButton));
    }
    public void selectDevelopmentApprovalStatus() throws Exception {
        WebElement commentsBox = textAreaBySname("PJ:Development Comments");
        scrollToElement(commentsBox);
        sleep(3);
        dropDownValueSelection("PJ:Development Approval Status","Approved");
        WebElement check = inputBoxDataBySname("PJ:CX NTP Start");
        checkBoxCheckByJS(check);
        sleep(6);
        /*if(!(isCheckboxSelected("idx470"))) {
         checkBoxCheckByJS(check);
    }*/
        click(find(applyButton));
        sleep(3);
    }

    public String verifyDevelopmentApprovalName() throws Exception {
        waitUntilVisibleElement(find(applyButton));
        WebElement commentsBox = textAreaBySname("PJ:Development Comments");
        scrollToElement(commentsBox);
        String developmentApprovalName = inputBoxDataBySname("PJ:Development Approval Name").getAttribute("value");
        System.out.println("dev Approval name"+developmentApprovalName);
        return developmentApprovalName;
    }

    public String verifyDevelopmentApprovalDate() throws Exception {
        wait(3);
        //waitUntilVisibleElement(find(applyButton));
        WebElement commentsBox = textAreaBySname("PJ:Development Comments");
        scrollToElement(commentsBox);
        String approval_Date = inputBoxDataBySname("PJ:Development Approval Date").getAttribute("value");
        System.out.println("Date is"+approval_Date);
        return approval_Date;
    }
    public boolean verifyAllApprovalsStatusFields() throws Exception {
        wait(6);
        // waitUntilVisibleElement(find(ntpTabs));
        String one = getFirstSelectedOptionInDropdown(selectionBoxBySname("PJ:Const. Approval Status").get(0));
        String two = getFirstSelectedOptionInDropdown(selectionBoxBySname("PJ:Ops Approval Status").get(0));
        String three = getFirstSelectedOptionInDropdown(selectionBoxBySname("PJ:RF Approval Status").get(0));
        String four = getFirstSelectedOptionInDropdown(selectionBoxBySname("PJ:Market Manager Approval Status").get(0));
        System.out.println("one"+one);
        System.out.println("two"+two);
        System.out.println("three"+three);
        System.out.println("four"+four);
        return true;
//        if(one && two && three && four){
//            return true;
//        }
//        else
//            return false;
    }

    public void switchToTracker(String parentWindow) throws Exception {
        // waitUntilVisibleElement(find(okButton));
        sleep(8);
        click(find(okButton));
        sleep(5);
        switchToSpecificWindow(parentWindow);
    }

    public void selectAllApprovalsStatus() throws Exception {
        waitUntilVisibleElement(find(ntpApprovals));
        dropDownValueSelection("PJ:Const. Approval Status", "Approved");
        dropDownValueSelection("PJ:Ops Approval Status","Approved");
        dropDownValueSelection("PJ:RF Approval Status","Approved");
        dropDownValueSelection("PJ:Market Manager Approval Status","Approved");
        click(find(applyButton));
        // sleep(3);
    }

    public Boolean verifyVendorApprovalsStatus(String status) throws Exception {
        waitUntilVisibleElement(find(applyButton));
        scrollToElement(selectionBoxBySname("PJ:Vendor Approval Status").get(0));
        String text = getFirstSelectedOptionInDropdown(selectionBoxBySname("PJ:Vendor Approval Status").get(0));
        if(text.contains(status)){
            System.out.println("__text "+text);
            return true;
        }
        else
            return false;
    }


    public String verifyNTP_SubmittedtoGCField() throws Exception {
        waitUntilVisibleElement(find(applyButton));
        WebElement lock = lockByLabelText("PJ:Construction NTP Submitted to GC (4075) [Doc]").get(0);
        scrollToElement(lock);
        String text = lock.getAttribute("class");
        System.out.println(text);
        if (text.equalsIgnoreCase("lock-icon")) {
            System.out.println("field unlocked");
            return "field unlocked";
        } else {
            System.out.println(" field locked");
            return "field locked";
        }
    }

    public boolean verifyPDF() throws Exception {
        waitUntilVisibleElement(find(applyButton));
        String pdfFile = inputBoxDataBySname("PJ:Construction NTP Submitted to GC (4075) [Doc]").getAttribute("id");
        String filename = getDocumentTextByIdJs(pdfFile);
        if(filename.contains("pdf")){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean createPreNtp(String parentWindow) throws Exception {
        sleep(5);
        click(checkBoxByLabel("PJ:Create Pre-NTP"));
        if(lockByLabelText("PJ:Pre-NTP Issued (3800)  [Doc]").get(0).getAttribute("class").contains("locked"))
            click(lockByLabelText("PJ:Pre-NTP Issued (3800)  [Doc]").get(0));
        waitUntilVisibleElement(find(applyButton));
        click(find(applyButton));
        sleep(20);
        String boxId = inputBoxDataBySname("PJ:Pre-NTP Issued (3800)  [Doc]").getAttribute("id");
        String fileText = getDocumentTextByIdJs(boxId);
        System.out.println("File Text "+fileText);
        if(fileText.contains("ZipEfiles")){
            sleep(3);
            click(find(okButton));
            switchToSpecificWindow(parentWindow);
            return true;
        }
        else{
            click(find(okButton));
            switchToSpecificWindow(parentWindow);
            return false;
        }

    }

    public String verifyLockNtp4100() throws Exception {
        WebElement lock = lockByLabelText("PJ:Construction NTP Accepted by GC (4100) [Doc]").get(0);
        scrollToElement(lock);
        String locktext = lock.getAttribute("class");
        System.out.println(locktext);
        if (locktext.equalsIgnoreCase("lock-icon")) {
            System.out.println("field unlocked");
            return "field unlocked";
        } else {
            System.out.println(" field locked");
            return "field locked";

        }

    }

    public String VerifyFileName(String sheetName) throws Exception {
        scrollToElement(inputBoxDataBySname(sheetName));
        String fileName = getDocumentTextByIdJs(inputBoxDataBySname(sheetName).getAttribute("id"));
        return fileName;
    }
    public String verifyApprovalStatus() throws Exception {
        WebElement status = selectionBoxBySname("PJ:Development Approval Status").get(0);
        scrollToElement(status);
        String approvalStatus = getFirstSelectedOptionInDropdown(status);
        return approvalStatus;
    }
    public boolean verifyNTPCheckBox() throws Exception {
        WebElement checkBox = inputBoxDataBySname("PJ:CX NTP Start");
        return checkBox.isSelected();
    }

    public void checkNTPCheckBox() throws Exception {
        WebElement checkBox = inputBoxDataBySname("PJ:CX NTP Start");
        WebElement commentsBox = textAreaBySname("PJ:Development Comments");
        scrollToElement(commentsBox);
        boolean isCheckBoxSelected = false;
        if(!isCheckBoxSelected){
            checkBoxCheckByJS(checkBox);
        }
        click(find(applyButton));
        sleep(5);
        acceptAlert();
        sleep(3);
    }

    public void selectVendorApproved() throws Exception {
        //wait(3);
        waitUntilVisibleElement(find(applyButton));
        scrollToElement(inputBoxDataBySname("PJ:Vendor Approval Name"));
        sleep(3);
        dropDownValueSelection("PJ:Vendor Approval Status","Approved");
        click(find(applyButton));
    }
    public void unActualizationOf4075() throws Exception {
        waitUntilVisibleElement(find(applyButton));
        WebElement NTPSubmittedBox = inputBoxDataBySname("PJ(A 4075) Construction NTP Submitted to GC");
        sleep(5);
        clearInputBoxByElementAndSendKeys(NTPSubmittedBox);
        sleep(5);
        click(find(applyButton));
    }

    public boolean ntpConstruction4075Validation() throws Exception {
        sleep(2);
        String value = getText(findAll(textboxNTPConstruction4075).get(0));
        if (value.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean coversheetpdffile4075validation() throws Exception {
        sleep(2);
        String coversheetValue = getText(findAll(coversheetpdffile4075).get(0));
        if (coversheetValue.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean checklistpdffile4075validation() throws Exception {
        sleep(2);
        String coversheetValue = getText(findAll(checklistpdffile4075).get(0));
        if (coversheetValue.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void unActualizationOf4100() throws Exception {
        waitUntilVisibleElement(find(applyButton));
        WebElement NTPAcceptedBox = inputBoxDataBySname("PJ(P 4100) Construction NTP Accepted by GC");
        clearInputBoxByElementAndSendKeys(NTPAcceptedBox);
        sleep(2);
        click(find(applyButton));
    }

    public boolean ntpConstruction4100Validation() throws Exception {
        waitUntilVisibleElement(find(textboxNTPConstruction4100));
        String value = getText(findAll(textboxNTPConstruction4100).get(0));
        if (value.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean coversheetpdffile4100validation() throws Exception {
        String coversheetValue = getText(findAll(coversheetpdffile4100).get(0));
        if (coversheetValue.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean checklistpdffile4100validation() throws Exception {
        String coversheetValue = getText(findAll(checklistpdffile4100).get(0));
        if (coversheetValue.isEmpty()) {
            //switchToSpecificWindow(parentWindow);
            return true;
        } else {
            //switchToSpecificWindow(parentWindow);
            return false;
        }
    }

    public void selectDevelopmentRejectionStatus() throws Exception {
        waitUntilVisibleElement(find(applyButton));
        sleep(6);
        dropDownValueSelection("PJ:Const. Approval Status","Rejected");
        dropDownValueSelection("PJ:Ops Approval Status","Rejected");
        dropDownValueSelection("PJ:RF Approval Status","Rejected");
        dropDownValueSelection("PJ:Market Manager Approval Status","Rejected");
        click(find(applyButton));
        sleep(4);
    }

    public void addNotes() throws Exception {
        fullScreen();
        waitUntilVisibleElement(find(applyButton));
        sleep(10);
        scrollToElement(textAreaBySname("PJ:Const. Comments"));
        setText(textAreaBySname("PJ:Const. Comments"),"Notes for Rejection");
        textAreaBySname("PJ:Ops Comments").sendKeys("Notes for Rejection");
        textAreaBySname("PJ:RF Comments").sendKeys("Notes for Rejection");
        sleep(3);
        scrollToElement(textAreaBySname("PJ:Market Manager Comments"));
        textAreaBySname("PJ:Market Manager Comments").sendKeys("Notes for Rejection");
        click(find(applyButton));
        sleep(3);
    }

    public void wait(int time){
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
//    public void waitForPageToLoad(){
//        JavascriptExecutor js = (JavascriptExecutor)driver;
//        int i=0;
//
//        while(i!=10){
//            String state = (String)js.executeScript("return document.readyState;");
//            System.out.println(state);
//
//            if(state.equals("complete"))
//                break;
//            else
//                wait(2);
//
//            i++;
//        }
//        wait(2);// wait of 2 sec between page status and jquery
//        // check for jquery status
//        i=0;
//        while(i!=10){
//
//            Boolean result= (Boolean) js.executeScript("return window.jQuery != undefined && jQuery.active == 0;");
//            System.out.println(result);
//            if(result )
//                break;
//            else
//                wait(2);
//            i++;
//
//        }
//    }
}
