package es.iessaladillo.pedrojoya.pr012.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr012.data.local.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Student> getStudents();

}
