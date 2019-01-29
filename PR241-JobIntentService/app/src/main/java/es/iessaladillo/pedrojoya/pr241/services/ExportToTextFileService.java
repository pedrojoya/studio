package es.iessaladillo.pedrojoya.pr241.services;

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

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr241.base.Resource;

@SuppressWarnings("WeakerAccess")
public class ExportToTextFileService extends JobIntentService {

    private static final String EXTRA_DATA = "EXTRA_DATA";
    private static final int JOB_ID = 1000;

    private static final MutableLiveData<Resource<Uri>> _result = new MutableLiveData<>();
    public static final LiveData<Resource<Uri>> result = _result;

    public static void start(Context context, List<String> data) {
        Intent intent = new Intent(context, ExportToTextFileService.class);
        intent.putExtra(EXTRA_DATA, new ArrayList<>(data));
        enqueueWork(context, ExportToTextFileService.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        _result.postValue(Resource.loading());
        File outputFile = createFile();
        try {
            Thread.sleep(5000);
            writeListToFile(outputFile, intent.getStringArrayListExtra(EXTRA_DATA));
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

}
