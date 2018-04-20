package es.iessaladillo.pedrojoya.pr174.reminder;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr174.AppConstants;
import es.iessaladillo.pedrojoya.pr174.R;
import es.iessaladillo.pedrojoya.pr174.ui.main.MainActivity;

public class ReminderService extends JobService {

    public static final int DEFAULT_INTERVAL = 5000;

    private static final int REMINDER_JOB_ID = 1;
    private static final String KEY_MESSAGE = "KEY_MESSAGE";
    private static final int RC_ENTENDIDO = 1;
    private static final int NC_NOTIFICATION = 1;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        if (jobParameters.getJobId() == REMINDER_JOB_ID) {
            PersistableBundle extras = jobParameters.getExtras();
            showNotification(
                    extras.getString(KEY_MESSAGE, getString(R.string.activity_main_txtMessage)));
        }
        // Work done.
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Toast.makeText(this, "Trabajo parado", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void showNotification(String mensaje) {
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, RC_ENTENDIDO, i, 0);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NC_NOTIFICATION, new NotificationCompat.Builder(this,
                AppConstants.CHANNEL_ID).setSmallIcon(R.drawable.ic_info_outline)
                .setContentTitle(getString(R.string.aviso_importante))
                .setContentText(mensaje)
                .setDefaults(Notification.DEFAULT_ALL)
                .setTicker(getString(R.string.aviso_importante))
                .setAutoCancel(true)
                .setContentIntent(pi)
                .build());
    }

    public static JobInfo newReminderJob(Context context, int jobId, String message, int interval) {
        PersistableBundle extras = new PersistableBundle();
        extras.putString(ReminderService.KEY_MESSAGE, message);
        return new JobInfo.Builder(jobId, new ComponentName(context.getPackageName(),
                ReminderService.class.getName())).setMinimumLatency(interval)
                .setPersisted(true)
                .setExtras(extras)
                .build();
    }

}
