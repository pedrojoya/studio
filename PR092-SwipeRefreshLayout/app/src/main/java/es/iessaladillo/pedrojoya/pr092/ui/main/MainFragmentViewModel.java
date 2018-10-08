package es.iessaladillo.pedrojoya.pr092.ui.main;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

@SuppressWarnings("WeakerAccess")
public class MainFragmentViewModel extends ViewModel {

    private final ArrayList<String> _data = new ArrayList<>();
    private final MutableLiveData<List<String>> data = new MutableLiveData<>();
    private LoadDataAsyncTask task;

    public void refresh() {
        task = new LoadDataAsyncTask();
        task.execute();
    }

    public LiveData<List<String>> getData() {
        return data;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (task != null) {
            task.cancel(true);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {

        private final long SIMULATION_SLEEP_MILI = 2000;
        private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss",
                Locale.getDefault());

        @Override
        protected String doInBackground(Void... voids) {
            // Loading time simulation.
            try {
                Thread.sleep(SIMULATION_SLEEP_MILI);
            } catch (InterruptedException e) {
                return null;
            }
            return simpleDateFormat.format(new Date());
        }

        @Override
        protected void onPostExecute(String result) {
            _data.add(result);
            data.setValue(new ArrayList<>(_data));
        }

    }

}
