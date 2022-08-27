package lib.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

@Epic("App Conditions Tests")
public class ChangeAppConditionTests extends CoreTestCase
{
    @DisplayName("Background app")
    @Description("Background - foreground app will show valid article")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testSearchArticleInBackground() {

        if (Platform.getInstance().isMW()) {return;}
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(2);
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @DisplayName("Change screen orientation")
    @Description("Search results list remains same after device orientation changes")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testChangeScreenOrientationOnSearchResults() {

        if (Platform.getInstance().isMW()) {return;}
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        String title_before_rotation = articlePageObject.getArticleTitle();

        try {
            this.rotateScreenLandscape();
        } catch (Exception ex) {
            System.out.println("Device orientation was not changed for the test! \n" + ex.getMessage());
            return;
        }

        String title_after_rotation = articlePageObject.getArticleTitle();
        assertEquals("Article title was changed after screen rotation",
                title_before_rotation,
                title_after_rotation);

        try {
            this.rotateScreenPortrait();
        } catch (Exception ex) {
            System.out.println("Device orientation was not changed for the test! \n" + ex.getMessage());
            return;
        }

        String title_after_second_rotation = articlePageObject.getArticleTitle();
        assertEquals("Article title was changed after screen rotation",
                title_before_rotation,
                title_after_second_rotation);
    }
}
