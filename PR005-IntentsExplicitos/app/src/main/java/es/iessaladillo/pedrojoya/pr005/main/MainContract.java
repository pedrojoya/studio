package es.iessaladillo.pedrojoya.pr005.main;

import android.content.Intent;

import es.iessaladillo.pedrojoya.pr005.base.BasePresenter;

@SuppressWarnings("unused")
public interface MainContract {

    interface Presenter extends BasePresenter<View> {
        void doSolicitarDatos(String nombreActual, int edadActual);

        void onSolicitarDatosResult(int resultCode, Intent data);
    }

    interface View {
        void navigateToSolicitar(String nombreActual, int edadActual);

        void showDatos(String nombre, int edad);

        void showErrorAlSolicitarDatos();
    }

}
