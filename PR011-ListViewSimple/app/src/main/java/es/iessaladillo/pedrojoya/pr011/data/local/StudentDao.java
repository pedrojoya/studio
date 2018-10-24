package es.iessaladillo.pedrojoya.pr011.data.local;

import java.util.List;

import androidx.annotation.NonNull;

public interface StudentDao {

    List<String> queryStudents();

    int addStudent(@NonNull String student);

    int deleteStudent(@NonNull String student);

}
