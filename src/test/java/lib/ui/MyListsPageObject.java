package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject
{
    protected static String
        FOLDER_BY_NAME_TPL,
        ARTICLE_BY_TITLE_TPL,
        SAVED_ARTICLE,
        CLOSE_SYNC_BUTTON,
        ARTICLE_SAVED_BUTTON,
            REMOVE_FROM_SAVED_BUTTON;

    public MyListsPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getSavedArticleXPathByTitle(String article_title)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    private static String getRemoveButtonByTitle(String article_title)
    {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
    }

    private static String getFolderXPathByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }
    /* TEMPLATES METHODS */

    @Step("Tap to open folder by name: {0}")
    public void openFolderByName(String name_of_folder)
    {
        String folder_name_xpath = getFolderXPathByName(name_of_folder);
        this.waitForElementAndClick(folder_name_xpath,
                "Cannot find folder by namer" + name_of_folder,
                5);
    }
    @Step("Swipe to delete saved item")
    public void swipeByArticleToDelete(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXPathByTitle(article_title);
        if (Platform.getInstance().isMW())
        {
            String remove_locator = getRemoveButtonByTitle(article_title);
            this.waitForElementAndClick(remove_locator, "Cannot click button to remove article from saved", 10);

        }
        this.swipeElementToTheLeft(article_xpath,
                "Cannot find saved article");
        if(Platform.getInstance().isIOS())
        {
            this.clickOnElementAtTheTopRightCorner(article_xpath, "Cannot find saved article");
        }
        if (Platform.getInstance().isMW())
        {
            driver.navigate().refresh();
        }
        this.waitForArticleToDisAppearByTitle(article_title);
    }

    @Step("Wait for article to disappear by title: {0} ")
    public void waitForArticleToDisAppearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXPathByTitle(article_title);
        this.waitForElementNotPresent(article_xpath, "Saved article is still present with title" + article_title, 15);
    }

    @Step("Wait for saved article to appear: {0}")
    public void waitForArticleToAppearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXPathByTitle(article_title);
        this.waitForElementPresence(article_xpath, "Cannot find saved article by title" + article_title, 15);
    }

    @Step("Tap to open saved article")
    public void openSavedArticleInFolder()
    {
        this.waitForElementPresence(SAVED_ARTICLE, "Cannot find saved article in the list", 15);
        this.waitForElementAndClick(SAVED_ARTICLE, "Cannot click on saved article in the list", 5);
    }

    @Step("Tap to close sync dialog")
    public void closeSyncDialog()
    {
        this.waitForElementAndClick(CLOSE_SYNC_BUTTON, "Cannot close sync overlay", 5);
    }

    @Step("Verify that saved button is present")
    public void checkSavedItemIsPresent()
    {
        this.waitForElementPresence(ARTICLE_SAVED_BUTTON, "Article is marked as not saved in favourites", 5);
    }
}
