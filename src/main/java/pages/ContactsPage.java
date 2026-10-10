package pages;

import dto.ContactDto;
import dto.UserLombok;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class ContactsPage extends BasePage{
    public ContactsPage(WebDriver driver) {
        PageFactory.initElements
                (new AjaxElementLocatorFactory(driver, 10),
                        this);
    }
    @FindBy(xpath = "//h1[text()=' No Contacts here!']")
    WebElement messageNoContacts;
    @FindBy(xpath = "//a[@href='/contacts']")
    WebElement linkContacts;
    @FindBy(xpath = "//input[@placeholder='Name']")
    WebElement inputName;
    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement inputLastName;
    @FindBy(xpath = "//input[@placeholder='Phone']")
    WebElement inputPhone;
    @FindBy(xpath = "//input[@placeholder='email']")
    WebElement inputContactEmail;
    @FindBy(xpath = "//input[@placeholder='Address']")
    WebElement inputAddress;
    @FindBy(xpath = "//input[@placeholder='description']")
    WebElement inputDescription;
    @FindBy(xpath = "//b[text() ='Save']")
    WebElement btnSave;

    public void typeContact(ContactDto contactDto) {
        inputName.sendKeys(contactDto.getName());
        inputLastName.sendKeys(contactDto.getLastName());
        inputContactEmail.sendKeys(contactDto.getEmail());
        inputPhone.sendKeys(contactDto.getPhone());
        inputAddress.sendKeys(contactDto.getAddress());
        inputDescription.sendKeys(contactDto.getDescription());
    }

    public void clickBtnSave() {
        btnSave.click();
    }

    public boolean validateTextInMessageNoContacts(String text){
        return isTextInElementPresent(messageNoContacts, text) ;
    }

    public boolean isLinkContactsDisplayed(){
        return linkContacts.isDisplayed();

    }

}
