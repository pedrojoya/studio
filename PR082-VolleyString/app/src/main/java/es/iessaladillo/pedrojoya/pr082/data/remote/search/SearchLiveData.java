package es.iessaladillo.pedrojoya.pr082.data.remote.search;

import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.android.volley.RequestQueue;

import java.net.URLEncoder;

import es.iessaladillo.pedrojoya.pr082.base.Event;
import es.iessaladillo.pedrojoya.pr082.base.RequestState;

public class SearchLiveData extends MutableLiveData<RequestState> {

    private final RequestQueue requestQueue;

    public SearchLiveData(@NonNull RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void search(String text) {
        try {
            postValue(new RequestState.Loading(true));
            requestQueue.add(new SearchRequest(URLEncoder.encode(text, "UTF-8"),
                    response -> {
                        postValue(new RequestState.Result<>(
                            new Event<>(extractResultFromContent(response))));
                        // Simulate delay
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    },
                    volleyError -> postValue(new RequestState.Error(
                            new Event<>(new Exception(volleyError.getMessage()))))
            ));
        } catch (Exception e) {
            postValue(new RequestState.Error(new Event<>(e)));
        }
    }

    public void cancel() {
        requestQueue.cancelAll(SearchRequest.SEARCH_TAG);
    }

    private String extractResultFromContent(String content) {
        String result = "";
        int ini = content.indexOf("Aproximadamente");
        if (ini != -1) {
            int end = content.indexOf(" ", ini + 16);
            result = content.substring(ini + 16, end);
        }
        return result;
    }

}