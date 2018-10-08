package es.iessaladillo.pedrojoya.pr134.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class BatteryStatusLiveData extends LiveData<BatteryStatus> {

    private final Context context;

    private final BatteryBroadcastReceiver batteryBroadcastReceiver = new BatteryBroadcastReceiver();
    private final IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

    public BatteryStatusLiveData(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    protected void onActive() {
        super.onActive();
        context.registerReceiver(batteryBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onInactive() {
        context.unregisterReceiver(batteryBroadcastReceiver);
        super.onInactive();
    }

    private class BatteryBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent !=  null) {
                postValue(new BatteryStatus(intent));
            }
        }

    }

}
