package es.iessaladillo.pedrojoya.pr249.ui.list;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr249.data.Repository;

class ListFragmentViewModel extends ViewModel {

    private final LiveData<List<String>> students;

    ListFragmentViewModel(@NonNull Repository repository) {
        students = repository.queryStudents();
    }

    LiveData<List<String>> getStudents() {
        return students;
    }

}
