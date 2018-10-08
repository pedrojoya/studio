package es.iessaladillo.pedrojoya.pr180.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import es.iessaladillo.pedrojoya.pr180.base.RequestState;
import es.iessaladillo.pedrojoya.pr180.data.remote.echo.EchoLiveData;
import es.iessaladillo.pedrojoya.pr180.data.remote.search.SearchLiveData;
import okhttp3.OkHttpClient;

@SuppressWarnings("WeakerAccess")
public class MainFragmenViewModel extends ViewModel {

    private final SearchLiveData searchLiveData;

    private final EchoLiveData echoLiveData;

    public MainFragmenViewModel(OkHttpClient okHttpClient) {
        searchLiveData = new SearchLiveData(okHttpClient);
        echoLiveData = new EchoLiveData(okHttpClient);
    }

    public LiveData<RequestState> getSearchLiveData() {
        return searchLiveData;
    }

    public LiveData<RequestState> getEchoLiveData() {
        return echoLiveData;
    }

    public void search(String text) {
        searchLiveData.search(text);
    }

    public void requestEcho(String text) {
        echoLiveData.requestEcho(text);
    }

    @Override
    protected void onCleared() {
        searchLiveData.cancel();
        echoLiveData.cancel();
        super.onCleared();
    }

}
