package es.iessaladillo.pedrojoya.pr134.base;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.lifecycle.LiveData;

public class BatteryStatusLiveData extends LiveData<BatteryStatus> {

    private static BatteryStatusLiveData instance;

    private final Application application;
    private final BatteryBroadcastReceiver batteryBroadcastReceiver = new BatteryBroadcastReceiver();
    private final IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

    private BatteryStatusLiveData(Application application) {
        this.application = application;
    }

    public static BatteryStatusLiveData getInstance(Application application) {
        if (instance == null) {
            synchronized (BatteryStatusLiveData.class) {
                if (instance == null) {
                    instance = new BatteryStatusLiveData(application);
                }
            }
        }
        return instance;
    }

    @Override
    protected void onActive() {
        super.onActive();
        application.registerReceiver(batteryBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onInactive() {
        application.unregisterReceiver(batteryBroadcastReceiver);
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
