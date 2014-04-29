package es.iessaladillo.pedrojoya.pr096;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

    // Constantes.
    private static final int BIG_TEXT_STYLE_NOTIF = 1;
    private static final int BIG_PICTURE_STYLE_NOTIF = 2;
    private static final int INBOX_STYLE_NOTIF = 3;
    private static final int PROGRESS_BAR_NOTIF = 4;
    private static final int INDETERMINATE_PROGRESS_NOTIF = 5;

    // Variables a nivel de clase.
    private NotificationManager mGestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getVistas();
        // Se obtiene el gestor de notificaciones.
        mGestor =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private void getVistas() {
        ((Button) findViewById(R.id.btnBigTextStyle)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnBigPictureStyle)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnInboxStyle)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnProgressBar)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnProgresoIndeterminado)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnResultado)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBigPictureStyle:
                notificarBigPictureStyle();
                break;
            case R.id.btnBigTextStyle:
                notificarBigTextStyle();
                break;
            case R.id.btnInboxStyle:
                notificarInboxStyle();
                break;
            case R.id.btnProgressBar:
                notificarProgressBar();
                break;
            case R.id.btnProgresoIndeterminado:
                notificarProgresoIndeterminado();
                break;
            case R.id.btnResultado:
                goToResultado();
                break;
        }
    }

    private void goToResultado() {
        Intent i = ResultadoActivity.getIntent(this, "Vengo desde la actividad principal");
        startActivity(i);
    }

    private NotificationCompat.Builder crearNotificacionSimple() {
        // Se crea el constructor de notificaciones.
        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        // Se configuran los elementos básicos de la notificación.
        b.setSmallIcon(R.drawable.ic_calendar); // Icono pequeño.
        b.setLargeIcon(((BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher))
                .getBitmap()); // Icono grande.
        b.setContentTitle("Content Title"); // Título (1ª línea).
        b.setContentText("Content Text"); // Texto (2º línea).
        b.setContentInfo("Content Info"); // Info adicional.
        b.setTicker("Ticker");  // Ticker.
        b.setAutoCancel(true); // La notificación se cancelará al pulsar.
        return b;
    }

    private void notificarInboxStyle() {
        // Se crea la notificación simple.
        NotificationCompat.Builder b = crearNotificacionSimple();
        // Se configuran los elementos para el modo expandido.
        NotificationCompat.InboxStyle estilo = new NotificationCompat.InboxStyle();
        estilo.setBigContentTitle("Big Content Title");
        estilo.addLine("Line 1");
        estilo.addLine("Line 2");
        estilo.addLine("Line 3");
        estilo.addLine("Line 4");
        estilo.addLine("Line 5");
        estilo.setSummaryText("Summary Text");
        b.setStyle(estilo);
        // Se realiza la notificación.
        mGestor.notify(INBOX_STYLE_NOTIF, b.build());
    }

    private void notificarBigTextStyle() {
        // Se crea la notificación simple.
        NotificationCompat.Builder b = crearNotificacionSimple();
        // Se configuran los elementos para el modo expandido.
        NotificationCompat.BigTextStyle estilo = new NotificationCompat.BigTextStyle();
        estilo.setBigContentTitle("Big Content Title");
        estilo.bigText("Big Text: Lorem ipsum dolor sit amet, consectetuer adipiscing " +
                "elit, sed diam " +
                "nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat " +
                "volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation " +
                "ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.");
        estilo.setSummaryText("Summary Text");
        b.setStyle(estilo);
        // Se realiza la notificación.
        mGestor.notify(BIG_TEXT_STYLE_NOTIF, b.build());
    }

    private void notificarBigPictureStyle() {
        // Se crea la notificación simple.
        NotificationCompat.Builder b = crearNotificacionSimple();
        // Se configuran los elementos para el modo expandido.
        NotificationCompat.BigPictureStyle estilo = new NotificationCompat.BigPictureStyle();
        estilo.setBigContentTitle("Big Content Title");
        estilo.bigPicture(BitmapFactory.decodeResource(getResources(),
                R.drawable.sunset));
        estilo.setSummaryText("Summary Text");
        b.setStyle(estilo);
        // Se realiza la notificación.
        mGestor.notify(BIG_PICTURE_STYLE_NOTIF, b.build());
    }

    private void notificarProgressBar() {
        // Se ejecuta la tarea asíncrona de actualización por pasos.
        new ActualizacionPorPasosTask().execute();
    }

    private void notificarProgresoIndeterminado() {
        // Se ejecuta la tarea asíncrona de actualización por pasos.
        new ActualizacionIndeterminadaTask().execute();
    }

    private class ActualizacionPorPasosTask extends AsyncTask<Void, Integer, Void> {

        private static final int NUM_PASOS = 10;
        NotificationCompat.Builder mBuilder;

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < NUM_PASOS; i++) {
                // Se simula la ejecución de un paso (1 segundo).
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Se publica el progreso.
                publishProgress(i + 1);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Se muestra la notificación inicial.
            mBuilder = new NotificationCompat.Builder(MainActivity.this);
            mBuilder.setSmallIcon(R.drawable.ic_calendar)
                    .setLargeIcon(((BitmapDrawable) getResources()
                            .getDrawable(R.drawable.ic_launcher)).getBitmap())
                    .setContentTitle(getString(R.string.actualizacion_de_datos))
                    .setContentText(getString(R.string.actualizando))
                    .setTicker(getString(R.string.actualizacion_de_datos))
                    .setProgress(NUM_PASOS, 0, false);
            mGestor.notify(PROGRESS_BAR_NOTIF, mBuilder.build());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int paso = values[0];
            // Se actualiza la notificación.
            mBuilder.setProgress(NUM_PASOS, paso, false)
                    .setContentText(getString(R.string.actualizando) +
                            " (" + paso + " " + getString(R.string.de) + " " + NUM_PASOS + ")");
            mGestor.notify(PROGRESS_BAR_NOTIF, mBuilder.build());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Se crea el intent que será enviado al pulsar la notificación.
            Intent intent = ResultadoActivity.getIntent(MainActivity.this,
                    getString(R.string.operacion_finalizada));
            // Se muestra la notificación final.
            mBuilder.setContentText(getString(R.string.operacion_finalizada))
                    .setTicker(getString(R.string.operacion_finalizada))
                    .setProgress(0, 0, false)
                    .setContentIntent(getPendingIntentForRegularActivity(intent))
                    .setAutoCancel(true);
            mGestor.notify(PROGRESS_BAR_NOTIF, mBuilder.build());
        }

    }

    private class ActualizacionIndeterminadaTask extends AsyncTask<Void, Void, Void> {

        NotificationCompat.Builder mBuilder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Se muestra la notificación inicial.
            mBuilder = new NotificationCompat.Builder(MainActivity.this);
            mBuilder.setSmallIcon(R.drawable.ic_calendar)
                    .setLargeIcon(((BitmapDrawable) getResources()
                            .getDrawable(R.drawable.ic_launcher)).getBitmap())
                    .setContentTitle(getString(R.string.actualizacion_de_datos))
                    .setContentText(getString(R.string.actualizando))
                    .setTicker(getString(R.string.actualizacion_de_datos))
                    .setProgress(0, 0, true);
            mGestor.notify(INDETERMINATE_PROGRESS_NOTIF, mBuilder.build());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Se simula la ejecución de la operación (10 segundo).
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Se crea el intent que será enviado al pulsar la notificación.
            Intent intent = ResultadoActivity.getIntent(MainActivity.this,
                    getString(R.string.operacion_finalizada));
            // Se muestra la notificación final.
            mBuilder.setContentText(getString(R.string.operacion_finalizada))
                    .setTicker(getString(R.string.operacion_finalizada))
                    .setProgress(0, 0, false)
                    .setContentIntent(getPendingIntentForRegularActivity(intent))
                    .setAutoCancel(true);
            mGestor.notify(INDETERMINATE_PROGRESS_NOTIF, mBuilder.build());
        }

    }

    // Crea un pending intent para la actividad normal. Recibe el intent que será enviado
    // cuando se pulse sobre la notificación.
    private PendingIntent getPendingIntentForRegularActivity(Intent intent) {
        // Se crea la pila de navegación.
        TaskStackBuilder pila = TaskStackBuilder.create(this);
        // Similar a llamar primero a addParentStack y luego a addNextIntent.
        pila.addNextIntentWithParentStack(intent);
        // Se retorna un PendingIntent que contiene la pila al completo.
        return pila.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
    }

}
