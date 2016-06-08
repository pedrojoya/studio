package es.iessaladillo.pedrojoya.pr182.login;

public class LoginInteractorImpl implements LoginInteractor {

    private LoginRepository mRepository;

    public LoginInteractorImpl() {
        mRepository = new LoginFirebaseRepository();
    }

    @Override
    public void doToSignIn(String email, String password) {
        mRepository.signIn(email, password);
    }

    @Override
    public void doToSignUp(String email, String password) {
        mRepository.signUp(email, password);
    }

    @Override
    public void doCheckAuthenticated() {
        mRepository.checkAuthenticated();
    }

}
