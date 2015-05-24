package es.iessaladillo.pedrojoya.pr095.actividades;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.software.shell.fab.ActionButton;

import java.io.File;
import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr095.R;
import es.iessaladillo.pedrojoya.pr095.data.Cancion;
import es.iessaladillo.pedrojoya.pr095.data.CancionesAdapter;
import es.iessaladillo.pedrojoya.pr095.servicios.MusicaService;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, ObservableScrollViewCallbacks {

    public static final String EXTENSION_ARCHIVO = ".mp3";

    private ObservableListView lstCanciones;
    private ActionButton btnPlayStop;

    private DownloadManager mGestorDescargas;
    private BroadcastReceiver mReceptorDescargaFinalizada;
    private CancionesAdapter mAdaptador;
    private Intent intentServicio;
    private LocalBroadcastManager mGestorLocal;
    private BroadcastReceiver mReceptorCancionFinalizada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        intentServicio = new Intent(getApplicationContext(),
                MusicaService.class);
        // Se obtiene el mGestorLocal de receptores locales.
        mGestorLocal = LocalBroadcastManager.getInstance(this);
        // Se obtiene el gestor de descargas
        mGestorDescargas = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        // Se crea el mReceptorCancionFinalizada de mensajes desde el servicio.
        mReceptorCancionFinalizada = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // Se reproduce la siguiente canción.
                int siguiente = (lstCanciones.getCheckedItemPosition() + 1)
                        % lstCanciones.getCount();
                reproducirCancion(siguiente);
            }
        };
        // Se crea el mReceptorCancionFinalizada de eventos relacionados con la descarga.
        mReceptorDescargaFinalizada = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // Se comprueba el estado de la descarga.
                comprobarDescarga(intent);
            }

        };
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        lstCanciones = (ObservableListView) findViewById(R.id.lstCanciones);
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
                    reproducirCancion(0);
                } else {
                    pararServicio();
                }
            }
        });
        lstCanciones.setScrollViewCallbacks(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se registra en el gestor de receptores locales el receptor de canción finalizada.
        IntentFilter filtroCompletada = new IntentFilter(MusicaService.ACTION_COMPLETADA);
        mGestorLocal.registerReceiver(mReceptorCancionFinalizada, filtroCompletada);
        // Se registra en el gestor general de receptores el receptor de descarga finalizada.
        IntentFilter filtroDescargas = new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(mReceptorDescargaFinalizada, filtroDescargas);
        // Se muestra en el fab el icono adecuado.
        btnPlayStop.setImageResource(lstCanciones.getCheckedItemPosition() ==
                AdapterView.INVALID_POSITION ? R.drawable.fab_play : R.drawable.fab_stop);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Se quita el registro del receptor de canción finalizada.
        mGestorLocal.unregisterReceiver(mReceptorCancionFinalizada);
        // Se quita el registro del receptor de descarga finalizada.
        unregisterReceiver(mReceptorDescargaFinalizada);
    }

    // Comprueba el estado de la descarga que se ha completado.
    private void comprobarDescarga(Intent intent) {
        // Se obtiene el id de la descarga.
        long downloadId = intent.getLongExtra(
                DownloadManager.EXTRA_DOWNLOAD_ID, 0);
        // Se realiza la consulta para obtener los datos de la descarga.
        DownloadManager.Query consulta = new DownloadManager.Query();
        consulta.setFilterById(downloadId);
        Cursor c = mGestorDescargas.query(consulta);
        // Se comprueba el registro resultante.
        if (c != null) {
            if (c.moveToFirst()) {
                // Dependiendo del estado de la descarga.
                int estado = c.getInt(c
                        .getColumnIndex(DownloadManager.COLUMN_STATUS));
                switch (estado) {
                    // Si la descarga se ha realizado correctamente.
                    case DownloadManager.STATUS_SUCCESSFUL:
                        // Se informa de la descarga permitiendo la reproducción.
                        String sUri = c.getString(c
                                .getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        // Se invalidan los datos para que se actualice el icono en el elemento.
                        mAdaptador.notifyDataSetInvalidated();
                        // Se reproduce la canción.
                        reproducir(Uri.parse(sUri));
                        break;
                    // Si se ha producido un error en la descarga.
                    case DownloadManager.STATUS_FAILED:
                        // Se informa al usuario del error.
                        String motivo = c.getString(c
                                .getColumnIndex(DownloadManager.COLUMN_REASON));
                        mostrarToast(getString(R.string.error) + motivo);
                        break;
                }
            }
            // Se cierra el cursor.
            c.close();
        }
    }

    // Cuando se pulsa en una canción de la lista.
    @Override
    public void onItemClick(AdapterView<?> lst, View v, int position, long id) {
        // Se reproduce la canción, descargándola si es necesario.
        reproducirCancion(position);
    }

    // Reproduce una canción, descargándola si es necesario.
    private void reproducirCancion(int position) {
        lstCanciones.setItemChecked(position, true);
        // Se invalidan los datos para que se actualice el icono en el elemento que deja de
        // estar reproduciendose y el que pasa a reproducirse.
        mAdaptador.notifyDataSetInvalidated();
        // Se comprueba si la canción está disponible en local.
        Cancion cancion = (Cancion) lstCanciones.getItemAtPosition(position);
        if (cancion != null) {
            File directory = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
            File fichero = new File(directory, cancion.getNombre()
                    + EXTENSION_ARCHIVO);
            if (fichero.exists()) {
                // Se reproduce.
                reproducir(Uri.fromFile(fichero));
            } else {
                // Se descarga.
                descargar(cancion);
            }
        }
    }

    // Descarga una canción.
    private void descargar(Cancion cancion) {
        // Se crea la solicitud de descarga.
        DownloadManager.Request solicitud = new DownloadManager.Request(
                Uri.parse(cancion.getUrl()));
        solicitud.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE);
        solicitud.setAllowedOverRoaming(false);
        solicitud.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_MUSIC, cancion.getNombre()
                        + EXTENSION_ARCHIVO);
        // solicitud.setDestinationInExternalFilesDir(this,
        // Environment.DIRECTORY_DOWNLOADS,
        // cancion.getNombre() + EXTENSION_ARCHIVO);
        solicitud.setTitle(cancion.getNombre());
        solicitud.setDescription(cancion.getNombre() + "("
                + cancion.getDuracion() + ")");
        solicitud.allowScanningByMediaScanner();
        solicitud
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        // Se encola la solicitud.
        mGestorDescargas.enqueue(solicitud);
        // Se informa al usuario.
        mostrarToast(getString(R.string.descargando) + " " + cancion.getNombre());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuDescargas:
                // Se muesta la actividad estándar de descargas.
                mostrarDescargas();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Muestra la actividad estándar de descargas.
    private void mostrarDescargas() {
        Intent i = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(i);
    }

    // Muestra un toast con el mensaje.
    private void mostrarToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    private void reproducir(Uri uri) {
        // Se invalidan los datos para que se actualice el icono en el elemento.
        mAdaptador.notifyDataSetInvalidated();
        File directory = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        File fichero = new File(directory, uri.getLastPathSegment());
        // Se inicia el servicio pasándole como extra la URL de la canción a reproducir.
        intentServicio.putExtra(MusicaService.EXTRA_PATH_CANCION,
                fichero.getAbsolutePath());
        startService(intentServicio);
        btnPlayStop.setImageResource(R.drawable.fab_stop);
    }

    // Para el servicio y cambia el aspecto visual.
    private void pararServicio() {
        stopService(intentServicio);
        lstCanciones.setItemChecked(lstCanciones.getCheckedItemPosition(), false);
        btnPlayStop.setImageResource(R.drawable.fab_play);
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
                .add(new Cancion("Dancing on Green Grass", "1:54", "The Green Orbs",
                        "https://www.youtube.com/audiolibrary_download?vid=81cb790358aa232c"));
        canciones
                .add(new Cancion("Roller Blades", "2:10", "Otis McDonald",
                        "https://www.youtube.com/audiolibrary_download?vid=42b9cb1799a7110f"));
        canciones
                .add(new Cancion("Aurora Borealis", "1:40", "Bird Creek",
                        "https://www.youtube.com/audiolibrary_download?vid=71e7af02e3fde394"));
        canciones
                .add(new Cancion("Sour Tennessee Red", "2:11", "John Deley and the 41",
                        "https://www.youtube.com/audiolibrary_download?vid=f24590587cad9a9b"));
        canciones
                .add(new Cancion("Water Lily", "2:09", "The 126ers",
                        "https://www.youtube.com/audiolibrary_download?vid=5875315a21edd73b"));
        canciones
                .add(new Cancion("Redhead From Mars", "3:29", "Silent Partner",
                        "https://www.youtube.com/audiolibrary_download?vid=7b17c89cc371a1bc"));
        canciones
                .add(new Cancion("Destructoid", "1:34", "MK2",
                        "https://www.youtube.com/audiolibrary_download?vid=5ad1f342b4676fc1"));
        return canciones;
    }

    @Override
    public void onScrollChanged(int i, boolean b, boolean b1) {
    }

    @Override
    public void onDownMotionEvent() {
    }

    // Cuando se mueve el scroll de la lista hacia abajo o arriba.
    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        ActionBar ab = getSupportActionBar();
        if (scrollState == ScrollState.UP) {
            if (ab != null && ab.isShowing()) {
                ab.hide();
            }
            btnPlayStop.hide();
        } else if (scrollState == ScrollState.DOWN) {
            if (ab != null && !ab.isShowing()) {
                ab.show();
            }
            btnPlayStop.show();
        }
    }

}
