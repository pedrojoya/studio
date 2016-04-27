package es.iessaladillo.pedrojoya.pr049.modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Obra implements Parcelable {

    // Propiedades.
    private int fotoResId;
    private String nombre;
    private String autor;
    private int anio;

    // Constructores.
    @SuppressWarnings("SameParameterValue")
    public Obra(int fotoResId, String nombre, String autor, int anio) {
        this.fotoResId = fotoResId;
        this.nombre = nombre;
        this.autor = autor;
        this.anio = anio;
    }

    // Getters y Setters.
    public int getFotoResId() {
        return fotoResId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnio() {
        return anio;
    }

    // Desde aquí para que sea Parcelable.

    // Constructor.
    private Obra(Parcel in) {
        readFromParcel(in);
    }

    // Implementación por defecto.
    public int describeContents() {
        return 0;
    }

    // Escribir las propiedades del objeto en un Parcel de destino.
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(fotoResId);
        dest.writeString(nombre);
        dest.writeString(autor);
        dest.writeInt(anio);
    }

    // Leer desde un Parcel las propiedades del objeto.
    private void readFromParcel(Parcel in) {
        fotoResId = in.readInt();
        nombre = in.readString();
        autor = in.readString();
        anio = in.readInt();
    }

    // Creador del objeto Parcelable.
    public static final Parcelable.Creator<Obra> CREATOR = new Parcelable.Creator<Obra>() {
        // Crea un objeto Alumno a partir de un Parcel.
        public Obra createFromParcel(Parcel in) {
            return new Obra(in);
        }

        // Crea un array de alumnos del tamaño pasado como parámetro.
        public Obra[] newArray(int size) {
            return new Obra[size];
        }
    };
}
