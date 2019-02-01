package es.iessaladillo.pedrojoya.pr211.data.local.model;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"name"}, unique = true)})
public class Student {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "name")
    @NonNull
    private final String name;
    @ColumnInfo(name = "phone")
    @NonNull
    private final String phone;
    @ColumnInfo(name = "grade")
    @NonNull
    private final String grade;
    @ColumnInfo(name = "address")
    private final String address;

    public Student(long id, @NonNull String name, @NonNull String phone, @NonNull String grade,
        String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.grade = grade;
        this.address = address;
    }

    @Ignore
    public Student(@NonNull String name, @NonNull String phone, @NonNull String grade,
        String address) {
        this.name = name;
        this.phone = phone;
        this.grade = grade;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    @NonNull
    public String getGrade() {
        return grade;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Objects.equals(name, student.name) && Objects.equals(phone,
            student.phone) && Objects.equals(grade, student.grade) && Objects.equals(address,
            student.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, grade, address);
    }

}
