package es.iessaladillo.pedrojoya.pr182.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackbarManager implements UIMessageManager {

    @Override
    public void showMessage(View refView, String message) {
        Snackbar.make(refView, message, Snackbar.LENGTH_SHORT).show();
    }

}
