package es.iessaladillo.pedrojoya.pr165;

import android.os.Parcel;
import android.os.Parcelable;

public class Producto implements Parcelable {

    private String nombre;
    private float cantidad;
    private String unidad;
    private boolean comprado;

    public Producto(String nombre, float cantidad, String unidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.comprado = false;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }

    public void toggleComprado() {
        this.comprado = !this.comprado;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeFloat(this.cantidad);
        dest.writeString(this.unidad);
        dest.writeByte(comprado ? (byte) 1 : (byte) 0);
    }

    protected Producto(Parcel in) {
        this.nombre = in.readString();
        this.cantidad = in.readFloat();
        this.unidad = in.readString();
        this.comprado = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Producto> CREATOR = new Parcelable.Creator<Producto>() {
        public Producto createFromParcel(Parcel source) {
            return new Producto(source);
        }

        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

}
