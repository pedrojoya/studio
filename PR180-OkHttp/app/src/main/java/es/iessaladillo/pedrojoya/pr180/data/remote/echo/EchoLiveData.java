package es.iessaladillo.pedrojoya.pr180.data.remote.echo;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.iessaladillo.pedrojoya.pr180.base.Event;
import es.iessaladillo.pedrojoya.pr180.base.RequestState;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class EchoLiveData extends MutableLiveData<RequestState> {

    private static final String KEY_NAME = "nombre";
    private static final String KEY_DATE = "fecha";

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "dd/MM/yyyy " + "HH:mm:ss", Locale.getDefault());
    private final OkHttpClient okHttpClient;
    private Call echoCall;

    public EchoLiveData(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public void requestEcho(String text) {
        try {
            postValue(new RequestState.Loading(true));
            URL url = new URL("http://www.informaticasaladillo.es/echo.php");
            RequestBody formBody = new FormBody.Builder().addEncoded(KEY_NAME, text).addEncoded(
                    KEY_DATE, simpleDateFormat.format(new Date())).build();
            Request request = new Request.Builder().url(url).post(formBody).build();
            echoCall = okHttpClient.newCall(request);
            echoCall.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    postValue(new RequestState.Error(new Event<>(e)));
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    // Simulate latency
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            String content = responseBody.string().trim();
                            postValue(new RequestState.Result<>(new Event<>(content)));
                        }
                    } else {
                        postValue(new RequestState.Error(new Event<>(new Exception(response.message()))));
                    }
                }
            });
        } catch (Exception e) {
            postValue(new RequestState.Error(new Event<>(e)));
        }
    }

    public void cancel() {
        if (echoCall != null) {
            echoCall.cancel();
            echoCall = null;
        }
    }

}