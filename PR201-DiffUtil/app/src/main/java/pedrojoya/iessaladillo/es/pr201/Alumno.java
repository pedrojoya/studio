package pedrojoya.iessaladillo.es.pr201;

// Modelo Alumno.
class Alumno {

    private final String nombre;
    private final String direccion;
    private final String urlFoto;

    public Alumno(String nombre, String direccion, String urlFoto) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.urlFoto = urlFoto;

    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getNombre() {
        return nombre;
    }

}
