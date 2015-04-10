package es.iessaladillo.pedrojoya.pr095.actividades;

import java.io.File;
import java.util.ArrayList;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import es.iessaladillo.pedrojoya.pr095.R;
import es.iessaladillo.pedrojoya.pr095.data.Cancion;
import es.iessaladillo.pedrojoya.pr095.data.CancionesAdapter;

public class MainActivity extends ActionBarActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    private static final String EXTENSION_ARCHIVO = ".mp4";

    private DownloadManager mGestor;
    private BroadcastReceiver mReceptor;
    private CancionesAdapter mAdaptador;

    private ImageView imgPlay;
    private ListView lstCanciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        // Se obtiene el gestor de descargas.
        mGestor = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        // Se crea el receptor de eventos relacionados con la descarga.
        mReceptor = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // Se comprueba el estado de la descarga.
                comprobarDescarga(intent);
            }

        };
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        lstCanciones = (ListView) findViewById(R.id.lstCanciones);
        mAdaptador = new CancionesAdapter(this, getListaCanciones(), lstCanciones);
        lstCanciones.setAdapter(mAdaptador);
        lstCanciones.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lstCanciones.setOnItemClickListener(this);
        lstCanciones.setEmptyView(findViewById(R.id.rlListaVacia));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se crea el filtro con las acción de descarga finalizada.
        IntentFilter filtro = new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        // Se registra el receptor.
        registerReceiver(mReceptor, filtro);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Se quita el registro del recpetor.
        unregisterReceiver(mReceptor);
    }

    // Comprueba el estado de la descarga que se ha completado.
    private void comprobarDescarga(Intent intent) {
        // Se obtiene el id de la descarga.
        long downloadId = intent.getLongExtra(
                DownloadManager.EXTRA_DOWNLOAD_ID, 0);
        // Se realiza la consulta para obtener los datos de la descarga.
        DownloadManager.Query consulta = new DownloadManager.Query();
        consulta.setFilterById(downloadId);
        Cursor c = mGestor.query(consulta);
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
                        informarDescarga(Uri.parse(sUri));
                        break;
                    // Si se ha producido un error en la descarga.
                    case DownloadManager.STATUS_FAILED:
                        // Se informa al usuario del error.
                        String motivo = c.getString(c
                                .getColumnIndex(DownloadManager.COLUMN_REASON));
                        informarError(motivo);
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
        // Se descarga la canción.
        descargarCancion(position);
    }

    // Trata de reproducir una canci�n, descarg�ndola si es necesario.
    private void descargarCancion(int position) {
        lstCanciones.setItemChecked(position, true);
        // Se comprueba si la canci�n est� disponible en local.
        Cancion cancion = (Cancion) lstCanciones.getItemAtPosition(position);
        if (cancion != null) {
            File directory = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
            File fichero = new File(directory, cancion.getNombre()
                    + EXTENSION_ARCHIVO);
            if (fichero.exists()) {
                // Se reproduce.
                reproducir(Uri.fromFile(fichero).toString());
            } else {
                // Se descarga.
                descargar(cancion);
            }
        }
    }

    // Descarga una canci�n.
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
        mGestor.enqueue(solicitud);
        // Se informa al usuario.
        Toast.makeText(this, getString(R.string.descargando), Toast.LENGTH_LONG)
                .show();
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
                // Se muesta la actividad est�ndar de descargas.
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

    // Muestra un snackbar con la opción de reproducir la canción.
    private void informarDescarga(final Uri uri) {
        SnackbarManager.show(
                Snackbar.with(this)
                        .text(getString(R.string.listado_exportado))
                        .actionLabel(getString(R.string.abrir))
                        .actionColorResource(R.color.accent)
                        .actionListener(new ActionClickListener() {
                            @Override
                            public void onActionClicked(Snackbar snackbar) {
                                reproducirCancion(uri);
                            }
                        })
                        .duration(Snackbar.SnackbarDuration.LENGTH_LONG)
        );
    }

    // Muestra un snackbar con el mensaje de error.
    private void informarError(String motivo) {
        SnackbarManager.show(
                Snackbar.with(this)
                        .text(getString(R.string.error) + motivo)
                        .duration(Snackbar.SnackbarDuration.LENGTH_LONG)
        );
    }

    private void reproducirCancion(Uri uri) {

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
