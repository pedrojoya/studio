package es.iessaladillo.pedrojoya.pr182.login;

import es.iessaladillo.pedrojoya.pr182.utils.LoadingView;

public interface LoginView extends LoadingView {

    void onUserSignedIn();
    void onUserSignedUp();
    void onErrorSigningIn();
    void onErrorSingingUp();
    void navigateToContactsActivity();

}
