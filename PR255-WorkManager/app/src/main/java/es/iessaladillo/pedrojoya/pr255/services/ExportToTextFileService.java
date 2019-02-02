package es.iessaladillo.pedrojoya.pr255.services;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import es.iessaladillo.pedrojoya.pr255.BuildConfig;
import es.iessaladillo.pedrojoya.pr255.R;

@SuppressWarnings("WeakerAccess")
public class ExportToTextFileService extends Worker {

    public static final String CHANNEL_ID = "foreground_service";
    private static final String EXTRA_INPUT_STRING_ARRAY = "EXTRA_INPUT_STRING_ARRAY";
    public static final String EXTRA_RESULT_URI_STRING = "EXTRA_RESULT_URI_STRING";
    private static final int RESULT_NOTIF_ID = 101;

    public ExportToTextFileService(@NonNull Context context,
        @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public static OneTimeWorkRequest.Builder newOneTimeWorkRequestBuilder(List<String> students) {
        Data inputData = new Data.Builder()
            .putStringArray(EXTRA_INPUT_STRING_ARRAY, students.toArray(new String[0]))
            .build();
        return new OneTimeWorkRequest.Builder(ExportToTextFileService.class)
            .setInputData(inputData);
    }

    private void showResultNotification(File outputFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW).setDataAndType(
            FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".fileprovider",
                outputFile), "text/plain").addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),
            CHANNEL_ID).setOngoing(true)
            .setSmallIcon(R.drawable.ic_file_download_black_24dp)
            .setTicker(getApplicationContext().getString(R.string.export_service_exporting))
            .setContentTitle(getApplicationContext().getString(R.string.export_service_exporting))
            .setContentText(getApplicationContext().getString(R.string.export_service_file_created))
            .setAutoCancel(true)
            // So it can be dismissed by swiping
            .setOngoing(false)
            .setContentIntent(pendingIntent)
            .addAction(0, getApplicationContext().getString(R.string.main_open), pendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        // PROGRESS_NOTIF_ID debe ser un entero único para cada notificación.
        notificationManager.notify(RESULT_NOTIF_ID, builder.build());
    }

    private void writeListToFile(File outputFile,
        List<String> items) throws FileNotFoundException, InterruptedException {
        try (PrintWriter printWriter = new PrintWriter(outputFile)) {
            for (String item : items) {
                Thread.sleep(100);
                printWriter.println(item);
            }
        }
    }

    private File createFile() {
        final String baseName = "students";
        String externalStorageState = Environment.getExternalStorageState();
        File rootDir;
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            rootDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);
        } else {
            rootDir = getApplicationContext().getFilesDir();
        }
        //noinspection ResultOfMethodCallIgnored
        rootDir.mkdirs();
        SimpleDateFormat simpleDataFormat = new SimpleDateFormat("yyyyMMddHHmm",
            Locale.getDefault());
        String filename = baseName + simpleDataFormat.format(new Date()) + ".txt";
        return new File(rootDir, filename);
    }

    @NonNull
    @Override
    public Result doWork() {
        Objects.requireNonNull(getInputData());
        Objects.requireNonNull(getInputData().getStringArray(EXTRA_INPUT_STRING_ARRAY));
        File outputFile = createFile();
        try {
            Thread.sleep(5000);
            //noinspection ConstantConditions
            writeListToFile(outputFile, Arrays.asList(getInputData().getStringArray(
                EXTRA_INPUT_STRING_ARRAY)));
            showResultNotification(outputFile);
        } catch (FileNotFoundException | InterruptedException e) {
            return Result.failure(new Data.Builder().putString(EXTRA_RESULT_URI_STRING,
                e.getMessage()).build());
        }
        Data output = new Data.Builder().putString(EXTRA_RESULT_URI_STRING,
            Uri.fromFile(outputFile).toString()).build();
        return Result.success(output);
    }

}
