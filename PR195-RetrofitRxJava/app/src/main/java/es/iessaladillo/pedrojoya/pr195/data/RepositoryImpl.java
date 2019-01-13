package es.iessaladillo.pedrojoya.pr195.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr195.data.mapper.StudentMapper;
import es.iessaladillo.pedrojoya.pr195.data.model.Student;
import es.iessaladillo.pedrojoya.pr195.data.remote.ApiService;
import io.reactivex.Single;

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
    public Single<List<Student>> queryStudents() {
        return apiservice.getStudents().map(studentMapper::map);
    }

}
