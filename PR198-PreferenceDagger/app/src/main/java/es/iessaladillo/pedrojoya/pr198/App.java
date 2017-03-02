package es.iessaladillo.pedrojoya.pr198;

import android.app.Application;

import es.iessaladillo.pedrojoya.pr198.dagger.AppComponent;
import es.iessaladillo.pedrojoya.pr198.dagger.AppModule;
import es.iessaladillo.pedrojoya.pr198.dagger.DaggerAppComponent;

public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        // Se construye el componente AppComponent de Dagger.
        mAppComponent = DaggerAppComponent.builder()
                // Debemos proporcionarle los módulos.
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}
