package es.iessaladillo.pedrojoya.pr193;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

public class PlanificadorService extends JobService {

    public static final int DEFAULT_INTERVAL = 5000;

    private static final String TRABAJO_MENSAJE_TAG = "TRABAJO_MENSAJE_TAG";
    private static final String KEY_MENSAJE = "key_mensaje";
    private static final int TRABAJO_ID = 1;
    private static final int RC_ENTENDIDO = 1;
    private static final int NC_AVISAR = 1;

    // Cuando se lanza un trabajo.
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        // Se muestra el mensaje.
        if (jobParameters.getTag() == TRABAJO_MENSAJE_TAG) {
            Bundle extras = jobParameters.getExtras();
            mostrarNotificacion(extras.getString(KEY_MENSAJE, "Trabajo lanzado"));
        }
        // Se indica que el trabajo ha finalizado.
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
        Toast.makeText(this, "Ponte a currar", Toast.LENGTH_LONG).show();
    }

    // Planifica un trabajo periódico persistente con el mensaje
    // y el intervalo recibidos. Retorna el id del trabajo.
    public static String planificarTrabajo(Context context, String mensaje, int intervalo) {
        // Se obtiene el dispatcher.
        FirebaseJobDispatcher planificador = new FirebaseJobDispatcher(
                new GooglePlayDriver(context));
        // Se obtiene el constructor de trabajos.
        Job.Builder builder = planificador.newJobBuilder();
        builder.setService(PlanificadorService.class);
        builder.setTag(TRABAJO_MENSAJE_TAG);
        // Se especifican las condiciones de disparo.
        builder.setRecurring(false); // Periódico.
        builder.setTrigger(Trigger.executionWindow(0, 60));
        builder.setRetryStrategy(RetryStrategy.DEFAULT_LINEAR);
        builder.setLifetime(Lifetime.FOREVER); // Persistirá tras reinicio.
        Bundle extras = new Bundle();
        extras.putString(PlanificadorService.KEY_MENSAJE, mensaje);
        builder.setExtras(extras);
        builder.setReplaceCurrent(true);
        builder.setConstraints(Constraint.ON_ANY_NETWORK);
        // Se planifica el trabajo.
        try {
            planificador.mustSchedule(builder.build());
            // Se guardan las preferencias.
            SharedPrefHelper prefs = new SharedPrefHelper(context.getResources(),
                    PreferenceManager.getDefaultSharedPreferences(context));
            prefs.applyString(R.string.pref_mensaje, mensaje);
            prefs.applyInt(R.string.pref_intervalo, intervalo);
            prefs.applyBoolean(R.string.pref_activo, true);
            // Se retorna el tag con el que se identifica.
            return TRABAJO_MENSAJE_TAG;
        } catch (Exception e) {
            e.printStackTrace();
            // Se retorna como tag una cadena vacía.
            return "";
        }
    }

    // Cancela la planificación del trabajo.
    public static void cancelarPlanificacionTrabajo(Context context, String trabajoTag) {
        // Se obtiene el dispatcher.
        FirebaseJobDispatcher planificador = new FirebaseJobDispatcher(
                new GooglePlayDriver(context));
        // Se cancela el trabajo.
        planificador.cancel(trabajoTag);
        // Se guarda la preferencia.
        SharedPrefHelper prefs = new SharedPrefHelper(context.getResources(),
                PreferenceManager.getDefaultSharedPreferences(context));
        prefs.applyBoolean(R.string.pref_activo, false);
    }

}
