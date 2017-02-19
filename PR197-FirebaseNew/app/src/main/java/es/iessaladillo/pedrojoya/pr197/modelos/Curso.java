package es.iessaladillo.pedrojoya.pr197.modelos;

@SuppressWarnings("unused")
public class Curso {
    private String nombre;

    public Curso() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
