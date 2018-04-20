package es.iessaladillo.pedrojoya.pr153.ui.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import es.iessaladillo.pedrojoya.pr153.data.Repository;
import es.iessaladillo.pedrojoya.pr153.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr153.data.local.model.Student;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends AndroidViewModel {

    private final Repository repository;
    private LiveData<List<Student>> students;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = RepositoryImpl.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Student>> getStudents() {
        if (students == null) {
            students = repository.getStudents();
        }
        return students;
    }


    public void insertStudent(Student student) {
        repository.insertStudent(student);
    }


    public int deleteStudent(Student student) {
        return repository.deleteStudent(student);
    }

}
