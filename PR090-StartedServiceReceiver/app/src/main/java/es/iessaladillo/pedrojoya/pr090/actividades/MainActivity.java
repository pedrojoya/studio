package es.iessaladillo.pedrojoya.pr090.actividades;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr090.R;
import es.iessaladillo.pedrojoya.pr090.data.Cancion;
import es.iessaladillo.pedrojoya.pr090.data.CancionesAdapter;
import es.iessaladillo.pedrojoya.pr090.servicios.MusicaOnlineService;

public class MainActivity extends AppCompatActivity implements
        OnItemClickListener {

    private Intent intentServicio;
    private ListView lstCanciones;
    private CancionesAdapter mAdaptador;
    private FloatingActionButton btnPlayStop;
    private BroadcastReceiver receptor;
    private IntentFilter filtro;
    private LocalBroadcastManager gestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        // Se crea el intent de inicio del servicio.
        intentServicio = new Intent(getApplicationContext(),
                MusicaOnlineService.class);
        // Se obtiene el gestor de receptores locales.
        gestor = LocalBroadcastManager.getInstance(this);
        // Se crea el receptor de mensajes desde el servicio.
        receptor = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // Se reproduce la siguiente canción.
                int siguiente = (lstCanciones.getCheckedItemPosition() + 1)
                        % lstCanciones.getCount();
                reproducirCancion(siguiente);
            }
        };
        // Se crea el filtro para al receptor.
        filtro = new IntentFilter(MusicaOnlineService.ACTION_COMPLETADA);
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        lstCanciones = ActivityCompat.requireViewById(this, R.id.lstCanciones);
        mAdaptador = new CancionesAdapter(this, getListaCanciones(), lstCanciones);
        lstCanciones.setAdapter(mAdaptador);
        lstCanciones.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lstCanciones.setOnItemClickListener(this);
        lstCanciones.setEmptyView(findViewById(R.id.rlListaVacia));
        btnPlayStop = ActivityCompat.requireViewById(this, R.id.btnPlayStop);
        btnPlayStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lstCanciones.getCheckedItemPosition() == AdapterView.INVALID_POSITION) {
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

    @Override
    protected void onResume() {
        super.onResume();
        // Se registra el receptor en el gestor de receptores locales para dicha
        // acción.
        gestor.registerReceiver(receptor, filtro);
    }

    @Override
    protected void onPause() {
        // Se desregistra el receptor del gestor de receptores locales para
        // dicha acción.
        gestor.unregisterReceiver(receptor);
        super.onPause();
    }

    // Cuando se pulsa en una canción de la lista.
    @Override
    public void onItemClick(AdapterView<?> lst, View v, int position, long id) {
        // Se inicia el servicio para que reproduzca la canción.
        reproducirCancion(position);
    }

    // Inicia el servicio de reproducción de la canción.
    private void reproducirCancion(int position) {
        lstCanciones.setItemChecked(position, true);
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
