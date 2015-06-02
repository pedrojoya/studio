package es.iessaladillo.pedrojoya.pr140;

class Poblacion {

    private final String nombre;
    private final String codigo;

    public Poblacion(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

}
