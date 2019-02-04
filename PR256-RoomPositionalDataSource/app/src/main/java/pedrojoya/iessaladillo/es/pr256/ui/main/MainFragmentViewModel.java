package pedrojoya.iessaladillo.es.pr256.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import pedrojoya.iessaladillo.es.pr256.data.Repository;
import pedrojoya.iessaladillo.es.pr256.data.local.model.Student;

class MainFragmentViewModel extends ViewModel {

    private final Repository repository;
    private final LiveData<PagedList<Student>> students;

    MainFragmentViewModel(Repository repository) {
        this.repository = repository;
        students = repository.queryAllStudents();
    }

    LiveData<PagedList<Student>> getStudents() {
        return students;
    }

    void insertStudent(Student student) {
        repository.insertStudent(student);
    }

    void deleteStudent(Student student) {
        repository.deleteStudent(student);
    }
}
