package es.iessaladillo.pedrojoya.pr082.data.remote.photo;

import android.graphics.Bitmap;

import com.android.volley.RequestQueue;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr082.base.Resource;

public class PhotoDataSourceImpl implements PhotoDataSource {

    private final RequestQueue requestQueue;

    public PhotoDataSourceImpl(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    @Override
    public LiveData<Resource<Bitmap>> loadPhoto(String photoUrl, String tag) {
        MutableLiveData<Resource<Bitmap>> result = new MutableLiveData<>();
        try {
            result.postValue(Resource.loading());
            requestQueue.add(new PhotoRequest(photoUrl, tag,
                response -> result.postValue(Resource.success(response)),
                volleyError -> result.postValue(
                    Resource.error(new Exception(volleyError.getMessage())))));
        } catch (Exception e) {
            result.postValue(Resource.error(e));
        }
        return result;
    }

    @Override
    public void cancel(String tag) {
        requestQueue.cancelAll(tag);
    }

}
