package es.iessaladillo.pedrojoya.pr116;

import android.database.Cursor;
import android.provider.CallLog;
import android.text.TextUtils;

import java.util.ArrayList;

public class Llamada {

    private String numero;
    private String nombre;
    private int tipo;
    private long fecha;
    private long duracion;

    public Llamada(Cursor cursor) {
        String sNombre = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME));
        if (TextUtils.isEmpty(sNombre)) {
            sNombre = "Desconocido";
        }
        nombre = sNombre;
        numero = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.NUMBER));
        duracion = cursor.getLong(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION));
        fecha = cursor.getLong(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE));
        tipo = cursor.getInt(cursor.getColumnIndexOrThrow(CallLog.Calls.TYPE));
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public long getDuracion() {
        return duracion;
    }

    public void setDuracion(long duracion) {
        this.duracion = duracion;
    }

    public static ArrayList<Llamada> CursorToArrayList(Cursor cursor) {
        ArrayList<Llamada> lista = new ArrayList<Llamada>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            lista.add(new Llamada(cursor));
            cursor.moveToNext();
        }
        return lista;
    }
}
