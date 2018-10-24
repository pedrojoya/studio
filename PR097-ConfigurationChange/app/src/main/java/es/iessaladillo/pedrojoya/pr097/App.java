package es.iessaladillo.pedrojoya.pr097;

import android.app.Application;

import com.evernote.android.state.StateSaver;

@SuppressWarnings("WeakerAccess")
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        StateSaver.setEnabledForAllActivitiesAndSupportFragments(this, true);
    }

}
