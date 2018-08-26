package es.iessaladillo.pedrojoya.pr080.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import es.iessaladillo.pedrojoya.pr080.base.RequestState;
import es.iessaladillo.pedrojoya.pr080.data.remote.echo.EchoLiveData;
import es.iessaladillo.pedrojoya.pr080.data.remote.search.SearchLiveData;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private final SearchLiveData searchLiveData = new SearchLiveData();
    private final EchoLiveData echoLiveData = new EchoLiveData();

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
