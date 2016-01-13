package au.com.happydev.atlassiantest;

import com.squareup.otto.Bus;

import au.com.happydev.atlassiantest.core.MainThreadBus;

/**
 * Created by laguta.yurii@gmail.com on 13/01/16.
 */
public class BusProvider {

    private static Bus sBus;

    public static Bus getBus() {
        if (sBus == null) {
            sBus = new MainThreadBus();
        }
        return sBus;
    }
}
