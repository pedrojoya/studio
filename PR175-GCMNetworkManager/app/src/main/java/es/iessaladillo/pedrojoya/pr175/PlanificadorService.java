package es.iessaladillo.pedrojoya.pr175;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;

public class PlanificadorService extends GcmTaskService {

    public static final int DEFAULT_INTERVAL = 5000;
    private static final int TRABAJO_ID = 1;
    public static final String KEY_MENSAJE = "key_mensaje";
    public static final String TRABAJO_MENSAJE_TAG = "trabajo_mensaje";

    private static final int RC_ENTENDIDO = 1;
    private static final int NC_AVISAR = 1;

    // Llamado por GCMNetworkManager después de actualizarse su versión.
    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
        // Volvemos a planificar la tarea.
        SharedPrefHelper prefs = new SharedPrefHelper(getResources(),
                PreferenceManager.getDefaultSharedPreferences(this));
        if (prefs.getBoolean(R.string.pref_activo, false)) {
            planificarTrabajo(this, prefs.getString(R.string.pref_mensaje,
                    getString(R.string.quillo_ponte_ya_a_currar)),
                    prefs.getInt(R.string.pref_intervalo, DEFAULT_INTERVAL));
        }
    }

    // Llamado por GCMNetworkManager cuando se lanza un trabajo.
    @Override
    public int onRunTask(TaskParams taskParams) {
        // Se muestra el mensaje.
        if (taskParams.getTag().equals(TRABAJO_MENSAJE_TAG)) {
            Bundle extras = taskParams.getExtras();
            mostrarNotificacion(extras.getString(KEY_MENSAJE, "Trabajo lanzado"));
            // Finalizada la planificación.
            return GcmNetworkManager.RESULT_SUCCESS;
        } else {
            return GcmNetworkManager.RESULT_FAILURE;
        }
    }

    // Muestra una notificación con el mensaje.
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
        Intent i = new Intent(this, es.iessaladillo.pedrojoya.pr175.MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, RC_ENTENDIDO, i,
                0);
        b.setContentIntent(pi);
        // Se construye y muestra la notificación.
        mGestor.notify(NC_AVISAR, b.build());
    }

    // Planifica un trabajo periódico persistente con el mensaje
    // y el intervalo recibidos.
    public static boolean planificarTrabajo(Context context,
                                            String mensaje, int intervalo) {
        Bundle extras = new Bundle();
        extras.putString(KEY_MENSAJE, mensaje);
        Task task = new PeriodicTask.Builder()
                .setService(PlanificadorService.class)
                .setPeriod(intervalo / 1000)
                .setFlex(1)
                .setUpdateCurrent(true)
                .setExtras(extras)
                .setTag(TRABAJO_MENSAJE_TAG)
                .setPersisted(true)
                .build();
        // Si está disponible GooglePlayServices se planifica la tarea.
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
                == ConnectionResult.SUCCESS) {
            GcmNetworkManager.getInstance(context).schedule(task);
            SharedPrefHelper prefs = new SharedPrefHelper(context.getResources(),
                    PreferenceManager.getDefaultSharedPreferences(context));
            // Se almacenan las preferencias.
            prefs.applyString(R.string.pref_mensaje, mensaje);
            prefs.applyInt(R.string.pref_intervalo, intervalo);
            prefs.applyBoolean(R.string.pref_activo, true);
            return true;
        } else {
            return false;
        }
    }

    // Cancela la planificación del trabajo.
    public static void cancelarPlanificacionTrabajo(Context context) {
        // Se cancela.
        GcmNetworkManager.getInstance(context).cancelTask(
                PlanificadorService.TRABAJO_MENSAJE_TAG, PlanificadorService.class);
        // Se guarda la preferencia.
        SharedPrefHelper prefs = new SharedPrefHelper(context.getResources(),
                PreferenceManager.getDefaultSharedPreferences(context));
        prefs.applyBoolean(R.string.pref_activo, false);
    }


}
