package es.iessaladillo.pedrojoya.pr014;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("CanBeFinal")
class Alumno implements Parcelable {

    private String nombre;
    private int edad;
    private String ciclo;
    private String curso;

    public Alumno(String nombre, int edad, String ciclo, String curso) {
        this.nombre = nombre;
        this.edad = edad;
        this.ciclo = ciclo;
        this.curso = curso;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getCiclo() {
        return ciclo;
    }

    public String getCurso() {
        return curso;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeInt(this.edad);
        dest.writeString(this.ciclo);
        dest.writeString(this.curso);
    }

    @SuppressWarnings("WeakerAccess")
    protected Alumno(Parcel in) {
        this.nombre = in.readString();
        this.edad = in.readInt();
        this.ciclo = in.readString();
        this.curso = in.readString();
    }

    public static final Parcelable.Creator<Alumno> CREATOR = new Parcelable.Creator<Alumno>() {
        @Override
        public Alumno createFromParcel(Parcel source) {
            return new Alumno(source);
        }

        @Override
        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };

}
