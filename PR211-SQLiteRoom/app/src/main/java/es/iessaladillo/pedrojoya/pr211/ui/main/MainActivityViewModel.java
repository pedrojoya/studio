package es.iessaladillo.pedrojoya.pr211.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import es.iessaladillo.pedrojoya.pr211.data.Repository;
import es.iessaladillo.pedrojoya.pr211.data.model.Student;

class MainActivityViewModel extends ViewModel {

    private final Repository repository;
    private LiveData<List<Student>> students;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Student>> getStudents() {
        if (students == null) {
            students = repository.getStudents();
        }
        return students;
    }

}
