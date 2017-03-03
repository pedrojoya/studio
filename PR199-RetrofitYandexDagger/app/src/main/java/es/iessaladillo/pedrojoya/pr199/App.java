package es.iessaladillo.pedrojoya.pr199;

import android.app.Application;

import es.iessaladillo.pedrojoya.pr199.dagger.AppComponent;
import es.iessaladillo.pedrojoya.pr199.dagger.AppModule;
import es.iessaladillo.pedrojoya.pr199.dagger.DaggerAppComponent;

@SuppressWarnings("WeakerAccess")
public class App extends Application {

    private AppComponent mAppComponent;

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Se crea el componente AppComponent.
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                // No es necesario proporcionar el NetModule porque todos sus métodos
                // son estáticos.
                // .netModule(new NetModule())
                .build();
    }

}
