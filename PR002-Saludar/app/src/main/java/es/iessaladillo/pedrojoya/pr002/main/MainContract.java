package es.iessaladillo.pedrojoya.pr002.main;

public interface MainContract {

    interface Presenter {
        void doSaludar(String nombre, boolean educado);
    }

    interface View {
        void saludar(String nombre);
        void saludarEducado(String nombre);
    }

}
