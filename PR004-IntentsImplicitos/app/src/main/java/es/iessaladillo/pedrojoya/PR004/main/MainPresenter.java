package es.iessaladillo.pedrojoya.PR004.main;

import android.net.Uri;

import es.iessaladillo.pedrojoya.PR004.main.MainContract.Presenter;

public class MainPresenter implements Presenter {

    @SuppressWarnings("CanBeFinal")
    private MainContract.View mView;

    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void doNavegar() {
        mView.showWeb(Uri.parse("http://www.genbeta.com"));
    }

    @Override
    public void doBuscar() {
        mView.showBuscar("IES Saladillo");
    }

    @Override
    public void doMarcar() {
        mView.showMarcar("(+34)12345789");
    }

    @Override
    public void doMostrarEnMapa() {
        mView.showPosicionEnMapa(36.1121, -5.44347, 19);
    }

    @Override
    public void doBuscarEnMapa() {
        mView.showBusquedaEnMapa("duque de rivas, Algeciras");
    }

    @Override
    public void doMostrarContactos() {
        mView.showContactos();
    }

    @Override
    public void doLlamar() {
        mView.showLlamar("(+34)123456789");
    }

}
