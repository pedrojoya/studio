package es.iessaladillo.pedrojoya.pr015.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
