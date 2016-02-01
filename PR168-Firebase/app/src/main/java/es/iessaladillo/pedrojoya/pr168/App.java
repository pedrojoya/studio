package es.iessaladillo.pedrojoya.pr168;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.firebase.client.Firebase;

public class App extends Application {

    public static final String FIREBASE_URL = "https://saladillo.firebaseio.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        // Se inicializa Firebase.
        Firebase.setAndroidContext(this);
    }

}
