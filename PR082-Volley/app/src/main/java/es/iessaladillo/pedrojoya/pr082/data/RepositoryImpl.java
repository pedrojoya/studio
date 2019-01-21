package es.iessaladillo.pedrojoya.pr082.data;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr082.base.Resource;
import es.iessaladillo.pedrojoya.pr082.data.remote.echo.EchoDataSource;
import es.iessaladillo.pedrojoya.pr082.data.remote.photo.PhotoDataSource;
import es.iessaladillo.pedrojoya.pr082.data.remote.search.SearchDataSource;

public class RepositoryImpl implements Repository {

    private final SearchDataSource searchDataSource;
    private final EchoDataSource echoDataSource;
    private final PhotoDataSource photoDataSource;

    public RepositoryImpl(SearchDataSource searchDataSource,
        EchoDataSource echoDataSource,
        PhotoDataSource photoDataSource) {
        this.searchDataSource = searchDataSource;
        this.echoDataSource = echoDataSource;
        this.photoDataSource = photoDataSource;
    }

    @Override
    public LiveData<Resource<String>> search(String text, String tag) {
        return searchDataSource.search(text, tag);
    }

    @Override
    public LiveData<Resource<String>> requestEcho(String text, String tag) {
        return echoDataSource.requestEcho(text, tag);
    }

    @Override
    public LiveData<Resource<Bitmap>> loadPhoto(String photoUrl, String tag) {
        return photoDataSource.loadPhoto(photoUrl, tag);
    }

    @Override
    public void cancelSearchRequest(String tag) {
        searchDataSource.cancel(tag);
    }

    @Override
    public void cancelEchoRequest(String tag) {
        echoDataSource.cancel(tag);
    }

    @Override
    public void cancelPhotoRequest(String tag) {
        photoDataSource.cancel(tag);
    }

}
