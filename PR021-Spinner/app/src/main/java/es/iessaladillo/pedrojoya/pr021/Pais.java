package es.iessaladillo.pedrojoya.pr021;

// Clase modelo de datos de país
class Pais {

    // Propiedades.
    int banderaResId;
    private String nombre;

    // Constructores.
    public Pais(int banderaResId, String nombre) {
        this.banderaResId = banderaResId;
        this.nombre = nombre;
    }

    public Pais() {
    }

    // Getters y Setters.
    public int getBanderaResId() {
        return banderaResId;
    }

    public void setBanderaResId(int banderaResId) {
        this.banderaResId = banderaResId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
