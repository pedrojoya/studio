package es.iessaladillo.pedrojoya.pr172.saludo;

import android.support.annotation.NonNull;

public interface SaludoContract {

    // Interfaz de comunicación desde la Vista con el Presentador.
    interface UserActionsListener {
        // Se ha solicitado saludar.
        void onSaludar(String nombre, boolean educado);

        // Se ha solicitado cambiar el modo de saludo.
        void onCambiarModoSaludo(boolean eduacado);
    }

    // Interfaz de comunicación desde el Presentador con la Vista.
    interface View {
        // Para mostrar el saludo.
        void mostrarSaludo(String mensaje);

        // Para cambiar el texto del modo de saludo.
        void cambiarTextoModo(boolean educado);
    }

    // Interfaz de comunicación entre el Presentador y el Repositorio.
    interface Repository {
        // Callback que debe implementar el Presentador y que será llamador por el Repositorio.
        interface GetSaludoCallback {
            void onSaludoLoaded(String mensaje);
        }

        // Para obtener el saludo (desde el Presentador al Repositorio)
        void getSaludo(String nombre, boolean educado, @NonNull GetSaludoCallback callback);
    }

}
