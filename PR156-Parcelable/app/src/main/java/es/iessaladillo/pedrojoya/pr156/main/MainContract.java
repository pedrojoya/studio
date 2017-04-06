package es.iessaladillo.pedrojoya.pr156.main;

import android.content.Intent;
import android.support.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr156.base.BasePresenter;
import es.iessaladillo.pedrojoya.pr156.model.Alumno;

@SuppressWarnings("unused")
public interface MainContract {

    interface Presenter extends BasePresenter<View> {
        void doSolicitarDatos(Alumno alumno);

        void onSolicitarDatosResult(int resultCode, Intent data);
    }

    interface View {
        void navigateToSolicitar(@NonNull Alumno alumnoActual);

        void showDatos(@NonNull Alumno alumno);

        void showMensajeExito();
    }

}
