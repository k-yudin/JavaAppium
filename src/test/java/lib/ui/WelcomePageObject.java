package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject
{
    private static final String
        STEP_LEARN_MORE_LINK = "id:Learn more about Wikipedia",
        STEP_NEW_WAYS_TO_EXPLORE_TEXT = "id:New ways to explore",
        STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK = "id:Add or edit preferred languages",
        STEP_LEARN_MORE_DATA_COLLECTED_LINK = "id:Learn more about data collected",
        NEXT_LINK = "id:Next",
        GET_STARTED_BUTTON = "id:Get started",
        SKIP = "id:Skip";


    public  WelcomePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    @Step("Wait for 'Learn more about Wikipedia' link to appear")
    public void waitForLearnMoreLInk()
    {
        this.waitForElementPresence(STEP_LEARN_MORE_LINK, "Cannot find 'Learn more about Wikipedia' link", 10);
    }

    @Step("Wait for 'New ways to explore' link to appear")
    public void waitForNewWaysToExploreText()
    {
        this.waitForElementPresence(STEP_NEW_WAYS_TO_EXPLORE_TEXT, "Cannot find 'New ways to explore' link", 10);
    }

    @Step("Wait for 'Add or edit preferred languages' link to appear")
    public void waitForAddOrEditPreferredLanguagesText()
    {
        this.waitForElementPresence(STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK, "Cannot find 'Add or edit preferred languages' link", 10);
    }

    @Step("Wait for 'Learn more about data collected' link to appear")
    public void waitForDataCollectedText()
    {
        this.waitForElementPresence(STEP_LEARN_MORE_DATA_COLLECTED_LINK, "Cannot find 'Learn more about data collected' link", 10);
    }

    @Step("Tap on 'Next' button")
    public void clickNextButton()
    {
        this.waitForElementAndClick(NEXT_LINK, "Cannot find and click 'Next' link", 10);
    }

    @Step("Tap on 'Get started' button")
    public void clickGetStartedButton()
    {
        this.waitForElementAndClick(GET_STARTED_BUTTON, "Cannot find and click 'Get started' button", 10);
    }

    @Step("Tap on skip button")
    public void clickSkip()
    {
        this.waitForElementAndClick(SKIP, "Cannot find and click skip button", 5);
    }
}
