package es.iessaladillo.pedrojoya.pr016;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.squareup.leakcanary.LeakCanary;

@SuppressWarnings("WeakerAccess")
public class App extends Application {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
