package es.iessaladillo.pedrojoya.pr117.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import es.iessaladillo.pedrojoya.pr117.R;
import es.iessaladillo.pedrojoya.pr117.activities.MainActivity;

public class UTCTimeUpdatedReceiver extends BroadcastReceiver {
    private static final int NC_UTCTIME_UPDATED = 10;

    @Override
    public void onReceive(Context context, Intent intent) {
        mostrarNotificacion(context);
    }

    private void mostrarNotificacion(Context context) {
        NotificationManager nm =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder b = new NotificationCompat.Builder(context.getApplicationContext());
        b.setSmallIcon(R.drawable.ic_launcher);
        b.setContentTitle("UTCTime");
        b.setContentText("Se ha actualizado la hora UTC en la base de datos");
        b.setTicker("Hora UTC actualizada");
        b.setAutoCancel(true);
        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        b.setContentIntent(pendingIntent);
        nm.notify(NC_UTCTIME_UPDATED, b.build());
    }
}
