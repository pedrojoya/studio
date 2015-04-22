package es.iessaladillo.pedrojoya.pr021;

// Clase modelo de datos de país
class Pais {

    // Propiedades.
    final int banderaResId;
    private final String nombre;

    // Constructores.
    public Pais(int banderaResId, String nombre) {
        this.banderaResId = banderaResId;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

}
