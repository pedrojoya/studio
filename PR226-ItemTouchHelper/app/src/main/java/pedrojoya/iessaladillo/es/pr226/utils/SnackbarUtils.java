package pedrojoya.iessaladillo.es.pr226.utils;

import com.google.android.material.snackbar.Snackbar;
import android.view.View;

public class SnackbarUtils {

    private SnackbarUtils() { }

    public static void snackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

}
