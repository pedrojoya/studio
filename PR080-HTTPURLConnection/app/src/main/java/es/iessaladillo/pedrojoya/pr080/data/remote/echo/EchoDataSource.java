package es.iessaladillo.pedrojoya.pr080.data.remote.echo;

import es.iessaladillo.pedrojoya.pr080.base.Call;
import es.iessaladillo.pedrojoya.pr080.base.Event;
import es.iessaladillo.pedrojoya.pr080.base.Resource;

public interface EchoDataSource {

    Call<Resource<Event<String>>> requestEcho(String text);

}
