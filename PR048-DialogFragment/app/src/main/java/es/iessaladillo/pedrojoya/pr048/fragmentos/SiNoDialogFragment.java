package es.iessaladillo.pedrojoya.pr048.fragmentos;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import es.iessaladillo.pedrojoya.pr048.R;

public class SiNoDialogFragment extends DialogFragment {

    // Variables.
    private SiNoDialogListener mListener = null;

    // Interfaz pública para comunicación con la actividad.
    @SuppressWarnings("UnusedParameters")
    public interface SiNoDialogListener {
        void onPositiveButtonClick(DialogFragment dialog);

        void onNegativeButtonClick(DialogFragment dialog);
    }

    // Al crear el diálogo. Retorna el diálogo configurado.
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.eliminar_usuario);
        b.setMessage(R.string.esta_seguro_de_que_quiere_eliminar_el_usuario);
        b.setIcon(R.mipmap.ic_launcher);
        b.setPositiveButton(R.string.si, new OnClickListener() {
            // Al pulsar el botón positivo.
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Se notifica el evento al listener.
                mListener.onPositiveButtonClick(SiNoDialogFragment.this);
            }
        });
        b.setNegativeButton(R.string.no, new OnClickListener() {
            // Al pulsar el botón negativo.
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Se notifica el evento al listener.
                mListener.onNegativeButtonClick(SiNoDialogFragment.this);
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
            mListener = (SiNoDialogListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz, se lanza excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar SiNoDialogListener");
        }
    }

}