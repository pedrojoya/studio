package es.iessaladillo.pedrojoya.pr107.data.model;

public class Student {

    private final long id;
    private final String name;
    private final String address;
    private final String photoUrl;

    public Student(long id, String name, String address, String photoUrl) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.photoUrl = photoUrl;
    }

    public long getId() {
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

}
