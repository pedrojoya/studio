package info.devexchanges.snaprecyclerview;

class App {

    private final int resIdLogo;
    private final String nombre;

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
