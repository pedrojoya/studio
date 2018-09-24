package es.iessaladillo.pedrojoya.pr018.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;

public class ToastUtils {

    private ToastUtils() { }

    public static void toast(Context context, @StringRes int messageResId) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
    }

}
