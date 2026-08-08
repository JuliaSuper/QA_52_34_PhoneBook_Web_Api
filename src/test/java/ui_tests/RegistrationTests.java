package ui_tests;

import dto.UserLombok;
import manedger.AppManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HoomPage;
import pages.LoginPage;

import java.util.Random;

public class RegistrationTests extends AppManager {
    @BeforeMethod
    public void goToRegistrationLoginPage() {
       new HoomPage(getDriver()).clickBtnlogin();
    }
    @Test
    public void registrationPositiveTests(){
        int i = new Random().nextInt(1000);
        UserLombok user = UserLombok.builder().
                username("vdgfc"+ i +"@mail.ru")
                .password("F236@hjk")
                .build();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnRegistration();
    }

}
