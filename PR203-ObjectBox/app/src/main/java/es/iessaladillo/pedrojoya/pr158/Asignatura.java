package es.iessaladillo.pedrojoya.pr158;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Generated;

@Entity
public class Asignatura {

    @Id
    long id;

    String nombre;

    @Generated(hash = 1545080799)
    public Asignatura(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Generated(hash = 1090136982)
    public Asignatura() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
