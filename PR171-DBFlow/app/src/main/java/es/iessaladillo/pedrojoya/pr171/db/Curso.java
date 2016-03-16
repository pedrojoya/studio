package es.iessaladillo.pedrojoya.pr171.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@ModelContainer
@Table(database = Instituto.class)
public class Curso extends BaseModel implements Parcelable {

    @PrimaryKey(autoincrement = true)
    @Column
    private long id;
    @Column
    private String nombre;

    List<Alumno> alumnos;

    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE}, variableName = "alumnos")
    public List<Alumno> getAlumnos() {
        if (alumnos == null || alumnos.isEmpty()) {
            alumnos = SQLite.select()
                    .from(Alumno.class)
                    .where(Alumno_Table.cursoForeignKeyContainer_id.eq(id))
                    .queryList();
        }
        return alumnos;
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

    public Curso() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.nombre);
        dest.writeTypedList(alumnos);
    }

    protected Curso(Parcel in) {
        this.id = in.readLong();
        this.nombre = in.readString();
        this.alumnos = in.createTypedArrayList(Alumno.CREATOR);
    }

    public static final Parcelable.Creator<Curso> CREATOR = new Parcelable.Creator<Curso>() {
        public Curso createFromParcel(Parcel source) {
            return new Curso(source);
        }

        public Curso[] newArray(int size) {
            return new Curso[size];
        }
    };
}
