package es.iessaladillo.pedrojoya.pr211.ui.student;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import es.iessaladillo.pedrojoya.pr211.data.Repository;
import es.iessaladillo.pedrojoya.pr211.data.model.Student;

class StudentActivityViewModel extends ViewModel {

    private final Repository repository;
    private LiveData<Student> student;

    public StudentActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<Student> getStudent(long studentId) {
        if (student == null) {
            student = repository.getStudent(studentId);
        }
        return student;
    }

}
