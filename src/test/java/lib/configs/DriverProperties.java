package lib.configs;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class DriverProperties {

    private static DriverProperties driverPropertiesHolder;
    private Properties properties;
    public static final String sauceUser = System.getenv("SAUCE_USERNAME");
    public static final String sauceKey = System.getenv("SAUCE_ACCESS_KEY");

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
        }
        return System.getenv("DEVICE_FARM_URL");
    }

    public String getPlatform() {
        String platform = this.properties.getProperty("PLATFORM");
        if (platform != null && !platform.contains("$")) {
            return platform;
        }
        else
            return System.getenv("PLATFORM");
    }

    public String getAppBinaryPath() {
        if (this.properties.getProperty("APP.PATH") != null && !this.properties.getProperty("APP.PATH").contains("$")) {
            return this.properties.getProperty("APP.PATH");
        }
        return System.getenv("APP_PATH");
    }

    public String getHost() { return System.getenv("host"); }
}
