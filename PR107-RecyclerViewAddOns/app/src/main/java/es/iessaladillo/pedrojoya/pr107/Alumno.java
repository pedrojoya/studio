package es.iessaladillo.pedrojoya.pr107;

// Modelo Alumno.
class Alumno {
    private final long id;
    private final String nombre;
    private final String direccion;
    private final String urlFoto;

    public Alumno(long id, String nombre, String direccion, String urlFoto) {
        this.id = id;
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

    public long getId() {
        return id;
    }

}
