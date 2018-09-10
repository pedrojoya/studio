package es.iessaladillo.pedrojoya.pr195.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr195.data.model.Student;
import es.iessaladillo.pedrojoya.pr195.data.remote.Api;
import io.reactivex.Observable;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;

    private final Api api;

    private RepositoryImpl(Api api) {
        this.api = api;
    }

    public static synchronized RepositoryImpl getInstance(Api api) {
        if (instance == null) {
            instance = new RepositoryImpl(api);
        }
        return instance;
    }

    @Override
    public Observable<List<Student>> getStudents() {
        return api.getStudents();
    }

}
