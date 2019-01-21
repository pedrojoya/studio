package es.iessaladillo.pedrojoya.pr082.data.remote.photo;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr082.base.Resource;

public interface PhotoDataSource {

    LiveData<Resource<Bitmap>> loadPhoto(String photoUrl, String tag);

    void cancel(String tag);
}
