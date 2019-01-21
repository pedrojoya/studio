package es.iessaladillo.pedrojoya.pr040.data.remote;

import java.util.List;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr040.base.Resource;
import es.iessaladillo.pedrojoya.pr040.data.remote.dto.StudentDto;

public interface ApiService {

    LiveData<Resource<List<StudentDto>>> getStudents(String tag);

    void cancel(String tag);
}
