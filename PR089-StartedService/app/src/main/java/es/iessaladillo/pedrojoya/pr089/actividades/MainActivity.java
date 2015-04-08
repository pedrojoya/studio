package es.iessaladillo.pedrojoya.pr089.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr089.R;
import es.iessaladillo.pedrojoya.pr089.data.Cancion;
import es.iessaladillo.pedrojoya.pr089.servicios.MusicaOnlineService;

public class MainActivity extends ActionBarActivity implements
        OnItemClickListener {

    private Intent intentServicio;
    private ListView lstCanciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getVistas();
        intentServicio = new Intent(getApplicationContext(),
                MusicaOnlineService.class);
    }

    private void getVistas() {
        lstCanciones = (ListView) findViewById(R.id.lstCanciones);
        ArrayList<Cancion> canciones = getListaCanciones();
        ArrayAdapter<Cancion> adaptador = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_activated_1, canciones);
        lstCanciones.setAdapter(adaptador);
        lstCanciones.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lstCanciones.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.mnuParar:
            stopService(intentServicio);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> lst, View v, int position, long id) {
        // Se inicia el servicio para que reproduzca la canci�n.
        reproducirCancion(position);
    }

    private void reproducirCancion(int position) {
        Cancion cancion = (Cancion) lstCanciones.getItemAtPosition(position);
        intentServicio.putExtra(MusicaOnlineService.EXTRA_URL_CANCION,
                cancion.getUrl());
        startService(intentServicio);
    }

    // Crea y retorna el ArrayList de canciones.
    private ArrayList<Cancion> getListaCanciones() {
        ArrayList<Cancion> canciones = new ArrayList<>();
        canciones
                .add(new Cancion("Morning Mood (by Grieg)", "3:43",
                        "https://www.youtube.com/audiolibrary_download?vid=036500ffbf472dcc"));
        canciones
                .add(new Cancion("Brahms Lullaby", "1:46",
                        "https://www.youtube.com/audiolibrary_download?vid=9894a50b486c6136"));
        canciones
                .add(new Cancion("From Russia With Love", "2:26",
                        "https://www.youtube.com/audiolibrary_download?vid=4e8d1a0fdb3bbe12"));
        canciones
                .add(new Cancion("Les Toreadors from Carmen (by Bizet)",
                        "2:21",
                        "https://www.youtube.com/audiolibrary_download?vid=fafb35a907cd6e73"));
        canciones
                .add(new Cancion("Funeral March (by Chopin)", "9:25",
                        "https://www.youtube.com/audiolibrary_download?vid=4a7d058f20d31cc4"));
        return canciones;
    }

}
