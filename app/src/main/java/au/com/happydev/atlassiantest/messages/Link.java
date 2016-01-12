package au.com.happydev.atlassiantest.messages;

/**
 * Created by laguta.yurii@gmail.com on 12/01/16.
 */
public class Link {
    public String url;
    public String title;

    public static Link from(String url) {
        Link link = new Link();
        link.url = url;
        link.title = TitleProvider.getInstance().getTitle(url);
        return link;
    }
}
