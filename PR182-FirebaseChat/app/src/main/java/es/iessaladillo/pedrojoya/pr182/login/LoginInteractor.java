package es.iessaladillo.pedrojoya.pr182.login;

import es.iessaladillo.pedrojoya.pr182.utils.BasePresenter;

public interface LoginInteractor {

    void doToSignIn(String email, String password);
    void doToSignUp(String email, String password);
    void doCheckAuthenticated();

}
