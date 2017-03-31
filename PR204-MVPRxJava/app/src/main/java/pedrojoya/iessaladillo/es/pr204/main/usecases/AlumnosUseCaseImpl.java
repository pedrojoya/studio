package pedrojoya.iessaladillo.es.pr204.main.usecases;

import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import pedrojoya.iessaladillo.es.pr204.model.entity.Alumno;

public class AlumnosUseCaseImpl implements AlumnosUseCase {
    @Override
    public Single<List<Alumno>> getListaAlumnos() {
        Log.d("Mia", "Pidiendo datos");
//        return Single.<List<Alumno>>error(new Throwable("No se pudo acceder"));
        return Single.just(Arrays.asList(
                new Alumno("Baldomero", "Su casa", "http://lorempixel.com/100/100/sports/1/"),
                new Alumno("Germán Ginés", "Su casita", "http://lorempixel.com/100/100/sports/2/"),
                new Alumno("Lorenzo", "Su casaza", "http://lorempixel.com/100/100/sports/3/")))
                .delay(5, TimeUnit.SECONDS);
    }
}
