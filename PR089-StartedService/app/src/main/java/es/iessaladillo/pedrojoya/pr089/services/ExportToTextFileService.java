package es.iessaladillo.pedrojoya.pr089.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr089.base.Resource;

public class ExportToTextFileService extends Service {

    private static final String EXTRA_DATA = "EXTRA_DATA";

    private static final MutableLiveData<Resource<Uri>> _result = new MutableLiveData<>();
    public static final LiveData<Resource<Uri>> result = _result;

    @Override
    public IBinder onBind(Intent intent) {
        // It's a started service, not a bound service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(ExportToTextFileService.class.getSimpleName(), "Service created");
    }

    @Override
    public void onDestroy() {
        Log.d(ExportToTextFileService.class.getSimpleName(), "Service destroyed");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(ExportToTextFileService.class.getSimpleName(), "Intent received");
        onHandleIntent(intent);
        // Not sticky service.
        return START_NOT_STICKY;
    }

    private void onHandleIntent(Intent intent) {
        if (intent != null && intent.hasExtra(EXTRA_DATA)) {
            new Thread(() -> saveToFile(intent.getStringArrayListExtra(EXTRA_DATA))).start();
        }
    }

    private void saveToFile(ArrayList<String> students) {
        _result.postValue(Resource.loading());
        File outputFile = createFile();
        try {
            Thread.sleep(5000);
            writeListToFile(outputFile, students);
        } catch (FileNotFoundException | InterruptedException e) {
            _result.postValue(Resource.error(e));
        }
        _result.postValue(Resource.success(Uri.fromFile(outputFile)));
    }

    private void writeListToFile(File outputFile, List<String> items) throws FileNotFoundException {
        try (PrintWriter printWriter = new PrintWriter(outputFile)) {
            for (String item : items) {
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

    public static void start(Context context, List<String> data) {
        context.startService(new Intent(context, ExportToTextFileService.class).putExtra(EXTRA_DATA, new ArrayList<>(data)));
    }

}
