package es.iessaladillo.pedrojoya.pr096;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.security.Timestamp;
import java.util.Calendar;
import java.util.Date;


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
        }
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
        // Se crea el constructor de notificaciones.
        final NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        // Se configuran los elementos básicos de la notificación.
        b.setSmallIcon(R.drawable.ic_calendar); // Icono pequeño.
        // Al no especificar el icono grande se utiliza en su lugar el pequeño, que ya no se
        // muestra abajo a la derecha.
        b.setContentTitle("Content Title"); // Título (1ª línea).
        b.setContentText("Content Text"); // Texto (2º línea).
        // Ojo, no se puede poner SubText o ContentInfo, o no se mostrará la ProgressBar.
        b.setTicker("Ticker");  // Ticker.
        // Se crea e inicia un hilo secundario que actualiza la progress bar.
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        // Operación larga con 10 pasos.
                        for (incr = 0; incr < 100; incr+=10) {
                            // Se establece el progreso determinado con un máximo de 100.
                            b.setProgress(100, incr, false);
                            b.setContentInfo(incr + " %");
                            // Se muestra (actualiza) la notificación.
                            mGestor.notify(PROGRESS_BAR_NOTIF, b.build());
                            // Se simula la duración del paso (1 segundo).
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        // Cuando la operación ha concluido se actualiza el Content Text.
                        b.setContentText("Operación completada");
                        // Se elimina la Progress Bar de la notificación (poniendo el límite a 0).
                        b.setProgress(0, 0, false);
                        b.setContentInfo("100 %");
                        b.setTicker("Operación completada");
                        // Se actualiza la notificación.
                        mGestor.notify(PROGRESS_BAR_NOTIF, b.build());
                    }
                }
        ).start();
    }

    private void notificarProgresoIndeterminado() {
        // Se crea el constructor de notificaciones.
        final NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        // Se configuran los elementos básicos de la notificación.
        b.setSmallIcon(R.drawable.ic_calendar); // Icono pequeño.
        b.setLargeIcon(((BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher))
                .getBitmap()); // Icono grande.
        b.setContentTitle("Content Title"); // Título (1ª línea).
        b.setContentText("Content Text"); // Texto (2º línea).
        // Ojo, no se puede poner SubText o ContentInfo, o no se mostrará la ProgressBar.
        b.setTicker("Ticker");  // Ticker.
        // Se crea e inicia un hilo secundario que actualiza la progress bar.
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        // Se muestra la progress bar indeterminada.
                        b.setProgress(0, 0, true);
                        // Se muestra la notificación.
                        mGestor.notify(INDETERMINATE_PROGRESS_NOTIF, b.build());
                        // Se simula la duración de la operación (10 segundos).
                        try {
                            Thread.sleep(10 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // Cuando la operación ha concluido se actualiza el Content Text.
                        b.setContentText("Operación completada");
                        // Se elimina la Progress Bar de la notificación (poniendola como
                        // determinada con límite 0).
                        b.setProgress(0, 0, false);
                        b.setTicker("Operación completada");
                        // Se actualiza la notificación.
                        mGestor.notify(INDETERMINATE_PROGRESS_NOTIF, b.build());
                    }
                }
        ).start();
    }

}
