package es.iessaladillo.pedrojoya.pr057.data;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr057.data.local.model.Student;

public interface Repository {

    @NonNull
    LiveData<List<Student>> queryStudents();
    void insertStudent(@NonNull Student student);
    void deleteStudent(@NonNull Student student);

}
