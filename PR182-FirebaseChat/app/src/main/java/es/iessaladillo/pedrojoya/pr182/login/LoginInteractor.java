package es.iessaladillo.pedrojoya.pr182.login;

import es.iessaladillo.pedrojoya.pr182.utils.BasePresenter;

public interface LoginInteractor {

    void doSignIn(String email, String password);
    void doSignUp(String email, String password);
    void doCheckAuthenticated();

}
