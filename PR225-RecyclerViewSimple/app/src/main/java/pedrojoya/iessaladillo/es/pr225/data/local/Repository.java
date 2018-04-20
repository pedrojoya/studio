package pedrojoya.iessaladillo.es.pr225.data.local;

import java.util.List;

import pedrojoya.iessaladillo.es.pr225.data.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Student> getStudents();

}
