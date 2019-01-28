package es.iessaladillo.pedrojoya.pr102.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import es.iessaladillo.pedrojoya.pr102.App;

public class ReminderBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_BOOT_COMPLETED)) {
            ReminderScheduler.getInstance((App) context.getApplicationContext()).onBoot();
        }
    }

}
