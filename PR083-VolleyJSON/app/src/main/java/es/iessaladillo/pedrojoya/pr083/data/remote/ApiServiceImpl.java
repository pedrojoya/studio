package es.iessaladillo.pedrojoya.pr083.data.remote;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr083.base.Resource;
import es.iessaladillo.pedrojoya.pr083.data.model.Student;
import es.iessaladillo.pedrojoya.pr083.data.remote.dto.StudentDto;

@SuppressWarnings({"SameParameterValue", "unused"})
public class ApiServiceImpl implements ApiService {

    private static final String BASE_URL = "http://my-json-server.typicode.com/pedrojoya/jsonserver/";

    private final Type studentListType = new TypeToken<List<StudentDto>>() { }.getType();
    private final Gson gson = new Gson();
    private final RequestQueue requestQueue;

    public ApiServiceImpl(@NonNull RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    @Override
    public LiveData<Resource<List<StudentDto>>> getStudents(String tag) {
        MutableLiveData<Resource<List<StudentDto>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        GsonArrayRequest<List<StudentDto>> request = new GsonArrayRequest<>(Request.Method.GET,
            BASE_URL + "students", studentListType,
            studentDtoList -> result.setValue(Resource.success(studentDtoList)),
            error -> result.setValue(Resource.error(new Exception(error.getMessage()))), gson, tag);
        requestQueue.add(request);
        return result;
    }

    @Override
    public void cancel(String tag) {
        requestQueue.cancelAll(tag);
    }

    // Just for demo purpose
    private ArrayList<Student> parseWithGson(String content) throws JsonSyntaxException {
        Gson gson = new Gson();
        Type studentListType = new TypeToken<List<Student>>() { }.getType();
        return gson.fromJson(content, studentListType);
    }

    // Just for demo purpose
    private List<StudentDto> parseJson(JSONArray studentsJSONArray) throws JSONException {
        ArrayList<StudentDto> students = new ArrayList<>();
        for (int i = 0; i < studentsJSONArray.length(); i++) {
            students.add(new StudentDto(studentsJSONArray.getJSONObject(i)));
        }
        return students;
    }

}
