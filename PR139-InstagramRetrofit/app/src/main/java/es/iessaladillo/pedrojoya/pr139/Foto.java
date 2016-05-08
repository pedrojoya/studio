package es.iessaladillo.pedrojoya.pr139;

import android.os.Parcel;
import android.os.Parcelable;

class Foto implements Parcelable {

    // Propiedades.
    private String url;
    private String descripcion;

    // Getters and setters.
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.descripcion);
    }

    public Foto() {
    }

    @SuppressWarnings("WeakerAccess")
    protected Foto(Parcel in) {
        this.url = in.readString();
        this.descripcion = in.readString();
    }

    public static final Parcelable.Creator<Foto> CREATOR = new Parcelable.Creator<Foto>() {
        public Foto createFromParcel(Parcel source) {
            return new Foto(source);
        }

        public Foto[] newArray(int size) {
            return new Foto[size];
        }
    };
}
