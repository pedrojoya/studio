package es.iessaladillo.pedrojoya.pr171;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Se inicializa DBFlow.
        FlowManager.init(this);
        // Se inicializa Stetho.
        Stetho.initializeWithDefaults(this);
    }

}
