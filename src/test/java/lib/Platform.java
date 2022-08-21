package lib;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
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
//    private static final String APPIUM_URL_LOCAL = "http://127.0.0.1:4723/wd/hub";
    private static final String APPIUM_URL = "https://oauth-k.yudin.qa-318a5:c18fd5c2-8bef-42f0-be0f-71b90bba223b@ondemand.eu-central-1.saucelabs.com:443/wd/hub";

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
        capabilities.setCapability("app","/Users/konstantin/learnJavaAppium/JavaAppiumAutomation/apks/org.wikipedia.apk");
        return capabilities;
    }

    private MutableCapabilities getIOSDesiredCapabilities()
    {
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("platformName", "iOS");
//        capabilities.setCapability("deviceName", "iPhone 8");
//        capabilities.setCapability("platformVersion", "14.3");
//        capabilities.setCapability("automationName", "Appium");
//        capabilities.setCapability("orientation", "PORTRAIT");
//        capabilities.setCapability("app", "/Users/kyudin/Automation/Wikipedia.app");

        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("platformName","iOS");
        caps.setCapability("appium:deviceName","iPhone Simulator");
        caps.setCapability("appium:deviceOrientation", "portrait");
        caps.setCapability("appium:platformVersion","15.4");
        caps.setCapability("appium:app", "storage:filename=Wikipedia.ipa");
        return caps;
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
        return System.getenv("PLATFORM");
    }

    private boolean isPlatform(String my_platform)
    {
        String platform = this.getPlatformVar();
        return my_platform.equals(platform);
    }
}
