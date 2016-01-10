package au.com.happydev.atlassiantest.messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by laguta.yurii@gmail.com on 11/01/16.
 */
public class Message {

    public static final String MENTION_PREFIX = "@";
    @SerializedName("mentions")
    @Expose
    private List<String> mMentions;

    public Message(String message) {
        if (message.contains("@")) {
            String[] parts = message.split(" ");
            for (String part : parts) {
               if (part.startsWith(MENTION_PREFIX)) {
                   getMentions().add(part.substring(1));
               }
            }
        }
    }

    private List<String> getMentions() {
        if (mMentions == null) {
            mMentions = new LinkedList<>();
        }
        return mMentions;
    }


}
