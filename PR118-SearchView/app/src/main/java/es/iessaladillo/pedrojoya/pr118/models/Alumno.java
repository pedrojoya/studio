package es.iessaladillo.pedrojoya.pr118.models;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import es.iessaladillo.pedrojoya.pr118.datos.InstitutoContract;

public class Alumno {

	// Propiedades.
    private long id;
	private String nombre;
	private String telefono;
	private String curso;
	private String direccion;

	// Constructores
	public Alumno(long id, String nombre, String telefono, String curso,
           String direccion) {
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.curso = curso;
		this.direccion = direccion;
	}

	public Alumno(String nombre, String telefono, String curso, String direccion) {
		this.id = 0;
		this.nombre = nombre;
		this.telefono = telefono;
		this.curso = curso;
		this.direccion = direccion;
	}

    public Alumno(JSONObject objeto) {
        try {
            this.nombre = objeto.getString(InstitutoContract.Alumno.NOMBRE);
            this.telefono = objeto.getString(InstitutoContract.Alumno.TELEFONO);;
            this.curso = objeto.getString(InstitutoContract.Alumno.CURSO);;
            this.direccion = objeto.getString(InstitutoContract.Alumno.DIRECCION);;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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
				.getColumnIndex(InstitutoContract.Alumno._ID)));
		alumno.setNombre(cursorAlumno.getString(cursorAlumno
                .getColumnIndex(InstitutoContract.Alumno.NOMBRE)));
		alumno.setCurso(cursorAlumno.getString(cursorAlumno
                .getColumnIndex(InstitutoContract.Alumno.CURSO)));
		alumno.setTelefono(cursorAlumno.getString(cursorAlumno
				.getColumnIndex(InstitutoContract.Alumno.TELEFONO)));
		alumno.setDireccion(cursorAlumno.getString(cursorAlumno
                .getColumnIndex(InstitutoContract.Alumno.DIRECCION)));
		// Retorno el objeto Alumno.
		return alumno;
	}

	// Crea un objeto ContentValues a partir de un objeto Alumo y lo retorna.
	public static ContentValues toContentValues(Alumno alumno) {
		// Creamos un la lista de pares clave-valor con cada campo-valor.
		ContentValues valores = new ContentValues();
		valores.put(InstitutoContract.Alumno.NOMBRE, alumno.getNombre());
		valores.put(InstitutoContract.Alumno.CURSO, alumno.getCurso());
		valores.put(InstitutoContract.Alumno.TELEFONO, alumno.getTelefono());
		valores.put(InstitutoContract.Alumno.DIRECCION, alumno.getDireccion());
		return valores;
	}

}
