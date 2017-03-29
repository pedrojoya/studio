package pedrojoya.iessaladillo.es.pr204.component;

import android.view.View;
import android.widget.Toast;

import pedrojoya.iessaladillo.es.pr204.base.UIMessageManager;

public class ToastUIMessageManager implements UIMessageManager {

    private ToastUIMessageManager() {
    }

    @Override
    public void showMessage(View refView, String message) {
        Toast.makeText(refView.getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
