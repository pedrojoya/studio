package es.iessaladillo.pedrojoya.pr180.data.remote.search;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr180.base.Event;
import es.iessaladillo.pedrojoya.pr180.base.Resource;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SearchDataSourceImpl implements SearchDataSource {

    private final OkHttpClient okHttpClient;

    public SearchDataSourceImpl(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public LiveData<Resource<Event<String>>> search(String text) {
        MutableLiveData<Resource<Event<String>>> result = new MutableLiveData<>();
        result.postValue(Resource.loading());
        try {
            URL url = new URL(
                "https://www.google.es/search?hl=es&q=\"" + URLEncoder.encode(text,
                    "UTF-8") + "\"");
            Request request = new Request.Builder().url(url).header("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5" + ".1)").build();
            Call echoCall = okHttpClient.newCall(request);
            echoCall.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    result.postValue(Resource.error(e));
                }

                @Override
                public void onResponse(@NonNull Call call,
                    @NonNull Response response) throws IOException {
                    // Simulate latency
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            String content = responseBody.string().trim();
                            result.postValue(Resource.success(new Event<>(extractResultFromContent(content))));
                        }
                    } else {
                        result.postValue(
                            Resource.error(new Exception(response.message())));
                    }
                }
            });
        } catch (Exception e) {
            result.postValue(Resource.error(e));
        }
        return result;
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

}
