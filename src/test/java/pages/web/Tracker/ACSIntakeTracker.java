package pages.web.Tracker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class ACSIntakeTracker extends BasePage {

    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By  searchButton = By.xpath("//input[@id='btnSearch0']");
    public By  editButton  = By.xpath("//input[@id='btnEdit0']");
    public By  searchTypeDropdown  = By.xpath("//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*");
    public By  selectedOption  = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public String  topDivSearchTypeDropdown = "//div[@class='component item_select'][normalize-space()='textName']";
    public By okButton  = By.xpath("//input[@id='btnOK']");
    public By ApplyButton = By.xpath("//input[@id='btnApply']");
    public By closeButton = By.xpath("//*[@id='managerWindow']/div/div[3]/div/div/div[1]/div[2]/div[1]/div[2]/div[2]/input");
    public By uploadFilesButton = By.xpath("//*[@id='managerWindow']/div/div[3]/div/div/div[1]/div[2]/div[1]/div[1]/div[1]/input");
    public By ReqDocuments = By.xpath("//*[@id='cfTable_1']/tbody/tr[51]/td[2]");
    public By count = By.xpath("//*[@class='component cf_efile newGuiConfigMultEFile']/div[2]/div[1]/div");
    public ACSIntakeTracker(WebDriver driver) {
        super(driver);
        this.driver = driver;
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
        WebElement searchDropdown = find(searchTypeDropdown);
        click(searchDropdown);
        System.out.println("sss" + searchDropdown);
        if (!find(selectedOption).getText().equals(type)) {
            dropDownButtons(topDivSearchTypeDropdown, type);
        }
    }
    public ACSIntakeTracker selectEditOption() throws Exception {
        sleep(4);
        click(find(editButton));
        sleep(5);
        return new ACSIntakeTracker(driver);
    }

    public String uploadBulkFiles() throws Exception {
        sleep(5);
        switchToChildWindows();
        fullScreenChildWindow();
        scrollToElement(inputBoxDataBySname("ACS:Designed Solution Type"));
        sleep(5);
        click(find(ReqDocuments));
        //click(inputBoxDataBySname("ACS:Required Documents"));
        sleep(5);
        WebElement addFile = driver.findElement(By.xpath(".//input[@type='file']"));
        addFile.sendKeys("C:/Users/prasanna.yedrami/Downloads/OneDrive_1_6-2-2022.zip");
        addFile.sendKeys("C:/Users/prasanna.yedrami/Downloads/samplepptx.pptx");
        addFile.sendKeys("C:/Users/prasanna.yedrami/Downloads/TestReports.html");
        addFile.sendKeys("C:/Users/prasanna.yedrami/Downloads/DevProjectTests.txt");
        sleep(3);
        click(find(closeButton));
        String filesCount = find(count).getText();
        System.out.println("Multiple Files uploaded Count - " + filesCount);
        sleep(5);
        click(find(ApplyButton));
        sleep(5);
        return filesCount;
    }
}
