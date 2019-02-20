package es.iessaladillo.pedrojoya.pr066.utils;

import android.os.Bundle;

public class BundleUtils {

    private BundleUtils() { }

    public static String requireString(Bundle bundle, String key) {
        if (bundle == null || !bundle.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        return bundle.getString(key);
    }

}
