package es.iessaladillo.pedrojoya.pr153.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.res.Resources;
import android.databinding.ObservableField;

import java.util.List;

import es.iessaladillo.pedrojoya.pr153.R;
import es.iessaladillo.pedrojoya.pr153.data.Repository;
import es.iessaladillo.pedrojoya.pr153.data.local.model.Student;

public class MainActivityViewModel extends ViewModel {

    private final Repository repository;
    private final Resources resources;
    private LiveData<List<Student>> students;
    public ObservableField<String> viewMessage;

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

    public void showStudent(Student item) {
        viewMessage.set(resources.getString(R.string.main_activity_student_clicked, item.getName()));
    }

    public void addFakeStudent() {
        repository.insertStudent(repository.newFakeStudent());
    }

    public void deleteStudent(Student item) {
        if (repository.deleteStudent(item) > 0) {
            viewMessage.set(resources.getString(R.string.main_activity_student_deleted, item.getName()));
        }
    }

}
