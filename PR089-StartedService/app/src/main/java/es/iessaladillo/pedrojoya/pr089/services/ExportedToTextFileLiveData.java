package es.iessaladillo.pedrojoya.pr089.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import es.iessaladillo.pedrojoya.pr089.base.Event;

public class ExportedToTextFileLiveData extends LiveData<Event<Uri>> {

    private final Context context;
    private final BroadcastReceiver broadcastReceiver;
    private final IntentFilter intentFilter = new IntentFilter(ExportToTextFileService.ACTION_EXPORTED);

    public ExportedToTextFileLiveData(Context context) {
        this.context = context.getApplicationContext();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.hasExtra(ExportToTextFileService.EXTRA_FILENAME)) {
                    postValue(new Event<>(intent.getParcelableExtra(ExportToTextFileService
                            .EXTRA_FILENAME)));
                }
            }
        };
    }

    @Override
    protected void onActive() {
        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onInactive() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
    }

}
