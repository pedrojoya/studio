package pedrojoya.iessaladillo.es.pr226.ui.main;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import pedrojoya.iessaladillo.es.pr226.data.Repository;
import pedrojoya.iessaladillo.es.pr226.data.local.model.Student;

class MainActivityViewModel extends ViewModel {

    @NonNull
    private final Repository repository;
    private LiveData<List<Student>> students;
    @NonNull
    private final MutableLiveData<Boolean> sortEnabledLiveData = new MutableLiveData<>();

    MainActivityViewModel(@NonNull Repository repository) {
        this.repository = repository;
        sortEnabledLiveData.postValue(false);
    }

    LiveData<List<Student>> getStudents() {
        if (students == null) {
            students = repository.getStudents();
        }
        return students;
    }

    void insertStudent(@NonNull Student student) {
        repository.insertStudent(student);
    }

    void deleteStudent(@NonNull Student student) {
        repository.deleteStudent(student);
    }

    boolean isSortEnabled() {
        return sortEnabledLiveData.getValue() == null ? false : sortEnabledLiveData.getValue();
    }

    LiveData<Boolean> getSortEnabled() {
        return sortEnabledLiveData;
    }

    void toggleSort() {
        sortEnabledLiveData.postValue(
            !(sortEnabledLiveData.getValue() == null ? true : sortEnabledLiveData.getValue()));
    }

    void saveOrder() {
        toggleSort();
        if (students.getValue() != null) {
            repository.updateStudents(students.getValue());
        }
    }
}