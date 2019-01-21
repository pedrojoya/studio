package es.iessaladillo.pedrojoya.pr180.data.remote.search;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr180.base.Resource;

public interface SearchDataSource {

    LiveData<Resource<String>> search(String text, String tag);

    void cancel(String tag);
}
