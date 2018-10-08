package es.iessaladillo.pedrojoya.pr105.ui.main.option2.tab1;

import java.util.List;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr105.data.Repository;
import es.iessaladillo.pedrojoya.pr105.data.local.model.Student;

class Option2Tab1ViewModel extends ViewModel {

    private final Repository repository;
    private List<Student> students;

    Option2Tab1ViewModel(Repository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("SameParameterValue")
    List<Student> getStudents(boolean forceLoad) {
        if (students == null || forceLoad) {
            students = repository.queryStudents();
        }
        return students;
    }

}
