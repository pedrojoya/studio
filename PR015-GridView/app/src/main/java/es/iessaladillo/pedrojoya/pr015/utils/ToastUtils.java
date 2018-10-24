package es.iessaladillo.pedrojoya.pr015.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class ToastUtils {

    public static void toast(@NonNull Context context, @NonNull String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
