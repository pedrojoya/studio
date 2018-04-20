package es.iessaladillo.pedrojoya.pr027;

import android.app.Application;

import com.facebook.stetho.Stetho;

@SuppressWarnings("WeakerAccess")
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
