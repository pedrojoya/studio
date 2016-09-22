package es.iessaladillo.pedrojoya.pr172.saludo;

import android.support.annotation.NonNull;

public class SaludoRepository implements SaludoContract.Repository {

    private static SaludoRepository instance = null;

    public static SaludoRepository getInstance() {
        if (instance == null) {
            instance = new SaludoRepository();
        }
        return instance;
    }

    private SaludoRepository() {
    }

    @Override
    public void getSaludo(String nombre, boolean educado, @NonNull GetSaludoCallback callback) {
        // Se llama al listener proporcionándole el mensaje.
        callback.onSaludoLoaded("Buenos días " + (educado ? "tenga usted " : "") + nombre);
    }

}
