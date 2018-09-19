package es.iessaladillo.pedrojoya.pr016.data.local;

import java.util.List;

import es.iessaladillo.pedrojoya.pr016.data.local.model.Level;
import es.iessaladillo.pedrojoya.pr016.data.local.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Level> queryLevels();
    List<Student> queryStudentsByLevel(long levelId);

}
