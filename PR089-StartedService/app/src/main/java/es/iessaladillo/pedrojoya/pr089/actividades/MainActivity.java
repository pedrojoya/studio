package es.iessaladillo.pedrojoya.pr089.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.software.shell.fab.ActionButton;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr089.R;
import es.iessaladillo.pedrojoya.pr089.data.Cancion;
import es.iessaladillo.pedrojoya.pr089.data.CancionesAdapter;
import es.iessaladillo.pedrojoya.pr089.servicios.MusicaOnlineService;

public class MainActivity extends ActionBarActivity implements
        OnItemClickListener {

    private Intent intentServicio;
    private ListView lstCanciones;
    private CancionesAdapter mAdaptador;
    private ActionButton btnPlayStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        intentServicio = new Intent(getApplicationContext(),
                MusicaOnlineService.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se muestra en el fab el icono adecuado.
        btnPlayStop.setImageResource(lstCanciones.getCheckedItemPosition() ==
                AdapterView.INVALID_POSITION ? R.drawable.fab_play : R.drawable.fab_stop);
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        lstCanciones = (ListView) findViewById(R.id.lstCanciones);
        mAdaptador = new CancionesAdapter(this, getListaCanciones(), lstCanciones);
        lstCanciones.setAdapter(mAdaptador);
        lstCanciones.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lstCanciones.setOnItemClickListener(this);
        lstCanciones.setEmptyView(findViewById(R.id.rlListaVacia));
        btnPlayStop = (ActionButton) findViewById(R.id.btnPlayStop);
        btnPlayStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lstCanciones.getCheckedItemPosition() == AdapterView.INVALID_POSITION) {
                    lstCanciones.setItemChecked(0, true);
                    reproducirCancion(0);
                } else {
                    pararServicio();
                }
            }
        });
    }

    // Para el servicio y cambia el aspecto visual.
    private void pararServicio() {
        stopService(intentServicio);
        lstCanciones.setItemChecked(lstCanciones.getCheckedItemPosition(), false);
        btnPlayStop.setImageResource(R.drawable.fab_play);
    }

    // Al hacer click sobre un elemento de la lista.
    @Override
    public void onItemClick(AdapterView<?> lst, View v, int position, long id) {
        // Se reproduce la canción
        reproducirCancion(position);
    }

    // Inicia el servicio para reproducir la canción y cambia aspecto visual.
    private void reproducirCancion(int position) {
        // Se invalidan los datos paara que se actualice el icono en el elemento que deja de
        // estar reproduciendose y el que pasa a reproducirse.
        mAdaptador.notifyDataSetInvalidated();
        btnPlayStop.setImageResource(R.drawable.fab_stop);
        // Se inicia el servicio pasándole como extra la URL de la canción a reproducir.
        Cancion cancion = (Cancion) lstCanciones.getItemAtPosition(position);
        intentServicio.putExtra(MusicaOnlineService.EXTRA_URL_CANCION,
                cancion.getUrl());
        startService(intentServicio);
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
        return canciones;
    }

}
