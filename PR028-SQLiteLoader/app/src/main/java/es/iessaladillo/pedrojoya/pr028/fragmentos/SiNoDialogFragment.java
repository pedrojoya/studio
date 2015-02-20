package es.iessaladillo.pedrojoya.pr028.fragmentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import es.iessaladillo.pedrojoya.pr028.R;

public class SiNoDialogFragment extends DialogFragment {

    private SiNoDialogListener listener = null;

    // Interfaz p�blica para comunicaci�n con la actividad.
    public interface SiNoDialogListener {
        public void onPositiveButtonClick(DialogFragment dialog);

        public void onNegativeButtonClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.eliminar_alumno);
        b.setMessage(R.string.esta_seguro_de_eliminar_los_alumnos);
        b.setIcon(R.drawable.ic_launcher);
        b.setPositiveButton(R.string.si, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Notifico al listener pas�ndole el fragmento.
                listener.onPositiveButtonClick(SiNoDialogFragment.this);
            }
        });
        b.setNegativeButton(R.string.no, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Notifico al listener pas�ndole el fragmento.
                listener.onNegativeButtonClick(SiNoDialogFragment.this);
            }
        });
        return b.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Establece la actividad como listener de los eventos del di�logo.
        try {
            listener = (SiNoDialogListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz, se lanza excepci�n.
            throw new ClassCastException(activity.toString()
                    + " debe implementar SiNoDialogListener");
        }
    }

}