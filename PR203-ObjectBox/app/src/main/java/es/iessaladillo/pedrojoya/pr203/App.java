package es.iessaladillo.pedrojoya.pr203;

import android.app.Application;

import es.iessaladillo.pedrojoya.pr203.data.model.MyObjectBox;
import io.objectbox.BoxStore;

public class App extends Application {

    private BoxStore boxstore;

    @Override
    public void onCreate() {
        super.onCreate();
        boxstore = MyObjectBox.builder().androidContext(App.this).build();
    }

    public BoxStore getBoxStore() {
        return boxstore;
    }

}
