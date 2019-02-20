package es.iessaldillo.pedrojoya.pr191.utils;

import android.os.BaseBundle;

import java.util.Objects;

public class BundleUtils {

    private BundleUtils() { }

    public static int requireInt(BaseBundle bundle, String key) {
        if (bundle == null || !bundle.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        return bundle.getInt(key);
    }

    public static String requireString(BaseBundle bundle, String key) {
        if (bundle == null || !bundle.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        return Objects.requireNonNull(bundle.getString(key));
    }

}
