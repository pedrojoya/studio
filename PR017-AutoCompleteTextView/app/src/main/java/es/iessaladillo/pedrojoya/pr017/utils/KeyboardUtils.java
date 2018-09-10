package es.iessaladillo.pedrojoya.pr017.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtils {

    private KeyboardUtils() { }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

}
