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

//        String badRequestHtml = "src/test/res/bad_request.html";
        String badRequestHtml = FILE_PREFIX + "nbcolympics.html";
        String mockResponseBody = Files.toString(new File(badRequestHtml), Charsets.UTF_8);

        mServer.enqueue(new MockResponse().setBody(mockResponseBody));
        TitleProvider.getInstance().setMockUrl(mServer.getUrl("/"));
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
    public void completeTransformation_isCorrect() throws Exception {
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
                        "      \"title\": \"Twitter / jdorfman: nice @littlebigdetail from ...\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");
    }
}