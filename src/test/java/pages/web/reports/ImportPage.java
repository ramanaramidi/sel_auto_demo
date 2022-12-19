package pages.web.reports;

import commons.constants.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.io.IOException;

public class ImportPage extends BasePage {

    public ImportPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By importButton = By.xpath("//input[@value='Import']");
    public By  searchButton = By.xpath("//input[@id='btnSearch0']");
    public By  editProject  = By.xpath("//input[@id='btnEdit0']");
    public By chooseFile = By.xpath("//body[1]/div[10]/div[1]/div[2]/div[1]/div[1]/form[1]/div[1]/fieldset[1]/table[1]/tbody[1]/tr[1]/td[2]");
    public By okButton = By.xpath("//input[@name='btnOK']");
    public By  searchTypeDropdown  = By.xpath("//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*");
    public By  selectedOption  = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public String  topDivSearchTypeDropdown = "//div[@class='component item_select'][normalize-space()='textName']";

    public void searchForValue(String data, String type) throws Exception
    {
        fullScreen();
        waitUntilVisibleElement(find(searchOption));
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitUntilVisibleElement(find(importButton));
        sleep(20);
    }

    public void selectSearchType(String type) throws Exception {
        WebElement searchDropdown = find(searchTypeDropdown);
        click(searchDropdown);
        System.out.println("sss" + searchDropdown);
        if (!find(selectedOption).getText().equals(type)) {
            dropDownButtons(topDivSearchTypeDropdown, type);
        }
    }

    public void clickOnImport() throws Exception {
        waitUntilVisibleElement(find(importButton));
        click(find(importButton));
        sleep(5);
    }

    public boolean importFile() throws Exception {
        String parent1 = switchToChildWindows();
        jsClickById("impFile");
        click(find(chooseFile));
        sleep(5);
      //  Runtime.getRuntime().exec(Constants.SECTOR_IMPORT_EXE);
//        waitUntilVisibleElement(find(chooseFile));
        sleep(10);
        if(findAll(okButton).size()>0){
            click(find(okButton));
            switchToSpecificWindow(parent1);
            return true;
        }
        return false;
    }

}
