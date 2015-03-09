package es.iessaladillo.pedrojoya.pr124.fragmentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;

import com.avast.android.dialogs.core.BaseDialogFragment;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.INeutralButtonDialogListener;
import com.avast.android.dialogs.iface.IPositiveButtonDialogListener;

import es.iessaladillo.pedrojoya.pr124.R;

public class LoginDialogFragment extends SimpleDialogFragment {

    private static final String TAG = "LoginDialog";

    public static void show(FragmentActivity activity, int requestCode) {
        LoginDialogFragment dlg = new LoginDialogFragment();
        dlg.mRequestCode = requestCode;
        dlg.show(activity.getSupportFragmentManager(), TAG);
    }

    @Override
    protected Builder build(Builder builder) {
        builder.setTitle(R.string.login);
        builder.setView(LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_login, null));
        builder.setPositiveButton(R.string.conectar, new View.OnClickListener() {
            // Al pulsar el bot�n positivo.
            @Override
            public void onClick(View v) {
                // Se notifica el evento al listener.
                for (IPositiveButtonDialogListener listener : getPositiveButtonDialogListeners()) {
                    listener.onPositiveButtonClicked(mRequestCode);
                }
                dismiss();            }
        });
        builder.setNeutralButton(R.string.cancelar, new View.OnClickListener() {
            // Al pulsar el bot�n negativo.
            @Override
            public void onClick(View v) {
                // Se notifica el evento al listener.
                for (INeutralButtonDialogListener listener : getNeutralButtonDialogListeners()) {
                    listener.onNeutralButtonClicked(mRequestCode);
                }
                dismiss();
            }
        });
        return builder;
    }

}
