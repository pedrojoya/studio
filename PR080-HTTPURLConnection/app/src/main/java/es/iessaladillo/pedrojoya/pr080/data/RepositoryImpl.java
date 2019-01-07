package es.iessaladillo.pedrojoya.pr080.data;

import es.iessaladillo.pedrojoya.pr080.base.Call;
import es.iessaladillo.pedrojoya.pr080.base.Event;
import es.iessaladillo.pedrojoya.pr080.base.Resource;
import es.iessaladillo.pedrojoya.pr080.data.remote.echo.EchoDataSource;
import es.iessaladillo.pedrojoya.pr080.data.remote.search.SearchDataSource;

public class RepositoryImpl implements Repository {

    private final SearchDataSource searchDataSource;
    private final EchoDataSource echoDataSource;

    public RepositoryImpl(SearchDataSource searchDataSource, EchoDataSource echoDataSource) {
        this.searchDataSource = searchDataSource;
        this.echoDataSource = echoDataSource;
    }

    @Override
    public Call<Resource<Event<String>>> search(String text) {
        return searchDataSource.search(text);
    }

    @Override
    public Call<Resource<Event<String>>> requestEcho(String text) {
        return echoDataSource.requestEcho(text);
    }

}
