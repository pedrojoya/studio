package es.iessaladillo.pedrojoya.pr199.dagger;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    public Application providesApplication() {
        return mApplication;
    }

    @Provides
    public Context providesContext() {
        return mApplication.getApplicationContext();
    }

}
