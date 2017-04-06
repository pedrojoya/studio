package es.iessaladillo.pedrojoya.pr002.main;

import es.iessaladillo.pedrojoya.pr002.utils.StringUtils;

class MainPresenter implements MainContract.Presenter {

    @SuppressWarnings("CanBeFinal")
    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        mView = view;
    }

    @Override
    public void doSaludar(String nombre, boolean educado) {
        if (educado) {
            mView.saludarEducado(StringUtils.capitalizeFirstLetter(nombre));
        } else {

            mView.saludar(StringUtils.capitalizeFirstLetter(nombre));
        }
    }

    @Override
    public void doCambiarEstadoEducado(boolean isEducado) {
        if (isEducado) {
            mView.mostrarTextoModoEducado();
        } else {
            mView.mostrarTextoModoNoEducado();
        }
    }

}
