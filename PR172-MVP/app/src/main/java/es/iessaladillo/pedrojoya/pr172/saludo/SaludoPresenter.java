package es.iessaladillo.pedrojoya.pr172.saludo;

import android.support.annotation.NonNull;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr172.R;

public class SaludoPresenter implements SaludoContract.UserActionsListener {

    private SaludoContract.Repository mRepositorio;
    private SaludoContract.View mVista;

    public SaludoPresenter(@NonNull SaludoContract.Repository repositorio, @NonNull SaludoContract.View vista) {
        mRepositorio = repositorio;
        mVista = vista;
    }

    @Override
    public void onSaludar(String nombre, boolean educado) {
        // Se obtiene el saludo desde el repositorio.
        mRepositorio.getSaludo(nombre, educado, new SaludoContract.Repository.GetSaludoCallback() {
            @Override
            public void onSaludoLoaded(String mensaje) {
                // Se indica a la Vista que debe mostrar el mensaje.
                mVista.mostrarSaludo(mensaje);
            }
        });
    }

    @Override
    public void onCambiarModoSaludo(boolean educado) {
        // Se indica a la Vista que debe cambiar el texto del modo.
        mVista.cambiarTextoModo(educado);
    }

}
