package es.iessaladillo.pedrojoya.pr005;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

@SuppressWarnings("WeakerAccess")
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
    }

}
