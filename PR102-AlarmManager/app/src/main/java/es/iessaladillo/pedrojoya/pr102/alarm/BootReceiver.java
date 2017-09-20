package es.iessaladillo.pedrojoya.pr102.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import es.iessaladillo.pedrojoya.pr102.data.model.Alarm;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_BOOT_COMPLETED)) {
            Alarm alarm = Alarm.getInstance(context);
            if (alarm.isOn()) {
                alarm.turnOn(context);
            }
        }
    }

}
