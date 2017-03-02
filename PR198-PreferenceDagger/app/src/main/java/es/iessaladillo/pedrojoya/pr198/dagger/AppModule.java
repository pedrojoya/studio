package es.iessaladillo.pedrojoya.pr198.dagger;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Application mApplication;

    // El módulo debe recibir el objeto Application.
    public AppModule(Application application) {
        mApplication = application;
    }

    // Proporciona el objeto Application.
    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    // Proporciona el contexto. Requiere del objeto Application
    @Provides
    @Singleton
    Context providesContext(Application application) {
        return application.getApplicationContext();
    }

    // Proporciona el defaultsharedpreferences. Requiere del contexto.
    @Provides
    @Singleton
    SharedPreferences providesSharedPreferenes(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
