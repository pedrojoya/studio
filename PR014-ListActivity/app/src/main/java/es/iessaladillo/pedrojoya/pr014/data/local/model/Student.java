package es.iessaladillo.pedrojoya.pr014.data.local.model;

@SuppressWarnings("CanBeFinal")
public class Student {

    private String name;
    private int age;
    private String level;
    private String grade;

    public Student(String name, int age, String level, String grade) {
        this.name = name;
        this.age = age;
        this.level = level;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getLevel() {
        return level;
    }

    public String getGrade() {
        return grade;
    }

}
