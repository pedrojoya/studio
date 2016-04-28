package es.iessaladillo.pedrojoya.pr083;

import android.os.Parcel;
import android.os.Parcelable;

// Clase para modelar el alumno.
class Alumno implements Parcelable {

    // Constantes.
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_DIRECCION = "direccion";
    public static final String KEY_TELEFONO = "telefono";
    public static final String KEY_CURSO = "curso";
    public static final String KEY_REPETIDOR = "repetidor";
    public static final String KEY_EDAD = "edad";
    public static final String KEY_FOTO = "foto";

    // Variables miembro.
    private String foto;
    private String nombre;
    private int edad;
    private String curso;
    private String direccion;
    private String telefono;
    private boolean repetidor;

    public String getFoto() {
        return foto;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getCurso() {
        return curso;
    }

    public boolean isRepetidor() {
        return repetidor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRepetidor(boolean repetidor) {
        this.repetidor = repetidor;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.foto);
        dest.writeString(this.nombre);
        dest.writeInt(this.edad);
        dest.writeString(this.curso);
        dest.writeString(this.direccion);
        dest.writeString(this.telefono);
        dest.writeByte(repetidor ? (byte) 1 : (byte) 0);
    }

    public Alumno() {
    }

    @SuppressWarnings("WeakerAccess")
    protected Alumno(Parcel in) {
        this.foto = in.readString();
        this.nombre = in.readString();
        this.edad = in.readInt();
        this.curso = in.readString();
        this.direccion = in.readString();
        this.telefono = in.readString();
        this.repetidor = in.readByte() != 0;
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