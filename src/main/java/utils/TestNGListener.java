package utils;

import manedger.AppManager;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGListener implements ITestListener {
    Logger logger = LoggerFactory
            .getLogger(TestNGListener.class);
    private WebDriver driver;

    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
        logger.info("start test --> " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        logger.info("test success --> " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            logger.error("Test failed: {}, Message: {}",
                    result.getName(), throwable.getMessage());
        } else
            logger.error("Test failed: {}, Status: {}",
                    result.getName(), result.getStatus());
        logger.error("test failed -->" + result.getName() + "status -->"
                + result.getStatus());
        this.driver = ((AppManager)result.getInstance()).getDriver();
        TakeScreenShot.takeScreenShot((TakesScreenshot)driver);
    }
}
