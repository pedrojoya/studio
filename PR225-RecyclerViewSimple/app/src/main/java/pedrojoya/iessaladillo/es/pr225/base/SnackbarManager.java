package pedrojoya.iessaladillo.es.pr225.base;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarManager implements MessageManager {

    private final View view;

    public SnackbarManager(View view) {
        this.view = view;
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

}
