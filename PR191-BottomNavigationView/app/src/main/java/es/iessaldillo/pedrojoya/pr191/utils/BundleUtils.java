package es.iessaldillo.pedrojoya.pr191.utils;

import android.os.Bundle;

import java.util.Objects;

public class BundleUtils {

    private BundleUtils() { }

    public static int requireInt(Bundle bundle, String key) {
        if (bundle == null || !bundle.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        return bundle.getInt(key);
    }

    public static String requireString(Bundle bundle, String key) {
        if (bundle == null || !bundle.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        return Objects.requireNonNull(bundle.getString(key));
    }

}
