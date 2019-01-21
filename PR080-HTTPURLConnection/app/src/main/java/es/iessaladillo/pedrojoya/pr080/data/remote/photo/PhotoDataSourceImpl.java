package es.iessaladillo.pedrojoya.pr080.data.remote.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr080.base.Resource;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpCall;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpCallback;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpClient;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpRequest;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpResponse;

public class PhotoDataSourceImpl implements PhotoDataSource {

    private final HttpClient httpClient;

    public PhotoDataSourceImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public LiveData<Resource<Bitmap>> loadPhoto(String photoUrl, String tag) {
        MutableLiveData<Resource<Bitmap>> result = new MutableLiveData<>();
        result.postValue(Resource.loading());
        try {
            URL url = URI.create(photoUrl).toURL();
            HttpRequest httpRequest = new HttpRequest.Builder(url).setTimeout(5000)
                .setTag(tag)
                .build();
            HttpCall photoHttpCall = httpClient.newCall(httpRequest);
            photoHttpCall.enqueue(new HttpCallback() {
                @Override
                public void onFailure(HttpCall httpCall, IOException exception) {
                    result.postValue(Resource.error(exception));
                }

                @Override
                public void onResponse(HttpCall httpCall, HttpResponse httpResponse) {
                    if (httpResponse.getCode() == HttpURLConnection.HTTP_OK) {
                        result.postValue(Resource.success(
                            BitmapFactory.decodeByteArray(httpResponse.getBody(), 0,
                                httpResponse.getBody().length)));
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
