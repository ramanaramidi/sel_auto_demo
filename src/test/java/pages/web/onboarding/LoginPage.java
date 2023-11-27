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


    public By emailAddressInputBox = By.xpath("//input[@type = 'email']");
    public By submitEmailAddress = By.xpath("//input[@type = 'submit']");
    public By userOrgHrm = By.xpath("//input[@name='username']");
    public By passwordOrgHrm = By.xpath("//input[@name='password']");
    public By loginSubmit = By.xpath("//button[text()=' Login ']");
    public By authNote = By.xpath("//div[@id='auth_message']");

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



    public MainSideMenu LoginOrgHrm() throws Exception {

        sleep(5);

        return new MainSideMenu(driver);

    }

    public void logInOrgHrm(Users user) throws Exception {

        setText(find(userOrgHrm),user.getUserName());
        setText(find(passwordOrgHrm),user.getPassword());
        find(loginSubmit).click();
        sleep(1);





    }
}
