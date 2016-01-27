package es.iessaladillo.pedrojoya.pr027.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr027.modelos.Alumno;

/**
 * Clase de acceso a los datos de la base de datps. Utiliza un objeto de un
 * clase privada interna para gestionar realmente la base de datos, creando
 * hacia el exterior un wrapper (una envoltura) que permita a otros objetos
 * interactuar con la base de datos si hacer uso de sentencias SQL ni conocer
 * detalles internos de ella.
 */
public class DAO {

    // Variables a nivel de clase.
    private final Helper mHelper; // Ayudante para la creación y gestión de la BD.

    // Constructor. Recibe el contexto.
    public DAO(Context contexto) {
        // Se obtiene el mHelper.
        mHelper = new Helper(contexto);
    }

    // Abre la base de datos para escritura.
    public SQLiteDatabase openWritableDatabase() {
        return mHelper.getWritableDatabase();
    }

    // Cierra la base de datos.
    public void closeDatabase() {
        mHelper.close();
    }

    // CRUD (Create-Read-Update-Delete) de la tabla alumnos

    // Inserta un alumno en la tabla de alumnmos.
    // Recibe el objeto Alumno a insertar.
    // Retorna el _id del alumna una vez insertado o -1 si se ha producido un
    // error.
    public long createAlumno(Alumno alumno) {
        // Se abre la base de datos.
        SQLiteDatabase bd = mHelper.getWritableDatabase();
        // Se crea la lista de pares campo-valor para realizar la inserción.
        ContentValues valores = new ContentValues();
        valores.put(Instituto.Alumno.NOMBRE, alumno.getNombre());
        valores.put(Instituto.Alumno.CURSO, alumno.getCurso());
        valores.put(Instituto.Alumno.TELEFONO, alumno.getTelefono());
        valores.put(Instituto.Alumno.DIRECCION, alumno.getDireccion());
        // Se realiza el insert
        long resultado = bd.insert(Instituto.Alumno.TABLA, null, valores);
        // Se cierra la base de datos.
        mHelper.close();
        // Se retorna el _id del alumno insertado o -1 si error.
        return resultado;
    }

    // Borra de la BD un alumno. Recibe el _id del alumno a borrar. Retorna true
    // si se ha realizado la eliminación con éxito.
    public boolean deleteAlumno(long id) {
        // Se abre la base de datos.
        SQLiteDatabase bd = mHelper.getWritableDatabase();
        // Se realiza el delete.
        long resultado = bd.delete(Instituto.Alumno.TABLA, Instituto.Alumno._ID + " = "
                + id, null);
        // Se cierra la base de datos.
        mHelper.close();
        // Se retorna si se ha eliminado algún alumno.
        return resultado > 0;

    }

    // Actualiza en la BD los datos de un alumno. Recibe el alumno. Retorna true
    // si la actualización se ha realizado con éxito.
    public boolean updateAlumno(Alumno alumno) {
        // Se abre la base de datos.
        SQLiteDatabase bd = mHelper.getWritableDatabase();
        // Se crea la lista de pares clave-valor con cada campo-valor.
        ContentValues valores = new ContentValues();
        valores.put(Instituto.Alumno.NOMBRE, alumno.getNombre());
        valores.put(Instituto.Alumno.CURSO, alumno.getCurso());
        valores.put(Instituto.Alumno.TELEFONO, alumno.getTelefono());
        valores.put(Instituto.Alumno.DIRECCION, alumno.getDireccion());
        // Se realiza el update.
        long resultado = bd.update(Instituto.Alumno.TABLA, valores, Instituto.Alumno._ID
                + " = " + alumno.getId(), null);
        // Se cierra la base de datos.
        mHelper.close();
        // Se retorna si ha actualizado algún alumno.
        return resultado > 0;

    }

    // Consulta en la BD los datos de un alumno. Recibe el _id del alumno a
    // consultar. Retorna el objeto Alumno o null si no existe.
    public Alumno queryAlumno(long id) {
        // Se abre la base de datos.
        SQLiteDatabase bd = mHelper.getWritableDatabase();
        // Se realiza la query SQL sobre la BD.
        Cursor cursor = bd.query(true, Instituto.Alumno.TABLA,
                Instituto.Alumno.TODOS, Instituto.Alumno._ID + " = " + id,
                null, null, null, null, null);
        // Se mueve al primer registro del cursor.
        Alumno alumno = null;
        if (cursor != null) {
            cursor.moveToFirst();
            // Retorno el objeto Alumno correspondiente.
            alumno = cursorToAlumno (cursor);
        }
        // Se cierra la base de datos.
        mHelper.close();
        // Se retorna el alumno o null.
        return alumno;
    }

    // Consulta en la BD todos los alumnos. Retorna el cursor resultado de la
    // consulta (puede ser null si no hay alumnos), ordenados alfabéticamente
    // por nombre.
    public Cursor queryAllAlumnos(SQLiteDatabase bd) {
        // Se realiza la consulta y se retorna el cursor.
        return  bd.query(Instituto.Alumno.TABLA, Instituto.Alumno.TODOS, null,
                null, null, null, Instituto.Alumno.NOMBRE);
    }

    // Consulta en la VD todos los alumnos. Retorna una lista de objeto Alumno,
    // ordenados alfabéticamente por nombre.
    public List<Alumno> getAllAlumnos() {
        // Se abre la base de datos.
        SQLiteDatabase bd = mHelper.getWritableDatabase();
        List<Alumno> lista = new ArrayList<>();
        // Se consultan todos los alumnos en la BD y obtiene un cursor.
        Cursor cursor = this.queryAllAlumnos(bd);
        // Se convierte cada registro del cursor en un elemento de la lista.
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Alumno alumno = cursorToAlumno(cursor);
            lista.add(alumno);
            cursor.moveToNext();
        }
        // Se cierra el cursor (IMPORTANTE).
        cursor.close();
        // Se cierra la base de datos.
        mHelper.close();
        // Se retorna la lista.
        return lista;
    }

    // Crea un objeto Alumno a partir del registro actual de un cursor. Recibe
    // el cursor y retorna un nuevo objeto Alumno cargado con los datos del
    // registro actual del cursor.
    public static Alumno cursorToAlumno(Cursor cursorAlumno) {
        // Crea un objeto Alumno y guarda los valores provenientes
        // del registro actual del cursor.
        Alumno alumno = new Alumno();
        alumno.setId(cursorAlumno.getLong(
                cursorAlumno.getColumnIndexOrThrow(Instituto.Alumno._ID)));
        alumno.setNombre(cursorAlumno.getString(
                cursorAlumno.getColumnIndexOrThrow(Instituto.Alumno.NOMBRE)));
        alumno.setCurso(cursorAlumno.getString(
                cursorAlumno.getColumnIndexOrThrow(Instituto.Alumno.CURSO)));
        alumno.setTelefono(cursorAlumno.getString(
                cursorAlumno.getColumnIndexOrThrow(Instituto.Alumno.TELEFONO)));
        alumno.setDireccion(cursorAlumno.getString(
                cursorAlumno.getColumnIndexOrThrow(Instituto.Alumno.DIRECCION)));
        // Se retorna el objeto Alumno.
        return alumno;
    }

}
