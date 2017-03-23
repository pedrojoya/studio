package es.iessaladillo.pedrojoya.pr158;

import android.app.Application;

import io.objectbox.BoxStore;

public class App extends Application {

    private BoxStore mBoxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        mBoxStore = MyObjectBox.builder().androidContext(App.this).build();
        mBoxStore.isClosed();
    }

    public BoxStore getBoxStore() {
        return mBoxStore;
    }

}
