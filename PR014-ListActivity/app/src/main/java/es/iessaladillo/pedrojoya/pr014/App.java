package es.iessaladillo.pedrojoya.pr014;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

@SuppressWarnings("WeakerAccess")
public class App extends Application {

    // Habilita la posibilidad de usar VectorDrawables dentro de
    // contenedores Drawable.
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

}
