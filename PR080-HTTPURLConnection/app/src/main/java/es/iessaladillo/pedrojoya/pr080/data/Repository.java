package es.iessaladillo.pedrojoya.pr080.data;

import es.iessaladillo.pedrojoya.pr080.base.Call;
import es.iessaladillo.pedrojoya.pr080.base.Event;
import es.iessaladillo.pedrojoya.pr080.base.Resource;

public interface Repository {

    Call<Resource<Event<String>>> search(String text);
    Call<Resource<Event<String>>> requestEcho(String text);

}
