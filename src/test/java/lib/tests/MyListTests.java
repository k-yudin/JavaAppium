package lib.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

@Epic("My List Tests")
public class MyListTests extends CoreTestCase
{
    private static final String name_of_folder = "Learning programming";
    private static final String
        login = "K.yudin",
        password = "KYmb45kC!w";

    @DisplayName("Save article to my list")
    @Description("Save 1 article to my list and delete it after that")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();

        String article_title = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }
        if (Platform.getInstance().isMW())
        {
            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();

            articlePageObject.waitForTitleElement();

            assertEquals("We are not on the same page after login", article_title, articlePageObject.getArticleTitle());

            articlePageObject.addArticlesToMySaved();
        }

        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid())
        {
            myListsPageObject.openFolderByName(name_of_folder);
        }
        else
        {
            myListsPageObject.closeSyncDialog();
        }

        myListsPageObject.swipeByArticleToDelete(article_title);
    }

    @DisplayName("Save articles to my list")
    @Description("Save several articles to my list")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testSaveTwoArticles() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();


        String article_title = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }

        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();

        if (Platform.getInstance().isIOS())
        {
            searchPageObject.tapToClearInputField();
        }

        searchPageObject.typeSearchLine("JavaScript");
        searchPageObject.clickByArticleWithSubstring("Programming language");

        articlePageObject.waitForTitleElement();


        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToExistingFolder(name_of_folder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }

        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid())
        {
            myListsPageObject.openFolderByName(name_of_folder);
        }
        else
        {
            myListsPageObject.closeSyncDialog();
        }

        myListsPageObject.swipeByArticleToDelete(article_title);

        if (Platform.getInstance().isIOS())
        {
            searchPageObject.clickByArticleWithSubstring("Programming language");
            myListsPageObject.checkSavedItemIsPresent();
        }
        else {

            String list_item_title = articlePageObject.getSavedArticleTitle();
            myListsPageObject.openSavedArticleInFolder();
            article_title = articlePageObject.getArticleTitle();

            assertEquals("Titles don't match!",
                    list_item_title,
                    article_title);
        }
    }
}
