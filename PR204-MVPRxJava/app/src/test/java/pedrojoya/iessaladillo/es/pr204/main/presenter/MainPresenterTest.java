package pedrojoya.iessaladillo.es.pr204.main.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import pedrojoya.iessaladillo.es.pr204.main.usecases.AlumnosUseCase;
import pedrojoya.iessaladillo.es.pr204.main.view.DummyMainView;
import pedrojoya.iessaladillo.es.pr204.main.view.MainView;
import pedrojoya.iessaladillo.es.pr204.model.entity.Alumno;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings({"CanBeFinal", "EmptyMethod"})
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    private MainPresenter mMainPresenter;
    @Mock
    MainView mMainView;
    @Mock
    AlumnosUseCase mAlumnosUseCase;
    @Mock
    Alumno mAlumno;
    private CompositeDisposable mSubscripciones;

    @Before
    public void setUp() throws Exception {
        mSubscripciones = new CompositeDisposable();
        mMainPresenter = new MainPresenter(mMainView, mAlumnosUseCase, Schedulers.trampoline(),
                Schedulers.trampoline(), mSubscripciones);
    }

    // Comprueba que cuando la vista nos informa de que ya está disponible, el presentador
    // guarda la referencia.
    @Test
    public void onViewAttach() throws Exception {
        // given.
        mMainPresenter = new MainPresenter(null, null, Schedulers.trampoline(),
                Schedulers.trampoline(), mSubscripciones);
        // when.
        mMainPresenter.onViewAttach(mMainView);
        // then.
        assertEquals(mMainView, mMainPresenter.getView());
    }

    @Test
    public void onViewDetach() throws Exception {
        // when.
        mMainPresenter.onViewDetach();
        // then.
        assertThat(mMainPresenter.getView(), instanceOf(DummyMainView.class));
    }

    @Test
    public void onDestroy() throws Exception {

    }

    @Test
    public void agregarAlumno() throws Exception {
        // when
        mMainPresenter.agregarAlumno();
        // then
        verify(mMainView).navegarNuevoAlumno();
    }

    @Test
    public void actualizarAlumno() throws Exception {
        // when
        mMainPresenter.actualizarAlumno(mAlumno);
        // then
        verify(mMainView).navegarAlumno(mAlumno);
    }

    @Test
    public void obtenerAlumnosError() throws Exception {
        // given
        when(mAlumnosUseCase.getListaAlumnos()).thenReturn(
                Single.error(new Throwable("Error al obtener los alummos")));
        // when.
        mMainPresenter.obtenerAlumnos();
        // then.
        verify(mAlumnosUseCase, times(1)).getListaAlumnos();
        verify(mMainView, times(1)).errorObtiendoAlumnos();
        verify(mMainView, never()).mostrarListaAlumnos(any());
    }

    @Test
    public void obtenerAlumnosExito() throws Exception {
        // given
        List<Alumno> alumnos = Collections.singletonList(new Alumno("1", "1", "1"));
        when(mAlumnosUseCase.getListaAlumnos()).thenReturn(Single.just(alumnos));
        // when.
        mMainPresenter.obtenerAlumnos();
        // then.
        verify(mAlumnosUseCase, times(1)).getListaAlumnos();
        verify(mMainView, times(1)).mostrarListaAlumnos(alumnos);
        verify(mMainView, never()).errorObtiendoAlumnos();
    }

}