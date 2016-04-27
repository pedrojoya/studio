package es.iessaladillo.pedrojoya.pr028.fragmentos;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import es.iessaladillo.pedrojoya.pr028.R;

public class SiNoDialogFragment extends DialogFragment {

    private SiNoDialogListener listener = null;

    // Interfaz pública para comunicación con la actividad.
    @SuppressWarnings({"EmptyMethod", "UnusedParameters"})
    public interface SiNoDialogListener {
        void onPositiveButtonClick(DialogFragment dialog);

        void onNegativeButtonClick(DialogFragment dialog);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.eliminar_alumno);
        b.setMessage(R.string.esta_seguro_de_eliminar_los_alumnos);
        b.setIcon(R.mipmap.ic_launcher);
        b.setPositiveButton(R.string.si, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Notifico al listener pasándole el fragmento.
                listener.onPositiveButtonClick(SiNoDialogFragment.this);
            }
        });
        b.setNegativeButton(R.string.no, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Notifico al listener pasándole el fragmento.
                listener.onNegativeButtonClick(SiNoDialogFragment.this);
            }
        });
        return b.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Establece la actividad como listener de los eventos del diálogo.
        try {
            listener = (SiNoDialogListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz, se lanza excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar SiNoDialogListener");
        }
    }

}