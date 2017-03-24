package es.iessaladillo.pedrojoya.pr158;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

public class AsignaturasDialogFragment extends DialogFragment {

    // Variables.
    private Listener mListener = null;
    private boolean[] mOptionIsChecked;
    private List<Asignatura> mAsignaturas;

    // Interfaz pública para comunicación con la actividad.
    @SuppressWarnings("UnusedParameters")
    public interface Listener {
        void onPositiveButtonClick(DialogFragment dialog, boolean[] optionIsChecked);
    }

    // Al crear el diálogo. Retorna el diálogo configurado.
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.asignaturas);
        BoxStore boxStore = ((App) getActivity().getApplication()).getBoxStore();
        Box<Asignatura> asignaturasBox = boxStore.boxFor(Asignatura.class);
        Query<Asignatura> asignaturasQuery = asignaturasBox.query()
                .order(Asignatura_.nombre)
                .build();
        mAsignaturas = asignaturasQuery.find();
        mOptionIsChecked = new boolean[mAsignaturas.size()];
        b.setMultiChoiceItems(mAsignaturas.toArray(new String[mAsignaturas.size()]), mOptionIsChecked,
                new DialogInterface.OnMultiChoiceClickListener() {
                    // Cuando se hace click en un elemento.
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        mOptionIsChecked[which] = isChecked;
                    }
                });
        b.setIcon(R.drawable.ic_book);
        b.setPositiveButton(R.string.aceptar,
                new DialogInterface.OnClickListener() {
                    // Cuando se hace click en el botón neutro.
                    @Override
                    public void onClick(DialogInterface d, int arg1) {
                        // Se crea la lista a retonar.
                        for (Asignatura asig : mAsignaturas) {

                        }
                        // Se cierra el diálogo.
                        d.dismiss();
                        // Se notifica el evento al listener indicando las opciones
                        // seleccionadas.
                        mListener.onPositiveButtonClick(AsignaturasDialogFragment.this,
                                mOptionIsChecked);
                    }
                });
        return b.create();
    }

    // Cuando se enlaza el fragmento con la actividad.
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        // Establece la actividad como listener de los eventos del diálogo.
        try {
            mListener = (Listener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz, se lanza excepción.
            throw new ClassCastException(
                    activity.toString() + " debe implementar AsignaturasDialogFragment");
        }
    }

}
