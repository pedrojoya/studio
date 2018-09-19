package pedrojoya.iessaladillo.es.pr225.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackbarUtils {

    private SnackbarUtils() {
    }

    public static void snackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

}
