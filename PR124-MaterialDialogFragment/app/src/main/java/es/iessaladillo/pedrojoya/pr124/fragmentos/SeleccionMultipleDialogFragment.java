package es.iessaladillo.pedrojoya.pr124.fragmentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import es.iessaladillo.pedrojoya.pr124.R;

public class SeleccionMultipleDialogFragment extends DialogFragment {

    // Variables.
    private SeleccionMultipleDialogListener mListener = null;
    public boolean[] mOptionIsChecked;

    // Interfaz p�blica para comunicaci�n con la actividad.
    public interface SeleccionMultipleDialogListener {
        public void onNeutralButtonClick(DialogFragment dialog,
                                         boolean[] optionIsChecked);
    }

    // Al crear el di�logo. Retorna el di�logo configurado.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.turno);
        mOptionIsChecked = new boolean[] { true, false, false };
        b.setMultiChoiceItems(R.array.turnos, mOptionIsChecked,
                new OnMultiChoiceClickListener() {
                    // Cuando se hace click en un elemento.
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                            boolean isChecked) {
                        mOptionIsChecked[which] = isChecked;
                    }
                });
        b.setIcon(R.mipmap.ic_launcher);
        b.setNeutralButton(R.string.aceptar, new OnClickListener() {
            // Cuando se hace click en el bot�n neutro.
            @Override
            public void onClick(DialogInterface d, int arg1) {
                // Se cierra el di�logo.
                d.dismiss();
                // Se notifica el evento al listener indicando las opciones
                // seleccionadas.
                mListener.onNeutralButtonClick(
                        SeleccionMultipleDialogFragment.this, mOptionIsChecked);
            }
        });
        return b.create();
    }

    // Cuando se enlaza el fragmento con la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Establece la actividad como listener de los eventos del di�logo.
        try {
            mListener = (SeleccionMultipleDialogListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz, se lanza excepci�n.
            throw new ClassCastException(activity.toString()
                    + " debe implementar SeleccionSimpleDialogListener");
        }
    }

}
