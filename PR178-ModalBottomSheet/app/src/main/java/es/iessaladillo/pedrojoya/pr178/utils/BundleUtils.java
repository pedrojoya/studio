package es.iessaladillo.pedrojoya.pr178.utils;

import android.os.Bundle;
import android.os.Parcelable;

public class BundleUtils {

    private BundleUtils() { }

    public static Parcelable requireParcelable(Bundle bundle, String key) {
        if (bundle == null || !bundle.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        return bundle.getParcelable(key);
    }

}
