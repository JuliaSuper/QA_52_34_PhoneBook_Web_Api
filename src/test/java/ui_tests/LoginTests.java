package ui_tests;

import dto.UserLombok;
import manedger.AppManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;

import static utils.PropertiesReader.getProperty;

public class LoginTests extends AppManager {
    LoginPage loginPage;
    SoftAssert softAssert= new SoftAssert();

    @BeforeMethod
    public void goToLoginPage(){
        softAssert = new SoftAssert();
        new HomePage(getDriver()).clickBtnLogin();
        loginPage = new LoginPage(getDriver());
    }
    @Test
    public void loginPositiveTests(){
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "email"))
                .password(getProperty("base.properties", "password"))
                .build();

        softAssert.assertTrue(loginPage.isBtnLoginDisplayed(),
                "The Login button is displayed before signing in");

        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();

        ContactsPage contactsPage = new ContactsPage(getDriver());
        softAssert.assertTrue(
                contactsPage.validateTextInMessageNoContacts
                        ("No Contacts here!"),
                "A message came from the contact page");
        softAssert.assertAll();
    }

    @Test
    public void loginNegativeWrongPasswordTests(){
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "email"))
                .password(getProperty("base.properties", "negativePassword"))
                .build();

        softAssert.assertTrue(loginPage.isBtnLoginDisplayed(),
                "The Login button is displayed before signing in");

        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();

        String alertText = loginPage.closeAlert();
        softAssert.assertTrue(
                alertText.contains("Wrong email or password"),
                "error message in the alert for wrong password");
        softAssert.assertTrue(loginPage.isBtnLoginDisplayed(),
                "Checking the Login button display after a failed login attempt");
        softAssert.assertAll();
    }

}
