package es.iessaladillo.pedrojoya.pr048.data.model;

public class Student {

    private final String name;
    private final String address;
    private final String photoUrl;

    public Student(String name, String address, String photoUrl) {
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

}
