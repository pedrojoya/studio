package es.iessaladillo.pedrojoya.pr194.data.remote;

import java.util.List;

import es.iessaladillo.pedrojoya.pr194.data.model.Student;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("datos.json")
    Call<List<Student>> getStudents();

}
