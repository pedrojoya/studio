package es.iessaladillo.pedrojoya.pr089.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class SummationService extends Service {

    public static final String EXTRA_NUMBER = "EXTRA_NUMBER";
    public static final String EXTRA_RESULT = "EXTRA_RESULT";
    public static final String ACTION_SUMMATION = "es.iessaladillo.pedrojoya.pr089.actions"
            + ".ACTION_SUMMATION";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(SummationService.class.getSimpleName(), "Service created");
    }

    @Override
    public void onDestroy() {
        Log.d(SummationService.class.getSimpleName(), "Service destroyed");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onHandleIntent(intent);
        // Not sticky service.
        return START_NOT_STICKY;
    }

    private void onHandleIntent(Intent intent) {
        if (intent != null && intent.hasExtra(EXTRA_NUMBER)) {
            long number = intent.getLongExtra(EXTRA_NUMBER, 0);
            (new Thread(() -> calculateSummation(number))).start();
        }
    }

    private void calculateSummation(long number) {
        long sum = summation(number);
        sendResult(number, sum);
    }

    private long summation(long number) {
        long result = 0;
        for (int i = 1; i <= number; i++) {
            result = result + i;
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private void sendResult(long number, long summation) {
        Intent intent = new Intent(ACTION_SUMMATION);
        intent.putExtra(EXTRA_NUMBER, number);
        intent.putExtra(EXTRA_RESULT, summation);
        if (!LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent)) {
            Log.d(SummationService.class.getSimpleName(), "Summation of " + number + " = " +
                    summation);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // It's a started service, not a bound service.
        return null;
    }

    public static void start(Context context, long number) {
        Intent intent = new Intent(context, SummationService.class);
        intent.putExtra(EXTRA_NUMBER, number);
        context.startService(intent);
    }

}
