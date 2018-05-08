package es.iessaladillo.pedrojoya.pr153.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.res.Resources;
import android.databinding.ObservableField;

import java.util.List;

import es.iessaladillo.pedrojoya.pr153.R;
import es.iessaladillo.pedrojoya.pr153.data.Repository;
import es.iessaladillo.pedrojoya.pr153.data.local.model.Student;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private final Repository repository;
    private final Resources resources;
    private LiveData<List<Student>> students;
    public final ObservableField<String> viewMessage = new ObservableField<>();

    public MainActivityViewModel(Repository respository, Resources resources) {
        this.repository = respository;
        this.resources = resources;
    }

    public LiveData<List<Student>> getStudents() {
        if (students == null) {
            students = repository.getStudents();
        }
        return students;
    }

    public void showStudent(Object item) {
        viewMessage.set(resources.getString(R.string.main_activity_student_clicked,
                ((Student) item).getName()));
    }

    public void addFakeStudent() {
        repository.insertStudent(repository.newFakeStudent());
    }

    public void deleteStudent(Object item) {
        if (repository.deleteStudent((Student) item) > 0) {
            viewMessage.set(resources.getString(R.string.main_activity_student_deleted,
                    ((Student) item).getName()));
        }
    }

}
