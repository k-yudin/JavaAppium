package lib.ui.android;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidArticlePageObject extends ArticlePageObject
{
    static {
                ARTICLE_TITLE = "id:org.wikipedia:id/view_page_title_text";
                SAVED_ARTICLE_TITLE = "id:org.wikipedia:id/page_list_item_title";
                FOOTER_ELEMENT = "xpath://*[@text='View page in browser']";
                OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']";
                OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']";
                ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button";
                MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
                MY_LIST_OK_BUTTON = "xpath://*[@text='OK']";
                CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";
                EXISTING_FOLDER_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";
    }

    public AndroidArticlePageObject (RemoteWebDriver driver)
    {
        super(driver);
    }
}
