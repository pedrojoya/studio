package pedrojoya.iessaladillo.es.pr181.main.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

// Modelo Alumno.
public class Student implements Parcelable {

    private final String nombre;
    private final String direccion;
    private final String urlFoto;

    public Student(String nombre, String direccion, String urlFoto) {
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

    protected Student(Parcel in) {
        this.nombre = in.readString();
        this.direccion = in.readString();
        this.urlFoto = in.readString();
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

}
