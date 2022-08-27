package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends MainPageObject
{
    private static final String
        LOGIN_BUTTON = "xpath://body/div/a[text()='Log in']",
        LOGIN_INPUT = "css:input[name='wpName']",
        PASSWORD_INPUT = "css:input[name='wpPassword']",
        SUBMIT_BUTTON = "css:button#wpLoginAttempt";

    public AuthorizationPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    @Step("Tap to log in")
    public void clickAuthButton()
    {
        this.waitForElementNotPresent(LOGIN_BUTTON, "Cannot find auth button", 5);
        this.waitForElementAndClick(LOGIN_BUTTON, "Cannot click auth button", 5);
    }

    @Step("Specify login: {0} and password: {1}")
    public void enterLoginData(String login, String password)
    {
        this.waitForElementAndSendKeys(LOGIN_INPUT, login, "Cannot specify login in the input field", 5);
        this.waitForElementAndSendKeys(PASSWORD_INPUT, password, "Cannot specify password in the input field", 5);
    }

    @Step("Tap to submit form")
    public void submitForm()
    {
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find and click to submit", 5);
    }
}
