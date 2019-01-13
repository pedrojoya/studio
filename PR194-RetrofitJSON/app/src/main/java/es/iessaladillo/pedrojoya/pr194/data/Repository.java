package es.iessaladillo.pedrojoya.pr194.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr194.data.model.Student;

public interface Repository {

    void queryStudents(Callback<List<Student>> callback);

    // Callback interface for communiation with client layer.
    interface Callback<T> {

        void onFailure(Exception exception);

        void onResponse(T result);

    }

}
