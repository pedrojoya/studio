package es.iessaladillo.pedrojoya.pr117.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import es.iessaladillo.pedrojoya.pr117.services.ApiService;

public class UTCTimeUpdateNeededReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context.getApplicationContext(), ApiService.class);
        context.startService(i);
    }

}
