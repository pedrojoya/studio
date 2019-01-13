package es.iessaladillo.pedrojoya.pr254.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr254.base.Resource;
import es.iessaladillo.pedrojoya.pr254.data.model.Student;

public interface Repository {
    LiveData<Resource<List<Student>>> queryStudents();
}
