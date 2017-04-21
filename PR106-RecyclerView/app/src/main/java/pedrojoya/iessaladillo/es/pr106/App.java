package pedrojoya.iessaladillo.es.pr106;

import android.app.Application;

import com.mooveit.library.Fakeit;

import java.util.Locale;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fakeit.initWithLocale(getApplicationContext(), new Locale("es", "ES"));
    }

}
