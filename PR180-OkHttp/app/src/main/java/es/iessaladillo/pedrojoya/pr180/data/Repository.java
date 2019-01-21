package es.iessaladillo.pedrojoya.pr180.data;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr180.base.Resource;

public interface Repository {

    LiveData<Resource<String>> search(String text, String tag);
    LiveData<Resource<String>> requestEcho(String text, String tag);
    LiveData<Resource<Bitmap>> loadPhoto(String photoUrl, String tag);
    void cancelSearchRequest(String tag);
    void cancelEchoRequest(String tag);
    void cancelPhotoRequest(String tag);

}
