package es.iessaladillo.pedrojoya.pr195.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr195.data.model.Student;
import io.reactivex.Single;

public interface Repository {

    Single<List<Student>> queryStudents();

}
