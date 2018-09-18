package es.iessaladillo.pedrojoya.pr011.data;

import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<String> queryStudents();
    void addStudent(String student);
    void deleteStudent(String student);

}
