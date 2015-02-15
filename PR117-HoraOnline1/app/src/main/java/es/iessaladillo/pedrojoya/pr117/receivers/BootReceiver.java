package es.iessaladillo.pedrojoya.pr117.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import es.iessaladillo.pedrojoya.pr117.alarms.UTCTimeUpdateNeededAlarm;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        UTCTimeUpdateNeededAlarm.set(context, UTCTimeUpdateNeededAlarm.ALARM_INTERVAL);
    }
}
