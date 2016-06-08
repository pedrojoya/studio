package es.iessaladillo.pedrojoya.pr182.login;

import es.iessaladillo.pedrojoya.pr182.utils.BasePresenter;

public interface LoginPresenter extends BasePresenter<LoginView> {

    void wantToSignIn(String email, String password);
    void wantToSignUp(String email, String password);
    void wantToCheckAuthenticated();

}
