package es.iessaladillo.pedrojoya.pr015.components.MessageManager;

import android.view.View;
import android.widget.Toast;

public class ToastMessageManager implements MessageManager {
    @Override
    public void showMessage(View refView, String message) {
            Toast.makeText(refView.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
