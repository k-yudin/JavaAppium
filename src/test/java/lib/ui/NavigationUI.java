package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject
{
    protected static String
        MY_LISTS_LINK,
        OPEN_NAV;

    public NavigationUI (RemoteWebDriver driver)
    {
        super(driver);
    }

    public void openNavigation()
    {
        if (Platform.getInstance().isMW())
        {
            this.waitForElementAndClick(OPEN_NAV, "Cannot find and click to open navigation button", 5);
        }
        else
        {
            System.out.println("Method openNavigation() does nothing for platform " + Platform.getInstance().getPlatformVar());

        }
    }

    public void clickMyLists()
    {
        if (Platform.getInstance().isMW())
        {
            this.tryClickElementWithFewAttempts(MY_LISTS_LINK, "Cannot find my list button", 5);
        }
        else {
            this.waitForElementAndClick(MY_LISTS_LINK,
                    "Cannot find my list button",
                    5);
        }
    }
}
