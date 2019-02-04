package pedrojoya.iessaladillo.es.pr256.data.local.model;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"name"}, unique = true)})
public class Student {

    @ColumnInfo(name = "name")
    @NonNull
    private final String name;
    @ColumnInfo(name = "address")
    @NonNull
    private final String address;
    @ColumnInfo(name = "photo")
    @NonNull
    private final String photo;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;


    public Student(long id, @NonNull String name, @NonNull String address, @NonNull String photo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.photo = photo;
    }

    @Ignore
    public Student(@NonNull String name, @NonNull String address, @NonNull String photo) {
        this.name = name;
        this.address = address;
        this.photo = photo;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    @NonNull
    public String getPhoto() {
        return photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Objects.equals(name, student.name) && Objects.equals(address,
            student.address) && Objects.equals(photo, student.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, photo);
    }

}
