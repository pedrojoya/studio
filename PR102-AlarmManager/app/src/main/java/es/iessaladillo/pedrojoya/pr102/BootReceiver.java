package es.iessaladillo.pedrojoya.pr102;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    // Al recibirse el broadcast.
    @Override
    public void onReceive(Context context, Intent intent) {
        // Si la alarma debe estar on, se reprograma.
        if (AvisarReceiver.isAlarmaOn(context)) {
            AvisarReceiver.reprogramarAlarma(context);
        }
    }

}
