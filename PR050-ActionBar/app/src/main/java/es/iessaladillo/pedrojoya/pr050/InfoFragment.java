package es.iessaladillo.pedrojoya.pr050;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class InfoFragment extends Fragment {

    // Interfaz pública de comunicación con la actividad.
    public interface Listener {
        // Cuando se solicita ver la foto.
        @SuppressWarnings("SameParameterValue")
        void onFoto(int fotoResId);
    }

    private Listener mListener;

    // Retorna la vista que debe mostrar el fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    // Al terminar de crearse la actividad con todos sus fragmentos.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se indica que el fragmento proporciona ítems al menú de opciones.
        setHasOptionsMenu(true);
        super.onActivityCreated(savedInstanceState);
    }

    // Cuando el fragmento se enlaza con la actividad.
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            // Establece la actividad como objeto mListener.
            mListener = (Listener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz.
            throw new ClassCastException(activity.toString() + activity.getString(
                    R.string._debe_implementar_infofragment_listener));
        }
    }


    // Cuando se crea el menú de opciones.
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Se agregan al menú de opciones los ítems correspondientes al
        // fragmento.
        inflater.inflate(R.menu.fragment_info, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // Cuando se pulsa sobre un ítem del menú de opciones.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo del item pulsado realizo la acción deseada.
        switch (item.getItemId()) {
            case R.id.mnuFoto:
                // Se simula la pulsación de atrás.
                mListener.onFoto(R.drawable.bench);
                break;
            default:
                // Se propaga el evento porque no ha sido resuelto.
                return super.onOptionsItemSelected(item);
        }
        // Si llegamos aquí es que se ha resuelto el evento.
        return true;
    }

}
