package es.iessaladillo.pedrojoya.pr170.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends AndroidViewModel {

    final ActivityMainModel model;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        model = new ActivityMainModel(application.getApplicationContext());
    }

    public ActivityMainModel getModel() {
        return model;
    }

}
