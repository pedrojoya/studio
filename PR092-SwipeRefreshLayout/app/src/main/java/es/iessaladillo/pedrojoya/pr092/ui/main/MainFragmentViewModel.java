package es.iessaladillo.pedrojoya.pr092.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr092.base.RequestState;
import es.iessaladillo.pedrojoya.pr092.data.Repository;

@SuppressWarnings("WeakerAccess")
public class MainFragmentViewModel extends ViewModel {

    private final LiveData<List<String>> data;
    private final MutableLiveData<Boolean> refreshing = new MutableLiveData<>();
    private final LiveData<RequestState<List<String>>> refreshState;
    private final Repository repository;

    public MainFragmentViewModel(Repository repository) {
        this.repository = repository;
        data = repository.queryLogs();
        refreshState = Transformations.switchMap(refreshing, active -> repository.refreshLogs());
    }

    public void refresh() {
        refreshing.setValue(true);
    }

    public LiveData<List<String>> getData() {
        return data;
    }

    public LiveData<RequestState<List<String>>> getRefreshState() {
        return refreshState;
    }

    @SuppressWarnings("EmptyMethod")
    @Override
    protected void onCleared() {
        super.onCleared();
        repository.cancelRefresh();
    }


}
