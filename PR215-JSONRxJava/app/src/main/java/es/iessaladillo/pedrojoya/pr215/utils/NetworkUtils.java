package es.iessaladillo.pedrojoya.pr215.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private NetworkUtils() {
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

    @SuppressWarnings("SameParameterValue")
    public static String loadUrl(String urlString, int timeout) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(urlString).openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(timeout);
        httpURLConnection.setReadTimeout(timeout);
        httpURLConnection.setDoInput(true);
        httpURLConnection.connect();
        String content = null;
        if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            content = readContent(httpURLConnection.getInputStream());
        }
        return content;
    }

}
