package es.iessaladillo.pedrojoya.pr180.main;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr180.base.Event;
import es.iessaladillo.pedrojoya.pr180.base.Resource;
import es.iessaladillo.pedrojoya.pr180.data.Repository;

class MainFragmentViewModel extends ViewModel {

    private final MutableLiveData<String> searchTrigger = new MutableLiveData<>();
    private final MutableLiveData<String> echoTrigger = new MutableLiveData<>();
    private final MutableLiveData<String> loadPhotoTrigger = new MutableLiveData<>();
    private final LiveData<Resource<Event<String>>> searchResultLiveData;
    private final LiveData<Resource<Event<String>>> echoResultLiveData;
    private final LiveData<Resource<Bitmap>> photoLiveData;

    MainFragmentViewModel(Repository repository) {
        searchResultLiveData = Transformations.switchMap(searchTrigger, repository::search);
        echoResultLiveData = Transformations.switchMap(echoTrigger, repository::requestEcho);
        photoLiveData = Transformations.switchMap(loadPhotoTrigger, repository::loadPhoto);
    }

    void search(String searchText) {
        searchTrigger.setValue(searchText);
    }

    void requestEcho(String text) {
        echoTrigger.setValue(text);
    }

    void loadPhoto(String photoUrl) {
        loadPhotoTrigger.setValue(photoUrl);
    }

    LiveData<Resource<Event<String>>> getSearchResultLiveData() {
        return searchResultLiveData;
    }

    LiveData<Resource<Event<String>>> getEchoResultLiveData() {
        return echoResultLiveData;
    }

    LiveData<Resource<Bitmap>> getPhotoLiveData() {
        return photoLiveData;
    }

}
