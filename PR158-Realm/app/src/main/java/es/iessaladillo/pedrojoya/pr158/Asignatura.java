package es.iessaladillo.pedrojoya.pr158;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Asignatura extends RealmObject {

    @PrimaryKey
    @Required
    private String id;
    @Required
    private String nombre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Asignatura() {
        super();
    }

}
