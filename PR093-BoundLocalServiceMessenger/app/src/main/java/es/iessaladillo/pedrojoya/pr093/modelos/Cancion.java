package es.iessaladillo.pedrojoya.pr093.modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Cancion implements Parcelable {

    // Propieadades.
    String nombre;
    String duracion;
    String url;

    // Constructor.
    public Cancion(String nombre, String duracion, String url) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.url = url;
    }

    // Getters.
    public String getNombre() {
        return nombre;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return nombre + " (" + duracion + ")";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeString(this.duracion);
        dest.writeString(this.url);
    }

    private Cancion(Parcel in) {
        this.nombre = in.readString();
        this.duracion = in.readString();
        this.url = in.readString();
    }

    public static Parcelable.Creator<Cancion> CREATOR = new Parcelable.Creator<Cancion>() {
        public Cancion createFromParcel(Parcel source) {
            return new Cancion(source);
        }

        public Cancion[] newArray(int size) {
            return new Cancion[size];
        }
    };

}
