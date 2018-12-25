package es.iessaladillo.pedrojoya.pr092.data;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr092.base.Event;
import es.iessaladillo.pedrojoya.pr092.base.RequestState;
import es.iessaladillo.pedrojoya.pr092.data.local.Database;

public class RepositoryImpl implements Repository {

    private final Database database;
    private LoadDataAsyncTask task;

    public RepositoryImpl(Database database) {
        this.database = database;
    }

    @NonNull
    @Override
    public LiveData<List<String>> queryLogs() {
        return database.queryLogs();
    }

    @NonNull
    @Override
    public LiveData<RequestState<List<String>>> refreshLogs() {
        MutableLiveData<RequestState<List<String>>> requestStateLiveData = new MutableLiveData<>();
        task = new LoadDataAsyncTask(requestStateLiveData);
        task.execute();
        return requestStateLiveData;
    }

    @Override
    public void cancelRefresh() {
        task.cancel(true);
    }


    @SuppressLint("StaticFieldLeak")
    class LoadDataAsyncTask extends AsyncTask<Void, Void, Void> {

        private final MutableLiveData<RequestState<List<String>>> requestState;
        private final long SIMULATION_SLEEP_MILI = 6000;
        private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss",
            Locale.getDefault());

        LoadDataAsyncTask(MutableLiveData<RequestState<List<String>>> requestState) {
            this.requestState = requestState;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            requestState.postValue(new RequestState.Loading<>());
            // Loading time simulation.
            try {
                Thread.sleep(SIMULATION_SLEEP_MILI);
            } catch (InterruptedException e) {
                requestState.postValue(new RequestState.Error<>(new Event<>(e)));
                return null;
            }
            String log = simpleDateFormat.format(new Date());
            database.insertLog(log);
            requestState.postValue(new RequestState.Result<>(new ArrayList<>(Collections.singletonList(log))));
            return null;
        }

    }

}
