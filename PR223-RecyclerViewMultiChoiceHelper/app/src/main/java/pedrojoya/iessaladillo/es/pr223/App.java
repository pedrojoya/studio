package pedrojoya.iessaladillo.es.pr223;

import android.app.Application;

import com.mooveit.library.Fakeit;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fakeit.init();
    }

}
