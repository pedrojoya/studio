package es.iessaladillo.pedrojoya.pr169.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import es.iessaladillo.pedrojoya.pr169.base.RequestState;
import es.iessaladillo.pedrojoya.pr169.data.remote.Api;
import es.iessaladillo.pedrojoya.pr169.data.remote.YandexLiveData;

class MainFragmentViewModel extends ViewModel {

    private final YandexLiveData yandexLiveData;

    public MainFragmentViewModel(Api api) {
        yandexLiveData = new YandexLiveData(api);
    }

    public LiveData<RequestState> getTranslation() {
        return yandexLiveData;
    }

    public void translateFromApi(String word) {
        yandexLiveData.translate(word);
    }

    @Override
    protected void onCleared() {
        yandexLiveData.cancel();
        super.onCleared();
    }

}
