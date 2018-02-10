package automations.gkalyan0510.com.captivelogin;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
class UserLoginTask extends AsyncTask<String, String, String> {
    String mystr;
    private static final String SHARED_KEY = "IITIGK";


    private final String mEmail;
    private final String mPassword;
    private final Context mcontext;

    UserLoginTask(final Context context, final String email, final String password) {
        mEmail = email;
        mPassword = password;
        mcontext = context;
    }


    private void appendLog(String text) {
        File logFile = new File(Environment.getExternalStorageDirectory(),"XCaptiveLogin.log.txt");


        //publishProgress(logFile.getPath());
        if (!logFile.exists())
        {
            try
            {
                logFile.createNewFile();
            }
            catch (IOException e)
            {

                e.printStackTrace();
            }
        }
        try
        {
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        }
        catch (IOException e)
        {

            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... params) {

        try {

            mystr =  Xcla.sendpost(mEmail, mPassword);

            appendLog("SUCCESS "+mystr+"\n");
           // publishProgress("SUCCESS "+mystr+"\n");
            return "Success";
        }catch (Exception e){
            e.printStackTrace();
            appendLog("Failure: "+e.toString()+"\n\n\n\n");
           // publishProgress("Failure: "+e.toString()+"\n\n\n\n");
            return "Failure";
        }

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        Toast.makeText(mcontext, ""+values[0], Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
        SharedPreferences settings = mcontext.getSharedPreferences(SHARED_KEY, 0);
        String un = settings.getString("un", "");
        String pa = settings.getString("pa", "");

        if(un.equals("") || pa.equals("")){
            Toast.makeText(mcontext, "No login credentials supplied", Toast.LENGTH_SHORT).show();
            return;
        }

        if(str.equals("Success")) {
            notifyUser();
        }

    }

    private void notifyUser() {
        WifiManager wifiManager = (WifiManager) mcontext.getSystemService (Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo ();
        String ssid  = info.getSSID();
        if(!ssid.equals("\"IITI\"")) {
            return;
        }


        String CHANNEL_ID = "gk_channel";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mcontext, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.image)
                        .setContentTitle("Captive Login")
                        .setContentText("Currently logged in as " +
                mcontext.getSharedPreferences(SHARED_KEY, 0).getString("un", "None")+ " in "+ssid);

        NotificationManager mNotificationManager =
                (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());
    }

}