package au.com.happydev.atlassiantest.messages;

import com.google.common.io.Files;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.apache.commons.io.Charsets;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class MessageTransformerTest {

    private static MockWebServer mServer;
    private final static String FILE_PREFIX = "src/test/res/";

    @BeforeClass
    public static void setup() throws IOException {
        mServer = new MockWebServer();
        mServer.start();
    }

    private void enqueueResponseFromFile(String fileName) throws IOException {
        String badRequestHtml = FILE_PREFIX + fileName;
        String mockResponseBody = Files.toString(new File(badRequestHtml), Charsets.UTF_8);
        mServer.enqueue(new MockResponse().setBody(mockResponseBody));
        TitleProvider.getInstance().setMockUrl(mServer.getUrl("/"));
    }


    @Test
    public void mentions_isCorrect() throws Exception {
        assertEquals(MessageTransformerProvider.getMessageTransformer().toJson("@chris you around?"),
                "{\n" +
                        "  \"mentions\": [\n" +
                        "    \"chris\"\n" +
                        "  ]\n" +
                        "}");
    }

    @Test
    public void mentions_edge_case_isCorrect() throws Exception {
        assertEquals(MessageTransformerProvider.getMessageTransformer().toJson("@ you around?"),
                "{}");
    }

    @Test
    public void emoicons_isCorrect() throws Exception {
        assertEquals(MessageTransformerProvider.getMessageTransformer().toJson("Good morning! (megusta) (coffee)"),
                "{\n" +
                        "  \"emoticons\": [\n" +
                        "    \"megusta\",\n" +
                        "    \"coffee\"\n" +
                        "  ]\n" +
                        "}");
    }

    @Test
    public void emoicons_edge_case_isCorrect() throws Exception {
        assertEquals(MessageTransformerProvider.getMessageTransformer().toJson("Good morning! () (@coffee) (a) (applePearPeach) (watermelonappleN) "),
                "{\n" +
                        "  \"emoticons\": [\n" +
                        "    \"a\",\n" +
                        "    \"applePearPeach\"\n" +
                        "  ]\n" +
                        "}");
    }

    @Test
    public void linksWithTitle_isCorrect() throws Exception {

        enqueueResponseFromFile("nbcolympics.html");
        assertEquals(MessageTransformerProvider.getMessageTransformer().toJson("Olympics are starting soon; http://www.nbcolympics.com"),
                "{\n" +
                        "  \"links\": [\n" +
                        "    {\n" +
                        "      \"url\": \"http://www.nbcolympics.com\",\n" +
                        "      \"title\": \"NBC Olympics | Home of the 2016 Olympic Games in Rio\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");
    }

    @Test
    public void linksWithNoTitle_isCorrect() throws Exception {
        enqueueResponseFromFile("html_no_title.html");
        assertEquals(MessageTransformerProvider.getMessageTransformer().toJson("Olympics are starting soon; http://www.nbcolympics.com"),
                "{\n" +
                        "  \"links\": [\n" +
                        "    {\n" +
                        "      \"url\": \"http://www.nbcolympics.com\",\n" +
                        "      \"title\": \"\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");
    }

    @Test
    public void linksWithTitle301_isCorrect() throws Exception {
        enqueueResponseFromFile("html_moved.html");
        assertEquals(MessageTransformerProvider.getMessageTransformer().toJson("Moved Permanently Test http://www.nbcolympics.com"),
                "{\n" +
                        "  \"links\": [\n" +
                        "    {\n" +
                        "      \"url\": \"http://www.nbcolympics.com\",\n" +
                        "      \"title\": \"301 Moved Permanently\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");
    }

    @Test
    public void linksWithMalformedTitle_isCorrect() throws Exception {
        enqueueResponseFromFile("html_malformed.html");
        assertEquals(MessageTransformerProvider.getMessageTransformer().toJson("malformed title https://www.nbcolympics.com"),
                "{\n" +
                        "  \"links\": [\n" +
                        "    {\n" +
                        "      \"url\": \"https://www.nbcolympics.com\",\n" +
                        "      \"title\": \"\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");
    }

    @Test
    public void linksWithDifferentLineTagsTitle_isCorrect() throws Exception {
        enqueueResponseFromFile("html_title_close_next_line.html");
        assertEquals(MessageTransformerProvider.getMessageTransformer().toJson("different lines title tag https://goo.gl"),
                "{\n" +
                        "  \"links\": [\n" +
                        "    {\n" +
                        "      \"url\": \"https://goo.gl\",\n" +
                        "      \"title\": \"Different Lines Title Tag\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");
    }

    @Test
    public void completeTransformation_isCorrect() throws Exception {
        enqueueResponseFromFile("twitter_jdorfman.html");
        assertEquals(MessageTransformerProvider.getMessageTransformer().toJson("@bob @john (success) such a cool feature; https://twitter.com/jdorfman/status/430511497475670016"),
                "{\n" +
                        "  \"mentions\": [\n" +
                        "    \"bob\",\n" +
                        "    \"john\"\n" +
                        "  ],\n" +
                        "  \"emoticons\": [\n" +
                        "    \"success\"\n" +
                        "  ],\n" +
                        "  \"links\": [\n" +
                        "    {\n" +
                        "      \"url\": \"https://twitter.com/jdorfman/status/430511497475670016\",\n" +
                        "      \"title\": \"Justin Dorfman on Twitter: &quot;nice @littlebigdetail from @HipChat (shows hex colors when pasted in chat). http://t.co/7cI6Gjy5pq&quot;\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");
    }
}