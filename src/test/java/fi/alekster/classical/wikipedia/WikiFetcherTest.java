package fi.alekster.classical.wikipedia;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by aleksandr on 11.11.2017.
 */
public class WikiFetcherTest {
    private final WikiFetcher fetcher;

    public WikiFetcherTest() {
        fetcher = new WikiFetcher();
    }

    @Test
    public void fetchUrlTest() {
        String fetchedUrl = fetcher.fetchUrl("Uuno Klami");

        Assert.assertEquals("https://en.wikipedia.org/wiki/Uuno_Klami", fetchedUrl);
    }

    @Test
    public void fetchDescriptionTest() {
        String fetchedDescription = fetcher.fetchDescription("Uuno Klami");

        Assert.assertEquals(
                "Uuno (Kalervo) Klami (20 September 1900 – 29 May 1961) was a Finnish composer. He was born in Virolahti. Many of his works are related to the Kalevala. He was influenced by French and Spanish music, and especially by Maurice Ravel, for whom he had a particular esteem.\n" +
                        "He participated in five armed conflicts, including two wars in Karelia, the Finnish Civil War, the Winter War of 1939-40 and the Continuation War of 1941-44.",
                fetchedDescription
        );
    }

    @Test
    public void fetchDescriptionWithNonAsciiCharsTest() {
        String fetchedDescription = fetcher.fetchDescription("Seinäjoki");

        Assert.assertEquals(
                "Seinäjoki is a city located in Southern Ostrobothnia, Finland. Seinäjoki originated around the Östermyra bruk iron and gunpowder factories founded in 1798. Seinäjoki became a municipality in 1868, market town in 1931 and town in 1960. In 2005, the municipality of Peräseinäjoki was merged into Seinäjoki, and in the beginning of 2009, the neighbouring municipalities of Nurmo and Ylistaro were consolidated with Seinäjoki.\n" +
                        "The Town library, Lakeuden Risti Church and central administrative buildings are designed by Alvar Aalto.\n" +
                        "The asteroid 1521 Seinäjoki bears the town's name.\n" +
                        "Seinäjoki was historically called Östermyra in Swedish. Today this name is very seldom used even among the Swedish speakers. Literal translation for Seinäjoki is \"Wallriver\".\n" +
                        "Seinäjoki Airport is located in the neighbouring municipality of Ilmajoki, 11 kilometres (10 mi) south of the Seinäjoki city centre.\n" +
                        "Seinäjoki is home of the Crocodiles, a professional American Football team that competes in the Finnish Maple League. The Seinäjoki Crocodiles are one of the top American Football teams in Europe.",
                fetchedDescription
        );
    }
}
