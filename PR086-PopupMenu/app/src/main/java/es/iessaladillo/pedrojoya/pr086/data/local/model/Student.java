package es.iessaladillo.pedrojoya.pr086.data.local.model;

@SuppressWarnings("unused")
public class Student {

    private long id;
    private final String name;
    private final String address;
    private final String phone;
    private final String grade;
    private final String photo;
    private final int age;
    private final boolean repeater;

    public Student(long id, String name, String address, String phone, String grade, String photo,
        int age, boolean repeater) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.grade = grade;
        this.photo = photo;
        this.age = age;
        this.repeater = repeater;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getGrade() {
        return grade;
    }

    public String getPhoto() {
        return photo;
    }

    public int getAge() {
        return age;
    }

    public boolean isRepeater() {
        return repeater;
    }

}
