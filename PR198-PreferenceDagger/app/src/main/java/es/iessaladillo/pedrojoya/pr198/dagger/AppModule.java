package es.iessaladillo.pedrojoya.pr198.dagger;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;

@SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
@Module
public class AppModule {

    Application mApplication;

    // El módulo debe recibir el objeto Application.
    public AppModule(Application application) {
        mApplication = application;
    }

    // Proporciona el objeto Application.
    @Provides
    @AppScope
    Application providesApplication() {
        return mApplication;
    }

    // Proporciona el contexto. Requiere del objeto Application
    @Provides
    @AppScope
    Context providesContext(Application application) {
        return application.getApplicationContext();
    }

    // Proporciona el default sharedpreferences. Requiere del contexto.
    @Provides
    @AppScope
    SharedPreferences providesSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
