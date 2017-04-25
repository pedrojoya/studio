package es.iessaladillo.pedrojoya.pr158;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import es.iessaladillo.pedrojoya.pr158.db.DbMigration;
import es.iessaladillo.pedrojoya.pr158.db.entities.Asignatura;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    private static final long DB_VERSION = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        // Se configura Realm.
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                //.name("instituto.realm")
                .schemaVersion(DB_VERSION)
                .initialData(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        addInitialData(realm);
                    }
                })
                .migration(new DbMigration())
                .build();
        Realm.setDefaultConfiguration(config);
        // Se configura Stetho para que trabaje con Realm.
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }

    private void addInitialData(Realm realm) {
        Asignatura asignatura = new Asignatura();
        asignatura.setId("PMDMO");
        asignatura.setNombre("Android");
        realm.copyToRealmOrUpdate(asignatura);
        Asignatura asignatura2 = new Asignatura();
        asignatura2.setId("PSPRO");
        asignatura2.setNombre("Multihilo");
        realm.copyToRealmOrUpdate(asignatura2);
        Asignatura asignatura3 = new Asignatura();
        asignatura3.setId("HLC");
        asignatura3.setNombre("Horas de libre configuración");
        realm.copyToRealmOrUpdate(asignatura3);
    }

}
