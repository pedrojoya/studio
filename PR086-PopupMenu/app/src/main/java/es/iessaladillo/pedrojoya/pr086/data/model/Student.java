package es.iessaladillo.pedrojoya.pr086.data.model;

public class Student {

    private final String name;
    private final String address;
    private final String phone;
    private final String grade;

    public Student(String name, String address, String phone, String grade) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.grade = grade;
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

}
