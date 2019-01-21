package es.iessaladillo.pedrojoya.pr082.data;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr082.base.Event;
import es.iessaladillo.pedrojoya.pr082.base.Resource;

public interface Repository {

    LiveData<Resource<Event<String>>> search(String text);
    LiveData<Resource<Event<String>>> requestEcho(String text);
    LiveData<Resource<Bitmap>> loadPhoto(String photoUrl);

}
