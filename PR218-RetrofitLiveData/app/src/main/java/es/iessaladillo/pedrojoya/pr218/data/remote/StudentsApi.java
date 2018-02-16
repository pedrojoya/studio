package es.iessaladillo.pedrojoya.pr218.data.remote;

import java.util.List;

import es.iessaladillo.pedrojoya.pr218.data.model.Student;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface StudentsApi {

    @GET("datos3.json")
    Observable<List<Student>> getStudents();

}
