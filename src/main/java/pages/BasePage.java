package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.WatchEvent;
import java.time.Duration;

public abstract class BasePage {
    static WebDriver driver;
    public Logger logger = LoggerFactory.getLogger(BasePage.class);

    public void setDriver(WebDriver wd) {
        BasePage.driver = wd;
            }


    public boolean isTextInElementPresent(WebElement element, String text) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(5)).
                    until(ExpectedConditions
                            .textToBePresentInElement(element, text));
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("created exception");
        }
        return false;
    }

    public String closeAlert() {
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }

    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isUrlContactsText(String text) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlContains(text));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }
}
