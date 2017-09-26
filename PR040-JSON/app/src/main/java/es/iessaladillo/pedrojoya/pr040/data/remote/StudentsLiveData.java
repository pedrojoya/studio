package es.iessaladillo.pedrojoya.pr040.data.remote;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr040.data.model.Student;
import es.iessaladillo.pedrojoya.pr040.utils.NetworkUtils;

public class StudentsLiveData extends LiveData<StudentsRequest> {

    public StudentsLiveData() {
        loadData();
    }

    public void loadData() {
        StudentsRequest currentStudentsRequest = getValue();
        setValue(StudentsRequest.newLoadingInstance(
                currentStudentsRequest == null ? null : currentStudentsRequest.getStudents()));
        (new LoadStudentsAsyncTask()).execute();
    }

    @SuppressLint("StaticFieldLeak")
    class LoadStudentsAsyncTask extends AsyncTask<Void, Void, StudentsRequest> {

        private static final String DATA_URL = "http://www.informaticasaladillo.es/datos.json";
        private static final int TIMEOUT = 5000;

        @Override
        protected StudentsRequest doInBackground(Void... voids) {
            String content;
            try {
                content = NetworkUtils.loadUrl(DATA_URL, TIMEOUT);
            } catch (IOException e) {
                e.printStackTrace();
                return StudentsRequest.newErrorInstance(e);
            }
            ArrayList<Student> data = null;
            if (content != null) {
                /*
                try {
                    data = parseJson(content);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return StudentsRequest.newErrorInstance(e);
                }
                */
                try {
                    data = parseWithGson(content);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    return StudentsRequest.newErrorInstance(e);
                }
            }
            return StudentsRequest.newListInstance(data);
        }

        @Override
        protected void onPostExecute(StudentsRequest data) {
            setValue(data);
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
