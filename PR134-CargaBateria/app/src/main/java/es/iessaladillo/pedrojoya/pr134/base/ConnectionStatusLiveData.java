package es.iessaladillo.pedrojoya.pr134.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import androidx.lifecycle.LiveData;

public class ConnectionStatusLiveData extends LiveData<ConnectionStatus> {

    private final Context context;

    private final InternetStatusBroadcastReceiver internetStatusBroadcastReceiver = new InternetStatusBroadcastReceiver();
    private final IntentFilter intentFilter = new IntentFilter(
            ConnectivityManager.CONNECTIVITY_ACTION);

    public ConnectionStatusLiveData(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    protected void onActive() {
        super.onActive();
        context.registerReceiver(internetStatusBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onInactive() {
        context.unregisterReceiver(internetStatusBroadcastReceiver);
        super.onInactive();
    }

    private class InternetStatusBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                postValue(new ConnectionStatus(intent));
            }
        }

    }

}
