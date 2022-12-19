package pages.web.Tracker;


import commons.objects.Users;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import utility.helper.MiscHelpers;

public class AddProjectPage extends BasePage {
    public WebDriver driver;
    String parentWindow = "";

    public AddProjectPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    public By applyButton  = By.xpath("//input[@id='btnApply']");
    public By projectCommentArea = By.id("idx78");
    public By okButton  = By.xpath("//input[@id='btnOK']");
    public By  editProject  = By.xpath("//input[@id='btnEdit0']");

    public ProjectTrackerPage updateExistingProjectTracker(String Comment) throws Exception {
        updatingExistingProjectTracker(Comment);
        return new ProjectTrackerPage(driver);
    }

    private void updatingExistingProjectTracker(String comment) throws Exception {
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(applyButton));
        scrollToElement(find(projectCommentArea));
        setText(find(projectCommentArea),comment);
        click(find(applyButton));
        parentWindow = parent1;
    }

    public boolean verifyUpdatedByAndDateDetails(Users user) throws Exception {
        waitUntilVisibleElement(find(applyButton));
        waitUntilVisibleElement(find(applyButton));
        click(find(applyButton));
        waitUntilVisibleElement(find(applyButton));
        WebElement modifiedBy = inputBoxDataBySname("PJ:Modified By");
        scrollToElement(modifiedBy);
        String modifiedUser = modifiedBy.getAttribute("origval");
        System.out.println("user::"+modifiedUser);
        String modifiedDatePath = inputBoxXpathBySname("PJ:Modified Date");
        String updatedDate = getDocumentTextByXpathJs(modifiedDatePath);
        System.out.println("date::"+updatedDate);
        if(modifiedUser.contains(user.getNtCode()) && updatedDate.contains(MiscHelpers.currentDateTime("MM/dd/yyyy"))){

            return true;
        }
        return false;
    }

    public ProjectTrackerPage returnToProjectTracker() throws Exception {
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        waitUntilVisibleElement(find(editProject));
        return new ProjectTrackerPage(driver);
    }

}

