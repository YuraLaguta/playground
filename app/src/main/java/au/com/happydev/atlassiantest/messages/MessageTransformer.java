package au.com.happydev.atlassiantest.messages;

import com.google.gson.Gson;

/**
 * Created by laguta.yurii@gmail.com on 11/01/16.
 */
public class MessageTransformer {

    private final Gson mGson;

    public MessageTransformer(Gson gson) {
        mGson = gson;
    }

    public String toJson(String message) {
        return mGson.toJson(Message.createMessage(message));
    }
}
