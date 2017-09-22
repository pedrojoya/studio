package es.iessaladillo.pedrojoya.pr040.ui.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr040.data.model.Student;
import es.iessaladillo.pedrojoya.pr040.utils.NetworkUtils;

public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<List<Student>> students = new MutableLiveData<>();
    private Context applicationContext;

    public MainActivityViewModel(Application application) {
        super(application);
        applicationContext = application.getApplicationContext();
        forceLoadStudents();
    }

    public void forceLoadStudents() {
        if (!NetworkUtils.isConnectionAvailable(applicationContext)) {
            return;
        }
        (new LoadStudentsAsyncTask()).execute();
    }

    public LiveData<List<Student>> getStudents() {
        return students;
    }

    class LoadStudentsAsyncTask extends AsyncTask<Void, Void, List<Student>> {

        private static final String DATA_URL =
                "http://www.informaticasaladillo.es/datos.json";

        @Override
        protected List<Student> doInBackground(Void... voids) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String content = null;
            try {
                content = NetworkUtils.loadUrl(DATA_URL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<Student> data = null;
            if (content != null) {
                try {
                    data = parseJson(content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //data = parseWithGson(content);
            }
            return data;
        }

        @Override
        protected void onPostExecute(List<Student> data) {
            students.setValue(data);
        }

        private ArrayList<Student> parseJson(String content) throws JSONException {
            ArrayList<Student> students = new ArrayList<>();
            JSONArray studentsJSONArray = new JSONArray(content);
            for (int i = 0; i < studentsJSONArray.length(); i++) {
                students.add(new Student(studentsJSONArray.getJSONObject(i)));
            }
            return students;
        }

        @SuppressWarnings("unused")
        private ArrayList<Student> parseWithGson(String content) {
            Gson gson = new Gson();
            Type studentListType = new TypeToken<List<Student>>() { }.getType();
            return gson.fromJson(content, studentListType);
        }

    }

}
