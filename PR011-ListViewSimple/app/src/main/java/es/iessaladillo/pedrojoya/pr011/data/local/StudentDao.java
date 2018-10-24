package es.iessaladillo.pedrojoya.pr011.data.local;

import java.util.List;

import androidx.annotation.NonNull;

public interface StudentDao {

    List<String> queryStudents();

    void addStudent(@NonNull String student);

    void deleteStudent(@NonNull String student);

}
