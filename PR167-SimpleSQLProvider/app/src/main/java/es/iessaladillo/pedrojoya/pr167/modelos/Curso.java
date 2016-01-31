package es.iessaladillo.pedrojoya.pr167.modelos;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;
import es.iessaladillo.pedrojoya.pr167.bd.Instituto;

@SimpleSQLTable(table = Instituto.Curso.TABLA, provider = Instituto.PROVIDER_NAME)
public class Curso {

    private long id;
    @SimpleSQLColumn(Instituto.Curso.NOMBRE)
    private String nombre;

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
