package es.iessaladillo.pedrojoya.pr050;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

public class App extends Application {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @SuppressWarnings("EmptyMethod")
    @Override
    public void onCreate() {
        super.onCreate();
    }

}
