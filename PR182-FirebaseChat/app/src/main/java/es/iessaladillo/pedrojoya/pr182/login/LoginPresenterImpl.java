package es.iessaladillo.pedrojoya.pr182.login;

import android.os.Handler;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView mView;

    @Override
    public void doSignIn() {
        if (mView != null) {
            mView.onLoadingStarted();
            // TODO Do sign in.
            new Handler().postDelayed(new Runnable() {
                @Override public void run() {
                    mView.onUserSignedIn();
                    mView.onLoadingFinished();
                }
            }, 2000);
        }
    }

    @Override
    public void doSignUp() {
        if (mView != null) {
            mView.onLoadingStarted();
            // TODO Do sign up.
            new Handler().postDelayed(new Runnable() {
                @Override public void run() {
                    mView.onUserSignedUp();
                    mView.onLoadingFinished();
                }
            }, 2000);
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
