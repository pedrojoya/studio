package es.iessaladillo.pedrojoya.pr156.editalumno;

import android.content.Intent;
import android.support.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr156.base.BasePresenter;
import es.iessaladillo.pedrojoya.pr156.model.Alumno;

@SuppressWarnings("unused")
public interface EditAlumnoContract {

    interface Presenter extends BasePresenter<View> {
        void doRetornarDatos(@NonNull Alumno alumno);
    }

    interface View {
        void returnIntent(Intent resultIntent);

        void showDatos(@NonNull Alumno alumno);
    }

}
