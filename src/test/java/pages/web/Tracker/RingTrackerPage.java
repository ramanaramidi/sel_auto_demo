package pages.web.Tracker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.Iterator;
import java.util.List;

public class RingTrackerPage extends BasePage {

    public WebDriver driver;

    public RingTrackerPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By  searchTypeDropdown  = By.xpath("//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*");
    public String  topDivSearchTypeDropdown = "//div[@class='component item_select'][normalize-space()='textName']";
    public By  selectedOption  = By.xpath("//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']");
    public By  editRing  = By.xpath("//input[@id='btnEdit0']");
    public By  searchButton = By.xpath("//input[@id='btnSearch0']");
    public By  ringTrackerTableElement  = By.xpath("//tr[contains(@class,'ev_dhx_skyblue ev_dhx_skyblue rowselected')]//parent::*");


    public AddRingPage selectEditOption() throws Exception {
        waitUntilVisibleElement(find(editRing));
        click(find(editRing));
        return new AddRingPage(driver);
    }

    public void searchForValue(String data, String type) throws Exception {
        sleep(5);
        waitUntilVisibleElement(find(ringTrackerTableElement));
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitUntilVisibleElement(find(ringTrackerTableElement));
        sleep(5);
    }

    public Boolean isDataPresentInTable() throws Exception {
        waitUntilVisibleElement(find(ringTrackerTableElement));
        WebElement ringTrackerTbElement = find(ringTrackerTableElement);
        List<WebElement> rows = getTableRows(ringTrackerTbElement);
        List<WebElement> cellData = getTableCellsByRow(rows.get(1));
        System.out.println("Data" + cellData.get(1).getText());
        //check if null check needed
        return !cellData.get(1).getText().isEmpty() && !cellData.get(1).getText().equals(" ");
    }

    public Boolean isRingStatus(String status) throws Exception {
        WebElement ringTrackerTbElement = find(ringTrackerTableElement);
        List<WebElement> tableRows = getTableRows(ringTrackerTbElement);
        Iterator<WebElement> allRows = tableRows.iterator();
        System.out.println("rows" + tableRows.size());
        allRows.next();
        while (allRows.hasNext()) {
            List<WebElement> cells = getTableCellsByRow(allRows.next());
            waitUntilVisibleElement(cells.get(1));
            System.out.println("STATUS:: "+cells.get(1).getText());
            if (!cells.get(1).getText().equals(status))
                return false;
        }
        return true;
    }

    public void selectSearchType(String type) throws Exception {
        WebElement searchDropdown = find(searchTypeDropdown);
        click(searchDropdown);
        System.out.println("sss" + searchDropdown);
        if (!find(selectedOption).getText().equals(type)) {
            dropDownButtons(topDivSearchTypeDropdown, type);
        }
    }

    public AddRingPage selectAddNewRingOption() throws Exception {
        waitUntilVisibleElement(find(editRing));
        buttonClick("Add", 1);
        sleep(10);
        return new AddRingPage(driver);
    }

    public boolean checkForPageLoad() throws Exception {
        waitUntilVisibleElement(find(editRing));
        return true;
    }
}
