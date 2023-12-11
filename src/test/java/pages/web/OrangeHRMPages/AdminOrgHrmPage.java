package pages.web.OrangeHRMPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

public class AdminOrgHrmPage extends BasePage {

    public WebDriver driver;

    public AdminOrgHrmPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public By userName = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");
    public By userRole = By.xpath("(//div[@class='oxd-select-text-input'])[1]");
    public By employeeName = By.xpath("(//input[@placeholder='Type for hints...'])");
    public By Status = By.xpath("(//div[contains(text(),'-- Select --')])");
    public By pimTab = By.linkText("PIM");
    public By empName = By.xpath("(//input[@placeholder='Type for hints...'])[1]");
    public By employeeId = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");
    public By employmentStatus = By.xpath("(//div[text()='-- Select --'])[1]");
    public By include = By.xpath("(//div[contains(text(),'Current Employees Only')])[1]");
    public By supervisorName = By.xpath("(//input[@placeholder='Type for hints...'])[2]");
    public By jobTitle = By.xpath("(//div[contains(text(),'-- Select --')])[1]");
    public By timeTab = By.linkText("Time");
    public By employeeName1 = By.xpath("(//input[@placeholder='Type for hints...'])[1]");
    public By view = By.xpath("//button[@type='submit']");
    public By search = By.xpath("//button[text()=' Search ']");
    public By bin = By.xpath("//i[@class = 'oxd-icon bi-trash']");
    public By job = By.xpath("//span[text()='Job ']");
    public By jobTitles = By.xpath("//a[text()='Job Titles']");
    public By payGrades = By.xpath("//a[text()='Pay Grades']");
    public By employmentstatus1 = By.xpath("//a[text()='Employment Status']");
    public By jobCategories = By.xpath("//a[text()='Job Categories']");
    public By workShifts = By.xpath("//a[text()='Work Shifts']");
    public By add = By.xpath("//button[text() = ' Add ']");
    public By save = By.xpath("//button[text() =' Save ']");
    public By firstName = By.name("firstName");
    public By lastName = By.name("lastName");

    public boolean isUserNameEnable() throws Exception {
        sleep(5);

        waitUntilVisibleElement(find(userName));

        System.out.println(getTitle());

        WebElement userNameField = find(userName);

        if (userNameField.isEnabled()) {

            return true;

        } else {
            return false;
        }
    }

    public void UserRoleFiled() throws Exception {
        sleep(5);
        waitUntilVisibleElement(find(userRole));
        WebElement userRoleField = find(userRole);
        userRoleField.sendKeys("ESS");
        /*
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].value='Admin';",userRoleField);
         */
        sleep(5);
    }

    public Boolean isEmployeeNameEnable() throws Exception {

        waitUntilVisibleElement(find(employeeName));

        WebElement EmployeeName = find(employeeName);

        if (EmployeeName.isEnabled()) {

            return true;

        } else {
            return false;
        }
    }

    public void StatusField() throws Exception {
        sleep(5);
        waitUntilVisibleElement(find(Status));
        WebElement userRoleField = find(Status);
        userRoleField.sendKeys("Enabled");
        sleep(5);
    }

    public Boolean VerifyPimTab() throws Exception {
        sleep(5);


        waitUntilVisibleElement(find(pimTab));

        WebElement PIMTab = find(pimTab);

        if (PIMTab.isEnabled()) {

            return true;

        } else {
            return false;
        }
    }


    public Boolean isEmpName1Enable() throws Exception {

        waitUntilVisibleElement(find(employeeName));

        WebElement EmployeeName1 = find(employeeName);

        if (EmployeeName1.isEnabled()) {
            return true;

        } else {
            return false;
        }
    }

    public Boolean isEmployeeIdEnable() throws Exception {

        waitUntilVisibleElement(find(employeeId));

        WebElement EmployeeId = find(employeeId);

        if (EmployeeId.isEnabled()) {

            return true;

        } else {
            return false;
        }
    }

    public Boolean EmploymentStatus() throws Exception {

        waitUntilVisibleElement(find(employmentStatus));
        WebElement EmploymentStatus = find(employmentStatus);
        EmploymentStatus.sendKeys("Full-Time Permanent");
        return true;
    }

    public Boolean Include() throws Exception {
        sleep(5);
        waitUntilVisibleElement(find(include));
        WebElement Include = find(include);
        Include.sendKeys("Current and Past Employees");
        return true;
    }

    public Boolean isSupervisorNameEnable() throws Exception {
        waitUntilVisibleElement(find(supervisorName));

        WebElement SuperiorName = find(supervisorName);

        if (SuperiorName.isEnabled()) {

            return true;

        } else {
            return false;
        }
    }

    public Boolean JobTitle() throws Exception
    {
        sleep(5);
        waitUntilVisibleElement(find(jobTitle));
        WebElement JobTitle = find(jobTitle);
        JobTitle.sendKeys("QA Engineer");
        return true;
    }

    public Boolean VerifyTimeTab() throws Exception {
        sleep(5);


        waitUntilVisibleElement(find(timeTab));

        WebElement TimeTab = find(timeTab);

        if (TimeTab.isEnabled()) {

            return true;

        } else {
            return false;
        }
    }

    public Boolean isEmployeename1Enable() throws Exception {

        waitUntilVisibleElement(find(employeeName1));

        WebElement EmployeeName1 = find(employeeName1);
        EmployeeName1.sendKeys("John ronaldo Smith");

        if (EmployeeName1.isEnabled()) {

            return true;

        } else {
            return false;
        }
    }

    public Boolean VerifyView() throws Exception {

        waitUntilVisibleElement(find(view));

        WebElement View = find(view);
        find(view).click();
        return true;
    }
    public Boolean VerifyPimTab1() throws Exception {
        sleep(5);
        waitUntilVisibleElement(find(pimTab));
        find(pimTab).click();
        find(employeeName1).sendKeys("Alamanda Cathartica Florencea");
        find(search).click();
        find(bin).click();
        return true;
    }
    public Boolean VerifyAdminTab1() throws Exception {
        sleep(5);
        waitUntilVisibleElement(find(job));
        find(job).click();
        find(jobTitles).isDisplayed();
        find(payGrades).isDisplayed();
        find(employmentstatus1).isDisplayed();
        find(jobCategories).isDisplayed();
        find(workShifts).isDisplayed();
        return true;
    }
    public Boolean VerifyPimTab2() throws Exception {
        sleep(5);
        waitUntilVisibleElement(find(add));
        find(add).click();
        find(save).click();
        return true;
    }
    public Boolean VerifyPimTab3() throws Exception {
        sleep(5);
        waitUntilVisibleElement(find(pimTab));
        find(pimTab).click();
        find(add).click();
        find(firstName).sendKeys("hari chandana");
        find(lastName).sendKeys("A");
        find(save).click();
        find(pimTab).click();
        find(empName).sendKeys("hari chandana");
        find(search).click();
        return true;
    }
}
