package es.iessaladillo.pedrojoya.pr102.data.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.text.TextUtils;

import es.iessaladillo.pedrojoya.pr102.alarm.AlarmBroadcastReceiver;
import es.iessaladillo.pedrojoya.pr102.R;

public class Alarm {

    private static final String SHARED_PREFERENCES_FILE_NAME = "alarms";
    private static final String PREF_MESSAGE = "pref_message";
    private static final String PREF_INTERVAL = "pref_interval";
    private static final String PREF_ON = "pref_on";

    private static final int INTERVAL_DEFAULT = 5000;
    private static final int RC_ALARM = 1;

    private static Alarm instance;

    private String message;
    private int interval;
    private boolean on;

    private final String messageDefault;

    private Alarm(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(
                SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        this.message = sharedPreferences.getString(PREF_MESSAGE,
                context.getString(R.string.activity_main_txtMessage));
        this.interval = sharedPreferences.getInt(PREF_INTERVAL, INTERVAL_DEFAULT);
        this.on = sharedPreferences.getBoolean(PREF_ON, false);
        messageDefault = context.getString(R.string.activity_main_txtMessage);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = TextUtils.isEmpty(message) ? messageDefault : message;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval <= 0 ? INTERVAL_DEFAULT : interval;
    }

    public boolean isOn() {
        return on;
    }

    public static Alarm getInstance(Context context) {
        if (instance == null) {
            instance = new Alarm(context);
        }
        return instance;
    }

    public void turnOn(Context context) {
        on = true;
        // Call alarm broadcast receiver when triggered
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(AlarmBroadcastReceiver.EXTRA_MESSAGE, message);
        PendingIntent pi = PendingIntent.getBroadcast(context, RC_ALARM, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + interval, interval, pi);
        }
        saveAlarm(context);
    }

    private void saveAlarm(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putBoolean(PREF_ON, on);
        editor.putString(PREF_MESSAGE, message);
        editor.putInt(PREF_INTERVAL, interval);
        editor.apply();
    }

    public void turnOff(Context context) {
        on = false;
        // Equivalent intent as when turned on.
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, RC_ALARM,
                intent, 0);
        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pi);
        }
        saveAlarm(context);
    }

}
