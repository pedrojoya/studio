package es.iessaladillo.pedrojoya.pr011.data;

import java.util.List;

import androidx.annotation.NonNull;

public interface Repository {

    @NonNull
    List<String> queryStudents();

    @SuppressWarnings("UnusedReturnValue")
    int addStudent(@NonNull String student);

    @SuppressWarnings("UnusedReturnValue")
    int deleteStudent(@NonNull String student);

}
