package es.iessaladillo.pedrojoya.pr040.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private NetworkUtils() {
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @SuppressWarnings("WeakerAccess")
    public static String readContent(InputStream inputStream) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = bufferedReader.readLine();
        while (line != null) {
            content.append(line);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return content.toString();
    }

    public static String loadUrl(String urlString) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(urlString).openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoInput(true);
        httpURLConnection.connect();
        String content = null;
        if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            content = readContent(httpURLConnection.getInputStream());
        }
        return content;
    }

}
