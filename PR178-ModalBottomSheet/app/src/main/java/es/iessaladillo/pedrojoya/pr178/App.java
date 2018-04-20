package es.iessaladillo.pedrojoya.pr178;

import android.app.Application;

import com.mooveit.library.Fakeit;

@SuppressWarnings("WeakerAccess")
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fakeit.init();
    }

}
