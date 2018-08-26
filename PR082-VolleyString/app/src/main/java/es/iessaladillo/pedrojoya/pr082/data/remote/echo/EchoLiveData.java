package es.iessaladillo.pedrojoya.pr082.data.remote.echo;

import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.android.volley.RequestQueue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.iessaladillo.pedrojoya.pr082.base.Event;
import es.iessaladillo.pedrojoya.pr082.base.RequestState;

public class EchoLiveData extends MutableLiveData<RequestState> {

    private static final String KEY_NAME = "nombre";
    private static final String KEY_DATE = "fecha";

    private final RequestQueue requestQueue;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

    public EchoLiveData(@NonNull RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void requestEcho(String text) {
        try {
            postValue(new RequestState.Loading(true));
            Map<String, String> params = new HashMap<>();
            params.put(KEY_NAME, text);
            params.put(KEY_DATE, simpleDateFormat.format(new Date()));
            requestQueue.add(new EchoRequest(params,
                    response -> {
                        postValue(new RequestState.Result<>(
                            new Event<>(response.trim())));
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
        requestQueue.cancelAll(EchoRequest.ECHO_TAG);
    }

}