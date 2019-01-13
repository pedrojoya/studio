package es.iessaladillo.pedrojoya.pr083.di;

import android.content.Context;

import com.android.volley.RequestQueue;

import es.iessaladillo.pedrojoya.pr083.data.Repository;
import es.iessaladillo.pedrojoya.pr083.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr083.data.mapper.StudentMapper;
import es.iessaladillo.pedrojoya.pr083.data.remote.ApiService;
import es.iessaladillo.pedrojoya.pr083.data.remote.ApiServiceImpl;
import es.iessaladillo.pedrojoya.pr083.data.remote.VolleyInstance;
import es.iessaladillo.pedrojoya.pr083.ui.main.MainFragmentViewModelFactory;

public class Injector {

    private Injector() {
    }

    public static MainFragmentViewModelFactory provideMainFragmentViewModelFactory(
        Context context) {
        return new MainFragmentViewModelFactory(getRepository(context));
    }

    private static Repository getRepository(Context context) {
        return RepositoryImpl.getInstance(provideApiService(context), provideStudentMapper());
    }

    private static ApiService provideApiService(Context context) {
        return new ApiServiceImpl(getRequestQueue(context));
    }

    private static StudentMapper provideStudentMapper() {
        return new StudentMapper();
    }

    private static RequestQueue getRequestQueue(Context context) {
        return VolleyInstance.getInstance(context.getApplicationContext()).getRequestQueue();
    }

}
