package es.iessaladillo.pedrojoya.pr083.main;

import android.content.Context;
import android.support.v4.content.Loader;

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

import es.iessaladillo.pedrojoya.pr083.data.model.Student;
import es.iessaladillo.pedrojoya.pr083.data.remote.GsonArrayRequest;
import es.iessaladillo.pedrojoya.pr083.data.remote.VolleyInstance;

class StudentsLoader extends Loader<ArrayList<Student>> {

    private final String urlString;
    private final RequestQueue requestQueue;

    private ArrayList<Student> students;

    @SuppressWarnings("SameParameterValue")
    public StudentsLoader(Context context, String urlString) {
        super(context);
        this.urlString = urlString;
        requestQueue = VolleyInstance.getInstance(context).getRequestQueue();
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
    protected void onForceLoad() {
        sendJsonRequest();
        //sendGsonRequest();
    }

    @Override
    public void deliverResult(ArrayList<Student> data) {
        students = data;
        super.deliverResult(data);
    }

    @SuppressWarnings("unused")
    private void sendJsonRequest() {
        requestQueue.add(new JsonArrayRequest(urlString, response -> {
            ArrayList<Student> students;
            try {
                students = parseJson(response);
            } catch (JSONException e) {
                e.printStackTrace();
                students = new ArrayList<>();
            }
            deliverResult(students);
        }, error -> deliverResult(new ArrayList<>())));
    }

    private void sendGsonRequest() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Student>>() { }.getType();
        requestQueue.add(new GsonArrayRequest<>(Request.Method.GET,
                urlString, type, this::deliverResult, error -> deliverResult(new ArrayList<>()), gson));
    }

    private ArrayList<Student> parseJson(JSONArray studentsJSONArray) throws JSONException {
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < studentsJSONArray.length(); i++) {
            students.add(new Student(studentsJSONArray.getJSONObject(i)));
        }
        return students;
    }

}
