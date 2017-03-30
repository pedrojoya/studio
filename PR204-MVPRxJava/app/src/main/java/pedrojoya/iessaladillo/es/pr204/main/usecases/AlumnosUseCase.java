package pedrojoya.iessaladillo.es.pr204.main.usecases;

import java.util.List;

import io.reactivex.Single;
import pedrojoya.iessaladillo.es.pr204.model.entity.Alumno;

public interface AlumnosUseCase {

    Single<List<Alumno>> getListaAlumnos();

}
