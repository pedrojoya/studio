package es.iessaladillo.pedrojoya.pr124.fragmentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import es.iessaladillo.pedrojoya.pr124.R;

public class SeleccionDirectaDialogFragment extends DialogFragment {

    // Variables.
    private SeleccionDirectaDialogListener mListener = null;

    // Interfaz p�blica para comunicaci�n con la actividad.
    public interface SeleccionDirectaDialogListener {
        public void onItemClick(DialogFragment dialog, int which);
    }

    // Al crear el di�logo. Retorna el di�logo configurado.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.turno);
        b.setItems(R.array.turnos, new OnClickListener() {
            // Cuando se selecciona el elemento.
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Se le notifica al listener, indic�ndole el �ndice del
                // elemento seleccionado.
                mListener.onItemClick(SeleccionDirectaDialogFragment.this,
                        which);
            }
        });
        b.setIcon(R.mipmap.ic_launcher);
        return b.create();
    }

    // Cuando se enlaza el fragmento con la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Establece la actividad como listener de los eventos del di�logo.
        try {
            mListener = (SeleccionDirectaDialogListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz, se lanza excepci�n.
            throw new ClassCastException(activity.toString()
                    + " debe implementar SeleccionDirectaDialogListener");
        }
    }

}
