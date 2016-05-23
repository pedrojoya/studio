package es.iessaladillo.pedrojoya.pr180;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class App extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}