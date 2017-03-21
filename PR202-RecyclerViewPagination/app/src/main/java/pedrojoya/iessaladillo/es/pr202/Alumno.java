package pedrojoya.iessaladillo.es.pr202;

import android.os.Parcel;
import android.os.Parcelable;

// Modelo Alumno.
@SuppressWarnings("unused")
class Alumno implements Parcelable {

    private String nombre;

    private String direccion;
    private String urlFoto;
    public Alumno(String nombre, String direccion, String urlFoto) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.urlFoto = urlFoto;

    }

    public Alumno() {
        nombre = null;

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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeString(this.direccion);
        dest.writeString(this.urlFoto);
    }

    protected Alumno(Parcel in) {
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
