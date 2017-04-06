package es.iessaladillo.pedrojoya.pr005.editalumno;

public class EditAlumnoPresenter implements EditAlumnoContract.Presenter {

    private EditAlumnoContract.View mView;
    private final String mNombreInicial;
    private final int mEdadInicial;
    private final boolean mMostrarDatosIniciales;

    public EditAlumnoPresenter(EditAlumnoContract.View view, String nombreInicial, int edadInicial,
            boolean mostrarDatosIniciales) {
        mView = view;
        mNombreInicial = nombreInicial;
        mEdadInicial = edadInicial;
        mMostrarDatosIniciales = mostrarDatosIniciales;
    }

    @Override
    public void doRetornarDatos(String nombre, int edad) {
        mView.returnIntent(EditAlumnoActivity.createResultIntent(nombre, edad));
    }

    @Override
    public void onViewAttach(EditAlumnoContract.View view) {
        mView = view;
        if (mMostrarDatosIniciales) {
            mView.showDatos(mNombreInicial, mEdadInicial);
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
