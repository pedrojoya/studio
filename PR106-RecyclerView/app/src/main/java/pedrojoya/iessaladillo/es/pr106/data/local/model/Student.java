package pedrojoya.iessaladillo.es.pr106.data.local.model;

import android.support.annotation.NonNull;

public class Student {

    private long id;
    private final String name;
    private final String address;
    private final String photoUrl;

    @SuppressWarnings({"WeakerAccess", "unused"})
    public Student(long id, @NonNull String name, String address, String photoUrl) {
        this.name = name;
        this.address = address;
        this.photoUrl = photoUrl;
    }

    public Student(@NonNull String name, String address, String photoUrl) {
        this(0, name, address, photoUrl);
    }

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
