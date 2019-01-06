package es.iessaladillo.pedrojoya.pr080.data;

import es.iessaladillo.pedrojoya.pr080.data.remote.echo.EchoDataSource;
import es.iessaladillo.pedrojoya.pr080.data.remote.echo.EchoRequest;
import es.iessaladillo.pedrojoya.pr080.data.remote.search.SearchDataSource;
import es.iessaladillo.pedrojoya.pr080.data.remote.search.SearchRequest;

public class RepositoryImpl implements Repository {

    private final SearchDataSource searchDataSource;
    private final EchoDataSource echoDataSource;

    public RepositoryImpl(SearchDataSource searchDataSource, EchoDataSource echoDataSource) {
        this.searchDataSource = searchDataSource;
        this.echoDataSource = echoDataSource;
    }

    @Override
    public SearchRequest search(String text) {
        return searchDataSource.search(text);
    }

    @Override
    public EchoRequest requestEcho(String text) {
        return echoDataSource.requestEcho(text);
    }

}
