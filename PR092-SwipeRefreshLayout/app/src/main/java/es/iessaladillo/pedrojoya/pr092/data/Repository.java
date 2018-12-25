package es.iessaladillo.pedrojoya.pr092.data;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr092.base.RequestState;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    @NonNull
    LiveData<List<String>> queryLogs();

    @NonNull
    LiveData<RequestState<List<String>>> refreshLogs();

    void cancelRefresh();

}
