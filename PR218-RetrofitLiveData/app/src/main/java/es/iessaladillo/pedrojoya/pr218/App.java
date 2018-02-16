package es.iessaladillo.pedrojoya.pr218;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class App extends Application {
    public void onCreate() {
        super.onCreate();
        setupStetho();
    }

    private void setupStetho() {
        Stetho.initializeWithDefaults(this);
    }

}
