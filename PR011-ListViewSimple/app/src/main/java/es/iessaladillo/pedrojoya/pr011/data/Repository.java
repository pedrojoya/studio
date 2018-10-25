package es.iessaladillo.pedrojoya.pr011.data;

import java.util.List;

import androidx.annotation.NonNull;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    @NonNull
    List<String> queryStudents();
    int addStudent(@NonNull String student);
    int deleteStudent(@NonNull String student);

}
