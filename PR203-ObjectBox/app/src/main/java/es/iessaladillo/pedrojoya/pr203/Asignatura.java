package es.iessaladillo.pedrojoya.pr203;

import android.os.Parcel;
import android.os.Parcelable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Generated;

@SuppressWarnings({"WeakerAccess", "unused"})
@Entity
public class Asignatura implements Parcelable {

    @Id
    long id;

    String nombre;

    @Generated(hash = 1545080799)
    public Asignatura(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Generated(hash = 1090136982)
    public Asignatura() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.nombre);
    }

    protected Asignatura(Parcel in) {
        this.id = in.readLong();
        this.nombre = in.readString();
    }

    public static final Parcelable.Creator<Asignatura> CREATOR = new Parcelable
            .Creator<Asignatura>() {
        @Override
        public Asignatura createFromParcel(Parcel source) {
            return new Asignatura(source);
        }

        @Override
        public Asignatura[] newArray(int size) {
            return new Asignatura[size];
        }
    };

}
