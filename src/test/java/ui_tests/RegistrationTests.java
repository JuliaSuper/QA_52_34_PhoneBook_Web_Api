package ui_tests;

import dto.UserLombok;
import manedger.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;

import static utils.UserFactory.*;

import java.util.Random;

public class RegistrationTests extends AppManager {
    LoginPage loginPage;
    @BeforeMethod
    public void goToRegistrationLoginPage() {
        new HomePage(getDriver()).clickBtnLogin();
        loginPage = new LoginPage(getDriver());
    }

    @Test
    public void registrationPositiveTests() {
        int i = new Random().nextInt(1000);
        UserLombok user = UserLombok.builder().
                username("vdgfc" + i + "@mail.ru")
                .password("F236@hjk")
                .build();
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnRegistration();
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
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(new ContactsPage(getDriver())
                .validateTextInMessageNoContacts("No Contacts here!"));
    }

    @Test
    public void registrationNegativeEmptyAllFieldsTests() {
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlert()
                .contains("Wrong email or password format"));
    }
}
