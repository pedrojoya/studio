package es.iessaladillo.pedrojoya.pr242.services;

import android.app.IntentService;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.iessaladillo.pedrojoya.pr242.Constants;
import es.iessaladillo.pedrojoya.pr242.R;

@SuppressWarnings("WeakerAccess")
public class ExportToTextFileService extends IntentService {

    private static final String EXTRA_DATA = "EXTRA_DATA";
    public static final String EXTRA_FILENAME = "EXTRA_FILENAME";
    public static final String ACTION_EXPORTED = "es.iessaladillo.pedrojoya.pr242.ACTION_EXPORTED";
    private static final int NOTIFICATION_ID = 100;

    private NotificationCompat.Builder builder;

    public ExportToTextFileService() {
        super("ExportToTextFileService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(NOTIFICATION_ID, buildForegroundNotification());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            try {
                File outputFile = createFile();
                writeListToFile(outputFile, intent.getStringArrayListExtra(EXTRA_DATA));
                sendResult(outputFile);
            } catch (FileNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        stopForeground(true);
    }

    private Notification buildForegroundNotification() {
        builder = new NotificationCompat.Builder(this, Constants.CHANNEL_ID).setOngoing(true)
                .setSmallIcon(R.drawable.ic_file_download_black_24dp)
                .setTicker(getString(R.string.export_service_exporting))
                .setContentTitle(getString(R.string.export_service_exporting))
                .setContentText(getString(R.string.export_service_progress, 0, 10))
                .setProgress(10, 0, false);
        return builder.build();
    }

    private void sendResult(File outputFile) {
        Intent intent = new Intent(ACTION_EXPORTED);
        intent.putExtra(EXTRA_FILENAME, Uri.fromFile(outputFile));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void writeListToFile(File outputFile, List<String> items) throws FileNotFoundException, InterruptedException {
        PrintWriter printWriter = new PrintWriter(outputFile);
        for (int i = 0; i < items.size(); i++) {
            Thread.sleep(1000);
            updateForegroundNotification(i + 1, items.size());
            printWriter.println(items.get(i));
        }
        printWriter.close();
    }

    private void updateForegroundNotification(int current, int max) {
        builder.setProgress(max, current, false).setContentText(
                getString(R.string.export_service_progress, current, max));
        startForeground(NOTIFICATION_ID, builder.build());
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

    public static void start(Context context, ArrayList<String> data) {
        Intent intent = new Intent(context, ExportToTextFileService.class);
        intent.putExtra(EXTRA_DATA, data);
        ContextCompat.startForegroundService(context, intent);
    }

}
