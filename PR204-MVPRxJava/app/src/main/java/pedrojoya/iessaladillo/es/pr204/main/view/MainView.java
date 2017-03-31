package pedrojoya.iessaladillo.es.pr204.main.view;

import java.util.List;

import pedrojoya.iessaladillo.es.pr204.model.entity.Alumno;

@SuppressWarnings("UnusedParameters")
public interface MainView {

    void mostrarListaAlumnos(List<Alumno> alumnos);
    void navegarNuevoAlumno();
    void navegarAlumno(Alumno alumno);
    void errorObtiendoAlumnos();
}
