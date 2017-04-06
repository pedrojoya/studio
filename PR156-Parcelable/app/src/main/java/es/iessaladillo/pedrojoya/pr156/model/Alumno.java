package es.iessaladillo.pedrojoya.pr156.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Alumno implements Parcelable {

    public static final int DEFAULT_EDAD = 18;
    public static final int MAX_EDAD = 130;

    private String nombre;
    private int edad;

    @SuppressWarnings("SameParameterValue")
    public Alumno(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    @SuppressWarnings("unused")
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    @SuppressWarnings("unused")
    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeInt(this.edad);
    }

    @SuppressWarnings("WeakerAccess")
    protected Alumno(Parcel in) {
        this.nombre = in.readString();
        this.edad = in.readInt();
    }

    public static final Parcelable.Creator<Alumno> CREATOR = new Parcelable.Creator<Alumno>() {
        public Alumno createFromParcel(Parcel source) {
            return new Alumno(source);
        }

        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };

}
