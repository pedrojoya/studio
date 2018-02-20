package es.iessaladillo.pedrojoya.pr220.data.remote;

import java.util.List;

import es.iessaladillo.pedrojoya.pr220.Constants;
import es.iessaladillo.pedrojoya.pr220.data.model.Student;
import es.iessaladillo.pedrojoya.pr220.data.remote.requests.DeleteStudentCriteria;
import es.iessaladillo.pedrojoya.pr220.data.remote.responses.StudentResponse;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

@SuppressWarnings("unused")
public interface StudentsApi {

    @GET(Constants.API_STUDENT_SPREADSHEET)
    Observable<List<StudentResponse>> getStudents();

    @GET(Constants.API_STUDENT_SPREADSHEET + "/search")
    Single<List<StudentResponse>> getStudent(
            @Query(Constants.API_STUDENT_ID_FIELD_NAME) String studentId);

    @POST(Constants.API_STUDENT_SPREADSHEET)
    Single<StudentResponse> insertStudent(@Body Student student);

    @PUT(Constants.API_STUDENT_SPREADSHEET + "/" + Constants.API_STUDENT_ID_FIELD_NAME +
            "/{studentId}")
    Single<List<StudentResponse>> updateStudent(@Path("studentId") String studentId,
            @Body Student student);

    @HTTP(method = "DELETE", path = Constants.API_STUDENT_SPREADSHEET, hasBody = true)
    Single<List<StudentResponse>> deleteStudent(@Body DeleteStudentCriteria criteria);

}
