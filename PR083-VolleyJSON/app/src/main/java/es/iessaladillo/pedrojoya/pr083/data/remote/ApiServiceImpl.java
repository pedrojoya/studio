package es.iessaladillo.pedrojoya.pr083.data.remote;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
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
    public void getStudents(ApiService.Callback<List<StudentDto>> callback) {
        //sendGetStudentsJsonRequest(BASE_URL + "students", callback);
        sendGetStudentsGsonRequest(BASE_URL + "students", callback);
    }

    // Just for demo purpose
    private void sendGetStudentsJsonRequest(String url, ApiService.Callback<List<StudentDto>> callback) {
        JsonArrayRequest request = new JsonArrayRequest(url, response -> {
            try {
                callback.onResponse(parseJson(response));
            } catch (Exception e) {
                callback.onFailure(e);
            }
        }, error -> callback.onFailure(new Exception(error.getMessage())));
        requestQueue.add(request);
    }

    private void sendGetStudentsGsonRequest(String url, ApiService.Callback<List<StudentDto>> callback) {
        GsonArrayRequest<List<StudentDto>> request = new GsonArrayRequest<>(Request.Method.GET, url,
            studentListType, callback::onResponse, error -> callback.onFailure(new Exception
            (error.getMessage())), gson);
        requestQueue.add(request);
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
