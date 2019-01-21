package es.iessaladillo.pedrojoya.pr080.data.remote.echo;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr080.base.Event;
import es.iessaladillo.pedrojoya.pr080.base.Resource;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpClient;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpRequest;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpResponse;

public class EchoDataSourceImpl implements EchoDataSource {

    private static final String KEY_NAME = "nombre";
    private static final String KEY_DATE = "fecha";

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
        "dd/MM/yyyy " + "HH:mm:ss", Locale.getDefault());

    @Override
    public LiveData<Resource<Event<String>>> requestEcho(String text) {
        MutableLiveData<Resource<Event<String>>> result = new MutableLiveData<>();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            result.postValue(Resource.loading());
            try {
                URL url = URI.create("http://www.informaticasaladillo.es/echo.php").toURL();
                LinkedHashMap<String, String> data = new LinkedHashMap<>();
                data.put(KEY_NAME, text);
                data.put(KEY_DATE, simpleDateFormat.format(new Date()));
                HttpClient httpClient = new HttpClient();
                HttpRequest httpRequest =
                    new HttpRequest.Builder(url)
                        .setMethod(HttpRequest.METHOD_POST)
                        .setTimeout(5000)
                        .setFormUrlEncodedBody(data)
                        .build();
                httpClient.enqueue(httpRequest, new HttpResponse.Callback() {
                    @Override
                    public void onFailure(HttpRequest httpRequest, IOException exception) {
                        result.postValue(Resource.error(exception));
                    }

                    @Override
                    public void onResponse(HttpRequest httpRequest, HttpResponse httpResponse) {
                        if (httpResponse.getCode() == HttpURLConnection.HTTP_OK) {
                            result.postValue(Resource.success(new Event<>(new String(httpResponse.getBody(), StandardCharsets.UTF_8).trim())));
                        } else {
                            result.postValue(Resource.error(new Exception(httpResponse.getMessage())));
                        }
                    }
                });
            } catch (IOException e) {
                result.postValue(Resource.error(e));
            }
        });
        return result;
    }

}
