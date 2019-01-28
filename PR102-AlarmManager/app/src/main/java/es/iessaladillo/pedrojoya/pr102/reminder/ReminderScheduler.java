package es.iessaladillo.pedrojoya.pr102.reminder;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.AlarmClock;

import java.util.Calendar;

import androidx.core.app.AlarmManagerCompat;
import es.iessaladillo.pedrojoya.pr102.ui.edit.EditActivity;

public class ReminderScheduler {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final String EXTRA_WHEN = "EXTRA_WHEN";

    private static final String SHARED_PREFERENCES_FILE_NAME = "reminder";
    private static final String PREF_REMINDER_MESSAGE = "PREF_REMINDER_MESSAGE";
    private static final String PREF_REMINDER_WHEN = "PREF_REMINDER_WHEN";
    private static final String PREF_REMINDER_CLOCK = "PREF_REMINDER_CLOCK";
    private static final String PREF_REMINDER_ON = "PREF_REMINDER_ON";
    private static final int RC_ALARM = 1;
    private static final int RC_EDIT = 2;

    private static ReminderScheduler INSTANCE;

    private final AlarmManager alarmManager;
    private final SharedPreferences sharedPreferences;
    private final Application application;

    private ReminderScheduler(Application application) {
        this.application = application;
        alarmManager = (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
        sharedPreferences = application.getApplicationContext().getSharedPreferences(
            SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static ReminderScheduler getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new ReminderScheduler(application);
        }
        return INSTANCE;
    }

    public void createReminder(String message, Calendar calendar) {
        long when = calendar.getTimeInMillis();
        Intent triggerAlarmIntent = new Intent(application, ReminderReceiver.class);
        triggerAlarmIntent.putExtra(EXTRA_MESSAGE, message);
        PendingIntent triggerAlarmPendingIntent = PendingIntent.getBroadcast(application, RC_ALARM,
            triggerAlarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManagerCompat.setExactAndAllowWhileIdle(alarmManager, AlarmManager.RTC_WAKEUP, when,
            triggerAlarmPendingIntent);
        saveToPreferences(message, calendar.getTimeInMillis(), false, true);
    }

    public void createClockAlarm(String message, Calendar calendar) {
        long when = calendar.getTimeInMillis();
        Intent triggerAlarmIntent = new Intent(application, ReminderReceiver.class);
        triggerAlarmIntent.putExtra(EXTRA_MESSAGE, message);
        PendingIntent triggerAlarmPendingIntent = PendingIntent.getBroadcast(application, RC_ALARM,
            triggerAlarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Intent editIntent = new Intent(application, EditActivity.class);
        editIntent.putExtra(EXTRA_MESSAGE, message);
        editIntent.putExtra(EXTRA_WHEN, when);
        PendingIntent editPendingIntent = PendingIntent.getActivity(application, RC_EDIT, editIntent,
            PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(
            calendar.getTimeInMillis(), editPendingIntent);
        alarmManager.setAlarmClock(alarmClockInfo, triggerAlarmPendingIntent);
        saveToPreferences(message, calendar.getTimeInMillis(), true, true);
    }

    public void sendToClockApp(String message, Calendar calendar) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM).putExtra(AlarmClock.EXTRA_MESSAGE,
            message).putExtra(AlarmClock.EXTRA_HOUR, calendar.get(Calendar.HOUR_OF_DAY)).putExtra(
            AlarmClock.EXTRA_MINUTES, calendar.get(Calendar.MINUTE));
        if (intent.resolveActivity(application.getPackageManager()) != null) {
            application.startActivity(intent);
        }
    }

    public void cancelReminder() {
        Intent triggerAlarmIntent = new Intent(application, ReminderReceiver.class);
        PendingIntent triggerAlarmPendingIntent = PendingIntent.getBroadcast(application, RC_ALARM,
            triggerAlarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(triggerAlarmPendingIntent);
        saveToPreferences("", 0, false, false);
    }

    private void saveToPreferences(String message, long when, boolean clock, boolean on) {
        sharedPreferences.edit().putString(PREF_REMINDER_MESSAGE, message).putLong(
            PREF_REMINDER_WHEN, when).putBoolean(PREF_REMINDER_CLOCK, clock).putBoolean(
            PREF_REMINDER_ON, on).apply();
    }

    void onBoot() {
        if (sharedPreferences.getBoolean(PREF_REMINDER_ON, false)) {
            Calendar when = Calendar.getInstance();
            when.setTimeInMillis(sharedPreferences.getLong(PREF_REMINDER_WHEN, 0));
            String message = sharedPreferences.getString(PREF_REMINDER_MESSAGE, "");
            if (sharedPreferences.getBoolean(PREF_REMINDER_CLOCK, false)) {
                createClockAlarm(message, when);
            } else {
                createReminder(message, when);
            }
        }
    }

}
