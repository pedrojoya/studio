package es.iessaladillo.pedrojoya.pr002.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtils {

    private KeyboardUtils() {
    }

    public static void hideKeyboard(@NonNull View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
