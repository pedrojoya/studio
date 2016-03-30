package es.iessaladillo.pedrojoya.pr172.saludo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

// Tests unitarios locales para el Presentador
public class SaludoPresenterTest {

    // Como el objeto Presentador requiere de la vista, usamos Mockito para simularla.
    @Mock
    private SaludoContract.View mVista;
    private SaludoPresenter mPresentador;

    // Antes de ejecutar cualquier test hay que crear el objeto Presentador
    @Before
    public void setupSaludoPresenter() {
        // Se inicializan los objetos mock anotados con @Mock.
        MockitoAnnotations.initMocks(this);
        // Se crea el objeto Presentador.
        mPresentador = new SaludoPresenter(mVista);
    }

    // Test para comprobar que se muestra el saludo en modo normal.
    @Test
    public void saludoMostrado() {
        // Se llama al método del presentador poara mostrar saludo.
        mPresentador.onSaludar("Baldomero", false);
        // Se comprueba que el método de la vista para mostrar el saludo es llamado.
        verify(mVista).mostrarSaludo("Buenos días Baldomero");
    }

    // Test para comprobar que se muestra el saludo en modo educado.
    @Test
    public void saludoEducadoMostrado() {
        // Se llama al método del presentador poara mostrar saludo.
        mPresentador.onSaludar("Baldomero", true);
        // Se comprueba que el método de la vista para mostrar el saludo es llamado.
        verify(mVista).mostrarSaludo("Buenos días tenga usted Baldomero");
    }

}
