package es.iessaladillo.pedrojoya.pr117.sync;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import es.iessaladillo.pedrojoya.pr117.receivers.UTCTimeUpdateNeededReceiver;

public class UTCTimeUpdateNeededAlarm {

    public static final int ALARM_INTERVAL = 10000;

    private static final int RC_UTCTIME_UPDATE_NEEDED = 1;

    // Establece la alarma de actualización con un determinado intervalo de repetición.
    public static void set(Context context, int intervalo) {
        AlarmManager am = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, UTCTimeUpdateNeededReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, RC_UTCTIME_UPDATE_NEEDED,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        // Se añade la alarma de tipo repetitivo y despertador.
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + intervalo, intervalo, pi);
    }

}
