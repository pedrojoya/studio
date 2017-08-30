package es.iessaladillo.pedrojoya.pr011.data;

import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<String> getStudents();
    void addStudent(String student);
    void deleteStudent(int position);

}
