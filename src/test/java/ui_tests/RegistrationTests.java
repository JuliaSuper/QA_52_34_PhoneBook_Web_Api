package ui_tests;

import dto.UserLombok;
import manedger.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import data_providers.UserDataProvider;
import pages.RegistrationPage;
import utils.TestNGListener;

import static utils.UserFactory.*;

import java.util.Random;

import static utils.PropertiesReader.getProperty;
import static utils.UserFactory.positiveUser;

@Listeners(TestNGListener.class)

public class RegistrationTests extends AppManager {
    RegistrationPage registrationPage;

    @BeforeMethod
    public void goToRegistrationLoginPage() {
        logger.info("Start registration test");
        new HomePage(getDriver()).clickBtnLogin();
        registrationPage = new RegistrationPage(getDriver());
    }

    @Test(groups = "smoke")
    public void registrationPositiveTest() {
        int i = new Random().nextInt(1000);
        UserLombok user = UserLombok.builder()
                .username("vbfgty" + i + "@fgrty.bh")
                .password("Adfert23!")
                .build();
        registrationPage.typeLoginRegistrationForm(user);
       registrationPage.clickBtnRegistration();
        Assert.assertTrue(new ContactsPage(getDriver())
                .validateTextInMessageNoContacts("No Contacts here!"));
    }

//    @Test
//    public void testMethod(){
//        new HomePage(getDriver()).method();
//    }
//
//    @Test
//    public void testAjaxMethod(){
//        new HomePage(getDriver()).ajaxMethod();
//    }
//

    @Test
    public void registrationPositiveWithFakerTests() {
        UserLombok user = positiveUser();
        System.out.println(user);
        RegistrationPage registrationPage = new RegistrationPage(getDriver());
        registrationPage.typeLoginRegistrationForm(user);
        registrationPage.clickBtnRegistration();
        Assert.assertTrue(new ContactsPage(getDriver())
                .validateTextInMessageNoContacts("No Contacts here!"));
    }

    @Test
    public void registrationNegativeEmptyAllFieldsTests() {
        registrationPage.clickBtnRegistration();
        Assert.assertTrue(registrationPage.closeAlert()
                .contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeEmptyEmailFieldTests() {
        UserLombok user = positiveUser();
        user.setUsername("");
        registrationPage.typeLoginRegistrationForm(user);
        registrationPage.clickBtnRegistration();
        Assert.assertTrue(registrationPage.closeAlert()
                .contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeEmptyPasswordFieldTests() {
        UserLombok user = positiveUser();
        user.setPassword("");
        registrationPage.typeLoginRegistrationForm(user);
        registrationPage.clickBtnRegistration();
        Assert.assertTrue(registrationPage.closeAlert()
                .contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeEmailWithoutAtTest() {
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties","emailInvalid"))
                .password(getProperty("base.properties","password"))
                .build();

        registrationPage.typeLoginRegistrationForm(user);
        registrationPage.clickBtnRegistration();

        Assert.assertTrue(registrationPage.closeAlert().contains("Wrong email or password format"),
                "Alert message when email lacks '@'");
    }

    @Test
    public void registrationNegativeEmailWithoutDomainTest() {
        UserLombok user = UserLombok.builder()
                .username("invalid@.com")
                .password(getProperty("base.properties","password"))
                .build();

        registrationPage.typeLoginRegistrationForm(user);
        registrationPage.clickBtnRegistration();

        Assert.assertTrue(registrationPage.closeAlert().contains("Wrong email or password format"),
                "Alert message when email domain is missing");
    }



    @Test(dataProvider = "dataProviderWrongPasswordOrEmail",
            dataProviderClass = UserDataProvider.class)
    public void registrationNegativeWrongPasswordTests(UserLombok user) {
        registrationPage.typeLoginRegistrationForm(user);
        registrationPage.clickBtnRegistration();
        Assert.assertTrue(registrationPage.closeAlert()
                .contains("Wrong email or password format"));
    }

    @Test(dataProvider = "dataProviderWrongEmailRegistration",
            dataProviderClass = UserDataProvider.class)
    public void registrationNegativeWrongEmailTests(UserLombok user) {
        registrationPage.typeLoginRegistrationForm(user);
        registrationPage.clickBtnRegistration();
        Assert.assertTrue(registrationPage.closeAlert()
                .contains("Wrong email or password format"));
    }
}
