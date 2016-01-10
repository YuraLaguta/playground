package au.com.happydev.atlassiantest;

import org.junit.Test;

import au.com.happydev.atlassiantest.transformers.MessageTransformer;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class MessageTransformerTest {

    @Test
    public void mentions_isCorrect() throws Exception {
        assertEquals(MessageTransformer.toJson("@chris you around?"),
                "{\n" +
                        "  \"mentions\": [\n" +
                        "    \"chris\"\n" +
                        "  ]\n" +
                        "}");
    }

    @Test
    public void emoicons_isCorrect() throws Exception {
        assertEquals(MessageTransformer.toJson("Good morning! (megusta) (coffee)"),
                "{\n" +
                        "  \"emoticons\": [\n" +
                        "    \"megusta\",\n" +
                        "    \"coffee\"\n" +
                        "  ]\n" +
                        "}");
    }

    @Test
    public void linksWithTitle_isCorrect() throws Exception {
        assertEquals(MessageTransformer.toJson("Olympics are starting soon; http://www.nbcolympics.com"),
                "");
    }

    @Test
    public void completeTransformation_isCorrect() throws Exception {
        assertEquals(MessageTransformer.toJson("@bob @john (success) such a cool feature; https://twitter.com/jdorfman/status/430511497475670016"),
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