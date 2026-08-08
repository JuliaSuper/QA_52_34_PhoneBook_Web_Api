package pages;

import dto.UserLombok;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        PageFactory.initElements
                (new AjaxElementLocatorFactory(driver, 10),
                        this);
    }

    @FindBy(xpath = "//form/input[1]")
    WebElement imputEmail;
    @FindBy(css = "input[placeholder='Password']")
    WebElement imputPassword;
    @FindBy(xpath = "//button[@type='submit' and @name='registration']")
    WebElement btnRegister;
    @FindBy(xpath = "//button[text()='Login']")
    WebElement btnLogin;

    public void typeLoginRegistrationForm(UserLombok user) {
        imputEmail.sendKeys(user.getUsername());
        imputPassword.sendKeys(user.getPassword());
    }

    public void clickBtnRegistration() {
        btnRegister.click();
    }
}
