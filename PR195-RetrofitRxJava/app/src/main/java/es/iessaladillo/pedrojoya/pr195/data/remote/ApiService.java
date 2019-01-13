package es.iessaladillo.pedrojoya.pr195.data.remote;

import java.util.List;

import es.iessaladillo.pedrojoya.pr195.data.remote.dto.StudentDto;
import io.reactivex.Single;

public interface ApiService {

    Single<List<StudentDto>> getStudents();

}
