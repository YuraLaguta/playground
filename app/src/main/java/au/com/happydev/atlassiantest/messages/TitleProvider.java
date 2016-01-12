package au.com.happydev.atlassiantest.messages;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by laguta.yurii@gmail.com on 12/01/16.
 */
public class TitleProvider {
    public static final String TITLE_OPEN_TAG = "<title>";
    public static final String TITLE_CLOSE_TAG = "</title>";
    private static final String TAG = "TitleProvider";
    private static TitleProvider sInstance;
    private URL mockUrl;

    public static TitleProvider getInstance() {
        if (sInstance == null) {
            sInstance = new TitleProvider();
        }
        return sInstance;
    }

    public String getTitle(String link) {

        try {
            URL url = takeMockUrl();
            if (url == null) {
                url = new URL(link);
            }
            URLConnection urlConnection = url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String line;
            boolean isTitleTagOpened;
            StringBuilder titleSb = new StringBuilder();
            while ((line = in.readLine()) != null) {
                int indexOfOpen = line.indexOf(TITLE_OPEN_TAG);
                if (indexOfOpen != -1) {
                    int indexOfClose = line.indexOf(TITLE_CLOSE_TAG);
                    if (indexOfClose != -1) {
                        titleSb.append(line.substring(indexOfOpen + TITLE_OPEN_TAG.length(), indexOfClose));
                    } else {
                        isTitleTagOpened = true;
                    }
                }
            }
            in.close();
            return titleSb.toString();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return "";
        }
    }

    private URL takeMockUrl() {
        URL tempUrl = mockUrl;
        mockUrl = null;
        return tempUrl;
    }

    public void setMockUrl(URL mockUrl) {
        this.mockUrl = mockUrl;
    }
}
