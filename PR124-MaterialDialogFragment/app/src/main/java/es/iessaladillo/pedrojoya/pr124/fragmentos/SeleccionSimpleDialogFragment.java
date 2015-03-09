package es.iessaladillo.pedrojoya.pr124.fragmentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import es.iessaladillo.pedrojoya.pr124.R;

public class SeleccionSimpleDialogFragment extends DialogFragment {

    // Variables.
    private SeleccionSimpleDialogListener mListener = null;
    private int mTurnoSeleccionado = 0;

    // Interfaz p�blica para comunicaci�n con la actividad.
    public interface SeleccionSimpleDialogListener {
        public void onNeutralButtonClick(DialogFragment dialog, int which);
    }

    // Al crear el di�logo. Retorna el di�logo configurado.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.turno);
        b.setSingleChoiceItems(R.array.turnos, mTurnoSeleccionado,
                new OnClickListener() {
                    // Al seleccionar un elemento.
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTurnoSeleccionado = which;
                    }
                });
        b.setIcon(R.mipmap.ic_launcher);
        b.setNeutralButton(R.string.aceptar, new OnClickListener() {
            // Al pulsar el bot�n neutro.
            @Override
            public void onClick(DialogInterface d, int arg1) {
                // Se cierra el di�logo.
                d.dismiss();
                // Se notifica el evento al listener indicando el �ndice del
                // elemento seleccionado.
                mListener.onNeutralButtonClick(
                        SeleccionSimpleDialogFragment.this, mTurnoSeleccionado);
            }
        });
        return b.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Establece la actividad como listener de los eventos del di�logo.
        try {
            mListener = (SeleccionSimpleDialogListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz, se lanza excepci�n.
            throw new ClassCastException(activity.toString()
                    + " debe implementar SeleccionSimpleDialogListener");
        }
    }

}
