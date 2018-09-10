package es.iessaladillo.pedrojoya.pr083.data.remote;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr083.base.Event;
import es.iessaladillo.pedrojoya.pr083.base.RequestState;
import es.iessaladillo.pedrojoya.pr083.data.model.Student;

public class StudentsLiveData extends LiveData<RequestState> {

    private static final String TAG_STUDENTS = "TAG_STUDENTS";

    private final RequestQueue requestQueue;
    private final String urlString;

    public StudentsLiveData(@NonNull RequestQueue requestQueue, @NonNull String urlString) {
        this.requestQueue = requestQueue;
        this.urlString = urlString;
        loadData();
    }

    public void loadData() {
        //sendJsonRequest();
        sendGsonRequest();
    }

    @SuppressWarnings("unused")
    private void sendJsonRequest() {
        postValue(new RequestState.Loading(true));
        JsonArrayRequest request = new JsonArrayRequest(urlString, response -> {
            try {
                postValue(new RequestState.Result<>(parseJson(response)));
            } catch (Exception e) {
                postValue(new RequestState.Error(new Event<>(e)));
            }
        }, error -> postValue(
                new RequestState.Error(new Event<>(new Exception(error.getMessage())))));
        request.setTag(TAG_STUDENTS);
        requestQueue.add(request);
    }

    private void sendGsonRequest() {
        postValue(new RequestState.Loading(true));
        Gson gson = new Gson();
        Type type = new TypeToken<List<Student>>() { }.getType();
        //noinspection unchecked
        requestQueue.add(new GsonArrayRequest<>(Request.Method.GET, urlString, type,
                response -> postValue(new RequestState.Result<>((List<Student>) response)),
                error -> postValue(new RequestState.Error(new Event<>(new Exception(error.getMessage())))),
                gson));
    }

    private List<Student> parseJson(JSONArray studentsJSONArray) throws JSONException {
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < studentsJSONArray.length(); i++) {
            students.add(new Student(studentsJSONArray.getJSONObject(i)));
        }
        return students;
    }

    public void cancel() {
        requestQueue.cancelAll(TAG_STUDENTS);
    }

}
