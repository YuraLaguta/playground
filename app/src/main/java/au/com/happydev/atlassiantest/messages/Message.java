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

    @SerializedName("emoticons")
    @Expose
    private List<String> mEmoticons;


    public Message(String message) {
        if (message.contains("@") || (message.contains("(") && message.contains(")"))) {
            String[] parts = message.split(" ");
            for (String part : parts) {
               if (part.startsWith(MENTION_PREFIX) && part.length() > 1) {
                   getMentions().add(part.substring(1));
               } else if (part.startsWith("(") && part.endsWith(")")) {
                   /*TODO replace to regexp*/
                   getEmoticons().add(part.substring(1, part.length() - 1));
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

    private List<String> getEmoticons() {
        if (mEmoticons == null) {
            mEmoticons = new LinkedList<>();
        }
        return mEmoticons;
    }




}
