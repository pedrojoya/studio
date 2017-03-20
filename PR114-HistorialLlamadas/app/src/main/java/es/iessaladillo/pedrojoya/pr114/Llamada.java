package es.iessaladillo.pedrojoya.pr114;

import android.database.Cursor;
import android.provider.CallLog;

import java.util.ArrayList;

@SuppressWarnings("unused")
class Llamada {

    private String nombre;
    private int tipo;
    private String numero;
    private long fecha;
    private long duracion;

    public Llamada() {
    }

    public Llamada(Cursor cursor) {
        nombre = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME));
        numero = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.NUMBER));
        duracion = cursor.getLong(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION));
        fecha = cursor.getLong(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE));
        tipo = cursor.getInt(cursor.getColumnIndexOrThrow(CallLog.Calls.TYPE));
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public static ArrayList<Llamada> toList(Cursor cursor) throws Exception {
        ArrayList<Llamada> llamadas = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                llamadas.add(new Llamada(cursor));
                cursor.moveToNext();
            }
        }
        return llamadas;
    }

}
