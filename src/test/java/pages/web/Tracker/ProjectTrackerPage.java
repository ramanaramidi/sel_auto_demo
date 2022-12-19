package pages.web.Tracker;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import utility.helper.MiscHelpers;
import java.util.List;

public class ProjectTrackerPage extends BasePage {

    public WebDriver driver;

    public ProjectTrackerPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By  searchButton = By.xpath("//input[@id='btnSearch0']");
    public By  editProject  = By.xpath("//input[@id='btnEdit0']");
    public By  searchTypeDropdown  = By.xpath("//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*");
    public By  selectedOption  = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public String  topDivSearchTypeDropdown = "//div[@class='component item_select'][normalize-space()='textName']";
    public By totalProjectIDCount= By.xpath("//span[@id='gridStat0']");
    public By projectCountNav = By.xpath("//div[@id='navRange0']");
    public By tableHeader = By.xpath("//table[@class='hdr']//tr//td");
    public By tableData = By.xpath("//table[@class='obj']//tr");
    public By okButton  = By.xpath("//input[@id='btnOK']");
    public By projectIDClick  = By.xpath("//div[@class='objbox customscroll']//table//tbody//tr[2]//td[3]//a");
    public By okButton1 =By.xpath("//input[@id='btnOK0']");
    public By applyButton  = By.xpath("//input[@id='btnApply']");
   // public By ntpTabs = By.xpath("//*[@id='tab30']");
  //  public By ntpApprovals =By.xpath("//*[@id='tabName31']");
    public By rfApproval = By.xpath("//*[@id='idx507']");
    public By opsApproval = By.xpath("//*[@id='idx497']");
    public By marketMgrApproval = By.xpath("//*[@id='idx517']");

    //    public By scipPhotoUpload = By.xpath("//input[@id='idx174_but']");
//    public By titleApprovedLabel = By.xpath("//label[@id='idx169_lbl']");
//    public By coloAppSubmittedImageBox = By.xpath("//input[@id='idx174_disp']");
    public By cancelButton = By.xpath("//input[@id='btnCancel']");
    public By AETab = By.xpath("//span[text()='A and E']");
    //    public By WorkPlanKickOffCheckBox = By.xpath("//label[@id='lblidx27']");
    public By WorkPlanCreationLocked = By.xpath("//div[@id='idx27_imglock']");
    public By BundlingDespositionUnlock = By.xpath("//div[@id='idx91_imglock']");
    public By cxNTPStart = By.xpath("//*[@id='lblidx466']");
    public By constructionApproval = By.xpath("//*[@id='idx488']");
    public By ntpConstruction = By.xpath("//span[normalize-space()='NTP Construction']");
    public By ntpTabs = By.xpath("//*[@id='tabName25']");
    public By ntpApprovals =By.xpath("//*[@id='tabName26']");
    public By label_NTPConstruction = By.xpath("//span[text()='NTP Construction']");
    public By label_NTPApproval = By.xpath("//span[text()='NTP Approvals']");
    public By VendorField = By.xpath("//label[text()='PJ:Vendor']/parent::td/following-sibling::td//input");
    public By VendorSelection = By.xpath("//input[@id='btnOK0']");
    public By VendorSelectionInputBox = By.xpath("//input[@id='qsValue0']");
    public By VendorSearchButton = By.xpath("//input[@id='btnSearch0']");
    public By VendorInfoTab = By.xpath("//span[text()='Vendor Info']");
    public By ClickView = By.xpath("//span[@id='viewCaption0']");
    public By SearchFilter = By.xpath("//input[@id='search_viewSearch0']");
    public By ApplyFilter = By.xpath("//input[@id='applyVFTerminal0']");
    public By SiteCode = By.xpath("//div[@class='hdr_cell']//div[text()='S:Site Code']");
    public By SelectStatus = By.xpath("//div[@class='gridEditControl']//select");
    public By StatusFind = By.xpath("//td[@title='New Trackor']/following-sibling::td");
    public By SaveChangesButton = By.xpath("//input[@value='Save Changes']");
    public By ClickStructuralModification = By.xpath("//label[text()='PJ(P 1375) Structural Modification Design Complete']");
    public By SelectNaTask = By.xpath("//div[text()='Un-N/A Task']");

    String parentWindow;

    public void searchForValue(String data, String type) throws Exception
    {
        fullScreen();
        waitForPageToLoad();
        // waitUntilVisibleElement(find(searchOption));
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitForPageToLoad();
        // sleep(10);
        //waitUntilVisibleElement(find(editProject));
        // sleep(20);
    }


    public void selectSearchType(String type) throws Exception {
        WebElement searchDropdown = find(searchTypeDropdown);
        click(searchDropdown);
        System.out.println("sss" + searchDropdown);
        if (!find(selectedOption).getText().equals(type)) {
            dropDownButtons(topDivSearchTypeDropdown, type);
        }
    }

    public int getProjectIDCount() throws Exception
    {   waitForPageToLoad();
        waitUntilVisibleElement(find(projectCountNav));
        if(!getText(find(projectCountNav)).contains("No Data")){
            waitUntilVisibleElement(find(totalProjectIDCount));
            hoverOver(find(totalProjectIDCount));
            String PORTrackerIDCount=find(totalProjectIDCount).getText();
            System.out.println(PORTrackerIDCount);
            return  Integer.parseInt(PORTrackerIDCount);
        }
        return 0;
    }

    public boolean isValuePresentInGrid(String columnName) throws Exception {
        waitForPageToLoad();
        int columnToFind = getTableData(columnName);
        List<WebElement> tableContents = findAll(tableData);
        List<WebElement> cellDataForTheFirstRow = findAll(By.tagName("td"),tableContents.get(1));
        String cellData = getText(cellDataForTheFirstRow.get(columnToFind));
        return !cellData.isEmpty() && !cellData.equals(null) && !cellData.equals(" ");
    }

    public int getTableData(String columnName) throws Exception {
        List<WebElement> tableHeaders = findAll(tableHeader);
        scrollToElement(tableHeaders.get(1));
        for(int i = 0; i<tableHeaders.size(); i++){
            scrollToElement(tableHeaders.get(i));
            if(getText(find(By.className("hdr_label"),tableHeaders.get(i))).contains(columnName)){
                scrollToElement(tableHeaders.get(1));
                return i;
            }
        }
        return -1;
    }

    public String searchForValueInGrid(String columnName, String searchByColumn, String searchValue) throws Exception {
        fullScreen();
        waitForPageToLoad();
        int columnToFind = getTableData(columnName);
        int columnValueToMatch = getTableData(searchByColumn);
        List<WebElement> tableContents = findAll(tableData);
        if(columnValueToMatch !=-1 && columnToFind !=-1){

            for (int row = 1; row<tableContents.size(); row++){
                List<WebElement> cellDataForTheRow = findAll(By.tagName("td"),tableContents.get(row));
                scrollToElement(cellDataForTheRow.get(1));
                if(getText(cellDataForTheRow.get(columnValueToMatch)).contains(searchValue)){
                    sleep(1);
                    scrollToElement(cellDataForTheRow.get(columnToFind));
                    String cellValue = getText(cellDataForTheRow.get(columnToFind));
                    if(cellValue.isEmpty()){
                        cellValue = getText(find(By.tagName("div"),cellDataForTheRow.get(columnToFind)));
                    }
                    return cellValue;
                }
            }
        }
        return "ERROR OCCURRED";
    }

    public AddProjectPage selectEditOption() throws Exception {
        waitUntilVisibleElement(find(editProject));
        waitForPageToLoad();
        click(find(editProject));
        waitForPageToLoad();
        return new AddProjectPage(driver);
    }


    public String projectIDClick() throws Exception
    {
        sleep(5);
        click(find(projectIDClick));
        sleep(5);
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(5);
        scrollToElement(inputBoxDataBySname("PJ:Planned Start Date"));
        dropDownDotsClick("PJ:Leasing");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        checkBoxSelection("DDT:Deliverable Name","Colo App Submitted");
        click(find(okButton1));
        switchToSpecificWindow(parent2);
        sleep(5);
        click(find(applyButton));
        sleep(10);
        String appSubmittedText = textAreaByTitle("Colo App Submitted").getAttribute("value");
        scrollToElement(clipButtonBySname("PJ:Colo App Submitted  (1200) [Doc]"));
        scrollToElement(inputBoxDataBySname("PJ(P 1225) Colo App Approved"));
        WebElement lock=lockByLabelText("PJ:Colo App Submitted  (1200) [Doc]").get(0);
        String locktext=lock.getAttribute("class");
        System.out.println(locktext);
        if( locktext.equalsIgnoreCase("lock-icon"))
        {
            System.out.println("PJ:Colo App Submitted (1200) [Doc] field  unlocked");
        }
        else
        {
            System.out.println("PJ:Colo App Submitted (1200) [Doc] field not unlocked");
        }
        click(find(okButton));
        sleep(10);
        switchToSpecificWindow(parent1);
        return appSubmittedText;
    }

    public String coloAppImageUpload() throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
//        WebElement scipPhotoUpload =  clipButtonBySname("PJ:Colo App Submitted  (1200) [Doc]");
//        //int docCount =  Integer.parseInt(getText(find(documentsTabCounter)));
//        scrollToElement(scipPhotoUpload);
//        click(scipPhotoUpload);
//        sleep(5);
//        Runtime.getRuntime().exec(Constants.IMAGE_FILE_UPLOAD);
//        waitUntilVisibleElement(scipPhotoUpload);
        sleep(2);
        click(find(applyButton));
        waitUntilVisibleElement(find(applyButton));
        sleep(10);
        WebElement coloAppApprovedDate = inputBoxDataBySname("PJ(P 1225) Colo App Approved");
        scrollToElement(coloAppApprovedDate);
        WebElement lock=lockByLabelText("PJ:Colo App Approved (1225) [Doc]").get(0);
        String locktext=lock.getAttribute("class");
        System.out.println(locktext);
        if(locktext.equalsIgnoreCase("lock-icon locked"))
        {
            System.out.println("Doc uploaded and field locked");
        }
        else
        {
            System.out.println("Doc not  uploaded and field not locked");
        }
        waitUntilVisibleElement(coloAppApprovedDate);
        //sleep(10);
        String coloApprovedFinishedDate = inputBoxDataBySname("PJ(A 1225) Colo App Approved").getAttribute("value");
        System.out.println("COl Finish Date_"+coloApprovedFinishedDate);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        return coloApprovedFinishedDate;
    }

    public String DeleteScipImage() throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();

        scrollToElement(inputBoxDataBySname("PJ:Title Approved (1050) [Doc]"));
        WebElement lock=lockByLabelText("PJ:Colo App Submitted  (1200) [Doc]").get(0);
        String locktext=lock.getAttribute("class");
        System.out.println(locktext);
        WebElement coloAppSubmittedImageBox = inputBoxDataBySname("PJ:Colo App Submitted  (1200) [Doc]");
        if( locktext.equalsIgnoreCase("lock-icon locked"))
        {
            click(lock);
            clearInputBoxByElementAndSendKeys(coloAppSubmittedImageBox);
            click(find(applyButton));
            System.out.println("Doc  removed and field unlocked");
        }
        else
        {
            clearInputBoxByElementAndSendKeys(coloAppSubmittedImageBox);
            click(find(applyButton));
            System.out.println("Doc not removed and field  locked");
        }
        WebElement coloAppSubmittedDate = inputBoxDataBySname("PJ(P 1200) Colo App Submitted");
        waitUntilVisibleElement(coloAppSubmittedDate);
        sleep(10);
        coloAppSubmittedDate = inputBoxDataBySname("PJ(A 1200) Colo App Submitted");
        String coloApprovedDate1 = coloAppSubmittedDate.getAttribute("value");
        System.out.println("Col Finish Date After Removing finish Date_"+coloApprovedDate1);
        //scrollToElement(find(coloAppSubmittedDate));
        click(find(okButton));
        switchToSpecificWindow(parent1);
        if(coloApprovedDate1 == null||coloApprovedDate1.isEmpty())
            return "";
        return coloApprovedDate1;
    }

    public String ProjectComments(String ProjectCommentText) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        WebElement projectCommentsTextArea = textAreaBySname("PJ:Project Comments");
        scrollToElement(projectCommentsTextArea);
        setText(projectCommentsTextArea,ProjectCommentText);
        click(find(applyButton));
        sleep(10);
        WebElement projectCommentsTextAreaHistory = textAreaBySname("PJ:Project Comment History");
        scrollToElement(projectCommentsTextAreaHistory);
        String ProjectCommentHistoryText=getText(projectCommentsTextAreaHistory);
        System.out.println(ProjectCommentHistoryText);

        if(ProjectCommentHistoryText.contains("Automation Connection Testing"))
        {
            System.out.println("The notes moved from the PJ:Project Comments field to the PJ:Project Comment History field");
        }
        else
        {
            System.out.println("The notes not moved from the PJ:Project Comments field to the PJ:Project Comment History field");
        }
        click(find(okButton));
        switchToSpecificWindow(parent1);

        return ProjectCommentHistoryText;
    }

    public String ActionComments(String ActionItemText) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        WebElement projectActionItems = textAreaBySname("PJ:Action Item Notes");
        scrollToElement(projectActionItems);
        setText(projectActionItems,ActionItemText);
        click(find(applyButton));
        sleep(10);
        WebElement projectActionItemsHistory = textAreaBySname("PJ:Action Item History");
        scrollToElement(projectActionItemsHistory);
        String ActionItemHistoryText=getText(projectActionItemsHistory);
        System.out.println(ActionItemHistoryText);
        if(ActionItemHistoryText.contains("Automation Connection Testing content in Action Item"))
        {
            System.out.println("The notes moved from the PJ:Action Item Notes field to the PJ:Action Item History field");
        }
        else
        {
            System.out.println("The notes not moved from the PJ:Action Item Notes field to the PJ:Action Item History field");
        }
        click(find(okButton));
        switchToSpecificWindow(parent1);

        return ActionItemHistoryText;
    }

    public String AETabDate() throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        scrollToElement(find(AETab));
        click(find(AETab));
        sleep(10);
        String FinishedDate= MiscHelpers.currentDateTime("MM/dd/yyyy");
        System.out.println(FinishedDate);
        WebElement StructuralOrdered = inputBoxDataBySname("PJ(A 1350) Structural Ordered");
        scrollToElement(StructuralOrdered);
        setText(StructuralOrdered,FinishedDate);
        click(find(applyButton));
        sleep(15);
        StructuralOrdered = inputBoxDataBySname("PJ(A 1350) Structural Ordered");
        String StructuralOrderedDate = StructuralOrdered.getAttribute("value");
        System.out.println(StructuralOrderedDate);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        return StructuralOrderedDate;
    }

    public String porCount() throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        WebElement PORCount = inputBoxDataBySname("PJ:POR Count");
        scrollToElement(PORCount);
        sleep(10);
        String PORCountValue=PORCount.getAttribute("origval");
        System.out.println("PorCountValue_" +PORCountValue);
        return PORCountValue;
    }

    public void projectStatus() throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        WebElement WorkPlanKickOffCheckBox1 = checkBoxByBoldedLabel("Workplan Creation Kickoff");
        // WebElement WorkPlanKickOffCheckBox = inputBoxDataBySname("PJ:<B>Workplan Creation Kickoff</B>");
        scrollToElement(WorkPlanKickOffCheckBox1);
        click(WorkPlanKickOffCheckBox1);
        click(find(applyButton));
        sleep(15);
        WorkPlanKickOffCheckBox1 =  checkBoxByBoldedLabel("Workplan Creation Kickoff");
        waitUntilVisibleElement(WorkPlanKickOffCheckBox1);
        scrollToElement(WorkPlanKickOffCheckBox1);
        scrollToElement(selectionBoxBySname("PJ:Project Status").get(0));
        click(find(okButton));
        sleep(50);
        switchToSpecificWindow(parent1);
        waitUntilVisibleElement(find(editProject));
    }

    public String LeasingDocsTotal() throws Exception
    {
        sleep(10);
        waitUntilVisibleElement(find(editProject));
        click(find(projectIDClick));
        sleep(5);
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(5);
        scrollToElement(inputBoxDataBySname("PJ:Planned Start Date"));
        dropDownDotsClick("PJ:Leasing");
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        checkBoxSelection("DDT:Deliverable Name","Amendment 1");
        checkBoxSelection("DDT:Deliverable Name","Consent Letter Approved by LL");
        click(find(okButton1));
        switchToSpecificWindow(parent2);
        sleep(5);
        click(find(applyButton));
        WebElement LeasingDocTotalCount = inputBoxDataBySname("PJ:Leasing Docs Total");
        scrollToElement(LeasingDocTotalCount);
        sleep(10);
        String DocCountValue=LeasingDocTotalCount.getAttribute("origval");
        System.out.println("LeasingDocTotalCount_" +DocCountValue);
        scrollToElement(inputBoxDataBySname("PJ(A 1200) Colo App Submitted"));
        String appSubmittedText = inputBoxDataBySname("PJ(A 1200) Colo App Submitted").getAttribute("value");
        System.out.println(appSubmittedText);
        scrollToElement(inputBoxDataBySname("PJ:Consent Letter Approved by LL (2010)[Doc]"));
        WebElement lock=lockByLabelText("PJ:Consent Letter Approved by LL (2010)[Doc]").get(0);
        String locktext=lock.getAttribute("class");
        System.out.println(locktext);
        if( locktext.equalsIgnoreCase("lock-icon"))
        {
            System.out.println("PJ:Consent Letter Approved by LL (2010)[Doc] field  unlocked");
        }
        else
        {
            System.out.println("PJ:Consent Letter Approved by LL (2010)[Doc] field locked");
        }

        WebElement lock1=lockByLabelText("PJ:Amendment 1 (2010) [Doc]").get(0);
        String locktext1=lock1.getAttribute("class");
        System.out.println(locktext1);
        if( locktext1.equalsIgnoreCase("lock-icon"))
        {
            System.out.println("PJ:Amendment 1 (2010) [Doc] field  unlocked");
        }
        else
        {
            System.out.println("PJ:Amendment 1 (2010) [Doc] field locked");
        }

        click(find(okButton));
        sleep(10);
        switchToSpecificWindow(parent1);
        return appSubmittedText;
    }

    public void plannedStartDateValidation() throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        WebElement PlannedStartDate = inputBoxDataBySname("PJ:Planned Start Date");
        scrollToElement(PlannedStartDate);
        WebElement lock = lockByLabelText("PJ:Planned Start Date").get(0);
        String locktext=lock.getAttribute("class");
        System.out.println(locktext);
        if( locktext.equalsIgnoreCase("lock-icon"))
        {
            System.out.println("field unlocked");
        }
        else
        {
            System.out.println(" field  locked");
            click(lock);
            click(find(applyButton));
            sleep(30);
        }

        String YesterdayDate = MiscHelpers.specificPastDateTime("MM/dd/YYYY",1);
        System.out.println(YesterdayDate);
        scrollToElement(PlannedStartDate);
        clearInputBoxByElementAndSendKeys(PlannedStartDate);
        setText(PlannedStartDate,YesterdayDate);
        WebElement WorkPlanKickOffCheckBox1 = checkBoxByBoldedLabel("Workplan Creation Kickoff");
        // WebElement WorkPlanKickOffCheckBox = inputBoxDataBySname("PJ:<B>Workplan Creation Kickoff</B>");
        scrollToElement(WorkPlanKickOffCheckBox1);
        click(WorkPlanKickOffCheckBox1);
        click(find(applyButton));
        sleep(15);
        clickCancelAndAlert(find(applyButton), "accept");
        sleep(15);
        acceptAlert();
        clickCancelAndAlert(find(cancelButton), "accept");
        acceptAlert();
        sleep(10);
        switchToSpecificWindow(parent1);
        waitUntilVisibleElement(find(editProject));
    }

    public boolean WorkPlanCreation() throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        String window = getCurrentWindow();
        WebElement lock = find(WorkPlanCreationLocked);
        scrollToElement(lock);
        String locktext=lock.getAttribute("class");
        System.out.println(locktext);
        if( locktext.equalsIgnoreCase("lock-icon"))
        {
            System.out.println("field unlocked");
        }
        else
        {
            System.out.println(" field  locked");
            click(find(WorkPlanCreationLocked));
            click(find(applyButton));
            sleep(10);
        }
        checkBoxByBoldedLabel("Workplan Creation Kickoff");
        WebElement WorkPlanKickOffCheckBox1 = checkBoxByBoldedLabel("Workplan Creation Kickoff");
        //WebElement WorkPlanKickOffCheckBox = inputBoxDataBySname("PJ:<B>Workplan Creation Kickoff</B>");
        scrollToElement(WorkPlanKickOffCheckBox1);
        WorkPlanKickOffCheckBox1 = checkBoxByBoldedLabel("Workplan Creation Kickoff");
        click(WorkPlanKickOffCheckBox1);
        click(find(applyButton));
        sleep(10);
        WorkPlanKickOffCheckBox1 = checkBoxByBoldedLabel("Workplan Creation Kickoff");
        scrollToElement(WorkPlanKickOffCheckBox1);
        click(checkBoxByBoldedLabel("Workplan Creation Kickoff"));
        click(find(applyButton));
        sleep(5);
        if(!isAlertPresent())
            return false;
//        clickCancelAndAlert(find(applyButton), "accept");
//        sleep(10);
        acceptAlert();
        switchToSpecificWindow(window);
        click(find(cancelButton));
        //clickCancelAndAlert(find(cancelButton), "accept");
        if(!isAlertPresent())
            return false;
        acceptAlert();
        sleep(10);
        switchToSpecificWindow(parent1);
        waitUntilVisibleElement(find(editProject));
        return true;
    }

    public String AEStructuralModificationDesign() throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        scrollToElement(find(AETab));
        click(find(AETab));
        sleep(10);
        waitUntilVisibleElement(clipButtonBySname("PJ:Structural Modification Design Complete (1375)[Doc]"));
        sleep(2);
        click(find(applyButton));
        sleep(10);
        //scrollToElement(find(StructuralModifiValue));
        WebElement status = selectionBoxBySname("PJ:Structural Modification Design Complete [Status]").get(0);
        scrollToElement(status);
        String StructuralModificationValue=getFirstSelectedOptionInDropdown(status);
//        Select select=new Select(driver.findElement(By.xpath("//select[@id='idx313']")));
//        WebElement option=select.getFirstSelectedOption();
//        String StructuralModificationValue=option.getText();
        System.out.println("___"+StructuralModificationValue);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        return StructuralModificationValue;
    }

    public String updateSiteStatus(String status) throws Exception {
        String parent1 = switchToChildWindows();
        waitUntilVisibleElement(find(applyButton));
        fullScreenChildWindow();
        scrollToElement(find(AETab));
        click(find(AETab));
        sleep(10);
        WebElement structuralModifiedValueStatus = selectionBoxBySname("PJ:Structural Modification Design Complete [Status]").get(0);
        scrollToElement(structuralModifiedValueStatus);
        if(status.equals("")){
            selectDropdownOptionByIndex(structuralModifiedValueStatus,0);
        }
        else {
            selectDropdownOption(structuralModifiedValueStatus, status);
        }
        click(find(applyButton));
        waitUntilVisibleElement(find(okButton));
        sleep(10);
        WebElement lock=lockByLabelText("PJ:Structural Modification Design Complete (1375)[Doc]").get(0);
        waitUntilVisibleElement(lock);
        sleep(5);
        String lockText=lock.getAttribute("class");
        System.out.println(lockText);
        if( lockText.equalsIgnoreCase("lock-icon"))
        {
            System.out.println("PJ:Structural Modification Design Complete (1375)[Doc]  unlocked");
        }
        else
        {
            System.out.println("PJ:Structural Modification Design Complete (1375)[Doc] locked");
        }

        WebElement lock1=lockByLabelText("PJ:Structural Modification Design Complete [Status]").get(0);
        String lockText1=lock1.getAttribute("class");
        System.out.println(lockText1);
        if( lockText1.equalsIgnoreCase("lock-icon"))
        {
            System.out.println("PJ:Structural Modification Design Complete [Status]  unlocked");
        }
        else
        {
            System.out.println("PJ:Structural Modification Design Complete [Status locked");
        }
        String modifiedDatePath = inputBoxXpathBySname("PJ(P 1375) Structural Modification Design Complete");
        String StructuralModificationDesignCompleteDate = getDocumentTextByXpathJs(modifiedDatePath);
        System.out.println(StructuralModificationDesignCompleteDate);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        return StructuralModificationDesignCompleteDate;
        //Approved
    }

    public String unbundleProjectSinglePOR(String status) throws Exception {
        String parent1 = switchToChildWindows();
        waitUntilVisibleElement(find(applyButton));
        fullScreenChildWindow();
        scrollToElement(find(BundlingDespositionUnlock));
        WebElement lock=driver.findElement(By.xpath("//div[@id='idx91_imglock']"));
        String locktext=lock.getAttribute("class");
        System.out.println(locktext);
        if( locktext.equalsIgnoreCase("lock-icon"))
        {
            System.out.println("Bundling Disposition field is  unlocked");
        }
        else
        {
            System.out.println("Bundling Disposition field is  unlocked");
            scrollToElement(find(BundlingDespositionUnlock));
            click(find(BundlingDespositionUnlock));
        }
        sleep(30);
        dropDownValueSelection("POR:Bundling Disposition","Unbundled");
        click(find(applyButton));
        sleep(30);

        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        // return StructuralModificationDesignCompleteDate;
        return null;
        //Approved

    }
    public boolean buttonDisplayed() throws Exception {
        List<WebElement> buttons = findAll(By.xpath("//input[@id='btnAdd0']"));
        if (buttons.size() > 0 && buttons.get(0).isDisplayed())
        {
            return true;
        }
        return false;
    }

    public String switchToProjectPage(){
        parentWindow = switchToChildWindows();
        fullScreen();
        sleep(3);
        return parentWindow;
    }



    public ProjectNTPPage goToApprovalsTab() throws Exception {
        waitUntilVisibleElement(find(label_NTPApproval));
        scrollToElement(find(label_NTPApproval));
        sleep(3);
        click(find(label_NTPApproval));
        sleep(3);
        return new ProjectNTPPage(driver);
    }

    public ProjectNTPPage goToNTPTabs() throws Exception {
        waitUntilVisibleElement(find(applyButton));
        scrollToElement(find(label_NTPConstruction));
        sleep(8);
        click(find(label_NTPConstruction));
        return new ProjectNTPPage(driver);
    }

    public ProjectNTPPage goToNtp() throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(applyButton));
        sleep(5);
        click(find(ntpConstruction));
        return new ProjectNTPPage(driver);
    }

    public boolean ntpConstruction_Validations(String parentWindow) throws Exception {
        waitUntilVisibleElement(find(ntpConstruction));
        scrollToElement(find(ntpConstruction));
        sleep(5);
        Boolean tab = isDisplayed(ntpConstruction);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        return tab;
    }

    public boolean ntpApproval_Validations(String parentWindow) throws Exception {
        waitUntilVisibleElement(find(label_NTPApproval));
        scrollToElement(find(label_NTPApproval));
        sleep(5);
        Boolean tab = isDisplayed(label_NTPApproval);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        return tab;
    }

    public boolean isNtpApprovalTabPresent() throws Exception {
        sleep(5);
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        //  scrollToElement(find(label_NTPApproval));
        if(findAll(label_NTPApproval).size()>0){
            return true;
        }else {
            return false;
        }
    }

    public boolean isNtpConstructionTabPresent() throws Exception {
        sleep(5);
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        //  scrollToElement(find(label_NTPApproval));
        if(findAll(label_NTPConstruction).size()>0){
            return true;
        }else {
            return false;
        }
    }

//     public void waitForPageToLoad(){
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
//
//    }
//    public void wait(int time){
//        try {
//            Thread.sleep(time*1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public String searchForImgInGrid(String columnName, String searchByColumn, String searchValue) throws Exception {
        fullScreen();
        int columnToFind = getTableData(columnName);
        int columnValueToMatch = getTableData(searchByColumn);
        List<WebElement> tableContents = findAll(tableData);
        if (columnValueToMatch != -1 && columnToFind != -1) {

            for (int row = 1; row < tableContents.size(); row++) {
                List<WebElement> cellDataForTheRow = findAll(By.tagName("td"), tableContents.get(row));
                scrollToElement(cellDataForTheRow.get(1));
                if (getText(cellDataForTheRow.get(columnValueToMatch)).contains(searchValue)) {
                    sleep(1);
                    scrollToElement(cellDataForTheRow.get(columnToFind));
                    List<WebElement> img = findAll(By.tagName("img"), cellDataForTheRow.get(columnToFind));
                    // String cellValue = getText(cellDataForTheRow.get(columnToFind));

                    if (img.size() > 0) {
                        return img.get(0).getAttribute("src");
                    }
                    return "FAILED";
                }
            }
        }
        return "ERROR OCCURRED";

    }

    public boolean isPopALert(String parent) throws Exception {
        click(find(applyButton));
        sleep(10);
        if(!isAlertPresent()){
            click(find(okButton));
            sleep(5);
            switchToSpecificWindow(parent);
            return false;
        }
        else{
            acceptAlert();
            click(find(cancelButton));
            if(!isAlertPresent())
            {
                //acceptAlert();
                switchToSpecificWindow(parent);
            }
            else {
                acceptAlert();
                switchToSpecificWindow(parent);
            }
            return true;
        }
    }
    public boolean verifyVendorIsEnabled() throws Exception{
        WebElement element = find(VendorField);
        scrollToElement(find(VendorField));
        sleep(5);
        boolean vendor = element.isEnabled();
        return vendor ;
    }

    public void selectVendor(String vendorName) throws Exception {
        sleep(5);
        scrollToElement(inputBoxDataBySname("PJ:Vendor"));
        dropDownDotsClick("PJ:Vendor");
        String parent2 = switchToChildWindows();
        waitUntilVisibleElement(find(VendorSelection));
        fullScreenChildWindow();
        waitUntilVisibleElement(find(VendorSelectionInputBox));
        setText(find(VendorSelectionInputBox),vendorName);
        click(find(VendorSearchButton));
        tableRadioButtonSelectionWithExactValue("V:Vendor",vendorName);
        click(find(VendorSelection));
        sleep(5);
        switchToSpecificWindow(parent2);
    }
    public void gotoVendorTab() throws Exception {
        waitUntilVisibleElement(find(applyButton));
        scrollToElement(find(VendorInfoTab));
        click(find(VendorInfoTab));
        sleep(10);
        waitUntilVisibleElement(find(applyButton));
    }
    public void changeStatus(String statusName, String visibleText, int index) throws Exception {
        WebElement status = selectionBoxBySname(statusName).get(index);
        scrollToElement(status);
        waitUntilVisibleElement(status);
        selectDropdownOption(status,visibleText);
        waitUntilVisibleElement(status);
        sleep(2);
        waitUntilVisibleElement(find(applyButton));
        click((find(applyButton)));
        waitUntilVisibleElement(find(okButton));
        sleep(15);
        //System.out.println(getStatusValue(statusName,index));
    }

    public String getStatusValue(String statusName,int index) throws Exception {
        WebElement status = selectionBoxBySname(statusName).get(index);
        scrollToElement(status);
        waitUntilVisibleElement(status);
        return  getFirstSelectedOptionInDropdown(status);
    }
    public void goToView(String viewName) throws Exception{
        sleep(5);
        waitUntilVisibleElement(find(editProject));
        waitUntilVisibleElement(find(ClickView));
        click(find(ClickView));
        find(SearchFilter).clear();
        find(SearchFilter).sendKeys(viewName);
        waitUntilVisibleElement(selectView(viewName));
        click(selectView(viewName));
        waitUntilVisibleElement(find(ApplyFilter));
        scrollToElement(find(ApplyFilter));
        click(find(ApplyFilter));
        waitUntilVisibleElement(find(editProject));

    }
    public WebElement selectView(String viewName) throws Exception{
        By View = By.xpath("//div[contains(text(),'"+viewName+"')]");
        return find(View);

    }
    public void viewOk() throws Exception{
        sleep(5);
        waitUntilVisibleElement(find(okButton));
        find(okButton).click();
        sleep(10);
    }
    public void vendorCancel() throws Exception{
        waitUntilVisibleElement(find(cancelButton));
        find(cancelButton).click();
        sleep(5);
    }
    public void searchForValueVendor(String data, String type) throws Exception {
        fullScreen();
        waitUntilVisibleElement(find(SiteCode));
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitUntilVisibleElement(find(SiteCode));
        sleep(10);
    }
    public WebElement statusGrid(int index) throws Exception{
        return findAll(StatusFind).get(index);
    }
    public void modifyStatusFromGrid(String visibleText, int index) throws Exception {
        click(statusGrid(index));
        WebElement statusDropDown = find(SelectStatus);
        scrollToElement(statusDropDown);
        waitUntilVisibleElement(statusDropDown);
        selectDropdownOption(statusDropDown,visibleText);
        waitUntilVisibleElement(statusDropDown);
        sleep(2);
        click(find(SiteCode));
        waitUntilVisibleElement(find(SaveChangesButton));
        click((find(SaveChangesButton)));
        sleep(10);
    }

    public String AEStructuralModificationEnableDate() throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        scrollToElement(find(AETab));
        click(find(AETab));
        sleep(10);
        waitUntilVisibleElement(clipButtonBySname("PJ:Structural Modification Design Complete (1375)[Doc]"));
        sleep(2);
        scrollToElement(find(ClickStructuralModification));
        click(find(ClickStructuralModification));
        click(find(SelectNaTask));
        sleep(2);
        click(find(applyButton));
        sleep(10);
        WebElement status = selectionBoxBySname("PJ:Structural Modification Design Complete [Status]").get(0);
        scrollToElement(status);
        String StructuralModificationValue=getFirstSelectedOptionInDropdown(status);
        System.out.println("___"+StructuralModificationValue);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        return StructuralModificationValue;
    }
}
