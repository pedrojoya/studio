package es.iessaladillo.pedrojoya.pr082.ui.main;

import com.android.volley.RequestQueue;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr082.base.RequestState;
import es.iessaladillo.pedrojoya.pr082.data.remote.echo.EchoLiveData;
import es.iessaladillo.pedrojoya.pr082.data.remote.search.SearchLiveData;

@SuppressWarnings("WeakerAccess")
public class MainFragmentViewModel extends ViewModel {

    private final SearchLiveData searchLiveData;
    private final EchoLiveData echoLiveData;

    public MainFragmentViewModel(@NonNull RequestQueue requestQueue) {
        searchLiveData = new SearchLiveData(requestQueue);
        echoLiveData = new EchoLiveData(requestQueue);
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
