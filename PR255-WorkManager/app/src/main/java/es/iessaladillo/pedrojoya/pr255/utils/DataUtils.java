package es.iessaladillo.pedrojoya.pr255.utils;

import java.util.Objects;

import androidx.work.Data;

public class DataUtils {

    private DataUtils() { }

    public static String[] requireStringArray(Data data, String key) {
        return Objects.requireNonNull(Objects.requireNonNull(data).getStringArray(key));
    }

}
