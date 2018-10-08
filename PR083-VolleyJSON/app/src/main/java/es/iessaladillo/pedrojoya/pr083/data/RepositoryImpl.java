package es.iessaladillo.pedrojoya.pr083.data;

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

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr083.base.Event;
import es.iessaladillo.pedrojoya.pr083.base.RequestState;
import es.iessaladillo.pedrojoya.pr083.data.model.Student;
import es.iessaladillo.pedrojoya.pr083.data.remote.GsonArrayRequest;

public class RepositoryImpl implements Repository {

    private static final String TAG_STUDENTS = "TAG_STUDENTS";
    private static final String DATA_URL = "http://www.informaticasaladillo.es/datos.json";

    private static RepositoryImpl instance;

    private final MutableLiveData<RequestState<List<Student>>> studentsLiveData = new MutableLiveData<>();
    private final RequestQueue requestQueue;

    private RepositoryImpl(@NonNull RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public static RepositoryImpl getInstance(RequestQueue requestQueue) {
        if (instance == null) {
            synchronized (RepositoryImpl.class) {
                if (instance == null) {
                    instance = new RepositoryImpl(requestQueue);
                }
            }
        }
        return instance;
    }

    @Override
    public LiveData<RequestState<List<Student>>> getStudents() {
        //sendJsonRequest();
        sendGsonRequest();
        return studentsLiveData;
    }

    @SuppressWarnings("unused")
    private void sendJsonRequest() {
        studentsLiveData.postValue(new RequestState.Loading<>(true));
        JsonArrayRequest request = new JsonArrayRequest(DATA_URL, response -> {
            try {
                studentsLiveData.postValue(new RequestState.Result<>(parseJson(response)));
            } catch (Exception e) {
                studentsLiveData.postValue(new RequestState.Error<>(new Event<>(e)));
            }
        }, error -> studentsLiveData.postValue(
                new RequestState.Error<>(new Event<>(new Exception(error.getMessage())))));
        request.setTag(TAG_STUDENTS);
        requestQueue.add(request);
    }

    private void sendGsonRequest() {
        studentsLiveData.postValue(new RequestState.Loading<>(true));
        Gson gson = new Gson();
        Type type = new TypeToken<List<Student>>() { }.getType();
        requestQueue.add(new GsonArrayRequest<List<Student>>(Request.Method.GET, DATA_URL, type,
                response -> studentsLiveData.postValue(new RequestState.Result<>((List<Student>) response)),
                error -> studentsLiveData.postValue(
                        new RequestState.Error<>(new Event<>(new Exception(error.getMessage())))),
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
