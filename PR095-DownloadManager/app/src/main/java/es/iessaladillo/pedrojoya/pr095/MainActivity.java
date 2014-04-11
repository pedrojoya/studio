package es.iessaladillo.pedrojoya.pr095;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener,
        MediaPlayer.OnPreparedListener,
        AdapterView.OnItemClickListener {

    // Variables.
    private DownloadManager mGestor;
    private BroadcastReceiver mReceptor;
    private MediaPlayer mReproductor;
    private int mPosCancionActual = -1;

    // Vistas.
    private ImageView imgPlay;
    private ListView lstCanciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getVistas();
        // Se crea el reproductor.
        mReproductor = new MediaPlayer();
        // Se obtiene el gestor de descargas.
        mGestor = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        // Se crea el receptor de eventos relacionados con la descarga.
        mReceptor = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // Se obtiene la acción del intent.
                String accion = intent.getAction();
                // Se obtiene el id de la descarga.
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                // Se realiza la consulta para obtener los datos de la descarga.
                DownloadManager.Query consulta = new DownloadManager.Query();
                consulta.setFilterById(downloadId);
                Cursor c = mGestor.query(consulta);
                if (c != null) {
                    if (c.moveToFirst()) {
                        // Dependiendo del estado de la descarga.
                        int estado = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        switch (estado) {
                            case DownloadManager.STATUS_SUCCESSFUL:
                                // Se reproduce la canción a partir de su Uri local.
                                String sUri = c.getString(
                                        c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                                reproducir(sUri);
                                break;
                            case DownloadManager.STATUS_FAILED:
                                // Se informe del error.
                                String motivo = c.getString(
                                        c.getColumnIndex(DownloadManager.COLUMN_REASON));
                                informarError(motivo);
                                break;
                        }
                    }
                    // Se cierra el cursor.
                    c.close();
                }
            }
        };
    }

    // Muestra la actividad estándar de descargas.
    private void mostrarDescargas() {
        // Se muestra la actividad estándar de descargas.
        Intent i = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        mReproductor.release();
        mReproductor = null;
        super.onDestroy();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        ImageView imgAnterior = (ImageView) findViewById(R.id.imgAnterior);
        imgAnterior.setOnClickListener(this);
        ImageView imgSiguiente = (ImageView) findViewById(R.id.imgSiguiente);
        imgSiguiente.setOnClickListener(this);
        imgPlay = (ImageView) findViewById(R.id.imgPlay);
        imgPlay.setOnClickListener(this);
        lstCanciones = (ListView) findViewById(R.id.lstCanciones);
        ArrayList<Cancion> canciones = getListaCanciones();
        ArrayAdapter<Cancion> adaptador = new ArrayAdapter<Cancion>(this,
                android.R.layout.simple_list_item_activated_1, canciones);
        lstCanciones.setAdapter(adaptador);
        lstCanciones.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lstCanciones.setOnItemClickListener(this);
    }

    // Informa al usuario del motivo del error en la descarga.
    private void informarError(String motivo) {
        Toast.makeText(this, "Error: " + motivo, Toast.LENGTH_LONG).show();
    }

    // Reproduce el audio en base a la uri recibida.
    private void reproducir(String sUri) {
// Se crea el mReproductor.
        try {
            // Se establecen sus propiedades.
            mReproductor.reset();
            mReproductor.setDataSource(this, Uri.parse(sUri)); // Fuente.
            mReproductor.setAudioStreamType(AudioManager.STREAM_MUSIC); // Música.
            // La actividad actuará como listener cuando el mReproductor esté preparado.
            mReproductor.setOnPreparedListener(this);
            // Se prepara el mReproductor (asíncronamente)
            mReproductor.prepareAsync();
        } catch (Exception e) {
            Log.d("Reproductor", "ERROR: " + e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se crea el filtro con las acción de descarga finalizada.
        IntentFilter filtro = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        // Se registra el receptor.
        registerReceiver(mReceptor, filtro);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Se quita el registro del recpetor.
        unregisterReceiver(mReceptor);
    }

    // Descarga una canción.
    private void descargar(Cancion cancion) {
        // Se crea la solicitud de descarga.
        DownloadManager.Request solicitud = new DownloadManager.Request(
                Uri.parse(cancion.getUrl()));
        solicitud.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        solicitud.setAllowedOverRoaming(false);
        solicitud.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, cancion.getNombre() + ".mp4");
        // solicitud.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "carmen.mp4");
        solicitud.setTitle(cancion.getNombre());
        solicitud.setDescription(cancion.getNombre() + "(" + cancion.getDuracion() + ")");
        solicitud.allowScanningByMediaScanner();
        solicitud.setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE);
        // Se encola la solicitud.
        mGestor.enqueue(solicitud);
        Toast.makeText(this, getString(R.string.descargando), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        // Se inicia la reproducción.
        mReproductor.start();
        // Se actualiza el icono al de pausar.
        imgPlay.setImageResource(android.R.drawable.ic_media_pause);
    }

    // Cuando se pulsa en una canción de la lista.
    @Override
    public void onItemClick(AdapterView<?> lst, View v, int position, long id) {
        // Se descarga la canción.
        reproducirCancion(position);
    }

    // Descarga la canción correspondiente a una posición.
    private void reproducirCancion(int position) {
        mPosCancionActual = position;
        lstCanciones.setItemChecked(position, true);
        Cancion cancion = (Cancion)lstCanciones.getItemAtPosition(position);
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        File fichero = new File(directory, cancion.getNombre() + ".mp4");
        if (fichero.exists()) {
            reproducir(Uri.fromFile(fichero).toString());
        }
        else {
            descargar(cancion);
        }
    }

    // Cuando se pulsa en algún botón del panel.
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAnterior:
                imgAnteriorOnClick();
                break;
            case R.id.imgSiguiente:
                imgSiguienteOnClick();
                break;
            case R.id.imgPlay:
                imgPlayOnClick();
                break;
        }
    }

    // Al pulsar sobre reproducir / pausar.
    private void imgPlayOnClick() {
            // Si ya se está reproduciendo.
            if (mPosCancionActual >= 0 && mReproductor.isPlaying()) {
                // Se pausa la reproducción.
                mReproductor.pause();
                // Se actualiza el icono al de reproducir.
                imgPlay.setImageResource(android.R.drawable.ic_media_play);
            } else {
                // Si estábamos en pausa.
                if (mPosCancionActual >= 0) {
                    // Se continua la reproducción.
                    mReproductor.start();
                } else {
                    // Se comienza la reproducción de la primera canción de la
                    // lista.
                    reproducirCancion(0);
                }
                // Se actualiza el icono al de pausar.
                imgPlay.setImageResource(android.R.drawable.ic_media_pause);
            }
    }

    // Al pulsar sobre Siguiente.
    private void imgSiguienteOnClick() {
        reproducirCancion((mPosCancionActual + 1) % lstCanciones.getCount());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuDescargas:
                mostrarDescargas();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Al pulsar sobre Anterior.
    private void imgAnteriorOnClick() {
        int anterior;
        if (mPosCancionActual >= 0) {
            anterior = mPosCancionActual - 1;
            if (anterior < 0) {
                anterior = lstCanciones.getCount() - 1;
            }
        } else {
            anterior = 0;
        }
        reproducirCancion(anterior);
    }

    // Crea y retorna el ArrayList de canciones.
    private ArrayList<Cancion> getListaCanciones() {
        ArrayList<Cancion> canciones = new ArrayList<Cancion>();
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
