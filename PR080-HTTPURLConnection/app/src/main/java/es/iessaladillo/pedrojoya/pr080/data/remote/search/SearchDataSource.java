package es.iessaladillo.pedrojoya.pr080.data.remote.search;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr080.base.Event;
import es.iessaladillo.pedrojoya.pr080.base.Resource;

public interface SearchDataSource {

    LiveData<Resource<Event<String>>> search(String text);

}
