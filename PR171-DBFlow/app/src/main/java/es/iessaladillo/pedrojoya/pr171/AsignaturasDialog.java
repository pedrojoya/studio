package es.iessaladillo.pedrojoya.pr171;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import es.iessaladillo.pedrojoya.pr171.db.Asignatura;

public class AsignaturasDialog extends DialogFragment {

    private static final String ARG_OPTIONS = "options";
    private static final String ARG_OPTIONS_CHECKED = "optionsChecked";

    // Variables.
    private String[] mOptions;
    private Listener mListener = null;
    private boolean[] mOptionIsChecked;

    static public AsignaturasDialog newInstance(String[] options, boolean[] optionsChecked) {
        AsignaturasDialog dlg = new AsignaturasDialog();
        Bundle argumentos = new Bundle();
        argumentos.putStringArray(ARG_OPTIONS, options);
        argumentos.putBooleanArray(ARG_OPTIONS_CHECKED, optionsChecked);
        dlg.setArguments(argumentos);
        return dlg;
    }

    // Interfaz pública para comunicación con la actividad.
    public interface Listener {
        public void onPositiveButtonClick(DialogFragment dialog,
                                          boolean[] optionIsChecked);
    }

    // Al crear el diálogo. Retorna el diálogo configurado.
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.asignaturas);
        mOptionIsChecked = new boolean[]{true, false, false};
        b.setMultiChoiceItems(mOptions, mOptionIsChecked,
                new DialogInterface.OnMultiChoiceClickListener() {
                    // Cuando se hace click en un elemento.
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        mOptionIsChecked[which] = isChecked;
                    }
                });
        b.setIcon(R.mipmap.ic_launcher);
        b.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            // Cuando se hace click en el botón neutro.
            @Override
            public void onClick(DialogInterface d, int arg1) {
                // Se cierra el diálogo.
                d.dismiss();
                // Se notifica el evento al listener indicando las opciones
                // seleccionadas.
                mListener.onPositiveButtonClick(
                        AsignaturasDialog.this, mOptionIsChecked);
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
            mListener = (Listener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz, se lanza excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar SeleccionSimpleDialogListener");
        }
    }

}