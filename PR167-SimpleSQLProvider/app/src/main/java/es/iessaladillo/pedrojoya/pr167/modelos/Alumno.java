package es.iessaladillo.pedrojoya.pr167.modelos;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;
import es.iessaladillo.pedrojoya.pr167.bd.Instituto;

@SimpleSQLTable(table = Instituto.Alumno.TABLA, provider = Instituto.PROVIDER_NAME)
public class Alumno {

    private long id;
    @SimpleSQLColumn(Instituto.Alumno.NOMBRE)
    private String nombre;
    @SimpleSQLColumn(Instituto.Alumno.TELEFONO)
    private String telefono;
    @SimpleSQLColumn(Instituto.Alumno.CURSO)
    private String curso;
    @SimpleSQLColumn(Instituto.Alumno.DIRECCION)
    private String direccion;

    public Alumno() {
        this.id = 0;
        this.nombre = null;
        this.telefono = null;
        this.curso = null;
        this.direccion = null;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
