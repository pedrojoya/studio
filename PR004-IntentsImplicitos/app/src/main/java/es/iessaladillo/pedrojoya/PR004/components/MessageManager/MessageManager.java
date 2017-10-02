package es.iessaladillo.pedrojoya.PR004.components.MessageManager;

import android.view.View;
import android.widget.Toast;

@SuppressWarnings("unused")
public interface MessageManager {

    default void showMessage(View refView, String message) {
        Toast.makeText(refView.getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
