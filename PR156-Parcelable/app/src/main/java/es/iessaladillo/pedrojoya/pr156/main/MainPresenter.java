package es.iessaladillo.pedrojoya.pr156.main;

import android.content.Intent;
import android.support.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr156.editalumno.EditAlumnoActivity;
import es.iessaladillo.pedrojoya.pr156.model.Alumno;

import static android.app.Activity.RESULT_OK;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        this.mView = view;
    }


    @Override
    public void doSolicitarDatos(@NonNull Alumno alumnoActual) {
        mView.navigateToSolicitar(alumnoActual);
    }

    @Override
    public void onSolicitarDatosResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            mView.showDatos(EditAlumnoActivity.getAlumnoFromIntent(data));
            mView.showMensajeExito();
        }
    }

    @Override
    public void onViewAttach(MainContract.View view) {
        mView = view;
    }

    @Override
    public void onViewDetach() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public MainContract.View getView() {
        return null;
    }

}
