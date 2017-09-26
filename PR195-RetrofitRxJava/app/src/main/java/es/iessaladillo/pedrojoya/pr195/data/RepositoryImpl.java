package es.iessaladillo.pedrojoya.pr195.data;

import android.content.Context;

import java.util.List;

import es.iessaladillo.pedrojoya.pr195.data.model.Student;
import es.iessaladillo.pedrojoya.pr195.data.remote.StudentsApi;
import es.iessaladillo.pedrojoya.pr195.data.remote.StudentsService;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;

    private final StudentsApi api;

    private RepositoryImpl(Context context) {
        api = StudentsService.getInstance(context).getApi();
    }

    public static synchronized RepositoryImpl getInstance(Context context) {
        if (instance == null) {
            instance = new RepositoryImpl(context);
        }
        return instance;
    }

    @Override
    public io.reactivex.Observable<List<Student>> getStudents() {
        return api.getStudents();
    }

}
