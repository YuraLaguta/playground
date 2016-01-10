package au.com.happydev.atlassiantest.messages;

import com.google.gson.GsonBuilder;

/**
 * Created by laguta.yurii@gmail.com on 11/01/16.
 */
public class MessageTransformerProvider {

    private static MessageTransformer sMessageTransformer;

    public static MessageTransformer getMessageTransformer() {
        if (sMessageTransformer == null) {
            sMessageTransformer = new MessageTransformer(new GsonBuilder().setPrettyPrinting().create());
        }
        return sMessageTransformer;
    }
}
