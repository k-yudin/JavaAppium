package lib.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

@Epic("Welcome Screen Tests")
public class GetStartedTest extends CoreTestCase
{
    @DisplayName("Pass through welcome screen")
    @Description("Go throughout all screen of welcome feature at app launch")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testPassThroughWelcome()
    {
        if ((Platform.getInstance().isAndroid()) || (Platform.getInstance().isMW()))
        {return;}
        WelcomePageObject welcomePageObject = new WelcomePageObject(driver);

        welcomePageObject.waitForLearnMoreLInk();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForNewWaysToExploreText();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForAddOrEditPreferredLanguagesText();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForDataCollectedText();
        welcomePageObject.clickGetStartedButton();
    }
}
