package es.iessaladillo.pedrojoya.pr011.injection;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr011.data.Repository;
import es.iessaladillo.pedrojoya.pr011.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr011.data.local.Database;
import es.iessaladillo.pedrojoya.pr011.data.local.StudentDao;
import es.iessaladillo.pedrojoya.pr011.data.local.StudentDaoImpl;
import es.iessaladillo.pedrojoya.pr011.ui.main.MainActivityViewModel;
import es.iessaladillo.pedrojoya.pr011.ui.main.MainActivityViewModelFactory;

public class Injector {

    private static final Injector instance = new Injector();

    public static Injector getInstance() {
        return instance;
    }

    private Injector() {
    }

    @NonNull
    private Database provideDatabase() {
        return Database.getInstance();
    }

    @NonNull
    private StudentDao provideStudentDao() {
        return new StudentDaoImpl(provideDatabase());
    }

    @NonNull
    private Repository provideRepository() {
        return RepositoryImpl.getInstance(provideStudentDao());
    }

    @NonNull
    private MainActivityViewModelFactory provideMainActivityViewModelFactory() {
        return new MainActivityViewModelFactory(provideRepository());
    }

    @NonNull
    public MainActivityViewModel provideMainActivityViewModel(@NonNull FragmentActivity activity) {
        return ViewModelProviders.of(activity, provideMainActivityViewModelFactory()).get(
                MainActivityViewModel.class);
    }

}
