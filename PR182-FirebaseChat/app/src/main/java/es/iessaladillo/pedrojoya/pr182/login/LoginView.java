package es.iessaladillo.pedrojoya.pr182.login;

import es.iessaladillo.pedrojoya.pr182.utils.LoadingView;

public interface LoginView extends LoadingView {

    void showUserHasSignedIn();
    void showUserHasSignedUp();
    void showErrorSigningIn(String errorMessage);
    void showErrorSigningUp(String errorMessage);
    void navigateToContactsActivity();

}
