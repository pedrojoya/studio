package es.iessaladillo.pedrojoya.pr080.data.remote.search;

import es.iessaladillo.pedrojoya.pr080.base.Call;
import es.iessaladillo.pedrojoya.pr080.base.Event;
import es.iessaladillo.pedrojoya.pr080.base.Resource;

public class SearchDataSourceImpl implements SearchDataSource {

    public Call<Resource<Event<String>>> search(String text) {
        return new SearchRequest(text);
    }

}
