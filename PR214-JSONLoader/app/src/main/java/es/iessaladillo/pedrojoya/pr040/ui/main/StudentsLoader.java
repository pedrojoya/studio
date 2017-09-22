package es.iessaladillo.pedrojoya.pr040.ui.main;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

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

class StudentsLoader extends AsyncTaskLoader<ArrayList<Student>> {

    private final String urlString;

    private ArrayList<Student> students;

    @SuppressWarnings("SameParameterValue")
    public StudentsLoader(Context context, String urlString) {
        super(context);
        this.urlString = urlString;
    }

    @Override
    protected void onStartLoading() {
        if (students == null || takeContentChanged()) {
            forceLoad();
        } else {
            deliverResult(students);
        }
    }

    @Override
    public ArrayList<Student> loadInBackground() {
        if (!NetworkUtils.isConnectionAvailable(getContext())) {
            return null;
        }
        String content = null;
        try {
            content = NetworkUtils.loadUrl(urlString);
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
    public void deliverResult(ArrayList<Student> data) {
        students = data;
        super.deliverResult(data);
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
