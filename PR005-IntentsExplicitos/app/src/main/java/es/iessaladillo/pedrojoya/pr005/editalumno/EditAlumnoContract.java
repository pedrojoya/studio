package es.iessaladillo.pedrojoya.pr005.editalumno;

import android.content.Intent;

import es.iessaladillo.pedrojoya.pr005.base.BasePresenter;

@SuppressWarnings("unused")
public interface EditAlumnoContract {

    interface Presenter extends BasePresenter<View> {
        void doRetornarDatos(String s, int edad);
    }

    interface View {
        void returnIntent(Intent resultIntent);

        void showDatos(String nombre, int edad);
    }

}
