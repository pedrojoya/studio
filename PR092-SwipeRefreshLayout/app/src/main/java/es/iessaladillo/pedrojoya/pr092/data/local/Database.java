package es.iessaladillo.pedrojoya.pr092.data.local;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

@SuppressWarnings("unused")
public class Database {

    private static volatile Database instance;
    @NonNull
    private final List<String> logs = new ArrayList<>();
    @NonNull
    private final MutableLiveData<List<String>> logsLiveData = new MutableLiveData<>();

    private Database() {
        updateLiveDatas();
    }

    private void updateLiveDatas() {
        logsLiveData.postValue(new ArrayList<>(logs));
    }

    @NonNull
    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    @NonNull
    public LiveData<List<String>> queryLogs() {
        return logsLiveData;
    }

    public synchronized void insertLog(@NonNull String log) {
        logs.add(log);
        updateLiveDatas();
    }

}
