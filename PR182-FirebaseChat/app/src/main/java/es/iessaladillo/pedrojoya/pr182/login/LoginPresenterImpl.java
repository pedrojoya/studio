package es.iessaladillo.pedrojoya.pr182.login;

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

    @Override
    public void wantToSignIn(String email, String password) {
        if (mView != null) {
            mView.onLoadingStarted();
            mInteractor.doToSignIn(email, password);
        }
    }

    @Override
    public void wantToSignUp(String email, String password) {
        if (mView != null) {
            mView.onLoadingStarted();
            mInteractor.doToSignUp(email, password);
        }
    }

    @Override
    public void wantToCheckAuthenticated() {
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
    public void onEvent(SignedInEvent event){
        if (mView != null) {
            mView.onLoadingFinished();
            mView.showUserHasSignedIn();
            mView.navigateToContactsActivity();
        }
    }

    @Subscribe
    public void onEvent(ErrorSigningInEvent event){
        if (mView != null) {
            mView.onLoadingFinished();
            mView.showErrorSigningIn(event.getErrorMessage());
        }
    }

    @Subscribe
    public void onEvent(SignedUpEvent event){
        if (mView != null) {
            mView.onLoadingFinished();
            mView.showUserHasSignedUp();
        }
    }

    @Subscribe
    public void onEvent(ErrorSigningUpEvent event){
        if (mView != null) {
            mView.onLoadingFinished();
            mView.showErrorSigningUp(event.getErrorMessage());
        }
    }

    @Subscribe
    public void onEvent(AuthenticatedEvent event){
        if (mView != null) {
            mView.navigateToContactsActivity();
        }
    }


}
