package es.iessaladillo.pedrojoya.pr083.data.remote;

import java.util.List;

import es.iessaladillo.pedrojoya.pr083.data.remote.dto.StudentDto;

public interface ApiService {

    void getStudents(Callback<List<StudentDto>> callback);

    // Callback interface for communication with client layer.
    interface Callback<T> {

        void onFailure(Exception exception);

        void onResponse(T result);

    }

}
