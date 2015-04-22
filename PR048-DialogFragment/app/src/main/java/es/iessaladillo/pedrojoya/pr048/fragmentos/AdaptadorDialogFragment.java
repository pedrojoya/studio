package es.iessaladillo.pedrojoya.pr048.fragmentos;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr048.R;
import es.iessaladillo.pedrojoya.pr048.adaptadores.AlumnosAdapter;
import es.iessaladillo.pedrojoya.pr048.modelos.Alumno;


public class AdaptadorDialogFragment extends DialogFragment {

    // Variables.
    private AdaptadorDialogListener mListener = null;
    private ArrayList<Alumno> mAlumnos;

    // Interfaz pública para comunicación con la actividad.
    public interface AdaptadorDialogListener {
        public void onListItemClick(DialogFragment dialog, Alumno alumno);
    }

    // Al crear el diálogo. Retorna el diálogo configurado.
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.alumno);
        b.setIcon(R.mipmap.ic_launcher);
        // Se crea el array de datos.
        mAlumnos = getDatos();
        // Se crea y asigna el adaptador.
        AlumnosAdapter adaptador = new AlumnosAdapter(this.getActivity(),
                getDatos());
        b.setAdapter(adaptador, new OnClickListener() {
            // Cuando se hace click sobre un elemento de la lista.
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Se notifica el evento al listener pasándole el alumno pulsado.
                mListener.onListItemClick(AdaptadorDialogFragment.this,
                        mAlumnos.get(which));
            }
        });
        // Se retorna el diálogo.
        return b.create();
    }

    // Cuando se enlaza el fragmento con la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Establece la actividad como listener de los eventos del diálogo.
        try {
            mListener = (AdaptadorDialogListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz, se lanza excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar SeleccionDirectaDialogListener");
        }
    }

    // Retorna la lista de datos.
    private ArrayList<Alumno> getDatos() {
        ArrayList<Alumno> alumnos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            alumnos.add(new Alumno(
                    "Alumno " + i,
                    "c/ Su casa, nº " + i,
                    i + 20,
                    "http://lorempixel.com/100/100/abstract/" + i + "/"));
        }
        return alumnos;
    }

}
