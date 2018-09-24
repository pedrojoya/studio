package pedrojoya.iessaladillo.es.pr225.data.local.model;

import androidx.annotation.NonNull;

public class Student {

    private long id;
    private final String name;
    private final String address;
    private final String photoUrl;

    @SuppressWarnings("WeakerAccess")
    public Student(long id, @NonNull String name, String address, String photoUrl) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.photoUrl = photoUrl;
    }

    public Student(@NonNull String name, String address, String photoUrl) {
        this(0, name, address, photoUrl);
    }

    @SuppressWarnings("unused")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

}
