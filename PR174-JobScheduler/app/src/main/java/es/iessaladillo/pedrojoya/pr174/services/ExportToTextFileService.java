package es.iessaladillo.pedrojoya.pr174.services;

import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PersistableBundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr174.BuildConfig;
import es.iessaladillo.pedrojoya.pr174.R;
import es.iessaladillo.pedrojoya.pr174.base.Resource;

@SuppressWarnings("WeakerAccess")
public class ExportToTextFileService extends JobService {

    public static final String CHANNEL_ID = "foreground_service";
    private static final String EXTRA_DATA = "EXTRA_DATA";
    private static final int RESULT_NOTIF_ID = 101;

    private static final MutableLiveData<Resource<Uri>> _result = new MutableLiveData<>();
    public static final LiveData<Resource<Uri>> result = _result;

    public static JobInfo newJobInfo(Context context, int jobId, List<String> students) {
        PersistableBundle extras = new PersistableBundle();
        extras.putStringArray(EXTRA_DATA, students.toArray(new String[0]));
        return new JobInfo.Builder(jobId, new ComponentName(context.getPackageName(),
            ExportToTextFileService.class.getName())).setMinimumLatency(1000).setPersisted(true).setExtras(extras).build();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        PersistableBundle extras = Objects.requireNonNull(params.getExtras());
        String[] data = Objects.requireNonNull(extras.getStringArray(EXTRA_DATA));
        _result.postValue(Resource.loading());
        File outputFile = createFile();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            try {
                Thread.sleep(5000);
                //noinspection ConstantConditions
                writeListToFile(outputFile,
                    Arrays.asList(data));
                showResultNotification(outputFile);
            } catch (FileNotFoundException | InterruptedException e) {
                _result.postValue(Resource.error(e));
            }
            _result.postValue(Resource.success(Uri.fromFile(outputFile)));
            jobFinished(params, false);
        });
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private void showResultNotification(File outputFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW).setDataAndType(
            FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider",
                outputFile), "text/plain").addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
            CHANNEL_ID).setOngoing(true)
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
            rootDir = getFilesDir();
        }
        //noinspection ResultOfMethodCallIgnored
        rootDir.mkdirs();
        SimpleDateFormat simpleDataFormat = new SimpleDateFormat("yyyyMMddHHmm",
            Locale.getDefault());
        String filename = baseName + simpleDataFormat.format(new Date()) + ".txt";
        return new File(rootDir, filename);
    }

}
