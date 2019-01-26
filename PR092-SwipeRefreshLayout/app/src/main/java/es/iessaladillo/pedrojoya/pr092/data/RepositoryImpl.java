package es.iessaladillo.pedrojoya.pr092.data;

import android.os.AsyncTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr092.data.local.Database;

public class RepositoryImpl implements Repository {

    private final long SIMULATION_SLEEP_MILI = 6000;

    private final Database database;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss",
        Locale.getDefault());


    public RepositoryImpl(Database database) {
        this.database = database;
    }

    @NonNull
    @Override
    public LiveData<List<String>> queryLogs() {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            try {
                Thread.sleep(SIMULATION_SLEEP_MILI);
                String log = simpleDateFormat.format(new Date());
                database.insertLog(log);
            } catch (InterruptedException ignored) { }
        });
        return database.queryLogs();
    }

}
