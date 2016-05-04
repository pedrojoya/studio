package es.iessaladillo.pedrojoya.pr111;

import com.facebook.stetho.Stetho;
import com.orm.SugarApp;

public class App extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

}
