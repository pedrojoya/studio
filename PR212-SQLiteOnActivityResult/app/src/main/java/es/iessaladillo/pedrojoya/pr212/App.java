package es.iessaladillo.pedrojoya.pr212;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

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
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

}
