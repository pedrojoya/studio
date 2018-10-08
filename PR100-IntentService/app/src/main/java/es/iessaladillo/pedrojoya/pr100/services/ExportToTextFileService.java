package es.iessaladillo.pedrojoya.pr100.services;

import android.app.IntentService;
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
import java.util.concurrent.TimeUnit;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import es.iessaladillo.pedrojoya.pr100.BuildConfig;

public class ExportToTextFileService extends IntentService {

    private static final String EXTRA_DATA = "EXTRA_DATA";
    public static final String EXTRA_FILENAME = "EXTRA_FILENAME";
    public static final String ACTION_EXPORTED = BuildConfig.APPLICATION_ID + ".ACTION_EXPORTED";
    private static final String SERVICE_NAME = "export";

    public ExportToTextFileService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            TimeUnit.SECONDS.sleep(5);
            File outputFile = createFile();
            writeListToFile(outputFile, intent.getStringArrayListExtra(EXTRA_DATA));
            sendResult(outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendResult(File outputFile) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(
                new Intent(ACTION_EXPORTED).putExtra(EXTRA_FILENAME, Uri.fromFile(outputFile)));
    }

    private void writeListToFile(File outputFile, List<String> items) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(outputFile);
        for (String item : items) {
            printWriter.println(item);
        }
        printWriter.close();
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
        context.startService(new Intent(context, ExportToTextFileService.class).putExtra(EXTRA_DATA, data));
    }

}
