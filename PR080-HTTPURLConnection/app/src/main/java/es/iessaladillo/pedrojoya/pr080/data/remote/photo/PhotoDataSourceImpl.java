package es.iessaladillo.pedrojoya.pr080.data.remote.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr080.base.Resource;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpClient;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpRequest;
import es.iessaladillo.pedrojoya.pr080.base.http.HttpResponse;

public class PhotoDataSourceImpl implements PhotoDataSource {

    @Override
    public LiveData<Resource<Bitmap>> loadPhoto(String photoUrl) {
        MutableLiveData<Resource<Bitmap>> result = new MutableLiveData<>();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            result.postValue(Resource.loading());
            try {
                URL url = URI.create(photoUrl).toURL();
                HttpClient httpClient = new HttpClient();
                HttpRequest httpRequest =
                    new HttpRequest.Builder(url)
                        .setTimeout(5000)
                        .build();
                httpClient.enqueue(httpRequest, new HttpResponse.Callback() {
                    @Override
                    public void onFailure(HttpRequest httpRequest, IOException exception) {
                        result.postValue(Resource.error(exception));
                    }

                    @Override
                    public void onResponse(HttpRequest httpRequest, HttpResponse httpResponse) {
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
        });
        return result;
    }

}
