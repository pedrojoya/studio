package es.iessaladillo.pedrojoya.pr040.data.remote;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr040.data.model.Student;
import es.iessaladillo.pedrojoya.pr040.base.Event;
import es.iessaladillo.pedrojoya.pr040.utils.NetworkUtils;
import es.iessaladillo.pedrojoya.pr040.base.RequestState;

public class StudentsLiveData extends LiveData<RequestState> {

    private LoadStudentsAsyncTask task;

    public StudentsLiveData() {
        loadData();
    }

    public void loadData() {
        task = new LoadStudentsAsyncTask();
        task.execute();
    }

    public void cancel() {
        if (task != null) task.cancel(true);
    }

    @SuppressLint("StaticFieldLeak")
    class LoadStudentsAsyncTask extends AsyncTask<Void, Void, Void> {

        private static final String DATA_URL = "http://www.informaticasaladillo.es/datos.json";
        private static final int TIMEOUT = 5000;

        @Override
        protected Void doInBackground(Void... voids) {
            postValue(new RequestState.Loading(true));
            String content;
            ArrayList<Student> data;
            try {
                // Simulate latency
                Thread.sleep(2000);
                content = NetworkUtils.loadUrl(DATA_URL, TIMEOUT);
                if (content != null) {
                    data = parseWithGson(content);
                    postValue(new RequestState.Result<List<Student>>(data));
                }
            } catch (Exception e) {
                postValue(new RequestState.Error(new Event<>(e)));
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
