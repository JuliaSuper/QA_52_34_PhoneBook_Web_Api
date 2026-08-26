package pages;

import dto.UserLombok;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class RegistrationPage extends BasePage {
    public RegistrationPage(WebDriver driver) {
        PageFactory.initElements(new AjaxElementLocatorFactory
                (driver, 10), this);
    }

    @FindBy(xpath = "//form/input[1]")
    WebElement inputEmail;
    @FindBy(xpath = "//form/input[2]")
    WebElement inputPassword;
    @FindBy(xpath = "//button[@type='submit' and @name='registration']")
    WebElement btnRegister;


    public void typeLoginRegistrationForm(UserLombok user) {
        inputEmail.clear();
        inputEmail.sendKeys(user.getUsername());
        inputPassword.clear();
        inputPassword.sendKeys(user.getPassword());
    }

    public void clickBtnRegistration() {
        btnRegister.click();
    }

    public boolean isBtnRegistrationDisplayed() {
        return btnRegister.isDisplayed();
    }
}

