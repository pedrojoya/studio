package es.iessaladillo.pedrojoya.pr242.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr242.BuildConfig;
import es.iessaladillo.pedrojoya.pr242.R;
import es.iessaladillo.pedrojoya.pr242.base.Resource;

@SuppressWarnings("WeakerAccess")
public class ExportToTextFileService extends IntentService {

    public static final String CHANNEL_ID = "foreground_service";
    private static final String EXTRA_DATA = "EXTRA_DATA";
    private static final int PROGRESS_NOTIF_ID = 100;
    private static final int RESULT_NOTIF_ID = 101;

    private static final MutableLiveData<Resource<Uri>> _result = new MutableLiveData<>();
    public static final LiveData<Resource<Uri>> result = _result;

    private NotificationCompat.Builder builder;

    public ExportToTextFileService() {
        super("ExportToTextFileService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(PROGRESS_NOTIF_ID, buildForegroundNotification());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            _result.postValue(Resource.loading());
            File outputFile = createFile();
            try {
                Thread.sleep(5000);
                writeListToFile(outputFile, intent.getStringArrayListExtra(EXTRA_DATA));
                showResultNotification(outputFile);
            } catch (FileNotFoundException | InterruptedException e) {
                _result.postValue(Resource.error(e));
            }
            _result.postValue(Resource.success(Uri.fromFile(outputFile)));
        }
        stopForeground(true);
    }

    private void showResultNotification(File outputFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW).setDataAndType(
                FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider",
                        outputFile), "text/plain").addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder = new NotificationCompat.Builder(this, CHANNEL_ID).setOngoing(true)
                .setSmallIcon(R.drawable.ic_file_download_black_24dp)
                .setTicker(getString(R.string.export_service_exporting))
                .setContentTitle(getString(R.string.export_service_exporting))
                .setContentText(getString(R.string.export_service_file_created))
                .setAutoCancel(true)
                // So it can be dismissed by swiping
                .setOngoing(false)
                .setContentIntent(pendingIntent)
                .addAction(0, getString(R.string.main_open), pendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // PROGRESS_NOTIF_ID debe ser un entero único para cada notificación.
        notificationManager.notify(RESULT_NOTIF_ID, builder.build());
    }

    private Notification buildForegroundNotification() {
        builder = new NotificationCompat.Builder(this, CHANNEL_ID).setOngoing(true)
                .setSmallIcon(R.drawable.ic_file_download_black_24dp)
                .setTicker(getString(R.string.export_service_exporting))
                .setContentTitle(getString(R.string.export_service_exporting))
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setContentText(getString(R.string.export_service_progress, 0, 10))
                .setProgress(10, 0, false);
        return builder.build();
    }

    private void writeListToFile(File outputFile, List<String> items) throws FileNotFoundException, InterruptedException {
        try (PrintWriter printWriter = new PrintWriter(outputFile)) {
            for (int i = 0, max = items.size(); i < items.size(); i++) {
                Thread.sleep(100);
                updateForegroundNotification(i + 1, max);
                printWriter.println(items.get(i));
            }
        }
    }

    private void updateForegroundNotification(int current, int max) {
        builder.setProgress(max, current, false).setContentText(
                getString(R.string.export_service_progress, current, max));
        startForeground(PROGRESS_NOTIF_ID, builder.build());
    }

    private File createFile() {
        final String baseName = "students";
        String externalStorageState = Environment.getExternalStorageState();
        File rootDir;
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            rootDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS);
        } else {
            rootDir = getFilesDir();
        }
        //noinspection ResultOfMethodCallIgnored
        rootDir.mkdirs();
        SimpleDateFormat simpleDataFormat = new SimpleDateFormat("yyyyMMddHHmm",
                Locale.getDefault());
        String filename = baseName + simpleDataFormat.format(new Date()) + ".txt";
        return new File(rootDir, filename);
    }

    public static void start(Context context, List<String> data) {
        Intent intent = new Intent(context, ExportToTextFileService.class);
        intent.putExtra(EXTRA_DATA, new ArrayList<>(data));
        ContextCompat.startForegroundService(context, intent);
    }

}
