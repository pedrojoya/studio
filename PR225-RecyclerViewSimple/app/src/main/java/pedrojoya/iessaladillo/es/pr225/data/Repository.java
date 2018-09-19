package pedrojoya.iessaladillo.es.pr225.data;

import java.util.List;

import pedrojoya.iessaladillo.es.pr225.data.local.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Student> queryStudents();

}
