package es.iessaladillo.pedrojoya.pr102.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import es.iessaladillo.pedrojoya.pr102.R;
import es.iessaladillo.pedrojoya.pr102.ui.reminder.ReminderActivity;

public class ReminderReceiver extends BroadcastReceiver {

    public static final String MAIN_CHANNEL_ID = "MAIN_CHANNEL_ID";

    private static final int NC_WARNING = 1;
    private static final int RC_WARNING = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context, intent);
    }

    private void showNotification(Context context, Intent intent) {
        NotificationCompat.Builder b = new NotificationCompat.Builder(context, MAIN_CHANNEL_ID);
        b.setSmallIcon(R.drawable.ic_info_outline_white_24dp);
        b.setContentTitle(context.getString(R.string.main_important_warning));
        b.setContentText(intent.getStringExtra(ReminderScheduler.EXTRA_MESSAGE));
        b.setDefaults(Notification.DEFAULT_ALL);
        b.setTicker(context.getString(R.string.main_important_warning));
        b.setAutoCancel(true);
        Intent warningIntent = new Intent(context, ReminderActivity.class);
        warningIntent.putExtra(ReminderScheduler.EXTRA_MESSAGE,
            intent.getStringExtra(ReminderScheduler.EXTRA_MESSAGE));
        PendingIntent pi = PendingIntent.getActivity(context, RC_WARNING, warningIntent,
            PendingIntent.FLAG_UPDATE_CURRENT);
        b.setContentIntent(pi);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
            Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(NC_WARNING, b.build());
        }
    }

}
