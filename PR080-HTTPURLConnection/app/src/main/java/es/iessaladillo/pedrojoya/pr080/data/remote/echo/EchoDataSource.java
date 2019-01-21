package es.iessaladillo.pedrojoya.pr080.data.remote.echo;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr080.base.Event;
import es.iessaladillo.pedrojoya.pr080.base.Resource;

public interface EchoDataSource {

    LiveData<Resource<Event<String>>> requestEcho(String text);

}
