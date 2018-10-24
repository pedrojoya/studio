package es.iessaladillo.pedrojoya.pr016.data;

import java.util.List;

import androidx.annotation.NonNull;
import es.iessaladillo.pedrojoya.pr016.data.local.model.Level;
import es.iessaladillo.pedrojoya.pr016.data.local.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    @NonNull
    List<Level> queryLevels();

    @NonNull
    List<Student> queryStudentsByLevel(long levelId);

}
