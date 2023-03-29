package pages.web.Tracker.site;

import commons.objects.Users;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.poifs.crypt.dsig.ExpiredCertificateSecurityException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import utility.helper.MiscHelpers;

public class SiteFopsPage extends BasePage {

    public WebDriver driver;

    public SiteFopsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public By searchOption = By.xpath("//input[@id='qsValue0']");
    public By searchButton = By.xpath("//input[@id='btnSearch0']");
    public By searchTypeDropdown = By.xpath(
            "//div[@id='qsField0']//div[@class='component button u_button_invert btn_raised btn_white']//child::*"
    );
    public By selectedOption = By.xpath(
            "//div[@id='qsField0']//div[@class='l_items']//div[@class='component item_select selected']"
    );
    public String topDivSearchTypeDropdown =
            "//div[@class='component item_select'][normalize-space()='textName']";
    public By editSite = By.xpath("//input[@id='btnEdit0']");
    public By applyButton = By.xpath("//input[@id='btnApply']");
    public By okButton = By.xpath("//input[@id='btnOK']");
    public By complianceDocumentation = By.xpath(
            "//label//i[contains(text(),'OSHA Compliance documentation is located on the D:Document tab.')]"
    );
    public By FOPSInfoTab = By.xpath("//div[@title='S:FOPS Info']");
    public By cancelButton = By.xpath("//*[@id='btnCancel']");
    public By notificationCheckBox = By.xpath("//label[text()='S:Telco Access Notification Required']//parent::td//following-sibling::td//child::label//input");
    public By notificationCheckBox_Other = By.xpath("//label[text()='S:Other Access Notification Required']//parent::td//following-sibling::td//child::label//input");
    public By fieldTechName = By.xpath("//label[text()='S:Field Tech Name ']");
    public By fieldTechNameTextField = By.xpath(
            "//input[@sname='S:Field Tech Name ']"
    );
    public By fieldTechPhone = By.xpath(
            "(//label[contains(text(),'S:Field Tech Phone')]/parent::td/following-sibling::td/div/div/input)[1]"
    );
    public By fieldTechEmail = By.xpath(
            "(//label[contains(text(),'S:Field Tech Email')]/parent::td/following-sibling::td/div/div/input)[1]"
    );
    public By AuditPhotos = By.xpath(
            "(//label[text()='S:EME Audit Photos [Doc]'])[2]"
    );
    public By auditLast = By.xpath(
            "(//label[text()='S:EME Audit Last Performed Date'])[2]"
    );
    public By auditSignage = By.xpath(
            "(//label[text()='S:EME Audit Signage Expiration Date'])[2]"
    );
    public By signageVisit = By.xpath(
            "(//label[text()='S:EME Signage Visit Required'])[2]"
    );
    public By backhaulType = By.xpath(
            "(//label[text()='S:Site Backhaul Type'])[2]"
    );
    public By documentUploadedBy = By.xpath(
            "(//label[text()='S:EME Document Uploaded By'])[2]"
    );
    public By documentUploadedByUser = By.xpath(
            "(//label[text()='S:EME Document Uploaded User'])[2]"
    );
    public By docTrackerError = By.xpath(
            "(//label[text()='S:Copy EME To Doc Tracker Error'])[2]"
    );
     //public By siteSecurityDropDown = By.xpath("(//label[text()='S:Site Backhaul Type'])[2]");

    public By siteSecurity = By.xpath(
            "//select[@sname='S:Site Security Issues']"
    );
    public By DocTrackerError = By.xpath(
            "(//textarea[@sname='S:Copy EME To Doc Tracker Error'])[2]"
    );
    public By documentUploadedByUserField = By.xpath(
            "((//label[text()='S:EME Document Uploaded User'])[2]/parent::td/following-sibling::td/div/div/input)[1]"
    );
    public By photoIcon = By.xpath(
            "(//input[@sname='S:EME Audit Photos [Doc]']//parent::div//following-sibling::div[@class = 'newFieldBtn']/descendant::*)[2]"
    );
    public By riotTab = By.xpath("//span[normalize-space()='Site - RIOT']");
    public By signageVisitLock = By.xpath(
            "((//label[text()='S:EME Signage Visit Required'])[2]/parent::td/descendant::*)[2]"
    );
    public By auditPhotoLock = By.xpath(
            "(//label[text()='S:EME Audit Photos [Doc]'])[2]/parent::td/descendant::div"
    );
    public By siteRiotSection = By.xpath("//label[text()='S:Site - RIOT']");
    public By photoUploadSection = By.xpath("//b[normalize-space()='RIOT Photo Upload']");
    public By currentVersionCheckBox = By.xpath("(//div[@class='hdr_cell']//div[text()='D:Current Version?']/ancestor::table/../following-sibling::div//label/input)[5]");
    public By documentTab = By.xpath("//span[normalize-space()='Document']");
    public String characters4K = MiscHelpers
            .getRandomString(4000, true)
            .toUpperCase();

    String parentWindow;

    public void searchForValue(String data, String type) throws Exception {
        fullScreen();
        waitForPageToLoad();
        setText(find(searchOption), data);
        selectSearchType(type);
        click(find(searchButton));
        waitForPageToLoad();
    }

    public void selectSearchType(String type) throws Exception {
        WebElement searchDropdown = find(searchTypeDropdown);
        click(searchDropdown);
        System.out.println("sss" + searchDropdown);
        if (!find(selectedOption).getText().equals(type)) {
            dropDownButtons(topDivSearchTypeDropdown, type);
        }
    }

    public SiteFopsPage selectEditOption() throws Exception {
        waitUntilVisibleElement(find(editSite));
        sleep(10);
        click(find(editSite));
        sleep(5);
        return new SiteFopsPage(driver);
    }

    public void goToFOPSTab() throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        click(find(FOPSInfoTab));
        waitForPageToLoad();
    }

    public boolean complianceDocumentation_OSHA() throws Exception {
        waitForPageToLoad();
        scrollToElement(find(complianceDocumentation));
        boolean complianceDocumentationField = find(complianceDocumentation)
                .isDisplayed();
        return complianceDocumentationField;
    }

    public void switchToTrackerPage() throws Exception {
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
    }

    public boolean validateFieldIsDisplayed(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldNameText = fieldByLabelTextIndex(name).get(0);
        scrollToElement(fieldNameText);
        boolean fieldName = fieldNameText.isDisplayed();
        return fieldName;
    }
    public boolean validateField_IsDisplayed(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldNameText = fieldByLabelTextIndex(name).get(1);
        scrollToElement(fieldNameText);
        boolean fieldName = fieldNameText.isDisplayed();
        return fieldName;
    }
    public String validateFieldIsReadOnly(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldName = inputBoxDataBySname(name);
        scrollToElement(fieldName);
        String readOnly = fieldName.getAttribute("readonly");
        return readOnly;
    }

    public String validateDrpFieldIsReadOnly(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldName = selectionBoxBySname(name).get(0);
        scrollToElement(fieldName);
        String readOnly = fieldName.getAttribute("readonly");
        return readOnly;
    }
    public void selectDropDownValue(String fieldName, String fieldValue)
            throws Exception {
        waitForPageToLoad();
        WebElement dropDownField = selectionBoxBySname(fieldName).get(0);
        scrollToElement(dropDownField);
        selectDropdownOption(dropDownField, fieldValue);
        sleep(5);
    }

    public boolean isPopAlertPresent() throws Exception {
        click(find(applyButton));
        waitForPageToLoad();
        waitUntilVisibleElement(find(applyButton));
        if (!isAlertPresent()) {
            click(find(okButton));
            sleep(5);
            switchToSpecificWindow(parentWindow);
            return false;
        } else {
            acceptAlert();
            click(find(cancelButton));
            if (!isAlertPresent()) {
                switchToSpecificWindow(parentWindow);
            } else {
                acceptAlert();
                switchToSpecificWindow(parentWindow);
            }
            return true;
        }
    }
    public String verifyFieldIsCheckbox(String fieldName) throws Exception {
        waitForPageToLoad();
        WebElement checkBoxField = checkBoxByLabelInput(fieldName);
        scrollToElement(checkBoxField);
        String value = checkBoxField.getAttribute("type");
        System.out.println(value);
        return value;
    }

    public String getComplianceRemediationText_OSHA(String fieldName)
            throws Exception {
        click(find(applyButton));
        waitForPageToLoad();
        waitUntilVisibleElement(find(okButton));
        WebElement ComplianceRemediation = inputBoxDataBySname(fieldName);
        scrollToElement(ComplianceRemediation);
        String ComplianceRemediationText = ComplianceRemediation.getAttribute(
                "origval"
        );
        System.out.println(ComplianceRemediationText);
        return ComplianceRemediationText;
    }

    public boolean clickCheckbox_OSHA(String fieldName) throws Exception {
        waitForPageToLoad();
        WebElement checkBoxFieldId = checkBoxByLabelInput(fieldName);
        WebElement checkBoxField = checkBoxByLabel(fieldName);
        scrollToElement(checkBoxField);
        click(checkBoxField);
        sleep(2);
        boolean checkboxSelected = isCheckboxSelected(
                checkBoxFieldId.getAttribute("id")
        );
        return checkboxSelected;
    }

    public String verifyFieldIsText_Facility(String fieldName) throws Exception {
        waitForPageToLoad();
        WebElement textField = textAreaBySname(fieldName);
        scrollToElement(textField);
        String isTextField = textField.getAttribute("class");
        System.out.println(isTextField);
        return isTextField;
    }

    public String verifyInputBoxFieldIsText_Facility(String fieldName)
            throws Exception {
        waitForPageToLoad();
        WebElement textField = inputBoxDataBySname(fieldName);
        scrollToElement(textField);
        String isTextField = textField.getAttribute("type");
        System.out.println(isTextField);
        return isTextField;
    }

    public boolean verifyTextareaFieldCharacterLimit_Facility(
            String fieldName,
            int limit
    ) throws Exception {
        waitForPageToLoad();
        WebElement textField = textAreaBySname(fieldName);
        scrollToElement(textField);
        String fieldText = textField.getAttribute("maxlength");
        int fieldLength = Integer.parseInt(fieldText);
        System.out.println("Actual length of the Field is -" + fieldLength);
        if (fieldLength == limit) {
            return true;
        } else return false;
    }

    public boolean verifyTextFieldCharacterLimit_Facility(
            String fieldName,
            int limit
    ) throws Exception {
        waitForPageToLoad();
        WebElement textField = inputBoxDataBySname(fieldName);
        scrollToElement(textField);
        String fieldText = textField.getAttribute("maxlength");
        int fieldLength = Integer.parseInt(fieldText);
        System.out.println("Actual length of the Field is -" + fieldLength);
        if (fieldLength == limit) {
            return true;
        } else return false;
    }
    public String validateTextAreaFieldIsReadOnly_Facility(String name)
            throws Exception {
        waitForPageToLoad();
        WebElement fieldName = textAreaBySname(name);
        scrollToElement(fieldName);
        String readOnly = fieldName.getAttribute("readonly");
        return readOnly;
    }
    public void addAccessDescriptionComments_Facility(
            String accessDescriptionText
    ) throws Exception {
        waitForPageToLoad();
        WebElement accessNotesField = textAreaBySname(
                "S:Facility Access Description"
        );
        scrollToElement(accessNotesField);
        setText(accessNotesField, accessDescriptionText);
        click(find(applyButton));
        waitForPageToLoad();
        waitUntilVisibleElement(find(applyButton));
    }

    public void addAccessNotesComments_Facility(String accessNotesText)
            throws Exception {
        waitForPageToLoad();
        WebElement accessNotesField = inputBoxDataBySname(
                "S:Facility Access Notes"
        );
        scrollToElement(accessNotesField);
        setText(accessNotesField, accessNotesText);
        click(find(applyButton));
        waitForPageToLoad();
        waitUntilVisibleElement(find(applyButton));
    }

    public String getTextAccessNotesHistory_Facility() throws Exception {
        waitForPageToLoad();
        WebElement accessNotesHistoryField = textAreaBySname(
                "S:Facility Access Notes History"
        );
        scrollToElement(accessNotesHistoryField);
        String accessNotesHistoryText = accessNotesHistoryField.getText();
        return accessNotesHistoryText;
    }

    public boolean getModifiedByFieldText(Users userDetails) throws Exception {
        waitForPageToLoad();
        scrollToElement(textAreaBySname("S:Site Access Details"));
        setText(textAreaBySname("S:Site Access Details"), "Testing");
        click(find(applyButton));
        waitForPageToLoad();
        waitUntilVisibleElement(find(okButton));
        String owner = userDetails.getNtCode();
        WebElement lastModifiedBy = inputBoxDataBySname(
                "S:Facility Access Modified By"
        );
        scrollToElement(lastModifiedBy);
        String updated = getDocumentTextByIdJs(lastModifiedBy.getAttribute("id"));
        System.out.println("Name::" + updated);
        boolean modifiedBy = updated.contains(owner);
        System.out.println("boolean:: " + modifiedBy);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        sleep(5);
        return modifiedBy;
    }

    public boolean getModifiedDateFieldText() throws Exception {
        waitForPageToLoad();
        scrollToElement(textAreaBySname("S:Site Access Details"));
        setText(textAreaBySname("S:Site Access Details"), "Testing");
        click(find(applyButton));
        waitForPageToLoad();
        waitUntilVisibleElement(find(okButton));
        String currentDate = MiscHelpers.currentDateTime("MM/dd/yyyy");
        WebElement lastModifiedDate = inputBoxDataBySname(
                "S:Facility Access Modified Date"
        );
        scrollToElement(lastModifiedDate);
        String modifiedDateText = getDocumentTextByIdJs(
                lastModifiedDate.getAttribute("id")
        );
        System.out.println("Date::" + modifiedDateText);
        boolean modifiedDate = modifiedDateText.contains(currentDate);
        System.out.println("boolean:: " + modifiedDate);
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        sleep(5);
        return modifiedDate;
    }
    public void setNotificationPeriod() throws Exception{
        waitForPageToLoad();
        WebElement notificationPeriod = inputBoxDataBySname("S:Facility Access Notification Period");
        scrollToElement(notificationPeriod);
        setText(notificationPeriod,"Test");
        click(find(applyButton));
        waitForPageToLoad();
        waitUntilVisibleElement(find(okButton));
    }

    public void clickCheckbox_Facility(String fieldName) throws Exception {
        waitForPageToLoad();
        WebElement checkBoxFieldId = checkBoxByLabelInput(fieldName);
        WebElement checkBoxField = checkBoxByLabel(fieldName);
        scrollToElement(checkBoxField);
        boolean checkboxSelected = isCheckboxSelected(
                checkBoxFieldId.getAttribute("id")
        );
        if(!checkboxSelected){
            click(checkBoxField);
            sleep(2);
        }
    }
    public boolean verifyCheckboxIsSelected_Facility(String fieldName) throws Exception {
        waitForPageToLoad();
        WebElement checkBoxFieldId = checkBoxByLabelInput(fieldName);
        WebElement checkBoxField = checkBoxByLabel(fieldName);
        scrollToElement(checkBoxField);
        boolean checkboxSelected = isCheckboxSelected(
                checkBoxFieldId.getAttribute("id")
        );
        return checkboxSelected;
    }

    public String verifyFieldFormat_Facility(String fieldName) throws Exception {
        waitForPageToLoad();
        WebElement fieldControl = inputBoxDataBySname(fieldName);
        scrollToElement(fieldControl);
        String format = fieldControl.getAttribute("title");
        System.out.println(format);
        return format;
    }

    public void updateTime(String fieldName) throws Exception {
        waitForPageToLoad();
        WebElement startTimeField = inputBoxDataBySname(fieldName);
        scrollToElement(startTimeField);
        String currentTime = MiscHelpers.currentDateTime("HH:mm");
        setText(startTimeField, currentTime);
        click(find(applyButton));
        waitForPageToLoad();
        waitUntilVisibleElement(find(okButton));
    }
    public String switchToProjectPage() {
        parentWindow = switchToChildWindows();
        fullScreen();
        sleep(3);
        return parentWindow;
    }
    public boolean isFOPSTabExists() throws Exception {
        sleep(3);
        if (findAll(FOPSInfoTab).size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public void goTo_FopsTab() throws Exception {
        waitUntilVisibleElement(find(applyButton));
        sleep(5);
        click(find(FOPSInfoTab));
        waitForPageToLoad();
    }
    public void switchToTracker(String parentWindow) throws Exception {
        sleep(8);
        click(find(okButton));
        sleep(5);
        switchToSpecificWindow(parentWindow);
    }
    public String verifyTextFields(String name) throws Exception {
        waitForPageToLoad();
        String text = "FOPS Other Access Testing";
        int length = text.length();
        System.out.println("length of the Field is -" + length);
        WebElement textFieldName = inputBoxDataBySname(name);
        scrollToElement(textFieldName);
        setText(textFieldName, text);
        sleep(3);
        String fieldText = textFieldName.getAttribute("value");
        System.out.println("Value of the Field is -" + fieldText);
        click(find(applyButton));
        sleep(3);
        return fieldText;
    }
    public String verifyTextArea_OtherAccessFields(String name) throws Exception {
        waitForPageToLoad();
        String text = "Testing";
        int length = text.length();
        System.out.println("length of the Field is -" + length);
        WebElement textAreaName = textAreaBySname(name);
        scrollToElement(textAreaName);
        setText(textAreaName, text);
        sleep(3);
        String fieldText = textAreaName.getText();
        System.out.println("text of the Field is -" + fieldText);
        click(find(applyButton));
        waitForPageToLoad();
        sleep(4);
        return fieldText;
    }
    public boolean verifyTextField(String name) throws Exception {
        waitForPageToLoad();
        String text =
                "Selenium supports automation of all the major browsers in the market through the use of WebDriver. WebDriver is an API and protocol that defines a lan";
        int length = text.length();
        System.out.println("Expected length of the Field is -" + length);
        WebElement textFieldName = inputBoxDataBySname("S:Telco Access Gate Combo");
        scrollToElement(textFieldName);
        setText(textFieldName, text);
        sleep(3);
        String fieldText = textFieldName.getAttribute("value");
        click(find(applyButton));
        sleep(3);
        System.out.println("Actual length of the Field is -" + fieldText);
        int fieldLength = fieldText.length();
        System.out.println("Actual length of the Field is -" + fieldLength);
        if (fieldLength == 150) {
            return true;
        } else if (fieldLength > 150) {
            return false;
        }
        return false;
    }

    public boolean verifyTextField_TelcoAccessKeys() throws Exception {
        waitForPageToLoad();
        String text = "Selenium supports automation in all major browsers";
        int length = text.length();
        System.out.println("Expected length of the Field is -" + length);
        WebElement textFieldName = inputBoxDataBySname("S:Telco Access Keys");
        scrollToElement(textFieldName);
        setText(textFieldName, text);
        sleep(3);
        String fieldText = textFieldName.getAttribute("value");
        System.out.println("Actual length of the Field is -" + fieldText);
        int fieldLength = fieldText.length();
        System.out.println("Actual length of the Field is -" + fieldLength);
        if (fieldLength == length) {
            return true;
        }
        return false;
    }

    public boolean verifyTextField_TelcoDemarcLocation() throws Exception {
        waitForPageToLoad();
        String text = "Selenium supports automation in all major browsers";
        int length = text.length();
        System.out.println("Expected length of the Field is -" + length);
        WebElement textFieldName = inputBoxDataBySname("S:Telco Demarc Location");
        scrollToElement(textFieldName);
        setText(textFieldName, text);
        sleep(3);
        String fieldText = textFieldName.getAttribute("value");
        System.out.println("Actual length of the Field is -" + fieldText);
        int fieldLength = fieldText.length();
        System.out.println("Actual length of the Field is -" + fieldLength);
        if (fieldLength == length) {
            return true;
        }
        return false;
    }

    public Boolean verifyTextField_TelcoAAVNIDLocation() throws Exception {
        waitForPageToLoad();
        String text = "Selenium supports automation in all major browsers";
        int length = text.length();
        System.out.println("Expected length of the Field is -" + length);
        WebElement textFieldName = inputBoxDataBySname("S:Telco AAV NID Location");
        scrollToElement(textFieldName);
        setText(textFieldName, text);
        sleep(3);
        String fieldText = textFieldName.getAttribute("value");
        System.out.println("Actual length of the Field is -" + fieldText);
        int fieldLength = fieldText.length();
        System.out.println("Actual length of the Field is -" + fieldLength);
        if (fieldLength == length) {
            return true;
        }
        return false;
    }

    public Boolean verifyTextField_TelcoAAVComboKeys() throws Exception {
        waitForPageToLoad();
        String text = "Selenium supports automation in all major browsers";
        int length = text.length();
        System.out.println("Expected length of the Field is -" + length);
        WebElement textFieldName = inputBoxDataBySname("S:Telco AAV Combo/Keys");
        scrollToElement(textFieldName);
        setText(textFieldName, text);
        sleep(3);
        String fieldText = textFieldName.getAttribute("value");
        System.out.println("Actual length of the Field is -" + fieldText);
        int fieldLength = fieldText.length();
        System.out.println("Actual length of the Field is -" + fieldLength);
        if (fieldLength == length) {
            return true;
        }
        return false;
    }

    public Boolean verifyTextField_TelcoTMOComboKeysField() throws Exception {
        waitForPageToLoad();
        String text = "Selenium supports automation in all major browsers";
        int length = text.length();
        System.out.println("Expected length of the Field is -" + length);
        WebElement textFieldName = inputBoxDataBySname("S:Telco TMO Combo/Keys");
        scrollToElement(textFieldName);
        setText(textFieldName, text);
        sleep(3);
        String fieldText = textFieldName.getAttribute("value");
        System.out.println("Actual length of the Field is -" + fieldText);
        int fieldLength = fieldText.length();
        System.out.println("Actual length of the Field is -" + fieldLength);
        if (fieldLength == length) {
            return true;
        }
        return false;
    }

    public Boolean verifyTextField_TelcoTMONIDLocation() throws Exception {
        waitForPageToLoad();
        String text = "Selenium supports automation in all major browsers";
        int length = text.length();
        System.out.println("Expected length of the Field is -" + length);
        WebElement textFieldName = inputBoxDataBySname("S:Telco TMO NID Location");
        scrollToElement(textFieldName);
        setText(textFieldName, text);
        sleep(3);
        String fieldText = textFieldName.getAttribute("value");
        System.out.println("Actual length of the Field is -" + fieldText);
        int fieldLength = fieldText.length();
        System.out.println("Actual length of the Field is -" + fieldLength);
        if (fieldLength == length) {
            return true;
        }
        return false;
    }

    public Boolean verifyTextField_TelcoProviderCompanyName() throws Exception {
        waitForPageToLoad();
        String text = "Selenium supports automation";
        int length = text.length();
        System.out.println("Expected length of the Field is -" + length);
        WebElement textFieldName = inputBoxDataBySname(
                "S:Telco Provider Company Name"
        );
        scrollToElement(textFieldName);
        setText(textFieldName, text);
        sleep(3);
        String fieldText = textFieldName.getAttribute("value");
        System.out.println("Actual length of the Field is -" + fieldText);
        int fieldLength = fieldText.length();
        System.out.println("Actual length of the Field is -" + fieldLength);
        if (fieldLength > 0) {
            return true;
        }
        return false;
    }
    public void updateTextArea_TelcoAccessDescription(String text) throws Exception {
        waitForPageToLoad();
        //String text = "Testing"
        int length = text.length();
        System.out.println("Expected length of the Field is -" + length);
        WebElement textAreaName = textAreaBySname("S:Telco Access Description");
        scrollToElement(textAreaName);
        setText(textAreaName, text);
        sleep(3);
        click(find(applyButton));
        waitForPageToLoad();
        sleep(4);
    }

    public Boolean verifyTextArea_TelcoAccessDescription() throws Exception {
        waitForPageToLoad();
        WebElement textAreaName = textAreaBySname("S:Telco Access Description");
        scrollToElement(textAreaName);
        String fieldText = textAreaName.getText();
        System.out.println("Actual length of the Field is -" + fieldText);
        int fieldLength = fieldText.length();
        System.out.println("Actual length of the Field is -" + fieldLength);
        if (fieldLength > 0) {
            return true;
        } else return false;
    }

    public Boolean verifyTextField_NotesField(String name, String text) throws Exception {
        waitForPageToLoad();
        // String text =
        //   "Selenium supports automation of all the major browsers in the market through the use of WebDriver. WebDriver is an API and protocol that defines a language-neutral interface for controlling the behaviour of web browsers. Each browser is backed by a specific WebDriver implementation, called a driver. The driver is the component responsible for delegating down to the browser, and handles communication to and from Selenium and the browser.This separation is part of a conscious effort to have browser vendors take responsibility for the implementation for their browsers. Selenium makes use of these third party drivers where possible, but also provides its own drivers maintained by the project for the cases when this is not a reality.The Selenium framework ties all of these pieces together through a user-facing interface that enables the different browser backends to be used transparently, enabling cross-browser and cross-platform automation.Selenium setup is quite different from the setup of other commercial tools. Before you can start writing Selenium code, you have to install the language bindings libraries for your language of choice, the browser you want to use, and the driver for that browser. Selenium supports automation of all the major browsers in the market through the use of WebDriver. WebDriver is an API and protocol that defines a language-neutral interface for controlling the behaviour of web browsers. Each browser is backed by a specific WebDriver implementation, called a driver. The driver is the component responsible for delegating down to the browser, and handles communication to and from Selenium and the browser.This separation is part of a conscious effort to have browser vendors take responsibility for the implementation for their browsers. Selenium makes use of these third party drivers where possible, but also provides its own drivers maintained by the project for the cases when this is not a reality.The Selenium framework ties all of these pieces together through a user-facing interface that enables the different browser backends to be used transparently, enabling cross-browser and cross-platform automation.Selenium setup is quite different from the setup of other commercial tools. Before you can start writing Selenium code, you have to install the language bindings libraries for your language of choice, the browser you want to use, and the driver for that browser. Selenium supports automation of all the major browsers in the market through the use of WebDriver. WebDriver is an API and protocol that defines a language-neutral interface for controlling the behaviour of web browsers. Each browser is backed by a specific WebDriver implementation, called a driver. The driver is the component responsible for delegating down to the browser, and handles communication to and from Selenium and the browser.This separation is part of a conscious effort to have browser vendors take responsibility for the implementation for their browsers. Selenium makes use of these third party drivers where possible, but also provides its own drivers maintained by the project for the cases when this is not a reality.The Selenium framework ties all of these pieces together through a user-facing interface that enables the different browser backends to be used transparently, enabling cross-browser and cross-platform automation.Selenium setup is quite different from the setup of other commercial tools. Before you can start writing Selenium code, you have to install the language bindings libraries for your language of choice, the browser you want to use, and the driver for that browser. Selenium setup is quite different from the setup of other commercial tools. Before you can start writing Selenium code, you have to install the language bindings libraries for your language of choice, the browser you want to use, and the driver for that browser. The Selenium framework ties all of these pieces together through a user-facing interface that enab";
        WebElement textFieldName = inputBoxDataBySname(name);
        scrollToElement(textFieldName);
        sleep(3);
        setText(textFieldName," ");
        setText(textFieldName, text);
        String textLength = textFieldName.getAttribute("maxlength");
        int fieldLength = Integer.parseInt(textLength);
        System.out.println("length is : " + textLength);
        sleep(2);
        click(find(applyButton));
        sleep(4);
        int maxLength = 2000;
        if (fieldLength == maxLength) {
            return true;
        } else return false;
    }

    public String verifyNotificationCheckBox(String fieldName)
            throws Exception {
        waitForPageToLoad();
        WebElement checkBoxField = checkBoxByLabelInput(fieldName);
        scrollToElement(checkBoxField);
        String value = checkBoxField.getAttribute("type");
        System.out.println(value);
        return value;
    }
    public boolean verifyCheckBoxChecked_TelcoAccess(String fieldName) throws Exception {
        waitForPageToLoad();
        WebElement checkBoxField = checkBoxByLabelInput(fieldName);
        scrollToElement(checkBoxField);
        sleep(4);
        boolean check = isCheckboxSelected( find(notificationCheckBox).getAttribute("id"));
        System.out.println("Telco Notification CheckBox checked - " + check);
        if (check) {
            checkBoxCheckByJS(checkBoxField);
            sleep(3);
            checkBoxCheckByJS(checkBoxField);
        } else if (!check) {
            checkBoxCheckByJS(checkBoxField);
        }
        click(find(applyButton));
        sleep(3);
        boolean check1 = isCheckboxSelected( find(notificationCheckBox).getAttribute("id"));
        if (check1){
            return true;
        }else{
            return false;
        }
    }
    public Boolean verifyTextField_TelcoProviderContactNumber() throws Exception {
        waitForPageToLoad();
        String text =
                "Selenium supports automation of all the major browsers in the market through the use of WebDriver.";
        int length = text.length();
        System.out.println("Expected length of the Field is -" + length);
        WebElement textFieldName = inputBoxDataBySname(
                "S:Telco Provider Contact Number"
        );
        scrollToElement(textFieldName);
        setText(textFieldName, text);
        sleep(3);
        String fieldText = textFieldName.getAttribute("value");
        sleep(3);
        click(find(applyButton));
        sleep(2);
        System.out.println("Actual length of the Field is -" + fieldText);
        int fieldLength = fieldText.length();
        System.out.println("Actual length of the Field is -" + fieldLength);
        if (fieldLength > 0) {
            return true;
        }
        return false;
    }
    public boolean verifyTextArea_NotesHistory(String name,String text) throws Exception {
        waitForPageToLoad();
        WebElement textFieldName = textAreaBySname(name);
        scrollToElement(textFieldName);
        sleep(3);
        String fieldText = textFieldName.getAttribute("readonly");
        System.out.println("Field is -" + fieldText);
        String textFromField = textFieldName.getAttribute("title");
        System.out.println("textFromField is -" + textFromField);
        if (textFromField.contains(text)){
            return true;
        }else {
            return false;
        }
    }
    public boolean verifyTextArea_TelcoAccessNotesHistory(String text) throws Exception {
        waitForPageToLoad();
        WebElement textFieldName = textAreaBySname("S:Telco Access Notes History");
        scrollToElement(textFieldName);
        sleep(3);
        String fieldText = textFieldName.getAttribute("readonly");
        System.out.println("Field is -" + fieldText);
        String textFromField = textFieldName.getAttribute("title");
        System.out.println("textFromField is -" + textFromField);
        if (textFromField.contains(text)){
            return true;
        }else {
            return false;
        }
    }
    public boolean verifyDateTimeStamped(String name) throws Exception {
        waitForPageToLoad();
        String notesText = textAreaBySname(name).getAttribute("title");
        String date_timeText = MiscHelpers.currentDateTime("MM/dd/yyyy hh:mm");
        System.out.println("current Date and Time is - " + date_timeText);
        if (notesText.contains(date_timeText)){
            return true;
        }else {
            return false;
        }
    }
    public boolean verifyModifiedBy(String name,Users userDetails) throws Exception {
        waitForPageToLoad();
        WebElement textFieldName = inputBoxDataBySname(
                name
        );
        scrollToElement(textFieldName);
        sleep(3);
        String fieldText = textFieldName.getAttribute("value");
        System.out.println("Field is -" + fieldText);
        String User = userDetails.getNtCode();
        //userDetails.getNtCode();
        System.out.println("User is -" + User);
        if (fieldText.contains(User)){
            return true;
        }else {
            return false;
        }
    }

    public Boolean verifyModifiedDate(String name) throws Exception {
        waitForPageToLoad();
        WebElement textFieldName = inputBoxDataBySname(
                name
        );
        scrollToElement(textFieldName);
        sleep(3);
        String fieldText = textFieldName.getAttribute("value");
        System.out.println("Field value is -" + fieldText);
        String date_Text = MiscHelpers.currentDateTime("MM/dd/yyyy");
        System.out.println("current Date is - " + date_Text);
        if (fieldText.equals(date_Text)){
            return true;
        }else{
            return false;
        }
    }

    public Boolean verifyTextField_TelcoAccessNotificationPeriod()
            throws Exception {
        waitForPageToLoad();
        String text =
                "Selenium supports automation of all the major browsers in the market through the use of WebDriver.";
        int length = text.length();
        System.out.println("Expected length of the Field is -" + length);
        WebElement textFieldName = inputBoxDataBySname(
                "S:Telco Access Notification Period"
        );
        scrollToElement(textFieldName);
        setText(textFieldName, text);
        sleep(3);
        String fieldText = textFieldName.getAttribute("value");
        sleep(3);
        click(find(applyButton));
        sleep(2);
        System.out.println("Actual length of the Field is -" + fieldText);
        int fieldLength = fieldText.length();
        System.out.println("Actual length of the Field is -" + fieldLength);
        if (fieldLength > 0) {
            return true;
        }
        return false;
    }

    public String verifyFieldDate(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldNameText = inputBoxDataBySname(name);
        scrollToElement(fieldNameText);
        sleep(2);
        String fieldName = fieldNameText.getAttribute("title");
        System.out.println("field starts with - " + fieldName);
        return fieldName;
    }
    public void updateFieldStartDate(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldNameText = inputBoxDataBySname(name);
        scrollToElement(fieldNameText);
        sleep(2);
        setText(fieldNameText,"14:00");
        click(find(applyButton));
        waitForPageToLoad();
        sleep(3);
    }
    public String verifyFieldStartDate(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldNameText = inputBoxDataBySname(name);
        scrollToElement(fieldNameText);
        String fieldName = fieldNameText.getAttribute("title");
        System.out.println("field starts with - " + fieldName);
        return fieldName;
    }
    public String verifyFieldEndDate(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldNameText = inputBoxDataBySname(name);
        scrollToElement(fieldNameText);
        String fieldName = fieldNameText.getAttribute("title");
        System.out.println("field starts with - " + fieldName);
        return fieldName;
    }
    public void updateFieldEndDate(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldNameText = inputBoxDataBySname(name);
        scrollToElement(fieldNameText);
        setText(fieldNameText,"22:00");
        click(find(applyButton));
        waitForPageToLoad();
        sleep(3);
    }
    public boolean verifyCheckBoxChecked_OtherAccess(String fieldName) throws Exception {
        waitForPageToLoad();
        WebElement checkBoxField = checkBoxByLabelInput(fieldName);
        scrollToElement(checkBoxField);
        sleep(4);
        boolean check = isCheckboxSelected( find(notificationCheckBox_Other).getAttribute("id"));
        System.out.println("Other Notification CheckBox checked - " + check);
        if (check) {
            checkBoxCheckByJS(checkBoxField);
            sleep(3);
            checkBoxCheckByJS(checkBoxField);
        } else if (!check) {
            checkBoxCheckByJS(checkBoxField);
        }
        click(find(applyButton));
        sleep(3);
        boolean check1 = isCheckboxSelected( find(notificationCheckBox_Other).getAttribute("id"));
        if (check1){
            return true;
        }else{
            return false;
        }
    }

    public Boolean verifyTextArea_OtherAccessDescription() throws Exception {
        waitForPageToLoad();
        String text =
                "Selenium supports automation of all the major browsers in the market through the use of WebDriver. WebDriver is an API and protocol that defines a language-neutral interface for controlling the behaviour of web browsers. Each browser is backed by a specific WebDriver implementation, called a driver. The driver is the component responsible for delegating down to the browser, and handles communication to and from Selenium and the browser.This separation is part of a conscious effort to have browser vendors take responsibility for the implementation for their browsers. Selenium makes use of these third party drivers where possible, but also provides its own drivers maintained by the project for the cases when this is not a reality.The Selenium framework ties all of these pieces together through a user-facing interface that enables the different browser backends to be used transparently, enabling cross-browser and cross-platform automation.Selenium setup is quite different from the setup of other commercial tools. Before you can start writing Selenium code, you have to install the language bindings libraries for your language of choice, the browser you want to use, and the driver for that browser. Selenium supports automation of all the major browsers in the market through the use of WebDriver. WebDriver is an API and protocol that defines a language-neutral interface for controlling the behaviour of web browsers. Each browser is backed by a specific WebDriver implementation, called a driver. The driver is the component responsible for delegating down to the browser, and handles communication to and from Selenium and the browser.This separation is part of a conscious effort to have browser vendors take responsibility for the implementation for their browsers. Selenium makes use of these third party drivers where possible, but also provides its own drivers maintained by the project for the cases when this is not a reality.The Selenium framework ties all of these pieces together through a user-facing interface that enables the different browser backends to be used transparently, enabling cross-browser and cross-platform automation.Selenium setup is quite different from the setup of other commercial tools. Before you can start writing Selenium code, you have to install the language bindings libraries for your language of choice, the browser you want to use, and the driver for that browser. Selenium supports automation of all the major browsers in the market through the use of WebDriver. WebDriver is an API and protocol that defines a language-neutral interface for controlling the behaviour of web browsers. Each browser is backed by a specific WebDriver implementation, called a driver. The driver is the component responsible for delegating down to the browser, and handles communication to and from Selenium and the browser.This separation is part of a conscious effort to have browser vendors take responsibility for the implementation for their browsers. Selenium makes use of these third party drivers where possible, but also provides its own drivers maintained by the project for the cases when this is not a reality.The Selenium framework ties all of these pieces together through a user-facing interface that enables the different browser backends to be used transparently, enabling cross-browser and cross-platform automation.Selenium setup is quite different from the setup of other commercial tools. Before you can start writing Selenium code, you have to install the language bindings libraries for your language of choice, the browser you want to use, and the driver for that browser. Selenium setup is quite different from the setup of other commercial tools. Before you can start writing Selenium code, you have to install the language bindings libraries for your language of choice, the browser you want to use, and the driver for that browser. The Selenium framework ties all of these pieces together through a user-facing interface that enab";
        WebElement textFieldName = textAreaBySname("S:Other Access Description");
        scrollToElement(textFieldName);
        sleep(3);
        setText(textFieldName," ");
        setText(textFieldName, text);
        String textLength = textFieldName.getAttribute("maxlength");
        int fieldLength = Integer.parseInt(textLength);
        System.out.println("length is : " + textLength);
        sleep(2);
        click(find(applyButton));
        sleep(4);
        int maxLength = 4000;
        if (fieldLength == maxLength) {
            return true;
        } else
            return false;
    }
    public boolean fobsTabVerification() throws Exception {
        parentWindow = switchToChildWindows();
        fullScreen();
        waitForPageToLoad();
        waitUntilVisibleElement(find(okButton));
        Boolean tab = isDisplayed(FOPSInfoTab);
        return tab;
    }

    public void goToSiteTracker() throws Exception {
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
    }
    public void applyAndGoToSiteTracker() throws Exception {
        click(find(applyButton));
        waitForPageToLoad();
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
    }

    public void switchToTrackerPageByCancel() throws Exception {
        click(find(cancelButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
    }

    public void goToFopsTab() throws Exception {
        parentWindow = switchToChildWindows();
        fullScreenChildWindow();
        waitForPageToLoad();
        click(find(FOPSInfoTab));
        waitForPageToLoad();
    }

    public boolean verifySectionIsDisplayed(String name) throws Exception {
        waitForPageToLoad();
        WebElement sectionNameText = sectionsByLabelText(name);
        scrollToElement(sectionNameText);
        boolean fieldName = sectionNameText.isDisplayed();
        return fieldName;
    }

    public boolean validateFieldTechName() throws Exception {
        waitForPageToLoad();
        WebElement fieldNameText = find(fieldTechName);
        //scrollToElement(fieldNameText);
        boolean fieldName = fieldNameText.isDisplayed();
        return fieldName;
    }
    public String validateFieldTechNameIsReadOnly() throws Exception {
        waitForPageToLoad();
        WebElement fieldNameText = find(fieldTechName);
        // scrollToElement(fieldName);
        String readOnly = fieldNameText.getAttribute("readonly");
        return readOnly;
    }

    public boolean validateFieldTechPhone() throws Exception {
        waitForPageToLoad();
        WebElement fieldNameText = find(fieldTechPhone);
        boolean phone = fieldNameText.isEnabled();
        return phone;
    }

    public boolean validateFieldTechEmail() throws Exception {
        waitForPageToLoad();
        WebElement fieldNameText = find(fieldTechEmail);
        boolean email = fieldNameText.isEnabled();
        return email;
    }

    public void updateMandatoryFields() throws Exception {
        // parentWindow = switchToChildWindows();
        //fullScreenChildWindow();
        //waitForPageToLoad();
        //dropDownValueSelection("S:Site Type", "Building");
        // sleep(5);
        //dropDownValueSelection("S:Site Class", "Roof Top Mount");
        //sleep(5);
        //click(find(applyButton));
        //waitForPageToLoad();
        //waitUntilVisibleElement(find(applyButton));
        // click(find(FOPSInfoTab));
        waitForPageToLoad();
        scrollToElement(inputBoxDataBySname("S:Telco Access Notification Period"));
        setText(inputBoxDataBySname("S:Telco Access Notification Period"), "Test");
        scrollToElement(inputBoxDataBySname("S:Telco TMO NID Location"));
        setText(inputBoxDataBySname("S:Telco TMO NID Location"), "Test");
        scrollToElement(inputBoxDataBySname("S:Telco AAV Combo/Keys"));
        setText(inputBoxDataBySname("S:Telco AAV Combo/Keys"), "4545");
        scrollToElement(inputBoxDataBySname("S:Power Access Notification Period"));
        setText(inputBoxDataBySname("S:Power Access Notification Period"), "Test");
        scrollToElement(inputBoxDataBySname("S:Other Access Notification Period"));
        setText(inputBoxDataBySname("S:Other Access Notification Period"), "Test");
        click(find(applyButton));
        waitForPageToLoad();
        waitUntilVisibleElement(find(applyButton));
        //click(find(okButton));
        //switchToSpecificWindow(parentWindow);
    }

    public boolean setFieldTechSectionEmptyAndValidate() throws Exception {
        waitForPageToLoad();
        WebElement fieldName = find(fieldTechName);
        scrollToElement(fieldName);
        setText(fieldName, "");
        click(find(applyButton));
        if (!isAlertPresent()) {
            return true;
        }
        return false;
    }

    public void switchToSiteTracker() throws Exception {
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
    }
    public boolean veryfySiteSecuruity() throws Exception {
        waitForPageToLoad();
        WebElement siteSecurity = selectionBoxBySname("S:Site Security Issues")
                .get(0);
        String value = getFirstSelectedOptionInDropdown(siteSecurity);
        if (value.equals("")) {
            return true;
        }
        return false;
    }
    public boolean verifyDocumentUploadedByTextField() throws Exception {
        waitForPageToLoad();
        scrollToElement(
                inputBoxDataBySnameByIndex("S:EME Document Uploaded By").get(1)
        );
        //List<WebElement> dropDownField = findAll(signageVisitDrpField);
        String value = inputBoxDataBySnameByIndex("S:EME Document Uploaded By")
                .get(1)
                .getAttribute("class");
        boolean Flag = false;
        if (value.contains("text")) {
            Flag = true;
        }
        return Flag;
    }

    public boolean verifyDocTrackerErrorTextField() throws Exception {
        waitForPageToLoad();
        scrollToElement(find(DocTrackerError));
        //List<WebElement> dropDownField = findAll(signageVisitDrpField);
        String value = find(DocTrackerError).getAttribute("class");
        boolean Flag = false;
        if (value.contains("textarea")) {
            Flag = true;
        }
        return Flag;
    }

    public boolean verifySiteSafetyCommentTextField() throws Exception {
        waitForPageToLoad();
        scrollToElement(textAreaBySname("S:Site Safety Comments"));
        //List<WebElement> dropDownField = findAll(signageVisitDrpField);
        String value = textAreaBySname("S:Site Safety Comments")
                .getAttribute("class");
        boolean Flag = false;
        if (value.contains("textarea")) {
            Flag = true;
        }
        return Flag;
    }

    public boolean verifyEnvironmentalHazardTextField() throws Exception {
        waitForPageToLoad();
        scrollToElement(textAreaBySname("S:Site Environmental Hazards"));
        //List<WebElement> dropDownField = findAll(signageVisitDrpField);
        String value = textAreaBySname("S:Site Environmental Hazards")
                .getAttribute("class");
        boolean Flag = false;
        if (value.contains("textarea")) {
            Flag = true;
        }
        return Flag;
    }

    public boolean verifyBackhaulTypeTextField() throws Exception {
        waitForPageToLoad();
        scrollToElement(inputBoxDataBySnameByIndex("S:Site Backhaul Type").get(1));
        //List<WebElement> dropDownField = findAll(signageVisitDrpField);
        String value = inputBoxDataBySnameByIndex("S:Site Backhaul Type")
                .get(1)
                .getAttribute("class");
        boolean Flag = false;
        if (value.contains("text")) {
            Flag = true;
        }
        return Flag;
    }

    public boolean verifyFaaNotamTextField() throws Exception {
        waitForPageToLoad();
        scrollToElement(inputBoxDataBySname("S:FAA Notam Contact"));
        //List<WebElement> dropDownField = findAll(signageVisitDrpField);
        String value = inputBoxDataBySname("S:FAA Notam Contact")
                .getAttribute("class");
        boolean Flag = false;
        if (value.contains("text")) {
            Flag = true;
        }
        return Flag;
    }

    public String validateDocumentUploadedByUserFieldIsReadOnly()
            throws Exception {
        waitForPageToLoad();
        WebElement fieldName = find(documentUploadedByUserField);
        // scrollToElement(fieldName);
        String readOnly = fieldName.getAttribute("readonly");
        return readOnly;
    }

    public String validateDocTrackerErrorFieldIsReadOnly() throws Exception {
        waitForPageToLoad();
        scrollToElement(find(DocTrackerError));
        WebElement fieldName = find(DocTrackerError);
        //List<WebElement> dropDownField = findAll(signageVisitDrpField);
        String readOnly = fieldName.getAttribute("readonly");
        return readOnly;
    }
    public boolean updateSiteSecurityWithFirstOption() throws Exception {
        waitForPageToLoad();
        scrollToElement(find(siteSecurity));
        WebElement dropDown = selectionBoxBySname("S:Site Security Issues").get(0);
        selectDropdownOptionByIndex(dropDown, 1);
        click(find(applyButton));
        if (!isAlertPresent()) {
            return true;
        }
        return false;
    }

    public boolean updateSignageVisitWithFirstOption() throws Exception {
        waitForPageToLoad();
        scrollToElement(selectionBoxBySname("S:EME Signage Visit Required").get(1));
        WebElement dropDown = selectionBoxBySname("S:EME Signage Visit Required")
                .get(1);
        selectDropdownOptionByIndex(dropDown, 1);
        click(find(applyButton));
        if (!isAlertPresent()) {
            return true;
        }
        return false;
    }

    public boolean signageVisitLockVerification(String secInfo) throws Exception {
        waitForPageToLoad();
        WebElement lock = lockByLabel(secInfo);
        System.out.println(lock.getAttribute("class"));
        if (lock.getAttribute("class").contains("lock-icon locked")) {
            return true;
        } else {
            return false;
        }
    }

    public void clickTheLockedIcon() throws Exception {
        waitForPageToLoad();
        click(find(signageVisitLock));
        waitForPageToLoad();
    }

    public boolean updateSignageVisitWithSecondOption() throws Exception {
        waitForPageToLoad();
        scrollToElement(selectionBoxBySname("S:EME Signage Visit Required").get(1));
        WebElement dropDown = selectionBoxBySname("S:EME Signage Visit Required")
                .get(1);
        selectDropdownOptionByIndex(dropDown, 2);
        click(find(applyButton));
        if (!isAlertPresent()) {
            return true;
        }
        return false;
    }

    public boolean updateSiteSecurityWithSecondOption() throws Exception {
        waitForPageToLoad();
        scrollToElement(find(siteSecurity));
        WebElement dropDown = selectionBoxBySname("S:Site Security Issues").get(0);
        selectDropdownOptionByIndex(dropDown, 2);
        click(find(applyButton));
        if (!isAlertPresent()) {
            return true;
        }
        return false;
    }

    public boolean updateSiteSecurityWithThirdOption() throws Exception {
        waitForPageToLoad();
        scrollToElement(find(siteSecurity));
        WebElement dropDown = selectionBoxBySname("S:Site Security Issues").get(0);
        selectDropdownOptionByIndex(dropDown, 3);
        click(find(applyButton));
        if (!isAlertPresent()) {
            return true;
        }
        return false;
    }

    public boolean updateSiteSecurityWithFourthOption() throws Exception {
        waitForPageToLoad();
        scrollToElement(find(siteSecurity));
        WebElement dropDown = selectionBoxBySname("S:Site Security Issues").get(0);
        selectDropdownOptionByIndex(dropDown, 4);
        click(find(applyButton));
        if (!isAlertPresent()) {
            return true;
        }
        return false;
    }

    public String validateAuditPhotosIcon() throws Exception {
        waitForPageToLoad();
        scrollToElement(
                inputBoxDataBySnameByIndex("S:EME Audit Photos [Doc]").get(1)
        );
        WebElement fieldName = find(photoIcon);

        //List<WebElement> dropDownField = findAll(signageVisitDrpField);
        String icon = fieldName.getAttribute("class");
        return icon;
    }

    public void goToRiotTab() throws Exception {
        //parentWindow = switchToChildWindows();
        // fullScreen();
        waitForPageToLoad();
        click(find(riotTab));
        waitForPageToLoad();
    }

    public boolean auditLastPerformedDateValidation() throws Exception {
        click(find(applyButton));
        waitForPageToLoad();
        click(find(FOPSInfoTab));
        waitForPageToLoad();
        WebElement date = inputBoxDataBySnameByIndex(
                "S:EME Audit Last Performed Date"
        )
                .get(1);
        String value = date.getAttribute("title");
        if (!value.isEmpty()) {
            return true;
        }
        return false;
    }
    public boolean validateTheImageInRiotTab() throws Exception {
        click(find(applyButton));
        waitForPageToLoad();
        //click(find(fopsTab));
        //waitForPageToLoad();
        WebElement date = inputBoxDataBySnameByIndex("S:EME Audit Photos [Doc]").get(1);
        String value = date.getAttribute("title");
        if (!value.isEmpty() && value.contains("png")) {
            return true;
        }
        return false;
    }

    public boolean auditSignageExpirationDateValidation() throws Exception {
        //click(find(applyButton));
        //waitForPageToLoad();
        //click(find(fopsTab));
        // waitForPageToLoad();
        WebElement date = inputBoxDataBySnameByIndex(
                "S:EME Audit Last Performed Date"
        )
                .get(1);
        String value = date.getAttribute("title");
        if (!value.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean documentUploadedByValidation() throws Exception {
        //click(find(applyButton));
        //waitForPageToLoad();
        // click(find(fopsTab));
        //waitForPageToLoad();
        WebElement date = inputBoxDataBySnameByIndex("S:EME Document Uploaded By")
                .get(1);
        String value = date.getAttribute("title");
        if (!value.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean expirationDateValidation() throws Exception {
        WebElement date = inputBoxDataBySnameByIndex(
                "S:EME Audit Signage Expiration Date"
        )
                .get(1);
        String value = date.getAttribute("title");
        String actualDate = value.split("")[1];
        String futureDate = MiscHelpers.specificFutureDateTime("MM/dd/yyyy", 450);
        if (actualDate.equals(futureDate)) {
            return true;
        }
        return false;
    }

    public boolean verifySignageVisitInitialValue() throws Exception {
        WebElement siteSecurity = selectionBoxBySname(
                "S:EME Signage Visit Required"
        )
                .get(1);
        scrollToElement(siteSecurity);
        String value = getFirstSelectedOptionInDropdown(siteSecurity);
        if (value.equals("")) {
            return true;
        }
        return false;
    }

    public boolean siteSafetyCommentsValidation() throws Exception {
        scrollToElement(textAreaBySname("S:Site Safety Comments"));
        textAreaBySname("S:Site Safety Comments").sendKeys(characters4K);
        click(find(applyButton));
        if (!isAlertPresent()) {
            return true;
        }
        return false;
    }

    public boolean siteEnvironmentalHazardValidation() throws Exception {
        scrollToElement(textAreaBySname("S:Site Environmental Hazards"));
        textAreaBySname("S:Site Environmental Hazards").sendKeys(characters4K);
        click(find(applyButton));
        if (!isAlertPresent()) {
            return true;
        }
        return false;
    }

    public String validateFaaNotamIsNotReadOnly_Safety() throws Exception {
        waitForPageToLoad();
        WebElement faaNotam = inputBoxDataBySname("S:FAA Notam Contact");
        // scrollToElement(fieldName);
        String readOnly = faaNotam.getAttribute("readonly");
        return readOnly;
    }

    public boolean updateObstructionLighting_safety() throws Exception {
        waitForPageToLoad();
        scrollToElement(selectionBoxBySname("S:Obstruction Lighting/Marking Required?").get(0));
        WebElement dropDown = selectionBoxBySname("S:Obstruction Lighting/Marking Required?").get(0);
        selectDropdownOptionByIndex(dropDown, 1);
        setText(inputBoxDataBySname("S:FAA Notam Contact"),"");
        click(find(applyButton));
        if (isAlertPresent()) {
            acceptAlert();
            return true;
        }
        return false;
    }
    public boolean updateObstructionLightingWithFaaNotam_safety() throws Exception {
        waitForPageToLoad();
        scrollToElement(selectionBoxBySname("S:Obstruction Lighting/Marking Required?").get(0));
        WebElement dropDown = selectionBoxBySname("S:Obstruction Lighting/Marking Required?").get(0);
        selectDropdownOptionByIndex(dropDown, 1);
        inputBoxDataBySname("S:FAA Notam Contact").sendKeys("Test");
        click(find(applyButton));
        if (!isAlertPresent()) {
            return true;
        }
        return false;
    }

    public boolean riotTabVerification() throws Exception {
        parentWindow = switchToChildWindows();
        fullScreen();
        waitForPageToLoad();
        waitUntilVisibleElement(find(okButton));
        Boolean tab = isDisplayed(riotTab);
        return tab;
    }
    public boolean verifySieRiotIsDisplayed_SiteRiot() throws Exception {
        waitForPageToLoad();
        WebElement sectionNameText = find(siteRiotSection);
        scrollToElement(sectionNameText);
        boolean fieldName = sectionNameText.isDisplayed();
        return fieldName;
    }
    public boolean verifyRiotPhotoUploadIsDisplayed_SiteRiot() throws Exception {
        waitForPageToLoad();
        WebElement sectionNameText = find(photoUploadSection);
        scrollToElement(sectionNameText);
        boolean fieldName = sectionNameText.isDisplayed();
        return fieldName;
    }
    public void goToRiotTab_SiteRiot() throws Exception {
        waitForPageToLoad();
        parentWindow = switchToChildWindows();
        fullScreen();
        waitForPageToLoad();
        click(find(riotTab));
        waitForPageToLoad();
    }
    public boolean validateFieldIsDisplayed_SiteRiot(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldNameText = fieldByLabelTextIndex(name).get(1);
        scrollToElement(fieldNameText);
        boolean fieldName = fieldNameText.isDisplayed();
        return fieldName;
    }
    public String validateAuditPhotosIsEditable_SiteRiot() throws Exception {
        waitForPageToLoad();
        WebElement auditPhoto = inputBoxDataBySname("S:EME Audit Photos [Doc]");
        // scrollToElement(fieldName);
        String readOnly = auditPhoto.getAttribute("readonly");
        return readOnly;
    }
    public boolean documentUploadedByValidation_SiteRiot() throws Exception {
        click(find(applyButton));
        waitForPageToLoad();
        //click(find(fopsTab));
        //waitForPageToLoad();
        WebElement date = inputBoxDataBySnameByIndex("S:EME Document Uploaded By").get(1);
        String value = date.getAttribute("title");
        if (!value.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean lastPerformedDateValidation_SiteRiot() throws Exception {
        //click(find(applyButton));
        waitForPageToLoad();
        //click(find(fopsTab));
        //waitForPageToLoad();
        WebElement date = inputBoxDataBySnameByIndex("S:EME Audit Last Performed Date").get(1);
        String value = date.getAttribute("title");
        if (!value.isEmpty()) {
            return true;
        }
        return false;
    }
    public boolean auditSignageExpirationDateValidation_SiteRiot() throws Exception {
        //click(find(applyButton));
        waitForPageToLoad();
        //click(find(fopsTab));
        //waitForPageToLoad();
        WebElement date = inputBoxDataBySnameByIndex("S:EME Audit Signage Expiration Date").get(2);
        String value = date.getAttribute("title");
        if (!value.isEmpty()) {
            return true;
        }
        return false;
    }
    public String validateAuditLastPerformedDate_SiteRiot() throws Exception {
        waitForPageToLoad();
        WebElement lastDate = inputBoxDataBySnameByIndex("S:EME Audit Last Performed Date").get(1);
        // scrollToElement(fieldName);
        String readOnly = lastDate.getAttribute("readonly");
        return readOnly;
    }
    public String validateAuditSignageExpirationDate_SiteRiot() throws Exception {
        waitForPageToLoad();
        WebElement auditPhoto = inputBoxDataBySnameByIndex("S:EME Audit Signage Expiration Date").get(1);
        // scrollToElement(fieldName);
        String readOnly = auditPhoto.getAttribute("readonly");
        return readOnly;
    }
    public String validateDocumentUploadedBy_SiteRiot() throws Exception {
        waitForPageToLoad();
        WebElement auditPhoto = inputBoxDataBySnameByIndex("S:EME Document Uploaded By").get(1);
        // scrollToElement(fieldName);
        String readOnly = auditPhoto.getAttribute("readonly");
        return readOnly;
    }
    public boolean auditPhotosValidation_SiteRiot() throws Exception {
        click(find(applyButton));
        waitForPageToLoad();
        //click(find(fopsTab));
        //waitForPageToLoad();
        WebElement date = inputBoxDataBySnameByIndex("S:EME Audit Photos [Doc]").get(1);
        String value = date.getAttribute("title");
        if (!value.isEmpty() && value.contains("png")) {
            return true;
        }
        return false;
    }
    public void clickTheLockedIcon_riot() throws Exception {
        click(find(applyButton));
        waitForPageToLoad();
        click(find(auditPhotoLock));
        setText(inputBoxDataBySnameByIndex("S:EME Audit Photos [Doc]").get(1),"");
        waitForPageToLoad();
    }
    public void goToDocumentTab() throws Exception{
        click(find(applyButton));
        waitForPageToLoad();
        click(find(documentTab));
        waitForPageToLoad();
    }

    public boolean currentVersionValidation_SiteRiot() throws Exception {
        sleep(5);
        WebElement checkBox = find(currentVersionCheckBox);
        if (checkBox.isSelected()) {
            return true;
        }
        return false;
    }
    public String getFirstDocumentText_SiteRiot() throws Exception {
        click(find(applyButton));
        waitForPageToLoad();
        String value = inputBoxDataBySnameByIndex("S:EME Audit Last Performed Date").get(1).getAttribute("title");
        click(find(auditPhotoLock));
        setText(inputBoxDataBySnameByIndex("S:EME Audit Photos [Doc]").get(1), "");
        waitForPageToLoad();
        return value;
    }


    public String getSecondDocumentText_SiteRiot() throws Exception {
        click(find(applyButton));
        waitForPageToLoad();
        String value = inputBoxDataBySnameByIndex("S:EME Audit Last Performed Date").get(1).getAttribute("title");
        //click(find(auditPhotoLock));
        //setText(inputBoxDataBySnameByIndex("S:EME Audit Photos [Doc]").get(1),"");
        // waitForPageToLoad();
        return value;
    }
    public boolean auditExpirationValidation_SiteRiot() throws Exception {
        click(find(applyButton));
        waitForPageToLoad();
        WebElement date = inputBoxDataBySnameByIndex("S:EME Audit Last Performed Date").get(1);
        String value = date.getAttribute("title");
        if (!value.isEmpty()) {
            return true;
        }
        return false;
    }
    public boolean expirationDateValidation_SiteRiot() throws Exception {
        WebElement date = inputBoxDataBySnameByIndex("S:EME Audit Last Performed Date").get(1);
        String value = date.getAttribute("title");
        String actualDate = value.split("")[1];
        String futureDate = MiscHelpers.specificFutureDateTime("MM/dd/yyyy", 450);
        if (actualDate.equals(futureDate)) {
            return true;
        }
        return false;
    }
    public String getFirstDocumentExpiryDate_SiteRiot() throws Exception {
        // click(find(applyButton));
        // waitForPageToLoad();
        String value = inputBoxDataBySnameByIndex("S:EME Audit Signage Expiration Date").get(1).getAttribute("title");
        click(find(auditPhotoLock));
        setText(inputBoxDataBySnameByIndex("S:EME Audit Photos [Doc]").get(1),"");
        waitForPageToLoad();
        return value;
    }
    public String getSecondDocumentExpiryDate_SiteRiot() throws Exception {
        click(find(applyButton));
        waitForPageToLoad();
        String value = inputBoxDataBySnameByIndex("S:EME Audit Signage Expiration Date").get(1).getAttribute("title");
        //click(find(auditPhotoLock));
        //setText(inputBoxDataBySnameByIndex("S:EME Audit Photos [Doc]").get(1),"");
        waitForPageToLoad();
        return value;
    }
    public String getFirstDocumentUploadedBy_SiteRiot() throws Exception {
        click(find(applyButton));
        waitForPageToLoad();
        String value = inputBoxDataBySnameByIndex("S:EME Document Uploaded By").get(1).getAttribute("title");
        click(find(auditPhotoLock));
        setText(inputBoxDataBySnameByIndex("S:EME Audit Photos [Doc]").get(1),"");
        waitForPageToLoad();
        return value;
    }
    public String getSecondDocumentUploadedBy_SiteRiot() throws Exception {
        click(find(applyButton));
        waitForPageToLoad();
        String value = inputBoxDataBySnameByIndex("S:EME Audit Signage Expiration Date").get(1).getAttribute("title");
        //click(find(auditPhotoLock));
        //setText(inputBoxDataBySnameByIndex("S:EME Audit Photos [Doc]").get(1),"");
        waitForPageToLoad();
        return value;
    }
    public boolean isFieldDropDown(String sName) throws Exception {
        waitForPageToLoad();
        WebElement selectBox = selectionBoxBySname(sName).get(0);
        scrollToElement(selectBox);
        List<WebElement> dropDownField = selectGetDropdownOptions(selectBox);
        if (dropDownField.size() > 0) {
            System.out.println(dropDownField.size());
            return true;
        }
        return false;
    }

    public boolean isFieldCheckbox(String fieldName) throws Exception {
        waitForPageToLoad();
        WebElement checkBoxField = checkBoxByLabelInput(fieldName);
        scrollToElement(checkBoxField);
        String value = checkBoxField.getAttribute("type");
        return value.contains("checkbox");
    }

    public boolean verifyInputBoxAndValue(String sName, String value)
            throws Exception {
        waitForPageToLoad();
        WebElement inputBox = inputBoxDataBySname(sName);
        String inputText = inputBox.getAttribute("title");
        return inputText.contains(value);
    }

    public boolean isFieldInputBox(String sName) throws Exception {
        waitForPageToLoad();
        List<WebElement> inputBox = inputBoxDataBySnameByIndex(sName);
        return inputBox.size() > 0;
    }

    public boolean isFieldTextArea(String sName) throws Exception {
        waitForPageToLoad();
        List<WebElement> textArea = textAreaBySnameAll(sName);
        return textArea.size() > 0;
    }

    public boolean updateInputBoxText(String sName, String text)
            throws Exception {
        waitForPageToLoad();
        WebElement inputBox = inputBoxDataBySname(sName);
        scrollToElement(inputBox);
        setText(inputBox, text);
        return applySelection();
    }

    public boolean updateInputTextArea(String sName, String text)
            throws Exception {
        waitForPageToLoad();
        WebElement textArea = textAreaBySname(sName);
        scrollToElement(textArea);
        setText(textArea, text);
        return applySelection();
    }

    public boolean verifyTextInTextArea(String sName, String text)
            throws Exception {
        waitForPageToLoad();
        WebElement textArea = textAreaBySname(sName);
        scrollToElement(textArea);
        System.out.println("__" + getText(textArea) + "__");
        return getText(textArea).contains(text);
    }

    public String getInputText(String sName) throws Exception {
        waitForPageToLoad();
        WebElement inputBox = inputBoxDataBySname(sName);
        scrollToElement(inputBox);
        return getText(inputBox);
    }

    public boolean validateFieldIsReadOnly_Power(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldName = inputBoxDataBySname(name);
        scrollToElement(fieldName);
        String readOnly = fieldName.getAttribute("readonly");
        return readOnly != null;
    }

    public boolean validateDrpFieldIsReadOnly_Power(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldName = selectionBoxBySname(name).get(0);
        scrollToElement(fieldName);
        String readOnly = fieldName.getAttribute("readonly");
        return readOnly != null;
    }

    public boolean validateTextAreaFieldIsReadOnly(String name) throws Exception {
        waitForPageToLoad();
        WebElement fieldName = textAreaBySname(name);
        scrollToElement(fieldName);
        String readOnly = fieldName.getAttribute("readonly");
        return readOnly != null;
    }

    public void checkUncheckCheckBox(String sName) throws Exception {
        waitForPageToLoad();
        WebElement checkBoxField = checkBoxByLabel(sName);
        scrollToElement(checkBoxField);
        click(checkBoxField);
    }

    public boolean applySelection() throws Exception {
        click(find(applyButton));
        sleep(10);
        if (!isAlertPresent()) {
            //No alert
            waitForPageToLoad();
            sleep(5);
            return true;
        } else {
            //got an alert
            acceptAlert();
            sleep(5);
            return false;
        }
    }

    public List<String> fieldDropDownValues(String sName) throws Exception {
        waitForPageToLoad();
        List<String> options = new ArrayList<String>();
        WebElement selectBox = selectionBoxBySname(sName).get(0);
        scrollToElement(selectBox);
        List<WebElement> dropDownField = selectGetDropdownOptions(selectBox);
        if (dropDownField.size() > 0) {
            for (int i = 0; i < dropDownField.size(); i++) {
                options.add(getText(dropDownField.get(i)));
            }
            return options;
        }
        return null;
    }

    public void selectDropDownOption(String sName, String option)
            throws Exception {
        waitForPageToLoad();
        WebElement selectBox = selectionBoxBySname(sName).get(0);
        scrollToElement(selectBox);
        selectDropdownOption(selectBox, option);
    }
    public void switchToTrackerPage(String parentWindow) throws Exception {
        click(find(okButton));
        switchToSpecificWindow(parentWindow);
        fullScreenChildWindow();
    }
}
