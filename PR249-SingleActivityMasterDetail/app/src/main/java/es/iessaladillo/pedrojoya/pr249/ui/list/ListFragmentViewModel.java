package es.iessaladillo.pedrojoya.pr249.ui.list;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr249.base.Event;
import es.iessaladillo.pedrojoya.pr249.data.Repository;

class ListFragmentViewModel extends ViewModel {

    private final LiveData<List<String>> students;
    private final MutableLiveData<Event<String>> navigateToDetail = new MutableLiveData<>();

    ListFragmentViewModel(@NonNull Repository repository) {
        students = repository.queryStudents();
    }

    LiveData<List<String>> getStudents() {
        return students;
    }

    LiveData<Event<String>> getNavigateToDetail() {
        return navigateToDetail;
    }

    void onItemSelected(String item) {
        navigateToDetail.postValue(new Event<>(item));
    }

}
