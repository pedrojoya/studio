package es.iessaladillo.pedrojoya.pr211.ui.list;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr211.data.Repository;
import es.iessaladillo.pedrojoya.pr211.data.local.model.Student;

class ListFragmentViewModel extends ViewModel {

    private final Repository repository;
    private final LiveData<List<Student>> students;

    ListFragmentViewModel(Repository repository) {
        this.repository = repository;
        students = repository.queryStudents();
    }

    LiveData<List<Student>> getStudents() {
        return students;
    }

    void deleteStudent(Student student) {
        repository.deleteStudent(student);
    }

}
