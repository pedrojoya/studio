package es.iessaladillo.pedrojoya.pr082.ui.main;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr082.base.Event;
import es.iessaladillo.pedrojoya.pr082.base.Resource;
import es.iessaladillo.pedrojoya.pr082.data.Repository;

class MainFragmentViewModel extends ViewModel {

    private static final String SEARCH_TAG =
        MainFragmentViewModel.class.getSimpleName() + "-Search";
    private static final String ECHO_TAG = MainFragmentViewModel.class.getSimpleName() + "-Echo";
    private static final String PHOTO_TAG = MainFragmentViewModel.class.getSimpleName() + "-Photo";

    private final MutableLiveData<String> searchTrigger = new MutableLiveData<>();
    private final MutableLiveData<String> echoTrigger = new MutableLiveData<>();
    private final MutableLiveData<String> loadPhotoTrigger = new MutableLiveData<>();
    private final LiveData<Resource<String>> searchResultLiveData;
    private final LiveData<Resource<String>> echoResultLiveData;
    private final LiveData<Resource<Bitmap>> photoLiveData;
    private final Repository repository;
    private final MediatorLiveData<Boolean> loading = new MediatorLiveData<>();
    private final MediatorLiveData<Event<String>> errorMessage = new MediatorLiveData<>();
    private final MediatorLiveData<Event<String>> sucessMessage = new MediatorLiveData<>();
    private final MediatorLiveData<Bitmap> photo = new MediatorLiveData<>();
    private boolean searchLoading;
    private boolean echoLoading;
    private boolean photoLoading;

    MainFragmentViewModel(Repository repository) {
        this.repository = repository;
        searchResultLiveData = Transformations.switchMap(searchTrigger, searchText -> {
            repository.cancelSearchRequest(SEARCH_TAG);
            return repository.search(searchText, SEARCH_TAG);
        });
        echoResultLiveData = Transformations.switchMap(echoTrigger, text -> {
            repository.cancelEchoRequest(ECHO_TAG);
            return repository.requestEcho(text, ECHO_TAG);
        });
        photoLiveData = Transformations.switchMap(loadPhotoTrigger, photoUrl -> {
            repository.cancelPhotoRequest(PHOTO_TAG);
            return repository.loadPhoto(photoUrl, PHOTO_TAG);
        });
        setupLoadingTransformation();
        setupErrorMessageTransformation();
        setupSuccessMessageTransformation();
        setupPhotoTransformation();
    }

    private void setupLoadingTransformation() {
        loading.addSource(searchResultLiveData, searchResource -> {
            searchLoading = searchResource.isLoading();
            loading.setValue(searchLoading || echoLoading || photoLoading);
        });
        loading.addSource(echoResultLiveData, echoResource -> {
            echoLoading = echoResource.isLoading();
            loading.setValue(searchLoading || echoLoading || photoLoading);
        });
        loading.addSource(photoLiveData, photoResource -> {
            photoLoading = photoResource.isLoading();
            loading.setValue(searchLoading || echoLoading || photoLoading);
        });
    }

    private void setupErrorMessageTransformation() {
        errorMessage.addSource(searchResultLiveData, searchResource -> {
            if (searchResource.hasError()) {
                errorMessage.setValue(new Event<>(searchResource.getException().getMessage()));
            }
        });
        errorMessage.addSource(echoResultLiveData, echoResource -> {
            if (echoResource.hasError()) {
                errorMessage.setValue(new Event<>(echoResource.getException().getMessage()));
            }
        });
        errorMessage.addSource(photoLiveData, photoResource -> {
            if (photoResource.hasError()) {
                errorMessage.setValue(new Event<>(photoResource.getException().getMessage()));
            }
        });
    }

    private void setupSuccessMessageTransformation() {
        sucessMessage.addSource(searchResultLiveData, searchResource -> {
            if (searchResource.hasSuccess()) {
                errorMessage.setValue(new Event<>(searchResource.getData()));
            }
        });
        sucessMessage.addSource(echoResultLiveData, echoResource -> {
            if (echoResource.hasSuccess()) {
                errorMessage.setValue(new Event<>(echoResource.getData()));
            }
        });
    }

    private void setupPhotoTransformation() {
        photo.addSource(photoLiveData, photoResource -> {
            if (photoResource.hasSuccess()) {
                photo.setValue(photoResource.getData());
            }
        });
    }

    LiveData<Boolean> loading() {
        return loading;
    }

    LiveData<Event<String>> errorMessage() {
        return errorMessage;
    }

    LiveData<Event<String>> successMessage() {
        return sucessMessage;
    }

    LiveData<Bitmap> photo() {
        return photo;
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

    @Override
    protected void onCleared() {
        super.onCleared();
        // Cancel all enqueued and running requests.
        repository.cancelSearchRequest(SEARCH_TAG);
        repository.cancelEchoRequest(ECHO_TAG);
        repository.cancelPhotoRequest(PHOTO_TAG);
    }

}
