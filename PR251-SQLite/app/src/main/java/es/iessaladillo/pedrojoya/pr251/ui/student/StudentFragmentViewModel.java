package es.iessaladillo.pedrojoya.pr251.ui.student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr251.data.Repository;
import es.iessaladillo.pedrojoya.pr251.data.local.model.Student;

class StudentFragmentViewModel extends ViewModel {

    private final Repository repository;
    private LiveData<Student> studentLiveData;

    StudentFragmentViewModel(Repository repository) {
        this.repository = repository;
    }

    LiveData<Student> getStudent(long studentId) {
        if (studentLiveData == null) {
            studentLiveData = repository.queryStudent(studentId);
        }
        return studentLiveData;
    }

    void insertStudent(Student student) {
        repository.insertStudent(student);
    }

    void updateStudent(Student student) {
        repository.updateStudent(student);
    }

}
