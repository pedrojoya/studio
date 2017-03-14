package es.iessaladillo.pedrojoya.pr172.saludo;

import android.support.annotation.NonNull;

public class SaludoPresenter implements SaludoContract.Presenter, SaludoContract.Repository.GetSaludoCallback {

    private final SaludoContract.Repository mRepositorio;
    private final SaludoContract.View mVista;

    public SaludoPresenter(@NonNull SaludoContract.Repository repositorio, @NonNull
            SaludoContract.View vista) {
        mRepositorio = repositorio;
        mVista = vista;
    }

    @Override
    public void onSaludar(String nombre, boolean educado) {
        // Se obtiene el saludo desde el repositorio.
        mRepositorio.getSaludo(nombre, educado, this);
    }

    @Override
    public void onCambiarModoSaludo(boolean educado) {
        // Se indica a la Vista que debe cambiar el texto del modo.
        mVista.cambiarTextoModo(educado);
    }

    @Override
    public void onSaludoLoaded(String mensaje) {
        mVista.mostrarSaludo(mensaje);
    }

}
