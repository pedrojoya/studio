package es.iessaladillo.pedrojoya.pr096.ui.main;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.appcompat.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr096.Constants;
import es.iessaladillo.pedrojoya.pr096.R;
import es.iessaladillo.pedrojoya.pr096.ui.result.ResultActivity;

public class MainActivity extends AppCompatActivity {

    private static final int NC_BIG_TEXT = 1;
    private static final int NC_BIG_PICTURE = 2;
    private static final int NC_INBOX = 3;
    private static final int NC_PROGRESS = 4;
    private static final int NC_IND_PROGRESS = 5;
    private static final int NC_HEADS_UP = 6;

    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        notificationManager = NotificationManagerCompat.from(this);
    }

    private void initViews() {
        ActivityCompat.requireViewById(this, R.id.btnBigTextStyle).setOnClickListener(
                v -> notifyBigText());
        ActivityCompat.requireViewById(this, R.id.btnBigPictureStyle).setOnClickListener(
                v -> notifyBigPicture());
        ActivityCompat.requireViewById(this, R.id.btnInboxStyle).setOnClickListener(
                v -> notifyInbox());
        ActivityCompat.requireViewById(this, R.id.btnProgressBar).setOnClickListener(
                v -> notifyProgressBar());
        ActivityCompat.requireViewById(this, R.id.btnIndeterminateProgress).setOnClickListener(
                v -> notifyIndeterminateProgress());
        ActivityCompat.requireViewById(this, R.id.btnHeadsUp).setOnClickListener(
                v -> notifyHeadsUp());
    }

    private void notifyBigText() {
        NotificationCompat.Builder b = getBasicBuilder(Constants.DEFAULT_CHANNEL_ID);
        setBigTextStyle(b);
        addDefaultAction(b);
        addSendActionButton(b);
        addDeleteActionButton(b);
        notificationManager.notify(NC_BIG_TEXT, b.build());
    }

    private void notifyBigPicture() {
        NotificationCompat.Builder b = getBasicBuilder(Constants.DEFAULT_CHANNEL_ID);
        Bitmap bigPicture = BitmapFactory.decodeResource(getResources(), R.drawable.sunset);
        b.setLargeIcon(bigPicture);
        setBigPictureStyle(b, bigPicture);
        addDefaultAction(b);
        notificationManager.notify(NC_BIG_PICTURE, b.build());
    }

    private void notifyInbox() {
        NotificationCompat.Builder b = getBasicBuilder(Constants.DEFAULT_CHANNEL_ID);
        setInbotStyle(b);
        addDefaultAction(b);
        notificationManager.notify(NC_INBOX, b.build());
    }

    private void notifyProgressBar() {
        new ProgressTask(this).execute();
    }

    private void notifyIndeterminateProgress() {
        new IndeterminateProgressTask(this).execute();
    }

    private void notifyHeadsUp() {
        NotificationCompat.Builder b = getBasicBuilder(Constants.HIGH_PRIORITY_CHANNEL_ID);
        b.setPriority(NotificationManagerCompat.IMPORTANCE_HIGH);
        addAnswerActionButton(b);
        addDefaultAction(b);
        notificationManager.notify(NC_HEADS_UP, b.build());
    }

    @NonNull
    private NotificationCompat.Builder getBasicBuilder(String channelId) {
        NotificationCompat.Builder b = new NotificationCompat.Builder(this, channelId);
        b.setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(getString(R.string.content_title))
                .setContentText(getString(R.string.content_text))
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                .setAutoCancel(true);
        return b;
    }

    private void setBigTextStyle(NotificationCompat.Builder b) {
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle().setBigContentTitle(
                getString(R.string.big_content_title))
                .bigText(getString(R.string.big_text))
                .setSummaryText(getString(R.string.summary_text));
        b.setStyle(bigTextStyle);
    }

    private void setBigPictureStyle(NotificationCompat.Builder b, Bitmap bitPicture) {
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(getString(R.string.big_content_title)).bigPicture(
                bitPicture).bigLargeIcon(null).setSummaryText(getString(R.string.summary_text));
        b.setStyle(bigPictureStyle);
    }

    private void setInbotStyle(NotificationCompat.Builder b) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(getString(R.string.big_content_title));
        for (int i = 0; i < 5; i++) {
            inboxStyle.addLine(getString(R.string.line, i + 1));
        }
        inboxStyle.setSummaryText(getString(R.string.summary_text));
        b.setStyle(inboxStyle);
    }

    private void addDefaultAction(NotificationCompat.Builder b) {
        Intent viewIntent = new Intent(this, ResultActivity.class);
        viewIntent.setAction(ResultActivity.ACTION_VIEW);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, 0);
        b.setContentIntent(viewPendingIntent);
    }

    private void addSendActionButton(NotificationCompat.Builder b) {
        Intent sendIntent = new Intent(this, ResultActivity.class);
        sendIntent.setAction(ResultActivity.ACTION_SEND);
        sendIntent.putExtra(ResultActivity.EXTRA_NOTIFICATION_CODE, NC_BIG_TEXT);
        PendingIntent sendPendingIntent = PendingIntent.getActivity(this, 0, sendIntent, 0);
        b.addAction(R.drawable.ic_send_black_24dp, getString(R.string.main_activity_send), sendPendingIntent);
    }

    private void addDeleteActionButton(NotificationCompat.Builder b) {
        Intent deleteIntent = new Intent(this, ResultActivity.class);
        deleteIntent.setAction(ResultActivity.ACTION_DELETE);
        deleteIntent.putExtra(ResultActivity.EXTRA_NOTIFICATION_CODE, NC_BIG_TEXT);
        PendingIntent deletePendingIntent = PendingIntent.getActivity(this, 0, deleteIntent, 0);
        b.addAction(R.drawable.ic_delete_black_24dp, getString(R.string.main_activity_delete),
                deletePendingIntent);
    }

    private void addAnswerActionButton(NotificationCompat.Builder b) {
        Intent answerIntent = new Intent(this, ResultActivity.class);
        answerIntent.setAction(ResultActivity.ACTION_ANSWER);
        answerIntent.putExtra(ResultActivity.EXTRA_NOTIFICATION_CODE, NC_HEADS_UP);
        PendingIntent answerPendingIntent = PendingIntent.getActivity(this, 0, answerIntent, 0);
        b.addAction(R.drawable.ic_send_black_24dp, getString(R.string.main_activity_answer),
                answerPendingIntent);
    }

    @SuppressLint("StaticFieldLeak")
    private static class ProgressTask extends AsyncTask<Void, Integer, Void> {

        private static final int STEPS = 10;

        private final NotificationManagerCompat notificationManager;
        private final Context context;
        private final NotificationCompat.Builder b;

        ProgressTask(Context context) {
            this.context = context.getApplicationContext();
            b = new NotificationCompat.Builder(context, Constants.DEFAULT_CHANNEL_ID);
            b.setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setContentTitle(context.getString(R.string.main_activity_progress))
                    .setContentText(context.getString(R.string.main_activity_updating))
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                    .setOnlyAlertOnce(true)
                    .setProgress(STEPS, 0, false);
            notificationManager = NotificationManagerCompat.from(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            notificationManager.notify(NC_PROGRESS, b.build());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < STEPS; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i + 1);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int step = values[0];
            // Update notification.
            b.setProgress(STEPS, step, false).setContentText(
                    context.getString(R.string.main_activity_updating, step, STEPS));
            notificationManager.notify(NC_PROGRESS, b.build());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Final notification update.
            b.setContentText(context.getString(R.string.main_activity_update_finished))
                    .setTicker(context.getString(R.string.main_activity_update_finished))
                    .setProgress(0, 0, false)
                    .setAutoCancel(true);
            Intent viewIntent = new Intent(context, ResultActivity.class);
            viewIntent.setAction(ResultActivity.ACTION_VIEW);
            PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, viewIntent,
                    0);
            b.setContentIntent(viewPendingIntent);
            notificationManager.notify(NC_PROGRESS, b.build());
        }

    }

    @SuppressLint("StaticFieldLeak")
    private static class IndeterminateProgressTask extends AsyncTask<Void, Void, Void> {

        private final NotificationManagerCompat notificationManager;
        private final Context context;
        private final NotificationCompat.Builder b;

        private IndeterminateProgressTask(Context context) {
            this.context = context.getApplicationContext();
            b = new NotificationCompat.Builder(context, Constants.DEFAULT_CHANNEL_ID);
            b.setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setContentTitle(context.getString(R.string.main_activity_progress))
                    .setContentText(context.getString(R.string
                            .main_activity_indeterminate_progress))
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                    .setOnlyAlertOnce(true)
                    .setProgress(0, 0, true);
            notificationManager = NotificationManagerCompat.from(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            notificationManager.notify(NC_IND_PROGRESS, b.build());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Final notification update.
            b.setContentText(context.getString(R.string.main_activity_update_finished))
                    .setProgress(0, 0, false)
                    .setAutoCancel(true);
            Intent viewIntent = new Intent(context, ResultActivity.class);
            viewIntent.setAction(ResultActivity.ACTION_VIEW);
            PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, viewIntent,
                    0);
            b.setContentIntent(viewPendingIntent);
            notificationManager.notify(NC_IND_PROGRESS, b.build());
        }

    }

}
