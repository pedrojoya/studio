package es.iessaladillo.pedrojoya.pr182.login;

import android.support.annotation.VisibleForTesting;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import es.iessaladillo.pedrojoya.pr182.login.events.AuthenticatedEvent;
import es.iessaladillo.pedrojoya.pr182.login.events.ErrorSigningInEvent;
import es.iessaladillo.pedrojoya.pr182.login.events.ErrorSigningUpEvent;
import es.iessaladillo.pedrojoya.pr182.login.events.SignedInEvent;
import es.iessaladillo.pedrojoya.pr182.login.events.SignedUpEvent;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView mView;
    private final LoginInteractor mInteractor;
    private final EventBus mEventBus;

    public LoginPresenterImpl() {
        mInteractor = new LoginInteractorImpl();
        mEventBus = EventBus.getDefault();
    }

    @VisibleForTesting
    public LoginPresenterImpl(LoginView view, LoginInteractor interactor, EventBus eventBus) {
        mView = view;
        mInteractor = interactor;
        mEventBus = eventBus;
    }

    @Override
    public void wantToSignIn(String email, String password) {
        if (mView != null) {
            mView.onLoadingStarted();
        }
        mInteractor.doSignIn(email, password);
    }

    @Override
    public void wantToSignUp(String email, String password) {
        if (mView != null) {
            mView.onLoadingStarted();
        }
        mInteractor.doSignUp(email, password);
    }

    @Override
    public void wantToCheckAuthenticated() {
        if (mView != null) {
            mView.onLoadingStarted();
        }
        mInteractor.doCheckAuthenticated();
    }

    @Override
    public void onViewAttached(LoginView view) {
        mView = view;
        mEventBus.register(this);
    }

    @Override
    public void onViewDetached() {
        mView = null;
        mEventBus.unregister(this);
    }

    @Override
    public void onViewDestroyed() {

    }

    @Subscribe
    public void onSignedIn(SignedInEvent event){
        if (mView != null) {
            mView.onLoadingFinished();
            mView.showUserHasSignedIn();
            mView.navigateToContactsActivity();
        }
    }

    @Subscribe
    public void onErrorSigningIn(ErrorSigningInEvent event){
        if (mView != null) {
            mView.onLoadingFinished();
            mView.showErrorSigningIn(event.getErrorMessage());
        }
    }

    @Subscribe
    public void onSignedUp(SignedUpEvent event){
        if (mView != null) {
            mView.onLoadingFinished();
            mView.showUserHasSignedUp();
            mView.navigateToContactsActivity();
        }
    }

    @Subscribe
    public void onErrorSigningUp(ErrorSigningUpEvent event){
        if (mView != null) {
            mView.onLoadingFinished();
            mView.showErrorSigningUp(event.getErrorMessage());
        }
    }

    @Subscribe
    public void onAuthenticated(AuthenticatedEvent event){
        if (mView != null) {
            mView.onLoadingFinished();
            mView.navigateToContactsActivity();
        }
    }

}
