package es.iessaladillo.pedrojoya.pr111;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class Alumno extends SugarRecord implements Parcelable {

    private String avatar;
    private String nombre;
    private String telefono;
    private String curso;
    private String direccion;

    public Alumno(String avatar, String nombre, String telefono, String curso, String direccion) {
        this.avatar = avatar;
        this.nombre = nombre;
        this.telefono = telefono;
        this.curso = curso;
        this.direccion = direccion;
    }

    // Es obligatorio dejar el constructor vacío.
    public Alumno() {
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getDireccion() {
        return direccion;
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
        dest.writeString(this.avatar);
        dest.writeString(this.nombre);
        dest.writeString(this.telefono);
        dest.writeString(this.curso);
        dest.writeString(this.direccion);
        dest.writeValue(this.getId());
    }

    private Alumno(Parcel in) {
        this.avatar = in.readString();
        this.nombre = in.readString();
        this.telefono = in.readString();
        this.curso = in.readString();
        this.direccion = in.readString();
        this.setId((Long) in.readValue(Long.class.getClassLoader()));
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
