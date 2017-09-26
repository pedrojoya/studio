package es.iessaladillo.pedrojoya.pr169.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import es.iessaladillo.pedrojoya.pr169.data.remote.YandexApi;
import es.iessaladillo.pedrojoya.pr169.data.remote.YandexLiveData;
import es.iessaladillo.pedrojoya.pr169.data.remote.YandexService;

class MainActivityViewModel extends AndroidViewModel {

    private final YandexApi yandexApi;

    public MainActivityViewModel(Application application) {
        super(application);
        yandexApi = YandexService.getInstance(application.getApplicationContext()).getApi();
    }

    public YandexLiveData getTranslationFromApi(String word) {
        return new YandexLiveData(yandexApi, word);
    }

}
