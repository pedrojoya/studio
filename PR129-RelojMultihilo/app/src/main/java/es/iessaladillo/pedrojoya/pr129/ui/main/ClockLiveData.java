package es.iessaladillo.pedrojoya.pr129.ui.main;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
                    Log.d("Mia", "Thread stopped");
                    return;
                }
            }
            Log.d("Mia", "Thread stopped");
        });
        thread.start();
        Log.d("Mia", "Thread started");
    }

    public void stop() {
        if (isRunning()) {
            thread.interrupt();
            Log.d("Mia", "Thread interrupted");
        }
    }

    public boolean isRunning() {
        return thread != null && thread.isAlive();
    }

}
