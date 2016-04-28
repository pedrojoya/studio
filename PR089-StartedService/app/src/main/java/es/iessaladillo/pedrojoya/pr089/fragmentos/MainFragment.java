package es.iessaladillo.pedrojoya.pr089.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr089.R;
import es.iessaladillo.pedrojoya.pr089.data.Cancion;
import es.iessaladillo.pedrojoya.pr089.data.CancionesAdapter;
import es.iessaladillo.pedrojoya.pr089.servicios.MusicaOnlineService;

public class MainFragment extends Fragment implements
        AdapterView.OnItemClickListener {

    public interface Listener {
        void onReproduciendo();
        void onParado();
    }

    private Intent intentServicio;
    private ListView lstCanciones;
    private CancionesAdapter mAdaptador;

    private Listener mListener;

    public MainFragment() {}

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initVistas(getView());
        intentServicio = new Intent(getActivity().getApplicationContext(),
                MusicaOnlineService.class);
    }

    private void initVistas(View view) {
        lstCanciones = (ListView) view.findViewById(R.id.lstCanciones);
        mAdaptador = new CancionesAdapter(getActivity(), getListaCanciones(), lstCanciones);
        if (lstCanciones != null) {
            lstCanciones.setAdapter(mAdaptador);
            lstCanciones.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            lstCanciones.setOnItemClickListener(this);
            lstCanciones.setEmptyView(view.findViewById(R.id.rlListaVacia));
        }
    }

    public void playstop() {
        if (lstCanciones.getCheckedItemPosition() == AdapterView.INVALID_POSITION) {
            reproducirCancion(0);
        } else {
            pararServicio();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Se notifica a la actividad el estado.
        if (lstCanciones.getCheckedItemPosition() ==
                AdapterView.INVALID_POSITION) {
            mListener.onParado();
        }
        else {
            mListener.onReproduciendo();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // Para el servicio y cambia el aspecto visual.
    private void pararServicio() {
        getActivity().stopService(intentServicio);
        lstCanciones.setItemChecked(lstCanciones.getCheckedItemPosition(), false);
        mListener.onParado();
    }

    // Al hacer click sobre un elemento de la lista.
    @Override
    public void onItemClick(AdapterView<?> lst, View v, int position, long id) {
        // Se reproduce la canción
        reproducirCancion(position);
    }

    // Inicia el servicio para reproducir la canción y cambia aspecto visual.
    private void reproducirCancion(int position) {
        lstCanciones.setItemChecked(position, true);
        // Se invalidan los datos paara que se actualice el icono en el elemento que deja de
        // estar reproduciendose y el que pasa a reproducirse.
        mAdaptador.notifyDataSetInvalidated();
        mListener.onReproduciendo();
        // Se inicia el servicio pasándole como extra la URL de la canción a reproducir.
        Cancion cancion = (Cancion) lstCanciones.getItemAtPosition(position);
        intentServicio.putExtra(MusicaOnlineService.EXTRA_URL_CANCION,
                cancion.getUrl());
        getActivity().startService(intentServicio);
    }

    // Crea y retorna el ArrayList de canciones.
    private ArrayList<Cancion> getListaCanciones() {
        ArrayList<Cancion> canciones = new ArrayList<>();
        canciones
                .add(new Cancion("Morning Mood", "3:43", "Grieg",
                        "https://www.youtube.com/audiolibrary_download?vid=036500ffbf472dcc"));
        canciones
                .add(new Cancion("Brahms Lullaby", "1:46", "Ron Meixsell",
                        "https://www.youtube.com/audiolibrary_download?vid=9894a50b486c6136"));
        canciones
                .add(new Cancion("Triangles", "3:05", "Silent Partner",
                        "https://www.youtube.com/audiolibrary_download?vid=8c9219f54213cb4f"));
        canciones
                .add(new Cancion("From Russia With Love", "2:26", "Huma-Huma",
                        "https://www.youtube.com/audiolibrary_download?vid=4e8d1a0fdb3bbe12"));
        canciones
                .add(new Cancion("Les Toreadors from Carmen",
                        "2:21", "Bizet",
                        "https://www.youtube.com/audiolibrary_download?vid=fafb35a907cd6e73"));
        canciones
                .add(new Cancion("Funeral March", "9:25", "Chopin",
                        "https://www.youtube.com/audiolibrary_download?vid=4a7d058f20d31cc4"));
        canciones
                .add(new Cancion("Funeral March", "9:25", "Chopin",
                        "https://www.youtube.com/audiolibrary_download?vid=4a7d058f20d31cc4"));
        canciones
                .add(new Cancion("Funeral March", "9:25", "Chopin",
                        "https://www.youtube.com/audiolibrary_download?vid=4a7d058f20d31cc4"));
        canciones
                .add(new Cancion("Funeral March", "9:25", "Chopin",
                        "https://www.youtube.com/audiolibrary_download?vid=4a7d058f20d31cc4"));
        canciones
                .add(new Cancion("Funeral March", "9:25", "Chopin",
                        "https://www.youtube.com/audiolibrary_download?vid=4a7d058f20d31cc4"));
        canciones
                .add(new Cancion("Funeral March", "9:25", "Chopin",
                        "https://www.youtube.com/audiolibrary_download?vid=4a7d058f20d31cc4"));
        canciones
                .add(new Cancion("Funeral March", "9:25", "Chopin",
                        "https://www.youtube.com/audiolibrary_download?vid=4a7d058f20d31cc4"));
        canciones
                .add(new Cancion("Funeral March", "9:25", "Chopin",
                        "https://www.youtube.com/audiolibrary_download?vid=4a7d058f20d31cc4"));
        canciones
                .add(new Cancion("Funeral March", "9:25", "Chopin",
                        "https://www.youtube.com/audiolibrary_download?vid=4a7d058f20d31cc4"));
        canciones
                .add(new Cancion("Funeral March", "9:25", "Chopin",
                        "https://www.youtube.com/audiolibrary_download?vid=4a7d058f20d31cc4"));
        canciones
                .add(new Cancion("Funeral March", "9:25", "Chopin",
                        "https://www.youtube.com/audiolibrary_download?vid=4a7d058f20d31cc4"));
        canciones
                .add(new Cancion("Funeral March", "9:25", "Chopin",
                        "https://www.youtube.com/audiolibrary_download?vid=4a7d058f20d31cc4"));
        return canciones;
    }

}
