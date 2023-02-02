package pages.web.onboarding;

import commons.enums.LoginOptionEnum;
import commons.objects.Users;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import pages.web.components.MainSideMenu;
import testData.UserData;

import java.util.List;

import static java.lang.Thread.sleep;

public class LoginPage extends BasePage {

    public WebDriver driver;
    public Users user = new Users();
    //private WebDriverWait wait = new WebDriverWait(driver, 60);

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public By currentAuthMethod = By.xpath("//*[@id = 'auth_method-current']");
    public By authMethodOptions = By.xpath("//*[@id = 'auth_method-list']/child::*");
    public By authSelection = By.xpath("//input[@class='auth_button auth_button_sso auth_submit']");
    public By emailAddressInputBox = By.xpath("//input[@type = 'email']");
    public By submitEmailAddress = By.xpath("//input[@type = 'submit']");
    public By profileImage = By.xpath("//img[@id='imgUserPicture']");
    public By loginAsOption = By.xpath("//div[@id='itemLoginAs']//div[@class='ic_container']");
    public By userOptionSelector = By.xpath("//input[@id='btnusername']");
    public By userSelectionInputBox = By.xpath("//input[@id='qsValue0']");
    public By userSearchButton = By.xpath("//input[@id='btnSearch0']");
    public By userSelection = By.xpath("//input[@id='btnOK0']");
    public By loginAsConfirmation = By.xpath("//input[@id='btnOK']");
    public By email = By.xpath("//input[@id='username']");
    public By password = By.xpath("//input[@id='password']");
    public By loginSubmit = By.xpath("//input[@type='submit']");
    public By authNote = By.xpath("//div[@id='auth_message']");
    public By loginButton = By.xpath("//*[@id='btn_11033']");


    public MainSideMenu LoginAsUser(Users user) throws Exception {
        fullScreenChildWindow();
        waitUntilVisibleElement(find(profileImage));
        click(find(profileImage));
        click(find(loginAsOption));
        String parent1 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(userOptionSelector));
        click(find(userOptionSelector));
        String parent2 = switchToChildWindows();
        fullScreenChildWindow();
        waitUntilVisibleElement(find(userSelectionInputBox));
        setText(find(userSelectionInputBox),user.getNtCode());
        click(find(userSearchButton));
        tableRadioButtonClickWithExactValue("User Name", user.getNtCode());
        click(find(userSelection));
        switchToSpecificWindow(parent2);
        waitUntilVisibleElement(find(loginAsConfirmation));
        click(find(loginAsConfirmation));
        switchToSpecificWindow(parent1);
        sleep(10);
        waitForPageToLoad();
        return new MainSideMenu(driver);
    }

    public Users getUserCredentials(String userType){
        if (userType.equalsIgnoreCase("")){
            System.out.println("UNDER CONSTRUCTION");
            return null;
        }
        else{
            return UserData.getAlphaUserDetails(user);
        }
    }

    public String getLoginUrl(Users userType) throws Exception {
        if(userType.getUser()==""){
            return null;
        }
        else{
            Users currentUser = userType;
            setEmailAddress(currentUser);
            submitEmailAddress();
            sleep(1);
            if(findAll(authNote).size()>0) {
                if (getText(findAll(authNote).get(0)).equals("")) {
                    System.out.println("Login was a success in first attempt");
                } else {
                    submitEmailAddress();
                    System.out.println("Login second attempt");
                }
            }
            sleep(15);
            if(currentUser.getIsContractor().equals("false")){
                return null;
            }
            return getBasicAuthUrl(currentUser);
        }
    }

    public void launchUrl(String url){
        driver.navigate().to(url);
    }

    public void submitEmailAddress() throws Exception {
        find(submitEmailAddress).click();
    }

    public void selectAuthMethod(LoginOptionEnum option) throws Exception {
        WebElement currentAuth = find(currentAuthMethod);
        List<WebElement> authOptions = findAll(authMethodOptions);
        String currentSelection = currentAuth.getText();
        if (!currentSelection.contains(option.name())) {
            currentAuth.click();
            sleep(2);
            System.out.println("count = " + authOptions.size());
            if (authOptions.get(1).getText().contains(option.name()))
                authOptions.get(1).click();
            else
                authOptions.get(0).click();
        }
    }

    public void doLogin(LoginOptionEnum option) throws Exception {
        sleep(2);
        selectAuthMethod(option);
        sleep(2);
        if (option.equals(LoginOptionEnum.SAML)) {
            find(authSelection).click();
        } else {
            System.out.println("TRYING A NORMAL LOGIN...");
        }
    }

    public void login(Users user) throws Exception {
        setText(find(email),user.getUserName());
        setText(find(password),user.getPassword());
        find(loginSubmit).click();
        sleep(1);
        if(findAll(authNote).size()>0) {
            if (getText(findAll(authNote).get(0)).equals("")) {
                System.out.println("::" + "LOGIN WAS SUCCESSFUL IN FIRST ATTEMPT");
            } else {
                setText(find(email), user.getUserName());
                setText(find(password), user.getPassword());
                find(loginSubmit).click();
                System.out.println("::" + "LOGIN WAS TRIED AGAIN");
            }
        }
        sleep(15);
    }

    public String getLoginPageTitle() {
        sleep(5);
        return getTitle();
    }

    public void setEmailAddress(Users currentUser) throws Exception {
        WebElement emailAddressBox = find(emailAddressInputBox);
        String email = currentUser.getUserName();
        waitUntilVisibleElement(emailAddressBox);
        clearEmailAddressBox();
        emailAddressBox.sendKeys(email + Keys.TAB);
    }

    public String getBasicAuthUrl(Users currentUser){
        String currentUrl = getPageUrl();
        String adConnection = "https://" + currentUser.getNtCode() + ":" + currentUser.getPassword()+"@";
        currentUrl = currentUrl.replace("https://", adConnection);
        System.out.println(currentUrl);
        return currentUrl;
    }

    public void clearEmailAddressBox() throws Exception {
        clearInputBoxByElementAndSendKeys(find(emailAddressInputBox));
    }

    public void userLogin(Users alphaUser) throws Exception{
        if(alphaUser.getIsServiceAccount().equals("true")){
            doLogin(LoginOptionEnum.UN_EMAIL);
            login(alphaUser);
        }
        else{
            waitUntilVisibleElement(find(loginButton));
            click(find(loginButton));
            sleep(5);
        }
    }
}
