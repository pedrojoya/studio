package es.iessaladillo.pedrojoya.pr096;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

@SuppressWarnings("WeakerAccess")
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        createChannel();
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(
                    Context.NOTIFICATION_SERVICE);
            // Normal channel.
            NotificationChannel defaultChannel = new NotificationChannel(
                    Constants.DEFAULT_CHANNEL_ID,
                    getString(R.string.default_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            defaultChannel.setDescription(getString(R.string.default_channel_name));
            //noinspection ConstantConditions
            notificationManager.createNotificationChannel(defaultChannel);
            // High priority channel.
            NotificationChannel highPriorityChannel = new NotificationChannel(
                    Constants.HIGH_PRIORITY_CHANNEL_ID,
                    getString(R.string.high_priority_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            defaultChannel.setDescription(getString(R.string.high_priority_channel_name));
            notificationManager.createNotificationChannel(highPriorityChannel);
        }
    }

}
