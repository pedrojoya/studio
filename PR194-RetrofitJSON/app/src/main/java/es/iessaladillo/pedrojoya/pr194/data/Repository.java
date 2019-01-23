package es.iessaladillo.pedrojoya.pr194.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr194.base.Resource;
import es.iessaladillo.pedrojoya.pr194.data.model.Student;

public interface Repository {

    LiveData<Resource<List<Student>>> queryStudents(String tag);

    void cancel(String tag);

}
