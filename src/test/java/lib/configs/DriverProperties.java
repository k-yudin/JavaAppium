package lib.configs;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class DriverProperties {

    private static DriverProperties driverPropertiesHolder;
    private Properties properties;

    public DriverProperties() {}

    public static synchronized DriverProperties getInstance() {
        if (driverPropertiesHolder == null) {
            driverPropertiesHolder = new DriverProperties();
            try {
                driverPropertiesHolder.properties = PropertiesLoaderUtils.loadAllProperties("driver.properties");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return driverPropertiesHolder;
    }

    public String getAppiumUrl() {
        String appiumURL = this.properties.getProperty("APPIUM.URL");
        if (appiumURL != null) {
            if (!appiumURL.contains("$")) {
                return appiumURL;
            }
            if (System.getProperty("DEVICE.FARM.URL") != null) {
                return appiumURL.replace("${deviceFarmURL}", System.getProperty("DEVICE.FARM.URL"));
            }
            System.out.println(" Device farm didn't find valid URL :  " + this.properties.getProperty("APPIUM.URL"));
        } else {
            System.out.println(" Device farm URL is missing :  " + this.properties.getProperty("APPIUM.URL"));
        }
        return "";
    }

    public String getPlatform() {
        String platform = this.properties.getProperty("PLATFORM");
        if (platform != null && !platform.contains("$")) {
            return platform;
        }
        if (System.getProperty("PLATFORM") != null) {
            return platform.replace("${platform}", System.getProperty("PLATFORM"));
        }
        System.out.println(" Platform value is missing :  " + this.properties.getProperty("PLATFORM"));
        return "";
    }

    public String getAppBinaryPath() {
        if (this.properties.getProperty("APP.PATH") != null && !this.properties.getProperty("APP.PATH").contains("$")) {
            return this.properties.getProperty("APP.PATH");
        }
        if (System.getProperty("APP.PATH") != null) {
            return this.properties.getProperty("APP.PATH").replace("${appBinaryPath}", System.getProperty("APP.PATH"));
        } else {
            System.out.println(" App path is missing :  " + this.properties.getProperty("APP.PATH"));
            return "";
        }
    }
}
