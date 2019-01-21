package es.iessaladillo.pedrojoya.pr080.data.remote.echo;

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
import es.iessaladillo.pedrojoya.pr080.base.Resource;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpCall;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpCallback;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpClient;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpRequest;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpResponse;

public class EchoDataSourceImpl implements EchoDataSource {

    private static final String KEY_NAME = "nombre";
    private static final String KEY_DATE = "fecha";
    private static final int TIMEOUT = 5000;

    private final HttpClient httpClient;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
        "dd/MM/yyyy " + "HH:mm:ss", Locale.getDefault());

    public EchoDataSourceImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public LiveData<Resource<String>> requestEcho(String text, String tag) {
        MutableLiveData<Resource<String>> result = new MutableLiveData<>();
        result.postValue(Resource.loading());
        try {
            URL url = URI.create("http://www.informaticasaladillo.es/echo.php").toURL();
            LinkedHashMap<String, String> data = new LinkedHashMap<>();
            data.put(KEY_NAME, text);
            data.put(KEY_DATE, simpleDateFormat.format(new Date()));
            HttpRequest httpRequest = new HttpRequest.Builder(url).setMethod(
                HttpRequest.METHOD_POST)
                .setTimeout(TIMEOUT)
                .setFormUrlEncodedBody(data)
                .setTag(tag)
                .build();
            HttpCall echoHttpCall = httpClient.newCall(httpRequest);
            echoHttpCall.enqueue(new HttpCallback() {
                @Override
                public void onFailure(HttpCall httpCall, IOException exception) {
                    result.postValue(Resource.error(exception));
                }

                @Override
                public void onResponse(HttpCall httpCall, HttpResponse httpResponse) {
                    if (httpResponse.getCode() == HttpURLConnection.HTTP_OK) {
                        result.postValue(Resource.success(new String(httpResponse.getBody(), StandardCharsets.UTF_8).trim()));
                    } else {
                        result.postValue(Resource.error(new Exception(httpResponse.getMessage())));
                    }
                }
            });
        } catch (IOException e) {
            result.postValue(Resource.error(e));
        }
        return result;
    }

    @Override
    public void cancel(String tag) {
        httpClient.cancel(tag);
    }

}
