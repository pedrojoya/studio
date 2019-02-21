package es.iessaladillo.pedrojoya.pr156.utils;

import android.content.Intent;
import android.os.Parcelable;

import java.util.Objects;

public class IntentsUtils {

    private IntentsUtils() { }

    public static Parcelable requireParcelableExtra(Intent intent, String key) {
        if (intent == null || !intent.hasExtra(key)) {
            throw new IllegalArgumentException();
        }
        return Objects.requireNonNull(intent.getParcelableExtra(key));
    }

}
