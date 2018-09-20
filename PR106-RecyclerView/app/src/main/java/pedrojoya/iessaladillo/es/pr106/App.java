package pedrojoya.iessaladillo.es.pr106;

import android.app.Application;

import com.mooveit.library.Fakeit;
import com.squareup.leakcanary.LeakCanary;

@SuppressWarnings("WeakerAccess")
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        Fakeit.init();
    }

}
