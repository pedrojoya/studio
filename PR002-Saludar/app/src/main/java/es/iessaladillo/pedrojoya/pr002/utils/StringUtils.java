package es.iessaladillo.pedrojoya.pr002.utils;

import android.text.TextUtils;

public class StringUtils {

    @SuppressWarnings("unused")
    private StringUtils() {
    }

    public static String capitalizeFirstLetter(String text) {
        return TextUtils.isEmpty(text) ? "" : text.substring(0, 1).toUpperCase() + text.substring(1)
                .toLowerCase();
    }

}
