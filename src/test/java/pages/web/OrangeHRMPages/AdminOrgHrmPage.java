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
    public By adminTab = By.linkText("Admin");

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



    public By status = By.xpath("(//div[@class ='oxd-select-text-input'])[2]");
    //public By employeeName1 = By.xpath("//input[@placeholder='Type for hints...'][1]");
    //public By employeeId = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");
    //public By employmentStatus = By.xpath("(//div[@class='oxd-select-text-input'])[1]");
    //public By include = By.xpath("(//div[@class='oxd-select-text-input'])[2]");
    //public By supervisorName = By.xpath("(//input[@placeholder='Type for hints...'])[2]");
    //public By jobTitle = By.xpath("(//div[@class='oxd-select-text-input'])[3]");
    public By subUnit = By.xpath("(//div[@class='oxd-select-text-input'])[4]");
    public By OrangeHRM = By.xpath("//title");
    //public By pimTab = By.linkText("PIM");
    public By leaveTab = By.linkText("Leave");
    //public By timeTab = By.linkText("Time");
    public By recruitmentTab = By.linkText("Recruitment");
    public By myInfoTab = By.linkText("My Info");
    public By performanceTab = By.linkText("Performance");
    public By dashBoardTab = By.linkText("Dashboard");
    public By directoryTab = By.linkText("Directory");
    public By maintenanceTab = By.linkText("Maintenance");
    public By claimTab = By.linkText("Claim");
    public By buzzTab = By.linkText("Buzz");
    public By addButton = By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--secondary']");
    //public By firstName = By.xpath("//input[@name='firstName']");
    //public By lastName = By.xpath("//input[@name='lastName']");
    //public By save = By.xpath("//button[@type='submit']");
    //public By search = By.xpath("//button[@type='submit']");
    public By timeTabEmployee = By.xpath("//input[@placeholder='Type for hints...']");
    //public By view = By.xpath("//button[@type='submit']");
    //public By bin = By.xpath("//i[@class='oxd-icon bi-trash']");
    public By yesDelete = By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--label-danger orangehrm-button-margin']");
    //public By job = By.xpath("(//span[@class = 'oxd-topbar-body-nav-tab-item'])[2]");
    //public By jobTitles =  By.xpath("//a[text() ='Job Titles']");
    //public By payGrades =  By.xpath("//a[text() ='Pay Grades']");
    public By employmentStatus1 =  By.xpath("//a[text() ='Employment Status']");
    //public By jobCategories =  By.xpath("//a[text() ='Job Categories']");
    //public By workShifts =  By.xpath("//a[text() ='Work Shifts']");
    public By personalDetails = By.xpath("//h6[text() ='Personal Details']");
    public By personalRecord = By.xpath("(//span[@class='oxd-text oxd-text--span'])[1]");

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

    /*
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
    */

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






    public Boolean verifyPageTitle() throws Exception
    {
        sleep(2);
        waitUntilVisibleElement(find(OrangeHRM));

        WebElement PageTitle = find(OrangeHRM);

        String expectedTitle = "OrangeHRM";

        if (getTitle().equals(expectedTitle))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean VerifyPimTab() throws Exception
    {
        sleep(5);
        waitUntilVisibleElement(find(pimTab));
        WebElement PimTab = find(pimTab);
        if (PimTab.isDisplayed())
        {
            return true;
        } else
        {
            return false;
        }
    }
    public Boolean VerifyLeaveTab() throws Exception
    {
        sleep(2);
        waitUntilVisibleElement(find(leaveTab));
        WebElement LeaveTab = find(leaveTab);
        if (LeaveTab.isDisplayed())
        {
            return true;
        } else
        {
            return false;
        }
    }
    public Boolean VerifyRecruitmentTab() throws Exception
    {
        sleep(2);
        waitUntilVisibleElement(find(recruitmentTab));
        WebElement RecruitmentTab = find(recruitmentTab);
        if (RecruitmentTab.isDisplayed())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean VerifyMyInfoTab() throws Exception
    {
        sleep(2);
        waitUntilVisibleElement(find(myInfoTab));
        WebElement MyInfoTab = find(myInfoTab);
        if (MyInfoTab.isDisplayed())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean VerifyPerformanceTab() throws Exception
    {
        sleep(2);
        waitUntilVisibleElement(find(performanceTab));
        WebElement PerformanceTab = find(performanceTab);
        if (PerformanceTab.isDisplayed())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean VerifyDashBoardTab() throws Exception
    {
        sleep(2);
        waitUntilVisibleElement(find(dashBoardTab));
        WebElement DashBoardTab = find(dashBoardTab);
        if(DashBoardTab.isDisplayed())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean VerifyDirectoryTab() throws Exception
    {
        sleep(2);
        waitUntilVisibleElement(find(directoryTab));
        WebElement DirectoryTab = find(directoryTab);
        if(DirectoryTab.isDisplayed())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean VerifyMaintenanceTab() throws Exception
    {
        sleep(2);
        waitUntilVisibleElement(find(maintenanceTab));
        WebElement MaintenanceTab = find(maintenanceTab);
        if (MaintenanceTab.isDisplayed())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Boolean VerifyClaimTab() throws Exception
    {
        sleep(2);
        waitUntilVisibleElement(find(claimTab));
        WebElement ClaimTab = find(claimTab);
        if(ClaimTab.isDisplayed())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean VerifyBuzzTab() throws Exception
    {
        sleep(2);
        waitUntilVisibleElement(find(buzzTab));
        WebElement BuzzTab = find(buzzTab);
        if(BuzzTab.isDisplayed())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Boolean VerifyEmployeeName() throws Exception
    {
        sleep(2);
        waitUntilVisibleElement(find(employeeName));
        WebElement EmployeeName = find(employeeName);
        if(EmployeeName.isEnabled())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void Status() throws Exception
    {
        sleep(2);
        waitUntilVisibleElement(find(status));
        WebElement Status = find(status);
        Status.sendKeys("Enabled");
    }
    public Boolean VerifyEmployeeName1() throws Exception
    {
        sleep(2);
        waitUntilVisibleElement(find(employeeName1));
        WebElement EmployeeName1 = find(employeeName1);
        if(EmployeeName1.isEnabled())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean VerifyEmployeeId() throws Exception
    {
        waitUntilVisibleElement(find(employeeId));
        WebElement EmployeeId = find(employeeId);
        if (EmployeeId.isEnabled())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean VerifyEmploymentStatus() throws Exception
    {
        sleep(5);
        waitUntilVisibleElement(find(employmentStatus));
        WebElement EmploymentStatus = find(employmentStatus);
        if (EmploymentStatus.isEnabled())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean VerifyInclude() throws Exception
    {
        sleep(5);
        waitUntilVisibleElement(find(include));
        WebElement Include = find(include);
        if (Include.isEnabled())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean VerifySupervisorName() throws Exception
    {
        sleep(5);
        waitUntilVisibleElement(find(supervisorName));
        WebElement SupervisorName = find(supervisorName);
        if (SupervisorName.isEnabled())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean VerifyJobTitle() throws Exception
    {
        sleep(5);
        waitUntilVisibleElement(find(jobTitle));
        WebElement JobTitle = find(jobTitle);
        if (JobTitle.isEnabled())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean VerifySubUnit() throws Exception
    {
        sleep(5);
        waitUntilVisibleElement(find(subUnit));
        WebElement SubUnit = find(subUnit);
        if (SubUnit.isEnabled())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean AddNewEmployee() throws Exception
    {
        sleep(5);
        waitUntilVisibleElement(find(addButton));
        WebElement AddButton = find(addButton);
        click(find(addButton));
        waitUntilVisibleElement(find(firstName));
        find(firstName).sendKeys("Janvitha");
        find(lastName).sendKeys("Boddiboyina");
        click(find(save));
        sleep(5);
        return true;
    }
    public Boolean VerifyPersonalDetailPage() throws Exception
    {
        waitUntilVisibleElement(find(personalDetails));
        if(find(personalDetails).isDisplayed()) {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean VerifyPersonalRecord() throws Exception {
        find(pimTab).click();
        sleep(2);
        find(employeeName1).sendKeys("Janvitha Boddiboyina");
        find(search).click();
        sleep(5);
        waitUntilVisibleElement(find(personalRecord));
        if(find(personalRecord).isDisplayed())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    /*
    public Boolean VerifyTimeTab() throws Exception
    {
        waitUntilVisibleElement(find(timeTab));
        WebElement TimeTab = find(timeTab);
        click(find(timeTab));
        sleep(2);
        find(timeTabEmployee).sendKeys("Janvitha Boddiboyina");
        find(view).click();
        return true;
    }
     */
    public Boolean VerifyAdminTab() throws Exception
    {
        waitUntilVisibleElement(find(adminTab));
        find(adminTab).click();
        find(job).click();
        find(jobTitles).isDisplayed();
        find(payGrades).isDisplayed();
        find(employmentStatus1).isDisplayed();
        find(jobCategories).isDisplayed();
        find(workShifts).isDisplayed();
        return true;
    }

    /*
    public Boolean VerifyPimTab1() throws Exception
    {
        sleep(2);
        waitUntilVisibleElement(find(pimTab));
        find(pimTab).click();
        find(addButton).click();
        sleep(2);
        find(save).click();
        return true;
    }
    */
    public Boolean DeleteRecord() throws Exception
    {
        sleep(2);
        waitUntilVisibleElement(find(pimTab));
        find(pimTab).click();
        find(employeeName1).sendKeys("Janvitha Boddiboyina");
        find(search).click();
        find(bin).click();
        find(yesDelete).click();
        return true;
    }
}
