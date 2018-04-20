package es.iessaladillo.pedrojoya.pr124.fragmentos;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

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
        builder.setView(LayoutInflater.from(requireActivity()).inflate(
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
