package es.iessaladillo.pedrojoya.pr101;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ExportarService extends IntentService {

    // Constantes.
    public static final String EXTRA_DATOS = "extra_datos";
    public static final String EXTRA_FILENAME = "extra_filename";
    private static final long ESPERA = 2;
    private static final String NOMBRE_ARCHIVO = "alumnos";
    private static final int NC_EXPORTADO = 5;

    // Constructor.
    public ExportarService() {
        // El constructor del padre requiere que se le pase un nombre al
        // servicio.
        super("exportar");
    }

    // Cuando se procesa cada llamada.
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            // Se simula que tarda en ejecutarse dos segundos.
            TimeUnit.SECONDS.sleep(ESPERA);
            // Se obtiene el directorio en el que crear el archivo.
            String estadoSD = Environment.getExternalStorageState();
            File rootDir;
            if (estadoSD.equals(Environment.MEDIA_MOUNTED)) {
                rootDir = Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            } else {
                rootDir = getFilesDir();
            }
            //noinspection ResultOfMethodCallIgnored
            rootDir.mkdirs();
            // Se crea el archivo.
            SimpleDateFormat formateadorFecha = new SimpleDateFormat(
                    "yyyyMMddHHmm", Locale.getDefault());
            String nombreArchivo = NOMBRE_ARCHIVO
                    + formateadorFecha.format(new Date()) + ".txt";
            File outputFile = new File(rootDir, nombreArchivo);
            // Se escriben los datos en el archivo.
            String[] alumnos = intent.getStringArrayExtra(EXTRA_DATOS);
            PrintWriter escritor = new PrintWriter(outputFile);
            for (String alumno : alumnos) {
                escritor.println(alumno);
            }
            escritor.close();
            // Se envía un broadcast.
            Uri uriFichero = Uri.fromFile(outputFile);
            Intent respuestaIntent = new Intent();
            respuestaIntent
                    .setAction("es.iessaladillo.pedrojoya.pr101.action.EXPORTED");
            respuestaIntent.putExtra(EXTRA_FILENAME, uriFichero.toString());
            Thread.sleep(3000);
            if (!LocalBroadcastManager.getInstance(this).sendBroadcast(respuestaIntent)) {
                enviarNotificacion(uriFichero);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enviarNotificacion(Uri uriFichero) {
        // Se obtiene el gestor de notificaciones del sistema.
        NotificationManager mGestor = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Se configura la notificación.
        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        b.setSmallIcon(R.drawable.ic_not_download);
        BitmapDrawable largeIcon =
                (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
        if (largeIcon != null) {
            b.setLargeIcon(largeIcon.getBitmap());
        }
        b.setContentTitle(getString(R.string.listado_de_alumnos));
        b.setContentText(getString(R.string.fichero_generado_con_exito));
        b.setTicker(getString(R.string.lista_de_alumnos_exportada));
        b.setAutoCancel(true);
        // Se crea el pending intent.
        Intent mostrarIntent = new Intent(Intent.ACTION_VIEW);
        mostrarIntent.setDataAndType(uriFichero, "text/plain");
        PendingIntent pi = PendingIntent.getActivity(this, 0, mostrarIntent,
                0);
        b.setContentIntent(pi);
        // Se construye y muestra la notificación, asignándole un código de
        // notificación entero.
        mGestor.notify(NC_EXPORTADO, b.build());
    }

}
