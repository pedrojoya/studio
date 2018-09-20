package pedrojoya.iessaladillo.es.pr229.data.local.model;

public class Student {

    private long id;
    private String name;
    private final String address;
    private final String photoUrl;

    public Student(long id, String name, String address, String photoUrl) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.photoUrl = photoUrl;
    }

    public Student(Student student) {
        this(student.id, student.name, student.address, student.photoUrl);
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

    public Student reverseName() {
        name = new StringBuilder(name).reverse().toString();
        return this;
    }

}
