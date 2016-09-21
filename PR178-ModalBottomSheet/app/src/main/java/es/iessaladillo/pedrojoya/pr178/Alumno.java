package es.iessaladillo.pedrojoya.pr178;

import android.os.Parcel;
import android.os.Parcelable;

// Modelo Alumno.
class Alumno implements Parcelable {
    private final long id;
    private final String nombre;
    private final String direccion;
    private final String urlFoto;

    public Alumno(long id, String nombre, String direccion, String urlFoto) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.urlFoto = urlFoto;

    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public long getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.nombre);
        dest.writeString(this.direccion);
        dest.writeString(this.urlFoto);
    }

    @SuppressWarnings("WeakerAccess")
    protected Alumno(Parcel in) {
        this.id = in.readLong();
        this.nombre = in.readString();
        this.direccion = in.readString();
        this.urlFoto = in.readString();
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
