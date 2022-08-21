package lib.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import static junit.framework.TestCase.assertEquals;

abstract public class SearchPageObject extends MainPageObject
{
    protected static String
        SEARCH_INIT_ELEMENT,
        SEARCH_INPUT,
        SEARCH_RESULT_BY_SUBSTRING_TPL,
        SEARCH_CANCEL_SEARCH,
        SEARCH_RESULT_ELEMENT,
        SEARCH_EMPTY_RESULT_ELEMENT,
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL,
        SEARCH_CLEAR_BUTTON,
        SEARCH_RESULT_TITLE_TPL,
        SEARCH_RESULT_DESCRIPTION_TPL;
    
    public SearchPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTiTleAndDescription(String title, String description)
    {
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}", title).
                replace("{DESCRIPTION}", description);
    }

    private static String getTitleXPAthByIndexInSearch(int index)
    {
        return SEARCH_RESULT_TITLE_TPL.replace("{INDEX}", Integer.toString(index));
    }

    private static String getTitleDescriptionXPAthByIndexInSearch(int index)
    {
        return SEARCH_RESULT_DESCRIPTION_TPL.replace("{INDEX}", Integer.toString(index));
    }
    /* TEMPLATES METHODS */

    public void initSearchInput()
    {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
        this.waitForElementPresence(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element");
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type in the search input", 5);
    }

    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresence(search_result_xpath, "Cannot find search result with substring " + substring);
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresence(SEARCH_CANCEL_SEARCH, "Cannot find clear button", 5);
    }

    public void waitForCancelButtonToDisAppear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_SEARCH, "Clear button is still present", 5);
    }

    public void clickCancelSearch()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_SEARCH, "Cannot find and click clear button", 5);
    }

    public void clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring " + substring, 10);
    }

    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresence(SEARCH_RESULT_ELEMENT,
                "Cannot find anything by request ",
                15);

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresence(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);
    }

    public void assertNoResultSearch()
    {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "No results were found");
    }

    public void clearSearchInputField()
    {
        this.waitForElementAndClear(SEARCH_INPUT, "Cannot find search input field", 5);
    }

    public void tapToClearInputField()
    {
        this.waitForElementAndClick(SEARCH_CLEAR_BUTTON,"Cannot find and click clear button for the input field", 5);
    }

    public String getSearchResultTitleByIndex(int index)
    {
        String search_result_xpath = getTitleXPAthByIndexInSearch(index);
        WebElement element = this.waitForElementPresence(search_result_xpath, "`Cannot find search result title and description by index: " + index, 5);
        return element.getAttribute("name");
    }

    public String getSearchResultDescByIndex(int index)
    {
        String search_result_xpath = getTitleDescriptionXPAthByIndexInSearch(index);
        WebElement element = this.waitForElementPresence(search_result_xpath, "`Cannot find search result title and description by index: " + index, 5);
        return element.getAttribute("name");
    }

    public void compareSearchResultTitleAndDescriptionByIndexInSearch(String title, String description, int index_in_search)
    {
        String search_result_title = getSearchResultTitleByIndex(index_in_search);
        String search_result_desc = getSearchResultDescByIndex(index_in_search);

        assertEquals("Title doesn't match for the " + index_in_search + " item in the search", search_result_title, title);
        assertEquals("Description doesn't match for the " + index_in_search + " item in the search", search_result_desc,  description);
    }
}
