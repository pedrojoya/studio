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
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener, MediaPlayer.OnPreparedListener {

    private DownloadManager mGestor;
    private BroadcastReceiver mReceptor;
    private MediaPlayer mReproductor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnDescargar).setOnClickListener(this);
        // Se obtiene el mGestor de descargas.
        mGestor = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        // Se crea el receptor de eventos relacionados con la descarga.
        mReceptor = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // Se obtiene la acción del intent.
                String accion = intent.getAction();
                // Si se ha completado la descarga.
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(accion)) {
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
                    }
                }
                // Si se ha pulsado sobre una notificación.
                else {
                    // Se muestra la actividad estándar de descargas.
                    Intent i = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
                    startActivity(i);
                }
            }
        };
    }

    // Informa al usuario del motivo del error en la descarga.
    private void informarError(String motivo) {
        Toast.makeText(this, "Error: " + motivo, Toast.LENGTH_LONG).show();
    }

    // Reproduce el audio en base a la uri recibida.
    private void reproducir(String sUri) {
// Se crea el mReproductor.
        mReproductor = new MediaPlayer();
        try {
            // Se establecen sus propiedades.
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

    @Override
    public void onClick(View view) {
        // Se crea la solicitud de descarga.
        DownloadManager.Request solicitud = new DownloadManager.Request(
                Uri.parse("https://www.youtube.com/audiolibrary_download?vid=fafb35a907cd6e730"));
        solicitud.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        solicitud.setAllowedOverRoaming(false);
        solicitud.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "carmen.mp4");
        // solicitud.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "carmen.mp4");
        solicitud.setTitle("Les Toreadors");
        solicitud.setDescription("Les Toreadors from Carmen (by Bizet)");
        solicitud.allowScanningByMediaScanner();
        solicitud.setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // Se encola la solicitud.
        mGestor.enqueue(solicitud);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        // Se inicia la reproducción.
        mReproductor.start();
    }
}
