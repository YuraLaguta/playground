package au.com.happydev.atlassiantest.messages;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import au.com.happydev.atlassiantest.BusProvider;

/**
 * Created by laguta.yurii@gmail.com on 13/01/16.
 */
public class TransformationIntentService extends IntentService {

    public TransformationIntentService() {
        super("TransformationIntentService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public TransformationIntentService(String name) {
        super(name);
    }

    public static void sendMessage(Activity activity, String message) {
        Intent intent = new Intent(activity, TransformationIntentService.class);
        intent.putExtra(Message.KEY_MESSAGE, message);
        activity.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String messageString = intent.getStringExtra(Message.KEY_MESSAGE);
        Message message = Message.createMessage(messageString);
        BusProvider.getBus().post(message);

    }
}
