package es.iessaladillo.pedrojoya.pr105.ui.main.option2.tab1;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr105.data.Repository;
import es.iessaladillo.pedrojoya.pr105.data.local.model.Student;

class Option2Tab1ViewModel extends ViewModel {

    private final LiveData<List<Student>> students;

    Option2Tab1ViewModel(Repository repository) {
        students = repository.queryStudents();
    }

    LiveData<List<Student>> getStudents() {
        return students;
    }

}
