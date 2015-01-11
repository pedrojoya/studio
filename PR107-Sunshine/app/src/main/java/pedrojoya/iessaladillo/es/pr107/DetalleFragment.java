package pedrojoya.iessaladillo.es.pr107;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetalleFragment extends Fragment {

    // Constantes.
    private static final String ARG_DATOS = "datos";

    // Variables a nivel de clase.
    private String mDatos;

    // Vistas.
    private TextView lblDatos;

    // Cuando se crea el menú de opciones.
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_detalle, menu);
        // Se configura el item de compartir.
        MenuItem item = menu.findItem(R.id.mnuCompartir);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(crearShareIntent());
        }
    }

    // Cuando la actividad ha sido creada al completo.
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        lblDatos = (TextView) getView().findViewById(R.id.lblDatos);
        lblDatos.setText(mDatos);
    }

    // Método factoría del fragmento.
    public static DetalleFragment newInstance(String datos) {
        DetalleFragment fragment = new DetalleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DATOS, datos);
        fragment.setArguments(args);
        return fragment;
    }

    // Constructor público vacío obligatorio.
    public DetalleFragment() {
    }

    // Al crear el fragmento.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // Se obtienen los argumentos.
        if (getArguments() != null) {
            mDatos = getArguments().getString(ARG_DATOS);
        }
    }

    // Retorna la vista que debe pintarse para el fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalle, container, false);
    }

    // Crea un intent que será usado por el shareActionProvider.
    public Intent crearShareIntent() {
        // La acción debe ser SEND.
        Intent intent = new Intent(Intent.ACTION_SEND);
        // Este flag es necesario para que al finalizar la compartición se vuelva
        // a nuestra actividad, y no a la de la aplicación con la que se ha compartido.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        // Se va a compartir texto plano, correspondiente al dato actual al que le
        // añadimos un hashtag.
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, mDatos + "#SunshineApp");
        return intent;

    }

}
