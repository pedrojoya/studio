package es.iessaladillo.pedrojoya.pr117.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import es.iessaladillo.pedrojoya.pr117.R;
import es.iessaladillo.pedrojoya.pr117.activities.MainActivity;

// Receptor registrado estáticamente que será lanzado cuando el servicio de API haya actualizado
// los datos en el Content Provider.
public class UTCTimeUpdatedReceiver extends BroadcastReceiver {

    private static final int NC_UTCTIME_UPDATED = 10;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Se muestra una notificación que al pulsarla llevará a la actividad principal.
        mostrarNotificacion(context);
    }

    // Muestra una notificación informando al usuario de que los datos han sido actualizados, de
    // manera que si el usuario la pulsa será llevado a la actividad principal.
    private void mostrarNotificacion(Context context) {
        NotificationManager nm =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder b = new NotificationCompat.Builder(context.getApplicationContext());
        b.setSmallIcon(R.drawable.ic_launcher);
        b.setContentTitle(context.getString(R.string.app_name));
        b.setContentText(context.getString(R.string.hora_actualizada));
        b.setTicker(context.getString(R.string.hora_utc_actualizada));
        b.setAutoCancel(true);
        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        b.setContentIntent(pendingIntent);
        nm.notify(NC_UTCTIME_UPDATED, b.build());
    }

}
