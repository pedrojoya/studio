package es.iessaladillo.pedrojoya.pr048.fragmentos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import es.iessaladillo.pedrojoya.pr048.R;

public class LoginDialogFragment extends DialogFragment {

    // Variables.
    private LoginDialogListener mListener = null;

    // Interfaz pública para comunicación con la actividad.
    @SuppressWarnings({"EmptyMethod", "UnusedParameters"})
    public interface LoginDialogListener {
        void onConectarClick(DialogFragment dialog);

        void onCancelarClick(DialogFragment dialog);
    }

    // Al crear el diálogo. Retorna el diálogo configurado.
    @SuppressLint("InflateParams")
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.login);
        b.setView(LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_login, null));
        b.setPositiveButton(R.string.conectar, new OnClickListener() {
            // Al pulsar el botón positivo.
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Se notifica el evento al listener.
                mListener.onConectarClick(LoginDialogFragment.this);
            }
        });
        b.setNeutralButton(R.string.cancelar, new OnClickListener() {
            // Al pulsar el botón negativo.
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Se notifica el evento al listener.
                mListener.onCancelarClick(LoginDialogFragment.this);
            }
        });
        return b.create();
    }

    // Al enlazar el fragmento con la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Establece la actividad como listener de los eventos del diálogo.
        try {
            mListener = (LoginDialogListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz, se lanza excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar SiNoDialogListener");
        }
    }

}
