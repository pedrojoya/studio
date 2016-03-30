package es.iessaladillo.pedrojoya.pr172.saludo;

import android.content.Context;

public interface SaludoContract {

    // Interfaz de comunicación desde la Vista con el Presentador.
    interface UserActionsListener {
        // Se ha solicitado saludar.
        void onSaludar(String nombre, boolean educado);
        //Se ha solicitado cambiar el modo de saludo.
        void onCambiarModoSaludo(boolean eduacado);
    }

    // Interfaz de comunicación desde el Presentador con la Vista.
    interface View {
        // Para obtener el contexto de la vista (para poder acceder a recursos).
        Context getContext();
        // Para mostrar el mensaje de saludo.
        void mostrarSaludo(String mensaje);
        // Para cambiar el texto del modo de saludo.
        void cambiarTextoModo(String texto);
    }

}
