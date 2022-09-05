package lib.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

@Epic("Search Tests")
public class SearchTests extends CoreTestCase
{
    @DisplayName("Simple search")
    @Description("Specify valid search query => results are displayed")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testSearch()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @DisplayName("Cancel search")
    @Description("There is no cancel button after tap on it")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testCancelSearch()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisAppear();
    }

    @DisplayName("Cancel search and check articles count")
    @Description("After search cancellation there are no search results")
    @Severity(SeverityLevel.CRITICAL)
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

    @DisplayName("1 Search Result")
    @Description("There is only 1 search result after specifying valid search query")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testAmountOfNotEmptySearch() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String search_line = "Linkin Park Diskography";
        searchPageObject.typeSearchLine(search_line);

        int amount_fo_search_results = searchPageObject.getAmountOfFoundArticles();

        assertTrue("We found too few results", amount_fo_search_results > 0);
    }

    @DisplayName("Invalid search query")
    @Description("Specify invalid search query => message is displayed")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testAmountOfEmptySearch() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String search_line = "rtertyuurwr";
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertNoResultSearch();
    }

    @DisplayName("Article title")
    @Description("Article title from search results list matches the one from article details screen")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testTitleElementPresent() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.assertTitleElementPresent();

    }

    @DisplayName("Compare titles and subtitles os search results")
    @Description("First search results from the list matches expected titles/ and subtitles")
    @Severity(SeverityLevel.CRITICAL)
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
