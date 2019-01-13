package es.iessaladillo.pedrojoya.pr254.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr254.base.Resource;
import es.iessaladillo.pedrojoya.pr254.data.mapper.StudentMapper;
import es.iessaladillo.pedrojoya.pr254.data.model.Student;
import es.iessaladillo.pedrojoya.pr254.data.remote.ApiService;
import es.iessaladillo.pedrojoya.pr254.data.remote.dto.StudentDto;

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
    public LiveData<Resource<List<Student>>> queryStudents() {
        MutableLiveData<Resource<List<Student>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        apiservice.getStudents(new ApiService.Callback<List<StudentDto>>() {
            @Override
            public void onFailure(Exception exception) {
                result.setValue(Resource.error(exception));
            }

            @Override
            public void onResponse(List<StudentDto> data) {
                result.setValue(Resource.success(studentMapper.map(data)));
            }
        });
        return result;
    }

}
