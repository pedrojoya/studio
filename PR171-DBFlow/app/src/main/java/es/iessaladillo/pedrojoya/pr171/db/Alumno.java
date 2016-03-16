package es.iessaladillo.pedrojoya.pr171.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.List;

@Table(database = Instituto.class)
public class Alumno extends BaseModel implements Parcelable {

    @PrimaryKey(autoincrement = true)
    @Column
    private long id;
    @Column
    private String avatar;
    @Column
    private String nombre;
    @Column
    private String telefono;
    @Column
    private String direccion;

    @ForeignKey(saveForeignKeyModel = false)
    ForeignKeyContainer<Curso> cursoForeignKeyContainer;

    Curso curso;

    public void setCurso(Curso curso) {
        cursoForeignKeyContainer = FlowManager.getContainerAdapter(Curso.class).toForeignKeyContainer(curso);
    }

    public Curso getCurso() {
        return cursoForeignKeyContainer.load();
    }

    List<Asignatura_Alumno> asignaturas;

    public List<Asignatura_Alumno> getAsignaturas() {
        if (asignaturas == null || asignaturas.isEmpty()) {
            asignaturas = SQLite.select()
                    .from(Asignatura_Alumno.class)
                    .where(Asignatura_Alumno_Table.alumno_id.eq(id))
                    .queryList();
        }
        return asignaturas;
    }

    public Alumno() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        dest.writeLong(this.id);
        dest.writeString(this.avatar);
        dest.writeString(this.nombre);
        dest.writeString(this.telefono);
        dest.writeString(this.direccion);
        dest.writeParcelable(getCurso(), 0);
    }

    protected Alumno(Parcel in) {
        this.id = in.readLong();
        this.avatar = in.readString();
        this.nombre = in.readString();
        this.telefono = in.readString();
        this.direccion = in.readString();
        setCurso((Curso) in.readParcelable(Curso.class.getClassLoader()));
    }

    public static final Creator<Alumno> CREATOR = new Creator<Alumno>() {
        public Alumno createFromParcel(Parcel source) {
            return new Alumno(source);
        }

        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };
}
