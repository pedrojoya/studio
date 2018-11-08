package pedrojoya.iessaladillo.es.pr201.ui.main;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import pedrojoya.iessaladillo.es.pr201.data.Repository;
import pedrojoya.iessaladillo.es.pr201.data.local.model.Student;

final class MainActivityViewModel extends ViewModel {

    @NonNull
    private final Repository repository;
    @NonNull
    private final MutableLiveData<Boolean> descLiveData = new MutableLiveData<>();
    @NonNull
    private final LiveData<List<Student>> studentsLiveData;
    @NonNull
    private final LiveData<Boolean> emptyListLiveData;

    MainActivityViewModel(@NonNull Repository respository) {
        this.repository = respository;
        studentsLiveData = Transformations.switchMap(descLiveData, repository::queryStudentsOrderedByName);
        emptyListLiveData = Transformations.map(studentsLiveData,
            students -> students == null || students.size() == 0);
        descLiveData.postValue(false);
    }

    void toggleOrder() {
        Boolean isDescendentBoxed = descLiveData.getValue();
        boolean isDescedent = isDescendentBoxed == null ? false : isDescendentBoxed;
        descLiveData.postValue(!isDescedent);
    }

    @NonNull
    LiveData<List<Student>> getStudents() {
        return studentsLiveData;
    }

    @NonNull
    LiveData<Boolean> isListEmpty() {
        return emptyListLiveData;
    }

    boolean isInDescendentOrder() {
        return descLiveData.getValue() != null && descLiveData.getValue();
    }

    void insertStudent(@NonNull Student student) {
        repository.insertStudent(student);
    }

    void deleteStudent(@NonNull Student student) {
        repository.deleteStudent(student);
    }

    void updateStudent(@NonNull Student student, @NonNull Student newStudent) {
        repository.updateStudent(student, newStudent);
    }

}
