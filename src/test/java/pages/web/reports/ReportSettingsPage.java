package pages.web.reports;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import utility.helper.MiscHelpers;

import java.util.List;

public class ReportSettingsPage extends BasePage {

    public WebDriver driver;
    public By tbGridLink = By.xpath("//a[text()='TB Grid with Submit']");

    public By okButton = By.xpath("//*[@id='btnOK']");
    public By tbGridWorksheets = By.xpath("//div[@title='TB Grid Worksheets']");
    public By general = By.xpath("//div[@title='General']");

    public By reportDeliveryOk = By.xpath("//input[@id='btnOK0']");

    public By okButton1 =By.xpath("//input[@id='btnOK0']");

    public By trackorType = By.xpath("//select[@id='trackorType_1002226']");

    public By viewName = By.xpath("//select[@id='viewName_1002226']");

    public By filterName = By.xpath("//select[@id='filterName_1002226']");

    public By emailUncheck = By.xpath("//*[@id='lblcb2']");
    public By emailLink = By.xpath("//*[@id='lblcb3']");

    public By fileCheck = By.xpath("//*[@id='lblcb1']");
    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By  searchButton = By.xpath("//input[@id='btnSearch0']");
    public By  editProject  = By.xpath("//input[@id='btnEdit0']");
    public By  searchTypeDropdown  = By.xpath("//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*");
    public By  selectedOption  = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public String  topDivSearchTypeDropdown = "//div[@class='component item_select'][normalize-space()='textName']";
    public By reportLink = By.xpath("//*[@id='group_event_3998285']/div[1]/div[1]/a");
    public By generalTab = By.xpath("//*[@id='tab1']");
    public By reportId= By.xpath("//*[@id='divPage1']/table/tbody/tr[3]/td[2]/a");
    public By specificTimeRadio = By.xpath("//*[@id='runTime']");
    public By mainLogo1 = By.xpath("//*[@id='mainLogo']");
    public By Hours = By.xpath("//*[@id='hours']");
    public By Minutes = By.xpath("//*[@id='minutes']");
    public By delivery = By.xpath("//tbody/tr/td[2]/div/input[@id='btnreportDelivery']");
    public By runHistory =By.xpath("//div[@title='Report History']");
    //public By marketButton = By.xpath("//tbody/tr/td/div/div[2]/input[@id='btnCrParam_10013219']");
    public By marketButton = By.xpath("(//td[contains(text(),'Select Market(s)')]//following::td//following::div//input)[2]");
    public  By projectStatus = By.xpath("//tbody/tr/td/div/div[2]/input[@id='btnCrParam_10013610']");
    public By checkAll = By.xpath("//div[@title='Select all records on this page']");
    public By unCheckAll = By.xpath("//div[@title='Deselect all records on this page']");
    public By tableHeader = By.xpath("//table[@class='hdr']//tr//td");
    public By tableData = By.xpath("//table[@class='obj']//tr");
    public By cancelButton = By.xpath("//input[@id='btnCancel']");
    public By radioBtn = By.xpath("//input[@value='Now']");
    public By specificRadioBtn = By.xpath("//table//label[text()='At a specific time ']//ancestor::td//input[2]");
    public By checkBox = By.xpath("//tbody/tr[3]/td[1]/label");
    public By deleteBtn = By.xpath("//input[@id='btnDelete0']");
    public By comments = By.xpath("//textarea[@id='comments']");
    public By sortButton = By.xpath("//*[@id='gridbox0']/div[1]/table/tbody/tr[2]/td[2]/div/div/div[2]");
    public By descButton = By.xpath("//*[@id='contextMenu']/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div");
    public By runOnce = By.xpath("//*[@id='reportRefreshFrequencyId']");
    String parentWindow;
    public ReportSettingsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    public RunReportsPage addNewReport( ) throws Exception {
        sleep(10);
        String parent1 = switchToChildWindows();
        fullScreen();
        //waitUntilVisibleElement(find(generalInfo));
        fullScreenChildWindow();
        sleep(10);
        quickClick(find(tbGridWorksheets));
        selectDropdownOption(find(trackorType),"Project Tracker");
        selectDropdownOption(find(viewName),"G: Project Milestone");
        selectDropdownOption(find(filterName),"G: Small Cell");
        find(general).click();
        dropDownDotsClick("Delivery");
        String parent2 = switchToChildWindows();
        waitUntilVisibleElement(find(reportDeliveryOk));
        fullScreenChildWindow();
        if(find(emailUncheck).isSelected())
            find(emailUncheck).click();
        find(fileCheck).click();
        sleep(3);
        find(fileCheck).click();
        waitUntilVisibleElement(find(reportDeliveryOk));
        click(find(reportDeliveryOk));
        switchToSpecificWindow(parent2);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        return new RunReportsPage(driver);
    }

    public RunReportsPage addReport() throws Exception{
        sleep(10);
        String parent1 = switchToChildWindows();
        waitUntilVisibleElement(find(okButton));
        fullScreen();
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        return new RunReportsPage(driver);
    }

    public RunReportsPage setMandatoryParameters() throws Exception {
        sleep(5);
        String parent0 =  switchToChildWindows();
        sleep(4);
        fullScreenChildWindow();
        sleep(10);
        click(find(marketButton));
        String parent = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(reportDeliveryOk));
        searchForValue("SAN FRANCISCO", "Market");
        click(find(checkAll));
        sleep(2);
        click(find(okButton1));
        switchToSpecificWindow(parent);
        click(find(projectStatus));
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        searchForValue("Completed", "PJ: Project Status");
        click(find(checkAll));
        sleep(5);
        click(find(okButton1));
        switchToSpecificWindow(parent1);
//        String date =   MiscHelpers.currentDateTime("MM/dd/yyyy");
//        System.out.println("Date is - " + date);
//        click(find(specificTimeRadio));
//        WebElement hours = find(Hours);
//        selectDropdownOptionByIndex(hours,19);
     /*   click(find(delivery));
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(3);
        WebElement element = find(fileCheck);
       if (element.isSelected() == true) {
           find(fileCheck).click();
       }
       else if (element.isSelected() == false){
           find(fileCheck).click();
       }*/
//        sleep(5);
//        click(find(okButton1));
        // sleep(2);
        //  switchToSpecificWindow(parent2);
        // inputBoxDataBySname("File NameHeader").sendKeys("Deployment Report");
        sleep(4);
        click(find(okButton));
        sleep(4);
        switchToSpecificWindow(parent0);
        return new RunReportsPage(driver);
    }
    public RunReportsPage setMandatoryParameter_Market() throws Exception {
        sleep(5);
        parentWindow = switchToChildWindows();
        sleep(4);
        fullScreenChildWindow();
        sleep(10);
        click(find(marketButton));
        String parent = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(reportDeliveryOk));
        searchForValue("SAN FRANCISCO", "Market");
        click(find(checkAll));
        sleep(2);
        click(find(okButton1));
        switchToSpecificWindow(parent);
        click(find(projectStatus));
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        searchForValue("Completed", "PJ: Project Status");
        click(find(checkAll));
        sleep(5);
        click(find(unCheckAll));
        click(find(okButton1));
        switchToSpecificWindow(parent1);
        sleep(4);
        click(find(okButton));
        sleep(4);
        acceptAlert();
        click(find(cancelButton));
        switchToSpecificWindow(parentWindow);
        return new RunReportsPage(driver);
    }

    public void searchForValue(String data, String type) throws Exception
    {
        fullScreen();
        waitForPageToLoad();
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitForPageToLoad();
        sleep(5);
    }


    public void selectSearchType(String type) throws Exception {
        sleep(5);
        WebElement searchDropdown = find(searchTypeDropdown);
        click(searchDropdown);
        System.out.println("TB" + searchDropdown);
        if (!find(selectedOption).getText().equals(type)) {
            dropDownButtons(topDivSearchTypeDropdown, type);
        }
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
    public int getTableData(String columnName) throws Exception {
        List<WebElement> tableHeaders = findAll(tableHeader);
        scrollToElement(tableHeaders.get(1));
        for(int i = 0; i<tableHeaders.size()-1; i++){
            scrollToElement(tableHeaders.get(i+1));
            if(getText(find(By.className("hdr_label"),tableHeaders.get(i))).contains(columnName))
                return i;
        }
        return -1;
    }

    public RunReportsPage generateReportDeliveryAsFile() throws Exception {
        sleep(5);
        String parent1 = switchToChildWindows();
        fullScreen();
        sleep(2);
//        quickClick(find(tbGridWorksheets));
//        selectDropdownOption(find(trackorType),"Project Tracker");
//        selectDropdownOption(find(viewName),"G: Project Milestone");
//        selectDropdownOption(find(filterName),"G: Small Cell");
//        find(general).click();
        dropDownDotsClick("Delivery");
        String parent2 = switchToChildWindows();
        WebElement fileCheck1 = find(fileCheck);
        // checkBoxCheckByJS(fileCheck1);
        fileCheck1.click();
        waitUntilVisibleElement(find(reportDeliveryOk));
        fullScreenChildWindow();
        sleep(3);
//        if(find(emailUncheck).isSelected())
//            find(emailUncheck).click();
        //checkBoxByLabel("lblcb0_0").click();
        boolean checkbox = isCheckboxSelected("cb1");
        System.out.println("checkbox status - " + checkbox);
        if ((isCheckboxSelected("cb1")) == false){
            click(find(fileCheck));
        }
        waitUntilVisibleElement(find(reportDeliveryOk));
        click(find(reportDeliveryOk));
        switchToSpecificWindow(parent2);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        return new RunReportsPage(driver);
    }

    public RunReportsPage generateReportDeliveryAsEmail() throws Exception {
        sleep(5);
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(5);
        dropDownDotsClick("Delivery");
        String parent2 = switchToChildWindows();
        waitUntilVisibleElement(find(reportDeliveryOk));
        fullScreenChildWindow();
//        if ((isCheckboxSelected("cb1") == true) && (isCheckboxSelected("cb2") == true)){
//            click(find(fileCheck));
//            sleep(3);
//        } else if ((isCheckboxSelected("cb1") == false) && (isCheckboxSelected("cb2") == false)){
//            click(find(emailUncheck));
//            sleep(3);
//        }
        click(find(emailUncheck));
        waitUntilVisibleElement(find(reportDeliveryOk));
        click(find(reportDeliveryOk));
        switchToSpecificWindow(parent2);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        return new RunReportsPage(driver);
    }

    public RunReportsPage generateReportDeliveryAsEmailWithLink() throws Exception {
        sleep(5);
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        sleep(5);
        dropDownDotsClick("Delivery");
        String parent2 = switchToChildWindows();
        sleep(5);
//        waitUntilVisibleElement(find(reportDeliveryOk));
        fullScreenChildWindow();
        if ((isCheckboxSelected("cb1")) && (isCheckboxSelected("cb2")) && (isCheckboxSelected("cb3"))){
            WebElement fileCheck1 = find(fileCheck);
            // checkBoxCheckByJS(fileCheck1);
            fileCheck1.click();
            sleep(5);
            // WebElement emailCheck1 = find(emailUncheck);
            // checkBoxCheckByJS(emailCheck1);
            //  emailCheck1.click();
            //  sleep(4);
        } else if ((!isCheckboxSelected("cb1")) && (!isCheckboxSelected("cb2")) && (!isCheckboxSelected("cb3"))){
            WebElement emailLink1 = find(emailLink);
            emailLink1.click();
            // checkBoxCheckByJS(emailLink1);
            sleep(3);
        }
        waitUntilVisibleElement(find(reportDeliveryOk));
        click(find(reportDeliveryOk));
        switchToSpecificWindow(parent2);
        click(find(okButton));
        switchToSpecificWindow(parent1);
        sleep(5);
        return new RunReportsPage(driver);
    }




    public boolean isValuePresentInGrid(String columnName) throws Exception {
        waitForPageToLoad();
        int columnToFind = getTableData(columnName);
        List<WebElement> tableContents = findAll(tableData);
        List<WebElement> cellDataForTheFirstRow = findAll(By.tagName("td"),tableContents.get(1));
        String cellData = getText(cellDataForTheFirstRow.get(columnToFind));
        return !cellData.isEmpty() && !cellData.equals(null) && !cellData.equals(" ");
    }


    public RunReportsPage selectDeliveryFile() throws Exception {
        sleep(5);
        String parent = switchToChildWindows();
        sleep(4);
        fullScreenChildWindow();
        sleep(10);
        dropDownDotsClick("Delivery");
        String parent1 = switchToChildWindows();
        waitUntilVisibleElement(find(reportDeliveryOk));
        fullScreenChildWindow();
        find(fileCheck).click();
        sleep(5);
        find(fileCheck).click();
        click(find(reportDeliveryOk));
        switchToSpecificWindow(parent1);
        click(find(okButton));
        sleep(5);
        switchToSpecificWindow(parent);
        return new RunReportsPage(driver);
    }
    public RunReportsPage selectDeliveryEmail() throws Exception {
        sleep(5);
        String parent = switchToChildWindows();
        sleep(4);
        fullScreenChildWindow();
        sleep(10);
        dropDownDotsClick("Delivery");
        String parent1 = switchToChildWindows();
        waitUntilVisibleElement(find(reportDeliveryOk));
        fullScreenChildWindow();
        find(emailLink).click();
        click(find(reportDeliveryOk));
        switchToSpecificWindow(parent1);
        click(find(okButton));
        sleep(5);
        switchToSpecificWindow(parent);
        return new RunReportsPage(driver);
    }
    public RunReportsPage generateReportForCurrentTime() throws Exception {
        sleep(5);
        String parent = switchToChildWindows();
        sleep(4);
        fullScreen();
        sleep(5);
        fullScreenChildWindow();
        //  sleep(5);
        //find(comments).sendKeys("Generating Report for current time");
        sleep(10);
        waitUntilVisibleElement(find(okButton));
        WebElement button = find(okButton);
        button.click();
        sleep(10);
        switchToSpecificWindow(parent);
        return new RunReportsPage(driver);
    }
    public RunReportsPage verifyRunHistory(String report) throws Exception {
        sleep(3);
        searchForValue(report,"Report Name");
        sleep(3);
        click(find(runHistory));
        switchToChildWindows();
        sleep(5);
        fullScreenChildWindow();
        sleep(2);
        WebElement icon = find(sortButton);
        icon.click();
        find(descButton).click();
        sleep(5);
        return new RunReportsPage(driver);
    }
    public RunReportsPage generateReportForSpecificTime() throws Exception {
        sleep(5);
        String parent = switchToChildWindows();
        sleep(4);
        fullScreen();
        sleep(5);
        fullScreenChildWindow();
        sleep(10);
        waitUntilVisibleElement(find(okButton));
        click(find(specificRadioBtn));
        sleep(6);
        WebElement option = find(runOnce);
        selectDropdownOptionByIndex(option,3);
        WebElement hours = find(Hours);
        selectDropdownOptionByIndex(hours,22);
        WebElement minutes = find(Minutes);
        selectDropdownOptionByIndex(minutes,0);
        sleep(4);
        click(find(okButton));
        sleep(2);
        switchToSpecificWindow(parent);
        return new RunReportsPage(driver);
    }

}
    /*public String clickReport() throws Exception {
        waitUntilVisibleElement(find(reportLink));
        click(find(reportLink));
        sleep(10);
        String parent = switchToChildWindows();
        waitUntilVisibleElement(find(generalTab));
        String ReportId = find(reportId).getText();
        click(find(okButton));
        switchToSpecificWindow(parent);
        return ReportId;
    }*/


