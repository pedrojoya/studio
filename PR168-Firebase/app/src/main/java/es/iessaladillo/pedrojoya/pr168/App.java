package es.iessaladillo.pedrojoya.pr168;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.firebase.client.Firebase;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        // Se inicializa Firebase.
        Firebase.setAndroidContext(this);
    }

}
