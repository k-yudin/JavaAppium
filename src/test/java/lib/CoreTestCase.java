package lib;

import com.saucelabs.saucerest.DataCenter;
import com.saucelabs.saucerest.SauceREST;
import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import lib.configs.DriverProperties;
import lib.ui.WelcomePageObject;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

public class CoreTestCase extends TestCase
{
    protected RemoteWebDriver driver;
    private SauceREST sauceClient = new SauceREST("sam17", "e256c465-7dd9-48e9-bc6c-953c6564a94b", DataCenter.EU);

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        driver = Platform.getInstance().getDriver();
        this.rotateScreenPortrait();
        this.skipWelcomeWindowForIOS();
        this.openWikiWebPageForMobileWeb();
    }

    @Override
    protected void tearDown() throws Exception
    {
        printSessionIdAndTestName();
        driver.quit();
        super.tearDown();
    }

    @Rule
    public TestRule watcher = new TestWatcher() {
        @Override
        protected void failed(Throwable throwable, Description description) {
            if (DriverProperties.getInstance().getHost().equals("saucelabs")) {
                sauceClient.jobFailed(driver.getSessionId().toString());
                System.out.println(String.format("https://saucelabs.com/tests/%s", driver.getSessionId().toString()));
            }
        }

        @Override
        protected void succeeded(Description description) {
            if (DriverProperties.getInstance().getHost().equals("saucelabs")) {
                sauceClient.jobPassed(driver.getSessionId().toString());
            }
        }
    };

    protected void rotateScreenPortrait()
    {
       if (driver instanceof AppiumDriver)
       {
           AppiumDriver driver = (AppiumDriver) this.driver;
           driver.rotate(ScreenOrientation.PORTRAIT);
       }
       else
       {
           System.out.println("Method rotateScreenPortrait() does nothing for platform " + Platform.getInstance().getPlatformVar());
       }
    }

    protected void rotateScreenLandscape()
    {
        if (driver instanceof AppiumDriver)
        {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.LANDSCAPE);
        }
        else
        {
            System.out.println("Method rotateScreenLandscape() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    protected void backgroundApp(int seconds)
    {
        if (driver instanceof AppiumDriver)
        {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.runAppInBackground(Duration.ofSeconds(seconds));
        }
        else
        {
            System.out.println("Method backgroundApp(int seconds) does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    private void skipWelcomeWindowForIOS()
    {
        if (Platform.getInstance().isIOS())
        {
            AppiumDriver driver = (AppiumDriver) this.driver;
            WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
            welcomePageObject.clickSkip();
        }
    }

    protected void openWikiWebPageForMobileWeb()
    {
        if(Platform.getInstance().isMW())
        {
            driver.get("https://en.m.wikipedia.org");
        }
        else
        {
            System.out.println("Method openWikiWebPageForMobileWeb() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    private void printSessionIdAndTestName() {
        String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s",
                driver.getSessionId().toString(), getName());
        System.out.println(message);
    }
}
