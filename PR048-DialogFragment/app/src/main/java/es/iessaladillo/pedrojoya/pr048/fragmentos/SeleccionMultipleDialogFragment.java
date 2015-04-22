package es.iessaladillo.pedrojoya.pr048.fragmentos;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import es.iessaladillo.pedrojoya.pr048.R;

public class SeleccionMultipleDialogFragment extends DialogFragment {

    // Variables.
    private SeleccionMultipleDialogListener mListener = null;
    private boolean[] mOptionIsChecked;

    // Interfaz pública para comunicación con la actividad.
    public interface SeleccionMultipleDialogListener {
        public void onPositiveButtonClick(DialogFragment dialog,
                                          boolean[] optionIsChecked);
    }

    // Al crear el diálogo. Retorna el diálogo configurado.
    @Override
    @NonNull
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
        b.setPositiveButton(R.string.aceptar, new OnClickListener() {
            // Cuando se hace click en el botón neutro.
            @Override
            public void onClick(DialogInterface d, int arg1) {
                // Se cierra el diálogo.
                d.dismiss();
                // Se notifica el evento al listener indicando las opciones
                // seleccionadas.
                mListener.onPositiveButtonClick(
                        SeleccionMultipleDialogFragment.this, mOptionIsChecked);
            }
        });
        return b.create();
    }

    // Cuando se enlaza el fragmento con la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Establece la actividad como listener de los eventos del diálogo.
        try {
            mListener = (SeleccionMultipleDialogListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz, se lanza excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar SeleccionSimpleDialogListener");
        }
    }

}
