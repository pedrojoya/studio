package es.iessaladillo.pedrojoya.pr182.login;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView mView;

    @Override
    public void doSignIn() {
        if (mView != null) {
            // TODO Do sign in.
            mView.onUserSignedIn();
        }
    }

    @Override
    public void doSignUp() {
        if (mView != null) {
            // TODO Do sign up.
            mView.onUserSignedUp();
        }
    }

    @Override
    public void onViewAttached(LoginView view) {
        mView = view;
    }

    @Override
    public void onViewDetached() {
        mView = null;
    }

    @Override
    public void onViewDestroyed() {

    }

}
