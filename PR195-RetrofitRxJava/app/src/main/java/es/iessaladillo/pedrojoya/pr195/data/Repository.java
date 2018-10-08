package es.iessaladillo.pedrojoya.pr195.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr195.base.RequestState;
import es.iessaladillo.pedrojoya.pr195.data.model.Student;

public interface Repository {
    LiveData<RequestState<List<Student>>> getStudents();
    void cancel();
}
