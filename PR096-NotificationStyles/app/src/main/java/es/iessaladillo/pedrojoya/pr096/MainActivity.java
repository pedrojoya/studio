package es.iessaladillo.pedrojoya.pr096;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;


public class MainActivity extends Activity implements View.OnClickListener {

    // Constantes.
    private static final int NC_BIG_TEXT = 1;
    private static final int NC_BIG_PICTURE = 2;
    private static final int NC_INBOX = 3;
    private static final int NC_PROGRESS = 4;
    private static final int NC_IND_PROGRESS = 5;
    private static final int RC_BIG_TEXT_VIEW = 1;
    private static final int RC_BIG_TEXT_SEND = 2;
    private static final int RC_BIG_TEXT_DELETE = 3;
    private static final int RC_BIG_PICTURE_VIEW = 4;
    private static final int RC_INBOX_VIEW = 5;
    private static final int RC_PROGRESS_VIEW = 6;
    private static final int RC_IND_PROGRESS_VIEW = 7;

    // Variables a nivel de clase.
    private NotificationManager mGestor;

    // Al crear la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getVistas();
        // Se obtiene el gestor de notificaciones.
        mGestor =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        // La actividad actuará como listener del click sobre cualquier botón.
        findViewById(R.id.btnBigTextStyle).setOnClickListener(this);
        findViewById(R.id.btnBigPictureStyle).setOnClickListener(this);
        findViewById(R.id.btnInboxStyle).setOnClickListener(this);
        findViewById(R.id.btnProgressBar).setOnClickListener(this);
        findViewById(R.id.btnProgresoIndeterminado).setOnClickListener(this);
        findViewById(R.id.btnResultado).setOnClickListener(this);
    }

    // Al hacer click sobre un botón.
    @Override
    public void onClick(View view) {
        // Dependiendo del botón pulsado.
        switch (view.getId()) {
            case R.id.btnBigTextStyle:
                notificarBigText();
                break;
            case R.id.btnBigPictureStyle:
                notificarBigPicture();
                break;
            case R.id.btnInboxStyle:
                notificarInbox();
                break;
            case R.id.btnProgressBar:
                notificarProgressBar();
                break;
            case R.id.btnProgresoIndeterminado:
                notificarIndeterminateProgressBar();
                break;
            case R.id.btnResultado:
                verResultado();
                break;
        }
    }

    // Muestra una notificación con estilo de texto grande en el modo expandido.
    private void notificarBigText() {
        // Se crea el constructor de notificaciones.
        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        // Se configuran los elementos básicos de la notificación.
        BitmapDrawable recurso = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher);
        if (recurso != null) {
            Bitmap iconoGrande = recurso.getBitmap();
            b.setLargeIcon(iconoGrande);
        }
        b.setSmallIcon(R.drawable.ic_calendar)
                .setContentTitle(getString(R.string.content_title))
                .setContentText(getString(R.string.content_text))
                .setContentInfo(getString(R.string.content_info))
                .setTicker(getString(R.string.ticker))
                .setAutoCancel(true);
        // Se configuran los elementos para el modo expandido.
        NotificationCompat.BigTextStyle estilo = new NotificationCompat.BigTextStyle();
        estilo.setBigContentTitle(getString(R.string.big_content_title))
                .bigText(getString(R.string.big_text))
                .setSummaryText(getString(R.string.summary_text));
        b.setStyle(estilo);
        // Se añade la acción de enviar.
        Intent iEnviar = new Intent(this, ResultadoActivity.class);
        iEnviar.setAction(ResultadoActivity.ACTION_SEND);
        iEnviar.putExtra(ResultadoActivity.EXTRA_NOTIFICATION_CODE, NC_BIG_TEXT);
        PendingIntent piEnviar = getPendingIntentForRegularActivity(iEnviar);
        b.addAction(android.R.drawable.ic_menu_send, getString(R.string.enviar), piEnviar);
        // Se añade la acción de eliminar.
        Intent iEliminar = new Intent(this, ResultadoActivity.class);
        iEliminar.setAction(ResultadoActivity.ACTION_DELETE);
        iEliminar.putExtra(ResultadoActivity.EXTRA_NOTIFICATION_CODE, NC_BIG_TEXT);
        PendingIntent piEliminar = getPendingIntentForRegularActivity(iEliminar);
        b.addAction(android.R.drawable.ic_menu_delete, getString(R.string.eliminar), piEliminar);
        // Se añade la acción por defecto.
        Intent iVer = new Intent(this, ResultadoActivity.class);
        iVer.setAction(ResultadoActivity.ACTION_VIEW);
        PendingIntent piVer = getPendingIntentForRegularActivity(iVer);
        b.setContentIntent(piVer);
        // Se realiza la notificación.
        mGestor.notify(NC_BIG_TEXT, b.build());
    }

    // Muestra una notificación con estilo de imagen grande en el modo expandido.
    private void notificarBigPicture() {
        // Se crea el constructor de notificaciones.
        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        // Se configuran los elementos básicos de la notificación.
        BitmapDrawable recurso = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher);
        if (recurso != null) {
            Bitmap iconoGrande = recurso.getBitmap();
            b.setLargeIcon(iconoGrande);
        }
        b.setSmallIcon(R.drawable.ic_calendar)
                .setContentTitle(getString(R.string.content_title))
                .setContentText(getString(R.string.content_text))
                .setContentInfo(getString(R.string.content_info))
                .setTicker(getString(R.string.ticker))
                .setAutoCancel(true);
        // Se configuran los elementos para el modo expandido.
        NotificationCompat.BigPictureStyle estilo = new NotificationCompat.BigPictureStyle();
        estilo.setBigContentTitle(getString(R.string.big_content_title))
                .bigPicture(BitmapFactory.decodeResource(getResources(),
                        R.drawable.sunset))
                .setSummaryText(getString(R.string.summary_text));
        b.setStyle(estilo);
        // Se añade la acción por defecto.
        Intent iVer = new Intent(this, ResultadoActivity.class);
        iVer.setAction(ResultadoActivity.ACTION_VIEW);
        PendingIntent piVer = getPendingIntentForRegularActivity(iVer);
        b.setContentIntent(piVer);
        // Se realiza la notificación.
        mGestor.notify(NC_BIG_PICTURE, b.build());
    }

    // Muestra una notificación con estilo Inbox en el modo expandido.
    private void notificarInbox() {
        // Se crea el constructor de notificaciones.
        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        // Se configuran los elementos básicos de la notificación.
        BitmapDrawable recurso = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher);
        if (recurso != null) {
            Bitmap iconoGrande = recurso.getBitmap();
            b.setLargeIcon(iconoGrande);
        }
        b.setSmallIcon(R.drawable.ic_calendar)
                .setContentTitle(getString(R.string.content_title))
                .setContentText(getString(R.string.content_text))
                .setContentInfo(getString(R.string.content_info))
                .setTicker(getString(R.string.ticker))
                .setAutoCancel(true);
        // Se configuran los elementos para el modo expandido.
        NotificationCompat.InboxStyle estilo = new NotificationCompat.InboxStyle();
        estilo.setBigContentTitle(getString(R.string.big_content_title));
        for (int i = 0; i < 5; i++) {
            estilo.addLine(getString(R.string.line) + " " + (i + 1));
        }
        estilo.setSummaryText(getString(R.string.summary_text));
        b.setStyle(estilo);
        // Se añade la acción por defecto.
        Intent iVer = new Intent(this, ResultadoActivity.class);
        iVer.setAction(ResultadoActivity.ACTION_VIEW);
        PendingIntent piVer = getPendingIntentForRegularActivity(iVer);
        b.setContentIntent(piVer);
        // Se realiza la notificación.
        mGestor.notify(NC_INBOX, b.build());
    }

    // Muestra una notificación con una barra de progreso.
    private void notificarProgressBar() {
        // Se ejecuta la tarea asíncrona de actualización por pasos.
        new ActualizacionPorPasosTask().execute();
    }

    // Muestra una notificación con una barra de progreso indeterminado.
    private void notificarIndeterminateProgressBar() {
        // Se ejecuta la tarea asíncrona de actualización con progreso indeterminado.
        new ActualizacionIndeterminadaTask().execute();
    }

    // Tarea con progreso por pasos.
    private class ActualizacionPorPasosTask extends AsyncTask<Void, Integer, Void> {

        // Contantes.
        private static final int NUM_PASOS = 10;

        // Variables a nivel de clase.
        NotificationCompat.Builder mBuilder;

        // Antes de ejecutar (hilo principal).
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Se muestra la notificación inicial.
            mBuilder = new NotificationCompat.Builder(MainActivity.this);
            BitmapDrawable recurso = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher);
            if (recurso != null) {
                Bitmap iconoGrande = recurso.getBitmap();
                mBuilder.setLargeIcon(iconoGrande);
            }
            mBuilder.setSmallIcon(R.drawable.ic_calendar)
                    .setContentTitle(getString(R.string.actualizacion_de_datos))
                    .setContentText(getString(R.string.actualizando))
                    .setTicker(getString(R.string.actualizacion_de_datos))
                    .setProgress(NUM_PASOS, 0, false);
            mGestor.notify(NC_PROGRESS, mBuilder.build());
        }

        // Ejecución de la tarea (hilo secundario).
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

        // Al publicar progreso (hilo principal).
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int paso = values[0];
            // Se actualiza la notificación.
            mBuilder.setProgress(NUM_PASOS, paso, false)
                    .setContentText(getString(R.string.actualizando) +
                            " (" + paso + " " + getString(R.string.de) + " " + NUM_PASOS + ")");
            mGestor.notify(NC_PROGRESS, mBuilder.build());
        }

        // Al finalizar la ejecución (hilo principal).
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Se configura la notificación final.
            mBuilder.setContentText(getString(R.string.operacion_finalizada))
                    .setTicker(getString(R.string.operacion_finalizada))
                    .setProgress(0, 0, false)
                    .setAutoCancel(true);
            // Se añade la acción por defecto.
            Intent iVer = new Intent(MainActivity.this, ResultadoActivity.class);
            iVer.setAction(ResultadoActivity.ACTION_VIEW);
            PendingIntent piVer = getPendingIntentForRegularActivity(iVer);
            mBuilder.setContentIntent(piVer);
            // Se muestra la notificación.
            mGestor.notify(NC_PROGRESS, mBuilder.build());
        }

    }

    // Tarea con progreso indeterminado.
    private class ActualizacionIndeterminadaTask extends AsyncTask<Void, Void, Void> {

        // Variables a nivel de clase.
        NotificationCompat.Builder mBuilder;

        // Antes de ejecutar (hilo principal).
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Se muestra la notificación inicial.
            mBuilder = new NotificationCompat.Builder(MainActivity.this);
            BitmapDrawable recurso = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher);
            if (recurso != null) {
                Bitmap iconoGrande = recurso.getBitmap();
                mBuilder.setLargeIcon(iconoGrande);
            }
            mBuilder.setSmallIcon(R.drawable.ic_calendar)
                    .setContentTitle(getString(R.string.actualizacion_de_datos))
                    .setContentText(getString(R.string.actualizando))
                    .setTicker(getString(R.string.actualizacion_de_datos))
                    .setProgress(0, 0, true);
            mGestor.notify(NC_IND_PROGRESS, mBuilder.build());
        }

        // Ejecución de la tarea (hilo secundario).
        @Override
        protected Void doInBackground(Void... voids) {
            // Se simula la ejecución de la operación (10 segundos).
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        // Al finalizar la ejecución (hilo principal).
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Se configura la notificación final.
            mBuilder.setContentText(getString(R.string.operacion_finalizada))
                    .setTicker(getString(R.string.operacion_finalizada))
                    .setProgress(0, 0, false)
                    .setAutoCancel(true);
            // Se añade la acción por defecto.
            Intent iVer = new Intent(MainActivity.this, ResultadoActivity.class);
            iVer.setAction(ResultadoActivity.ACTION_VIEW);
            PendingIntent piVer = getPendingIntentForRegularActivity(iVer);
            mBuilder.setContentIntent(piVer);
            // Se muestra la notificación.
            mGestor.notify(NC_IND_PROGRESS, mBuilder.build());
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
        return pila.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // Muestra la actividad de resultado.
    private void verResultado() {
        Intent i = new Intent(MainActivity.this, ResultadoActivity.class);
        startActivity(i);
    }

}
