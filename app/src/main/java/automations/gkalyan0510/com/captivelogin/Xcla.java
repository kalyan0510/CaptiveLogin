package automations.gkalyan0510.com.captivelogin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


class Xcla {
    public static String outputm="";

    public static String sendpost(String x, String y) throws Exception {
        final String httpsURL = "https://fwiiti1.iiti.ac.in:8003/index.php?zone=iiti_auth";
        final HashMap<String, String> map = new HashMap<>();
        map.put("redirurl", "http://iiti.ac.in");
        map.put("zone", "iiti_auth");
        map.put("auth_user", x);
        map.put("auth_pass", y);
        map.put("auth_voucher", "");
        map.put("accept", "Sign In");
        return performPostCall(httpsURL, map);
    }

    private static String  performPostCall(String requestURL,
                                   HashMap<String, String> postDataParams) {

        URL url;
        StringBuilder response = new StringBuilder();
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            System.out.print(getPostDataString(postDataParams));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();
            response.append(responseCode+"");
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response.append(line);
                }
            }
            else {
                response = new StringBuilder();

            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }

        return response.toString();
    }


    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}
