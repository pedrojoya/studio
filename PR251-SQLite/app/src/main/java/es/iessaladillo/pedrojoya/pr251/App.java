package es.iessaladillo.pedrojoya.pr251;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

@SuppressWarnings("WeakerAccess")
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupLeakCanary();
        setupStetho();
    }

    private void setupStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private void setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

}
