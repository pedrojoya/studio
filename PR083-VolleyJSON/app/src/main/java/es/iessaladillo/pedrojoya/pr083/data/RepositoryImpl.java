package es.iessaladillo.pedrojoya.pr083.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import es.iessaladillo.pedrojoya.pr083.base.Resource;
import es.iessaladillo.pedrojoya.pr083.data.mapper.StudentMapper;
import es.iessaladillo.pedrojoya.pr083.data.model.Student;
import es.iessaladillo.pedrojoya.pr083.data.remote.ApiService;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;

    private final ApiService apiservice;
    private final StudentMapper studentMapper;

    public static RepositoryImpl getInstance(ApiService apiService, StudentMapper studentMapper) {
        if (instance == null) {
            synchronized (RepositoryImpl.class) {
                if (instance == null) {
                    instance = new RepositoryImpl(apiService, studentMapper);
                }
            }
        }
        return instance;
    }

    private RepositoryImpl(ApiService apiservice, StudentMapper studentMapper) {
        this.apiservice = apiservice;
        this.studentMapper = studentMapper;
    }

    @Override
    public LiveData<Resource<List<Student>>> queryStudents(String tag) {
        return Transformations.map(apiservice.getStudents(tag), resource -> {
            if (resource.isLoading()) {
                return Resource.loading();
            } else if (resource.hasError()) {
                return Resource.error(resource.getException());
            } else {
                return Resource.success(studentMapper.map(resource.getData()));
            }
        });
    }

    @Override
    public void cancel(String tag) {
        apiservice.cancel(tag);
    }

}
