package es.iessaladillo.pedrojoya.pr080.data.remote.search;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr080.base.Resource;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpCall;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpCallback;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpClient;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpRequest;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpResponse;

public class SearchDataSourceImpl implements SearchDataSource {

    private final HttpClient httpClient;

    public SearchDataSourceImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public LiveData<Resource<String>> search(String text, String tag) {
        MutableLiveData<Resource<String>> result = new MutableLiveData<>();
        result.postValue(Resource.loading());
        try {
            URL url = URI.create(
                "https://www.google.es/search?hl=es&q=" + URLEncoder.encode(text, "UTF-8")).toURL();
            HttpRequest httpRequest = new HttpRequest.Builder(url).setTimeout(5000)
                .addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)")
                .setTag(tag)
                .build();
            HttpCall searchHttpCall = httpClient.newCall(httpRequest);
            searchHttpCall.enqueue(new HttpCallback() {
                @Override
                public void onFailure(HttpCall httpCall, IOException exception) {
                    result.postValue(Resource.error(exception));
                }

                @Override
                public void onResponse(HttpCall httpCall, HttpResponse httpResponse) {
                    if (httpResponse.getCode() == HttpURLConnection.HTTP_OK) {
                        result.postValue(Resource.success(extractResultFromContent(
                            new String(httpResponse.getBody(), StandardCharsets.UTF_8))));
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

    private String extractResultFromContent(String content) {
        String result = "";
        int ini = content.indexOf("Aproximadamente");
        if (ini != -1) {
            // Next white space after Aproximadamente.
            int fin = content.indexOf(" ", ini + 16);
            // Result is what goes after Aproximadamente until the next white space.
            result = content.substring(ini + 16, fin);
        }
        return result;
    }

}
