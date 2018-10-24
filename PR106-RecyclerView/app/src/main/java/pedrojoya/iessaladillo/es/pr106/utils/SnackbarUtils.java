package pedrojoya.iessaladillo.es.pr106.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;

public class SnackbarUtils {

    private SnackbarUtils() {
    }

    public static void snackbar(@NonNull View view, @NonNull String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

}
