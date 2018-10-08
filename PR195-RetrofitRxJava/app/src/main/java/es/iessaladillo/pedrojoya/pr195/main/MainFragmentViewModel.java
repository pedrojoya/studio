package es.iessaladillo.pedrojoya.pr195.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr195.base.RequestState;
import es.iessaladillo.pedrojoya.pr195.data.Repository;
import es.iessaladillo.pedrojoya.pr195.data.model.Student;

class MainFragmentViewModel extends ViewModel {

    private LiveData<RequestState<List<Student>>> studentsRequest;
    private final Repository repository;

    MainFragmentViewModel(Repository repository) {
        this.repository = repository;
    }

    LiveData<RequestState<List<Student>>> getStudents() {
        if (studentsRequest == null) {
            refreshStudents();
        }
        return studentsRequest;
    }

    void refreshStudents() {
        studentsRequest = repository.getStudents();
    }

    @Override
    protected void onCleared() {
        repository.cancel();
        super.onCleared();
    }

}
