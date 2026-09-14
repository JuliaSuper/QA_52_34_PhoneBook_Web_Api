package ui_tests;

import dto.UserLombok;
import manedger.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;

import static utils.PropertiesReader.*;
import data_providers.UserDataProvider;
import utils.TestNGListener;

@Listeners(TestNGListener.class)

public class LoginTests extends AppManager {
    LoginPage loginPage;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void goToLoginPage() {
        new HomePage(getDriver()).clickBtnLogin();
        loginPage = new LoginPage(getDriver());


    }

    @Test(groups = "smoke")
    public void loginPositiveTests() {
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
                contactsPage.isLinkContacktsDisplayed(),
                "validate isLinkContactsDisplayed");
        softAssert.assertTrue(contactsPage.isUrlContactsText("contacts"), "validate url");
        softAssert.assertAll();
    }

    @Test
    public void loginNegativeWrongPasswordTests() {
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

    @Test
    public void loginNegativeUnregisteredEmailTest() {
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "emailUnregistered"))
                .password(getProperty("base.properties", "passwordUnregistered"))
                .build();

        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();

        String alertText = loginPage.closeAlert();
        Assert.assertTrue(alertText.contains("Wrong email or password"),
                "Alert message for unregistered email");
    }

    @Test
    public void loginNegativeAllFieldsEmptyWOTypeFormTests() {
        loginPage.clickBtnLogin();
//        Assert.assertTrue(loginPage.closeAlert().contains("Wrong email or password"));
        Assert.assertEquals(loginPage.closeAlert(),
                "Wrong email or password");

    }

    @Test
    public void loginNegativeEmptyEmailTest() {
        UserLombok user = UserLombok.builder()
                .username("")
                .password(getProperty("base.properties", "password"))
                .build();

        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();

        Assert.assertTrue(loginPage.closeAlert().contains("Wrong email or password"),
                "Alert message when email is empty");
    }

    @Test
    public void loginNegativeEmptyPasswordTest() {
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "email"))
                .password("")
                .build();

        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();

        Assert.assertTrue(loginPage.closeAlert().contains("Wrong email or password"),
                "Alert message when password is empty");
    }

    @Test
    public void loginNegativeInvalidEmailFormatWithoutAtTest() {
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "emailInvalid"))
                .password(getProperty("base.properties", "password"))
                .build();

        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();

        Assert.assertTrue(loginPage.closeAlert().contains("Wrong email or password"),
                "Alert message when email has no '@'");
    }

    @Test
    public void loginNegativeInvalidEmailFormatWithoutDomainTest() {
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "invalidEmail"))
                .password(getProperty("base.properties", "password"))
                .build();

        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();

        Assert.assertTrue(loginPage.closeAlert().contains("Wrong email or password"),
                "Alert message when email has no domain");
    }

    @Test(dataProvider = "dataProviderWrongPasswordOrEmail", dataProviderClass = UserDataProvider.class)
    public void loginNegativeWrongEmailOrPasswordFromCsvTest(UserLombok user) {
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();

        Assert.assertTrue(loginPage.closeAlert().contains("Wrong email or password"),
                "Alert message for CSV data-driven invalid credentials");
    }
}
