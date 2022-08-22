package lib.tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase
{
    @Test
    public void testSearch()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @Test
    public void testCancelSearch()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisAppear();
    }

    @Test
    public void testCancelSearchWithAssertArticlesCount() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Java");

        int searchResultsCount = searchPageObject.getAmountOfFoundArticles();
        assertTrue("Just 1 article was found!", searchResultsCount > 1);

        searchPageObject.clearSearchInputField();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisAppear();

    }

    @Test
    public void testAmountOfNotEmptySearch() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String search_line = "Linkin Park Diskography";
        searchPageObject.typeSearchLine(search_line);

        int amount_fo_search_results = searchPageObject.getAmountOfFoundArticles();

        assertTrue("We found too few results", amount_fo_search_results > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String search_line = "rtertyuurwr";
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertNoResultSearch();
    }

    @Test
    public void testTitleElementPresent() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.assertTitleElementPresent();

    }

    @Test
    public void testTitleAndDescriptionInSearchResults()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Japan");

        searchPageObject.compareSearchResultTitleAndDescriptionByIndexInSearch("Japan", "Island country in East Asia", 1);
        searchPageObject.compareSearchResultTitleAndDescriptionByIndexInSearch("Japanese language", "Language spoken in East Asia", 2);
        searchPageObject.compareSearchResultTitleAndDescriptionByIndexInSearch("Japan national football team", "Association football team", 3);
    }
}