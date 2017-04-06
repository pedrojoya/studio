package es.iessaladillo.pedrojoya.pr005.main;

import android.content.Intent;

import es.iessaladillo.pedrojoya.pr005.editalumno.EditAlumnoActivity;

import static android.app.Activity.RESULT_OK;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        this.mView = view;
    }


    @Override
    public void doSolicitarDatos(String nombreActual, int edadActual) {
        mView.navigateToSolicitar(nombreActual, edadActual);
    }

    @Override
    public void onSolicitarDatosResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            mView.showDatos(EditAlumnoActivity.getNombreFromIntent(data), EditAlumnoActivity
                    .getEdadFromIntent(data));
        } else {
            mView.showErrorAlSolicitarDatos();
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
