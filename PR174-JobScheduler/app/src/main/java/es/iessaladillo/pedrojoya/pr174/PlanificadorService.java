package es.iessaladillo.pedrojoya.pr174;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class PlanificadorService extends JobService {

    public static final String KEY_MENSAJE = "key_mensaje";
    public static final int TRABAJO_MENSAJE_ID = 1;

    private static final int RC_ENTENDIDO = 1;
    private static final int NC_AVISAR = 1;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        // Se muestra el mensaje.
        if (jobParameters.getJobId() == TRABAJO_MENSAJE_ID) {
            PersistableBundle extras = jobParameters.getExtras();
            mostrarNotificacion(extras.getString(KEY_MENSAJE, "Trabajo lanzado"));
        }
        // Finalizada la planificación.
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Toast.makeText(this, "Trabajo parado", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void mostrarNotificacion(String mensaje) {
        // Se obtiene el gestor de notificaciones del sistema.
        NotificationManager mGestor = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        // Se configura la notificación.
        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        b.setSmallIcon(R.drawable.ic_info_outline);
        b.setContentTitle(getString(R.string.aviso_importante));
        b.setContentText(mensaje);
        b.setDefaults(Notification.DEFAULT_ALL);
        b.setTicker(getString(R.string.aviso_importante));
        b.setAutoCancel(true);
        // Al pulsarse la notificación se mostrará la actividad principal.
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, RC_ENTENDIDO, i,
                0);
        b.setContentIntent(pi);
        // Se construye y muestra la notificación.
        mGestor.notify(NC_AVISAR, b.build());
    }

}
