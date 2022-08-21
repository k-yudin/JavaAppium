package lib.ui;

import org.openqa.selenium.WebElement;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject
{
    protected static String
        ARTICLE_TITLE,
        SAVED_ARTICLE_TITLE,
        FOOTER_ELEMENT,
        OPTIONS_BUTTON,
        OPTIONS_ADD_TO_MY_LIST_BUTTON,
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
        ADD_TO_MY_LIST_OVERLAY,
        MY_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON,
        CLOSE_ARTICLE_BUTTON,
        EXISTING_FOLDER_NAME_TPL;

    public ArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    private static String getExistingFolderName (String folder_name)
    {
        return EXISTING_FOLDER_NAME_TPL.replace("{FOLDER_NAME}", folder_name);
    }

    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresence(ARTICLE_TITLE, "Cannot find article title", 15);
    }

    public WebElement waitForSavedTitleElement()
    {
        return this.waitForElementPresence(SAVED_ARTICLE_TITLE, "Cannot find article title", 15);
    }

    public String getArticleTitle()
    {
        WebElement title = waitForTitleElement();
        if (Platform.getInstance().isAndroid())
        {
        return title.getAttribute("text");
        }
        else if (Platform.getInstance().isMW())
        {
            return title.getText();
        }
        else
            return title.getAttribute("name");
    }

    public void swipeToFooter()
    {
        if(Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of article", 40);
        }
        else if (Platform.getInstance().isMW())
        {
            this.scrollWebPageTillElementNotVisible(FOOTER_ELEMENT, "Cannot find the end of article", 40);
        }
        else {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT, "Cannot find the end of article", 40);
        }
    }

    public void addArticleToMyList(String name_of_folder)
    {
        this.waitForElementAndClick(OPTIONS_BUTTON,
                "Cannot find options button",
                5);

        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find reading list button",
                5);

        this.waitForElementAndClick(ADD_TO_MY_LIST_OVERLAY,
                "Cannot find got it button",
                5);

        this.waitForElementAndClear(MY_LIST_NAME_INPUT,
                "Cannot find input for article folder",
                5);

        this.waitForElementAndSendKeys(MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot specify text for article folder",
                5);

        this.waitForElementAndClick(MY_LIST_OK_BUTTON,
                "Cannot find confirm button",
                5);
    }

    public void addArticleToExistingFolder(String folder_name)
    {
        this.waitForElementAndClick(OPTIONS_BUTTON,
                "Cannot find options button",
                5);

        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find reading list button",
                5);
        String existing_folder_xpath = getExistingFolderName(folder_name);
        this.waitForElementAndClick(existing_folder_xpath,
                "Cannot find saved folder",
                15);
    }

    public void closeArticle()
    {
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            this.waitForElementAndClick(CLOSE_ARTICLE_BUTTON,
                    "Cannot find close an article",
                    5);
        }
        else
        {
            System.out.println("Method closeArticle() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public String getSavedArticleTitle()
    {
        WebElement title = waitForSavedTitleElement();
        return title.getAttribute("text");
    }

     public void assertTitleElementPresent()
     {
         this.assertElementPresent(ARTICLE_TITLE, "Cannot find title element");
     }

     public void addArticlesToMySaved()
     {
         if (Platform.getInstance().isMW())
         {
             removeArticleFromSavedIfAdded();
         }
         this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to the reading list", 5);
     }

     public void removeArticleFromSavedIfAdded()
     {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON))
        {
            this.waitForElementAndClick(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON, "Cannot click button to remove an article from saved", 5);
        }
        this.waitForElementPresence(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find button to add to my list", 5);
     }
}
