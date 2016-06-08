package es.iessaladillo.pedrojoya.pr182.login;

public interface LoginRepository {

    void signIn(String email, String password);
    void signUp(String email, String password);
    void checkAuthenticated();

}
