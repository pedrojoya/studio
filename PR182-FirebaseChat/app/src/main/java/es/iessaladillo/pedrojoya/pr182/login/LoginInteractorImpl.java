package es.iessaladillo.pedrojoya.pr182.login;

import android.support.annotation.VisibleForTesting;

public class LoginInteractorImpl implements LoginInteractor {

    private LoginRepository mRepository;

    public LoginInteractorImpl() {
        mRepository = new LoginFirebaseRepository();
    }

    @VisibleForTesting
    public LoginInteractorImpl(LoginRepository repository) {
        mRepository = repository;
    }

    @Override
    public void doSignIn(String email, String password) {
        mRepository.signIn(email, password);
    }

    @Override
    public void doSignUp(String email, String password) {
        mRepository.signUp(email, password);
    }

    @Override
    public void doCheckAuthenticated() {
        mRepository.checkAuthenticated();
    }

}
