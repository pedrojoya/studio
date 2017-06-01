package es.iessaladillo.pedrojoya.pr203;

import android.app.Application;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

@SuppressWarnings({"WeakerAccess", "unused"})
public class App extends Application {

    private BoxStore mBoxStore;
    private Box<Alumno> mAlumnoBox;
    private Box<Asignatura> mAsignaturasBox;

    @Override
    public void onCreate() {
        super.onCreate();
        mBoxStore = MyObjectBox.builder().androidContext(App.this).build();
        mAsignaturasBox = mBoxStore.boxFor(Asignatura.class);
        createAsignaturas();
    }

    public BoxStore getBoxStore() {
        return mBoxStore;
    }

    private void createAsignaturas() {
        Query<Asignatura> queryAsignatura = mAsignaturasBox.query()
                .equal(Asignatura_.nombre, "")
                .build();
        Asignatura asignatura;
        if (queryAsignatura.setParameter(Asignatura_.nombre, "PMDMO").findFirst() == null) {
            asignatura = new Asignatura();
            asignatura.setNombre("PMDMO");
            mAsignaturasBox.put(asignatura);
        }
        if (queryAsignatura.setParameter(Asignatura_.nombre, "PSPRO").findFirst() == null) {
            asignatura = new Asignatura();
            asignatura.setNombre("PSPRO");
            mAsignaturasBox.put(asignatura);
        }
        if (queryAsignatura.setParameter(Asignatura_.nombre, "HLC").findFirst() == null) {
            asignatura = new Asignatura();
            asignatura.setNombre("HLC");
            mAsignaturasBox.put(asignatura);
        }
    }

}
