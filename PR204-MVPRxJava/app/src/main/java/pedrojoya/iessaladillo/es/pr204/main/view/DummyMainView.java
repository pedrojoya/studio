package pedrojoya.iessaladillo.es.pr204.main.view;

import android.util.Log;

import java.util.List;

import pedrojoya.iessaladillo.es.pr204.model.entity.Alumno;

public class DummyMainView implements MainView {

    @Override
    public void mostrarListaAlumnos(List<Alumno> alumnos) {
        Log.d(getClass().getSimpleName(), "mostrarListAlumno con vista null");
    }

    @Override
    public void navegarNuevoAlumno() {
        Log.d(getClass().getSimpleName(), "navegarNuevoAlumno con vista null");
    }

    @Override
    public void navegarAlumno(Alumno alumno) {
        Log.d(getClass().getSimpleName(), "navegarAlumno con vista null");
    }

    @Override
    public void errorObtiendoAlumnos() {
        Log.d(getClass().getSimpleName(), "errorObtiendoAlumnos con vista null");
    }

}
