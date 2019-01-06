package es.iessaladillo.pedrojoya.pr080.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr080.base.Event;
import es.iessaladillo.pedrojoya.pr080.base.Resource;
import es.iessaladillo.pedrojoya.pr080.data.Repository;

class MainFragmentViewModel extends ViewModel {

    private final MutableLiveData<String> searchTextLiveData = new MutableLiveData<>();
    private final LiveData<Resource<Event<String>>> searchResultLiveData;
    private final MutableLiveData<String> echoTextLiveData = new MutableLiveData<>();
    private final LiveData<Resource<Event<String>>> echoResultLiveData;

    MainFragmentViewModel(Repository repository) {
        searchResultLiveData =
            Transformations.switchMap(searchTextLiveData, repository::search);
        echoResultLiveData =
            Transformations.switchMap(echoTextLiveData, repository::requestEcho);
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

}
