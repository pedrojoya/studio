package es.iessaladillo.pedrojoya.pr002.main;

@SuppressWarnings("unused")
public interface MainContract {

    interface Presenter {
        void doSaludar(String nombre, boolean educado);

        void doCambiarEstadoEducado(boolean isEducado);
    }

    interface View {
        void saludar(String nombre);

        void saludarEducado(String nombre);

        void mostrarTextoModoEducado();

        void mostrarTextoModoNoEducado();
    }

}
