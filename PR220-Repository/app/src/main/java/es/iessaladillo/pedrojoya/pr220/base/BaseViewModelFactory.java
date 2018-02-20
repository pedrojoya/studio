package es.iessaladillo.pedrojoya.pr220.base;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class BaseViewModelFactory extends ViewModelProvider.AndroidViewModelFactory{


    private final Application application;

    public BaseViewModelFactory(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

}
