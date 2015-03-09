package es.iessaladillo.pedrojoya.pr124.fragmentos;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DatePickerDialogFragment extends DialogFragment {

    // Variables.
    private OnDateSetListener mListener;

    // Al crear el di�logo. Retorna el di�logo configurado.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendario = Calendar.getInstance();
        DatePickerDialog dialogo = new DatePickerDialog(this.getActivity(),
                mListener, calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH));
        return dialogo;
    }

    // Cuando se enlaza el fragmento con la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Establece la actividad como listener de los eventos del di�logo.
        try {
            mListener = (OnDateSetListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz, se lanza excepci�n.
            throw new ClassCastException(activity.toString()
                    + " debe implementar DatePickerDialog.OnDateSetListener");
        }
    }

}
