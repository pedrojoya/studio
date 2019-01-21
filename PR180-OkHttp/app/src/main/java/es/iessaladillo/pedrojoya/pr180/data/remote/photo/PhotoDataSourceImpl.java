package es.iessaladillo.pedrojoya.pr180.data.remote.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr180.base.Resource;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PhotoDataSourceImpl implements PhotoDataSource {

    private final OkHttpClient okHttpClient;

    public PhotoDataSourceImpl(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    public LiveData<Resource<Bitmap>> loadPhoto(String photoUrl) {
        MutableLiveData<Resource<Bitmap>> result = new MutableLiveData<>();
        result.postValue(Resource.loading());
        try {
            URL url = URI.create(photoUrl).toURL();
            Request request = new Request.Builder().url(url).build();
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
                            byte[] content = responseBody.bytes();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(content, 0,
                                content.length);
                            result.postValue(Resource.success(bitmap));
                        }
                    } else {
                        result.postValue(Resource.error(new Exception(response.message())));
                    }
                }
            });
        } catch (IOException e) {
            result.postValue(Resource.error(e));
        }
        return result;
    }

}
