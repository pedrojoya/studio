package es.iessaladillo.pedrojoya.pr180.data;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr180.base.Event;
import es.iessaladillo.pedrojoya.pr180.base.Resource;
import es.iessaladillo.pedrojoya.pr180.data.remote.echo.EchoDataSource;
import es.iessaladillo.pedrojoya.pr180.data.remote.photo.PhotoDataSource;
import es.iessaladillo.pedrojoya.pr180.data.remote.search.SearchDataSource;

public class RepositoryImpl implements Repository {

    private final SearchDataSource searchDataSource;
    private final EchoDataSource echoDataSource;
    private final PhotoDataSource photoDataSource;

    public RepositoryImpl(SearchDataSource searchDataSource, EchoDataSource echoDataSource,
        PhotoDataSource photoDataSource) {
        this.searchDataSource = searchDataSource;
        this.echoDataSource = echoDataSource;
        this.photoDataSource = photoDataSource;
    }

    @Override
    public LiveData<Resource<Event<String>>> search(String text) {
        return searchDataSource.search(text);
    }

    @Override
    public LiveData<Resource<Event<String>>> requestEcho(String text) {
        return echoDataSource.requestEcho(text);
    }

    @Override
    public LiveData<Resource<Bitmap>> loadPhoto(String photoUrl) {
        return photoDataSource.loadPhoto(photoUrl);
    }

}
