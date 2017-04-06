package es.iessaladillo.pedrojoya.PR004.main;

import android.net.Uri;

@SuppressWarnings("unused")
public interface MainContract {

    interface Presenter {
        void doNavegar();

        void doBuscar();

        void doMarcar();

        void doMostrarEnMapa();

        void doBuscarEnMapa();

        void doMostrarContactos();

        void doLlamar();
    }

    @SuppressWarnings("SameParameterValue")
    interface View {
        void showWeb(Uri uri);

        void showBuscar(String texto);

        void showMarcar(String tel);

        void showPosicionEnMapa(double longitud, double latitud, int zoom);

        void showBusquedaEnMapa(String texto);

        void showContactos();

        void showLlamar(String tel);
    }

}
