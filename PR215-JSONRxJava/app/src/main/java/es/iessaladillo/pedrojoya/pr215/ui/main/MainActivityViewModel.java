package es.iessaladillo.pedrojoya.pr215.ui.main;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr215.data.model.Student;
import es.iessaladillo.pedrojoya.pr215.utils.NetworkUtils;
import io.reactivex.Observable;

@SuppressLint("StaticFieldLeak")
class MainActivityViewModel extends AndroidViewModel {

    private static final String DATA_URL = "http://www.informaticasaladillo.es/datos.json";
    private static final int TIMEOUT = 5000;

    private List<Student> cachedStudents = null;

    public MainActivityViewModel(Application application) {
        super(application);
    }

    public Observable<List<Student>> getStudents(boolean forceLoad) {
        if (forceLoad || cachedStudents == null) {
            return Observable.fromCallable(this::loadStudents).doOnNext(
                    students -> cachedStudents = students);
        } else {
            return Observable.just(cachedStudents);
        }
    }

    private List<Student> loadStudents() throws Exception {
        Log.d(getClass().getName(), "Loading");
        String content = NetworkUtils.loadUrl(DATA_URL, TIMEOUT);
        ArrayList<Student> data = null;
        if (content != null) {
            // data = parseJson(content);
            data = parseWithGson(content);
        }
        return data;
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
