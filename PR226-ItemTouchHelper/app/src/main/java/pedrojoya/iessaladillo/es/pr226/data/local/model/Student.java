package pedrojoya.iessaladillo.es.pr226.data.local.model;

public class Student {

    private long id;
    private final String name;
    private final String address;
    private final String photoUrl;
    private long order;

    public Student(long id, String name, String address, String photoUrl, long order) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.photoUrl = photoUrl;
        this.order = order;
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

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

}

