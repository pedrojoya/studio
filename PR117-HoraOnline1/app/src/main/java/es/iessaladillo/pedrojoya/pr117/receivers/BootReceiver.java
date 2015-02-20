package es.iessaladillo.pedrojoya.pr117.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import es.iessaladillo.pedrojoya.pr117.sync.UTCTimeUpdateNeededAlarm;

// Receptor que será lanzado en el arranque del dispositivo.
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Se establece la alarma de actualización.
        UTCTimeUpdateNeededAlarm.set(context, UTCTimeUpdateNeededAlarm.ALARM_INTERVAL);
    }
}
