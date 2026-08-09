package ui_tests;

import dto.UserLombok;
import manedger.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HoomPage;
import pages.LoginPage;
import utils.UserFactory;
import static utils.UserFactory.*;

import java.util.Random;

public class RegistrationTests extends AppManager {
    LoginPage loginPage;
    @BeforeMethod
    public void goToRegistrationLoginPage() {
        new HoomPage(getDriver()).clickBtnLogin();
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
//        new HoomPage(getDriver()).method();
//    }
//
//    @Test
//    public void testAjaxMethod(){
//        new HoomPage(getDriver()).ajaxMethod();
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
    public void registrationNegativeEmtyAllFieldsTests() {
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlert()
                .contains("Wrong email or password format"));
    }
}
