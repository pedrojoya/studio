package pedrojoya.iessaladillo.es.pr232;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.mooveit.library.Fakeit;

public class App extends Application {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fakeit.init();
    }

}
