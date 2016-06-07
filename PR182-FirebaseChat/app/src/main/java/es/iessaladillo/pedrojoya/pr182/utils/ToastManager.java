package es.iessaladillo.pedrojoya.pr182.utils;

import android.view.View;
import android.widget.Toast;

public class ToastManager implements UIMessageManager {

    @Override
    public void showMessage(View refView, String message) {
        Toast.makeText(refView.getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
