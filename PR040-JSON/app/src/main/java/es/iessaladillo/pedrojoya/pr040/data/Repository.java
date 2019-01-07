package es.iessaladillo.pedrojoya.pr040.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr040.base.Call;
import es.iessaladillo.pedrojoya.pr040.base.Resource;
import es.iessaladillo.pedrojoya.pr040.data.remote.model.Student;

public interface Repository {
    Call<Resource<List<Student>>> queryStudents();
}
