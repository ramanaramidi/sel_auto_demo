package pages.web.reports;

import org.openqa.selenium.*;
import pages.BasePage;
import utility.helper.MiscHelpers;

import java.util.List;

public class RunReportsPage extends BasePage {

    public By bellIcon = By.xpath("//input[@id='topPanelBtnEvents']");
    public By reportId= By.xpath("//*[@id='divPage1']/table/tbody/tr[3]/td[2]");

    public By okButton = By.xpath("//input[@id='btnOK']");

    public WebDriver driver;

    public RunReportsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    public ReportSettingsPage goToReportSettingsPage(String report) throws Exception {
        clickTableLinkByText(report);
        return new ReportSettingsPage(driver);
    }

    public boolean verifyHeaderItemInNotification(String type,String name) throws Exception {
        List<WebElement> element = headerItemVerification(type,name);
        if(element.size()>0)
            return true;
        return false;
    }

    public boolean verifyBtDateAndStatus(String status,String type,String name) throws Exception {
        String date = MiscHelpers.currentDateTime("10/17/2022");
        List<WebElement> element = headerItemRootNotification(type,name);
        System.out.println("root size-"+element.size());
        List<WebElement> dataElements = dataItemVerification(date,status,element.get(0));
        System.out.println(" size-"+dataElements.size());
        if(dataElements.size()>0)
            return true;
        return false;
    }

    public Boolean verifyReportStatus(String status)throws Exception {
        String date = MiscHelpers.currentDateTime("MM/dd/yyyy");
        List<WebElement> panelData =  notificationPanelData(date,status);
        System.out.println("panelData size-"+panelData.size());
        sleep(10);
        if(panelData.size()>0){
            return  true;
        }
        return false;
    }

    public void clickBellIcon() throws Exception{
        waitUntilVisibleElement(find(bellIcon));
        click(find(bellIcon));
        sleep(5);

    }

    private void reportsList (String Report) throws Exception{
        List<WebElement> reportSelected = notificationPanelReportLink(Report);
        System.out.println("Report List size is- " +reportSelected.size());
        for (int i=0; i<reportSelected.size();i++){
            sleep(20);
            reportSelected = notificationPanelReportLink(Report);}
    }

    public String selectReport(String Report) throws Exception {
        List<WebElement> reportSelected = notificationPanelReportLink(Report);
        System.out.println("Report List size is- " +reportSelected.size());
        if(reportSelected.size()<=0) {
            reportsList(Report);
        }
        if(reportSelected.size()>0){
            scrollToElement(reportSelected.get(0));
            click(reportSelected.get(0));
            sleep(10);
        }
        String parent = switchToChildWindows();
        String ReportId = find(reportId).getText();
        System.out.println("Report ID is " + ReportId);
        sleep(5);
        click(find(okButton));
        switchToSpecificWindow(parent);
        return ReportId;
    }
}
