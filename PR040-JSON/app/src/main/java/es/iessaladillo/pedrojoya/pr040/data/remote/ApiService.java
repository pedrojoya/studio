package es.iessaladillo.pedrojoya.pr040.data.remote;

import java.util.List;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr040.base.Resource;
import es.iessaladillo.pedrojoya.pr040.data.remote.model.Student;

public interface ApiService {

    LiveData<Resource<List<Student>>> getStudents();

}
