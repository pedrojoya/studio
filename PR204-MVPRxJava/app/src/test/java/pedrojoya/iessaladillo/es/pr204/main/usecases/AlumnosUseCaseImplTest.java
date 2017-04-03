package pedrojoya.iessaladillo.es.pr204.main.usecases;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import pedrojoya.iessaladillo.es.pr204.model.entity.Alumno;

import static org.junit.Assert.*;

public class AlumnosUseCaseImplTest {

    private AlumnosUseCaseImpl mAlumnosUseCase;
    private CompositeDisposable mSubscripciones;

    @Before
    public void setUp() throws Exception {
        mAlumnosUseCase = new AlumnosUseCaseImpl();
        mSubscripciones = new CompositeDisposable();
    }

    @Test
    public void getListaAlumnos() throws Exception {
        // when.
        TestObserver<List<Alumno>> observer = mAlumnosUseCase.getListaAlumnos().test();
        // then.
        observer.awaitTerminalEvent();
        observer.assertNoErrors();
        observer.assertValueCount(1);
        assertEquals(observer.values().get(0).get(0).getNombre(), "Baldomero");
    }

}