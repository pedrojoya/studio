package es.iessaladillo.pedrojoya.pr011.data;

import java.util.List;

import androidx.annotation.NonNull;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<String> queryStudents();
    void addStudent(@NonNull String student);
    void deleteStudent(@NonNull String student);

}
