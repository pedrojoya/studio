package pedrojoya.iessaladillo.es.pr202;

// Modelo Alumno.
@SuppressWarnings("unused")
class Alumno {

    private String nombre;
    private String direccion;
    private String urlFoto;

    public Alumno(String nombre, String direccion, String urlFoto) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.urlFoto = urlFoto;
    }

    public Alumno() {
        nombre = null;

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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

}
