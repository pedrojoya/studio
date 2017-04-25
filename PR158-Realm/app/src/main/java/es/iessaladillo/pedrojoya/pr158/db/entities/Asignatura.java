package es.iessaladillo.pedrojoya.pr158.db.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

@SuppressWarnings({"WeakerAccess", "unused"})
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

}
