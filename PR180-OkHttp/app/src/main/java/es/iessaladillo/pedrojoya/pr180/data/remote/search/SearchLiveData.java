package es.iessaladillo.pedrojoya.pr180.data.remote.search;

import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import es.iessaladillo.pedrojoya.pr180.base.Event;
import es.iessaladillo.pedrojoya.pr180.base.RequestState;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SearchLiveData extends MutableLiveData<RequestState> {

    private final OkHttpClient okHttpClient;
    private Call searchCall;

    public SearchLiveData(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public void search(String text) {
        try {
            postValue(new RequestState.Loading(true));
            URL url = new URL(
                    "https://www.google.es/search?hl=es&q=\"" + URLEncoder.encode(text, "UTF-8")
                            + "\"");
            Request request = new Request.Builder().url(url).header("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5" + ".1)").build();
            searchCall = okHttpClient.newCall(request);
            searchCall.enqueue(new Callback() {
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
                            String content = responseBody.string();
                            postValue(new RequestState.Result<>(
                                    new Event<>(extractResultFromContent(content))));
                        }
                    } else {
                        postValue(new RequestState.Error(
                                new Event<>(new Exception(response.message()))));
                    }
                }
            });
        } catch (Exception e)

        {
            postValue(new RequestState.Error(new Event<>(e)));
        }

    }

    private String extractResultFromContent(String contenido) {
        String resultado = "";
        int ini = contenido.indexOf("Aproximadamente");
        if (ini != -1) {
            // Se busca el siguiente espacio en blanco después de
            // Aproximadamente.
            int fin = contenido.indexOf(" ", ini + 16);
            // El resultado corresponde a lo que sigue a
            // Aproximadamente, hasta el siguiente espacio en blanco.
            resultado = contenido.substring(ini + 16, fin);
        }
        return resultado;
    }

    public void cancel() {
        if (searchCall != null) {
            searchCall.cancel();
            searchCall = null;
        }
    }

}