package es.iessaladillo.pedrojoya.pr040.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr040.base.Resource;
import es.iessaladillo.pedrojoya.pr040.data.remote.ApiService;
import es.iessaladillo.pedrojoya.pr040.data.remote.model.Student;

public class RepositoryImpl implements Repository {

    private final ApiService apiservice;

    public RepositoryImpl(ApiService apiservice) {
        this.apiservice = apiservice;
    }

    @Override
    public LiveData<Resource<List<Student>>> queryStudents() {
        return apiservice.getStudents();
    }

}
