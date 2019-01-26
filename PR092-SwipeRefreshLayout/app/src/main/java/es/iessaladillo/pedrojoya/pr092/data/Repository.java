package es.iessaladillo.pedrojoya.pr092.data;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    @NonNull
    LiveData<List<String>> queryLogs();

}
