package es.iessaladillo.pedrojoya.pr011.base;

import android.content.Context;

import es.iessaladillo.pedrojoya.pr011.utils.ToastUtils;

public class ToastManager implements MessageManager {

    private final Context context;

    public ToastManager(Context context) {
        this.context = context;
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.toast(context, message);
    }

}
