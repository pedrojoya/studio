package pedrojoya.iessaladillo.es.pr181.main.model.entity;

// Modelo Alumno.
public class Student {

    private final String nombre;
    private final String direccion;
    private final String urlFoto;

    public Student(String nombre, String direccion, String urlFoto) {
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
