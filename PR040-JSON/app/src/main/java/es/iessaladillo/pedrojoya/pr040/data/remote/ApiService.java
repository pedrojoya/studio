package es.iessaladillo.pedrojoya.pr040.data.remote;

import java.util.List;

import es.iessaladillo.pedrojoya.pr040.base.Call;
import es.iessaladillo.pedrojoya.pr040.base.Resource;
import es.iessaladillo.pedrojoya.pr040.data.remote.model.Student;

public interface ApiService {

    Call<Resource<List<Student>>> getStudents();

}
