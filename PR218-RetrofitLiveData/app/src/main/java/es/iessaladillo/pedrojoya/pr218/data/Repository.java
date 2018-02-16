package es.iessaladillo.pedrojoya.pr218.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr218.data.model.Student;
import io.reactivex.Observable;

public interface Repository {
    Observable<List<Student>> getStudents();
}
