package es.iessaladillo.pedrojoya.pr194.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr194.data.mapper.StudentMapper;
import es.iessaladillo.pedrojoya.pr194.data.model.Student;
import es.iessaladillo.pedrojoya.pr194.data.remote.ApiService;
import es.iessaladillo.pedrojoya.pr194.data.remote.dto.StudentDto;

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
    public void queryStudents(Callback<List<Student>> callback) {
        apiservice.getStudents(new ApiService.Callback<List<StudentDto>>() {
            @Override
            public void onFailure(Exception exception) {
                callback.onFailure(exception);
            }

            @Override
            public void onResponse(List<StudentDto> result) {
                callback.onResponse(studentMapper.map(result));
            }
        });
    }

}
