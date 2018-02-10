package automations.gkalyan0510.com.captivelogin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


/**
 * Created by iitindore on 2/7/17.
 */
public class MyWReciever extends BroadcastReceiver {
    private static final String SHARED_KEY = "IITIGK";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences settings = context.getSharedPreferences(SHARED_KEY, 0);
        String un = settings.getString("un", "");
        String pa = settings.getString("pa", "");
        CaptiveLoginUtility.login(context, un, pa);

    }

}
