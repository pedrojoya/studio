package es.iessaladillo.pedrojoya.pr040.di;

import es.iessaladillo.pedrojoya.pr040.data.Repository;
import es.iessaladillo.pedrojoya.pr040.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr040.data.mapper.StudentMapper;
import es.iessaladillo.pedrojoya.pr040.data.remote.ApiService;
import es.iessaladillo.pedrojoya.pr040.data.remote.ApiServiceImpl;
import es.iessaladillo.pedrojoya.pr040.ui.main.MainFragmentViewModelFactory;

public class Injector {

    private Injector() { }

    public static MainFragmentViewModelFactory provideMainFragmentViewModelFactory() {
        return new MainFragmentViewModelFactory(getRepository());
    }

    private static Repository getRepository() {
        return RepositoryImpl.getInstance(provideApiService(), provideStudentMapper());
    }

    private static ApiService provideApiService() {
        return new ApiServiceImpl();
    }

    private static StudentMapper provideStudentMapper() {
        return new StudentMapper();
    }

}
