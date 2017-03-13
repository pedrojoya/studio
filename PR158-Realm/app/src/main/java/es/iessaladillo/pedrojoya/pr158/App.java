package es.iessaladillo.pedrojoya.pr158;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Se configura Realm. La BD se llamará instituto
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                //.name("instituto.realm")
                .build();
        Realm.setDefaultConfiguration(config);
        // Se configura Stetho para que trabaje con Realm.
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }

}
