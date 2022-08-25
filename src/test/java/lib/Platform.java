package lib;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import lib.configs.DriverProperties;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Platform
{
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_MOBILE_WEB = "mobile_web";
    private static final String APPIUM_URL = DriverProperties.getInstance().getAppiumUrl();

    private static Platform instance;
    private Platform(){}
    public static Platform getInstance()
    {
        if (instance == null)
            instance = new Platform();
        return instance;
    }

    public boolean isAndroid()
    {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS()
    {
        return isPlatform(PLATFORM_IOS);
    }

    public boolean isMW()
    {
        return isPlatform(PLATFORM_MOBILE_WEB);
    }

    public RemoteWebDriver getDriver() throws Exception
    {
        URL URL = new URL(APPIUM_URL);
        if (this.isAndroid())
            return new AndroidDriver(URL, this.getAndroidDesiredCapabilities());
        else if (this.isIOS())
            return  new IOSDriver(URL, this.getIOSDesiredCapabilities());
        else if (this.isMW())
            return  new ChromeDriver(this.getMyChromeOptions());
        else
            throw new Exception("Cannot detect driver type. Platform value: " + this.getPlatformVar());
    }

    private DesiredCapabilities getAndroidDesiredCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","6.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("orientation", "PORTRAIT");
        capabilities.setCapability("app",DriverProperties.getInstance().getAppBinaryPath());
        return capabilities;
    }

    private MutableCapabilities getIOSDesiredCapabilities()
    {
        MutableCapabilities capabilities = new MutableCapabilities();
        String host = DriverProperties.getInstance().getHost();
        switch (host) {
            case "saucelabs":
                capabilities.setCapability("platformName", "iOS");
                capabilities.setCapability("appium:deviceName", System.getenv("SELENIUM_DEVICE"));
                capabilities.setCapability("appium:platformVersion", System.getenv("SELENIUM_VERSION"));
                capabilities.setCapability("appium:deviceOrientation", System.getenv("SELENIUM_DEVICE_ORIENTATION"));
                capabilities.setCapability("appium:app", DriverProperties.getInstance().getAppBinaryPath());
                capabilities.setCapability("build", System.getenv("SAUCE_BUILD_NAME"));
                break;
            case "local":
                capabilities.setCapability("platformName", "iOS");
                capabilities.setCapability("deviceName", "iPhone 8");
                capabilities.setCapability("platformVersion", "14.3");
                capabilities.setCapability("orientation", "PORTRAIT");
                capabilities.setCapability("app", DriverProperties.getInstance().getAppBinaryPath());
                break;
        }
        return capabilities;
    }

    private ChromeOptions getMyChromeOptions()
    {
        Map<String, Object> deviceMetrics = new HashMap<String, Object>();
        deviceMetrics.put("width", 360);
        deviceMetrics.put("height", 640);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<String, Object>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("window-size=340,640");

        return options;
    }

    public String getPlatformVar()
    {
        return DriverProperties.getInstance().getPlatform();
    }

    private boolean isPlatform(String my_platform)
    {
        String platform = this.getPlatformVar();
        return my_platform.equals(platform);
    }
}
