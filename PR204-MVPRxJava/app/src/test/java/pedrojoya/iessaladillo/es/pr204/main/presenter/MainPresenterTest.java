package pedrojoya.iessaladillo.es.pr204.main.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;
import pedrojoya.iessaladillo.es.pr204.main.usecases.AlumnosUseCase;
import pedrojoya.iessaladillo.es.pr204.main.view.MainView;
import pedrojoya.iessaladillo.es.pr204.model.entity.Alumno;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    MainPresenter mMainPresenter;
    @Mock
    MainView mMainView;
    @Mock
    AlumnosUseCase mAlumnosUseCase;
    @Mock
    Alumno mAlumno;

    @Before
    public void setUp() throws Exception {
        mMainPresenter = new MainPresenter(mMainView, mAlumnosUseCase);
    }

    // Comprueba que cuando la vista nos informa de que ya está disponibe, el presentador
    // guarda la referencia.
    @Test
    public void onViewAttach() throws Exception {
        // given.
        mMainPresenter = new MainPresenter(null, null);
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
        assertEquals(mMainPresenter.getView(), null);
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
        Mockito.when(mAlumnosUseCase.getListaAlumnos()).thenReturn(Single.<List<Alumno>>error(new
                Throwable("Error al obtener los alummos")));
        // when.
        mMainPresenter.obtenerAlumnos();
        // then.
        verify(mAlumnosUseCase).getListaAlumnos();
        verify(mMainView).errorObtiendoAlumnos();
    }

    @Test
    public void obtenerAlumnosExito() throws Exception {
        // given
        List<Alumno> alumnos = Arrays.asList(new Alumno("1", "1", "1"));
        Mockito.when(mAlumnosUseCase.getListaAlumnos()).thenReturn(Single.just(alumnos));
        // when.
        mMainPresenter.obtenerAlumnos();
        // then.
        verify(mAlumnosUseCase).getListaAlumnos();
        verify(mMainView).mostrarListaAlumnos(alumnos);
    }

}