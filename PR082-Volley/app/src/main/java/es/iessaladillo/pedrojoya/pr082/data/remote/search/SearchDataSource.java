package es.iessaladillo.pedrojoya.pr082.data.remote.search;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr082.base.Resource;

public interface SearchDataSource {

    LiveData<Resource<String>> search(String text, String tag);

    void cancel(String tag);
}
