package es.iessaladillo.pedrojoya.pr109.Model;

public class UsuarioNuevo {
    private String username;
    private String password;

    public UsuarioNuevo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

}
