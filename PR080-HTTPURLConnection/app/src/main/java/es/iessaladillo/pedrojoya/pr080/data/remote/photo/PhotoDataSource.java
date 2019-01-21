package es.iessaladillo.pedrojoya.pr080.data.remote.photo;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr080.base.Resource;

public interface PhotoDataSource {

    LiveData<Resource<Bitmap>> loadPhoto(String photoUrl);

}
