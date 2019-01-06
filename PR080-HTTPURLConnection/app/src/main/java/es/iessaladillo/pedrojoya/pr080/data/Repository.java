package es.iessaladillo.pedrojoya.pr080.data;

import es.iessaladillo.pedrojoya.pr080.data.remote.echo.EchoRequest;
import es.iessaladillo.pedrojoya.pr080.data.remote.search.SearchRequest;

public interface Repository {

    SearchRequest search(String text);
    EchoRequest requestEcho(String text);

}
