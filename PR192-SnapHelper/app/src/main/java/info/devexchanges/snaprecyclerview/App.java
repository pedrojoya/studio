package info.devexchanges.snaprecyclerview;

public class App {

    private int resIdLogo;
    private String nombre;

    public App(String nombre, int resIdLogo) {
        this.resIdLogo = resIdLogo;
        this.nombre = nombre;
    }

    public int getResIdLogo() {
        return resIdLogo;
    }

    public String getNombre() {
        return nombre;
    }

}
