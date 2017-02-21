package es.iessaladillo.pedrojoya.pr100;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ExportarService extends IntentService {

    // Constantes.
    public static final String EXTRA_DATOS = "extra_datos";
    public static final String EXTRA_FILENAME = "extra_filename";
    public static final String ACTION_COMPLETADA =
            "es.iessaladillo.pedrojoya.pr100.action_completada";
    private static final long ESPERA = 2;
    private static final String SERVICE_NAME = "exportar";

    // Constructor.
    public ExportarService() {
        // El constructor del padre requiere que se le pase un nombre al
        // servicio.
        super(SERVICE_NAME);
    }

    // Cuando se procesa cada llamada.
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            // Se simula que tarda en ejecutarse dos segundos.
            TimeUnit.SECONDS.sleep(ESPERA);
            // Se crea el fichero y se escriben en él los datos recibidos.
            File outputFile = getFichero();
            exportar(outputFile, intent.getStringArrayExtra(EXTRA_DATOS));
            // Se informa de la finalización de la exportación.
            informar(outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Envía un broadcast al llamador para informar de la finalización de
    // la exportación, incluyendo como extra la uri del archivo generado.
    private void informar(File outputFile) {
        Intent respuestaIntent = new Intent(ACTION_COMPLETADA);
        respuestaIntent.putExtra(EXTRA_FILENAME, Uri.fromFile(outputFile));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(
                respuestaIntent);
    }

    // Escribe los datos recibidos en el archivo recibido.
    private void exportar(File outputFile, String[] alumnos)
            throws FileNotFoundException {
        PrintWriter escritor = new PrintWriter(outputFile);
        for (String alumno : alumnos) {
            escritor.println(alumno);
        }
        escritor.close();
    }

    // Retorna el fichero en el que realizar la exportación.
    private File getFichero() {
        final String nombre = "alumnos";
        // Se obtiene el directorio en el que crear el archivo (preferiblemente
        // en almacenamiento externo).
        String estadoSD = Environment.getExternalStorageState();
        File rootDir;
        if (estadoSD.equals(Environment.MEDIA_MOUNTED)) {
            rootDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS);
        } else {
            rootDir = getFilesDir();
        }
        //noinspection ResultOfMethodCallIgnored
        rootDir.mkdirs();
        // Se crea el archivo.
        SimpleDateFormat formateadorFecha = new SimpleDateFormat(
                "yyyyMMddHHmm", Locale.getDefault());
        String nombreArchivo = nombre
                + formateadorFecha.format(new Date()) + ".txt";
        return new File(rootDir, nombreArchivo);
    }

}
