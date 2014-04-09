package es.iessaladillo.pedrojoya.pr095;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

    private DownloadManager mGestor;
    private BroadcastReceiver mReceptor;

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
                // Se obtiene el id de la descarga.
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                // Se realiza la consulta para obtener los datos de la descarga.
                DownloadManager.Query consulta = new DownloadManager.Query();
                consulta.setFilterById(downloadId);
                Cursor c = mGestor.query(consulta);
                if (c != null) {
                    if (c.moveToFirst()) {
                        // Se obtiene el dato sobre el estado de la descarga.
                        int estado = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        // Si la descarga ha sido satisfactoria se muestra la actividad estándar
                        // de descargas.
                        if (estado == DownloadManager.STATUS_SUCCESSFUL) {
                            Intent i = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
                            startActivity(i);
                        }
                    }
                }
            }
        };
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
                Uri.parse("https://www.youtube.com/audiolibrary_download?vid=fafb35a907cd6e73"));
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

}
