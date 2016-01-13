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
    public static final String PATTERN_EMOTICON = "\\([a-zA-Z0-9]{1,15}\\)";
    public static final String PATTERN_LINK = "^(?i)(https?)://.*";
    public static final String KEY_MESSAGE = "key_message";

    @SerializedName("mentions")
    @Expose
    private List<String> mMentions;

    @SerializedName("emoticons")
    @Expose
    private List<String> mEmoticons;

    @SerializedName("links")
    @Expose
    private List<Link> mLinks;

    public static Message createMessage(String msgContent) {
        Message message = new Message();
        if (msgContent.contains("@") || (msgContent.contains("(") && msgContent.contains(")")) || msgContent.contains("http")) {
            String[] parts = msgContent.split(" ");
            for (String part : parts) {
                if (part.startsWith(MENTION_PREFIX) && part.length() > 1) {
                    message.getMentions().add(part.substring(1));
                } else if (part.matches(PATTERN_EMOTICON)) {
                    message.getEmoticons().add(part.substring(1, part.length() - 1));
                } else if (part.matches(PATTERN_LINK)) {
                    message.getLinks().add(Link.from(part));
                }
            }
        }
        return message;
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

    private List<Link> getLinks() {
        if (mLinks == null) {
            mLinks = new LinkedList<>();
        }
        return mLinks;
    }
}
