package es.iessaladillo.pedrojoya.pr168;

import android.app.Application;
import android.text.TextUtils;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

public class App extends Application {

    private static String sUid;

    public static String getsUid() {
        return sUid;
    }

    public static void setUid(String uid) {
        sUid = uid;
    }

    public static String getUidAlumnosUrl() {
        return "users/" + (TextUtils.isEmpty(sUid) ? "" : sUid + "/") + "alumnos/";
    }

    public static String getUidCursosUrl() {
        return "users/" + (TextUtils.isEmpty(sUid) ? "" : sUid + "/") + "cursos/";
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Se inicializa Firebase.
        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

}
