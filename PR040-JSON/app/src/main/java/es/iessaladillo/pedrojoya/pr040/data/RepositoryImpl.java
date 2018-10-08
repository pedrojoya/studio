package es.iessaladillo.pedrojoya.pr040.data;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr040.base.Event;
import es.iessaladillo.pedrojoya.pr040.base.RequestState;
import es.iessaladillo.pedrojoya.pr040.data.model.Student;
import es.iessaladillo.pedrojoya.pr040.utils.NetworkUtils;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;

    private final MutableLiveData<RequestState<List<Student>>> studentsLiveData = new MutableLiveData<>();
    private LoadStudentsAsyncTask task;

    public static RepositoryImpl getInstance() {
        if (instance == null) {
            synchronized (RepositoryImpl.class) {
                if (instance == null) {
                    instance = new RepositoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public LiveData<RequestState<List<Student>>> getStudents() {
        task = new LoadStudentsAsyncTask();
        task.execute();
        return studentsLiveData;
    }

    @Override
    public void cancel() {
        if (task != null) task.cancel(true);
    }

    @SuppressLint("StaticFieldLeak")
    class LoadStudentsAsyncTask extends AsyncTask<Void, Void, Void> {

        private static final String DATA_URL = "http://www.informaticasaladillo.es/datos.json";
        private static final int TIMEOUT = 5000;

        @Override
        protected Void doInBackground(Void... voids) {
            studentsLiveData.postValue(new RequestState.Loading<>(true));
            String content;
            ArrayList<Student> data;
            try {
                // Simulate latency
                Thread.sleep(2000);
                content = NetworkUtils.loadUrl(DATA_URL, TIMEOUT);
                if (content != null) {
                    data = parseWithGson(content);
                    studentsLiveData.postValue(new RequestState.Result<>(data));
                }
            } catch (Exception e) {
                studentsLiveData.postValue(new RequestState.Error<>(new Event<>(e)));
            }
            return null;
        }

        @SuppressWarnings("unused")
        private ArrayList<Student> parseJson(String content) throws JSONException {
            ArrayList<Student> students = new ArrayList<>();
            JSONArray studentsJSONArray = new JSONArray(content);
            for (int i = 0; i < studentsJSONArray.length(); i++) {
                students.add(new Student(studentsJSONArray.getJSONObject(i)));
            }
            return students;
        }

        private ArrayList<Student> parseWithGson(String content) throws JsonSyntaxException {
            Gson gson = new Gson();
            Type studentListType = new TypeToken<List<Student>>() { }.getType();
            return gson.fromJson(content, studentListType);
        }

    }

}
