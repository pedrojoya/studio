package es.iessaladillo.pedrojoya.pr203;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Generated;

@SuppressWarnings("WeakerAccess")
@Entity
public class Alumno {

    @Id
    long id;
    String nombre;
    String direccion;
    String urlFoto;
    @Generated(hash = 2135107283)
    public Alumno(long id, String nombre, String direccion, String urlFoto) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.urlFoto = urlFoto;
    }
    @Generated(hash = 721709040)
    public Alumno() {
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
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getUrlFoto() {
        return urlFoto;
    }
    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

}
