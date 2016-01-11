package au.com.happydev.atlassiantest.messages;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class MessageTransformerTest {

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
                "{\n" +
                        "}");
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
    public void linksWithTitle_isCorrect() throws Exception {
        assertEquals(MessageTransformerProvider.getMessageTransformer().toJson("Olympics are starting soon; http://www.nbcolympics.com"),
                "");
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