package es.iessaladillo.pedrojoya.pr172.saludo;

import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr172.R;

public class SaludoPresenter implements SaludoContract.UserActionsListener {

    public SaludoPresenter(SaludoContract.View vista) {
        mVista = vista;
    }

    private SaludoContract.View mVista;

    @Override
    public void onSaludar(String nombre, boolean educado) {
        // Se crea el mensaje a mostrar.
        String mensaje = mVista.getContext().getString(R.string.buenos_dias,
                educado?mVista.getContext().getString(R.string.tenga_usted) + " ":"",
                nombre);
        // Se indica a la Vista que debe mostrar el mensaje.
        mVista.mostrarSaludo(mensaje);
    }

    @Override
    public void onCambiarModoSaludo(boolean eduacado) {
        // Se crea el texto a mostrar.
        String texto = eduacado?mVista.getContext().getString(R.string.saludar_educadamente):
                mVista.getContext().getString(R.string.saludar_normal);
        // Se indica a la Vista que debe cambiar el texto del modo.
        mVista.cambiarTextoModo(texto);
    }

}
