package es.iessaladillo.pedrojoya.pr080.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetworkUtils {

    private NetworkUtils() {
    }

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

}
