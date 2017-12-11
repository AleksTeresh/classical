package fi.alekster.classical.youtube;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by aleksandr on 11.11.2017.
 */
public class YoutubeSearcherTest {
    private final YoutubeSearcher searcher;

    public YoutubeSearcherTest() {
        searcher = new YoutubeSearcher();
        searcher.setApiKey("AIzaSyA5OK9oE6qsmfurNRCwD1buBv8-3tXGDZ0");
    }

    @Test
    public void getYoutubeIdTest() {
        String videoId = searcher.getYoutubeId("Franz Liszt Liebestraum");

        Assert.assertEquals("KpOtuoHL45Y", videoId);
    }
}
