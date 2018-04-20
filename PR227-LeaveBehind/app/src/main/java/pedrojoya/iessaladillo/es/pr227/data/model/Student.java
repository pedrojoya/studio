package pedrojoya.iessaladillo.es.pr227.data.model;

import android.support.annotation.NonNull;

public class Student {

    private final String name;
    private final String address;
    private final String photoUrl;

    public Student(@NonNull String name, String address, String photoUrl) {
        this.name = name;
        this.address = address;
        this.photoUrl = photoUrl;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
