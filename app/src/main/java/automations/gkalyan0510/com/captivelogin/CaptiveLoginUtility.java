package automations.gkalyan0510.com.captivelogin;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

/**
 * Created by kalyan on 13/1/18.
 */

public class CaptiveLoginUtility {

    private static UserLoginTask mAuthTask = null;

    public static void login(Context context, final String email, final String password) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        Network[] networks = new Network[1];
        networks = connManager.getAllNetworks();

        if(networks == null || networks.length == 0)
            return;

        for( int i = 0; i < networks.length; i++) {
            Network ntk = networks[i];
            NetworkInfo ntkInfo = null;

            ntkInfo = connManager.getNetworkInfo(ntk);

            if (ntkInfo.getType() == ConnectivityManager.TYPE_WIFI && ntkInfo.isConnected() ) {
                final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                connManager.bindProcessToNetwork(ntk);
                if (connectionInfo != null) {
                    Toast.makeText(context, "Automated Captive Login", Toast.LENGTH_SHORT).show();
                    mAuthTask = new UserLoginTask(context, email, password);

                    mAuthTask.execute();
                }
            }

        }
    }

}
