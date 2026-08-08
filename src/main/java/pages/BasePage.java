package pages;

import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    static WebDriver driver;

    public void setDriver(WebDriver wd) {
        BasePage.driver = wd;
    }
    public void  pause(int taim) {
        try {
            Thread.sleep(taim);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
