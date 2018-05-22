package pedrojoya.iessaladillo.es.pr247.data.model;

import android.support.annotation.NonNull;

import java.util.Objects;

public class Student {

    private final int id;
    private final String name;
    private final String address;
    private final String photoUrl;

    public Student(int id, @NonNull String name, String address, String photoUrl) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.photoUrl = photoUrl;
    }

    public int getId() {
        return id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id &&
                Objects.equals(name, student.name) &&
                Objects.equals(address, student.address) &&
                Objects.equals(photoUrl, student.photoUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, address, photoUrl);
    }
}
