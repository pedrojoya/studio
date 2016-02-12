package es.iessaladillo.pedrojoya.pr168;

import android.app.Application;
import android.text.TextUtils;

import com.facebook.stetho.Stetho;
import com.firebase.client.Firebase;
import com.firebase.client.Logger;

public class App extends Application {

    public static final String FIREBASE_URL = "https://saladillo.firebaseio.com/";

    private static String sUid;

    public static String getsUid() {
        return sUid;
    }

    public static void setUid(String uid) {
        sUid = uid;
    }

    public static String getUidAlumnosUrl() {
        return FIREBASE_URL + "users/" + (TextUtils.isEmpty(sUid)?"":sUid + "/") + "alumnos/";
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        // Se inicializa Firebase.
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setLogLevel(Logger.Level.DEBUG);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }

}
