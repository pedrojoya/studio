package es.iessaladillo.pedrojoya.pr027.fragmentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import es.iessaladillo.pedrojoya.pr027.R;

public class SiNoDialogFragment extends DialogFragment {

    private Dialog dialogo = null;
    private SiNoDialogListener listener = null;

    // Interfaz pública para comunicación con la actividad.
    public interface SiNoDialogListener {
        public void onPositiveButtonClick(DialogFragment dialog);

        public void onNegativeButtonClick(DialogFragment dialog);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setTitle(R.string.eliminar_alumno);
        b.setMessage(R.string.esta_seguro_de_eliminar_los_alumnos);
        b.setIcon(R.mipmap.ic_launcher);
        b.setPositiveButton(R.string.si, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Se notifica al listener pasándole el fragmento.
                listener.onPositiveButtonClick(SiNoDialogFragment.this);
            }
        });
        b.setNegativeButton(R.string.no, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Se notifica al listener pasándole el fragmento.
                listener.onNegativeButtonClick(SiNoDialogFragment.this);
            }
        });
        dialogo = b.create();
        return dialogo;
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