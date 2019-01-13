package es.iessaladillo.pedrojoya.pr254.utils;

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
    public static String loadUrl(String urlString, int timeout) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        connection.setDoInput(true);
        connection.connect();
        String content;
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            content = readContent(connection.getInputStream());
        } else {
            throw new Exception(connection.getResponseMessage());
        }
        return content;
    }

}
