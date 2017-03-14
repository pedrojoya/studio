package es.iessaladillo.pedrojoya.pr172.saludo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

// Tests unitarios locales para el Presentador
@SuppressWarnings({"CanBeFinal", "Convert2Lambda"})
public class SaludoPresenterTest {

    // Como el objeto Presentador requiere de la vista, usamos Mockito para simularla.
    @Mock
    private SaludoContract.View mVista;
    @Mock
    private SaludoContract.Repository mRepositorio;

    private SaludoPresenter mPresentador;

    // Antes de ejecutar cualquier test hay que crear el objeto Presentador
    @Before
    public void setupSaludoPresenter() {
        // Se inicializan los objetos mock anotados con @Mock.
        MockitoAnnotations.initMocks(this);
        // Se crea el objeto Presentador.
        mPresentador = new SaludoPresenter(mRepositorio, mVista);
    }

    // Test para comprobar que se muestra el saludo en modo normal solicitando el saludo al
    // repositorio.
    @Test
    public void saludoMostrado() {
        // Se hace que cuando se llame al método getSaludo del repositorio se llame al método
        // onSaludoLoaded del callback con el que ha sido llamado.
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((SaludoContract.Repository.GetSaludoCallback) invocation.getArguments()[2])
                        .onSaludoLoaded(
                        "Buenos días Baldomero");
                return null;
            }
        }).when(mRepositorio).getSaludo("Baldomero", false, mPresentador);
        // then
        mPresentador.onSaludar("Baldomero", false);
        // verify
        verify(mRepositorio).getSaludo("Baldomero", false, mPresentador);
        verify(mVista).mostrarSaludo("Buenos días Baldomero");
    }

    // Test para comprobar que se muestra el saludo en modo educado solicitando el saludo
    // al respositorio.
    @Test
    public void saludoEducadoMostrado() {
        // Se hace que cuando se llame al método getSaludo del repositorio se llame al método
        // onSaludoLoaded del callback con el que ha sido llamado.
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((SaludoContract.Repository.GetSaludoCallback) invocation.getArguments()[2])
                        .onSaludoLoaded(
                                "Buenos días tenga usted Baldomero");
                return null;
            }
        }).when(mRepositorio).getSaludo("Baldomero", true, mPresentador);
        // then
        mPresentador.onSaludar("Baldomero", true);
        // verify
        verify(mRepositorio).getSaludo("Baldomero", true, mPresentador);
        verify(mVista).mostrarSaludo("Buenos días tenga usted Baldomero");
    }

}
