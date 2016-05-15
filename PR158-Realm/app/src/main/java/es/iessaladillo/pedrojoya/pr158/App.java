package es.iessaladillo.pedrojoya.pr158;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Se configura Realm. La BD se llamará instituto
        Realm.setDefaultConfiguration(
                new RealmConfiguration.Builder(this).name("instituto.realm").build());
    }
}
