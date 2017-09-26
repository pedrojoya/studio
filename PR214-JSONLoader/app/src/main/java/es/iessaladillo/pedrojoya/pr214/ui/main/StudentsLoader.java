package es.iessaladillo.pedrojoya.pr214.ui.main;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr214.data.model.Student;
import es.iessaladillo.pedrojoya.pr214.data.remote.StudentsRequest;
import es.iessaladillo.pedrojoya.pr214.utils.NetworkUtils;

class StudentsLoader extends AsyncTaskLoader<StudentsRequest> {

    private final String urlString;
    private final int timeout;

    private StudentsRequest studentsRequest;

    @SuppressWarnings("SameParameterValue")
    public StudentsLoader(Context context, String urlString, int timeout) {
        super(context);
        this.urlString = urlString;
        this.timeout = timeout;
    }

    @Override
    protected void onStartLoading() {
        if (studentsRequest == null || takeContentChanged()) {
            forceLoad();
        } else {
            deliverResult(studentsRequest);
        }
    }

    @Override
    public StudentsRequest loadInBackground() {
        String content;
        try {
            content = NetworkUtils.loadUrl(urlString, timeout);
        } catch (IOException e) {
            e.printStackTrace();
            return StudentsRequest.newErrorInstance(e);
        }
        ArrayList<Student> data = null;
        if (content != null) {
            try {
                data = parseJson(content);
            } catch (JSONException e) {
                e.printStackTrace();
                return StudentsRequest.newErrorInstance(e);
            }
            //data = parseWithGson(content);
        }
        return StudentsRequest.newListInstance(data);
    }

    @Override
    public void deliverResult(StudentsRequest studentsRequest) {
        this.studentsRequest = studentsRequest;
        super.deliverResult(this.studentsRequest);
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
    private ArrayList<Student> parseWithGson(String content) throws JsonSyntaxException {
        Gson gson = new Gson();
        Type studentListType = new TypeToken<List<Student>>() { }.getType();
        return gson.fromJson(content, studentListType);
    }

}
