package pedrojoya.iessaladillo.es.pr181.component;

import android.view.View;
import android.widget.Toast;

public class UIMessageManager {

    private UIMessageManager() {

    }

    public static void showMessage(View view, String message) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
