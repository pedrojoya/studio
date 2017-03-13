package es.iessaladillo.pedrojoya.pr164;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Grupo extends ListItem implements Parcelable {

    private String nombre;
    private ArrayList<Alumno> mHiddenChildren;


    public Grupo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @SuppressWarnings("unused")
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Alumno> getHiddenChildren() {
        return mHiddenChildren;
    }

    public void setHiddenChildren(ArrayList<Alumno> hiddenChildren) {
        mHiddenChildren = hiddenChildren;
    }

    @Override
    public int getType() {
        return ListItem.TYPE_HEADER;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeTypedList(mHiddenChildren);
    }

    private Grupo(Parcel in) {
        this.nombre = in.readString();
        this.mHiddenChildren = in.createTypedArrayList(Alumno.CREATOR);
    }

    public static final Parcelable.Creator<Grupo> CREATOR = new Parcelable.Creator<Grupo>() {
        public Grupo createFromParcel(Parcel source) {
            return new Grupo(source);
        }

        public Grupo[] newArray(int size) {
            return new Grupo[size];
        }
    };

}
