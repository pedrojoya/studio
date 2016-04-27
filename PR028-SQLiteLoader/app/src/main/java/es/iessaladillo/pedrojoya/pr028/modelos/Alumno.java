package es.iessaladillo.pedrojoya.pr028.modelos;

import android.content.ContentValues;
import android.database.Cursor;

import es.iessaladillo.pedrojoya.pr028.bd.Instituto;

public class Alumno {

    private long id;
    private String avatar;
    private String nombre;
    private String telefono;
    private String curso;
    private String direccion;

    public Alumno() {
        // Establezo los valores iniciales para las propiedades
        this.id = 0;
        this.avatar = null;
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

    @SuppressWarnings("WeakerAccess")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    // Crea un objeto Alumno a partir del registro actual de un cursor. Recibe
    // el cursor y retorna un nuevo objeto Alumno cargado con los datos del
    // registro actual del cursor.
    public static Alumno fromCursor(Cursor cursorAlumno) {
        // Crea un objeto Alumno y guarda los valores provenientes
        // del registro actual del cursor.
        Alumno alumno = new Alumno();
        alumno.setId(cursorAlumno.getLong(cursorAlumno
                .getColumnIndex(Instituto.Alumno._ID)));
        alumno.setAvatar(cursorAlumno.getString(cursorAlumno
                .getColumnIndex(Instituto.Alumno.AVATAR)));
        alumno.setNombre(cursorAlumno.getString(cursorAlumno
                .getColumnIndex(Instituto.Alumno.NOMBRE)));
        alumno.setCurso(cursorAlumno.getString(cursorAlumno
                .getColumnIndex(Instituto.Alumno.CURSO)));
        alumno.setTelefono(cursorAlumno.getString(cursorAlumno
                .getColumnIndex(Instituto.Alumno.TELEFONO)));
        alumno.setDireccion(cursorAlumno.getString(cursorAlumno
                .getColumnIndex(Instituto.Alumno.DIRECCION)));
        // Retorno el objeto Alumno.
        return alumno;
    }

    // Crea un objeto ContentValues a partir de un objeto Alumo y lo retorna.
    public static ContentValues toContentValues(Alumno alumno) {
        // Creamos un la lista de pares clave-valor con cada campo-valor.
        ContentValues valores = new ContentValues();
        valores.put(Instituto.Alumno.AVATAR, alumno.getAvatar());
        valores.put(Instituto.Alumno.NOMBRE, alumno.getNombre());
        valores.put(Instituto.Alumno.CURSO, alumno.getCurso());
        valores.put(Instituto.Alumno.TELEFONO, alumno.getTelefono());
        valores.put(Instituto.Alumno.DIRECCION, alumno.getDireccion());
        return valores;
    }

}