package lib;

import com.saucelabs.saucerest.DataCenter;
import com.saucelabs.saucerest.SauceREST;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import lib.configs.DriverProperties;
import lib.ui.WelcomePageObject;
import org.junit.Rule;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

public class CoreTestCase {

    protected RemoteWebDriver driver;
    private SauceREST sauceClient;
    private String sessionID;
    private String host = DriverProperties.getInstance().getHost();

    @Rule
    public SauceTestWatcher watcher = new SauceTestWatcher();

    @Rule
    public TestName testName = new TestName();

    @Rule
    public ExternalResource resource = new ExternalResource() {

        @Override
        protected void before() throws Exception {
            driver = Platform.getInstance().getDriver();
            sessionID = driver.getSessionId().toString();
            sauceClient = new SauceREST(DriverProperties.sauceUser, DriverProperties.sauceKey, DataCenter.EU);
            rotateScreenPortrait();
            skipWelcomeWindowForIOS();
            //this.openWikiWebPageForMobileWeb();
        }

        @Override
        protected void after() {
            printSessionIdAndTestName();
            driver.quit();
        }
    };

    @Step("Rotate screen to portrait mode")
    protected void rotateScreenPortrait() {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.PORTRAIT);
        } else {
            System.out.println("Method rotateScreenPortrait() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Rotate screen to landscape mode")
    protected void rotateScreenLandscape() {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.LANDSCAPE);
        } else {
            System.out.println("Method rotateScreenLandscape() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Background app for {0} seconds")
    protected void backgroundApp(int seconds) {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.runAppInBackground(Duration.ofSeconds(seconds));
        } else {
            System.out.println("Method backgroundApp(int seconds) does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Skip welcome screen")
    private void skipWelcomeWindowForIOS() {
        if (Platform.getInstance().isIOS()) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
            welcomePageObject.clickSkip();
        }
    }

    @Step("Open wiki page for mobile web view")
    protected void openWikiWebPageForMobileWeb() {
        if (Platform.getInstance().isMW()) {
            driver.get("https://en.m.wikipedia.org");
        } else {
            System.out.println("Method openWikiWebPageForMobileWeb() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    private void printSessionIdAndTestName() {
        String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s",
                sessionID, testName.getMethodName());
        System.out.println(message);
    }

    protected class SauceTestWatcher extends TestWatcher {
        @Override
        protected void failed(Throwable e, Description description) {
            if (host.equals("saucelabs")) {
                sauceClient.jobFailed(sessionID);
                System.out.println(String.format("https://saucelabs.com/tests/%s", sessionID));
            }
        }

        @Override
        protected void succeeded(Description description) {
            if (host.equals("saucelabs")) {
                sauceClient.jobPassed(sessionID);
            }
        }
    }
}
