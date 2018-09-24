package es.iessaladillo.pedrojoya.pr022.utils;

import com.google.android.material.snackbar.Snackbar;
import android.view.View;

public class SnackbarUtils {

    private SnackbarUtils() { }

    @SuppressWarnings("unused")
    public static void snackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public static void snackbar(View view, String message, int duration, String actionText,
            View.OnClickListener onClickListener) {
        Snackbar.make(view, message, duration).setAction(actionText, onClickListener).show();
    }

}
