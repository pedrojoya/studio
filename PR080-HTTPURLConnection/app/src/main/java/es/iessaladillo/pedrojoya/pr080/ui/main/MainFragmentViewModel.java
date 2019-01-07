package es.iessaladillo.pedrojoya.pr080.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr080.base.Call;
import es.iessaladillo.pedrojoya.pr080.base.Event;
import es.iessaladillo.pedrojoya.pr080.base.Resource;
import es.iessaladillo.pedrojoya.pr080.data.Repository;

class MainFragmentViewModel extends ViewModel {

    private final MutableLiveData<String> searchTextLiveData = new MutableLiveData<>();
    private final LiveData<Resource<Event<String>>> searchResultLiveData;
    private final MutableLiveData<String> echoTextLiveData = new MutableLiveData<>();
    private final LiveData<Resource<Event<String>>> echoResultLiveData;

    private Call<Resource<Event<String>>> previousSearchTask;
    private Call<Resource<Event<String>>> previousEchoTask;

    MainFragmentViewModel(Repository repository) {
        searchResultLiveData =
            Transformations.switchMap(searchTextLiveData, searchText -> {
                cancelPreviousSearchTask();
                previousSearchTask = repository.search(searchText);
                return previousSearchTask;
            });
        echoResultLiveData =
            Transformations.switchMap(echoTextLiveData, echoText -> {
                cancelPreviousEchoTask();
                previousEchoTask = repository.requestEcho(echoText);
                return previousEchoTask;
            });
    }

    void search(String searchText) {
        searchTextLiveData.setValue(searchText);
    }

    void requestEcho(String text) {
        echoTextLiveData.setValue(text);
    }

    LiveData<Resource<Event<String>>> getSearchResultLiveData() {
        return searchResultLiveData;
    }

    LiveData<Resource<Event<String>>> getEchoResultLiveData() {
        return echoResultLiveData;
    }

    private void cancelPreviousSearchTask() {
        if (previousSearchTask != null) {
            previousSearchTask.cancel(true);
        }
    }

    private void cancelPreviousEchoTask() {
        if (previousEchoTask != null) {
            previousEchoTask.cancel(true);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        cancelPreviousSearchTask();
        cancelPreviousEchoTask();
    }

}
