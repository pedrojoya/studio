package es.iessaladillo.pedrojoya.pr174;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class PlanificadorService extends JobService {

    public static final int DEFAULT_INTERVAL = 5000;

    private static final int TRABAJO_MENSAJE_ID = 1;
    private static final String KEY_MENSAJE = "key_mensaje";
    private static final int TRABAJO_ID = 1;
    private static final int RC_ENTENDIDO = 1;
    private static final int NC_AVISAR = 1;

    // Cuando se lanza un trabajo.
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        // Se muestra el mensaje.
        if (jobParameters.getJobId() == TRABAJO_MENSAJE_ID) {
            PersistableBundle extras = jobParameters.getExtras();
            mostrarNotificacion(extras.getString(KEY_MENSAJE, "Trabajo lanzado"));
        }
        // Se indica qua el trabajo ha finalizado.
        return false;
    }

    // Cuando se para un trabajo en ejecución.
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Toast.makeText(this, "Trabajo parado", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void mostrarNotificacion(String mensaje) {
        // Se obtiene el gestor de notificaciones del sistema.
        NotificationManager mGestor = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
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
        PendingIntent pi = PendingIntent.getActivity(this, RC_ENTENDIDO, i, 0);
        b.setContentIntent(pi);
        // Se construye y muestra la notificación.
        mGestor.notify(NC_AVISAR, b.build());
    }

    // Planifica un trabajo periódico persistente con el mensaje
    // y el intervalo recibidos. Retorna el id del trabajo.
    public static int planificarTrabajo(Context context, String mensaje, int intervalo) {
        // Se obtiene el constructor de trabajos aportándole el ID que queremos
        // para el trabajo y el nombre de la clase correspondiente al servicio
        // que será llamado cuando se "dispare" el trabajo.
        JobInfo.Builder builder = new JobInfo.Builder(TRABAJO_ID,
                new ComponentName(context.getPackageName(), PlanificadorService.class.getName()));
        // Se especifican las condiciones de disparo.
        builder.setPeriodic(intervalo);
        builder.setPersisted(true);
        PersistableBundle extras = new PersistableBundle();
        extras.putString(PlanificadorService.KEY_MENSAJE, mensaje);
        builder.setExtras(extras);
        // Se planifica el trabajo.
        JobScheduler planificador = (JobScheduler) context.getSystemService(
                Context.JOB_SCHEDULER_SERVICE);
        int trabajoId = planificador.schedule(builder.build());
        if (trabajoId > 0) {
            // Se guardan las preferencias.
            SharedPrefHelper prefs = new SharedPrefHelper(context.getResources(),
                    PreferenceManager.getDefaultSharedPreferences(context));
            prefs.applyString(R.string.pref_mensaje, mensaje);
            prefs.applyInt(R.string.pref_intervalo, intervalo);
            prefs.applyBoolean(R.string.pref_activo, true);
        }
        return trabajoId;
    }

    // Cancela la planificación del trabajo.
    public static void cancelarPlanificacionTrabajo(Context context, int trabajoId) {
        // Se obtiene el planificador de trabajos.
        JobScheduler planificador = (JobScheduler) context.getSystemService(
                Context.JOB_SCHEDULER_SERVICE);
        // Se cancela.
        planificador.cancel(trabajoId);
        // Se guarda la preferencia.
        SharedPrefHelper prefs = new SharedPrefHelper(context.getResources(),
                PreferenceManager.getDefaultSharedPreferences(context));
        prefs.applyBoolean(R.string.pref_activo, false);
    }

}
