package es.iessaladillo.pedrojoya.pr083.data.model;

// Data layer model
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class Student {

    private String photo;
    private String name;
    private int age;
    private String grade;
    private String phone;
    private boolean repeater;

    public Student(String photo, String name, int age, String grade, String phone,
        boolean repeater) {
        this.photo = photo;
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.phone = phone;
        this.repeater = repeater;
    }

    public Student() { }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isRepeater() {
        return repeater;
    }

    public void setRepeater(boolean repeater) {
        this.repeater = repeater;
    }

}
