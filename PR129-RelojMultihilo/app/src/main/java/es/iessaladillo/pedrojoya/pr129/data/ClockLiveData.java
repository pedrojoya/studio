package es.iessaladillo.pedrojoya.pr129.data;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.lifecycle.LiveData;

@SuppressWarnings("WeakerAccess")
public class ClockLiveData extends LiveData<String> {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    private Thread thread;

    public void start() {
        if (thread == null || !thread.isAlive())
        thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                postValue(simpleDateFormat.format(new Date()));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // Interrupted while sleeping.
                    Log.d(ClockLiveData.class.getSimpleName(), "Thread stopped");
                    return;
                }
            }
            Log.d(ClockLiveData.class.getSimpleName(), "Thread stopped");
        });
        thread.start();
        Log.d(ClockLiveData.class.getSimpleName(), "Thread started");
    }

    public void stop() {
        if (isRunning()) {
            thread.interrupt();
            Log.d(ClockLiveData.class.getSimpleName(), "Thread interrupted");
        }
    }

    public boolean isRunning() {
        return thread != null && thread.isAlive();
    }

}
