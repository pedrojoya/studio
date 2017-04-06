package es.iessaladillo.pedrojoya.pr156.editalumno;

import android.support.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr156.model.Alumno;

public class EditAlumnoPresenter implements EditAlumnoContract.Presenter {

    private EditAlumnoContract.View mView;
    private final Alumno mAlumnoInicial;
    private final boolean mDebeMostrarDatosIniciales;

    public EditAlumnoPresenter(EditAlumnoContract.View view, Alumno alumnoInicial,
            boolean debeMostrarDatosIniciales) {
        mView = view;
        mAlumnoInicial = alumnoInicial;
        mDebeMostrarDatosIniciales = debeMostrarDatosIniciales;
    }

    @Override
    public void doRetornarDatos(@NonNull Alumno alumno) {
        mView.returnIntent(EditAlumnoActivity.createResultIntent(alumno));
    }

    @Override
    public void onViewAttach(EditAlumnoContract.View view) {
        mView = view;
        if (mDebeMostrarDatosIniciales) {
            mView.showDatos(mAlumnoInicial);
        }
    }

    @Override
    public void onViewDetach() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public EditAlumnoContract.View getView() {
        return mView;
    }

}
