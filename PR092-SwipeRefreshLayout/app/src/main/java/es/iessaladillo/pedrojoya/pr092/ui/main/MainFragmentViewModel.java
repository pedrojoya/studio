package es.iessaladillo.pedrojoya.pr092.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr092.data.Repository;

@SuppressWarnings("WeakerAccess")
public class MainFragmentViewModel extends ViewModel {

    private final MutableLiveData<Boolean> queryLogsTrigger = new MutableLiveData<>();
    private final LiveData<List<String>> logs;
    private final MediatorLiveData<Boolean> refreshing = new MediatorLiveData<>();

    public MainFragmentViewModel(Repository repository) {
        logs = Transformations.switchMap(queryLogsTrigger, query -> repository.queryLogs());
        refreshing.addSource(queryLogsTrigger, query -> refreshing.setValue(true));
        refreshing.addSource(logs, data -> refreshing.setValue(false));
        refresh();
    }

    public void refresh() {
        queryLogsTrigger.setValue(true);
    }

    public LiveData<List<String>> getLogs() {
        return logs;
    }

    public LiveData<Boolean> getRefreshing() {
        return refreshing;
    }

}
