package es.iessaladillo.pedrojoya.pr005.utils;

import android.content.Intent;

import java.util.Objects;

public class IntentsUtils {

    private IntentsUtils() { }

    public static int requireIntExtra(Intent intent, String key) {
        if (intent == null || !intent.hasExtra(key)) {
            throw new IllegalArgumentException();
        }
        return intent.getIntExtra(key, 0);
    }

    public static String requireStringExtra(Intent intent, String key) {
        if (intent == null || !intent.hasExtra(key)) {
            throw new IllegalArgumentException();
        }
        return Objects.requireNonNull(intent.getStringExtra(key));
    }

}
