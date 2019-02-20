package es.iessaladillo.pedrojoya.pr102;

import android.content.Intent;

import java.util.Objects;

public class IntentsUtils {

    private IntentsUtils() { }

    public static long requireLongExtra(Intent intent, String key) {
        if (intent == null || !intent.hasExtra(key)) {
            throw new IllegalArgumentException();
        }
        return intent.getLongExtra(key, 0);
    }

    public static String requireStringExtra(Intent intent, String key) {
        if (intent == null || !intent.hasExtra(key)) {
            throw new IllegalArgumentException();
        }
        return Objects.requireNonNull(intent.getStringExtra(key));
    }

}
